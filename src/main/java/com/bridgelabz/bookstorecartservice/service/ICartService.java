package com.bridgelabz.bookstorecartservice.service;

import com.bridgelabz.bookstorecartservice.dto.CartDTO;
import com.bridgelabz.bookstorecartservice.model.CartModel;

import java.util.List;

public interface ICartService {

	CartModel addCart(CartDTO cartDTO, String token, Long bookId);

	CartModel removingCart(String token, Long cartId);

	CartModel updateQuantity(Long cartId, String token, Integer quantity);

	List<CartModel> getAllCartItemsForUser(String token);

	List<CartModel> getAllCartItems(String token);

}
