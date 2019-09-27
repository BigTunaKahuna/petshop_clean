package com.petshop.dao.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.petshop.dao.VetDao;
import com.petshop.exception.IdNotFoundException;
import com.petshop.models.Authority;
import com.petshop.models.Roles;
import com.petshop.models.Vet;
import com.petshop.repository.RolesRepository;
import com.petshop.repository.VetRepository;

@Repository
public class VetDaoImpl implements VetDao {

	@Autowired
	private VetRepository vetRepository;
	
	@Autowired
	private RolesRepository roleRepository;

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
		Authority role = new Authority();
		role.setRoles(Roles.ADMIN);
		
		roleRepository.save(role);
		vet.addRole(role);
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
	public Vet findByEmail(String email) {
		System.out.println("vet " +vetRepository.findByEmail(email).toString());
		return vetRepository.findByEmail(email);
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
