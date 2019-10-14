package com.petshop.service.impl;

import com.petshop.dao.AuthorityDao;
import com.petshop.dao.CustomerDao;
import com.petshop.dao.VetDao;
import com.petshop.dto.CustomerDTO;
import com.petshop.dto.CustomerWithRolesDTO;
import com.petshop.exception.EmailAlreadyExistsException;
import com.petshop.exception.IdNotFoundException;
import com.petshop.exception.RoleNotFoundException;
import com.petshop.mapper.impl.CustomerMapper;
import com.petshop.mapper.impl.CustomerWithRolesMapper;
import com.petshop.models.Customer;
import com.petshop.models.Vet;
import com.petshop.models.authority.Authority;
import com.petshop.models.authority.Role;
import com.petshop.service.CustomerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Service
public class CustomerServiceImpl implements CustomerService {
	@Autowired
	private AuthorityDao authorityDao;
	@Autowired
	private CustomerDao customerDao;
	@Autowired
	private CustomerMapper customerMapper;
	@Autowired
	private BCryptPasswordEncoder bcrypt;
	@Autowired
	private CustomerWithRolesMapper customerWithRolesMapper;
	@Autowired
	private VetDao vetDao;
	@PersistenceContext
	EntityManager em;

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
		Authority auth = authorityDao.findByRole(Role.USER);
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
	@Transactional
	public CustomerWithRolesDTO updateVetForCustomer(Long newVetId, Long customerId) {
		Vet newVet = vetDao.getVetById(newVetId);
		Customer customer = customerDao.getCustomerById(customerId);
		if (customerId != null && newVet != null) {

			newVet.addCustomer(customer);
			customer.setVet(newVet);
			customerDao.deleteCustomerById(customerId);
			em.flush();
			return customerWithRolesMapper.mapEntityToDto(customerDao.saveCustomer(customer));
		} else
			throw new IdNotFoundException();
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
