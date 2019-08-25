package com.petshop.mapper.impl;

import com.petshop.dto.VetDTO;
import com.petshop.mapper.VetMapper;
import com.petshop.models.Vet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class VetMapperImpl implements VetMapper {

	Logger logger = LoggerFactory.getLogger(VetMapperImpl.class);

	@Override
	public VetDTO map(Vet vetSource) {
		VetDTO vetDTO = new VetDTO();
		logger.info(vetSource.toString());
		var checkExistenceOfCustomers = vetSource.getCustomers();

		if (vetSource != null) {
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
		}

		if (checkExistenceOfCustomers != null) {
			vetDTO.setCustomers(vetSource.getCustomers());
			logger.info("Exista un customer");
		} else {
			logger.info("Nu exista niciun customer");
			vetDTO.setCustomers(null);
		}

		return vetDTO;
	}

}
