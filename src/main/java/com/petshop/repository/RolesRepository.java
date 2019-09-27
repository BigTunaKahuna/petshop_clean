package com.petshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.petshop.models.Authority;

public interface RolesRepository extends JpaRepository<Authority, Long> {

}
