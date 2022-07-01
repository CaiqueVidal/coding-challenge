package com.api.codingchallenge.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.api.codingchallenge.dto.LoginDTO;
import com.api.codingchallenge.dto.SessionDTO;
import com.api.codingchallenge.model.User;
import com.api.codingchallenge.repository.UserRepository;
import com.api.codingchallenge.security.JWTCreator;
import com.api.codingchallenge.security.JWTObject;
import com.api.codingchallenge.security.SecurityConfig;

@Service
public class LoginService {

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private LoginAttempsService attempsService;

	public SessionDTO signIn(LoginDTO loginDTO) {
		User user = userRepository.findByEmail(loginDTO.getEmail()).orElse(null);
		
		if (user != null) {
			if (attempsService.isBlocked(loginDTO.getEmail())) {
				throw new RuntimeException("Repeat limit exceeded! Try again later");
			}
			
			boolean invalidPassword = !encoder.matches(loginDTO.getPassword(), user.getPassword());
			if (invalidPassword) {
				attempsService.shouldClearAttemps(loginDTO.getEmail());
				attempsService.addAttemp(loginDTO.getEmail());
				throw new RuntimeException("invalid credentials");
			}
			
			attempsService.clearAttemps(loginDTO.getEmail());
			
			SessionDTO sessionDTO = new SessionDTO();
			sessionDTO.setLogin(user.getEmail());

			JWTObject jwtObject = new JWTObject();
			jwtObject.setSubject(loginDTO.getEmail());
			jwtObject.setIssuedAt(new Date(System.currentTimeMillis()));
			jwtObject.setExpiration((new Date(System.currentTimeMillis() + SecurityConfig.EXPIRATION)));
			jwtObject.setRoles(user.getRole().name());
			sessionDTO.setToken(JWTCreator.create(SecurityConfig.PREFIX, SecurityConfig.KEY, jwtObject));
			return sessionDTO;
		} else {
			throw new RuntimeException("Error trying to login");
		}
	}
}
