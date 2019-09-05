package com.petshop.service_test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.petshop.dao.CustomerDao;
import com.petshop.dto.CustomerDTO;
import com.petshop.http_errors.IdNotFoundException;
import com.petshop.mapper.CustomerMapper;
import com.petshop.mapper.VetMapper;
import com.petshop.models.Customer;
import com.petshop.models.Vet;
import com.petshop.service.CustomerService;
import com.petshop.service.VetService;
import static org.mockito.BDDMockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

	@Autowired
	CustomerService customerService;
	@Autowired
	VetService vetService;
	@Autowired
	CustomerMapper customerMapper;
	@Autowired
	VetMapper vetMapper;
	@MockBean
	CustomerDao customerDao;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetCustomerById() throws IdNotFoundException {
		Customer customer = new Customer(1L, "Adrian", "012345", "Labrador", "Toby", new Vet());
		when(customerDao.getCustomerById(anyLong())).thenReturn(customer);
		CustomerDTO customerDTO = customerService.getCustomerById(anyLong());

		verify(customerDao).getCustomerById(anyLong());
		assertThat(customerDTO.getId()).isEqualTo(1L);
		assertThat(customerDTO.getName()).isEqualTo("Adrian");
		assertThat(customerDTO.getPhone()).isEqualTo("012345");
		assertThat(customerDTO.getPetSpecies()).isEqualTo("Labrador");
		assertThat(customerDTO.getPetName()).isEqualTo("Toby");
		assertThat(customerDTO.getVet()).isNullOrEmpty();
	}

	@Test
	public void testGetAllCustomers() {
		List<Customer> allCustomers = new ArrayList<>();
		Customer customer1 = new Customer(1L, "Adrian", "012345", "Labrador", "Toby", new Vet());
		Customer customer2 = new Customer(2L, "Andrei", "054321", "Bichon", "Ali", new Vet());
		allCustomers.add(customer1);
		allCustomers.add(customer2);

		when(customerDao.getAllCustomers()).thenReturn(allCustomers);

		List<CustomerDTO> retrieveAllCustomers = customerService.getAllCustomers();

		verify(customerDao).getAllCustomers();
		assertEquals(2, retrieveAllCustomers.size());
	}

	@Test
	public void testSaveCustomer() {
		Customer customer = new Customer(1L, "Adrian", "012345", "Labrador", "Toby", new Vet());

		when(customerDao.saveCustomer(anyLong(), any(Customer.class))).thenReturn(customer);
		CustomerDTO customerDTO = customerService.saveCustomer(anyLong(), any(CustomerDTO.class));

		verify(customerDao).saveCustomer(anyLong(), any(Customer.class));
		assertThat(customerDTO.getId()).isEqualTo(1L);
		assertThat(customerDTO.getName()).isEqualTo("Adrian");
		assertThat(customerDTO.getPhone()).isEqualTo("012345");
		assertThat(customerDTO.getPetSpecies()).isEqualTo("Labrador");
		assertThat(customerDTO.getPetName()).isEqualTo("Toby");
		assertThat(customerDTO.getVet()).isNullOrEmpty();
	}

	@Test
	public void testUpdateCustomer() {
		Customer customer = new Customer("Andrei", "543210", "Bichon", "Ali", new Vet());

		when(customerDao.updateCustomer(anyLong(), any(Customer.class))).thenReturn(customer);
		CustomerDTO customerDTO = customerService.updateCustomer(anyLong(), any(CustomerDTO.class));

		verify(customerDao).updateCustomer(anyLong(), any(Customer.class));
		assertEquals("Andrei", customerDTO.getName());
		assertEquals("543210", customerDTO.getPhone());
		assertEquals("Bichon", customerDTO.getPetSpecies());
		assertEquals("Ali", customerDTO.getPetName());
	}

	@Test
	public void testUpdateVetForCustomer() {
		Vet vet = new Vet();
		vet.setName("Iulica");
		Customer customer = new Customer(1L, "Adrian", "012345", "Labrador", "Toby", vet);

		when(customerDao.updateVetCustomer(anyLong(), anyLong(), any(Customer.class))).thenReturn(customer);
		CustomerDTO customerDTO = customerService.updateVetForCustomer(anyLong(), anyLong(), any(CustomerDTO.class));

		verify(customerDao).updateVetCustomer(anyLong(), anyLong(), any(Customer.class));
		assertThat(customerDTO.getId()).isEqualTo(1L);
		assertThat(customerDTO.getName()).isEqualTo("Adrian");
		assertThat(customerDTO.getPhone()).isEqualTo("012345");
		assertThat(customerDTO.getPetSpecies()).isEqualTo("Labrador");
		assertThat(customerDTO.getPetName()).isEqualTo("Toby");
		assertThat(customerDTO.getVet()).isEqualTo("Iulica");
	}

	@Test
	public void testDeleteCustomerById() {
		customerService.deleteCustomerById(anyLong());

		verify(customerDao).deleteCustomerById(anyLong());
	}
}
