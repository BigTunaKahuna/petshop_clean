package com.petshop.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.petshop.models.authority.Authority;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "CUSTOMER")
public class Customer implements Serializable {
	private static final long serialVersionUID = -4592973994222019477L;
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

	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "vet_id")
	private Vet vet;

	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.EAGER)
	@JoinTable(name = "customer_role", joinColumns = @JoinColumn(name = "customer_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Authority> role = new HashSet<>();

	public Customer() {
	}

	public Customer(Long id, @NotEmpty(message = "Please enter a name") String name,
			@Email(message = "Email format is not valid") @NotEmpty(message = "Please enter an email") String email,
			@NotEmpty(message = "Please enter a password") @Size(min = 6, max = 20, message = "Password must be between 6 and 20 characters") String password,
			@NotEmpty(message = "Please enter a phone number") @Size(min = 10, max = 10, message = "Phone size must be 10") String phone,
			@NotEmpty(message = "Please enter a pet species") String petSpecies,
			@NotEmpty(message = "Please enter a pet name") String petName, Vet vet, Set<Authority> role) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.phone = phone;
		this.petSpecies = petSpecies;
		this.petName = petName;
		this.vet = vet;
		this.role = role;
	}

	public Customer(Long id, @NotEmpty String name, @NotEmpty String email, @NotEmpty String password,
			@NotEmpty String phone, @NotEmpty String petSpecies, @NotEmpty String petName, Vet vet) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.phone = phone;
		this.petSpecies = petSpecies;
		this.petName = petName;
		this.vet = vet;
	}

	public Customer(@NotEmpty String name, @NotEmpty String email, @NotEmpty String password, @NotEmpty String phone,
			@NotEmpty String petSpecies, @NotEmpty String petName, Vet vet) {
		super();
		this.email = email;
		this.password = password;
		this.name = name;
		this.phone = phone;
		this.petSpecies = petSpecies;
		this.petName = petName;
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

	public Vet getVet() {
		return vet;
	}

	public void setVet(Vet vet) {
		this.vet = vet;
	}

	public Set<Authority> getRole() {
		return role;
	}

	public void setRole(Set<Authority> role) {
		this.role = role;
	}

	public void addRole(Authority auth) {
		role.add(auth);
	}

	public void removeRole(Authority auth) {
		role.remove(auth);
	}

}
