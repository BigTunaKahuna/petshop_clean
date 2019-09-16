package com.petshop.service.impl;

import com.petshop.dao.CustomerDao;
import com.petshop.dto.CustomerDTO;
import com.petshop.exception.IdNotFoundException;
import com.petshop.mapper.impl.CustomerMapper;
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
		return customerMapper.mapEntityToDto(customer);
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
		return customerMapper.mapEntityToDto(customerDao.saveCustomer(vetId, customer));
	}

	@Override
	public CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO) {
		Customer customer = customerMapper.mapDtoToEntity(customerDTO);
		return customerMapper.mapEntityToDto(customerDao.updateCustomer(id, customer));
	}

	@Override
	public CustomerDTO updateVetForCustomer(Long newVetId, Long customerId, CustomerDTO customerDTO) {
		Customer customer = customerMapper.mapDtoToEntity(customerDTO);
		return customerMapper.mapEntityToDto(customerDao.updateVetCustomer(newVetId, customerId, customer));
	}

	@Override
	public void deleteCustomerById(Long id) {
		try {
			customerDao.deleteCustomerById(id);
		} catch (IdNotFoundException e) {
			throw new IdNotFoundException();
		}
	}
}
