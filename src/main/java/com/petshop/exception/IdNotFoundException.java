package com.petshop.exception;

public class IdNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 6054637847318577805L;

	public IdNotFoundException() {
		super("The id is wrong or it doesn't exist!");
	}
}
