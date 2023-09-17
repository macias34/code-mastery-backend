package com.macias34.codemastery.user.controller;

import java.util.List;

import com.macias34.codemastery.user.dto.UserDto;
import com.macias34.codemastery.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.macias34.codemastery.user.entity.UserEntity;
import com.macias34.codemastery.user.repository.UserRepository;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	// TODO all needed crud methods
	@GetMapping("")
	public ResponseEntity<List<UserDto>> getUsers() {
		// TODO Pagination and searching
		return ResponseEntity.ok(userService.getAllUsers());
	}

}
