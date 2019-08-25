package com.petshop.dto;

import com.petshop.models.Vet;

public class CustomerDTO {
    private Long customerId;
    private String name;
    private String phone;
    private String petSpecies;
    private String petName;
    private Vet vet;

    public CustomerDTO() {
    }

    public CustomerDTO(Long customerId, String name, String phone, String petSpecies, String petName, Vet vet) {
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

    public Vet getVet() {
        return vet;
    }

    public void setVet(Vet vet) {
        this.vet = vet;
    }
}
