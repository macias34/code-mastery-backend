package com.macias34.codemastery.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.macias34.codemastery.auth.dto.SignUpDto;
import com.macias34.codemastery.auth.service.AuthService;
import com.macias34.codemastery.exception.ResourceAlreadyExistsException;
import com.macias34.codemastery.user.repository.UserRepository;

public class AuthServiceTest {
	@Test
	public void shouldThrowExceptionWhenUserAlreadyExists() {
		UserRepository userRepository = mock(UserRepository.class);
		PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);

		when(userRepository.existsByUsername(anyString())).thenReturn(true);

		SignUpDto signUpDto = new SignUpDto();
		signUpDto.setUsername("username");
		signUpDto.setEmail("email@mail.com");
		signUpDto.setPassword("password");

		AuthService authService = new AuthService(userRepository, passwordEncoder, null, null);

		assertThrows(ResourceAlreadyExistsException.class, () -> {
			authService.createUser(signUpDto);
		});
	}

}
