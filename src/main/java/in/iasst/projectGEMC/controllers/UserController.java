package in.iasst.projectGEMC.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import in.iasst.projectGEMC.entities.User;
import in.iasst.projectGEMC.exception.ResourceNotFoundException;
import in.iasst.projectGEMC.services.UserServices;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {
	@Autowired
	UserServices user_services;
	
	
	@PostMapping("/login")
	public String userLogin(@RequestParam String userName,@RequestParam String password, HttpSession session, RedirectAttributes ra) {
		
		if(user_services.usernameExist(userName)) {
			if(user_services.validateUser(userName, password)) {
				session.setAttribute("userName", userName);
				return "redirect:/home";
			}
			else {
				ra.addFlashAttribute("message", "Invalid Username or Password");
				return "redirect:/map-login";
				}
		}
		else {
			ra.addFlashAttribute("message", "Invalid Username or Password");
			return "redirect:/map-login";
		}
	}
	
	
	@GetMapping("/logout")
	public String userLogout(HttpSession session) {
		session.removeAttribute("userName");
		return "login";
	}
	
	
	@GetMapping("/manage-users")
	public String manageUser(Model model, HttpServletResponse response,
			HttpSession session, RedirectAttributes ra) {
		
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
	    response.setHeader("Pragma", "no-cache"); // HTTP 1.0
	    response.setHeader("Expires", "0"); // Proxies
	    
	    String username = (String)session.getAttribute("userName");
	    
	    if(username != null && user_services.isAdmin(username)) {
	    	List<User> users= new ArrayList<>();
			for(User u: user_services.getAllUsers()) {
				if(!u.getRole().equals("admin")) {
					users.add(u);
				}
			}
			model.addAttribute("users", users);
			return "admin/manageUsers";
	    }
	    ra.addFlashAttribute("message", "Unauthorised!");
		return "redirect:/logout";
	    
	}
	
	
	@GetMapping("/admin/users/edit/{id}")
	public String editUserForm(@PathVariable("id") int id, Model model, HttpSession session, 
			HttpServletResponse response, RedirectAttributes ra) {
		
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
	    response.setHeader("Pragma", "no-cache"); // HTTP 1.0
	    response.setHeader("Expires", "0"); // Proxies
	    
	    String username = (String)session.getAttribute("userName");
	    
	    if(username != null && user_services.isAdmin(username)) {
	    	model.addAttribute("user", user_services.getUserById(id).orElseThrow(() -> new ResourceNotFoundException("User with ID " + id + " not found")));
			String formToken = UUID.randomUUID().toString();
			session.setAttribute("formToken", formToken);
			model.addAttribute("token", formToken);
			return "admin/userForm";
	    }
	    ra.addFlashAttribute("message", "Unauthorised!");
		return "redirect:/logout";
	}
	
	
	@GetMapping("/admin/users/new")
	public String newUserForm(Model model, HttpSession session,
			HttpServletResponse response, RedirectAttributes ra) {
		
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
	    response.setHeader("Pragma", "no-cache"); // HTTP 1.0
	    response.setHeader("Expires", "0"); // Proxies
	    
	    String username = (String)session.getAttribute("userName");
	    
	    if(username != null && user_services.isAdmin(username)) {
			String formToken = UUID.randomUUID().toString();
			session.setAttribute("formToken", formToken);
			model.addAttribute("token", formToken);
			model.addAttribute("user", new User());
			return "admin/userForm";
	    }
	    ra.addFlashAttribute("message", "Unauthorised!");
		return "redirect:/logout";
	}
	
	
	@GetMapping("/admin/edit/{id}")
	public String editAdminForm(@PathVariable("id") int id, Model model, HttpSession session,
			RedirectAttributes ra, HttpServletResponse response) {
		
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
	    response.setHeader("Pragma", "no-cache"); // HTTP 1.0
	    response.setHeader("Expires", "0"); // Proxies
	    
	    String username = (String)session.getAttribute("userName");
	    
	    if(username != null && user_services.isAdmin(username)
	    		&& user_services.isAdmin(id)) {
	    	model.addAttribute("user", user_services.getUserById(id).orElseThrow(() -> new ResourceNotFoundException("User with ID " + id + " not found")));
			String formToken = UUID.randomUUID().toString();
			session.setAttribute("formToken", formToken);
			model.addAttribute("token", formToken);
			return "admin/admin-form";
	    }
	    ra.addFlashAttribute("message", "Unauthorised!");
		return "redirect:/logout";
		
	}
	
	
	@PostMapping("/admin/users/save")
	public String userAdd(@ModelAttribute User user, Model model,@RequestParam("token") String requestToken, 
            HttpSession session, RedirectAttributes ra) {
		
		String username = (String)session.getAttribute("userName");
	    
		if(username != null && user_services.isAdmin(username)) {
			String sessionToken = (String) session.getAttribute("formToken");
			if (sessionToken == null || !sessionToken.equals(requestToken)) {
	          
	            ra.addFlashAttribute("message", "This form has already been submitted.");
	            return "redirect:/manage-users";
	        }
			boolean isNewUser = (user.getUserid() == 0 || !user_services.getUserById(user.getUserid()).isPresent());
			
		    User existingUserWithSameUsername = user_services.findByUserName(user.getUserName());
		    
		    if (isNewUser) {
		    	if(!user_services.usernameExist(user.getUserName())) {
		    		session.removeAttribute("formToken");
		    		user_services.saveUser(user);
		    		ra.addFlashAttribute("message", "User added successfully.");
					return "redirect:/admin/users/new";
				}
				else {
					model.addAttribute("message", "Username already exist, try different username.");
					model.addAttribute("user", user);
					model.addAttribute("token", requestToken);
					return "admin/userForm";
				}
		    } else {
		    	if (existingUserWithSameUsername != null && !(existingUserWithSameUsername.getUserid()==user.getUserid())) {
			        ra.addFlashAttribute("message", "Username already taken by another user. Try a different one.");
			        session.removeAttribute("formToken");
			        if(user_services.isAdmin(user.getUserid())) {
			        	return "redirect:/admin/edit/"+user.getUserid();
			        }
			        else {
			        	return "redirect:/admin/users/edit/"+user.getUserid();
			        }
			    }
		    	else {
		    		session.removeAttribute("formToken");
		    		user_services.saveUser(user);
			        ra.addFlashAttribute("message", "User updated successfully.");
			        if(user_services.isAdmin(user.getUserid())) {
			        	return "redirect:/account";
			        }
			        return "redirect:/manage-users";
			    }
		    }
		}
	    ra.addFlashAttribute("message", "Unauthorised!");
		return "redirect:/logout";
	}
	
}
