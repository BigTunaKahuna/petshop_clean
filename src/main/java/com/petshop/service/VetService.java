package com.petshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.petshop.dao.VetDao;
import com.petshop.models.Vet;

@Service
public class VetService {
	@Autowired
	VetDao vetDao;
	
	public Vet getVetById(Long id) {
		return vetDao.getVetById(id);
	}

}
