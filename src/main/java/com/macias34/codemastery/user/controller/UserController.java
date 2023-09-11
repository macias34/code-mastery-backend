package com.macias34.codemastery.user.controller;

import java.sql.Timestamp;
import java.util.List;

import com.macias34.codemastery.user.entity.UserEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.macias34.codemastery.user.entity.UserRole;
import com.macias34.codemastery.user.repository.UserRepository;
import com.macias34.codemastery.util.DateTimeUtil;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class UserController {

	private UserRepository userRepository;

	@GetMapping("/users")
	public List<UserEntity> getUsers() {
		return userRepository.findAll();
	}

	@PostMapping("/users")
	public void signUp(@RequestParam UserEntity user) {

		Timestamp createdAt = DateTimeUtil.getCurrentTimestamp();

		UserEntity userToSave = new UserEntity(user.getUsername(), user.getEmail(), user.getPassword(), createdAt,
				UserRole.USER.toString());

		userRepository.save(userToSave);
	}

}
