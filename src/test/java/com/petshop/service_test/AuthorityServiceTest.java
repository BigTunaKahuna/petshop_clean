package com.petshop.service_test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.petshop.dao.AuthorityDao;
import com.petshop.dao.CustomerDao;
import com.petshop.dao.VetDao;
import com.petshop.dto.AuthorityDTO;
import com.petshop.dto.CustomerWithRolesDTO;
import com.petshop.dto.VetWithRolesDTO;
import com.petshop.exception.RoleNotFoundException;
import com.petshop.mapper.impl.AuthorityMapper;
import com.petshop.models.Customer;
import com.petshop.models.Vet;
import com.petshop.models.authority.Authority;
import com.petshop.models.authority.Role;
import com.petshop.service.AuthorityService;
import com.petshop.service.CustomerService;
import com.petshop.service.VetService;

@SpringBootTest
public class AuthorityServiceTest {
	@Autowired
	private AuthorityService authorityService;
	@Autowired
	private BCryptPasswordEncoder bcrypt;
	@MockBean
	private AuthorityDao authorityDao;
	@Autowired
	private AuthorityMapper authorityMapper;
	@MockBean
	private VetService vetService;
	@MockBean
	private VetDao vetDao;
	@MockBean
	private CustomerService customerService;
	@MockBean
	private CustomerDao customerDao;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetAllRoles() throws Exception {
		List<Authority> authorities = new ArrayList<>();
		Authority auth1 = new Authority(Role.ADMIN);
		Authority auth2 = new Authority(Role.USER);
		authorities.add(auth1);
		authorities.add(auth2);
		when(authorityDao.getAllRoles()).thenReturn(authorities);

		List<AuthorityDTO> testAuth = authorityService.getAllRoles();
		verify(authorityDao).getAllRoles();

		assertEquals(2, testAuth.size());
	}

	@Test
	public void testSaveAuthority() throws Exception {
		Authority authority = new Authority(Role.ADMIN);
		AuthorityDTO authorityHolderDTO = authorityMapper.mapEntityToDto(authority);

		when(authorityDao.saveAuthority(any(Authority.class))).thenReturn(authority);
		AuthorityDTO authorityDTO = authorityService.saveAuthority(authorityHolderDTO);

		verify(authorityDao).saveAuthority(any(Authority.class));
		assertEquals(authorityDTO.getId(), authorityHolderDTO.getId());
		assertEquals(authorityDTO.getRole(), authorityDTO.getRole());
	}

	@Test
	public void testChangeRoleOfVet() throws Exception {
		Authority oldAuth = new Authority(Role.ADMIN);
		Authority newAuth = new Authority(Role.USER);
		VetWithRolesDTO vet = new VetWithRolesDTO();
		vet.setId(1L);
		vet.setName("Marius");
		vet.setEmail("foo@gmail.com");
		vet.setPassword(bcrypt.encode("password"));
		vet.setAge(30);
		vet.setYearsOfExperience(6D);
		vet.addRole(oldAuth);

		when(vetService.findVetById(anyLong())).thenReturn(vet);
		when(authorityDao.findByRole(any(Role.class))).thenReturn(newAuth);
		doNothing().when(vetDao).saveVetAndFlush(any(Vet.class));
		authorityService.changeRoleOfVet(vet.getId(), Role.ADMIN, Role.USER);

		verify(vetService).findVetById(anyLong());
		verify(authorityDao).findByRole(Role.USER);
		verify(vetDao).saveVetAndFlush(any(Vet.class));

		assertEquals(Role.USER, vet.getRoles().stream().map(x -> {
			if (x.getRole().equals(Role.USER))
				return Role.USER;
			throw new RoleNotFoundException();
		}).findAny().get());
	}

	@Test
	public void testChangeRoleOfCustomer() throws Exception {
		Authority newAuth = new Authority(Role.ADMIN);
		Authority oldAuth = new Authority(Role.USER);
		CustomerWithRolesDTO customer = new CustomerWithRolesDTO();
		customer.setId(1L);
		customer.setName("Rares");
		customer.setEmail("foo@gmail.com");
		customer.setPassword("password");
		customer.setPhone("1234567890");
		customer.setPetSpecies("Labrador");
		customer.setPetName("Toby");
		customer.addRole(oldAuth);

		when(customerService.getCustomerWithRolesById(anyLong())).thenReturn(customer);
		when(authorityDao.findByRole(any(Role.class))).thenReturn(newAuth);
		doNothing().when(customerDao).saveCustomerAndFlush(any(Customer.class));

		authorityService.changeRoleOfCustomer(customer.getId(), Role.USER, Role.ADMIN);

		verify(customerService).getCustomerWithRolesById(anyLong());
		verify(authorityDao).findByRole(Role.ADMIN);
		verify(customerDao).saveCustomerAndFlush(any(Customer.class));

		assertEquals(Role.ADMIN, customer.getRoles().stream().map(x -> {
			if (x.getRole().equals(Role.ADMIN))
				return Role.ADMIN;
			throw new RoleNotFoundException();
		}).findAny().get());
	}

