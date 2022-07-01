package com.api.codingchallenge.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CPF;

public class UserDTO {

	@NotBlank(message = "The name field is required.")
	@Size(min = 2, max = 120, message = "The length of the name must be between 2 and 120 characters.")
	private String name;

	@CPF(message = "Enter a valid cpf.")
	@NotBlank(message = "The cpf field is required.")
	private String cpf;

	@NotBlank(message = "The email field is required.")
	@Email(message = "Enter a valid email address.")
	private String email;

	@NotBlank(message = "The password field is required.")
	@Size(min = 8, message = "Password length must be longer than 8 characters.")
	private String password;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

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
