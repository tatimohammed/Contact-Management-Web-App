package com.app.ContactManagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.app.ContactManagement.service.MyUserDetailsService;

@Deprecated
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	// Core interface which loads user-specific data.
	@Bean
	public UserDetailsService userDetailsService() {
		return new MyUserDetailsService();
	};
	
	// Implementation of PasswordEncoder that uses the BCrypt strong hashing function.
	@Bean
	public BCryptPasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	// An AuthenticationProvider implementation that retrieves user details from a UserDetailsService.
	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider daoAP = new DaoAuthenticationProvider();
		daoAP.setUserDetailsService(userDetailsService());
		daoAP.setPasswordEncoder(getPasswordEncoder());
		
		return daoAP;
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(daoAuthenticationProvider()); // setting the authentication type
		
	}
	
	// securing the endpoints 
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.authorizeRequests()
		.antMatchers("/", "/signup", "/login", "/createUser", "/css/*", "/rsc/*", "/templates/*", "/js/*")
		.permitAll()
		.antMatchers("/home", "/add", "/delete", "/search", "/update", "/group").hasAuthority("USER")
		.anyRequest()
		.authenticated()
		.and()
		.formLogin()
		.loginPage("/login")
		.loginProcessingUrl("/signin")
		.defaultSuccessUrl("/home")
		.and()
		.logout()
		.logoutUrl("/logout")
		.invalidateHttpSession(true)
		.clearAuthentication(true)
		.logoutSuccessUrl("/login")
		.permitAll();
		
		
	}
}
