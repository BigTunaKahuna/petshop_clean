package com.petshop.controller_test;

import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.petshop.dto.CustomerDTO;
import com.petshop.http_errors.IdNotFoundException;
import com.petshop.mapper.CustomerMapper;
import com.petshop.mapper.VetMapper;
import com.petshop.models.Customer;
import com.petshop.models.Vet;
import com.petshop.service.impl.CustomerServiceImpl;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class CustomerControllerTest {

	@Autowired
	ObjectMapper mapper;
	@MockBean
	CustomerServiceImpl customerService;
	@Autowired
	private MockMvc mvc;
	@Autowired
	VetMapper vetMapper;
	@Autowired
	CustomerMapper customerMapper;
	
	@Test
	public void testGetCustomerById() throws Exception {
		Customer customer = new Customer(Long.valueOf(1),"Adrian", "012345", "Labrador", "Toby", new Vet());
		CustomerDTO customerDTO = customerMapper
				.mapEntityToDto(customer);
		given(customerService.getCustomerById(anyLong())).willReturn(customerDTO);
		this.mvc.perform(get("/customer/1").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").value(1))
			.andExpect(jsonPath("$.name").value("Adrian"))
			.andExpect(jsonPath("$.phone").value("012345"))
			.andExpect(jsonPath("$.petSpecies").value("Labrador"))
			.andExpect(jsonPath("$.petName").value("Toby"));
	}
	
	@Test
	public void testGetAllCustomers() throws Exception{
		List<CustomerDTO> allCustomers = new ArrayList<>();
		CustomerDTO customer1 = customerMapper
				.mapEntityToDto(new Customer(Long.valueOf(1),"Adrian", "012345", "Labrador", "Toby", new Vet()));
		CustomerDTO customer2 = customerMapper
				.mapEntityToDto(new Customer(Long.valueOf(2),"Andrei", "054321", "Bichon", "Ali", new Vet()));

		allCustomers.add(customer1);
		allCustomers.add(customer2);
		
		given(customerService.getAllCustomers()).willReturn(allCustomers);
		
		this.mvc.perform(get("/customer/all").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			
			// Checking the first CustomerJSON
			.andExpect(jsonPath("$[0].id").value(1))
			.andExpect(jsonPath("$[0].name").value("Adrian"))
			.andExpect(jsonPath("$[0].phone").value("012345"))
			.andExpect(jsonPath("$[0].petSpecies").value("Labrador"))
			.andExpect(jsonPath("$[0].petName").value("Toby"))
			.andExpect(jsonPath("$[0].vet").value(IsNull.nullValue()))
			
			// Checking the second CustomerJSON
			.andExpect(jsonPath("$[1].id").value(2))
			.andExpect(jsonPath("$[1].name").value("Andrei"))
			.andExpect(jsonPath("$[1].phone").value("054321"))
			.andExpect(jsonPath("$[1].petSpecies").value("Bichon"))
			.andExpect(jsonPath("$[1].petName").value("Ali"))
			.andExpect(jsonPath("$[1].vet").value(IsNull.nullValue()));
	}
	
	@Test
	public void testSaveCustomer() throws Exception{
		CustomerDTO customer = customerMapper
				.mapEntityToDto(new Customer(Long.valueOf(1),"Adrian", "012345", "Labrador", "Toby", new Vet()));
		
		given(customerService.saveCustomer(anyLong(),any(CustomerDTO.class))).willReturn(customer);
		
		this.mvc.perform(post("/customer/vet/1").content(mapper.writeValueAsString(customer)).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated())
			
			.andExpect(jsonPath("$.id").value(1))
			.andExpect(jsonPath("$.name").value("Adrian"))
			.andExpect(jsonPath("$.phone").value("012345"))
			.andExpect(jsonPath("$.petSpecies").value("Labrador"))
			.andExpect(jsonPath("$.petName").value("Toby"));
	}
	
	@Test
	public void testUpdateCustomer() throws Exception{
		CustomerDTO customer = customerMapper
				.mapEntityToDto(new Customer(Long.valueOf(1),"Adrian", "012345", "Labrador", "Toby", new Vet()));
		
		this.mvc.perform(put("/customer/1").content(mapper.writeValueAsString(customer)).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated());
	}
	
	@Test
	public void testUpdateVetForCustomer() throws JsonProcessingException, Exception {
		CustomerDTO customer = customerMapper
				.mapEntityToDto(new Customer(Long.valueOf(1),"Adrian", "012345", "Labrador", "Toby", new Vet()));
		
		this.mvc.perform(put("/customer/1/1").content(mapper.writeValueAsString(customer)).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated());	}
	
	@Test
	public void testDeleteVetById() throws Exception {
		this.mvc.perform(delete("/customer/1")).andExpect(status().isOk());

		String content = this.mvc.perform(delete("/customer/1")).andReturn().getResponse().getContentAsString();
		assertEquals(content, "The customer was deleted succesfully!");
	}
	
	@Test
	public void testIdNotFoundException() throws Exception, IdNotFoundException {
		doThrow(new IdNotFoundException()).when(customerService).getCustomerById(anyLong());
		this.mvc.perform(get("/customer/1")).andExpect(status().isNotFound());
	}
}




















