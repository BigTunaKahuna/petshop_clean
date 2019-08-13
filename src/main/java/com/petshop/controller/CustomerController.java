package com.petshop.controller;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.petshop.models.Customer;
import com.petshop.models.Vet;
import com.petshop.repository.CustomerRepository;
import com.petshop.repository.VetRepository;
import exception.ResourceNotFoundException;

@RestController
@RequestMapping("/api")
public class CustomerController {

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	VetRepository vetRepository;

	// REQUEST:GET @PATH: /api/customers
	@GetMapping("/customers")
	public List<Customer> getAllCustomers() {
		return customerRepository.findAll();
	}

	// REQUEST:GET @PATH: /api/customer/{id}
	@GetMapping("/customer/{id}")
	public Customer getCustomer(@PathVariable(value = "id") Long id) {
		return customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer", "id", id));
	}

	// REQUEST:POST @PATH: /api/vet/{vetId}/customer
	@PostMapping("/vet/{vetId}/customer")
	public Customer postCustomer(@PathVariable(value = "vetId") Long vetId, @RequestBody Customer customer) {
		Vet vet = vetRepository.findById(vetId).orElseThrow();
		System.out.println(vet.toString());
		System.out.println(customer.toString());
		vet.addCustomer(customer);
		customer.setVet(vet);
		return customerRepository.save(customer);

	}

	// REQUEST:PUT @PATH: /api/vet/{vetId}/customer/{customerId} - Transfer to another doctor
	@PutMapping("/vet/{vetId}/customer/{customerId}")
	public @Valid Customer putCustomer(@PathVariable(value = "vetId") Long vetId,
			@PathVariable(value = "customerId") Long customerId, @Valid @RequestBody Customer customer) {
//		return vetRepository.findById(vetId).map(vet -> {
//			if (!customerRepository.existsById(customerId)) {
//				throw new ResourceNotFoundException("customer", "id", customer);
//			}
//			Customer cust = customerRepository.findById(customerId)
//					.orElseThrow(() -> new ResourceNotFoundException("customer", "id", customerId));
//			cust.setName(customer.getName());
//			cust.setPetName(customer.getName());
//			cust.setPetSpecies(customer.getPetSpecies());
//			cust.setPhone(customer.getPhone());
//			cust.setVet(vet);
//			return customerRepository.save(cust);
//		}).orElseThrow(() -> new ResourceNotFoundException("customer", "id", vetId));
		Vet vet = vetRepository.findById(vetId).orElseThrow(() -> new ResourceNotFoundException("vet", "id", vetId));
		
		return null;
	}

	// REQUEST:PUT @PATH: /api/customer/{customerId} - Change data of a customer
	@PutMapping("/customer/{customerId}")
	public Customer putCustomerWithoutDoctor(@PathVariable(value = "customerId") Long customerId,
			@RequestBody Customer customer) {
		Customer cust = customerRepository.findById(customerId)
				.orElseThrow(() -> new ResourceNotFoundException("customer", "id", customerId));
		cust.setName(customer.getName());
		cust.setPetName(customer.getPetName());
		cust.setPetSpecies(customer.getPetSpecies());
		cust.setPhone(customer.getPhone());
		return customerRepository.save(cust);

	}

	// REQUEST:DELETE @PATH: /api/customer/{customerId}
	@DeleteMapping("customer/{customerId}")
	public ResponseEntity<?> deleteCustomer(@PathVariable(value = "customerId") Long customerId) {
//		return customerRepository.findById(customerId).map(cust -> {
//			customerRepository.delete(cust);
//			return ResponseEntity.ok().build();
//		}).orElseThrow(() -> new ResourceNotFoundException("customer", "id", customerId));
		Customer cust = customerRepository.findById(customerId)
				.orElseThrow(() -> new ResourceNotFoundException("name",  "id", customerId));
		customerRepository.delete(cust);
		return ResponseEntity.ok().build();
	}
}
