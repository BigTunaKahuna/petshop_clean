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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.petshop.dao.CustomerDao;
import com.petshop.dto.AuthorityDTO;
import com.petshop.dto.CustomerDTO;
import com.petshop.exception.IdNotFoundException;
import com.petshop.mapper.impl.CustomerMapper;
import com.petshop.models.Customer;
import com.petshop.models.Vet;
import com.petshop.models.authority.Role;
import com.petshop.service.AuthorityService;
import com.petshop.service.CustomerService;

import static org.mockito.BDDMockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

	@Autowired
	private CustomerService customerService;
	@MockBean
	private CustomerDao customerDao;
	@Autowired
	private AuthorityService authorityService;
	@Autowired
	private BCryptPasswordEncoder bcrypt;
	@Autowired
	private CustomerMapper customerMapper;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
		AuthorityDTO ADMIN = new AuthorityDTO(1L, Role.ADMIN);
		AuthorityDTO USER = new AuthorityDTO(2L, Role.USER);
		authorityService.saveAuthority(ADMIN);
		authorityService.saveAuthority(USER);
	}

	public Customer initCustomer() {
		Customer customer = new Customer();
		customer.setId(1L);
		customer.setName("Rares");
		customer.setEmail("foo@gmail.com");
		customer.setPassword(bcrypt.encode("password"));
		customer.setPhone("1234567890");
		customer.setPetSpecies("Labrador");
		customer.setPetName("Toby");
		return customer;
	}

	@Test
	public void testGetCustomerById() throws IdNotFoundException {
		Customer customer = initCustomer();

		when(customerDao.getCustomerById(anyLong())).thenReturn(customer);

		CustomerDTO customerDTO = customerService.getCustomerById(anyLong());

		verify(customerDao).getCustomerById(anyLong());
		assertThat(customerDTO.getId()).isEqualTo(1L);
		assertThat(customerDTO.getName()).isEqualTo("Rares");
		assertThat(customerDTO.getEmail()).isEqualTo("foo@gmail.com");
		assertThat(bcrypt.matches("password", customerDTO.getPassword())).isEqualTo(true);
		assertThat(customerDTO.getPhone()).isEqualTo("1234567890");
		assertThat(customerDTO.getPetSpecies()).isEqualTo("Labrador");
		assertThat(customerDTO.getPetName()).isEqualTo("Toby");
		assertThat(customerDTO.getVet()).isNullOrEmpty();
	}

	@Test
	public void testGetAllCustomers() {
		List<Customer> allCustomers = new ArrayList<>();
		Customer customer1 = initCustomer();
		Customer customer2 = initCustomer();

		allCustomers.add(customer1);
		allCustomers.add(customer2);

		when(customerDao.getAllCustomers()).thenReturn(allCustomers);

		List<CustomerDTO> retrieveAllCustomers = customerService.getAllCustomers();

		verify(customerDao).getAllCustomers();
		assertEquals(2, retrieveAllCustomers.size());
	}

	@Test
	public void testSaveCustomer() {
		Customer customer = initCustomer();

		CustomerDTO mapperCustomer = customerMapper.mapEntityToDto(customer);

		when(customerDao.saveCustomer(anyLong(), any(Customer.class))).thenReturn(customer);
		CustomerDTO customerDTO = customerService.saveCustomer(anyLong(), mapperCustomer);

		verify(customerDao).saveCustomer(anyLong(), any(Customer.class));
		assertThat(customerDTO.getId()).isEqualTo(1L);
		assertThat(customerDTO.getName()).isEqualTo("Rares");
		assertThat(customerDTO.getEmail()).isEqualTo("foo@gmail.com");
		assertThat(bcrypt.matches("password", customerDTO.getPassword())).isEqualTo(true);
		assertThat(customerDTO.getPhone()).isEqualTo("1234567890");
		assertThat(customerDTO.getPetSpecies()).isEqualTo("Labrador");
		assertThat(customerDTO.getPetName()).isEqualTo("Toby");
		assertThat(customerDTO.getVet()).isNullOrEmpty();
	}

	@Test
	public void testUpdateCustomer() {
		Customer customer = initCustomer();

		CustomerDTO mapperCustomer = customerMapper.mapEntityToDto(customer);

		when(customerDao.updateCustomer(anyLong(), any(Customer.class))).thenReturn(customer);
		CustomerDTO customerDTO = customerService.updateCustomer(customer.getId(), mapperCustomer);

		verify(customerDao).updateCustomer(anyLong(), any(Customer.class));
		assertThat(customerDTO.getId()).isEqualTo(1L);
		assertThat(customerDTO.getName()).isEqualTo("Rares");
		assertThat(customerDTO.getEmail()).isEqualTo("foo@gmail.com");
		assertThat(bcrypt.matches("password", customerDTO.getPassword())).isEqualTo(true);
		assertThat(customerDTO.getPhone()).isEqualTo("1234567890");
		assertThat(customerDTO.getPetSpecies()).isEqualTo("Labrador");
		assertThat(customerDTO.getPetName()).isEqualTo("Toby");
		assertThat(customerDTO.getVet()).isNullOrEmpty();
	}

	@Test
	public void testUpdateVetForCustomer() {
		Vet vet = new Vet();
		vet.setName("Iulica");
		Customer customer = initCustomer();
		customer.setVet(vet);

		when(customerDao.updateVetCustomer(anyLong(), anyLong(), any(Customer.class))).thenReturn(customer);
		CustomerDTO customerDTO = customerService.updateVetForCustomer(anyLong(), anyLong(), any(CustomerDTO.class));

		verify(customerDao).updateVetCustomer(anyLong(), anyLong(), any(Customer.class));
		assertThat(customerDTO.getId()).isEqualTo(1L);
		assertThat(customerDTO.getName()).isEqualTo("Rares");
		assertThat(customerDTO.getEmail()).isEqualTo("foo@gmail.com");
		assertThat(bcrypt.matches("password", customerDTO.getPassword())).isEqualTo(true);
		assertThat(customerDTO.getPhone()).isEqualTo("1234567890");
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
