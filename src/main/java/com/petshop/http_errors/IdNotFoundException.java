package com.petshop.http_errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Wrong id or it doesn't exist")
public class IdNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 3001720877072869553L;
}
