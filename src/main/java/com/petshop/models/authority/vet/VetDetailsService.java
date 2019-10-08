package com.petshop.models.authority.vet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.petshop.dao.VetDao;
import com.petshop.models.Vet;

@Service
public class VetDetailsService implements UserDetailsService {

	private static final Logger log = LoggerFactory.getLogger(VetDetailsService.class);

	@Autowired
	private VetDao vetDao;

	@Override
	public UserDetails loadUserByUsername(String username) {
		Vet vet = vetDao.findByEmail(username);
		if (vet == null)
			throw new UsernameNotFoundException("User not found!");

		log.warn("Parola este: {}", vet.getPassword());
		return new VetPrincipal(vet);
	}

}
