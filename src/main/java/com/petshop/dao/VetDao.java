package com.petshop.dao;

import java.util.List;

import com.petshop.models.Vet;

public interface VetDao {
	
	Vet getVetById(Long id);
	
	List<Vet> gettAllVets();
	
	Vet saveVet(Vet vet);
	
	Vet updateVet(Long id,Vet vet);
	
	void deleteVet(Long id);
}
