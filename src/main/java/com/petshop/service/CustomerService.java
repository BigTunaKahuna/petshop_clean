package com.petshop.service;

import java.util.List;

import com.petshop.models.Customer;

public interface CustomerService {

	Customer getCustomerById(Long id);

	List<Customer> getAllCustomers();

	Customer saveCustomer(Long vetId, Customer customer);

	Customer updateCustomer(Long id, Customer customer);

	Customer updateVetForCustomer(Long vetId,Long customerId, Customer customer);

	void deleteCustomer(Long id);

}