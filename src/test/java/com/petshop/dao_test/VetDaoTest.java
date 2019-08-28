package com.petshop.dao_test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.petshop.dao.VetDao;
import com.petshop.http_errors.IdNotFoundException;
import com.petshop.models.Vet;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class VetDaoTest {

	@Autowired
	VetDao vetDao;

	@Test
	@Transactional
	@Rollback(true)
	public void testGetVetById() {
		Vet vet = new Vet("Marius", 25, Double.valueOf(3), "foo@gmail.com", new ArrayList<>());
		vetDao.saveVet(vet);
		Vet getVet = vetDao.getVetById(vet.getId());
		assertEquals(vet, getVet);
	};

	@Test
	@Transactional
	@Rollback(true)
	public void testGetVetByIdNotFound() {
		assertThrows(IdNotFoundException.class, () -> vetDao.getVetById(Long.valueOf(100)));
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testGetAllVets() {
		vetDao.saveVet(new Vet("Marius", 25, Double.valueOf(3), "foo@gmail.com", new ArrayList<>()));
		vetDao.saveVet(new Vet("Andrei", 40, Double.valueOf(13), "fooTest@gmail.com", new ArrayList<>()));

		List<Vet> getAllVets = vetDao.gettAllVets();

		assertEquals(2, getAllVets.size());
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testSaveVet() {
		Vet vet = vetDao.saveVet(new Vet("Marius", 25, Double.valueOf(3), "foo@gmail.com", new ArrayList<>()));
		Vet getVet = vetDao.getVetById(vet.getId());

		assertEquals(vet.getId(), getVet.getId());
		assertEquals("Marius", getVet.getName());
		assertEquals(Integer.valueOf(25), getVet.getAge());
		assertEquals(Double.valueOf(3), vet.getYearsOfExperience());
		assertEquals("foo@gmail.com", getVet.getEmail());
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testUpdateVet() {
		Vet vet = vetDao.saveVet(new Vet("Marius", 25, Double.valueOf(3), "foo@gmail.com", new ArrayList<>()));
		
		// Checking the old vet
		assertEquals(Integer.valueOf(1).toString(), vet.getId().toString());
		assertEquals("Marius", vet.getName());
		assertEquals(Integer.valueOf(25), vet.getAge());
		assertEquals(Double.valueOf(3), vet.getYearsOfExperience());
		assertEquals("foo@gmail.com", vet.getEmail());

		Vet newVet = vetDao.updateVet(vet.getId(),
				new Vet("Andrei", 40, Double.valueOf(13), "fooTest@gmail.com", new ArrayList<>()));
		Vet updatedVet = vetDao.getVetById(newVet.getId());

		// Checking the updated vet
		assertEquals(Integer.valueOf(1).toString(), updatedVet.getId().toString());
		assertEquals("Andrei", updatedVet.getName());
		assertEquals(Integer.valueOf(40), updatedVet.getAge());
		assertEquals(Double.valueOf(13), updatedVet.getYearsOfExperience());
		assertEquals("fooTest@gmail.com", updatedVet.getEmail());
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testDeleteVetById() {
		Vet vet = vetDao.saveVet(new Vet("Marius", 25, Double.valueOf(3), "foo@gmail.com", new ArrayList<>()));
		vetDao.deleteVetById(vet.getId());

		assertThrows(IdNotFoundException.class, () -> vetDao.getVetById(vet.getId()));
	}
}
