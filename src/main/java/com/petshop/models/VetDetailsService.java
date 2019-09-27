package com.petshop.models;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.petshop.dao.VetDao;

@Service
public class VetDetailsService implements UserDetailsService {

	@Autowired
	private VetDao vetDao;
	
	private String role; 

	@Override
	public UserDetails loadUserByUsername(String username) {
		Vet vet = vetDao.findByEmail(username);
		if (vet == null)
			throw new UsernameNotFoundException("User not found!");
		System.out.println("Vet: "+ vet.toString());
		System.out.println("Vet principal " + new VetPrincipal(vet).getAuthorities());
		System.out.println("Vet principal " + new VetPrincipal(vet).getPassword());
		System.out.println("Vet principal " + new VetPrincipal(vet).toString());
		return new VetPrincipal(vet);
	}

}
