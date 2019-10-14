package com.petshop.service_test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

import com.petshop.dao.AuthorityDao;
import com.petshop.dao.CustomerDao;
import com.petshop.dao.VetDao;
import com.petshop.dto.CustomerDTO;
import com.petshop.dto.CustomerWithRolesDTO;
import com.petshop.exception.EmailAlreadyExistsException;
import com.petshop.exception.IdNotFoundException;
import com.petshop.exception.RoleNotFoundException;
import com.petshop.mapper.impl.CustomerMapper;
import com.petshop.models.Customer;
import com.petshop.models.Vet;
import com.petshop.models.authority.Authority;
import com.petshop.models.authority.Role;
import com.petshop.service.CustomerService;

import static org.mockito.BDDMockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

	@Autowired
	private CustomerService customerService;
	@MockBean
	private CustomerDao customerDao;
	@MockBean
	private AuthorityDao authorityDao;
	@Autowired
	private BCryptPasswordEncoder bcrypt;
	@Autowired
	private CustomerMapper customerMapper;
	@MockBean
	private VetDao vetDao;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
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
	public void testGetCustomerWithRolesDTO() throws Exception {
		Customer customer = initCustomer();

		when(customerDao.getCustomerById(anyLong())).thenReturn(customer);

		CustomerWithRolesDTO customerWithRolesDTO = customerService.getCustomerWithRolesById(anyLong());

		verify(customerDao).getCustomerById(anyLong());
		assertThat(customerWithRolesDTO.getId()).isEqualTo(1L);
		assertThat(customerWithRolesDTO.getName()).isEqualTo("Rares");
		assertThat(customerWithRolesDTO.getEmail()).isEqualTo("foo@gmail.com");
		assertThat(bcrypt.matches("password", customerWithRolesDTO.getPassword())).isEqualTo(true);
		assertThat(customerWithRolesDTO.getPhone()).isEqualTo("1234567890");
		assertThat(customerWithRolesDTO.getPetSpecies()).isEqualTo("Labrador");
		assertThat(customerWithRolesDTO.getPetName()).isEqualTo("Toby");

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
		Authority authority = new Authority(Role.USER);

		CustomerDTO mapperCustomer = customerMapper.mapEntityToDto(customer);

		when(authorityDao.findByRole(any(Role.class))).thenReturn(authority);
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
		vet.setId(1L);
		vet.setName("Marius");
		vet.setEmail("foo@gmail.com");
		vet.setPassword(bcrypt.encode("password"));
		vet.setAge(30);
		vet.setYearsOfExperience(6D);
		Customer customer = initCustomer();
		customer.setVet(vet);

		when(vetDao.getVetById(anyLong())).thenReturn(vet);
		when(customerDao.getCustomerById(anyLong())).thenReturn(customer);
		when(customerDao.saveCustomer(any(Customer.class))).thenReturn(customer);
		doNothing().when(customerDao).deleteCustomerById(anyLong());

		CustomerWithRolesDTO customerDTO = customerService.updateVetForCustomer(vet.getId(), customer.getId());

		verify(vetDao).getVetById(anyLong());
		verify(customerDao).saveCustomer(any(Customer.class));
		verify(customerDao).getCustomerById(anyLong());
		verify(customerDao).deleteCustomerById(anyLong());

		assertThat(customerDTO.getId()).isEqualTo(1L);
		assertThat(customerDTO.getName()).isEqualTo("Rares");
		assertThat(customerDTO.getEmail()).isEqualTo("foo@gmail.com");
		assertThat(bcrypt.matches("password", customerDTO.getPassword())).isEqualTo(true);
		assertThat(customerDTO.getPhone()).isEqualTo("1234567890");
		assertThat(customerDTO.getPetSpecies()).isEqualTo("Labrador");
		assertThat(customerDTO.getPetName()).isEqualTo("Toby");
		assertThat(customerDTO.getVet().getName()).isEqualTo("Marius");
	}

	@Test
	public void testDeleteCustomerById() {
		doNothing().when(customerDao).deleteCustomerById(anyLong());
		customerService.deleteCustomerById(anyLong());

		verify(customerDao).deleteCustomerById(anyLong());
	}

	@Test
	public void testSaveCustomerEmailAlreadyExistsError() throws Exception {
		Customer customer = initCustomer();

		CustomerDTO mappedCustomer = customerMapper.mapEntityToDto(customer);

		when(customerDao.checkEmail(anyString())).thenReturn(true);
		when(customerDao.saveCustomer(anyLong(), any(Customer.class))).thenReturn(customer);

		assertThrows(EmailAlreadyExistsException.class, () -> customerService.saveCustomer(anyLong(), mappedCustomer));
	}

	@Test
	public void testSaveCustomerRoleIsNullError() throws Exception {
		Customer customer = initCustomer();

		CustomerDTO mappedCustomer = customerMapper.mapEntityToDto(customer);

		when(customerDao.saveCustomer(anyLong(), any(Customer.class))).thenReturn(customer);

		assertThrows(RoleNotFoundException.class, () -> customerService.saveCustomer(anyLong(), mappedCustomer));
	}

	@Test
	public void testDeleteCustomerByIdThrowError() throws Exception {
		doThrow(IdNotFoundException.class).when(customerDao).deleteCustomerById(anyLong());

		assertThrows(IdNotFoundException.class, () -> customerService.deleteCustomerById(anyLong()));
	}
}
