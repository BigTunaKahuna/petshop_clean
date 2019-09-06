package com.petshop.service_test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.BDDMockito.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.petshop.dao.VetDao;
import com.petshop.dto.VetDTO;
import com.petshop.models.Vet;
import com.petshop.service.VetService;

@SpringBootTest
public class VetServiceTest {

	@Autowired
	private VetService vetService;
	@MockBean
	private VetDao vetDao;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetVetById() {
		Vet vet = new Vet(1L, "NumeDTO", 30, 10, "foo@gmail.com", new ArrayList<>());

		when(vetDao.getVetById(anyLong())).thenReturn(vet);
		VetDTO vetDTO = vetService.getVetById(anyLong());

		verify(vetDao).getVetById(anyLong());
		assertThat(vetDTO.getId()).isEqualTo(1L);
		assertThat(vetDTO.getName()).isEqualTo("NumeDTO");
		assertThat(vetDTO.getAge()).isEqualTo(30);
		assertThat(vetDTO.getYearsOfExperience()).isEqualTo(10);
		assertThat(vetDTO.getEmail()).isEqualTo("foo@gmail.com");
		assertThat(vetDTO.getCustomers()).isNullOrEmpty();
	}

	@Test
	public void testGetAllVets() {
		List<Vet> vets = new ArrayList<>();
		Vet vet1 = new Vet(1L, "Marius", 25, Double.valueOf(3), "foo@gmail.com", new ArrayList<>());
		Vet vet2 = new Vet(2L, "Andrei", 40, Double.valueOf(20), "foo2@gmail.com", new ArrayList<>());

		vets.add(vet1);
		vets.add(vet2);

		when(vetDao.getAllVets()).thenReturn(vets);
		List<VetDTO> allVets = vetService.getAllVets();

		assertEquals(2, allVets.size());
		verify(vetDao).getAllVets();
	}

	@Test
	public void testSaveVet() {
		Vet vet = new Vet(Long.valueOf(1), "NumeDTO", 30, 10, "foo@gmail.com", new ArrayList<>());

		when(vetDao.saveVet(any(Vet.class))).thenReturn(vet);
		VetDTO vetDTO = vetService.saveVet(any(VetDTO.class));

		verify(vetDao).saveVet(any(Vet.class));
		assertThat(vetDTO.getId()).isEqualTo(1L);
		assertThat(vetDTO.getName()).isEqualTo("NumeDTO");
		assertThat(vetDTO.getAge()).isEqualTo(30);
		assertThat(vetDTO.getYearsOfExperience()).isEqualTo(10);
		assertThat(vetDTO.getEmail()).isEqualTo("foo@gmail.com");
		assertThat(vetDTO.getCustomers()).isNullOrEmpty();

	}

	@Test
	public void testUpdateVet() {
		Vet vet = new Vet(1L, "Andrei", 40, 20D, "foo2@gmail.com", new ArrayList<>());

		when(vetDao.updateVet(anyLong(), any(Vet.class))).thenReturn(vet);
		VetDTO vetDTO = vetService.updateVet(anyLong(), any(VetDTO.class));

		verify(vetDao).updateVet(anyLong(), any(Vet.class));
		assertThat(vetDTO.getId()).isEqualTo(1L);
		assertThat(vetDTO.getName()).isEqualTo("Andrei");
		assertThat(vetDTO.getAge()).isEqualTo(40);
		assertThat(vetDTO.getYearsOfExperience()).isEqualTo(20D);
		assertThat(vetDTO.getEmail()).isEqualTo("foo2@gmail.com");
		assertThat(vetDTO.getCustomers()).isNullOrEmpty();
	}

	@Test
	public void testDeleteVetById() {

		vetService.deleteVetById(anyLong());

		verify(vetDao).deleteVetById(anyLong());
	}

}
