package in.iasst.projectGEMC.services;
import java.util.List;
import java.util.Optional;

import in.iasst.projectGEMC.entities.User;

public interface UserServices {
	public String saveUser(User user);
	
	public Optional<User> getUserById(Integer id);
	
	public User findByUserName(String userName);
	
	public Boolean usernameExist(String userName);
 	
	public Boolean validateUser(String userName, String password);

	public List<User> getAllUsers();

	//public String deleteUser(int id);
	
	public List<User> findAllUsersByRole(String role);
	
	public Boolean isAdmin(int id);
	
	public Boolean isAdmin(String username);
	
	public Boolean isBuyer(String username);
	
	public Boolean isConsignee(String username);
		
	public Boolean isPaymentAuth(String username);
}
