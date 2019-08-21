package com.petshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
public class SpringPetshopApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringPetshopApplication.class, args);
	}

}
