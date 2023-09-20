package com.macias34.codemastery.user.controller;

import java.util.List;

import com.macias34.codemastery.user.dto.UpdateUserDto;
import com.macias34.codemastery.user.dto.UserDto;
import com.macias34.codemastery.user.entity.UserRole;
import com.macias34.codemastery.user.model.UserFilter;
import com.macias34.codemastery.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	// TODO all needed crud methods
	@GetMapping("")
	public ResponseEntity<List<UserDto>> getUsers(
			@RequestParam(required = false) String username,
			@RequestParam(required = false) String email,
			@RequestParam(required = false) UserRole role) {
		UserFilter userFilter = new UserFilter(username, email, role);
		return ResponseEntity.ok(userService.getAllUsers(userFilter));
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserDto> getUserById(
			@PathVariable int id) {
		return ResponseEntity.ok(userService.getUserById(id));
	}

	@GetMapping("/me")
	public ResponseEntity<UserDto> getCurrentUserDto() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		UserDto userDto = userService.getUserByUsername(authentication.getName());

		return ResponseEntity.ok(userDto);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<UserDto> updateUser(
			@PathVariable int id,
			@RequestBody UpdateUserDto dto) {
		// todo check if someone has username or email
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		String loggedUserUserName = authentication.getName();

		return ResponseEntity.ok(userService.updateUser(id, dto, loggedUserUserName));
	}

}
