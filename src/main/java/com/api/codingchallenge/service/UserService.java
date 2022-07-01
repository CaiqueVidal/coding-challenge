package com.api.codingchallenge.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.api.codingchallenge.dto.UserDTO;
import com.api.codingchallenge.dto.UserUpdateDTO;
import com.api.codingchallenge.enums.Role;
import com.api.codingchallenge.model.User;
import com.api.codingchallenge.repository.UserRepository;
import com.api.codingchallenge.service.exception.PSQLException;
import com.api.codingchallenge.service.exception.ResourceNotFoundException;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder encoder;

	public List<User> findAll() {
		return userRepository.findAll();
	}

	public User findById(Long userId) {
		return userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User id not found " + userId));
	}

	@Transactional
	public User save(UserDTO userDTO) {
		checkEmailUniqueness(userDTO.getEmail(), null);
		checkCpfUniqueness(userDTO.getCpf(), null);
		var user = new User();
		BeanUtils.copyProperties(userDTO, user);
		user.setPassword(encoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	@Transactional
	public User update(UserUpdateDTO userUpdateDTO) {
		User currentUser = getCurrentUser();
		checkEmailUniqueness(userUpdateDTO.getEmail(), currentUser.getId());
		checkCpfUniqueness(userUpdateDTO.getCpf(), currentUser.getId());

		User user = currentUser;
		user.setName(userUpdateDTO.getName());
		user.setCpf(userUpdateDTO.getCpf());
		user.setEmail(userUpdateDTO.getEmail());
		if (userUpdateDTO.getPassword() != null)
			user.setPassword(encoder.encode(userUpdateDTO.getPassword()));

		return userRepository.save(user);

	}

	@Transactional
	public void delete() {
		User currentUser = getCurrentUser();
		userRepository.deleteById(currentUser.getId());
	}

	public User getCurrentUser() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		return userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User id not found"));
	}

	@Transactional
	public User makeModerator(Long userId) {
		try {
			User user = userRepository.findById(userId).get();
			user.setRole(Role.MODERADOR);
			return userRepository.save(user);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("User id not found " + userId);
		}

	}

	@Transactional
	protected void addScore(Long userId) {
		User user = userRepository.findById(userId).get();
		Integer newScore = user.getScore() + 1;

		if (user.getRole() != Role.MODERADOR)
			user.setRole(checkRole(newScore));

		user.setScore(newScore);
		userRepository.save(user);
	}

	private Role checkRole(Integer score) {
		if (score >= 1000)
			return Role.MODERADOR;

		if (score >= 100)
			return Role.AVANCADO;

		if (score >= 20)
			return Role.BASICO;

		return Role.LEITOR;
	}

	private void checkEmailUniqueness(String email, Long userId) {
		Optional<User> userByEmail = userRepository.findByEmail(email);
		if (userByEmail.isPresent() && userByEmail.get().getId() != userId) {
			throw new PSQLException("Registered email already exists " + email);
		}
	}

	private void checkCpfUniqueness(String cpf, Long userId) {
		Optional<User> userByCpf = userRepository.findByCpf(cpf);
		if (userByCpf.isPresent() && userByCpf.get().getId() != userId) {
			throw new PSQLException("Registered cpf already exists " + cpf);
		}
	}

}
