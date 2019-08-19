package com.petshop.models;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

@Entity
@Table(name = "CUSTOMER")
@Data
public class Customer implements Serializable {
	private static final long serialVersionUID = -4592973994222019477L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long customerId;
	@NotNull
	private String name;
	@NotNull
	private String phone;
	@NotNull
	private String petSpecies;
	@NotNull
	private String petName;
	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "vet_id")
	private Vet vet;

}
