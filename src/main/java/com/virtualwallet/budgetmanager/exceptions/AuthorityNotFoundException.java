package com.virtualwallet.budgetmanager.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AuthorityNotFoundException extends RuntimeException {
	public AuthorityNotFoundException(String menssage) {
		super(menssage);
	}

	private static final long serialVersionUID = 1L;
}
