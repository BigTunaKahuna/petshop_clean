package com.petshop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.petshop.dao.CustomerDao;
import com.petshop.models.Customer;

public class CustomerService {
	@Autowired
	CustomerDao customerDao;

	public Customer getCustomerById(Long id) {
		return customerDao.getCustomerById(id);
	}

	public List<Customer> getAllCustomers() {
		return customerDao.getAllCustomers();
	}

	public Customer saveCustomer(Long vetId, Customer customer) {
		return customerDao.saveCustomer(vetId, customer);
	}

	public Customer updateCustomer(Long id, Customer customer) {
		return customerDao.updateCustomer(id, customer);
	}

	public void deleteCustomer(Long id) {
		customerDao.deleteCustomerById(id);
	}
}
