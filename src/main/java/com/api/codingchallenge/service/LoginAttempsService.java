package com.api.codingchallenge.service;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class LoginAttempsService {
	public static final int BLOCKED_MINUTES = 15;

	public static final int RESET_ATTEMPS_INTERVAL = 15;

	private Map<String, List<Instant>> attempsMap = new HashMap<>();

	private List<Instant> getAttemps(String email) {
		return attempsMap.getOrDefault(email, new ArrayList<>());
	}

	public boolean isBlocked(String email) {
		List<Instant> attemps = getAttemps(email);

		if (attemps.size() >= 3) {
			Instant lastAttemp = attemps.get(2);

			long minutes = Duration.between(lastAttemp, Instant.now()).toMinutes();

			if (minutes < BLOCKED_MINUTES) {
				return true;
			}

			attemps.clear();
		}

		return false;
	}

	public void shouldClearAttemps(String email) {
		List<Instant> attemps = getAttemps(email);

		if (attemps.size() > 0) {
			Instant lastLoginAttemp = attemps.get(attemps.size() - 1);

			if (Duration.between(lastLoginAttemp, Instant.now()).toMinutes() >= RESET_ATTEMPS_INTERVAL) {
				attemps.clear();
			}
		}
	}

	public void addAttemp(String email) {
		List<Instant> attemps = getAttemps(email);
		attemps.add(Instant.now());
		attempsMap.put(email, attemps);
	}

	public void clearAttemps(String email) {
		attempsMap.put(email, new ArrayList<>());
	}
}