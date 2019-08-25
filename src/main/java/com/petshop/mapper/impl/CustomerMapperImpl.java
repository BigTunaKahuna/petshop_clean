package com.petshop.mapper.impl;

import com.petshop.dto.CustomerDTO;
import com.petshop.mapper.CustomerMapper;
import com.petshop.models.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapperImpl implements CustomerMapper {
    @Override
    public CustomerDTO map(Customer customerSource) {
        CustomerDTO customerDTO = new CustomerDTO();

        if (customerSource != null) {
            if (customerSource.getCustomerId() != null) {
                customerDTO.setCustomerId(customerSource.getCustomerId());
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
                customerDTO.setVet(customerSource.getVet());
            }

        }

        return customerDTO;
    }
}
