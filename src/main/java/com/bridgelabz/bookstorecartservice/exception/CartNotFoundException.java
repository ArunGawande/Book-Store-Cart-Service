package com.bridgelabz.bookstorecartservice.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class CartNotFoundException extends RuntimeException{
	private int statusCode;
	private String message;
	
	public CartNotFoundException(int statusCode, String message) {
		super();
		this.statusCode = statusCode;
		this.message = message;
	}
	
}
