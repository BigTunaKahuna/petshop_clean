package com.petshop.controller;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.petshop.models.Vet;
import com.petshop.repository.VetRepository;
import com.petshop.service.VetService;

import exception.ResourceNotFoundException;

@RestController
@RequestMapping(path = "/api")
public class VetController {

	@Autowired
	VetRepository vetRepository;

	@Autowired
	VetService vetService;

	// REQUEST:GET @PATH: /api/vets
	@GetMapping("/vets")
	public List<Vet> getAllVet() {
		return vetRepository.findAll();
	}

	// REQUEST:GET @PATH: /api/vet/{id}
	@GetMapping("/vet/{id}")
	public Vet getVet(@PathVariable(value = "id") Long id) {
		return vetService.getVetById(id);
	}

	// REQUEST:POST @PATH: /api/vet
	@PostMapping("/vet")
	public Vet postVet(@Valid @RequestBody Vet vet) {
		return vetRepository.save(vet);

	}

	// REQUEST:PUT @PATH: /api/vet/{id}
	@PutMapping("/vet/{id}")
	public Vet vet(@PathVariable(value = "id") Long id, @Valid @RequestBody Vet vet) {
		return vetRepository.findById(id).map(vetPar -> {
			vetPar.setName(vet.getName());
			vetPar.setEmail(vet.getEmail());
			vetPar.setAge(vet.getAge());
			vetPar.setYearsOfExperience(vet.getYearsOfExperience());
			return vetRepository.save(vetPar);
		}).orElseThrow(() -> new ResourceNotFoundException("Vet", "id", id));
	}

	// REQUEST:DELETE @PATH: /api/vet/{id}
	@DeleteMapping("/vet/{id}")
	public ResponseEntity<?> deleteVet(@PathVariable(value = "id") Long id) {
		return vetRepository.findById(id).map(idDel -> {
			vetRepository.delete(idDel);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new ResourceNotFoundException("Vet", "id", id));
	}

}
