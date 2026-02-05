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

import in.iasst.projectGEMC.entities.ConsigneeDetails;
import in.iasst.projectGEMC.entities.Contract;
//import in.iasst.projectGEMC.entities.User;
import in.iasst.projectGEMC.services.ConsigneeDetailsServices;
import in.iasst.projectGEMC.services.ContractServices;
import in.iasst.projectGEMC.services.UserServices;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
@Controller
public class ConsigneeDetailsController {
	@Autowired
	ContractServices contract_services;
	@Autowired
	ConsigneeDetailsServices consignee_service;
	@Autowired
	UserServices user_services;
	
	
	@GetMapping("/consignee/add/{id}")
	public String showConsigneeForm(@PathVariable("id") int id, Model model,
			HttpServletResponse response, RedirectAttributes ra, HttpSession session) {	
		
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
	    response.setHeader("Pragma", "no-cache"); // HTTP 1.0
	    response.setHeader("Expires", "0"); // Proxies
		
	    String username = (String)session.getAttribute("userName");
		
	    if(username != null && user_services.isConsignee(username)) {
			Contract contract = contract_services.getContractById(id)
			        .orElseThrow(() -> new IllegalArgumentException("Invalid contract Id:" + id));
			    model.addAttribute("contract", contract);
			    model.addAttribute("consigneeDetails", new ConsigneeDetails());
			    return "consignee/consigneeForm";
		}
		ra.addFlashAttribute("message", "Unauthorised!");
		return "redirect:/logout";
	}
	
	
	@PostMapping("/consignee/save-details")
	public String saveConsigneeDetails(
	        @ModelAttribute("consigneeDetails") ConsigneeDetails details, 
	        @RequestParam("contractId") int contractId, RedirectAttributes ra,
	        HttpServletResponse response, HttpSession session) {
		
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
	    response.setHeader("Pragma", "no-cache"); // HTTP 1.0
	    response.setHeader("Expires", "0"); // Proxies
		
	    String username = (String)session.getAttribute("userName");
		
	    if(username != null && user_services.isConsignee(username)) {
			int totalProcessed = details.getAceptedQuantity() + details.getRejectedQuantity();
		    
		    if (totalProcessed > details.getRecieptQuantity()) {
		    	ra.addFlashAttribute("message", 
		            "Error: Accepted quantity cannot be more than receipt quantity!");
		        
		        return "redirect:/consignee/add/" + contractId;
		    }
		    else {
		    // 1. Fetch the existing contract from the database
		    Contract contract = contract_services.getContractById(contractId)
		            .orElseThrow(() -> new IllegalArgumentException("Invalid contract Id:" + contractId));

		    // 2. Establish the relationship (Both sides if bidirectional)
		    details.setContract(contract);
		    contract.setConsigneeDetails(details);

		    // 3. Save the details
		    consignee_service.saveConsigneeDetails(details);

		    // 4. Redirect back to the home page with a success message
		    ra.addFlashAttribute("message", "Details saved successfully!");
		    return "redirect:/home";
		    }
		}
		ra.addFlashAttribute("message", "Unauthorised!");
		return "redirect:/logout";
	}
	

	@GetMapping("/consignee/edit/{id}")
	public String showUpdateConsigneeForm(@PathVariable("id") int id, Model model,
			HttpServletResponse response, RedirectAttributes ra, HttpSession session) {
		
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
	    response.setHeader("Pragma", "no-cache"); // HTTP 1.0
	    response.setHeader("Expires", "0"); // Proxies
		
	    String username = (String)session.getAttribute("userName");
		
	    if(username != null && user_services.isConsignee(username)) {
			Contract contract = contract_services.getContractById(id)
					.orElseThrow(() -> new IllegalArgumentException("Invalid contract Id:" + id));
			model.addAttribute("contract",contract);
			return "consignee/consigneeEditForm";
		}
		ra.addFlashAttribute("message", "Unauthorised!");
		return "redirect:/logout";
	}
	
	
	@PostMapping("/consignee/update-details")
	public String updateConsigneeDetails(@ModelAttribute("contract") Contract contract,
			HttpServletResponse response, RedirectAttributes ra, HttpSession session) {
		
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
	    response.setHeader("Pragma", "no-cache"); // HTTP 1.0
	    response.setHeader("Expires", "0"); // Proxies
		
	    String username = (String)session.getAttribute("userName");
		
	    if(username != null && user_services.isConsignee(username)) {
			Contract existingContract = contract_services.getContractById(contract.getContractId())
					.orElseThrow(() -> new IllegalArgumentException("Invalid contract Id:"));
			existingContract.setConsigneeDetails(contract.getConsigneeDetails());
			contract_services.saveContract(existingContract);
			ra.addFlashAttribute("message", "Details Updated successfully!");
			return "redirect:/history";
		}
		ra.addFlashAttribute("message", "Unauthorised!");
		return "redirect:/logout";
	}

}
