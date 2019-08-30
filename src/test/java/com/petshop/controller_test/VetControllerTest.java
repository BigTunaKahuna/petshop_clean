package com.petshop.controller_test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.petshop.controller.VetController;
import com.petshop.dto.VetDTO;
import com.petshop.mapper.VetMapper;
import com.petshop.models.Vet;
import com.petshop.service.service_impl.VetServiceImpl;

@SpringBootTest
@AutoConfigureMockMvc
public class VetControllerTest {

	@Autowired
	MockMvc mvc;
	@MockBean
	VetServiceImpl vetService;
	@Autowired
	VetMapper vetMapper;


	@Test
	public void getVetByIdTest() throws Exception{
		
		Vet vet = new Vet(Long.valueOf(1), "Marius", 25, Double.valueOf(3), "foo@gmail.com", new ArrayList<>());
		VetDTO vetDTO = vetMapper.mapEntityToDto(vet);
		when(vetService.getVetById(vetDTO.getId())).thenReturn(vetDTO);
		
		mvc.perform(get("/vet/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].id", is("1")));
	}
}



















