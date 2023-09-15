package com.macias34.codemastery.auth.dto;

import lombok.Data;

@Data
public class SignUpDto extends SignInDto {
	private String email;

}
