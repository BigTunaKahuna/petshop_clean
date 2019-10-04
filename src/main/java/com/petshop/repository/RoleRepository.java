package com.petshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.petshop.models.authority.Authority;
import com.petshop.models.authority.Role;

public interface RoleRepository extends JpaRepository<Authority, Long> {
	Authority findByRole(Role role);
}
