package com.petshop.controller;

import com.petshop.dto.VetDTO;
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
	private VetService vetService;

	Logger logger = LoggerFactory.getLogger(VetController.class);

	// REQUEST:GET @PATH: /vet/{id} - Get vet by id
	@GetMapping("/{id}")
	public ResponseEntity<VetDTO> getVetById(@PathVariable(value = "id") Long id) {
		try {
			VetDTO vet = vetService.getVetById(id).join();
			logger.info("Thread {} executed successfuly!", Thread.currentThread().getName());
			return new ResponseEntity<>(vet, HttpStatus.OK);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			logger.error("Thread {} gave an InterruptedException error!", Thread.currentThread().getName());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// REQUEST:GET @PATH: /vet/all - Get all vets
	@GetMapping("/all")
	public ResponseEntity<List<VetDTO>> getAllVets() {
		List<VetDTO> allVets = vetService.getAllVets();
		return new ResponseEntity<>(allVets, HttpStatus.OK);
	}

	// REQUEST:POST @PATH: /vet - Save a vet
	@PostMapping("")
	public ResponseEntity<VetDTO> saveVet(@Valid @RequestBody VetDTO vetDTO) {
		VetDTO vet = vetService.saveVet(vetDTO);
		return new ResponseEntity<>(vet, HttpStatus.CREATED);
	}

	// REQUEST:PUT @PATH: /vet/{id} - Update a vet by id
	@PutMapping("/{id}")
	public ResponseEntity<VetDTO> updateVet(@PathVariable(value = "id") Long id, @Valid @RequestBody VetDTO vetDTO) {
		VetDTO vet = vetService.updateVet(id, vetDTO);
		return new ResponseEntity<>(vet, HttpStatus.CREATED);
	}

	// REQUEST:DELETE @PATH: /vet/{id} - Delete a vet by id
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteVetById(@PathVariable(value = "id") Long id) {
		vetService.deleteVetById(id);
		return new ResponseEntity<>("The vet was deleted succesfully!", HttpStatus.OK);
	}

}
