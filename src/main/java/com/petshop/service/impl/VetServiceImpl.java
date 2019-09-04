package com.petshop.service.service_impl;

import com.petshop.dao.VetDao;
import com.petshop.dto.VetDTO;
import com.petshop.http_errors.IdNotFoundException;
import com.petshop.mapper.VetMapper;
import com.petshop.models.Vet;
import com.petshop.service.VetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VetServiceImpl implements VetService {
	@Autowired
	VetDao vetDao;
	@Autowired
	VetMapper vetMapper;

	Logger logger = LoggerFactory.getLogger(VetServiceImpl.class);

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
			if (vetDao.getVetById(id) != null) {
				vetDao.deleteVetById(id);
				logger.info("Not Null");
			}
		} catch (Exception e) {
			logger.info("we throwed exception");
			throw new IdNotFoundException();
		}
	}

}
