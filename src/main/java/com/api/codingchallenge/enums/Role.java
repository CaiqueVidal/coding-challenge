package com.api.codingchallenge.enums;

public enum Role {
	LEITOR(1), BASICO(2), AVANCADO(3), MODERADOR(4);

	private int code;

	private Role(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public static Role valueOf(int code) {
		for (Role value : Role.values()) {
			if (code == value.getCode())
				return value;
		}
		throw new IllegalArgumentException("Invalid Role code");
	}
}
