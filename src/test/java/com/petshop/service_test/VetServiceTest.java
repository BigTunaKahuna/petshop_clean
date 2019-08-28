package com.petshop.service_test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.petshop.dao.VetDao;
import com.petshop.dao.impl.VetDaoImpl;
import com.petshop.dto.VetDTO;
import com.petshop.mapper.VetMapper;
import com.petshop.models.Vet;
import com.petshop.service.VetService;
import com.petshop.service.service_impl.VetServiceImpl;



@SpringBootTest
public class VetServiceTest {
	
	@InjectMocks
	VetServiceImpl vetServiceImpl;
	@Mock
	VetDao vetDao;
	
	@Autowired
	VetMapper vetMapper;
	
	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testGetAllEmployers() {
		List<Vet> vets = new ArrayList<>();
		Vet vet1 = new Vet(Long.valueOf(1),"Marius", 25, Double.valueOf(3), "foo@gmail.com", new ArrayList<>());
		Vet vet2 = new Vet(Long.valueOf(2),"Andrei", 40, Double.valueOf(20), "foo2@gmail.com", new ArrayList<>());
		vetMapper.mapEntityToDto(vet1);
		vetMapper.mapEntityToDto(vet2);
		vets.add(vet1);
		vets.add(vet2);

		when(vetDao.gettAllVets()).thenReturn(vets);
		
		List<VetDTO> vetList = vetServiceImpl.getAllVets();
		
		assertEquals(2, vetList.size());
		verify(vetDao,times(1)).gettAllVets();
		
	}

}










