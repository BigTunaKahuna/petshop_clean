package com.petshop.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.petshop.dao.impl.AuthorityDaoImpl;
import com.petshop.dto.AuthorityDTO;
import com.petshop.dto.VetWithRolesDTO;
import com.petshop.exception.RoleNotFoundException;
import com.petshop.mapper.impl.AuthorityMapper;
import com.petshop.mapper.impl.VetWithRolesMapper;
import com.petshop.models.authority.Authority;
import com.petshop.models.authority.Role;
import com.petshop.repository.RoleRepository;
import com.petshop.repository.VetRepository;
import com.petshop.service.AuthorityService;
import com.petshop.service.VetService;

@Service
public class AuthorityServiceImpl implements AuthorityService {
	@Autowired
	private AuthorityDaoImpl authorityDao;
	@Autowired
	private AuthorityMapper authorityMapper;
	@Autowired
	private VetWithRolesMapper vetWithRolesMapper;
	@Autowired
	private VetService vetService;
	@Autowired
	private VetRepository vetRepository;
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public AuthorityDTO saveAuthority(AuthorityDTO authorityDTO) {
		Authority authority = authorityMapper.mapDtoToEntity(authorityDTO);

		return authorityMapper.mapEntityToDto(authorityDao.saveAuthority(authority));
	}

	public List<AuthorityDTO> getAllRoles() {
		List<Authority> authority = authorityDao.getAllRoles();
		List<AuthorityDTO> authorityDTOs = new ArrayList<>();
		for (Authority authorityHolder : authority) {
			authorityDTOs.add(authorityMapper.mapEntityToDto(authorityHolder));
		}
		return authorityDTOs;
	}

	// After the role has changed to ADMIN you need to delete cookies to don't get
	// forbidden
	public void changeRoleOfVet(Long vetId, Role oldAuthority, Role newAuthority) {
		VetWithRolesDTO vet = vetService.findVetById(vetId);
		Authority newAuth = roleRepository.findByRole(newAuthority);
		if (newAuth != null) {
			Authority oldAuth = vet.getRoles().stream().filter(role -> role.getRole().equals(oldAuthority)).findAny()
					.orElseThrow();
			vet.removeRole(oldAuth);
			vet.addRole(newAuth);
			vetRepository.saveAndFlush(vetWithRolesMapper.mapDtoToEntity(vet));
		} else throw new RoleNotFoundException();
	}
}
