package com.petshop.service;

import com.petshop.dto.CustomerDTO;
import com.petshop.dto.CustomerWithRolesDTO;

import java.util.List;

public interface CustomerService {

	CustomerDTO getCustomerById(Long id);
	
	CustomerWithRolesDTO getCustomerWithRolesById(Long id); 

	List<CustomerDTO> getAllCustomers();

	CustomerDTO saveCustomer(Long vetId, CustomerDTO customerDTO);

	CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO);

	CustomerWithRolesDTO updateVetForCustomer(Long newVetId, Long customerId);

	void deleteCustomerById(Long id);

}