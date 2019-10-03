package com.petshop.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.petshop.dao.AuthorityDao;
import com.petshop.exception.IdNotFoundException;
import com.petshop.models.Vet;
import com.petshop.models.authority.Authority;
import com.petshop.repository.RoleRepository;
import com.petshop.repository.VetRepository;

@Repository
public class AuthorityDaoImpl implements AuthorityDao {

	@Autowired
	RoleRepository rolesRepository;
	@Autowired
	VetRepository vetRepository;

	private static final Logger log = LoggerFactory.getLogger(AuthorityDaoImpl.class);

	@Override
	public Authority saveAuthority(Authority authority) {
		return rolesRepository.save(authority);
	}

	public List<Authority> getAllRoles() {
		return rolesRepository.findAll();
	}

	public Authority changeVetRole(Authority authority, Long id) {
		Vet vet = vetRepository.findById(id).orElseThrow(IdNotFoundException::new);
		Authority authHolder = rolesRepository.findById(authority.getId()).orElseThrow(IdNotFoundException::new);
		log.info("AuthHolder is {}", authHolder);

		return authHolder;
	}

}
