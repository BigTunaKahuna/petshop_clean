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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.doThrow;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.petshop.dto.CustomerDTO;
import com.petshop.exception.IdNotFoundException;
import com.petshop.mapper.impl.CustomerMapper;
import com.petshop.models.Customer;
import com.petshop.service.impl.CustomerServiceImpl;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class CustomerControllerTest {

	@Autowired
	private ObjectMapper mapper;
	@MockBean
	private CustomerServiceImpl customerService;
	@Autowired
	private MockMvc mvc;
	@Autowired
	private CustomerMapper customerMapper;
	@Autowired
	private BCryptPasswordEncoder bcrypt;

	@Test
	@WithMockUser(authorities = "USER")
	public void testGetCustomerById() throws Exception {
		Customer customer = new Customer();
		customer.setId(1L);
		customer.setName("Rares");
		customer.setEmail("foo@gmail.com");
		customer.setPassword(bcrypt.encode("password"));
		customer.setPhone("1234567890");
		customer.setPetSpecies("Labrador");
		customer.setPetName("Toby");

		CustomerDTO customerDTO = customerMapper.mapEntityToDto(customer);
		
		given(customerService.getCustomerById(anyLong())).willReturn(customerDTO);
		this.mvc.perform(get("/customer/1").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.name").value("Rares"))
				.andExpect(jsonPath("$.email").value("foo@gmail.com"))
				.andExpect(jsonPath("$.password").value(customerDTO.getPassword()))
				.andExpect(jsonPath("$.phone").value("1234567890"))
				.andExpect(jsonPath("$.petSpecies").value("Labrador"))
				.andExpect(jsonPath("$.petName").value("Toby"));
	}

	@Test
	@WithMockUser(authorities = "USER")
	public void testGetAllCustomers() throws Exception {
		List<CustomerDTO> allCustomers = new ArrayList<>();
		Customer customer1 = new Customer();
		customer1.setId(1L);
		customer1.setName("Rares");
		customer1.setEmail("foo@gmail.com");
		customer1.setPassword(bcrypt.encode("password"));
		customer1.setPhone("1234567890");
		customer1.setPetSpecies("Labrador");
		customer1.setPetName("Toby");
		
		Customer customer2 = new Customer();
		customer2.setId(2L);
		customer2.setName("Andrei");
		customer2.setEmail("foo2@gmail.com");
		customer2.setPassword(bcrypt.encode("password2"));
		customer2.setPhone("1234567890");
		customer2.setPetSpecies("Labrador2");
		customer2.setPetName("Toby2");

		allCustomers.add(customerMapper.mapEntityToDto(customer1));
		allCustomers.add(customerMapper.mapEntityToDto(customer2));

		given(customerService.getAllCustomers()).willReturn(allCustomers);

		this.mvc.perform(
				get("/customer/all").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())

				// Checking the first CustomerJSON
				.andExpect(jsonPath("$[0].id").value(1))
				.andExpect(jsonPath("$[0].name").value("Rares"))
				.andExpect(jsonPath("$[0].email").value("foo@gmail.com"))
				.andExpect(jsonPath("$[0].password").value(customer1.getPassword()))
				.andExpect(jsonPath("$[0].phone").value("1234567890"))
				.andExpect(jsonPath("$[0].petSpecies").value("Labrador"))
				.andExpect(jsonPath("$[0].petName").value("Toby"))
				.andExpect(jsonPath("$[0].vet").value(IsNull.nullValue()))

				// Checking the second CustomerJSON
				.andExpect(jsonPath("$[1].id").value(2))
				.andExpect(jsonPath("$[1].name").value("Andrei"))
				.andExpect(jsonPath("$[1].email").value("foo2@gmail.com"))
				.andExpect(jsonPath("$[1].password").value(customer2.getPassword()))
				.andExpect(jsonPath("$[1].phone").value("1234567890"))
				.andExpect(jsonPath("$[1].petSpecies").value("Labrador2"))
				.andExpect(jsonPath("$[1].petName").value("Toby2"))
				.andExpect(jsonPath("$[1].vet").value(IsNull.nullValue()));
	}

	@Test
	@WithMockUser(authorities = "USER")
	public void testSaveCustomer() throws Exception {
		Customer customer = new Customer();
		customer.setId(1L);
		customer.setName("Rares");
		customer.setEmail("foo@gmail.com");
		customer.setPassword(bcrypt.encode("password"));
		customer.setPhone("1234567890");
		customer.setPetSpecies("Labrador");
		customer.setPetName("Toby");

		CustomerDTO customerDTO = customerMapper.mapEntityToDto(customer);
		
		given(customerService.saveCustomer(anyLong(), any(CustomerDTO.class))).willReturn(customerDTO);

		this.mvc.perform(post("/customer/vet/1").content(mapper.writeValueAsString(customerDTO))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.name").value("Rares"))
				.andExpect(jsonPath("$.email").value("foo@gmail.com"))
				.andExpect(jsonPath("$.password").value(customerDTO.getPassword()))
				.andExpect(jsonPath("$.phone").value("1234567890"))
				.andExpect(jsonPath("$.petSpecies").value("Labrador"))
				.andExpect(jsonPath("$.petName").value("Toby"));
	}

	@Test
	@WithMockUser(authorities = "USER")
	public void testUpdateCustomer() throws Exception {
		Customer customer = new Customer();
		customer.setId(1L);
		customer.setName("Rares");
		customer.setEmail("foo@gmail.com");
		customer.setPassword(bcrypt.encode("password"));
		customer.setPhone("1234567890");
		customer.setPetSpecies("Labrador");
		customer.setPetName("Toby");

		CustomerDTO customerDTO = customerMapper.mapEntityToDto(customer);
		

		this.mvc.perform(put("/customer/1").content(mapper.writeValueAsString(customerDTO))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
	}

	@Test
	@WithMockUser(authorities = "USER")
	public void testUpdateVetForCustomer() throws JsonProcessingException, Exception {
		Customer customer = new Customer();
		customer.setId(1L);
		customer.setName("Rares");
		customer.setEmail("foo@gmail.com");
		customer.setPassword(bcrypt.encode("password"));
		customer.setPhone("1234567890");
		customer.setPetSpecies("Labrador");
		customer.setPetName("Toby");

		CustomerDTO customerDTO = customerMapper.mapEntityToDto(customer);


		this.mvc.perform(put("/customer/1/1").content(mapper.writeValueAsString(customerDTO))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
	}

	@Test
	@WithMockUser(authorities = "USER")
	public void testDeleteVetById() throws Exception {
		this.mvc.perform(delete("/customer/1")).andExpect(status().isOk());

		String content = this.mvc.perform(delete("/customer/1")).andReturn().getResponse().getContentAsString();
		assertEquals(content, "The customer was deleted succesfully!");
	}

	@Test
	@WithMockUser(authorities = "USER")
	public void testIdNotFoundException() throws Exception, IdNotFoundException {
		doThrow(new IdNotFoundException()).when(customerService).getCustomerById(anyLong());
		this.mvc.perform(get("/customer/1")).andExpect(status().isNotFound());
	}

	@Test
	public void testMissingName() throws JsonProcessingException, Exception {
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setId(1L);
		customerDTO.setEmail("foo@gmail.com");
		customerDTO.setPassword(bcrypt.encode("password"));
		customerDTO.setPhone("1234567890");
		customerDTO.setPetSpecies("Labrador");
		customerDTO.setPetName("Toby");

		this.mvc.perform(put("/customer/1").content(mapper.writeValueAsString(customerDTO))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest()).andExpect(jsonPath("$.status").value("400"))
				.andExpect(jsonPath("$.errors[0]").value("Please enter a name"));
	}
	
	@Test
	public void testMissingEmail() throws JsonProcessingException, Exception {
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setId(1L);
		customerDTO.setName("Andrei");
		customerDTO.setPassword(bcrypt.encode("password"));
		customerDTO.setPhone("1234567890");
		customerDTO.setPetSpecies("Labrador");
		customerDTO.setPetName("Toby");

		this.mvc.perform(put("/customer/1").content(mapper.writeValueAsString(customerDTO))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest()).andExpect(jsonPath("$.status").value("400"))
				.andExpect(jsonPath("$.errors[0]").value("Please enter an email"));
	}

	@Test
	public void testMissingPhone() throws JsonProcessingException, Exception {
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setId(1L);
		customerDTO.setName("Rares");
		customerDTO.setEmail("foo@gmail.com");
		customerDTO.setPassword(bcrypt.encode("password"));
		customerDTO.setPetSpecies("Labrador");
		customerDTO.setPetName("Toby");

		this.mvc.perform(put("/customer/1").content(mapper.writeValueAsString(customerDTO))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest()).andExpect(jsonPath("$.status").value("400"))
				.andExpect(jsonPath("$.errors[0]").value("Please enter a phone number"));
	}

	@Test
	public void testMissingPetSpecies() throws JsonProcessingException, Exception {
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setId(1L);
		customerDTO.setName("Rares");
		customerDTO.setEmail("foo@gmail.com");
		customerDTO.setPassword(bcrypt.encode("password"));
		customerDTO.setPhone("1234567890");
		customerDTO.setPetName("Toby");

		this.mvc.perform(put("/customer/1").content(mapper.writeValueAsString(customerDTO))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest()).andExpect(jsonPath("$.status").value("400"))
				.andExpect(jsonPath("$.errors[0]").value("Please enter the pet species"));
	}

	@Test
	public void testMissingPetName() throws JsonProcessingException, Exception {
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setId(1L);
		customerDTO.setName("Rares");
		customerDTO.setEmail("foo@gmail.com");
		customerDTO.setPassword(bcrypt.encode("password"));
		customerDTO.setPhone("1234567890");
		customerDTO.setPetSpecies("Labrador");

		this.mvc.perform(put("/customer/1").content(mapper.writeValueAsString(customerDTO))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest()).andExpect(jsonPath("$.status").value("400"))
				.andExpect(jsonPath("$.errors[0]").value("Please enter a pet name"));
	}
	
	@Test
	public void testMissingPassword() throws  Exception {
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setId(1L);
		customerDTO.setName("Rares");
		customerDTO.setEmail("foo@gmail.com");
		customerDTO.setPhone("1234567890");
		customerDTO.setPetSpecies("Labrador");
		customerDTO.setPetName("Toby");
		
		this.mvc.perform(put("/customer/1").content(mapper.writeValueAsString(customerDTO))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest()).andExpect(jsonPath("$.status").value("400"))
				.andExpect(jsonPath("$.errors[0]").value("Please enter a password"));
	}
	
	@Test
	public void testPasswordTooShort() throws  Exception {
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setId(1L);
		customerDTO.setName("Rares");
		customerDTO.setEmail("foo@gmail.com");
		customerDTO.setPassword("");
		customerDTO.setPhone("1234567890");
		customerDTO.setPetSpecies("Labrador");
		customerDTO.setPetName("Toby");
		
		this.mvc.perform(put("/customer/1").content(mapper.writeValueAsString(customerDTO))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isBadRequest()).andExpect(jsonPath("$.status").value("400"))
				.andExpect(jsonPath("$.errors[0]").value("Password must be at least 6 characters"));
	}

}
