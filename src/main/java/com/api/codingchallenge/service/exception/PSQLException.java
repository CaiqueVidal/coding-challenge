package com.api.codingchallenge.service.exception;

public class PSQLException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public PSQLException(String msg) {
		super(msg);
	}
}
