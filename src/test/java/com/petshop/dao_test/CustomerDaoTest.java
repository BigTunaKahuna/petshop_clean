package com.petshop.dao_test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;

import com.petshop.dao.CustomerDao;
import com.petshop.dao.VetDao;
import com.petshop.exception.IdNotFoundException;
import com.petshop.models.Customer;
import com.petshop.models.Vet;

@TestPropertySource(locations = "classpath:test.properties")
@SpringBootTest
public class CustomerDaoTest {

	@Autowired
	private CustomerDao customerDao;
	@Autowired
	private VetDao vetDao;

	@Test
	@Transactional
	@Rollback(true)
	public void testGetAllCustomers() {
		Vet vet = new Vet();
		vet.setName("Marius");
		vet.setEmail("foo@gmail.com");
		vet.setPassword("password");
		vet.setAge(30);
		vet.setYearsOfExperience(6D);
		vetDao.saveVet(vet);

		Customer customer1 = new Customer();
		customer1.setName("Rares");
		customer1.setEmail("foo@gmail.com");
		customer1.setPassword("password");
		customer1.setPhone("1234567890");
		customer1.setPetSpecies("Labrador");
		customer1.setPetName("Toby");
		customer1.setVet(vet);

		Customer customer2 = new Customer();
		customer2.setName("Rares");
		customer2.setEmail("foo@gmail.com");
		customer2.setPassword("password");
		customer2.setPhone("1234567890");
		customer2.setPetSpecies("Labrador");
		customer2.setPetName("Toby");
		customer2.setVet(vet);

		Customer customer3 = new Customer();
		customer3.setName("Rares");
		customer3.setEmail("foo@gmail.com");
		customer3.setPassword("password");
		customer3.setPhone("1234567890");
		customer3.setPetSpecies("Labrador");
		customer3.setPetName("Toby");
		customer3.setVet(vet);

		customerDao.saveCustomer(vet.getId(), customer1);
		customerDao.saveCustomer(vet.getId(), customer2);
		customerDao.saveCustomer(vet.getId(), customer3);

		assertEquals(3, customerDao.getAllCustomers().size());
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testSaveAndGetByIdCustomer() {
		Vet vet = new Vet();
		vet.setName("Marius");
		vet.setEmail("foo@gmail.com");
		vet.setPassword("password");
		vet.setAge(30);
		vet.setYearsOfExperience(6D);
		vetDao.saveVet(vet);
		Vet vetAfterSave = vetDao.saveVet(vet);

		Customer customer = new Customer();
		customer.setName("Rares");
		customer.setEmail("foo@gmail.com");
		customer.setPassword("password");
		customer.setPhone("1234567890");
		customer.setPetSpecies("Labrador");
		customer.setPetName("Toby");
		customer.setVet(vet);
		Customer customerAfterSave = customerDao.saveCustomer(vet.getId(), customer);

		// Vet validate
		assertEquals(vet.getId(), vetAfterSave.getId());
		assertEquals("Marius", vetAfterSave.getName());
		assertEquals((Integer) 30, vetAfterSave.getAge());
		assertEquals(Double.valueOf(6), vetAfterSave.getYearsOfExperience());
		assertEquals("foo@gmail.com", vetAfterSave.getEmail());

		// Customer after save validate
		assertEquals(customer.getId(), customerAfterSave.getId());
		assertEquals("Rares", customerAfterSave.getName());
		assertEquals("Labrador", customerAfterSave.getPetSpecies());
		assertEquals("Toby", customerAfterSave.getPetName());
		assertEquals("1234567890", customerAfterSave.getPhone());
		assertEquals(vet.toString(), customerAfterSave.getVet().toString());

		Customer customerAfterRetrieve = customerDao.getCustomerById(customerAfterSave.getId());
		// Customer after retrieve validate
		assertEquals(customer.getId(), customerAfterRetrieve.getId());
		assertEquals("Rares", customerAfterRetrieve.getName());
		assertEquals("Labrador", customerAfterRetrieve.getPetSpecies());
		assertEquals("Toby", customerAfterRetrieve.getPetName());
		assertEquals("1234567890", customerAfterRetrieve.getPhone());
		assertEquals(vet.toString(), customerAfterRetrieve.getVet().toString());
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testUpdateCustomer() {
		Vet vet = new Vet();
		vet.setName("Marius");
		vet.setEmail("foo@gmail.com");
		vet.setPassword("password");
		vet.setAge(30);
		vet.setYearsOfExperience(6D);
		vetDao.saveVet(vet);

		Customer customer = new Customer();
		customer.setName("Rares");
		customer.setEmail("foo@gmail.com");
		customer.setPassword("password");
		customer.setPhone("1234567890");
		customer.setPetSpecies("Labrador");
		customer.setPetName("Toby");
		customer.setVet(vet);
		customerDao.saveCustomer(vet.getId(), customer);

		Customer customerToBeChanged = new Customer();
		customerToBeChanged.setName("Andrei");
		customerToBeChanged.setEmail("fooUpdate@gmail.com");
		customerToBeChanged.setPassword("password");
		customerToBeChanged.setPhone("1234567890");
		customerToBeChanged.setPetSpecies("Retriever");
		customerToBeChanged.setPetName("Dart");
		customerToBeChanged.setVet(vet);

		Customer customerUpdate = customerDao.updateCustomer(customer.getId(), customerToBeChanged);
		Customer updatedCustomer = customerDao.getCustomerById(customerUpdate.getId());

		assertEquals("Andrei", updatedCustomer.getName());
		assertEquals("fooUpdate@gmail.com", updatedCustomer.getEmail());
		assertEquals("password", updatedCustomer.getPassword());
		assertEquals("1234567890", updatedCustomer.getPhone());
		assertEquals("Retriever", updatedCustomer.getPetSpecies());
		assertEquals("Dart", updatedCustomer.getPetName());
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testDeleteCustomerById() {
		Vet vet = new Vet();
		vet.setName("Marius");
		vet.setEmail("foo@gmail.com");
		vet.setPassword("password");
		vet.setAge(30);
		vet.setYearsOfExperience(6D);
		vetDao.saveVet(vet);

		Customer customer = new Customer();
		customer.setName("Rares");
		customer.setEmail("foo@gmail.com");
		customer.setPassword("password");
		customer.setPhone("1234567890");
		customer.setPetSpecies("Labrador");
		customer.setPetName("Toby");
		customer.setVet(vet);
		customerDao.saveCustomer(vet.getId(), customer);

		customerDao.deleteCustomerById(customer.getId());
		assertThrows(IdNotFoundException.class, () -> customerDao.getCustomerById(customer.getId()));
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testCheckEmail() {
		Vet vet = new Vet();
		vet.setName("Marius");
		vet.setEmail("foo@gmail.com");
		vet.setPassword("password");
		vet.setAge(30);
		vet.setYearsOfExperience(6D);
		vetDao.saveVet(vet);

		Customer customer = new Customer();
		customer.setName("Rares");
		customer.setEmail("foo@gmail.com");
		customer.setPassword("password");
		customer.setPhone("1234567890");
		customer.setPetSpecies("Labrador");
		customer.setPetName("Toby");
		customer.setVet(vet);
		customerDao.saveCustomer(vet.getId(), customer);
		
		Boolean email = customerDao.checkEmail(customer.getEmail());
		Boolean email2 = customerDao.checkEmail(null);
		assertEquals(true, email);
		assertEquals(false, email2);
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testFindByEmail() {
		Vet vet = new Vet();
		vet.setName("Marius");
		vet.setEmail("foo@gmail.com");
		vet.setPassword("password");
		vet.setAge(30);
		vet.setYearsOfExperience(6D);
		vetDao.saveVet(vet);

		Customer customer = new Customer();
		customer.setName("Rares");
		customer.setEmail("foo@gmail.com");
		customer.setPassword("password");
		customer.setPhone("1234567890");
		customer.setPetSpecies("Labrador");
		customer.setPetName("Toby");
		customer.setVet(vet);
		customerDao.saveCustomer(vet.getId(), customer);
		
		Customer findByEmail = customerDao.findByEmail(customer.getEmail());
		
		assertEquals(customer, findByEmail);
	}
	

	@Test
	public void testIdNotFoundException() {
		assertThrows(IdNotFoundException.class, () -> customerDao.getCustomerById(Long.valueOf(100)));
	}
	
	
}















