package com.petshop.exception;

public class RoleAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = -2034214833336292310L;

	public RoleAlreadyExistsException() {
		super("Role already exists");
	}

}
