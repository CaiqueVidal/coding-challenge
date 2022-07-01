package com.api.codingchallenge.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.codingchallenge.dto.LoginDTO;
import com.api.codingchallenge.dto.SessionDTO;
import com.api.codingchallenge.service.LoginService;

@RestController
@RequestMapping("/login")
public class LoginController {

	@Autowired
	private LoginService loginService;

	@PostMapping
	public ResponseEntity<SessionDTO> login(@RequestBody @Valid LoginDTO loginDTO) {
		return ResponseEntity.status(HttpStatus.OK).body(loginService.signIn(loginDTO));
	}
}
