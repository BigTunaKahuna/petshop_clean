package com.petshop.mapper.impl;

import com.petshop.dto.CustomerDTO;
import com.petshop.mapper.Mapper;
import com.petshop.models.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper implements Mapper<Customer, CustomerDTO> {

	@Override
	public CustomerDTO mapEntityToDto(Customer customerSource) {
		CustomerDTO customerDTO = new CustomerDTO();

		if (customerSource != null) {
			if (customerSource.getId() != null) {
				customerDTO.setId(customerSource.getId());
			}

			if (customerSource.getName() != null) {
				customerDTO.setName(customerSource.getName());
			}

			if (customerSource.getPetName() != null) {
				customerDTO.setPetName(customerSource.getPetName());
			}

			if (customerSource.getPhone() != null) {
				customerDTO.setPhone(customerSource.getPhone());
			}

			if (customerSource.getPetSpecies() != null) {
				customerDTO.setPetSpecies(customerSource.getPetSpecies());
			}

			if (customerSource.getVet() != null) {
				customerDTO.setVet(customerSource.getVet().getName());
			}

		}

		return customerDTO;
	}

	@Override
	public Customer mapDtoToEntity(CustomerDTO customerDTO) {
		Customer customer = new Customer();
		if (customerDTO != null) {
			if (customerDTO.getId() != null) {
				customer.setId(customerDTO.getId());
			}

			if (customerDTO.getName() != null) {
				customer.setName(customerDTO.getName());
			}

			if (customerDTO.getPetName() != null) {
				customer.setPetName(customerDTO.getPetName());
			}

			if (customerDTO.getPhone() != null) {
				customer.setPhone(customerDTO.getPhone());
			}

			if (customerDTO.getPetSpecies() != null) {
				customer.setPetSpecies(customerDTO.getPetSpecies());
			}

		}

		return customer;
	}
}
