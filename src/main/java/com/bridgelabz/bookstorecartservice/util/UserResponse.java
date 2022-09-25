package com.bridgelabz.bookstorecartservice.util;

import com.bridgelabz.bookstorecartservice.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
	private int errorCode;
	private String message;
	private UserDTO object;
	
	public UserResponse(int errorCode, String message) {
		super();
		this.errorCode = errorCode;
		this.message = message;
	}
}
