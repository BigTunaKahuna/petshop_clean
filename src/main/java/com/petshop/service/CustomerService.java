package com.petshop.service;

import com.petshop.dto.CustomerDTO;
import com.petshop.models.Customer;

import java.util.List;

public interface CustomerService {

    CustomerDTO getCustomerById(Long id);

	List<Customer> getAllCustomers();

	Customer saveCustomer(Long vetId, Customer customer);

	Customer updateCustomer(Long id, Customer customer);

	Customer updateVetForCustomer(Long vetId,Long customerId, Customer customer);

	void deleteCustomerById(Long id);

}