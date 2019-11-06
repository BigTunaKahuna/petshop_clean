package com.petshop.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
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
@Order(Ordered.LOWEST_PRECEDENCE)
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
			.antMatchers("/oauth/**","/login**","/error").permitAll()
			// Vet path
			.antMatchers(HttpMethod.GET, "/vet/**").hasAuthority(ADMIN)
			.antMatchers(HttpMethod.GET, "vet/all").hasAuthority(ADMIN)
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
			.anyRequest().authenticated()
			.and()
			.formLogin().permitAll()
			.and()
			.logout()
			.and()
			.csrf().disable()
			.httpBasic().disable();	
		
		}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(vetAuthProvider());
		auth.authenticationProvider(customerAuthProvider());
//		auth.inMemoryAuthentication().withUser("username").password(encoder().encode("password")).roles("USER");
//		auth.userDetailsService(vetDetailsService);
//		auth.userDetailsService(customerDetailsService);
	}
	

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

}
