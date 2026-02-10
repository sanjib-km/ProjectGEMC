package in.iasst.projectGEMC.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
        
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        String username = (String)session.getAttribute("userName");
        
        if(username != null && user_services.isPaymentAuth(username)) {
            Contract contract = contract_services.getContractById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid contract Id:" + id));
            
            // Ensure the list is initialized so Thymeleaf can iterate over it
            if(contract.getPaymentDetails() == null) {
                contract.setPaymentDetails(new ArrayList<>());
            }
            
            model.addAttribute("contract", contract);
            return "paymentAuth/paymentDetailsForm";
        }
        ra.addFlashAttribute("message", "Unauthorised!");
        return "redirect:/logout";
    }
    
    @PostMapping("/payment_details/save-details")
    public String savePaymentDetails(@ModelAttribute("contract") Contract incomingContract, 
            RedirectAttributes ra, HttpSession session) {
        
        String username = (String)session.getAttribute("userName");
        
        if(username != null && user_services.isPaymentAuth(username)) {
            // 1. Fetch the REAL existing contract from DB using the ID from the hidden field
            Contract existingContract = contract_services.getContractById(incomingContract.getContractId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid contract Id"));

            // 2. Clear the old list and add the new list from the form
            // This prevents orphan records and ensures the bi-directional link is set
            existingContract.getPaymentDetails().clear(); 
            
            if (incomingContract.getPaymentDetails() != null) {
                for (PaymentDetails detail : incomingContract.getPaymentDetails()) {
                    detail.setContract(existingContract); // Set the back-reference
                    existingContract.getPaymentDetails().add(detail);
                }
            }

            // 3. Set the Payment Authority user
            User paymentAuth = user_services.findByUserName(username);
            existingContract.setPaymentAuth(paymentAuth);
            
            // 4. Save the fully populated existing contract
            contract_services.saveContract(existingContract);
            System.out.print(existingContract.getPaymentDetails().size());
            System.out.print(existingContract.getPaymentAuth().getUserName());
        }
        
        ra.addFlashAttribute("message", "Details saved successfully!");
        return "redirect:/home";
    }

}