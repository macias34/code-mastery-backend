package com.macias34.codemastery.auth.dto;

import com.macias34.codemastery.user.dto.UserDto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignInResponse {
	private String accessToken;
	private UserDto user;
}
