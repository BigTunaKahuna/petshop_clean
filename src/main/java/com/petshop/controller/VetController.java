package com.petshop.controller;

import java.util.List;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.petshop.dto.VetDTO;
import com.petshop.models.Vet;
import com.petshop.service.VetService;

@RestController
@RequestMapping(path = "/vet")
public class VetController {

	@Autowired
	VetService vetService;
	
	Logger logger = LoggerFactory.getLogger(VetController.class);

	// REQUEST:GET @PATH: /vet/{id}
	@GetMapping("/{id}")
	public VetDTO getVetById(@PathVariable(value = "id") Long id) {
		logger.info("Controller");
		return vetService.getVetById(id);
	}

	// REQUEST:GET @PATH: /vet/all
	@GetMapping("/all")
	public List<Vet> getAllVets() {
		return vetService.getAllVets();
	}

	// REQUEST:POST @PATH: /vet
	@PostMapping("")
	public Vet saveVet(@Valid @RequestBody Vet vet) {
		return vetService.saveVet(vet);
	}

	// REQUEST:PUT @PATH: /vet/{id}
	@PutMapping("/{id}")
	public Vet updateVet(@PathVariable(value = "id") Long id, @Valid @RequestBody Vet vet) {
		return vetService.updateVet(id, vet);
	}

	// REQUEST:DELETE @PATH: /vet/{id}
	@DeleteMapping("/{id}")
	public void deleteVetById(@PathVariable(value = "id") Long id) {
		vetService.deleteVetById(id);
	}

}
