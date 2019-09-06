package com.petshop.service.impl;

import com.petshop.dao.CustomerDao;
import com.petshop.dto.CustomerDTO;
import com.petshop.http_errors.IdNotFoundException;
import com.petshop.mapper.CustomerMapper;
import com.petshop.models.Customer;
import com.petshop.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
	@Autowired
	private CustomerDao customerDao;

	@Autowired
	private CustomerMapper customerMapper;

	@Override
	public CustomerDTO getCustomerById(Long id) {
		Customer customer = customerDao.getCustomerById(id);
		CustomerDTO customerDTO = customerMapper.mapEntityToDto(customer);
		return customerDTO;
	}

	@Override
	public List<CustomerDTO> getAllCustomers() {
		List<Customer> customers = customerDao.getAllCustomers();
		List<CustomerDTO> customerDTO = new ArrayList<>();
		for (var customer : customers) {
			customerDTO.add(customerMapper.mapEntityToDto(customer));
		}
		return customerDTO;
	}

	@Override
	public CustomerDTO saveCustomer(Long vetId, CustomerDTO customerDTO) {
		Customer customer = customerMapper.mapDtoToEntity(customerDTO);
		CustomerDTO customerDtoHolder = customerMapper.mapEntityToDto(customerDao.saveCustomer(vetId, customer));
		return customerDtoHolder;
	}

	@Override
	public CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO) {
		Customer customer = customerMapper.mapDtoToEntity(customerDTO);
		CustomerDTO customerDtoHolder = customerMapper.mapEntityToDto(customerDao.updateCustomer(id, customer));
		return customerDtoHolder;
	}

	@Override
	public CustomerDTO updateVetForCustomer(Long newVetId, Long customerId, CustomerDTO customerDTO) {
		Customer customer = customerMapper.mapDtoToEntity(customerDTO);
		CustomerDTO customerDtoHolder = customerMapper
				.mapEntityToDto(customerDao.updateVetCustomer(newVetId, customerId, customer));
		return customerDtoHolder;
	}

	@Override
	public void deleteCustomerById(Long id) {
		try {
				customerDao.deleteCustomerById(id);
		} catch (IdNotFoundException e) {
			throw e;
		}
	}
}
