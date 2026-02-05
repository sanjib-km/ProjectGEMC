package in.iasst.projectGEMC.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.iasst.projectGEMC.entities.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	
	public User getUserByUserName(String userName);
	
	public List<User> findAllByRole(String role);
}
