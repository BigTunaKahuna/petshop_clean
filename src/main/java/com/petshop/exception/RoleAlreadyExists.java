package com.petshop.exception;

public class RoleAlreadyExists extends RuntimeException {

	private static final long serialVersionUID = -2034214833336292310L;

	public RoleAlreadyExists() {
		super("Role already exists");
	}

}
