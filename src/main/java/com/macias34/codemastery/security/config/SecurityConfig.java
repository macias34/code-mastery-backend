package com.macias34.codemastery.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.macias34.codemastery.user.entity.UserRole;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()
				.requestMatchers(HttpMethod.GET).permitAll()
				.anyRequest().authenticated()
				.and().httpBasic();

		return http.build();

	}

	@Bean
	UserDetailsService users() {
		UserDetails admin = User.builder()
				.username("admin")
				.password("password")
				.roles(UserRole.ADMIN.name())
				.build();

		UserDetails user = User.builder()
				.username("user")
				.password("password")
				.roles(UserRole.USER.name())
				.build();

		return new InMemoryUserDetailsManager(admin, user);
	}
}
