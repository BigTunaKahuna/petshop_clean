package com.petshop.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.petshop.models.authority.customer.CustomerDetailsService;
import com.petshop.models.authority.vet.VetDetailsService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private VetDetailsService vetDetailsService;
	private CustomerDetailsService customerDetailsService;
	private static final String ADMIN = "ADMIN";
	private static final String USER = "USER";

	@Autowired
	public WebSecurityConfig(VetDetailsService vetDetailsService, CustomerDetailsService customerDetailsService) {
		this.vetDetailsService = vetDetailsService;
		this.customerDetailsService = customerDetailsService;
	}
	

	@Bean
	protected AuthenticationProvider vetAuthProvider() {
		final DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(vetDetailsService);
		provider.setPasswordEncoder(encoder());
		return provider;
	}
	
	@Bean
	protected AuthenticationProvider customerAuthProvider() {
		final DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(customerDetailsService);
		provider.setPasswordEncoder(encoder());
		return provider;
		
	}

	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
			// Vet path
			.antMatchers(HttpMethod.GET, "/vet/1").hasAuthority(ADMIN)
			.antMatchers(HttpMethod.GET, "vet/all").hasAuthority(ADMIN)
			.antMatchers(HttpMethod.POST, "/vet").permitAll()
			.antMatchers(HttpMethod.PUT, "/vet/1").hasAuthority(ADMIN)
			.antMatchers(HttpMethod.DELETE, "/vet/1").hasAuthority(ADMIN)
			// Customer path
			.antMatchers(HttpMethod.GET,"/customer/1").hasAuthority(USER)
			.antMatchers(HttpMethod.GET, "/customer/all").hasAuthority(USER)
			.antMatchers(HttpMethod.POST, "/customer/vet/1").permitAll()
			.antMatchers(HttpMethod.PUT, "/customer/1").hasAuthority(USER)
			.antMatchers(HttpMethod.PUT, "/customer/1/1").hasAuthority(USER)
			.antMatchers(HttpMethod.DELETE, "/customer/1").hasAuthority(USER)
			// Authority path
			.antMatchers(HttpMethod.GET, "/role/all").hasAuthority(ADMIN)
			// Permit all for testing purposes
			.antMatchers(HttpMethod.POST, "/role").permitAll()
			.antMatchers(HttpMethod.POST, "/role/vet/1/ROLE").hasAuthority(ADMIN)
			.antMatchers(HttpMethod.POST, "/role/customer/1/ROLE").hasAuthority(ADMIN)
			.antMatchers(HttpMethod.PUT, "/role/change/vet/1/ROLE/ROLE").hasAuthority(ADMIN)
			.antMatchers(HttpMethod.PUT, "/role/change/customer/1/ROLE/ROLE").hasAnyAuthority(ADMIN)
			.antMatchers(HttpMethod.DELETE, "/role/vet/1/ROLE").hasAuthority(ADMIN)
			.antMatchers(HttpMethod.DELETE, "/role/customer/1/ROLE").hasAuthority(ADMIN)
			
			.and()
			.formLogin()
			.and()
			.logout()
			.and()
			.httpBasic()
			.and()
			.csrf().disable();	
		}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) {
		auth.authenticationProvider(vetAuthProvider());
		auth.authenticationProvider(customerAuthProvider());
	}

	@Bean
	public BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

}
