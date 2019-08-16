package com.petshop.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.petshop.dao.VetDao;
import com.petshop.http_errors.IdNotFoundException;
import com.petshop.models.Vet;

@Service
public class VetService {
	@Autowired
	VetDao vetDao;

	Logger logger = LoggerFactory.getLogger(VetService.class);

	public Vet getVetById(Long id) {
		return vetDao.getVetById(id);
	}

	public List<Vet> getAllVets() {
		return vetDao.gettAllVets();
	}
	
	public Vet saveVet(Vet vet) {
		return vetDao.saveVet(vet);
	}

	public Vet updateVet(Long id, Vet vet) {
		return vetDao.updateVet(id, vet);
	}

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
