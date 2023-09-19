package com.macias34.codemastery.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.macias34.codemastery.mail.service.MailService;
import com.macias34.codemastery.user.mapper.InvoiceDetailsMapper;
import com.macias34.codemastery.user.mapper.PersonalDetailsMapper;
import com.macias34.codemastery.user.repository.InvoiceDetailsRepository;
import com.macias34.codemastery.user.repository.PersonalDetailsRepository;
import com.macias34.codemastery.user.service.UserService;
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
		InvoiceDetailsRepository invoiceDetailsRepository = mock(InvoiceDetailsRepository.class);
		PersonalDetailsRepository personalDetailsRepository = mock(PersonalDetailsRepository.class);
		PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);

		PersonalDetailsMapper personalDetailsMapper = mock(PersonalDetailsMapper.class);
		InvoiceDetailsMapper invoiceDetailsMapper = mock(InvoiceDetailsMapper.class);
		MailService mailService = mock(MailService.class);
		UserService userService = mock(UserService.class);

		when(userRepository.existsByUsername(anyString())).thenReturn(true);

		SignUpDto signUpDto =  SignUpDto.builder().username("username").email("email@mail.com").password("password").build();

		AuthService authService = new AuthService(userRepository, passwordEncoder, null, null,invoiceDetailsMapper,personalDetailsMapper,invoiceDetailsRepository,personalDetailsRepository,mailService,userService);

		assertThrows(ResourceAlreadyExistsException.class, () -> {
			authService.createUser(signUpDto);
		});
	}

}
