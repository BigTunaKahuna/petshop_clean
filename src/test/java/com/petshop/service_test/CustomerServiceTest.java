package com.petshop.service_test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.petshop.dto.CustomerDTO;
import com.petshop.dto.VetDTO;
import com.petshop.http_errors.IdNotFoundException;
import com.petshop.mapper.CustomerMapper;
import com.petshop.mapper.VetMapper;
import com.petshop.models.Customer;
import com.petshop.models.Vet;
import com.petshop.service.CustomerService;
import com.petshop.service.VetService;

@SpringBootTest
public class CustomerServiceTest {

	@Mock
	CustomerService customerService;
	@Mock
	VetService vetService;
	@Autowired
	CustomerMapper customerMapper;
	@Autowired
	VetMapper vetMapper;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetCustomerById() throws IdNotFoundException {
		when(customerService.getCustomerById(Long.valueOf(1))).thenReturn(
				customerMapper.mapEntityToDto(new Customer("Adrian", "012345", "Labrador", "Toby", new Vet())));
		CustomerDTO customerDTO = customerService.getCustomerById(Long.valueOf(1));

		assertEquals("Adrian", customerDTO.getName());
		assertEquals("012345", customerDTO.getPhone());
		assertEquals("Labrador", customerDTO.getPetSpecies());
		assertEquals("Toby", customerDTO.getPetName());
	}

	@Test
	public void testGetAllCustomers() {
		List<CustomerDTO> allCustomers = new ArrayList<>();
		CustomerDTO customer1 = customerMapper
				.mapEntityToDto(new Customer("Adrian", "012345", "Labrador", "Toby", new Vet()));
		CustomerDTO customer2 = customerMapper
				.mapEntityToDto(new Customer("Andrei", "054321", "Bichon", "Ali", new Vet()));

		allCustomers.add(customer1);
		allCustomers.add(customer2);

		when(customerService.getAllCustomers()).thenReturn(allCustomers);
		List<CustomerDTO> retrievedAllCustomers = customerService.getAllCustomers();

		assertEquals(2, retrievedAllCustomers.size());
		verify(customerService).getAllCustomers();
	}

	@Test
	public void testSaveCustomer() {
		VetDTO vet = vetMapper.mapEntityToDto(new Vet("NumeDTO", 30, 10, "foo@gmail.com", new ArrayList<>()));
		CustomerDTO customer = customerMapper
				.mapEntityToDto(new Customer("Adrian", "012345", "Labrador", "Toby", new Vet()));
		when(customerService.saveCustomer(vet.getId(), customer)).thenReturn(customer);
		customerService.saveCustomer(vet.getId(), customer);
		verify(customerService).saveCustomer(vet.getId(), customer);
	}

	@Test
	public void testUpdateCustomer() {
		CustomerDTO customer1 = customerMapper
				.mapEntityToDto(new Customer("Adrian", "012345", "Labrador", "Toby", new Vet()));
		CustomerDTO customer2 = customerMapper
				.mapEntityToDto(new Customer("Andrei", "543210", "Bichon", "Ali", new Vet()));

		when(customerService.updateCustomer(customer1.getCustomerId(), customer2)).thenReturn(customer2);
		CustomerDTO updatedCustomer = customerService.updateCustomer(customer1.getCustomerId(), customer2);
		verify(customerService).updateCustomer(customer1.getCustomerId(), customer2);

		assertEquals("Andrei", updatedCustomer.getName());
		assertEquals("543210", updatedCustomer.getPhone());
		assertEquals("Bichon", updatedCustomer.getPetSpecies());
		assertEquals("Ali", updatedCustomer.getPetName());
	}

	@Test
	public void testUpdateVetForCustomer() {
		Vet vet1 = new Vet("NumeDTO", 30, 10, "foo@gmail.com", new ArrayList<>());
		Vet vet2 = new Vet("Andrei", 40, Double.valueOf(20), "foo2@gmail.com", new ArrayList<>());

		CustomerDTO customer = customerMapper
				.mapEntityToDto(new Customer("Adrian", "012345", "Labrador", "Toby", vet1));
		CustomerDTO updatedCustomer = customerMapper
				.mapEntityToDto(new Customer("Adrian", "012345", "Labrador", "Toby", vet2));
		
		vetService.saveVet(vetMapper.mapEntityToDto(vet1));
		vetService.saveVet(vetMapper.mapEntityToDto(vet2));

		when(customerService.saveCustomer(vet1.getId(), customer)).thenReturn(customer);
		when(customerService.updateVetForCustomer(vet2.getId(), customer.getCustomerId(), customer))
				.thenReturn(updatedCustomer);

		customerService.saveCustomer(vet1.getId(), customer);
		updatedCustomer = customerService.updateVetForCustomer(vetMapper.mapEntityToDto(vet2).getId(),
				customer.getCustomerId(), customer);

		verify(customerService).saveCustomer(vet1.getId(), customer);
		verify(customerService).updateVetForCustomer(vetMapper.mapEntityToDto(vet2).getId(), customer.getCustomerId(),
				customer);
		assertEquals(vet2.getName(), updatedCustomer.getVet());
		assertEquals("012345", updatedCustomer.getPhone());
		assertEquals("Labrador", updatedCustomer.getPetSpecies());
		assertEquals("Toby", updatedCustomer.getPetName());
	}

	@Test
	public void testDeleteCustomerById() {
		Vet vet = new Vet();
		Customer customer = new Customer("Adrian", "012345", "Labrador", "Toby", vet);
		CustomerDTO customerDTO = customerService.getCustomerById(Long.valueOf(1));
		
		when(customerService.saveCustomer(vet.getId(), customerDTO)).thenReturn(customerDTO);
		customerService.saveCustomer(vet.getId(), customerDTO);
		customerService.deleteCustomerById(customer.getCustomerId());
		
		verify(customerService).saveCustomer(vet.getId(), customerDTO);
		verify(customerService).deleteCustomerById(customer.getCustomerId());
	}
}




























