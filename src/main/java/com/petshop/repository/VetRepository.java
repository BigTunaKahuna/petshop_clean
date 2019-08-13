package com.petshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.petshop.models.Vet;

@Repository
public interface VetRepository extends JpaRepository<Vet, Long>{

}
