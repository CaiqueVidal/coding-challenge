package com.api.codingchallenge.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.codingchallenge.dto.UserDTO;
import com.api.codingchallenge.dto.UserUpdateDTO;
import com.api.codingchallenge.model.User;
import com.api.codingchallenge.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping
	public ResponseEntity<User> saveUser(@RequestBody @Valid UserDTO userDTO) {
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(userDTO));
	}

	@PutMapping()
	public ResponseEntity<User> updateUser(@RequestBody @Valid UserUpdateDTO userUpdateDTO) {
		return ResponseEntity.status(HttpStatus.OK).body(userService.update(userUpdateDTO));
	}

	@DeleteMapping("/delete-account")
	public ResponseEntity<Void> deleteUser() {
		userService.delete();
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@PutMapping("/make-moderator/{id}")
	public ResponseEntity<User> makeUserModerator(@PathVariable(value = "id") Long userId) {
		return ResponseEntity.status(HttpStatus.OK).body(userService.makeModerator(userId));
	}

}
