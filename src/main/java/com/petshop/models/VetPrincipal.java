package com.petshop.models;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class VetPrincipal implements UserDetails {

	private static final long serialVersionUID = -5338048780781990225L;
	private Vet vet;
	private GrantedAuthority authorities;

	public VetPrincipal(Vet vet) {

		this.authorities = authorities;
		this.vet = vet;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		  final List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("USER"));
	        return authorities;
	}

	@Override
	public String getPassword() {
		return vet.getPassword();
	}

	@Override
	public String getUsername() {
		return vet.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public Vet getVetDetails() {
		return vet;
	}

	@Override
	public String toString() {
		return "VetPrincipal [vet=" + vet + "]";
	}

}
