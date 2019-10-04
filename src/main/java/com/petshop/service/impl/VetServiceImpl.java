package com.petshop.service.impl;

import com.petshop.dao.VetDao;
import com.petshop.dto.VetDTO;
import com.petshop.dto.VetWithRolesDTO;
import com.petshop.exception.EmailAlreadyExistsException;
import com.petshop.exception.IdNotFoundException;
import com.petshop.exception.RoleNotFoundException;
import com.petshop.mapper.impl.VetMapper;
import com.petshop.mapper.impl.VetWithRolesMapper;
import com.petshop.models.Vet;
import com.petshop.models.authority.Authority;
import com.petshop.models.authority.Role;
import com.petshop.repository.RoleRepository;
import com.petshop.service.VetService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
	private RoleRepository roleRepository;
	@Autowired
	private VetMapper vetMapper;
	@Autowired
	private VetWithRolesMapper vetWithRolesMapper;
	@Autowired
	BCryptPasswordEncoder bcrypt;

	@Override
	@Async("taskExecutor")
	@Transactional
	public CompletableFuture<VetDTO> getVetById(Long id) throws InterruptedException {
		Vet vet = vetDao.getVetById(id);
		VetDTO vetDTO = vetMapper.mapEntityToDto(vet);
		return CompletableFuture.completedFuture(vetDTO);
	}

	@Override
	public VetWithRolesDTO findVetById(Long id) {
		Vet vet = vetDao.getVetById(id);
		return vetWithRolesMapper.mapEntityToDto(vet);
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
		if (vetDao.checkEmail(vetDTO.getEmail())) {
			throw new EmailAlreadyExistsException();
		}
		Vet vet = vetMapper.mapDtoToEntity(vetDTO);
		Authority auth = roleRepository.findByRole(Role.ADMIN);
		if (auth != null) {
			vet.setPassword(bcrypt.encode(vetDTO.getPassword()));
			vet.addRole(auth);
			return vetMapper.mapEntityToDto(vetDao.saveVet(vet));
		} else throw new RoleNotFoundException();
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
