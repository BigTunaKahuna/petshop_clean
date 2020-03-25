package com.petshop.controller;

import com.petshop.dto.CustomerDTO;
import com.petshop.dto.CustomerWithRolesDTO;
import com.petshop.exception.IdNotFoundException;
import com.petshop.service.CustomerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	// REQUEST:GET @PATH: /customer/{id} - Retrieve customer based on ID
	@GetMapping("/{id}")
	public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable(value = "id") Long id) {
		CustomerDTO customerDTO = customerService.getCustomerById(id);
		return new ResponseEntity<>(customerDTO, HttpStatus.OK);
	}

	// REQUEST:GET @PATH: /customer/all - Retrieve all customers
	@GetMapping("/all")
	public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
		List<CustomerDTO> allCustomers = customerService.getAllCustomers();
		return new ResponseEntity<>(allCustomers, HttpStatus.OK);
	}

	// REQUEST:POST @PATH: /customer/vet/{vetId} - Save a customer
	@PostMapping("/vet/{vetId}")
	public ResponseEntity<CustomerDTO> saveCustomer(@PathVariable(value = "vetId") Long vetId,
			@Valid @RequestBody CustomerDTO customerDTO) {
		CustomerDTO customer = customerService.saveCustomer(vetId, customerDTO);
		return new ResponseEntity<>(customer, HttpStatus.CREATED);
	}

	// REQUEST:PUT @PATH: /customer/{customerId}/{newVetId} - Transfer to
	// another doctor
	@PutMapping("/{customerId}/{newVetId}")
	public @Valid ResponseEntity<CustomerWithRolesDTO> updateVetForCustomer(@PathVariable(value = "newVetId") Long newVetId,
			@PathVariable(value = "customerId") Long customerId) {

		try {
			CustomerWithRolesDTO customer = customerService.updateVetForCustomer(newVetId, customerId);
			return new ResponseEntity<>(customer, HttpStatus.CREATED);
		} catch (IdNotFoundException e) {
			throw new IdNotFoundException();
		}
	}

	// REQUEST:PUT @PATH: /customer/{customerId} - Change data of a customer
	@PutMapping("/{customerId}")
	public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable(value = "customerId") Long customerId,
			@Valid @RequestBody CustomerDTO customer) {
		CustomerDTO customerDTO = customerService.updateCustomer(customerId, customer);
		return new ResponseEntity<>(customerDTO, HttpStatus.CREATED);

	}

	// REQUEST:DELETE @PATH: /customer/{customerId} - Delete a customer
	@DeleteMapping("/{customerId}")
	public ResponseEntity<String> deleteCustomerById(@PathVariable(value = "customerId") Long customerId) {
		customerService.deleteCustomerById(customerId);
		return new ResponseEntity<>("The customer was deleted succesfully!", HttpStatus.OK);
	}
}
