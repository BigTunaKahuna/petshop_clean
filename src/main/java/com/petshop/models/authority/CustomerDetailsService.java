package com.petshop.models.authority;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.petshop.dao.CustomerDao;
import com.petshop.models.Customer;

public class CustomerDetailsService implements UserDetailsService {
	@Autowired
	private CustomerDao customerDao;

	@Override
	public UserDetails loadUserByUsername(String username) {
		Customer customer = customerDao.findByEmail(username);
		if (customer == null) {
			throw new UsernameNotFoundException("User not found!");
		}
		return new CustomerPrincipal(customer);
	}
}
