package com.petshop.models.authority;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.petshop.models.Customer;
import com.petshop.models.Vet;

@Entity
public class Authority implements Serializable {
	private static final long serialVersionUID = -3621847689219057132L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(unique = true)
	@NotNull(message = "Invalid role request")
	private Role role;

	@ManyToMany(mappedBy = "role")
	@JsonIgnore
	private Set<Vet> vets = new HashSet<>();

	@ManyToMany(mappedBy = "role")
	@JsonIgnore
	private Set<Customer> customers = new HashSet<>();

	public Authority() {
	}

	public Authority(Role roles) {
		this.role = roles;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<Vet> getVets() {
		return vets;
	}

	public void setVets(Set<Vet> vets) {
		this.vets = vets;
	}

	public Role getRole() {
		return role;
	}

	public void setRoles(Role roles) {
		this.role = roles;
	}

	public Set<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(Set<Customer> customers) {
		this.customers = customers;
	}
	
	

}