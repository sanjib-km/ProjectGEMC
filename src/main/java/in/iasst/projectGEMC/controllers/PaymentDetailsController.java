package in.iasst.projectGEMC.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

//import in.iasst.projectGEMC.entities.ConsigneeDetails;
import in.iasst.projectGEMC.entities.Contract;
import in.iasst.projectGEMC.entities.PaymentDetails;
import in.iasst.projectGEMC.entities.User;
import in.iasst.projectGEMC.services.ContractServices;
import in.iasst.projectGEMC.services.UserServices;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class PaymentDetailsController {
	
	@Autowired
    private ContractServices contract_services;
	@Autowired
	UserServices user_services;

    @GetMapping("/payment_details/add/{id}")
    public String showPaymentDetailsForm(@PathVariable("id") int id, Model model,
    		HttpServletResponse response, RedirectAttributes ra, HttpSession session) {
    	
    	response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
	    response.setHeader("Pragma", "no-cache"); // HTTP 1.0
	    response.setHeader("Expires", "0"); // Proxies
    	
	    String username = (String)session.getAttribute("userName");
		
	    if(username != null && user_services.isPaymentAuth(username)) {
			Contract contract = contract_services.getContractById(id)
					.orElseThrow(() -> new IllegalArgumentException("Invalid contract Id:" + id));
	    	model.addAttribute("contract",contract);
	    	if(contract.getPaymentDetails()==null) {
	    		model.addAttribute("paymentDetails", new PaymentDetails());
	    	}else {
	    		model.addAttribute("paymentDetails", contract.getPaymentDetails());
	    	}
	    	
	        return "paymentAuth/paymentDetailsForm";
		}
		ra.addFlashAttribute("message", "Unauthorised!");
		return "redirect:/logout";
    }
    
    
    @PostMapping("/payment_details/save-details")
    public String savePaymentDetails(@ModelAttribute("paymentDetails") PaymentDetails details, 
	        @RequestParam("contractId") int contractId, RedirectAttributes ra, 
	        HttpServletResponse response, HttpSession session) {
    	
    	response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
	    response.setHeader("Pragma", "no-cache"); // HTTP 1.0
	    response.setHeader("Expires", "0"); // Proxies
    	
	    String username = (String)session.getAttribute("userName");
		
	    if(username != null && user_services.isPaymentAuth(username)) {
			User paymentAuth=user_services.findByUserName(username);
	    	Contract contract = contract_services.getContractById(contractId)
					.orElseThrow(() -> new IllegalArgumentException("Invalid contract Id:" + contractId));
	    	details.setContract(contract);
	    	contract.setPaymentDetails(details);
	    	contract.setPaymentAuth(paymentAuth);
	    	paymentAuth.getPaidContracts().add(contract);
	    	contract_services.saveContract(contract);
	    	user_services.saveUser(paymentAuth);
		}
    	ra.addFlashAttribute("message", "Details saved successfully!");
    	return "redirect:/home";
    }
}
