package com.petshop.service;

import java.util.List;

import com.petshop.dto.AuthorityDTO;
import com.petshop.models.authority.Role;

public interface AuthorityService {

	AuthorityDTO saveAuthority(AuthorityDTO authorityDTO);

	List<AuthorityDTO> getAllRoles();

	void changeRoleOfVet(Long vetId, Role oldAuthority, Role newAuthority);
	
	void deleteRoleForVet(Long vetId, Role role);

	void changeRoleOfCustomer(Long customerId, Role oldAuthority, Role newAuthority);

	void deleteRoleForCustomer(Long customerId, Role role);

	void addRoleForVet(Long vetId, Role role);

	void addRoleForCustomer(Long customerId, Role role);
}
