package com.petshop.mapper;

import com.petshop.dto.VetDTO;
import com.petshop.models.Vet;

public interface VetMapper {
	VetDTO mapEntityToDto(Vet vet);
	Vet mapDtoToEntity(VetDTO vetDTO);
}
