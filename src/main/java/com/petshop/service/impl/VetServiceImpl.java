package com.petshop.service.impl;

import com.petshop.dao.VetDao;
import com.petshop.dto.VetDTO;
import com.petshop.http_errors.IdNotFoundException;
import com.petshop.mapper.VetMapper;
import com.petshop.models.Vet;
import com.petshop.service.VetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VetServiceImpl implements VetService {
	@Autowired
	private VetDao vetDao;
	@Autowired
	private VetMapper vetMapper;

	@Override
	public VetDTO getVetById(Long id) {
		Vet vet = vetDao.getVetById(id);
		VetDTO vetDTO = vetMapper.mapEntityToDto(vet);
		return vetDTO;
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
		VetDTO vetDtoHolder = vetMapper.mapEntityToDto(vetDao.saveVet(vet));
		return vetDtoHolder;
	}

	@Override
	public VetDTO updateVet(Long id, VetDTO vetDTO) {
		Vet vet = vetMapper.mapDtoToEntity(vetDTO);
		VetDTO vetDtoHolder = vetMapper.mapEntityToDto(vetDao.updateVet(id, vet));
		return vetDtoHolder;
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
