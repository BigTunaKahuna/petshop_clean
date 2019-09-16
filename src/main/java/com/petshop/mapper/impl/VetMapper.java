package com.petshop.mapper.impl;

import com.petshop.dto.VetDTO;
import com.petshop.mapper.Mapper;
import com.petshop.models.Customer;
import com.petshop.models.Vet;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class VetMapper implements Mapper<Vet, VetDTO> {

	Logger logger = LoggerFactory.getLogger(VetMapper.class);

	@Override
	public VetDTO mapEntityToDto(Vet vetSource) {
		VetDTO vetDTO = new VetDTO();
		var checkExistenceOfCustomers = vetSource.getCustomers();
		List<Customer> emptyArray = new ArrayList<>();

		if (checkExistenceOfCustomers.size() != emptyArray.size()) {
			vetDTO.setCustomers(vetSource.getCustomers());
			logger.info("Exista un customer");
		} else {
			logger.info("Nu exista niciun customer");
			vetDTO.setCustomers(emptyArray);
		}

		if (vetSource.getId() != null) {
			vetDTO.setId(vetSource.getId());
		}
		if (vetSource.getAge() != null) {
			vetDTO.setAge(vetSource.getAge());
		}
		if (vetSource.getEmail() != null) {
			vetDTO.setEmail(vetSource.getEmail());
		}
		if (vetSource.getName() != null) {
			vetDTO.setName(vetSource.getName());
		}
		if (vetSource.getYearsOfExperience() != null) {
			vetDTO.setYearsOfExperience(vetSource.getYearsOfExperience());
		}

		return vetDTO;
	}

	@Override
	public Vet mapDtoToEntity(VetDTO vetDTO) {
		Vet vet = new Vet();
		if (vetDTO != null) {
			if (vetDTO.getId() != null) {
				vet.setId(vetDTO.getId());
			}

			if (vetDTO.getAge() != null) {
				vet.setAge(vetDTO.getAge());
			}

			if (vetDTO.getEmail() != null) {
				vet.setEmail(vetDTO.getEmail());
			}

			if (vetDTO.getName() != null) {
				vet.setName(vetDTO.getName());
			}

			if (vetDTO.getYearsOfExperience() != null) {
				vet.setYearsOfExperience(vetDTO.getYearsOfExperience());
			}
		}

		return vet;
	}

}
