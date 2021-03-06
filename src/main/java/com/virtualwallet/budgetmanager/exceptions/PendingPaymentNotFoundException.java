package com.virtualwallet.budgetmanager.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PendingPaymentNotFoundException extends RuntimeException {

	public PendingPaymentNotFoundException(String menssage) {
		super(menssage);
	}

	private static final long serialVersionUID = 1L;

}
