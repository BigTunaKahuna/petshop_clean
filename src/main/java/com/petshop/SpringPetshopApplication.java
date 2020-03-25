package com.petshop;

import java.security.Principal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableTransactionManagement
@SpringBootApplication
@EnableAsync
@EnableJpaRepositories
@RestController
public class SpringPetshopApplication {
	

	public static void main(String[] args) {
		SpringApplication.run(SpringPetshopApplication.class, args);
	}

	@PreAuthorize("#oauth2.hasScope('read')")
	@GetMapping("/user")
	public Principal user(Principal principal) {
		var sec = SecurityContextHolder.getContext().getAuthentication();
		return sec;
	}

}
