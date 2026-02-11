package in.iasst.projectGEMC.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import in.iasst.projectGEMC.entities.Contract;
import in.iasst.projectGEMC.entities.User;
import in.iasst.projectGEMC.services.ContractServices;
import in.iasst.projectGEMC.services.UserServices;
import jakarta.servlet.http.HttpSession;

@Controller
public class ContractController {
	@Autowired
	ContractServices contract_service;
	@Autowired
	UserServices user_services;
	
	@PostMapping("/addContract")
	public String addContract(@ModelAttribute Contract contract, RedirectAttributes ra, HttpSession session) {
		
		User user=user_services.findByUserName((String)session.getAttribute("userName"));
		
		if (user != null && user.getRole().equals("buyer")) {
			if(contract_service.existsByGemNo(contract.getGemNo())){
				ra.addFlashAttribute("errorMsg","GEMC Number already exists");
				return "redirect:/home";
			}
			else {
				if ("core".equalsIgnoreCase(contract.getSourceOfFund())) {
			        contract.setProjectName(null);
			    }
				contract.setBuyerName(user);
				user.getBoughtContracts().add(contract);
				User consignee=contract.getConsigneeName();
				if (consignee != null) {
				    consignee.getReceivedContracts().add(contract);
				}
				user_services.saveUser(user);
				user_services.saveUser(consignee);
				contract_service.saveContract(contract);
				ra.addFlashAttribute("message","Contract Added Successfully");
				return "redirect:/home";
			}
		}
		ra.addFlashAttribute("message", "Unauthorised!");
		return "redirect:/logout";
	}
	
	@GetMapping("/buyer/edit/{id}")
	public String showUpdateContractForm(@PathVariable("id") int id, Model model,
			RedirectAttributes ra, HttpSession session) {
		
		String username = (String)session.getAttribute("userName");
		
		if(username != null && user_services.isBuyer(username)) {
			Contract contract = contract_service.getContractById(id)
					.orElseThrow(() -> new IllegalArgumentException("Invalid contract Id:" + id));
			List<User> consigneeList = user_services.findAllUsersByRole("consignee");
			model.addAttribute("consignees",consigneeList);
			model.addAttribute("contract",contract);
			return "buyer/contractEditForm";
		}
		ra.addFlashAttribute("message", "Unauthorised!");
		return "redirect:/logout";
	}
	

	@PostMapping("/contract/update")
	public String updateContract(@ModelAttribute Contract contract,
			HttpSession session, RedirectAttributes ra) {
		
		String username = (String)session.getAttribute("userName");
		
		if(username != null && user_services.isBuyer(username)) {
			Contract old_contract = contract_service.getContractById(contract.getContractId())
					.orElseThrow(() -> new IllegalArgumentException("Invalid contract Id."));
			
			if(contract_service.existsByGemNo(contract.getGemNo()) &&
					!old_contract.getGemNo().equals(contract.getGemNo())){
				
					ra.addFlashAttribute("errorMsg","GEMC Number already exists");
					return "redirect:/buyer/edit/"+contract.getContractId();
				
			}
			else {
				if ("core".equalsIgnoreCase(contract.getSourceOfFund())) {
			        contract.setProjectName(null);
			    }
				contract.setConsigneeDetails(old_contract.getConsigneeDetails());
				contract.setPaymentDetails(old_contract.getPaymentDetails());
				contract.setPaymentAuth(old_contract.getPaymentAuth());
				User new_consignee=contract.getConsigneeName();
				User old_consignee=old_contract.getConsigneeName();
				if(new_consignee != null && new_consignee != old_consignee) {
					old_consignee.getReceivedContracts().remove(old_contract);
					user_services.saveUser(old_consignee);
					new_consignee.getReceivedContracts().add(contract);
				    user_services.saveUser(new_consignee);
				}
				contract_service.saveContract(contract);
				ra.addFlashAttribute("message","Contract Updated Successfully");
				return "redirect:/history";
			}
		}
		ra.addFlashAttribute("message", "Unauthorised!");
		return "redirect:/logout";	
	}
	
	
	@GetMapping("/contract/view/{id}")
	public String viewData(@PathVariable("id") int id, Model model) {
		
		Contract contract = contract_service.getContractById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid contract Id:" + id));
		model.addAttribute("contract",contract);
		return "viewData";
	}
	
	
	@GetMapping("/search")
	public String search(@RequestParam(required = false) String gemNo,
	                     @RequestParam(required = false) LocalDate startDate,
	                     @RequestParam(required = false) LocalDate endDate,
	                     @RequestParam(defaultValue = "0") int page, // Added page param
	                     Model model, HttpSession session) {

	    String searchGemNo = (gemNo != null && !gemNo.isEmpty()) ? gemNo : null;
	    String username = (String)session.getAttribute("userName");
	    
	    // 1. Create the Pageable object (20 results per page)
	    Pageable pageable = PageRequest.of(page, 20, Sort.by("contractId").descending());
	    Page<Contract> contractPage;

	    // 2. Call service with pageable
	    if (searchGemNo != null || (startDate != null && endDate != null)) {
	        contractPage = contract_service.findByGemNoOrDateRange(searchGemNo, startDate, endDate, pageable);
	    } else {
	        contractPage = Page.empty(); 
	    }

	    // 3. Add pagination data to model
	    model.addAttribute("contracts", contractPage.getContent());
	    model.addAttribute("currentPage", page);
	    model.addAttribute("totalPages", contractPage.getTotalPages());
	    
	    // Keep search params for the "Next/Previous" links
	    model.addAttribute("gemNo", gemNo);
	    model.addAttribute("startDate", startDate);
	    model.addAttribute("endDate", endDate);
	    model.addAttribute("status", "search");

	    // Your existing Role-based view logic
	    if (username == null) return "publicView";
	    if (user_services.isBuyer(username)) return "buyer/buyerView";
	    if (user_services.isConsignee(username)) return "consignee/consigneeView";
	    if (user_services.isPaymentAuth(username)) return "paymentAuth/paymentAuthView";
	    if (user_services.isAdmin(username)) return "admin/adminView";
	    
	    return "redirect:/logout";
	}
	
	@PostMapping("/contract/delete/{id}")
	public String deleteContract(@PathVariable("id") int id, HttpSession session, RedirectAttributes ra) {
		
		String username = (String)session.getAttribute("userName");
		
		if(username != null && user_services.isAdmin(username)) {
			if(contract_service.isContractExist(id)) {
				contract_service.deleteContractById(id);
				ra.addFlashAttribute("message", "Contract Deleted successfully!");
				return "redirect:/view";
			}
			else {
				ra.addFlashAttribute("message", "Contract Does not Exist!");
				return "redirect:/view";
			}
		}
		ra.addFlashAttribute("message", "Unauthorised!");
		return "redirect:/logout";	
	}
	
}
