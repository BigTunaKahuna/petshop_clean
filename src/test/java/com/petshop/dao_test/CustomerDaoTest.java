package com.petshop.dao_test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.ArrayList;
import javax.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import com.petshop.dao.CustomerDao;
import com.petshop.dao.VetDao;
import com.petshop.http_errors.IdNotFoundException;
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
		Vet vet = new Vet("Marius", 25, Double.valueOf(3), "foo@gmail.com", new ArrayList<>());
		vetDao.saveVet(vet);

		Customer customer1 = new Customer("Rares", "12345", "Labrador", "Toby", vet);
		Customer customer2 = new Customer("Rares", "12345", "Labrador", "Toby", vet);
		Customer customer3 = new Customer("Rares", "12345", "Labrador", "Toby", vet);

		customerDao.saveCustomer(vet.getId(), customer1);
		customerDao.saveCustomer(vet.getId(), customer2);
		customerDao.saveCustomer(vet.getId(), customer3);

		assertEquals(3, customerDao.getAllCustomers().size());
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testSaveAndGetByIdCustomer() {
		Vet vet = new Vet("Marius", 25, Double.valueOf(3), "foo@gmail.com", new ArrayList<>());
		Vet vetAfterSave = vetDao.saveVet(vet);

		Customer customer = new Customer("Rares", "12345", "Labrador", "Toby", vet);
		Customer customerAfterSave = customerDao.saveCustomer(vet.getId(), customer);

		// Vet validate
		assertEquals(vet.getId(), vetAfterSave.getId());
		assertEquals("Marius", vetAfterSave.getName());
		assertEquals((Integer) 25, vetAfterSave.getAge());
		assertEquals(Double.valueOf(3), vetAfterSave.getYearsOfExperience());
		assertEquals("foo@gmail.com", vetAfterSave.getEmail());

		// Customer after save validate
		assertEquals(customer.getId(), customerAfterSave.getId());
		assertEquals("Rares", customerAfterSave.getName());
		assertEquals("Labrador", customerAfterSave.getPetSpecies());
		assertEquals("Toby", customerAfterSave.getPetName());
		assertEquals("12345", customerAfterSave.getPhone());
		assertEquals(vet.toString(), customerAfterSave.getVet().toString());

		Customer customerAfterRetrieve = customerDao.getCustomerById(customerAfterSave.getId());
		// Customer after retrieve validate
		assertEquals(customer.getId(), customerAfterRetrieve.getId());
		assertEquals("Rares", customerAfterRetrieve.getName());
		assertEquals("Labrador", customerAfterRetrieve.getPetSpecies());
		assertEquals("Toby", customerAfterRetrieve.getPetName());
		assertEquals("12345", customerAfterRetrieve.getPhone());
		assertEquals(vet.toString(), customerAfterRetrieve.getVet().toString());
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testUpdateCustomer() {
		Vet vet = new Vet("Marius", 25, Double.valueOf(3), "foo@gmail.com", new ArrayList<>());
		vetDao.saveVet(vet);

		Customer customer = new Customer("Rares", "12345", "Labrador", "Toby", vet);
		customerDao.saveCustomer(vet.getId(), customer);

		Customer customerToBeChanged = new Customer("Andrei", "54321", "Retriever", "Dart", vet);
		Customer changedCustomer = customerDao.updateCustomer(customer.getId(), customerToBeChanged);

		assertEquals("Andrei", changedCustomer.getName());
		assertEquals("54321", changedCustomer.getPhone());
		assertEquals("Retriever", changedCustomer.getPetSpecies());
		assertEquals("Dart", changedCustomer.getPetName());
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testUpdateVetCustomer() {
		Vet vet1 = new Vet("Marius", 25, Double.valueOf(3), "foo@gmail.com", new ArrayList<>());
		Vet vet2 = new Vet("Andrei", 40, Double.valueOf(13), "fooTest@gmail.com", new ArrayList<>());
		Customer customer = new Customer("Rares", "12345", "Labrador", "Toby", new Vet());
		vetDao.saveVet(vet1);
		vetDao.saveVet(vet2);
		customerDao.saveCustomer(vet1.getId(),customer);
		Customer updatedCustomer = customerDao.updateVetCustomer(vet2.getId(), customer.getId(), customer);

		assertEquals(vet2.toString(), updatedCustomer.getVet().toString());
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testDeleteCustomerById() {
		Vet vet = new Vet("Marius", 25, Double.valueOf(3), "foo@gmail.com", new ArrayList<>());
		vetDao.saveVet(vet);

		Customer customer = customerDao.saveCustomer(vet.getId(),
				new Customer("Rares", "12345", "Labrador", "Toby", vet));

		customerDao.deleteCustomerById(customer.getId());
		assertThrows(IdNotFoundException.class, () -> customerDao.getCustomerById(customer.getId()));

	}

	@Test
	public void testIdNotFoundException() {
		assertThrows(IdNotFoundException.class, () -> customerDao.getCustomerById(Long.valueOf(100)));
	}
}
