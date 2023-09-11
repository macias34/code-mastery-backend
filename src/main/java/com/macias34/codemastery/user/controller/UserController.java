package com.macias34.codemastery.user.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.macias34.codemastery.user.model.User;
import com.macias34.codemastery.user.model.UserRole;
import com.macias34.codemastery.user.repository.UserRepository;
import com.macias34.codemastery.util.DateTimeUtil;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class UserController {

	private UserRepository userRepository;

	@GetMapping("/users")
	public List<User> getUsers() {
		return userRepository.findAll();
	}

	@PostMapping("/users")
	public void signUp(@RequestBody User user) {

		Timestamp createdAt = DateTimeUtil.getCurrentTimestamp();

		User userToSave = new User(user.getUsername(), user.getEmail(), user.getPassword(), createdAt,
				UserRole.USER.toString());

		userRepository.save(userToSave);
	}

}
