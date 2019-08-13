package com.petshop.dao.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.petshop.dao.VetDao;
import com.petshop.models.Vet;
import com.petshop.repository.VetRepository;

import exception.ResourceNotFoundException;

@Repository
public class VetDaoImpl implements VetDao {

	@Autowired
	VetRepository vetRepository;

	@Override
	public Vet getVetById(Long id) {
		return vetRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("vet", "id", id));
	}

	@Override
	public List<Vet> gettAllVets() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vet saveVet(Vet vet) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vet updateVet(Long id, Vet vet) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteVet(Long id) {
		// TODO Auto-generated method stub

	}

}
