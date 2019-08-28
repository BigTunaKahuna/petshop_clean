package com.petshop.dao;

import java.util.List;

import com.petshop.models.Customer;

public interface CustomerDao {

	Customer getCustomerById(Long id);

	List<Customer> getAllCustomers();

	Customer saveCustomer(Long vetId, Customer customer);

	Customer updateCustomer(Long vetId, Customer customer);

	Customer updateVetCustomer(Long vetId, Long custId, Customer customer);

	void deleteCustomerById(Long id);
}
