package com.api.codingchallenge.enums;

public enum Reaction {
	GOSTEI(1), NAO_GOSTEI(2);

	private int code;

	private Reaction(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public static Reaction valueOf(int code) {
		for (Reaction value : Reaction.values()) {
			if (code == value.getCode())
				return value;
		}
		throw new IllegalArgumentException("Invalid Reaction code");
	}

}
