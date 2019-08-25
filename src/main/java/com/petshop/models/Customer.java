package com.petshop.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

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

    public Customer() {
    }

    public Customer(Long customerId, @NotNull String name, @NotNull String phone, @NotNull String petSpecies,
                    @NotNull String petName, Vet vet) {
        super();
        this.customerId = customerId;
        this.name = name;
        this.phone = phone;
        this.petSpecies = petSpecies;
        this.petName = petName;
        this.vet = vet;
    }

}
