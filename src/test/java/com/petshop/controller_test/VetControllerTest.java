package com.petshop.controller_test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.petshop.dto.VetDTO;
import com.petshop.http_errors.IdNotFoundException;
import com.petshop.mapper.VetMapper;
import com.petshop.models.Customer;
import com.petshop.models.Vet;
import com.petshop.service.impl.VetServiceImpl;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class VetControllerTest {

	ObjectMapper mapper = new ObjectMapper();

	@Autowired
	private MockMvc mvc;
	@MockBean
	private VetServiceImpl vetService;
	@Autowired
	private VetMapper vetMapper;

	@Test
	public void getVetByIdTest() throws Exception {
		Vet vet = new Vet(Long.valueOf(1), "Marius", 25, Double.valueOf(3), "foo@gmail.com", new ArrayList<>());
		VetDTO vetDTO = vetMapper.mapEntityToDto(vet);

		Customer customer = new Customer(Long.valueOf(1), "Adrian", "012345", "Labrador", "Toby", vet);
		List<Customer> allCustomers = new ArrayList<>();
		allCustomers.add(customer);
		vetDTO.setCustomers(allCustomers);

		given(vetService.getVetById(vetDTO.getId())).willReturn(vetDTO);

		this.mvc.perform(get("/vet/1").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())

				// Checking the existence of VetJSON
				.andExpect(jsonPath("$.id").exists()).andExpect(jsonPath("$.name").exists())
				.andExpect(jsonPath("$.age").exists()).andExpect(jsonPath("$.yearsOfExperience").exists())
				.andExpect(jsonPath("$.email").exists()).andExpect(jsonPath("$.customers").exists())
				.andExpect(jsonPath("$.customers[0].id").exists()).andExpect(jsonPath("$.customers[0].name").exists())
				.andExpect(jsonPath("$.customers[0].phone").exists())
				.andExpect(jsonPath("$.customers[0].petSpecies").exists())
				.andExpect(jsonPath("$.customers[0].petName").exists())

				// Checking the value types of VetJSON
				.andExpect(jsonPath("$.id").isNumber()).andExpect(jsonPath("$.name").isString())
				.andExpect(jsonPath("$.age").isNumber()).andExpect(jsonPath("$.yearsOfExperience").isNumber())
				.andExpect(jsonPath("$.email").isString()).andExpect(jsonPath("$.customers").isArray())
				.andExpect(jsonPath("$.customers[0].id").isNumber())
				.andExpect(jsonPath("$.customers[0].name").isString())
				.andExpect(jsonPath("$.customers[0].phone").isString())
				.andExpect(jsonPath("$.customers[0].petSpecies").isString())
				.andExpect(jsonPath("$.customers[0].petName").isString())

				// Checking the VetJSON values
				.andExpect(jsonPath("$.id").value(1)).andExpect(jsonPath("$.name").value("Marius"))
				.andExpect(jsonPath("$.age").value(25)).andExpect(jsonPath("$.yearsOfExperience").value(3))
				.andExpect(jsonPath("$.email").value("foo@gmail.com")).andExpect(jsonPath("$.customers[0].id").value(1))
				.andExpect(jsonPath("$.customers[0].name").value("Adrian"))
				.andExpect(jsonPath("$.customers[0].phone").value("012345"))
				.andExpect(jsonPath("$.customers[0].petSpecies").value("Labrador"))
				.andExpect(jsonPath("$.customers[0].petName").value("Toby"));
	}

	@Test
	public void testGetAllVets() throws Exception {
		Vet vet1 = new Vet(Long.valueOf(1), "Marius", 25, Double.valueOf(3), "foo@gmail.com", new ArrayList<>());
		Vet vet2 = new Vet(Long.valueOf(2), "Radu", 50, Double.valueOf(25), "foo2@gmail.com", new ArrayList<>());
		VetDTO vetDTO1 = vetMapper.mapEntityToDto(vet1);
		VetDTO vetDTO2 = vetMapper.mapEntityToDto(vet2);
		List<VetDTO> allVets = new ArrayList<>();
		allVets.add(vetDTO1);
		allVets.add(vetDTO2);

		given(vetService.getAllVets()).willReturn(allVets);

		this.mvc.perform(get("/vet/all").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$").isArray())

				// Checking the first VetJSON
				.andExpect(jsonPath("$[0].id").value(1)).andExpect(jsonPath("$[0].name").value("Marius"))
				.andExpect(jsonPath("$[0].age").value(25)).andExpect(jsonPath("$[0].yearsOfExperience").value(3))
				.andExpect(jsonPath("$[0].email").value("foo@gmail.com"))

				// Checking the seconds VetJSON
				.andExpect(jsonPath("$[1].id").value(2)).andExpect(jsonPath("$[1].name").value("Radu"))
				.andExpect(jsonPath("$[1].age").value(50)).andExpect(jsonPath("$[1].yearsOfExperience").value(25))
				.andExpect(jsonPath("$[1].email").value("foo2@gmail.com"));
	}

	@Test
	public void testSaveVet() throws Exception {
		Vet vet = new Vet(Long.valueOf(1), "Marius", 25, Double.valueOf(3), "foo@gmail.com", new ArrayList<>());
		VetDTO vetDTO = vetMapper.mapEntityToDto(vet);

		given(vetService.saveVet(any(VetDTO.class))).willReturn(vetDTO);

		this.mvc.perform(post("/vet").content(mapper.writeValueAsString(vetDTO)).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())

				// Checking VetJSON values
				.andExpect(jsonPath("$.id").value(Long.valueOf(1)))
				.andExpect(jsonPath("$.name").value("Marius"))
				.andExpect(jsonPath("$.age").value(25))
				.andExpect(jsonPath("$.yearsOfExperience").value(3))
				.andExpect(jsonPath("$.email").value("foo@gmail.com"));
	}

	@Test
	public void testUpdateVet() throws Exception {
		Vet vet = new Vet(Long.valueOf(2), "Radu", 50, Double.valueOf(25), "foo2@gmail.com", new ArrayList<>());
		VetDTO vetDTO2 = vetMapper.mapEntityToDto(vet);

		given(vetService.updateVet(anyLong(), any(VetDTO.class))).willReturn(vetDTO2);

		this.mvc.perform(put("/vet/1").content(mapper.writeValueAsString(vetDTO2))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").value(Long.valueOf(2)))
				.andExpect(jsonPath("$.name").value("Radu"))
				.andExpect(jsonPath("$.age").value(50))
				.andExpect(jsonPath("$.yearsOfExperience").value(25))
				.andExpect(jsonPath("$.email").value("foo2@gmail.com"));
	}

	@Test
	public void testDeleteVetById() throws Exception {
		this.mvc.perform(delete("/vet/1")).andExpect(status().isOk());

		String content = this.mvc.perform(delete("/vet/1")).andReturn().getResponse().getContentAsString();
		assertEquals(content, "The vet was deleted succesfully!");
	}

	@Test
	public void testIdNotFoundException() throws Exception, IdNotFoundException {
		doThrow(new IdNotFoundException()).when(vetService).getVetById(anyLong());
		this.mvc.perform(get("/vet/2")).andExpect(status().isNotFound());
	}

}
