package com.petshop.dto;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.petshop.models.Vet;
import com.petshop.models.authority.Authority;

public class CustomerWithRolesDTO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty(message = "Please enter a name")
	private String name;

	@Email(message = "Email format is not valid")
	@NotEmpty(message = "Please enter an email")
	private String email;

	@NotEmpty(message = "Please enter a password")
	private String password;

	@NotEmpty(message = "Please enter a phone number")
	@Size(min = 10, max = 10, message = "Phone size must be 10")
	private String phone;

	@NotEmpty(message = "Please enter a pet species")
	private String petSpecies;

	@NotEmpty(message = "Please enter a pet name")
	private String petName;

	private Set<Authority> roles = new HashSet<>();
	private Vet vet;

	public CustomerWithRolesDTO() {
	}

	public CustomerWithRolesDTO(Long id, @NotEmpty(message = "Please enter a name") String name,
			@Email(message = "Email format is not valid") @NotEmpty(message = "Please enter an email") String email,
			@NotEmpty(message = "Please enter a password") String password,
			@NotEmpty(message = "Please enter a phone number") @Size(min = 10, max = 10, message = "Phone size must be 10") String phone,
			@NotEmpty(message = "Please enter a pet species") String petSpecies,
			@NotEmpty(message = "Please enter a pet name") String petName, Set<Authority> roles, Vet vet) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.phone = phone;
		this.petSpecies = petSpecies;
		this.petName = petName;
		this.roles = roles;
		this.vet = vet;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPetSpecies() {
		return petSpecies;
	}

	public void setPetSpecies(String petSpecies) {
		this.petSpecies = petSpecies;
	}

	public String getPetName() {
		return petName;
	}

	public void setPetName(String petName) {
		this.petName = petName;
	}

	public Set<Authority> getRoles() {
		return roles;
	}

	public void setRoles(Set<Authority> roles) {
		this.roles = roles;
	}

	public void addRole(Authority auth) {
		roles.add(auth);
	}

	public void removeRole(Authority auth) {
		roles.remove(auth);
	}
	
	public Vet getVet() {
		return vet;
	}

	public void setVet(Vet vet) {
		this.vet = vet;
	}
}
