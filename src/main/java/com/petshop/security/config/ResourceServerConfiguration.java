package com.petshop.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

//	JWT CONFIG
//	@Autowired
//	CustomAccessTokenConverter accessTokenConverter;
	
//	@Autowired
//	TokenStore tokenStore;
//	
//	@Autowired
//	DefaultTokenServices defaultTokenServices;
//	
//	
//	
//	@Override
//	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
//		resources.tokenServices(defaultTokenServices);
//	}
//


	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
			.and()
		.authorizeRequests()
		.antMatchers("/login").permitAll()
		.antMatchers(HttpMethod.POST, "/vet").permitAll()
		.antMatchers(HttpMethod.POST,"/role").permitAll()
		.antMatchers(HttpMethod.POST,"/customer/vet/**").permitAll()
		.anyRequest().authenticated()
		.and()
		.formLogin().permitAll();
	}
	
}
