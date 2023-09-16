package com.macias34.codemastery.user.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.macias34.codemastery.user.entity.UserEntity;
import com.macias34.codemastery.user.repository.UserRepository;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class UserController {

	private UserRepository userRepository;

	@GetMapping("/users")
	public List<UserEntity> getUsers() {
		return userRepository.findAll();
	}

}
