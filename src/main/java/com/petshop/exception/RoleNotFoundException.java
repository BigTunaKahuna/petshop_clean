package com.petshop.exception;

public class RoleNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 2195217462326535602L;
	
	public RoleNotFoundException() {
		super("Role not found");
	}
}
