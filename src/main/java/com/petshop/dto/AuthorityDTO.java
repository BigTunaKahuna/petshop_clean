package com.petshop.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.petshop.models.authority.Role;

public class AuthorityDTO {
	private Long id;

	@NotNull(message = "Invalid role request")
	private Role role;

	public AuthorityDTO() {
	}

	public AuthorityDTO(Long id, @NotEmpty(message = "Role can't be empty!") Role role) {
		super();
		this.id = id;
		this.role = role;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
}
