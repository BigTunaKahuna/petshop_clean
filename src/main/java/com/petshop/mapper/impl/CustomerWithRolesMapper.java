package com.petshop.mapper.impl;

import org.springframework.stereotype.Component;

import com.petshop.dto.CustomerWithRolesDTO;
import com.petshop.mapper.Mapper;
import com.petshop.models.Customer;

@Component
public class CustomerWithRolesMapper implements Mapper<Customer, CustomerWithRolesDTO> {

	@Override
	public CustomerWithRolesDTO mapEntityToDto(Customer entity) {
		CustomerWithRolesDTO customerWithRolesDTO = new CustomerWithRolesDTO();
		if (entity.getId() != null)
			customerWithRolesDTO.setId(entity.getId());
		if (entity.getName() != null)
			customerWithRolesDTO.setName(entity.getName());
		if (entity.getEmail() != null)
			customerWithRolesDTO.setEmail(entity.getEmail());
		if (entity.getPassword() != null)
			customerWithRolesDTO.setPassword(entity.getPassword());
		if (entity.getPhone() != null)
			customerWithRolesDTO.setPhone(entity.getPhone());
		if (entity.getPetSpecies() != null)
			customerWithRolesDTO.setPetSpecies(entity.getPetSpecies());
		if (entity.getPetName() != null)
			customerWithRolesDTO.setPetName(entity.getPetName());
		if (entity.getRole() != null)
			customerWithRolesDTO.setRoles(entity.getRole());

		return customerWithRolesDTO;
	}

	@Override
	public Customer mapDtoToEntity(CustomerWithRolesDTO dto) {
		Customer customer = new Customer();
		if (dto.getId() != null)
			customer.setId(dto.getId());
		if (dto.getName() != null)
			customer.setName(dto.getName());
		if (dto.getEmail() != null)
			customer.setEmail(dto.getEmail());
		if (dto.getPassword() != null)
			customer.setPassword(dto.getPassword());
		if (dto.getPhone() != null)
			customer.setPhone(dto.getPhone());
		if (dto.getPetSpecies() != null)
			customer.setPetSpecies(dto.getPetSpecies());
		if (dto.getPetName() != null)
			customer.setPetName(dto.getPetName());
		if (dto.getRoles() != null)
			customer.setRole(dto.getRoles());

		return customer;
	}

}
