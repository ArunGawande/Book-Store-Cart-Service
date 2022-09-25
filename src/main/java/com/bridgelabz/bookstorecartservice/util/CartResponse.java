package com.bridgelabz.bookstorecartservice.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartResponse {
	private int errorCode;
	private String message;
	private Object object;
	
	public CartResponse(int errorCode, String message) {
		super();
		this.errorCode = errorCode;
		this.message = message;
	}
}
