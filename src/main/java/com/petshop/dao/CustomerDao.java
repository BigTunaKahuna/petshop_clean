package com.petshop.dao;

import java.util.List;

import com.petshop.models.Customer;

public interface CustomerDao {

	Customer getCustomerById(Long id);

	List<Customer> getAllCustomers();

	Customer saveCustomer(Customer customer);

	Customer saveCustomerAndFlush(Customer customer);

	Customer saveCustomer(Long vetId, Customer customer);

	Customer updateCustomer(Long vetId, Customer customer);

	Customer updateVetCustomer(Long vetId, Long custId, Customer customer);

	void deleteCustomerById(Long id);

	Boolean checkEmail(String email);

	Customer findByEmail(String email);
}
