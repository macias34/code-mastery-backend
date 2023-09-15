package com.macias34.codemastery.auth.dto;

public class SignUpDto extends SignInDto {
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
