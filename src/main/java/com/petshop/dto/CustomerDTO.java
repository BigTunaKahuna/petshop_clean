package com.petshop.dto;

import javax.validation.constraints.NotEmpty;

public class CustomerDTO {
	private Long id;
	@NotEmpty
	private String name;
	@NotEmpty
	private String phone;
	@NotEmpty
	private String petSpecies;
	@NotEmpty
	private String petName;
	private String vet;

	public CustomerDTO() {
	}

	public CustomerDTO(Long id, String name, String phone, String petSpecies, String petName, String vet) {
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

	public String getVet() {
		return vet;
	}

	public void setVet(String string) {
		this.vet = string;
	}
}
