package com.macias34.codemastery.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.macias34.codemastery.auth.dto.AuthResponseDto;
import com.macias34.codemastery.auth.dto.SignInDto;
import com.macias34.codemastery.auth.dto.SignUpDto;
import com.macias34.codemastery.auth.service.AuthService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

	private AuthService authService;

	@PostMapping("/sign-up")
	public ResponseEntity<String> signUp(@RequestBody SignUpDto signUpDto) {
		authService.createUser(signUpDto);

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("/sign-in")
	public ResponseEntity<AuthResponseDto> signIn(@RequestBody SignInDto signInDto) {

		String token = authService.generateJwt(signInDto);

		return new ResponseEntity<>(new AuthResponseDto(token), HttpStatus.OK);
	}
}
