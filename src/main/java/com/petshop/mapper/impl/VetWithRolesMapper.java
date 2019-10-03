package com.petshop.mapper.impl;

import org.springframework.stereotype.Component;

import com.petshop.dto.VetWithRolesDTO;
import com.petshop.mapper.Mapper;
import com.petshop.models.Vet;

@Component
public class VetWithRolesMapper implements Mapper<Vet, VetWithRolesDTO> {

	@Override
	public VetWithRolesDTO mapEntityToDto(Vet entity) {
		VetWithRolesDTO vetWithRolesDTO = new VetWithRolesDTO();
		if (entity.getId() != null)
			vetWithRolesDTO.setId(entity.getId());
		if (entity.getName() != null)
			vetWithRolesDTO.setName(entity.getName());
		if (entity.getEmail() != null)
			vetWithRolesDTO.setEmail(entity.getEmail());
		if (entity.getPassword() != null)
			vetWithRolesDTO.setPassword(entity.getPassword());
		if (entity.getYearsOfExperience() != null)
			vetWithRolesDTO.setYearsOfExperience(entity.getYearsOfExperience());
		if (entity.getAge() != null)
			vetWithRolesDTO.setAge(entity.getAge());
		if (entity.getRoles() != null)
			vetWithRolesDTO.setRoles(entity.getRoles());

		return vetWithRolesDTO;
	}

	@Override
	public Vet mapDtoToEntity(VetWithRolesDTO dto) {
		Vet vet = new Vet();
		if (dto.getId() != null)
			vet.setId(dto.getId());
		if (dto.getName() != null)
			vet.setName(dto.getName());
		if (dto.getEmail() != null)
			vet.setEmail(dto.getEmail());
		if (dto.getPassword() != null)
			vet.setPassword(dto.getPassword());
		if (dto.getYearsOfExperience() != null)
			vet.setYearsOfExperience(dto.getYearsOfExperience());
		if (dto.getAge() != null)
			vet.setAge(dto.getAge());
		if (dto.getRoles() != null)
			vet.setRoles(dto.getRoles());

		return vet;
	}

}
