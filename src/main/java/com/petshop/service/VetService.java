package com.petshop.service;

import com.petshop.dto.VetDTO;
import com.petshop.models.Vet;

import java.util.List;

public interface VetService {

	VetDTO getVetById(Long id);

	List<VetDTO> getAllVets();

	VetDTO saveVet(Vet vet);

	VetDTO updateVet(Long id, Vet vet);

	void deleteVetById(Long id);

}