package com.petshop.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.petshop.dao.AuthorityDao;
import com.petshop.models.authority.Authority;
import com.petshop.models.authority.Role;
import com.petshop.repository.RoleRepository;
import com.petshop.repository.VetRepository;

@Repository
public class AuthorityDaoImpl implements AuthorityDao {

	@Autowired
	RoleRepository rolesRepository;
	@Autowired
	VetRepository vetRepository;

	@Override
	public Authority saveAuthority(Authority authority) {
		return rolesRepository.save(authority);
	}

	public List<Authority> getAllRoles() {
		return rolesRepository.findAll();
	}

	@Override
	public Authority findByRole(Role role) {
		return rolesRepository.findByRole(role);
	}

}
