package com.petshop.dao_test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.petshop.dao.VetDao;
import com.petshop.exception.IdNotFoundException;
import com.petshop.models.Vet;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

@TestPropertySource(locations = "classpath:test.properties")
@SpringBootTest
public class VetDaoTest {

	@Autowired
	private VetDao vetDao;

	@Test
	@Transactional
	@Rollback(true)
	public void testGetVetById() {
		Vet vet = new Vet();
		vet.setName("Marius");
		vet.setEmail("foo@gmail.com");
		vet.setPassword("password");
		vet.setAge(30);
		vet.setYearsOfExperience(6D);
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
		Vet vet1 = new Vet();
		vet1.setName("Marius");
		vet1.setEmail("foo@gmail.com");
		vet1.setPassword("password");
		vet1.setAge(30);
		vet1.setYearsOfExperience(6D);

		Vet vet2 = new Vet();
		vet2.setName("Andrei");
		vet2.setEmail("foo@gmail.com");
		vet2.setPassword("password");
		vet2.setAge(30);
		vet2.setYearsOfExperience(6D);
		vetDao.saveVet(vet1);
		vetDao.saveVet(vet2);

		List<Vet> getAllVets = vetDao.getAllVets();

		assertEquals(2, getAllVets.size());
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testSaveVet() {
		Vet vet = new Vet();
		vet.setName("Marius");
		vet.setEmail("foo@gmail.com");
		vet.setPassword("password");
		vet.setAge(30);
		vet.setYearsOfExperience(6D);
		vetDao.saveVet(vet);
		Vet getVet = vetDao.getVetById(vet.getId());

		assertEquals(vet.getId(), getVet.getId());
		assertEquals("Marius", getVet.getName());
		assertEquals("password", getVet.getPassword());
		assertEquals(Integer.valueOf(30), getVet.getAge());
		assertEquals(Double.valueOf(6), getVet.getYearsOfExperience());
		assertEquals("foo@gmail.com", getVet.getEmail());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testSaveVetAndFlush() {
		Vet vet = new Vet();
		vet.setName("Marius");
		vet.setEmail("foo@gmail.com");
		vet.setPassword("password");
		vet.setAge(30);
		vet.setYearsOfExperience(6D);
		vetDao.saveVetAndFlush(vet);
		Vet getVet = vetDao.getVetById(vet.getId());

		assertEquals(vet.getId(), getVet.getId());
		assertEquals("Marius", getVet.getName());
		assertEquals("password", getVet.getPassword());
		assertEquals(Integer.valueOf(30), getVet.getAge());
		assertEquals(Double.valueOf(6), getVet.getYearsOfExperience());
		assertEquals("foo@gmail.com", getVet.getEmail());
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testUpdateVet() {
		Vet vet1 = new Vet();
		vet1.setName("Marius");
		vet1.setEmail("foo@gmail.com");
		vet1.setPassword("password");
		vet1.setAge(30);
		vet1.setYearsOfExperience(6D);
		Vet vetHolder = vetDao.saveVet(vet1);

		// Checking the old vet
		assertEquals("Marius", vetHolder.getName());
		assertEquals("password", vetHolder.getPassword());
		assertEquals(Integer.valueOf(30), vetHolder.getAge());
		assertEquals(Double.valueOf(6), vetHolder.getYearsOfExperience());
		assertEquals("foo@gmail.com", vetHolder.getEmail());

		Vet vet2 = new Vet();
		vet2.setName("Andrei");
		vet2.setEmail("fooUpdate@gmail.com");
		vet2.setPassword("password2");
		vet2.setAge(40);
		vet2.setYearsOfExperience(13D);

		Vet newVet = vetDao.updateVet(vet1.getId(), vet2);
		Vet updatedVet = vetDao.getVetById(newVet.getId());

		// Checking the updated vet
		assertEquals("Andrei", updatedVet.getName());
		assertEquals("password2", updatedVet.getPassword());
		assertEquals(Integer.valueOf(40), updatedVet.getAge());
		assertEquals(Double.valueOf(13), updatedVet.getYearsOfExperience());
		assertEquals("fooUpdate@gmail.com", updatedVet.getEmail());
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testDeleteVetById() {
		Vet vet = new Vet();
		vet.setName("Marius");
		vet.setPassword("password");
		vet.setAge(30);
		vet.setYearsOfExperience(6D);
		vet.setEmail("foo@gmail.com");

		Vet vetHolder = vetDao.saveVet(vet);
		vetDao.deleteVetById(vetHolder.getId());

		assertThrows(IdNotFoundException.class, () -> vetDao.getVetById(vetHolder.getId()));
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testFindByEmail() {
		Vet vet = new Vet();
		vet.setName("Marius");
		vet.setPassword("password");
		vet.setAge(30);
		vet.setYearsOfExperience(6D);
		vet.setEmail("foo@gmail.com");
		
		vetDao.saveVet(vet);
		Vet findByEmail = vetDao.findByEmail(vet.getEmail());
		
		assertEquals(vet, findByEmail);
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testCheckEmail() {
		Vet vet = new Vet();
		vet.setName("Marius");
		vet.setPassword("password");
		vet.setAge(30);
		vet.setYearsOfExperience(6D);
		vet.setEmail("foo@gmail.com");
		vetDao.saveVet(vet);
		
		Boolean email1 = vetDao.checkEmail(vet.getEmail());
		Boolean email2 = vetDao.checkEmail("");
		
		assertEquals(true, email1);
		assertEquals(false, email2);
		
	}
}
