package in.iasst.projectGEMC;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
@EnableJpaAuditing
@SpringBootApplication
public class ProjectGeMApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectGeMApplication.class, args);
	}

}
