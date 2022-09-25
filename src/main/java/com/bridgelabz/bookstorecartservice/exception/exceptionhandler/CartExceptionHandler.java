package com.bridgelabz.bookstorecartservice.exception.exceptionhandler;

import com.bridgelabz.bookstorecartservice.exception.CartNotFoundException;
import com.bridgelabz.bookstorecartservice.util.CartResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;


@ControllerAdvice
public class CartExceptionHandler {
	@ExceptionHandler(CartNotFoundException.class)
	 public ResponseEntity<CartResponse> response(CartNotFoundException cartNotFoundException) {
		CartResponse cartResponse = new CartResponse();
		cartResponse.setErrorCode(400);
		cartResponse.setMessage(cartNotFoundException.getMessage());
        return new ResponseEntity<>(cartResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
	
	@ExceptionHandler(value = Exception.class)
    public ResponseEntity<CartResponse> defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
		CartResponse cartResponse = new CartResponse();
		cartResponse.setErrorCode(500);
		cartResponse.setMessage(e.getMessage());
        return new ResponseEntity<CartResponse>(cartResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
