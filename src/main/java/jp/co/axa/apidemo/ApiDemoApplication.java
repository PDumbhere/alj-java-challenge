package jp.co.axa.apidemo;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import jp.co.axa.apidemo.entities.Role;
import jp.co.axa.apidemo.entities.User;
import jp.co.axa.apidemo.repositories.RoleRepository;
import jp.co.axa.apidemo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;


@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Employee CRM REST APIs",
				description = "Employee CRM REST APIs Documentation as a Code Test round for AXA Life Insurance, Japan",
				version = "v1.0",
				contact = @Contact(
						name = "Pratik",
						email = "pratikdumbhere@hotmail.com"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "Employee CRM REST APIs Documentation for ALJ coding test",
				url = "https://github.com/PratikD/alj-java-challange"
		)
)
@EnableWebSecurity
//@EnableSwagger2
public class ApiDemoApplication implements CommandLineRunner {
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private UserRepository userRepository;
	public static void main(String[] args) {
		SpringApplication.run(ApiDemoApplication.class, args);
	}

	@Transactional
	@Override
	public void run(String... args) throws Exception {

		Role	 adminRole = new Role();
		adminRole.setName("ROLE_ADMIN");
		roleRepository.save(adminRole);

		Role userRole = new Role();
		userRole.setName("ROLE_USER");
		Role savedUser =roleRepository.save(userRole);

		Set<Role> roles= new HashSet<>();
		roles.add(roleRepository.findByName("ROLE_ADMIN").get());
		roles.add(savedUser);
		User admin = new User();
		admin.setName("Admin");
		admin.setUsername("admin");
		admin.setEmail("admin@gmail.com");
		admin.setPassword("$2a$10$tewt5neYVkg5zCDi4zrZA.PJEG3RCqBSrLZVcdrW4fNSSE1SdeEVO");
		admin.setRoles(roles);

		if(!userRepository.existsByUsername("Admin")){
			userRepository.save(admin);
		}
	}
}
