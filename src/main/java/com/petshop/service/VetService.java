package com.petshop.service;

import com.petshop.dto.VetDTO;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface VetService {

	CompletableFuture<VetDTO> getVetById(Long id) throws InterruptedException;

	List<VetDTO> getAllVets();

	VetDTO saveVet(VetDTO vetDTO);

	VetDTO updateVet(Long id, VetDTO vetDTO);

	void deleteVetById(Long id);

}