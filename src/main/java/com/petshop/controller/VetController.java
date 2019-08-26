package com.petshop.controller;

import com.petshop.dto.VetDTO;
import com.petshop.service.VetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
	public VetDTO getVetById(@PathVariable(value = "id") Long id) {
		logger.info("Controller");
		return vetService.getVetById(id);
	}

	// REQUEST:GET @PATH: /vet/all
	@GetMapping("/all")
	public List<VetDTO> getAllVets() {
		return vetService.getAllVets();
	}

	// REQUEST:POST @PATH: /vet
	@PostMapping("")
	public VetDTO saveVet(@Valid @RequestBody VetDTO vetDTO) {
		return vetService.saveVet(vetDTO);
	}

	// REQUEST:PUT @PATH: /vet/{id}
	@PutMapping("/{id}")
	public VetDTO updateVet(@PathVariable(value = "id") Long id, @Valid @RequestBody VetDTO vetDTO) {
		return vetService.updateVet(id, vetDTO);
	}

	// REQUEST:DELETE @PATH: /vet/{id}
	@DeleteMapping("/{id}")
	public void deleteVetById(@PathVariable(value = "id") Long id) {
		vetService.deleteVetById(id);
	}

}
