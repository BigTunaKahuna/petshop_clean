package com.petshop.service;

import java.util.List;

import com.petshop.dto.VetDTO;
import com.petshop.models.Vet;

public interface VetService {

	VetDTO getVetById(Long id);

	List<Vet> getAllVets();

	Vet saveVet(Vet vet);

	Vet updateVet(Long id, Vet vet);

	void deleteVetById(Long id);

}