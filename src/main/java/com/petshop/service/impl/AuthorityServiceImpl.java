package com.petshop.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.petshop.dao.AuthorityDao;
import com.petshop.dao.VetDao;
import com.petshop.dto.AuthorityDTO;
import com.petshop.dto.VetWithRolesDTO;
import com.petshop.exception.RoleAlreadyExists;
import com.petshop.exception.RoleNotFoundException;
import com.petshop.mapper.impl.AuthorityMapper;
import com.petshop.mapper.impl.VetWithRolesMapper;
import com.petshop.models.Vet;
import com.petshop.models.authority.Authority;
import com.petshop.models.authority.Role;
import com.petshop.service.AuthorityService;
import com.petshop.service.VetService;

@Service
public class AuthorityServiceImpl implements AuthorityService {
	@Autowired
	private AuthorityDao authorityDao;
	@Autowired
	private AuthorityMapper authorityMapper;
	@Autowired
	private VetWithRolesMapper vetWithRolesMapper;
	@Autowired
	private VetService vetService;
	@Autowired
	private VetDao vetDao;

	@Override
	public List<AuthorityDTO> getAllRoles() {
		List<Authority> authority = authorityDao.getAllRoles();
		List<AuthorityDTO> authorityDTOs = new ArrayList<>();
		for (Authority authorityHolder : authority) {
			authorityDTOs.add(authorityMapper.mapEntityToDto(authorityHolder));
		}
		return authorityDTOs;
	}

	@Override
	public AuthorityDTO saveAuthority(AuthorityDTO authorityDTO) {
		try {
			Authority authority = authorityMapper.mapDtoToEntity(authorityDTO);
			return authorityMapper.mapEntityToDto(authorityDao.saveAuthority(authority));			
		} catch (DataIntegrityViolationException e) {
			throw new RoleAlreadyExists();
		}
		
	}

	// After the role has changed to ADMIN you need to delete cookies to don't get
	// forbidden
	@Override
	public void changeRoleOfVet(Long vetId, Role oldAuthority, Role newAuthority) {
		VetWithRolesDTO vet = vetService.findVetById(vetId);
		Authority newAuth = authorityDao.findByRole(newAuthority);
		if (newAuth != null) {
			Authority oldAuth = vet.getRoles().stream().filter(role -> role.getRole().equals(oldAuthority)).findAny()
					.orElseThrow();
			vet.removeRole(oldAuth);
			vet.addRole(newAuth);
			vetDao.saveVetAndFlush(vetWithRolesMapper.mapDtoToEntity(vet));
		} else
			throw new RoleNotFoundException();
	}

	@Override
	public void deleteRoleForVet(Long vetId, Role role) {
		Authority auth = authorityDao.findByRole(role);
		Vet vet = vetDao.getVetById(vetId);
		if (auth != null && vet.getRoles().contains(auth)) {
			vet.removeRole(auth);
			vetDao.saveVetAndFlush(vet);
		} else
			throw new RoleNotFoundException();
	}
}