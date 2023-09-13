package com.macias34.codemastery.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.macias34.codemastery.auth.dto.AuthResponseDto;
import com.macias34.codemastery.auth.dto.SignInDto;
import com.macias34.codemastery.auth.dto.SignUpDto;
import com.macias34.codemastery.security.jwt.JwtGenerator;
import com.macias34.codemastery.user.entity.UserEntity;
import com.macias34.codemastery.user.entity.UserRole;
import com.macias34.codemastery.user.repository.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

	private AuthenticationManager authenticationManager;
	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;
	private JwtGenerator jwtGenerator;

	@PostMapping("/sign-up")
	public ResponseEntity<String> signUp(@RequestBody SignUpDto signUpDto) {
		if (userRepository.existsByUsername(signUpDto.getUsername())) {
			return new ResponseEntity<String>("Username is taken.", HttpStatus.BAD_REQUEST);
		}

		UserEntity user = new UserEntity();
		user.setUsername(signUpDto.getUsername());
		user.setEmail(signUpDto.getEmail());
		user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
		user.setRole(UserRole.USER);

		userRepository.save(user);

		return new ResponseEntity<>("User signed up succesfully.", HttpStatus.OK);
	}

	@PostMapping("/sign-in")
	public ResponseEntity<AuthResponseDto> signIn(@RequestBody SignInDto signInDto) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(signInDto.getUsername(), signInDto.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtGenerator.generateToken(authentication);

		return new ResponseEntity<>(new AuthResponseDto(token), HttpStatus.OK);
	}
}
