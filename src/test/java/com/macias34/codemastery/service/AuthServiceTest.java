package com.macias34.codemastery.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.macias34.codemastery.auth.dto.SignUpDto;
import com.macias34.codemastery.auth.service.AuthService;
import com.macias34.codemastery.user.entity.UserEntity;
import com.macias34.codemastery.user.repository.UserRepository;

public class AuthServiceTest {
	@Test
	public void shouldCreateUserAndCallSaveOnRepository() {

		UserRepository userRepository = mock(UserRepository.class);
		PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);

		SignUpDto signUpDto = new SignUpDto();
		signUpDto.setUsername("username");
		signUpDto.setEmail("email@mail.com");
		signUpDto.setPassword("password");

		AuthService authService = new AuthService(userRepository, passwordEncoder, null, null);
		authService.createUser(signUpDto);

		verify(userRepository, times(1)).save(any(UserEntity.class));
	}
}
