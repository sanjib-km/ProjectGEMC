package in.iasst.projectGEMC.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import in.iasst.projectGEMC.entities.User;
import in.iasst.projectGEMC.repositories.UserRepository;
@Component
public class AdminUserSeeder implements CommandLineRunner{

	@Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            User admin = new User();
            admin.setUserName("admin");
            admin.setPassword("admin123");
            admin.setName("Admin");
            admin.setRole("admin");
            userRepository.save(admin);
            System.out.println("Initial Admin user created successfully. ");
            System.out.println("Username : admin, Password: admin123");
        }
    }
}
