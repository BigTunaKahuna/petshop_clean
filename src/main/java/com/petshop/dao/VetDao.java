package com.petshop.dao;

import java.util.List;

import com.petshop.models.Vet;

public interface VetDao {

	Vet getVetById(Long id);

	List<Vet> getAllVets();

	Vet saveVet(Vet vet);
	
	void saveVetAndFlush(Vet vet); 

	Vet updateVet(Long id, Vet vet);

	void deleteVetById(Long id);

	Boolean checkEmail(String email);

	Vet findByEmail(String email);
}
