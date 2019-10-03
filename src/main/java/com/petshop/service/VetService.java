package com.petshop.service;

import com.petshop.dto.VetDTO;
import com.petshop.dto.VetWithRolesDTO;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface VetService {

	CompletableFuture<VetDTO> getVetById(Long id) throws InterruptedException;

	VetWithRolesDTO findVetById(Long id);

	List<VetDTO> getAllVets();

	VetDTO saveVet(VetDTO vetDTO);

	VetDTO updateVet(Long id, VetDTO vetDTO);

	void deleteVetById(Long id);

}