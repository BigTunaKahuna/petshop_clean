package com.petshop.controller_test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.petshop.dto.VetDTO;
import com.petshop.exception.IdNotFoundException;
import com.petshop.mapper.impl.VetMapper;
import com.petshop.models.Customer;
import com.petshop.models.Vet;
import com.petshop.service.impl.VetServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class VetControllerTest {

	@Autowired
	ObjectMapper mapper ;
	@Autowired
	private MockMvc mvc;
	@MockBean
	private VetServiceImpl vetService;
	@Autowired
	private VetMapper vetMapper;
	@Autowired
	private BCryptPasswordEncoder bcrypt;
	private static final String ADMIN = "ADMIN";

	@Test
	@WithMockUser(authorities = ADMIN)
	public void getVetByIdTest() throws Exception {
		Vet vet = new Vet();
		vet.setId(1L);
		vet.setName("Marius");
		vet.setEmail("foo@gmail.com");
		vet.setPassword(bcrypt.encode("password"));
		vet.setAge(30);
		vet.setYearsOfExperience(6D);
		VetDTO vetDTO = vetMapper.mapEntityToDto(vet);

		Customer customer = new Customer();
		customer.setId(1L);
		customer.setName("Rares");
		customer.setEmail("foo@gmail.com");
		customer.setPassword(bcrypt.encode("password"));
		customer.setPhone("1234567890");
		customer.setPetSpecies("Labrador");
		customer.setPetName("Toby");
		customer.setVet(vet);

		List<Customer> allCustomers = new ArrayList<>();
		allCustomers.add(customer);
		vetDTO.setCustomers(allCustomers);

		CompletableFuture<VetDTO> vetFuture = CompletableFuture.completedFuture(vetDTO);
		given(vetService.getVetById(vetDTO.getId())).willReturn(vetFuture);

		this.mvc.perform(get("/vet/1").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())

				// Checking the existence of VetJSON
				.andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.name").exists())
				.andExpect(jsonPath("$.email").exists())
				.andExpect(jsonPath("$.password").exists())
				.andExpect(jsonPath("$.age").exists())
				.andExpect(jsonPath("$.yearsOfExperience").exists())
				.andExpect(jsonPath("$.email").exists())
				.andExpect(jsonPath("$.customers").exists())
				.andExpect(jsonPath("$.customers[0].id").exists())
				.andExpect(jsonPath("$.customers[0].name").exists())
				.andExpect(jsonPath("$.customers[0].email").exists())
				.andExpect(jsonPath("$.customers[0].password").exists())
				.andExpect(jsonPath("$.customers[0].phone").exists())
				.andExpect(jsonPath("$.customers[0].petSpecies").exists())
				.andExpect(jsonPath("$.customers[0].petName").exists())

				// Checking the value types of VetJSON
				.andExpect(jsonPath("$.id").isNumber())
				.andExpect(jsonPath("$.name").isString())
				.andExpect(jsonPath("$.email").isString())
				.andExpect(jsonPath("$.customers").isArray())
				.andExpect(jsonPath("$.password").isString())
				.andExpect(jsonPath("$.age").isNumber())
				.andExpect(jsonPath("$.yearsOfExperience").isNumber())
				.andExpect(jsonPath("$.customers[0].id").isNumber())
				.andExpect(jsonPath("$.customers[0].name").isString())
				.andExpect(jsonPath("$.customers[0].email").isString())
				.andExpect(jsonPath("$.customers[0].password").isString())
				.andExpect(jsonPath("$.customers[0].phone").isString())
				.andExpect(jsonPath("$.customers[0].petSpecies").isString())
				.andExpect(jsonPath("$.customers[0].petName").isString())

				// Checking the VetJSON values
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.name").value("Marius"))
				.andExpect(jsonPath("$.password").value(vet.getPassword()))
				.andExpect(jsonPath("$.email").value("foo@gmail.com"))
				.andExpect(jsonPath("$.age").value(30))
				.andExpect(jsonPath("$.yearsOfExperience").value(6))
				.andExpect(jsonPath("$.customers[0].id").value(1))
				.andExpect(jsonPath("$.customers[0].name").value("Rares"))
				.andExpect(jsonPath("$.customers[0].email").value("foo@gmail.com"))
				.andExpect(jsonPath("$.customers[0].password").value(customer.getPassword()))
				.andExpect(jsonPath("$.customers[0].phone").value("1234567890"))
				.andExpect(jsonPath("$.customers[0].petSpecies").value("Labrador"))
				.andExpect(jsonPath("$.customers[0].petName").value("Toby"));
		verify(vetService).getVetById(anyLong());
	}

	@Test
	@WithMockUser(authorities = ADMIN)
	public void testGetAllVets() throws Exception {
		Vet vet1 = new Vet();
		vet1.setId(1L);
		vet1.setName("Marius");
		vet1.setEmail("foo@gmail.com");
		vet1.setPassword(bcrypt.encode("password"));
		vet1.setAge(30);
		vet1.setYearsOfExperience(6D);
		
		Vet vet2 = new Vet();
		vet2.setId(2L);
		vet2.setName("Andrei");
		vet2.setEmail("fooUpdate@gmail.com");
		vet2.setPassword(bcrypt.encode("password2"));
		vet2.setAge(40);
		vet2.setYearsOfExperience(13D);
		
		VetDTO vetDTO1 = vetMapper.mapEntityToDto(vet1);
		VetDTO vetDTO2 = vetMapper.mapEntityToDto(vet2);
		List<VetDTO> allVets = new ArrayList<>();
		allVets.add(vetDTO1);
		allVets.add(vetDTO2);

		given(vetService.getAllVets()).willReturn(allVets);

		this.mvc.perform(get("/vet/all").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$").isArray())

				// Checking the first VetJSON
				.andExpect(jsonPath("$[0].id").value(1))
				.andExpect(jsonPath("$[0].name").value("Marius"))
				.andExpect(jsonPath("$[0].email").value("foo@gmail.com"))
				.andExpect(jsonPath("$[0].password").value(vet1.getPassword()))
				.andExpect(jsonPath("$[0].age").value(30))
				.andExpect(jsonPath("$[0].yearsOfExperience").value(6))

				// Checking the seconds VetJSON
				.andExpect(jsonPath("$[1].id").value(2))
				.andExpect(jsonPath("$[1].email").value("fooUpdate@gmail.com"))
				.andExpect(jsonPath("$[1].name").value("Andrei"))
				.andExpect(jsonPath("$[1].password").value(vet2.getPassword()))
				.andExpect(jsonPath("$[1].age").value(40))
				.andExpect(jsonPath("$[1].yearsOfExperience").value(13));
		verify(vetService).getAllVets();
	}

	@Test
	public void testSaveVet() throws Exception {
		Vet vet = new Vet();
		vet.setId(1L);
		vet.setName("Marius");
		vet.setEmail("foo@gmail.com");
		vet.setPassword(bcrypt.encode("password"));
		vet.setAge(30);
		vet.setYearsOfExperience(6D);
		VetDTO vetDTO = vetMapper.mapEntityToDto(vet);

		given(vetService.saveVet(any(VetDTO.class))).willReturn(vetDTO);

		this.mvc.perform(post("/vet").content(mapper.writeValueAsString(vetDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())

				// Checking VetJSON values
				.andExpect(jsonPath("$.id").value(Long.valueOf(1)))
				.andExpect(jsonPath("$.name").value("Marius"))
				.andExpect(jsonPath("$.email").value("foo@gmail.com"))
				.andExpect(jsonPath("$.password").value(vet.getPassword()))
				.andExpect(jsonPath("$.age").value(30))
				.andExpect(jsonPath("$.yearsOfExperience").value(6));
		verify(vetService).saveVet(any(VetDTO.class));
	}

	@Test
	@WithMockUser(authorities = ADMIN)
	public void testUpdateVet() throws Exception {
		Vet vet = new Vet();
		vet.setId(1L);
		vet.setName("Marius");
		vet.setEmail("foo@gmail.com");
		vet.setPassword(bcrypt.encode("password"));
		vet.setAge(30);
		vet.setYearsOfExperience(6D);
		
		VetDTO vetDTO = vetMapper.mapEntityToDto(vet);

		given(vetService.updateVet(anyLong(), any(VetDTO.class))).willReturn(vetDTO);

		this.mvc.perform(put("/vet/1").content(mapper.writeValueAsString(vetDTO))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").value(Long.valueOf(1)))
				.andExpect(jsonPath("$.name").value("Marius"))
				.andExpect(jsonPath("$.email").value("foo@gmail.com"))
				.andExpect(jsonPath("$.password").value(vet.getPassword()))
				.andExpect(jsonPath("$.age").value(30))
				.andExpect(jsonPath("$.yearsOfExperience").value(6));
		verify(vetService).updateVet(anyLong(), any(VetDTO.class));
	}

	@Test
	@WithMockUser(authorities = ADMIN)
	public void testDeleteVetById() throws Exception {
		this.mvc.perform(delete("/vet/1")).andExpect(status().isOk());

		String content = this.mvc.perform(delete("/vet/1")).andReturn().getResponse().getContentAsString();
		assertEquals("The vet was deleted succesfully!", content);
		verify(vetService,times(2)).deleteVetById(anyLong());
	}

	@Test
	@WithMockUser(authorities = ADMIN)
	public void testIdNotFoundException() throws Exception, IdNotFoundException {
		doThrow(new IdNotFoundException()).when(vetService).getVetById(anyLong());
		this.mvc.perform(get("/vet/2")).andExpect(status().isNotFound());
	}
	
	@Test
	public void testNoAuthority() throws Exception {
		Vet vet = new Vet();
		vet.setId(1L);
		vet.setName("Marius");
		vet.setEmail("foo@gmail.com");
		vet.setPassword(bcrypt.encode("password"));
		vet.setAge(30);
		vet.setYearsOfExperience(6D);
		
		VetDTO vetDTO = vetMapper.mapEntityToDto(vet);
		CompletableFuture<VetDTO> vetFuture = CompletableFuture.completedFuture(vetDTO);


		given(vetService.getVetById(anyLong())).willReturn(vetFuture);

		this.mvc.perform(get("/vet/1").content(mapper.writeValueAsString(vetDTO))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnauthorized());
				
	}

	@Test
	public void testErrorNoName() throws Exception {
		VetDTO vetDTO = new VetDTO();
		vetDTO.setId(1L);
		vetDTO.setEmail("foo@gmail.com");
		vetDTO.setPassword(bcrypt.encode("password"));
		vetDTO.setAge(30);
		vetDTO.setYearsOfExperience(6D);

		given(vetService.saveVet(any(VetDTO.class))).willReturn(vetDTO);

		this.mvc.perform(post("/vet").content(mapper.writeValueAsString(vetDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.status").value(400))
				.andExpect(jsonPath("$.errors[0]").value("Please enter a name"));
	}
	
	@Test
	public void testMissingPassword() throws Exception {
		VetDTO vetDTO = new VetDTO();
		vetDTO.setId(1L);
		vetDTO.setName("Marius");
		vetDTO.setEmail("foo@gmail.com");
		vetDTO.setAge(30);
		vetDTO.setYearsOfExperience(6D);

		given(vetService.saveVet(any(VetDTO.class))).willReturn(vetDTO);

		this.mvc.perform(post("/vet").content(mapper.writeValueAsString(vetDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.status").value(400))
				.andExpect(jsonPath("$.errors[0]").value("Please enter a password"));
	}

	@Test
	public void testMissingEmail() throws Exception {
		VetDTO vetDTO = new VetDTO();
		vetDTO.setId(1L);
		vetDTO.setName("Marius");
		vetDTO.setPassword(bcrypt.encode("password"));
		vetDTO.setAge(30);
		vetDTO.setYearsOfExperience(6D);

		given(vetService.saveVet(any(VetDTO.class))).willReturn(vetDTO);

		this.mvc.perform(post("/vet").content(mapper.writeValueAsString(vetDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.status").value(400))
				.andExpect(jsonPath("$.errors[0]").value("Please enter an email"));
	}

	@Test
	public void testMissingExperience() throws Exception {
		VetDTO vetDTO = new VetDTO();
		vetDTO.setId(1L);
		vetDTO.setName("Marius");
		vetDTO.setEmail("foo@gmail.com");
		vetDTO.setPassword(bcrypt.encode("password"));
		vetDTO.setAge(30);

		given(vetService.saveVet(any(VetDTO.class))).willReturn(vetDTO);

		this.mvc.perform(post("/vet").content(mapper.writeValueAsString(vetDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.status").value(400))
				.andExpect(jsonPath("$.errors[0]").value("Experience must be greater then 1"));
	}

	@Test
	public void testMissingAge() throws Exception {
		VetDTO vetDTO = new VetDTO();
		vetDTO.setId(1L);
		vetDTO.setName("Marius");
		vetDTO.setEmail("foo@gmail.com");
		vetDTO.setPassword(bcrypt.encode("password"));
		vetDTO.setYearsOfExperience(6D);

		this.mvc.perform(post("/vet").content(mapper.writeValueAsString(vetDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.status").value(400))
				.andExpect(jsonPath("$.errors[0]").value("Please enter an age"));
	}

	@Test
	public void testBadEmailFormats() throws Exception {
		VetDTO email1 = new VetDTO();
		email1.setId(1L);
		email1.setName("Marius");
		email1.setPassword(bcrypt.encode("password"));
		email1.setAge(30);
		email1.setYearsOfExperience(6D);
		this.mvc.perform(post("/vet").content(mapper.writeValueAsString(email1)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errors[0]").value("Please enter an email")).andDo(print());

		email1.setEmail("foo@");
		this.mvc.perform(post("/vet").content(mapper.writeValueAsString(email1)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.status").value(400))
				.andExpect(jsonPath("$.errors[0]").value("Email format is not valid")).andDo(print());

		email1.setEmail("foo@.");
		this.mvc.perform(post("/vet").content(mapper.writeValueAsString(email1)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.status").value(400))
				.andExpect(jsonPath("$.errors[0]").value("Email format is not valid"));

		email1.setEmail("foo@ab.");
		this.mvc.perform(post("/vet").content(mapper.writeValueAsString(email1)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.status").value(400))
				.andExpect(jsonPath("$.errors[0]").value("Email format is not valid"));

		email1.setEmail("foo@.c");
		this.mvc.perform(post("/vet").content(mapper.writeValueAsString(email1)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.status").value(400))
				.andExpect(jsonPath("$.errors[0]").value("Email format is not valid"));
	}

}
