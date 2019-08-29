package com.petshop.service_test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.petshop.http_errors.IdNotFoundException;
import com.petshop.repository.CustomerRepository;

@SpringBootTest
public class CustomerServiceTest {

	@Autowired
	CustomerRepository customerRepository;

	@Test
	void testGetCustomerById() throws IdNotFoundException {
		
	}
	
	public void InitVet() {
		
	}
}
