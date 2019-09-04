package com.petshop.controller;

import com.petshop.dto.VetDTO;
import com.petshop.http_errors.IdNotFoundException;
import com.petshop.service.VetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/vet")
public class VetController {

	@Autowired
	VetService vetService;

	Logger logger = LoggerFactory.getLogger(VetController.class);

	// REQUEST:GET @PATH: /vet/{id}
	@GetMapping("/{id}")
	public ResponseEntity<VetDTO> getVetById(@PathVariable(value = "id") Long id) {
		try{VetDTO vet = vetService.getVetById(id);
		return new ResponseEntity<VetDTO>(vet,HttpStatus.OK);}
		catch (IdNotFoundException e) {
			throw e;
		}
	}

	// REQUEST:GET @PATH: /vet/all
	@GetMapping("/all")
	public ResponseEntity<List<VetDTO>> getAllVets() {
		List<VetDTO> allVets =vetService.getAllVets(); 
		return new ResponseEntity<List<VetDTO>>(allVets,HttpStatus.OK);
	}

	// REQUEST:POST @PATH: /vet
	@PostMapping("")
	public ResponseEntity<VetDTO>saveVet(@Valid @RequestBody VetDTO vetDTO) {
		VetDTO vet = vetService.saveVet(vetDTO);
		return new ResponseEntity<VetDTO>(vet, HttpStatus.CREATED);
	}

	// REQUEST:PUT @PATH: /vet/{id}
	@PutMapping("/{id}")
	public ResponseEntity<VetDTO> updateVet(@PathVariable(value = "id") Long id, @Valid @RequestBody VetDTO vetDTO) {
		VetDTO vet = vetService.updateVet(id, vetDTO);
		return new ResponseEntity<VetDTO>(vet, HttpStatus.CREATED);
	}

	// REQUEST:DELETE @PATH: /vet/{id}
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteVetById(@PathVariable(value = "id") Long id) {
		vetService.deleteVetById(id);
		return new ResponseEntity<String>("The vet was deleted succesfully!",HttpStatus.OK);
	}

}
