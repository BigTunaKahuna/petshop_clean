package com.petshop.dao;

import java.util.List;

import com.petshop.models.authority.Authority;
import com.petshop.models.authority.Role;

public interface AuthorityDao {

	Authority saveAuthority(Authority authority);

	Authority findByRole(Role role);

	List<Authority> getAllRoles();

}
