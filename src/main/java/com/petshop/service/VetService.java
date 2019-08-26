package com.petshop.service;

import com.petshop.dto.VetDTO;

import java.util.List;

public interface VetService {

	VetDTO getVetById(Long id);

	List<VetDTO> getAllVets();

	VetDTO saveVet(VetDTO vetDTO);

	VetDTO updateVet(Long id, VetDTO vetDTO);

	void deleteVetById(Long id);

}