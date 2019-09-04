package com.petshop.dto;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.petshop.models.Customer;

public class VetDTO {
	private Long id;
	@NotEmpty
	private String name;
	@NotNull
	private Integer age;
	@NotNull
	private double yearsOfExperience;
	@Email
	@NotEmpty
	private String email;
	private List<Customer> customers;

	public VetDTO() {
	}

	@Override
	public String toString() {
		return "VetDTO [id=" + id + ", name=" + name + ", age=" + age + ", yearsOfExperience=" + yearsOfExperience
				+ ", email=" + email + "]";
	}

	public VetDTO(Long id, String name, int age, double yearsOfExperience, String email, List<Customer> customers) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.yearsOfExperience = yearsOfExperience;
		this.email = email;
		this.customers = customers;
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

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Double getYearsOfExperience() {
		return yearsOfExperience;
	}

	public void setYearsOfExperience(double yearsOfExperience) {
		this.yearsOfExperience = yearsOfExperience;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}

}
