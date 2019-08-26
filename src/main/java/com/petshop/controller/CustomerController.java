package com.petshop.controller;

import com.petshop.dto.CustomerDTO;
import com.petshop.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	CustomerService customerService;

	// REQUEST:GET @PATH: /customer/{id}
	@GetMapping("/{id}")
	public CustomerDTO getCustomerById(@PathVariable(value = "id") Long id) {
		return customerService.getCustomerById(id);
	}

	// REQUEST:GET @PATH: /customer/all
	@GetMapping("/all")
	public List<CustomerDTO> getAllCustomers() {
		return customerService.getAllCustomers();
	}

	// REQUEST:POST @PATH: /customer/vet/{vetId}
	@PostMapping("/vet/{vetId}")
	public CustomerDTO saveCustomer(@PathVariable(value = "vetId") Long vetId, @RequestBody CustomerDTO customerDTO) {
		return customerService.saveCustomer(vetId, customerDTO);

	}

	// REQUEST:PUT @PATH: /customer/{customerId}/{newVetId} - Transfer to
	// another doctor
	@Transactional
	@PutMapping("/{customerId}/{newVetId}")
	public @Valid CustomerDTO updateCustomer(@PathVariable(value = "newVetId") Long newVetId,
			@PathVariable(value = "customerId") Long customerId, @Valid @RequestBody CustomerDTO customerDTO) {

		try {
			return customerService.updateVetForCustomer(newVetId, customerId, customerDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// REQUEST:PUT @PATH: /customer/{customerId} - Change data of a customer
	@PutMapping("/{customerId}")
	public CustomerDTO updateVetForCustomer(@PathVariable(value = "customerId") Long customerId,
			@RequestBody CustomerDTO customer) {
		return customerService.updateCustomer(customerId, customer);

	}

	// REQUEST:DELETE @PATH: /customer/{customerId}
	@DeleteMapping("/{customerId}")
	public void deleteCustomerById(@PathVariable(value = "customerId") Long customerId) {
		customerService.deleteCustomerById(customerId);
	}
}
