package com.petshop.dto;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.petshop.models.authority.Authority;

public class VetWithRolesDTO {

	private Long id;

	@NotEmpty(message = "Please enter a name")
	private String name;

	@NotEmpty(message = "Please enter a password")
	@Size(min = 6, max = 20, message = "Password must be between 6 and 20 characters")
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
	private Set<Authority> roles = new HashSet<>();

	public VetWithRolesDTO() {
	}

	@Override
	public String toString() {
		return "VetDTO [id=" + id + ", name=" + name + ", age=" + age + ", yearsOfExperience=" + yearsOfExperience
				+ ", email=" + email + "]";
	}

	public VetWithRolesDTO(Long id, String name, String password, int age, double yearsOfExperience, String email,
			Set<Authority> roles) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.age = age;
		this.yearsOfExperience = yearsOfExperience;
		this.email = email;
		this.roles = roles;
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

	public Set<Authority> getRoles() {
		return roles;
	}

	public void setRoles(Set<Authority> authorities) {
		this.roles = authorities;
	}

	public void addRole(Authority auth) {
		roles.add(auth);
	}

	public void removeRole(Authority auth) {
		roles.remove(auth);
	}

}
