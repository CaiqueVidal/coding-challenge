package com.api.codingchallenge.dto;

import javax.validation.constraints.NotBlank;

public class LoginDTO {

	@NotBlank(message = "The email field is required.")
	private String email;

	@NotBlank(message = "The password field is required.")
	private String password;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}