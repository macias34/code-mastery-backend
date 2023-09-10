package com.macias34.codemastery.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.macias34.codemastery.user.model.User;
import com.macias34.codemastery.user.repository.UserRepository;

import lombok.AllArgsConstructor;

@RestController
public class UserController {

	private UserRepository userRepository;

	public UserController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@GetMapping("/users")
	public List<User> getUsers() {
		return userRepository.findAll();
	}
}
