package com.petshop.exception;

public class EmailAlreadyExistsException extends RuntimeException{
	private static final long serialVersionUID = -7545730538036638196L;

	public EmailAlreadyExistsException() {
		super("Email already exists!");
	}
}
