package com.petshop.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.header.HeaderWriter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	UserDetailsService userDetailsService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("username").password(encoder().encode("password")).roles("USER").and()
				.withUser("admin").password(cryptPassword("password")).roles("USER", "ADMIN");
	}
	
	protected AuthenticationProvider authProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		return provider;
		
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		HeaderWriter foo = (request,response) -> {     
				response.addHeader("Access-Control-Allow-Origin", "*");
				response.addHeader("Access-Control-Request-Headers", "*");
				response.addHeader("Access-Control-Allow-Headers", "*");
				response.setStatus(HttpStatus.OK.value());
				request.getCookies();
		};
		http
			.headers().addHeaderWriter(foo).and()			
			.httpBasic().and()
			.authorizeRequests()
			.antMatchers(HttpMethod.POST,"/login").permitAll()
			.antMatchers(HttpMethod.GET, "/vet/1").hasRole("USER")
			.antMatchers(HttpMethod.GET, "/vet/all").hasRole("ADMIN")
			.antMatchers(HttpMethod.POST, "/vet/**").hasRole("USER")
			.antMatchers(HttpMethod.GET, "/customer/**").hasRole("ADMIN")
			.anyRequest().authenticated().and()			
			.formLogin().loginPage("/login").permitAll().and()
			.logout().and()
			.csrf().disable();
	}

	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public String cryptPassword(String password) {
		String salt = BCrypt.gensalt();
		return BCrypt.hashpw(password, salt);
	}

}
