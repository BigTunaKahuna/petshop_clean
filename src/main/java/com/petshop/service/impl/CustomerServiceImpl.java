package com.petshop.service.impl;

import com.petshop.dao.CustomerDao;
import com.petshop.dto.CustomerDTO;
import com.petshop.dto.CustomerWithRolesDTO;
import com.petshop.exception.EmailAlreadyExistsException;
import com.petshop.exception.IdNotFoundException;
import com.petshop.exception.RoleNotFoundException;
import com.petshop.mapper.impl.CustomerMapper;
import com.petshop.mapper.impl.CustomerWithRolesMapper;
import com.petshop.models.Customer;
import com.petshop.models.authority.Authority;
import com.petshop.models.authority.Role;
import com.petshop.repository.RoleRepository;
import com.petshop.service.CustomerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private CustomerDao customerDao;
	@Autowired
	private CustomerMapper customerMapper;
	@Autowired
	private BCryptPasswordEncoder bcrypt;
	@Autowired
	private CustomerWithRolesMapper customerWithRolesMapper;

	@Override
	public CustomerDTO getCustomerById(Long id) {
		Customer customer = customerDao.getCustomerById(id);
		return customerMapper.mapEntityToDto(customer);
	}

	@Override
	public CustomerWithRolesDTO getCustomerWithRolesById(Long id) {
		Customer customer = customerDao.getCustomerById(id);
		return customerWithRolesMapper.mapEntityToDto(customer);
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
		if (customerDao.checkEmail(customerDTO.getEmail())) {
			throw new EmailAlreadyExistsException();
		}
		Customer customer = customerMapper.mapDtoToEntity(customerDTO);
		Authority auth = roleRepository.findByRole(Role.USER);
		if (auth != null) {
			customer.setPassword(bcrypt.encode(customerDTO.getPassword()));
			customer.addRole(auth);
			return customerMapper.mapEntityToDto(customerDao.saveCustomer(vetId, customer));
		} else
			throw new RoleNotFoundException();
	}

	@Override
	public CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO) {
		customerDTO.setPassword(bcrypt.encode(customerDTO.getPassword()));
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
