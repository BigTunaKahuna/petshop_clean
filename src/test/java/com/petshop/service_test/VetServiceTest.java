package com.petshop.service_test;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import org.springframework.test.context.TestPropertySource;

import com.petshop.dto.VetDTO;
import com.petshop.mapper.VetMapper;
import com.petshop.models.Customer;
import com.petshop.models.Vet;
import com.petshop.service.VetService;

@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
public class VetServiceTest {

	@Mock
	VetService vetService;
	@Autowired
	VetMapper vetMapper;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetVetById() {
		when(vetService.getVetById(Long.valueOf(1))).thenReturn(vetMapper
				.mapEntityToDto(new Vet(Long.valueOf(1), "NumeDTO", 30, 10, "foo@gmail.com", new ArrayList<>())));
		VetDTO vetDTO = vetService.getVetById(Long.valueOf(1));

		List<Customer> emptyCustomerList = new ArrayList<>();
		assertEquals(Long.valueOf(1), vetDTO.getId());
		assertEquals("NumeDTO", vetDTO.getName());
		assertEquals(Integer.valueOf(30), vetDTO.getAge());
		assertEquals(Double.valueOf(10), vetDTO.getYearsOfExperience());
		assertEquals("foo@gmail.com", vetDTO.getEmail());
		assertEquals(emptyCustomerList, vetDTO.getCustomers());
	}

	@Test
	public void testGetAllVets() {
		List<Vet> vets = new ArrayList<>();
		Vet vet1 = new Vet(Long.valueOf(1), "Marius", 25, Double.valueOf(3), "foo@gmail.com", new ArrayList<>());
		Vet vet2 = new Vet(Long.valueOf(2), "Andrei", 40, Double.valueOf(20), "foo2@gmail.com", new ArrayList<>());

		vets.add(vet1);
		vets.add(vet2);

		List<VetDTO> vetDTO = new ArrayList<>();
		for (var vet : vets) {
			vetDTO.add(vetMapper.mapEntityToDto(vet));
		}

		when(vetService.getAllVets()).thenReturn(vetDTO);
		var vetList = vetService.getAllVets();

		assertEquals(2, vetList.size());
		verify(vetService).getAllVets();
	}

	@Test
	public void testSaveVet() {
		Vet vet = new Vet(Long.valueOf(1), "NumeDTO", 30, 10, "foo@gmail.com", new ArrayList<>());
		VetDTO vetDTO = vetMapper.mapEntityToDto(vet);

		when(vetService.saveVet(vetDTO)).thenReturn(vetDTO);
		vetService.saveVet(vetDTO);
		verify(vetService).saveVet(vetDTO);

	}

	@Test
	public void testUpdateVet() {
		VetDTO vetDTO1 = vetMapper.mapEntityToDto(new Vet("NumeDTO", 30, 10, "foo@gmail.com", new ArrayList<>()));
		VetDTO vetDTO2 = vetMapper
				.mapEntityToDto(new Vet("Andrei", 40, Double.valueOf(20), "foo2@gmail.com", new ArrayList<>()));

		when(vetService.saveVet(vetDTO1)).thenReturn(vetDTO1);
		vetService.saveVet(vetDTO1);
		verify(vetService).saveVet(vetDTO1);

		when(vetService.updateVet(vetDTO1.getId(), vetDTO2)).thenReturn(vetDTO2);
		VetDTO updatedVet = vetService.updateVet(vetDTO1.getId(), vetDTO2);
		verify(vetService).updateVet(vetDTO1.getId(), vetDTO2);

		assertEquals(updatedVet.getId(), vetDTO2.getId());
		assertEquals(updatedVet.getName(), vetDTO2.getName());
		assertEquals(updatedVet.getAge(), vetDTO2.getAge());
		assertEquals(updatedVet.getYearsOfExperience(), vetDTO2.getYearsOfExperience());
		assertEquals(updatedVet.getEmail(), vetDTO2.getEmail());
		assertEquals(updatedVet.getCustomers(), vetDTO2.getCustomers());
	}

	@Test
	public void testDeleteVetById() {
		Vet vet = new Vet(Long.valueOf(1), "NumeDTO", 30, 10, "foo@gmail.com", new ArrayList<>());
		VetDTO vetDTO = vetMapper.mapEntityToDto(vet);

		when(vetService.saveVet(vetDTO)).thenReturn(vetDTO);
		vetService.saveVet(vetDTO);
		vetService.deleteVetById(vetDTO.getId());

		verify(vetService).saveVet(vetDTO);
		verify(vetService).deleteVetById(vetDTO.getId());
	}

}
