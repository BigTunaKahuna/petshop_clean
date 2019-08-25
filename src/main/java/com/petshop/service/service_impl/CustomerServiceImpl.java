package com.petshop.service.service_impl;

import com.petshop.dao.CustomerDao;
import com.petshop.dto.CustomerDTO;
import com.petshop.mapper.CustomerMapper;
import com.petshop.models.Customer;
import com.petshop.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
	@Autowired
	CustomerDao customerDao;

	@Autowired
	CustomerMapper customerMapper;

	@Override
	public CustomerDTO getCustomerById(Long id) {
		Customer customer = customerDao.getCustomerById(id);
		CustomerDTO customerDTO = customerMapper.map(customer);
		return customerDTO;
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
	public void deleteCustomerById(Long id) {
		customerDao.deleteCustomerById(id);
	}
}
