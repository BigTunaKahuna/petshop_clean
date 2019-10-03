package com.petshop.mapper.impl;

import org.springframework.stereotype.Component;

import com.petshop.dto.AuthorityDTO;
import com.petshop.mapper.Mapper;
import com.petshop.models.authority.Authority;

@Component
public class AuthorityMapper implements Mapper<Authority, AuthorityDTO> {

	@Override
	public AuthorityDTO mapEntityToDto(Authority entity) {
		AuthorityDTO authorityDTO = new AuthorityDTO();

		if (entity.getId() != null)
			authorityDTO.setId(entity.getId());
		if (entity.getRole() != null)
			authorityDTO.setRole(entity.getRole());
		return authorityDTO;
	}

	@Override
	public Authority mapDtoToEntity(AuthorityDTO dto) {
		Authority authority = new Authority();

		if (dto.getId() != null)
			authority.setId(dto.getId());
		if (dto.getRole() != null)
			authority.setRoles(dto.getRole());

		return authority;
	}

}
