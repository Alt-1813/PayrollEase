package com.self.paytrack.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import com.self.paytrack.employee.service.CustomUserDetailService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class MySecurityConfig {

	@Autowired
	private CustomUserDetailService customUserDetailService;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
		.authorizeHttpRequests()		
		.requestMatchers(HttpMethod.GET, "/employees").permitAll()
        .requestMatchers(HttpMethod.POST, "/createEmployee").permitAll()
        .requestMatchers(HttpMethod.PUT, "/employee/**").permitAll()
        .requestMatchers(HttpMethod.DELETE, "/employee/**").permitAll()
        .requestMatchers(HttpMethod.GET, "/generate-payslip/**").permitAll()
        .anyRequest()
		.authenticated()
		.and()
		.httpBasic(); 

		return http.csrf().disable().build();
	}

	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserDetailService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(12);
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

}
