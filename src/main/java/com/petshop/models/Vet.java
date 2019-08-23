package com.petshop.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
public class Vet implements Serializable {

	private static final long serialVersionUID = 8582488702975524966L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "vet_id")
	private Long id;
	@NotNull
	private String name;
	@NotNull
	private Integer age;
	@NotNull
	private Double yearsOfExperience;
	@NotNull
	@Email
	private String email;
	@JsonManagedReference
	@OneToMany(mappedBy = "vet", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Customer> customers = new ArrayList<>();

	public Vet(Long id, @NotNull String name, @NotNull int age, @NotNull double yearsOfExperience,
			@NotNull @Email String email, List<Customer> customers) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.yearsOfExperience = yearsOfExperience;
		this.email = email;
		this.customers = customers;
	}

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

	@Override
	public String toString() {
		return "Vet [id=" + id + ", name=" + name + ", age=" + age + ", yearsOfExperience=" + yearsOfExperience
				+ ", email=" + email + "]";
	}

}
