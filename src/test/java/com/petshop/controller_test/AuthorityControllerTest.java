package com.petshop.controller_test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petshop.dto.AuthorityDTO;
import com.petshop.mapper.impl.AuthorityMapper;
import com.petshop.models.authority.Authority;
import com.petshop.models.authority.Role;
import com.petshop.service.AuthorityService;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthorityControllerTest {

	@Autowired
	private ObjectMapper mapper;
	@Autowired
	private MockMvc mvc;
	@MockBean
	private AuthorityService authorityService;
	@Autowired
	private AuthorityMapper authorityMapper;

	@Test
	@WithMockUser(authorities = "ADMIN")
	public void testGetAllRoles() throws Exception {
		Authority auth1 = new Authority();
		auth1.setRoles(Role.USER);
		
		Authority auth2 = new Authority();
		auth2.setRoles(Role.ADMIN);
		
		AuthorityDTO authority1 = authorityMapper.mapEntityToDto(auth1);
		AuthorityDTO authority2 = authorityMapper.mapEntityToDto(auth2);

		List<AuthorityDTO> authorityDTOList = new ArrayList<>();
		authorityDTOList.add(authority1);
		authorityDTOList.add(authority2);
		
		given(authorityService.getAllRoles()).willReturn(authorityDTOList);
		
		this.mvc.perform(get("/role/all").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.[0].role").value("USER"))
				.andExpect(jsonPath("$.[1].role").value("ADMIN"));
		verify(authorityService).getAllRoles();
	}

	@Test
	@WithMockUser(authorities = "ADMIN")
	public void testAddRole() throws Exception {
		Authority authority = new Authority();
		authority.setRoles(Role.ADMIN);
		AuthorityDTO authorityDTO = authorityMapper.mapEntityToDto(authority);

		given(authorityService.saveAuthority(any(AuthorityDTO.class))).willReturn(authorityDTO);

		this.mvc.perform(post("/role").content(mapper.writeValueAsString(authorityDTO))
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andExpect(jsonPath("$.role").value("ADMIN"));
		verify(authorityService).saveAuthority(any(AuthorityDTO.class));
	}
	
	@Test
	@WithMockUser(authorities = "ADMIN")
	public void testAddRoleForVet() throws Exception {
		String success = "Role was added successfully";
	
		doNothing().when(authorityService).addRoleForVet(anyLong(), any(Role.class));
		
		this.mvc.perform(post("/role/vet/1/USER").content(mapper.writeValueAsString(success)).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$").value(success));
		verify(authorityService).addRoleForVet(anyLong(), any(Role.class));
	}
	
	@Test
	@WithMockUser(authorities = "ADMIN")
	public void testAddRoleForCustomer() throws Exception {
		String success = "Role was added successfully";
	
		doNothing().when(authorityService).addRoleForCustomer(anyLong(), any(Role.class));
		
		this.mvc.perform(post("/role/customer/1/USER").content(mapper.writeValueAsString(success)).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$").value(success));
		verify(authorityService).addRoleForCustomer(anyLong(), any(Role.class));
	}
	
	@Test
	@WithMockUser(authorities = "ADMIN")
	public void testChangeVetRole() throws Exception {
		String success = "Role was successfully changed";
		
		doNothing().when(authorityService).changeRoleOfVet(anyLong(), any(Role.class), any(Role.class));
		
		this.mvc.perform(put("/role/change/vet/1/ADMIN/USER").content(mapper.writeValueAsString(success)).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)).andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$").value(success));
		verify(authorityService).changeRoleOfVet(anyLong(), any(Role.class), any(Role.class));
	}
	
	@Test
	@WithMockUser(authorities = "ADMIN")
	public void testChangeCustomerRole() throws Exception {
		String success = "Role was successfully changed";
		
		doNothing().when(authorityService).changeRoleOfCustomer(anyLong(), any(Role.class), any(Role.class));
		
		this.mvc.perform(put("/role/change/customer/1/USER/ADMIN").content(mapper.writeValueAsString(success)).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$").value(success));
		
		verify(authorityService).changeRoleOfCustomer(anyLong(), any(Role.class), any(Role.class));
	}
	
	@Test
	@WithMockUser(authorities = "ADMIN")
	public void testDeleteRoleForVet() throws Exception {
		String success = "Role was successfully deleted";
		
		doNothing().when(authorityService).deleteRoleForVet(anyLong(), any(Role.class));
		
		this.mvc.perform(delete("/role/vet/1/ADMIN").content(mapper.writeValueAsString(success)).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)).andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$").value(success));
		verify(authorityService).deleteRoleForVet(anyLong(), any(Role.class));
	}
	
	@Test
	@WithMockUser(authorities = "ADMIN")
	public void testDeleteRoleForCustomer() throws Exception {
		String success = "Role was successfully deleted";
		
		doNothing().when(authorityService).deleteRoleForCustomer(anyLong(), any(Role.class));
		
		this.mvc.perform(delete("/role/customer/1/USER").content(mapper.writeValueAsString(success)).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)).andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$").value(success));
		verify(authorityService).deleteRoleForCustomer(anyLong(), any(Role.class));
	}
	

}























