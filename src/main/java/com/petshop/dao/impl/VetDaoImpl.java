package com.petshop.dao.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.petshop.dao.VetDao;
import com.petshop.exception.IdNotFoundException;
import com.petshop.models.Vet;
import com.petshop.repository.VetRepository;

@Repository
public class VetDaoImpl implements VetDao {

	@Autowired
	private VetRepository vetRepository;

	@Override
	public Vet getVetById(Long id) {
		return vetRepository.findById(id).orElseThrow(IdNotFoundException::new);
	}

	@Override
	public List<Vet> getAllVets() {
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
		}).orElseThrow(IdNotFoundException::new);
	}

	@Override
	public void deleteVetById(Long id) {
		vetRepository.deleteById(id);
	}

	@Override
	public Boolean checkEmail(String email) {
		Boolean exists = false;
		if (vetRepository.findByEmail(email) != null) {
			exists = true;
			return exists;
		}

		return exists;
	}

}
