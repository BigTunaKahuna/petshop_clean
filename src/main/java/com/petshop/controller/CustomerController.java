package com.petshop.controller;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.petshop.models.Customer;
import com.petshop.service.CustomerService;

@RestController
@RequestMapping("/api")
public class CustomerController {

	@Autowired
	CustomerService customerService;

	// REQUEST:GET @PATH: /api/customers
	@GetMapping("/customers")
	public List<Customer> getAllCustomers() {
		return customerService.getAllCustomers();
	}

	// REQUEST:GET @PATH: /api/customer/{id}
	@GetMapping("/customer/{id}")
	public Customer getCustomer(@PathVariable(value = "id") Long id) {
		return customerService.getCustomerById(id);
	}

	// REQUEST:POST @PATH: /api/vet/{vetId}/customer
	@PostMapping("/vet/{vetId}/customer")
	public Customer postCustomer(@PathVariable(value = "vetId") Long vetId, @RequestBody Customer customer) {
		return customerService.saveCustomer(vetId, customer);

	}

	// REQUEST:PUT @PATH: /api/customer/{customerId}/{newVetId} - Transfer to
	// another doctor
	@PutMapping("/customer/{customerId}/{newVetId}")
	public @Valid Customer putCustomer(@PathVariable(value = "newVetId") Long newVetId,
			@PathVariable(value = "customerId") Long customerId, @Valid @RequestBody Customer customer) {
		return customerService.updateVetForCustomer(newVetId, customerId, customer);
	}

	// REQUEST:PUT @PATH: /api/customer/{customerId} - Change data of a customer
	@PutMapping("/customer/{customerId}")
	public Customer putCustomerWithoutDoctor(@PathVariable(value = "customerId") Long customerId,
			@RequestBody Customer customer) {
		return customerService.updateCustomer(customerId, customer);

	}

	// REQUEST:DELETE @PATH: /api/customer/{customerId}
	@DeleteMapping("customer/{customerId}")
	public void deleteCustomer(@PathVariable(value = "customerId") Long customerId) {
		customerService.deleteCustomer(customerId);
	}
}
