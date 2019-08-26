package com.petshop.mapper;

import com.petshop.dto.CustomerDTO;
import com.petshop.models.Customer;

public interface CustomerMapper {
	CustomerDTO mapEntityToDto(Customer customer);
	Customer mapDtoToEntity(CustomerDTO customerDTO);
}
