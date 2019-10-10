package com.petshop.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.petshop.dao.AuthorityDao;
import com.petshop.dao.CustomerDao;
import com.petshop.dao.VetDao;
import com.petshop.dto.AuthorityDTO;
import com.petshop.dto.CustomerWithRolesDTO;
import com.petshop.dto.VetWithRolesDTO;
import com.petshop.exception.RoleAlreadyExistsException;
import com.petshop.exception.RoleNotFoundException;
import com.petshop.mapper.impl.AuthorityMapper;
import com.petshop.mapper.impl.CustomerWithRolesMapper;
import com.petshop.mapper.impl.VetWithRolesMapper;
import com.petshop.models.Customer;
import com.petshop.models.Vet;
import com.petshop.models.authority.Authority;
import com.petshop.models.authority.Role;
import com.petshop.repository.CustomerRepository;
import com.petshop.service.AuthorityService;
import com.petshop.service.CustomerService;
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
	private CustomerWithRolesMapper customerWithRolesMapper;
	@Autowired
	private VetService vetService;
	@Autowired
	private VetDao vetDao;
	@Autowired
	private CustomerDao customerDao;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private CustomerRepository customerRepository;

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
			throw new RoleAlreadyExistsException();
		}

	}

	// After the role has changed to ADMIN you need to delete cookies to don't get
	// forbidden or to reset privileges
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
	public void changeRoleOfCustomer(Long customerId, Role oldAuthority, Role newAuthority) {
		CustomerWithRolesDTO customer = customerService.getCustomerWithRolesById(customerId);
		Authority newAuth = authorityDao.findByRole(newAuthority);
		if (newAuth != null) {
			Authority oldAuth = customer.getRoles().stream().filter(role -> role.getRole().equals(oldAuthority))
					.findAny().orElseThrow(RoleNotFoundException::new);
			customer.removeRole(oldAuth);
			customer.addRole(newAuth);
			customerDao.saveCustomerAndFlush(customerWithRolesMapper.mapDtoToEntity(customer));
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

	@Override
	public void deleteRoleForCustomer(Long customerId, Role role) {
		Authority auth = authorityDao.findByRole(role);
		Customer customer = customerDao.getCustomerById(customerId);
		if (auth != null && customer.getRole().contains(auth)) {
			customer.removeRole(auth);
			customerDao.saveCustomerAndFlush(customer);
		} else
			throw new RoleNotFoundException();
	}

}