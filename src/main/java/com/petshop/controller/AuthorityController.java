package com.petshop.controller;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.petshop.dao.AuthorityDao;
import com.petshop.dto.AuthorityDTO;
import com.petshop.dto.VetDTO;
import com.petshop.dto.VetWithRolesDTO;
import com.petshop.exception.IdNotFoundException;
import com.petshop.exception.RoleNotFoundException;
import com.petshop.models.Vet;
import com.petshop.models.authority.Authority;
import com.petshop.models.authority.Role;
import com.petshop.repository.RoleRepository;
import com.petshop.repository.VetRepository;
import com.petshop.service.VetService;
import com.petshop.service.impl.AuthorityServiceImpl;

@RestController
@RequestMapping("/role")
public class AuthorityController {

	@Autowired
	AuthorityServiceImpl authorityService;
	@Autowired
	AuthorityDao authorityDao;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	VetRepository vetRepository;

	private static final Logger log = LoggerFactory.getLogger(AuthorityController.class);

	@PostMapping("")
	public ResponseEntity<AuthorityDTO> addRole(@RequestBody AuthorityDTO authorityDTO) {
		return new ResponseEntity<>(authorityService.saveAuthority(authorityDTO), HttpStatus.CREATED);
	}

	@GetMapping("/all")
	public ResponseEntity<List<AuthorityDTO>> getAllRoles() {
		return new ResponseEntity<>(authorityService.getAllRoles(), HttpStatus.OK);
	}

	@GetMapping("/foo")
	public void getSomething() {
		var foo = roleRepository.findAll();
	}

	@GetMapping("/foo2")
	public void deleteVetRole() {
//		Authority auth = new Authority();
//		auth.setRoles(Role.ADMIN);
		Authority auth = roleRepository.findById(1L).orElseThrow();
		Vet vet = vetRepository.findById(1L).orElseThrow();
		vet.removeRole(auth);
		vetRepository.save(vet);
	}

	@PutMapping("/change/{vetId}/{oldAuthority}/{newAuthority}")
	public ResponseEntity<String> changeRole(@PathVariable(name = "vetId") Long vetId,
			@PathVariable(name = "oldAuthority") Role oldAuthority,
			@PathVariable(name = "newAuthority") Role newAuthority) {
		try {
			authorityService.changeRoleOfVet(vetId, oldAuthority, newAuthority);
			return new ResponseEntity<>("Role was successfully changed", HttpStatus.CREATED);			
		} catch (RoleNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
		}
	}

}
