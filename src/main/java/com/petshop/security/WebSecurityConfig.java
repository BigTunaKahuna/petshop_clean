package com.petshop.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.header.HeaderWriter;
import org.springframework.web.bind.annotation.CrossOrigin;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("username").password(encoder().encode("password")).roles("USER").and()
				.withUser("admin").password(encoder().encode("password")).roles("USER", "ADMIN");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		HeaderWriter foo = (request,response) -> {     
				response.addHeader("Access-Control-Allow-Origin", "*");
				response.addHeader("Access-Control-Request-Headers", "*");
				response.addHeader("Access-Control-Allow-Headers", "*");
				response.setStatus(HttpStatus.OK.value());
				System.out.println(request.getCookies()+ " " + request.getSession());
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

}
