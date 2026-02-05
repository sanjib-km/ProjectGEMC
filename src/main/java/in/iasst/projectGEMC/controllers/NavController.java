package in.iasst.projectGEMC.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.iasst.projectGEMC.entities.Contract;
import in.iasst.projectGEMC.entities.User;
import in.iasst.projectGEMC.services.ContractServices;
import in.iasst.projectGEMC.services.UserServices;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class NavController {
	@Autowired
	UserServices user_services;
	@Autowired
	ContractServices contract_service;

	@GetMapping("/")
	public String mapIndex() {
		return "index";
	}
	
	
	@GetMapping("/map-login")
	public String mapLogin() {
		return "login";
	}
	
	
	@GetMapping("/view")
	public String mapView(@RequestParam(defaultValue = "0") int page, 
			HttpServletResponse response, HttpSession session, Model model) {
		
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
	    response.setHeader("Pragma", "no-cache"); // HTTP 1.0
	    response.setHeader("Expires", "0"); // Proxies
	    
		String username = (String)session.getAttribute("userName");
		Pageable pageable = PageRequest.of(page, 20, Sort.by("contractId").descending());
		Page<Contract> contractPage = contract_service.contractPage(pageable);

		model.addAttribute("contracts", contractPage.getContent());
		model.addAttribute("currentPage", page);
	    model.addAttribute("totalPages", contractPage.getTotalPages());
		
	    if(username==null) {
			return "publicView";
		}
		else if(user_services.isBuyer(username)) {
			return "buyer/buyerView";
		}
		else if(user_services.isConsignee(username)){
			return "consignee/consigneeView";
		}
		else if(user_services.isPaymentAuth(username)) {
			return "paymentAuth/paymentAuthView";
		}
		else if(user_services.isAdmin(username)) {
			return "admin/adminView";
		}
		else {
			return "redirect:/logout";
		}
	}
	
	@GetMapping("/home")
	public String mapHome(@RequestParam(defaultValue = "0") int page, HttpSession session, Model model) {
		
		User user=user_services.findByUserName((String)session.getAttribute("userName"));
		Pageable pageable = PageRequest.of(page, 20, Sort.by("contractId").descending());
		
		if(user==null) {
			return "redirect:/";
		}
		else if(user.getRole().equals("buyer")) {
			
			List<User> consigneeList = user_services.findAllUsersByRole("consignee");
			model.addAttribute("consignees",consigneeList);
			model.addAttribute("user",user);
			return "buyer/buyerHome";
		}
		else if(user.getRole().equals("consignee")){
			
			Page<Contract> contractPage = contract_service.getConsigneePendingContracts(user, pageable);
	        model.addAttribute("contracts", contractPage.getContent());
	        model.addAttribute("currentPage", page);
	        model.addAttribute("totalPages", contractPage.getTotalPages());
	        return "consignee/consigneeHome";
		}
		else if(user.getRole().equals("paymentAuth")) {

			Page<Contract> contractPage = contract_service.getPaymentAuthPendingContracts(user, pageable);        
	        model.addAttribute("contracts", contractPage.getContent());
	        model.addAttribute("currentPage", page);
	        model.addAttribute("totalPages", contractPage.getTotalPages());
	        return "paymentAuth/paymentAuthHome";
		}
		else if(user.getRole().equals("admin")) {
			
			return "admin/adminHome";
		}
		else {
			return "redirect:/logout";
		}
	}

	
	@GetMapping("/history")
	public String mapHistory(@RequestParam(defaultValue = "0") int page, 
			HttpServletResponse response, HttpSession session, Model model) {
		
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
	    response.setHeader("Pragma", "no-cache"); // HTTP 1.0
	    response.setHeader("Expires", "0"); // Proxies
	   
	    String username = (String) session.getAttribute("userName");
	    User user = user_services.findByUserName(username);

	    if (user == null) {
	        return "redirect:/map-login";
	    }

	    Pageable pageable = PageRequest.of(page, 20, Sort.by("contractId").descending());
	    Page<Contract> contractPage;

	    if (user.getRole().equals("buyer")) {
	        // Fetch only contracts where this user is the buyer
	        contractPage = contract_service.getContractsByBuyer(user, pageable);
	        model.addAttribute("contract_list", contractPage.getContent());
	        model.addAttribute("currentPage", page);
	        model.addAttribute("totalPages", contractPage.getTotalPages());
	        return "buyer/buyerHistory";
	    } 
	    else if (user.getRole().equals("consignee")) {
	        // Fetch only contracts where this user is consignee AND details exist
	        contractPage = contract_service.getContractsByConsigneeWithDetails(user, pageable);
	        model.addAttribute("contract_list", contractPage.getContent());
	        model.addAttribute("currentPage", page);
	        model.addAttribute("totalPages", contractPage.getTotalPages());
	        return "consignee/consigneeHistory";
	    } 
	    else if (user.getRole().equals("paymentAuth")) {
	        // Fetch only contracts where this user is the payment authority
	        contractPage = contract_service.getContractsByPaymentAuth(user, pageable);
	        model.addAttribute("contract_list", contractPage.getContent());
	        model.addAttribute("currentPage", page);
	        model.addAttribute("totalPages", contractPage.getTotalPages());
	        return "paymentAuth/paymentAuthHistory";
	    } 
	    else if (user.getRole().equals("admin")) {
	        return "admin/adminHome";
	    } 
	    else {
	        return "redirect:/logout";
	    }
	}
	
	
	@GetMapping("/account")
	public String mapAccount(HttpSession session, Model model) {
		
		User user=user_services.findByUserName((String)session.getAttribute("userName"));
		model.addAttribute("user",user);
		
		if(user==null) {
			return "redirect:/map-login";
		}
		else if(user.getRole().equals("buyer")) {
			return "buyer/buyerAccount";
		}
		else if(user.getRole().equals("consignee")){
			return "consignee/consigneeAccount";
		}
		else if(user.getRole().equals("paymentAuth")) {
			return "paymentAuth/paymentAuthAccount";
		}
		else if(user.getRole().equals("admin")) {
			return "admin/adminAccount";
		}
		else {
			return "redirect:/logout";
		}
	}
	
	
}
