package com.petshop.dto;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.petshop.models.Customer;

public class VetDTO {
	private Long id;

	@NotEmpty(message = "Please enter a name")
	private String name;

	@NotEmpty(message = "Please enter a password")
	@Size(min = 6, message = "Password must be at least 6 characters")
	private String password;

	@NotNull(message = "Please enter an age")
	@Min(value = 18, message = "Age must be at least 18")
	@Max(value = 70, message = "Age must be less then 80")
	private Integer age;

	@NotNull(message = "Please enter the years of experience")
	@Min(value = 1, message = "Experience must be greater then 1")
	@Max(value = 62, message = "Experience must be less then 62")
	private double yearsOfExperience;

	@Email(message = "Email format is not valid")
	@NotEmpty(message = "Please enter an email")
	private String email;
	private List<Customer> customers;

	public VetDTO() {
	}

	@Override
	public String toString() {
		return "VetDTO [id=" + id + ", name=" + name + ", age=" + age + ", yearsOfExperience=" + yearsOfExperience
				+ ", email=" + email + "]";
	}

	public VetDTO(Long id, String name, String password, int age, double yearsOfExperience, String email,
			List<Customer> customers) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
