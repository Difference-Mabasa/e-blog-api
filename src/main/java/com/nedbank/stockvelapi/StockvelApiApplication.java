package com.nedbank.stockvelapi;

import com.nedbank.stockvelapi.model.Role;
import com.nedbank.stockvelapi.repository.RoleRepository;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Stockvel API",
				description = "Spring boot Stockvel App API Documentation",
				version = "v1.0",
				contact = @Contact(
						name = "admin",
						email = "admin@stockvel.com",
						url = "https://www.stockvel.com"
				),
				license = @License(
						name = "Apache 2.0",
						url = "https://www.stockvel.com/license"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "Github",
				url = "https://github.com/Difference-Mabasa/stockvel-api"
		)
)
public class StockvelApiApplication implements CommandLineRunner {

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(StockvelApiApplication.class, args);
	}

	private RoleRepository roleRepository;

	public StockvelApiApplication(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	@Override
	public void run(String... args) throws Exception {
		Role adminRole = new Role();
		adminRole.setName("ROLE_ADMIN");
		roleRepository.save(adminRole);
		
		Role userRole = new Role();
		userRole.setName("ROLE_USER");
		roleRepository.save(userRole);
	}
}
