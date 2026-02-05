package in.iasst.projectGEMC.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.iasst.projectGEMC.entities.User;
import in.iasst.projectGEMC.exception.ResourceNotFoundException;
import in.iasst.projectGEMC.repositories.UserRepository;

@Service
public class UserServicesImplementation implements UserServices {
	@Autowired
	UserRepository user_repo;

	@Override
	public String saveUser(User user) {
		user_repo.save(user);
		return "User added";
	}

	@Override
	public Boolean validateUser(String userName, String password) {
		if(user_repo.getUserByUserName(userName).getPassword().equals(password)){
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public Boolean usernameExist(String userName) {
		if(user_repo.getUserByUserName(userName)==null) {
			return false;
		}
		else {
			return true;
		}
	}
	
	@Override
	public User findByUserName(String userName) {
		return user_repo.getUserByUserName(userName);
	}

	@Override
	public Optional<User> getUserById(Integer id) {
		
		return user_repo.findById(id);
	}

	@Override
	public List<User> getAllUsers() {
		
		return user_repo.findAll();
	}

//	@Override
//	public String deleteUser(int id) {
//		user_repo.deleteById(id);
//		return "user-deleted";
//	}
	
	
	@Override
	public List<User> findAllUsersByRole(String role) {
		
		return user_repo.findAllByRole(role);
	}

	@Override
	public Boolean isAdmin(int id) {
		if(user_repo.findById(id)
				.orElseThrow(() ->new ResourceNotFoundException("User with ID " + id + " not found"))
				.getRole().equals("admin")) {
			return true;
		}
		else {
		return false;
		}
	}

	@Override
	public Boolean isAdmin(String username) {
		if(user_repo.getUserByUserName(username).getRole().equals("admin")) {
			return true;
		}
		else {
			return false;
		}
	}


	@Override
	public Boolean isBuyer(String username) {
		if(user_repo.getUserByUserName(username).getRole().equals("buyer")) {
			return true;
		}
		else {
			return false;
		}
	}


	@Override
	public Boolean isConsignee(String username) {
		if(user_repo.getUserByUserName(username).getRole().equals("consignee")) {
			return true;
		}
		else {
			return false;
		}
	}


	@Override
	public Boolean isPaymentAuth(String username) {
		if(user_repo.getUserByUserName(username).getRole().equals("paymentAuth")) {
			return true;
		}
		else {
			return false;
		}
	}

}