	@Test
	public void testDeleteRoleForVet() throws Exception {
		Set<Authority> emptyAuth = new HashSet<>();
		Authority auth = new Authority(Role.ADMIN);
		Vet vet = new Vet();
		vet.setId(1L);
		vet.setName("Marius");
		vet.setEmail("foo@gmail.com");
		vet.setPassword(bcrypt.encode("password"));
		vet.setAge(30);
		vet.setYearsOfExperience(6D);
		vet.addRole(auth);

		when(authorityDao.findByRole(any(Role.class))).thenReturn(auth);
		when(vetDao.getVetById(anyLong())).thenReturn(vet);
		doNothing().when(vetDao).saveVetAndFlush(any(Vet.class));

		authorityService.deleteRoleForVet(vet.getId(), Role.ADMIN);

		verify(authorityDao).findByRole(any(Role.class));
		verify(vetDao).getVetById(anyLong());
		verify(vetDao).saveVetAndFlush(any(Vet.class));

		assertEquals(vet.getRoles(), emptyAuth);
	}

	@Test
	public void testDeleteRoleForCustomer() throws Exception {
		Set<Authority> emptyAuth = new HashSet<>();
		Authority auth = new Authority(Role.USER);
		Customer customer = new Customer();
		customer.setId(1L);
		customer.setName("Rares");
		customer.setEmail("foo@gmail.com");
		customer.setPassword("password");
		customer.setPhone("1234567890");
		customer.setPetSpecies("Labrador");
		customer.setPetName("Toby");
		customer.addRole(auth);

		when(authorityDao.findByRole(any(Role.class))).thenReturn(auth);
		when(customerDao.getCustomerById(anyLong())).thenReturn(customer);
		doNothing().when(customerDao).saveCustomerAndFlush(customer);

		authorityService.deleteRoleForCustomer(customer.getId(), Role.USER);

		verify(authorityDao).findByRole(any(Role.class));
		verify(customerDao).getCustomerById(anyLong());
		verify(customerDao).saveCustomerAndFlush(any(Customer.class));
		assertEquals(customer.getRole(), emptyAuth);
	}

	@Test
	public void testChangeRoleOfVetError() throws Exception {
		Authority oldAuth = new Authority(Role.ADMIN);
		VetWithRolesDTO vet = new VetWithRolesDTO();
		vet.setId(1L);
		vet.setName("Marius");
		vet.setEmail("foo@gmail.com");
		vet.setPassword(bcrypt.encode("password"));
		vet.setAge(30);
		vet.setYearsOfExperience(6D);
		vet.addRole(oldAuth);

		when(vetService.findVetById(anyLong())).thenReturn(vet);
		when(authorityDao.findByRole(any(Role.class))).thenReturn(null);

		assertThrows(RoleNotFoundException.class,
				() -> authorityService.changeRoleOfVet(vet.getId(), Role.ADMIN, Role.USER));
	}

	@Test
	public void testChangeRoleOfCustomerError() throws Exception {
		Authority oldAuth = new Authority(Role.USER);
		CustomerWithRolesDTO customer = new CustomerWithRolesDTO();
		customer.setId(1L);
		customer.setName("Rares");
		customer.setEmail("foo@gmail.com");
		customer.setPassword("password");
		customer.setPhone("1234567890");
		customer.setPetSpecies("Labrador");
		customer.setPetName("Toby");
		customer.addRole(oldAuth);

		when(customerService.getCustomerWithRolesById(anyLong())).thenReturn(customer);
		when(authorityDao.findByRole(any(Role.class))).thenReturn(null);
		doNothing().when(customerDao).saveCustomerAndFlush(any(Customer.class));

		assertThrows(RoleNotFoundException.class,
				() -> authorityService.changeRoleOfCustomer(customer.getId(), Role.USER, Role.ADMIN));
	}

	@Test
	public void testDeleteRoleForVetError() throws Exception {
		Authority auth = new Authority(Role.ADMIN);
		Authority USER = new Authority(Role.USER);
		Vet vet = new Vet();
		vet.setId(1L);
		vet.setName("Marius");
		vet.setEmail("foo@gmail.com");
		vet.setPassword(bcrypt.encode("password"));
		vet.setAge(30);
		vet.setYearsOfExperience(6D);
		vet.addRole(auth);

		when(authorityDao.findByRole(Role.ADMIN)).thenReturn(USER);
		when(vetDao.getVetById(anyLong())).thenReturn(vet);
		doNothing().when(vetDao).saveVetAndFlush(any(Vet.class));

		assertThrows(RoleNotFoundException.class, () -> authorityService.deleteRoleForVet(vet.getId(), Role.USER));
	}

	@Test
	public void testDeleteRoleForCustomerError() throws Exception {
		Authority auth = new Authority(Role.USER);
		Authority ADMIN = new Authority(Role.ADMIN);
		Customer customer = new Customer();
		customer.setId(1L);
		customer.setName("Rares");
		customer.setEmail("foo@gmail.com");
		customer.setPassword("password");
		customer.setPhone("1234567890");
		customer.setPetSpecies("Labrador");
		customer.setPetName("Toby");
		customer.addRole(auth);

		when(authorityDao.findByRole(any(Role.class))).thenReturn(ADMIN);
		when(customerDao.getCustomerById(anyLong())).thenReturn(customer);

		assertThrows(RoleNotFoundException.class,
				() -> authorityService.deleteRoleForCustomer(customer.getId(), Role.USER));
	}

}
