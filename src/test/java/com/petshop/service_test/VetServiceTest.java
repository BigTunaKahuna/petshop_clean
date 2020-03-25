package com.petshop.service_test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.petshop.dao.VetDao;
import com.petshop.dto.AuthorityDTO;
import com.petshop.dto.VetDTO;
import com.petshop.mapper.impl.VetMapper;
import com.petshop.models.Vet;
import com.petshop.models.authority.Role;
import com.petshop.service.AuthorityService;
import com.petshop.service.VetService;

@SpringBootTest
public class VetServiceTest {

	@Autowired
	private VetService vetService;
	@MockBean
	private VetDao vetDao;
	@Autowired
	private BCryptPasswordEncoder bcrypt;
	@Autowired
	private VetMapper vetMapper;
	@Autowired
	private AuthorityService authorityService;

	@BeforeEach
	public void addRoles() {
		AuthorityDTO ADMIN = new AuthorityDTO(1L, Role.ADMIN);
		AuthorityDTO USER = new AuthorityDTO(2L, Role.USER);
		authorityService.saveAuthority(ADMIN);
		authorityService.saveAuthority(USER);
	}

	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetVetById() throws InterruptedException, ExecutionException {
		Vet vet = new Vet();
		vet.setId(1L);
		vet.setName("Marius");
		vet.setEmail("foo@gmail.com");
		vet.setPassword(bcrypt.encode("password"));
		vet.setAge(30);
		vet.setYearsOfExperience(6D);
		when(vetDao.getVetById(anyLong())).thenReturn(vet);
		VetDTO vetDTO = vetService.getVetById(1L).join();

		verify(vetDao).getVetById(anyLong());
		assertThat(vetDTO.getId()).isEqualTo(1L);
		assertThat(vetDTO.getName()).isEqualTo("Marius");
		assertThat(vetDTO.getEmail()).isEqualTo("foo@gmail.com");
		assertThat(bcrypt.matches("password", vetDTO.getPassword())).isEqualTo(true);
		assertThat(vetDTO.getAge()).isEqualTo(30);
		assertThat(vetDTO.getYearsOfExperience()).isEqualTo(6D);
		assertThat(vetDTO.getCustomers()).isNullOrEmpty();
	}

	@Test
	public void testGetAllVets() {
		List<Vet> vets = new ArrayList<>();
		Vet vet1 = new Vet();
		vet1.setName("Marius");
		vet1.setEmail("foo@gmail.com");
		vet1.setPassword("password");
		vet1.setAge(30);
		vet1.setYearsOfExperience(6D);

		Vet vet2 = new Vet();
		vet2.setName("Andrei");
		vet2.setEmail("fooUpdate@gmail.com");
		vet2.setPassword("password2");
		vet2.setAge(40);
		vet2.setYearsOfExperience(13D);

		vets.add(vet1);
		vets.add(vet2);

		when(vetDao.getAllVets()).thenReturn(vets);
		List<VetDTO> allVets = vetService.getAllVets();

		assertEquals(2, allVets.size());
		verify(vetDao).getAllVets();
	}

	@Test
	public void testSaveVet() {
		Vet vet = new Vet();
		vet.setId(1L);
		vet.setName("Marius");
		vet.setEmail("foo@gmail.com");
		vet.setPassword(bcrypt.encode("password"));
		vet.setAge(30);
		vet.setYearsOfExperience(6D);
		VetDTO mappedVetDTO = vetMapper.mapEntityToDto(vet);

		when(vetDao.saveVet(any(Vet.class))).thenReturn(vet);

		VetDTO vetDTO = vetService.saveVet(mappedVetDTO);

		verify(vetDao).saveVet(any(Vet.class));
		assertThat(vetDTO.getId()).isEqualTo(1L);
		assertThat(vetDTO.getName()).isEqualTo("Marius");
		assertThat(vetDTO.getEmail()).isEqualTo("foo@gmail.com");
		assertThat(bcrypt.matches("password", vetDTO.getPassword())).isEqualTo(true);
		assertThat(vetDTO.getAge()).isEqualTo(30);
		assertThat(vetDTO.getYearsOfExperience()).isEqualTo(6);
		assertThat(vetDTO.getCustomers()).isNullOrEmpty();

	}

	@Test
	public void testUpdateVet() {
		Vet vet = new Vet();
		vet.setId(1L);
		vet.setName("Marius");
		vet.setEmail("foo@gmail.com");
		vet.setPassword(bcrypt.encode("password"));
		vet.setAge(30);
		vet.setYearsOfExperience(6D);
		
		VetDTO mappedVetDTO = vetMapper.mapEntityToDto(vet);
		
		when(vetDao.updateVet(anyLong(), any(Vet.class))).thenReturn(vet);
		VetDTO vetDTO = vetService.updateVet(vet.getId(), mappedVetDTO);

		verify(vetDao).updateVet(anyLong(), any(Vet.class));
		assertThat(vetDTO.getId()).isEqualTo(1L);
		assertThat(vetDTO.getName()).isEqualTo("Marius");
		assertThat(vetDTO.getEmail()).isEqualTo("foo@gmail.com");
		assertThat(bcrypt.matches("password", vetDTO.getPassword())).isEqualTo(true);
		assertThat(vetDTO.getAge()).isEqualTo(30);
		assertThat(vetDTO.getYearsOfExperience()).isEqualTo(6D);
		assertThat(vetDTO.getCustomers()).isNullOrEmpty();
	}

	@Test
	public void testDeleteVetById() {

		vetService.deleteVetById(anyLong());

		verify(vetDao).deleteVetById(anyLong());
	}

}
