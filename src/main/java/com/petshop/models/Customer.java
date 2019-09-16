package com.petshop.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "CUSTOMER")
public class Customer implements Serializable {
	private static final long serialVersionUID = -4592973994222019477L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotEmpty(message = "Please enter a name")
	private String name;
	@NotEmpty(message = "Please enter a phone number")
	private String phone;
	@NotEmpty(message = "Please enter a pet species")
	private String petSpecies;
	@NotEmpty(message = "Please enter a pet name")
	private String petName;
	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "vet_id")
	private Vet vet;

	public Customer() {
	}

	public Customer(@NotNull String name, @NotNull String phone, @NotNull String petSpecies, @NotNull String petName,
			Vet vet) {
		super();
		this.name = name;
		this.phone = phone;
		this.petSpecies = petSpecies;
		this.petName = petName;
		this.vet = vet;
	}

	public Customer(Long id, @NotNull String name, @NotNull String phone, @NotNull String petSpecies,
			@NotNull String petName, Vet vet) {
		super();
		this.id = id;
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

}
