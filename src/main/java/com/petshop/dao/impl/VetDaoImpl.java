package com.petshop.dao.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.petshop.dao.VetDao;
import com.petshop.http_errors.IdNotFoundException;
import com.petshop.models.Vet;
import com.petshop.repository.VetRepository;

@Repository
public class VetDaoImpl implements VetDao {

	@Autowired
	VetRepository vetRepository;

	@Override
	public Vet getVetById(Long id) {
		return vetRepository.findById(id).orElseThrow(() -> new IdNotFoundException());
	}

	@Override
	public List<Vet> gettAllVets() {
		return vetRepository.findAll();
	}

	@Override
	public Vet saveVet(Vet vet) {
		return vetRepository.save(vet);
	}

	@Override
	public Vet updateVet(Long id, Vet vet) {
		return vetRepository.findById(id).map(vetReq -> {
			vetReq.setName(vet.getName());
			vetReq.setAge(vet.getAge());
			vetReq.setYearsOfExperience(vet.getYearsOfExperience());
			vetReq.setEmail(vet.getEmail());
			return vetRepository.save(vetReq);
		}).orElseThrow(() -> new IdNotFoundException());
	}

	@Override
	public void deleteVetById(Long id) {
		vetRepository.deleteById(id);
	}

}
