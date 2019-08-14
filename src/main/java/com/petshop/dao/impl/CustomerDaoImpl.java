package com.petshop.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.petshop.dao.CustomerDao;
import com.petshop.http_errors.IdNotFoundException;
import com.petshop.models.Customer;
import com.petshop.models.Vet;
import com.petshop.repository.CustomerRepository;
import com.petshop.repository.VetRepository;

@Repository
public class CustomerDaoImpl implements CustomerDao {

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	VetRepository vetRepository;

	@Override
	public Customer getCustomerById(Long id) {
		return customerRepository.findById(id).orElseThrow(() -> new IdNotFoundException());
	}

	@Override
	public List<Customer> getAllCustomers() {
		return customerRepository.findAll();
	}

	@Override
	public Customer saveCustomer(Long vetId, Customer customer) {
		Vet vet = vetRepository.findById(vetId).orElseThrow(() -> new IdNotFoundException());
		vet.addCustomer(customer);
		customer.setVet(vet);
		return customerRepository.save(customer);
	}

	@Override
	public Customer updateCustomer(Long id, Customer customer) {
		return customerRepository.findById(id).map(customerReq -> {
			customerReq.setName(customer.getName());
			customerReq.setPetName(customer.getPetName());
			customerReq.setPetSpecies(customer.getPetSpecies());
			customerReq.setPhone(customer.getPhone());
			return customerRepository.save(customerReq);
		}).orElseThrow(() -> new IdNotFoundException());
	}

	@Override
	public Customer updateVetCustomer(Long vetId, Long custId, Customer customer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteCustomerById(Long id) {
		try {
			if(customerRepository.findById(id)!=null) {
				customerRepository.deleteById(id);
			}
		} catch (Exception e) {
			throw new IdNotFoundException();
		}

	}

}
