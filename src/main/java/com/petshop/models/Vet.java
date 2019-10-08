package com.petshop.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.petshop.models.authority.Authority;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "VET")
public class Vet implements Serializable {

	private static final long serialVersionUID = 8582488702975524966L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "vet_id")
	private Long id;

	@NotEmpty(message = "Please enter a name")
	private String name;

	@NotEmpty(message = "Please enter a password")
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

	@JsonManagedReference
	@OneToMany(mappedBy = "vet", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<Customer> customers = new ArrayList<>();

	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.EAGER)
	@JoinTable(name = "vet_role", joinColumns = @JoinColumn(name = "vet_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Authority> role = new HashSet<>();

	public Vet(Long id, @NotEmpty String name, @NotEmpty String password, @NotNull int age,
			@NotNull double yearsOfExperience, @NotEmpty @Email String email, List<Customer> customers,
			Set<Authority> roles) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.age = age;
		this.yearsOfExperience = yearsOfExperience;
		this.email = email;
		this.customers = customers;
		this.role = roles;
	}

//	public Vet(Long id, @NotEmpty String name, @NotEmpty String password, @NotNull int age,
//			@NotNull double yearsOfExperience, @NotEmpty @Email String email, List<Customer> customers) {
//		super();
//		this.id = id;
//		this.name = name;
//		this.password = password;
//		this.age = age;
//		this.yearsOfExperience = yearsOfExperience;
//		this.email = email;
//		this.customers = customers;
//	}
//
//	public Vet(@NotNull String name, @NotNull int age, @NotNull double yearsOfExperience, @NotNull @Email String email,
//			List<Customer> customers) {
//		super();
//		this.name = name;
//		this.age = age;
//		this.yearsOfExperience = yearsOfExperience;
//		this.email = email;
//		this.customers = customers;
//	}

	public Vet() {
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

	public void setYearsOfExperience(Double yearsOfExperience) {
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

	public void addCustomer(Customer customer) {
		customers.add(customer);
		customer.setVet(this);
	}

	public void removeCustomer(Customer customer) {
		customers.remove(customer);
		customer.setVet(null);
	}

	public Set<Authority> getRoles() {
		return role;
	}

	public void setRoles(Set<Authority> role) {
		this.role = role;
	}

	public void addRole(Authority auth) {
		role.add(auth);
	}

	public void removeRole(Authority auth) {
		role.remove(auth);
	}

	@Override
	public String toString() {
		return "Vet [id=" + id + ", name=" + name + ", age=" + age + ", yearsOfExperience=" + yearsOfExperience
				+ ", email=" + email + "]";
	}

}
