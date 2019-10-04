package com.petshop.service;

import java.util.List;

import com.petshop.dto.AuthorityDTO;
import com.petshop.models.authority.Role;

public interface AuthorityService {

	AuthorityDTO saveAuthority(AuthorityDTO authorityDTO);

	List<AuthorityDTO> getAllRoles();

	void changeRoleOfVet(Long vetId, Role oldAuthority, Role newAuthority);
	
	void deleteRoleForVet(Long vetId, Role role);
}
