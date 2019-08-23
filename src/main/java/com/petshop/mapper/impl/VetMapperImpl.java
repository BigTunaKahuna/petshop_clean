package com.petshop.mapper.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.petshop.dto.VetDTO;
import com.petshop.mapper.VetMapper;
import com.petshop.models.Vet;

@Component
public class VetMapperImpl implements VetMapper {

	Logger logger = LoggerFactory.getLogger(VetMapperImpl.class);

	@Override
	public VetDTO map(Vet vetSource) {
		VetDTO vetDTO = new VetDTO();
		Vet vet = vetSource;
		Integer age = vet.getAge();
		logger.info(vet.toString());
		var checkExistenceOfCustomers = vet.getCustomers();

		if (vet != null) {
			if (vet.getId() != null) {
				vetDTO.setId(vet.getId());
			}
			if (age != null) {
				vetDTO.setAge(age);
			}
			if (vet.getEmail() != null) {
				vetDTO.setEmail(vet.getEmail());
			}
			if (vet.getName() != null) {
				vetDTO.setName(vet.getName());
			}
			if (vet.getYearsOfExperience() != null) {
				vetDTO.setYearsOfExperience(vet.getYearsOfExperience());
			}
		}

		if (checkExistenceOfCustomers != null) {
			vetDTO.setCustomers(vet.getCustomers());
			logger.info("Exista un customer");
		} else {
			logger.info("Nu exista niciun customer");
			vetDTO.setCustomers(null);
		}

		return vetDTO;
	}

}
