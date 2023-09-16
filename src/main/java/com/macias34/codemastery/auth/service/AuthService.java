package com.macias34.codemastery.auth.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.macias34.codemastery.auth.dto.SignInDto;
import com.macias34.codemastery.auth.dto.SignUpDto;
import com.macias34.codemastery.exception.ResourceAlreadyExistsException;
import com.macias34.codemastery.exception.ResourceNotFoundException;
import com.macias34.codemastery.exception.WrongCredentialsException;
import com.macias34.codemastery.security.jwt.JwtGenerator;
import com.macias34.codemastery.user.entity.UserEntity;
import com.macias34.codemastery.user.entity.UserRole;
import com.macias34.codemastery.user.repository.UserRepository;
import com.macias34.codemastery.util.DtoValidator;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class AuthService {

	private static final String USER_EXISTS_MESSAGE = "User with username %s already exists.";
	private static final String EMAIL_EXISTS_MESSAGE = "User with email %s already exists.";
	private static final String USER_DOESNT_EXIST_MESSAGE = "User with username %s doesn't exist.";
	private static final String WRONG_CREDENTIALS_MESSAGE = "Username %s has a different password.";

	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;
	private AuthenticationManager authenticationManager;
	private JwtGenerator jwtGenerator;

	public String authenticateAndGenerateJwt(SignInDto signInDto) throws BadCredentialsException {
		Authentication authentication = authenticate(signInDto);
		return jwtGenerator.generateToken(authentication);
	}

	private Authentication authenticate(SignInDto signInDto) {
		try {
			return authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(signInDto.getUsername(), signInDto.getPassword()));
		} catch (BadCredentialsException e) {
			throw new WrongCredentialsException(String.format(WRONG_CREDENTIALS_MESSAGE, signInDto.getUsername()));
		}
	}

	@Transactional
	public void createUser(SignUpDto signUpDto) {
		DtoValidator.validate(signUpDto);

		checkIfUserExists(signUpDto);

		UserEntity user = createUserEntity(signUpDto);

		userRepository.save(user);

	}

	private UserEntity createUserEntity(SignUpDto signUpDto) {
		UserEntity user = new UserEntity();
		user.setUsername(signUpDto.getUsername());
		user.setEmail(signUpDto.getEmail());
		user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
		user.setRole(UserRole.USER);

		return user;
	}

	public void checkIfUserExists(SignUpDto signUpDto) throws ResourceAlreadyExistsException {
		if (userRepository.existsByUsername(signUpDto.getUsername())) {
			throw new ResourceAlreadyExistsException(
					String.format(USER_EXISTS_MESSAGE, signUpDto.getUsername()));
		}

		if (userRepository.existsByEmail(signUpDto.getEmail())) {
			throw new ResourceAlreadyExistsException(
					String.format(EMAIL_EXISTS_MESSAGE, signUpDto.getEmail()));
		}
	}

	public void checkIfUserDoesntExist(SignInDto signInDto) throws ResourceNotFoundException {
		if (!userRepository.existsByUsername(signInDto.getUsername())) {
			throw new ResourceNotFoundException(
					String.format(USER_DOESNT_EXIST_MESSAGE, signInDto.getUsername()));
		}
	}

}
