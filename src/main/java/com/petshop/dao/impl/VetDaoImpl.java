package com.petshop.dao.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.petshop.dao.VetDao;
import com.petshop.exception.IdNotFoundException;
import com.petshop.models.Vet;
import com.petshop.models.authority.Authority;
import com.petshop.repository.RoleRepository;
import com.petshop.repository.VetRepository;

@Repository
public class VetDaoImpl implements VetDao {

	@Autowired
	private VetRepository vetRepository;
	@Autowired
	private RoleRepository roleRepository;

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
//		List<Authority> auth = roleRepository.findAll();
//		for (Authority authority : auth) {
//			vet.addRole(authority);
//		}
		addRoles(vet.getRoles());
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

	public void addRoles(Set<Authority> obj) {
		List<Authority> auth = roleRepository.findAll();
		for (Authority authority : auth) {
			obj.add(authority);
		}
	}

}
