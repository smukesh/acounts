package com.eazybytes.acounts;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef= "auditAwareImpl")
@OpenAPIDefinition(
		info = @Info(
				title = "Accounts microservice REST API Documentation",
				description = "Accounts microservice REST API Documentation",
				version = "v0",
				contact = @Contact(
						name = "Mukesh S",
						email = "mukeshkanna@gmail.com"
				)
		)
)
public class AcountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AcountsApplication.class, args);
	}

}
