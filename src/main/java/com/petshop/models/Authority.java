package com.petshop.models;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Authority implements Serializable {
	private static final long serialVersionUID = -3621847689219057132L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	private Roles roles;

	@ManyToMany(mappedBy = "roles")
	private Set<Vet> vets = new HashSet<>();

	public Authority() {
	}

	public Authority(Roles roles) {
		this.roles = roles;
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

	public Roles getRole() {
		return roles;
	}

	public void setRoles(Roles roles) {
		this.roles = roles;
	}

}