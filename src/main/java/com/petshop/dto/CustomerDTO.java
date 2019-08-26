package com.petshop.dto;

public class CustomerDTO {
	private Long customerId;
	private String name;
	private String phone;
	private String petSpecies;
	private String petName;
	private String vet;

	public CustomerDTO() {
	}

	public CustomerDTO(Long customerId, String name, String phone, String petSpecies, String petName, String vet) {
		super();
		this.customerId = customerId;
		this.name = name;
		this.phone = phone;
		this.petSpecies = petSpecies;
		this.petName = petName;
		this.vet = vet;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
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
