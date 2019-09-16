package com.petshop.service.impl;

import com.petshop.dao.VetDao;
import com.petshop.dto.VetDTO;
import com.petshop.exception.IdNotFoundException;
import com.petshop.mapper.impl.VetMapper;
import com.petshop.models.Vet;
import com.petshop.service.VetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class VetServiceImpl implements VetService {
	@Autowired
	private VetDao vetDao;
	@Autowired
	private VetMapper vetMapper;

	@Override
	@Async("taskExecutor")
	@Transactional
	public CompletableFuture<VetDTO> getVetById(Long id) throws InterruptedException {
		Vet vet = vetDao.getVetById(id);
		VetDTO vetDTO = vetMapper.mapEntityToDto(vet);
		return CompletableFuture.completedFuture(vetDTO);
	}

	@Override
	public List<VetDTO> getAllVets() {
		List<Vet> vets = vetDao.getAllVets();
		List<VetDTO> vetDTO = new ArrayList<>();
		for (var vet : vets) {
			vetDTO.add(vetMapper.mapEntityToDto(vet));
		}
		return vetDTO;
	}

	@Override
	public VetDTO saveVet(VetDTO vetDTO) {
		Vet vet = vetMapper.mapDtoToEntity(vetDTO);
		return vetMapper.mapEntityToDto(vetDao.saveVet(vet));
	}

	@Override
	public VetDTO updateVet(Long id, VetDTO vetDTO) {
		Vet vet = vetMapper.mapDtoToEntity(vetDTO);
		return vetMapper.mapEntityToDto(vetDao.updateVet(id, vet));
	}

	@Override
	public void deleteVetById(Long id) {
		try {
			vetDao.deleteVetById(id);
		} catch (IdNotFoundException e) {
			throw new IdNotFoundException();
		}
	}

}
