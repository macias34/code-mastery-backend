package com.macias34.codemastery.auth.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.macias34.codemastery.auth.dto.SignInDto;
import com.macias34.codemastery.auth.dto.SignUpDto;
import com.macias34.codemastery.exception.ResourceAlreadyExistsException;
import com.macias34.codemastery.security.jwt.JwtGenerator;
import com.macias34.codemastery.user.entity.UserEntity;
import com.macias34.codemastery.user.repository.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class AuthService {

	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;
	private AuthenticationManager authenticationManager;
	private JwtGenerator jwtGenerator;

	public void createUser(SignUpDto signUpDto) {
		checkIfUserExists(signUpDto);

		UserEntity user = new UserEntity();
		user.setUsername(signUpDto.getUsername());
		user.setEmail(signUpDto.getEmail());
		user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

		userRepository.save(user);

	}

	public void checkIfUserExists(SignUpDto signUpDto) throws ResourceAlreadyExistsException {
		if (userRepository.existsByUsername(signUpDto.getUsername())) {
			throw new ResourceAlreadyExistsException(
					"User with username " + signUpDto.getUsername() + " already exists.");
		}

		if (userRepository.existsByEmail(signUpDto.getEmail())) {
			throw new ResourceAlreadyExistsException(
					"User with email " + signUpDto.getEmail() + " already exists.");
		}
	}

	public String generateJwt(SignInDto signInDto) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(signInDto.getUsername(), signInDto.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtGenerator.generateToken(authentication);

		return token;
	}

}
