package com.petshop.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.petshop.dao.CustomerDao;
import com.petshop.exception.IdNotFoundException;
import com.petshop.models.Customer;
import com.petshop.models.Vet;
import com.petshop.repository.CustomerRepository;
import com.petshop.repository.VetRepository;

@Repository
public class CustomerDaoImpl implements CustomerDao {
	Logger logger = LoggerFactory.getLogger(CustomerDaoImpl.class);

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private VetRepository vetRepository;

	@Override
	public Customer getCustomerById(Long id) {
		return customerRepository.findById(id).orElseThrow(IdNotFoundException::new);
	}

	@Override
	public List<Customer> getAllCustomers() {
		return customerRepository.findAll();
	}

	@Override
	public Customer saveCustomer(Customer customer) {
		return customerRepository.save(customer);
	}

	@Override
	public Customer saveCustomer(Long vetId, Customer customer) {
		Vet vet = vetRepository.findById(vetId).orElseThrow(IdNotFoundException::new);
		vet.addCustomer(customer);
		customer.setVet(vet);
		return customerRepository.save(customer);
	}

	@Override
	public void saveCustomerAndFlush(Customer customer) {
		customerRepository.saveAndFlush(customer);
	}

	@Override
	public Customer updateCustomer(Long id, Customer customer) {
		return customerRepository.findById(id).map(customerReq -> {
			customerReq.setName(customer.getName());
			customerReq.setEmail(customer.getEmail());
			customerReq.setPassword(customer.getPassword());
			customerReq.setPetName(customer.getPetName());
			customerReq.setPetSpecies(customer.getPetSpecies());
			customerReq.setPhone(customer.getPhone());
			return customerRepository.save(customerReq);
		}).orElseThrow(IdNotFoundException::new);
	}

	@Override
	public void deleteCustomerById(Long id) {
		try {
			customerRepository.deleteById(id);
		} catch (IdNotFoundException e) {
			throw new IdNotFoundException();
		}
	}

	@Override
	public Boolean checkEmail(String email) {
		Boolean exists = false;
		if (customerRepository.findByEmail(email) != null) {
			exists = true;
			return exists;
		}

		return exists;
	}

	@Override
	public Customer findByEmail(String email) {
		return customerRepository.findByEmail(email);
	}

}
