package com.petshop.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.petshop.dto.AuthorityDTO;
import com.petshop.models.authority.Role;
import com.petshop.service.AuthorityService;

@RestController
@RequestMapping("/role")
public class AuthorityController {

	@Autowired
	private AuthorityService authorityService;

	@PostMapping("")
	public ResponseEntity<AuthorityDTO> addRole(@Valid @RequestBody AuthorityDTO authorityDTO) {
		return new ResponseEntity<>(authorityService.saveAuthority(authorityDTO), HttpStatus.CREATED);
	}

	@GetMapping("/all")
	public ResponseEntity<List<AuthorityDTO>> getAllRoles() {
		return new ResponseEntity<>(authorityService.getAllRoles(), HttpStatus.OK);
	}

	@PutMapping("/change/vet/{vetId}/{oldAuthority}/{newAuthority}")
	public ResponseEntity<String> changeVetRole(@PathVariable(name = "vetId") Long vetId,
			@PathVariable(name = "oldAuthority") Role oldAuthority,
			@PathVariable(name = "newAuthority") Role newAuthority) {
		authorityService.changeRoleOfVet(vetId, oldAuthority, newAuthority);
		return new ResponseEntity<>("Role was successfully changed", HttpStatus.OK);
	}

	@PutMapping("/change/customer/{customerId}/{oldAuthority}/{newAuthority}")
	public ResponseEntity<String> changeCustomerRole(@PathVariable(name = "customerId") Long customerId,
			@PathVariable(name = "oldAuthority") Role oldAuthority,
			@PathVariable(name = "newAuthority") Role newAuthority) {
		authorityService.changeRoleOfCustomer(customerId, oldAuthority, newAuthority);
		return new ResponseEntity<>("Role was successfully changed", HttpStatus.OK);
	}

	@DeleteMapping("/vet/{id}/{role}")
	public ResponseEntity<String> deletRoleForVet(@PathVariable(name = "id") Long vetId,
			@PathVariable(name = "role") Role role) {
		authorityService.deleteRoleForVet(vetId, role);
		return new ResponseEntity<>("Role was successfully deleted", HttpStatus.OK);
	}

	@DeleteMapping("/customer/{id}/{role}")
	public ResponseEntity<String> deleteRoleForCustomer(@PathVariable(name = "id") Long customerId,
			@PathVariable(name = "role") Role role) {
		authorityService.deleteRoleForCustomer(customerId, role);
		return new ResponseEntity<>("Role was successfully deleted", HttpStatus.OK);
	}

}
