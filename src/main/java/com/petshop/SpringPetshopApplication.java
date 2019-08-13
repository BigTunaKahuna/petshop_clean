package com.petshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableJpaRepositories
@EnableTransactionManagement
@SpringBootApplication
public class SpringPetshopApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringPetshopApplication.class, args);
	}

}
