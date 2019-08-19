package com.petshop.service.service_impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.petshop.dao.CustomerDao;
import com.petshop.models.Customer;
import com.petshop.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {
	@Autowired
	CustomerDao customerDao;

	@Override
	public Customer getCustomerById(Long id) {
		return customerDao.getCustomerById(id);
	}

	@Override
	public List<Customer> getAllCustomers() {
		return customerDao.getAllCustomers();
	}

	@Override
	public Customer saveCustomer(Long vetId, Customer customer) {
		return customerDao.saveCustomer(vetId, customer);
	}

	@Override
	public Customer updateCustomer(Long id, Customer customer) {
		return customerDao.updateCustomer(id, customer);
	}

	@Override
	public Customer updateVetForCustomer(Long newVetId, Long customerId, Customer customer) {
		return customerDao.updateVetCustomer(newVetId, customerId, customer);
	}

	@Override
	public void deleteCustomer(Long id) {
		customerDao.deleteCustomerById(id);
	}
}
