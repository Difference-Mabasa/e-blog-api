package com.enelosoft.eblog.eblogapi;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "EBlog API",
				description = "Spring boot Blog App API Documentation",
				version = "v1.0",
				contact = @Contact(
						name = "admin",
						email = "admin@eblog.com",
						url = "https://www.eblog.com"
				),
				license = @License(
						name = "Apache 2.0",
						url = "https://www.eblog.com/license"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "Github",
				url = "https://github.com/Difference-Mabasa/e-blog-api"
		)
)
public class EBlogApiApplication {

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(EBlogApiApplication.class, args);
	}

}
