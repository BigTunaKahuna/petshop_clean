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

	VetDetailsService vetDetailsService;
	CustomerDetailsService customerDetailsService;

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
			.antMatchers(HttpMethod.GET, "/vet/**").hasAuthority("ADMIN")
			.antMatchers(HttpMethod.GET,"/customer/**").hasAuthority("USER")
			.antMatchers(HttpMethod.POST, "/vet").permitAll()
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
