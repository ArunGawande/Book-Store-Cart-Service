package com.bridgelabz.bookstorecartservice.service;

import com.bridgelabz.bookstorecartservice.dto.CartDTO;
import com.bridgelabz.bookstorecartservice.exception.CartNotFoundException;
import com.bridgelabz.bookstorecartservice.model.CartModel;
import com.bridgelabz.bookstorecartservice.repository.CartServiceRepository;
import com.bridgelabz.bookstorecartservice.util.BookResponse;
import com.bridgelabz.bookstorecartservice.util.TokenUtil;
import com.bridgelabz.bookstorecartservice.util.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

/*
 * Purpose :All Service implementation of CartService
 */

@Service
@Slf4j
public class CartService implements ICartService{
	@Autowired
	CartServiceRepository cartServiceRepository;
	@Autowired
	 RestTemplate restTemplate;
	@Autowired
	TokenUtil tokenUtil;


	@Override
	public CartModel addCart(CartDTO cartDTO, String token, Long bookId) {
		Long userId = tokenUtil.decodeToken(token);
		UserResponse isUserPresent = restTemplate.getForObject("http://BOOKSTORE-USERSERVICE:8081/bookstoreuser/validateuser/" + userId, UserResponse.class);
		if (isUserPresent.getErrorCode() == 200) {
			BookResponse isBookPresent = restTemplate.getForObject("http://BOOKSTORE-BOOKSERVICE:8082/bookdetails/validatebookId/" + bookId, BookResponse.class);
			if (isBookPresent.getErrorCode() == 200) {
				CartModel cartModel = new CartModel(cartDTO);
				cartModel.setUserId(userId);
				cartModel.setBookId(bookId);
				if (isBookPresent.getObject().getBookQuantity()>=cartDTO.getQuantity()) {
					cartModel.setQuantity(cartDTO.getQuantity());
					cartModel.setTotalPrice((cartDTO.getQuantity()) * (isBookPresent.getObject().getBookPrice()));	
					cartServiceRepository.save(cartModel);
					BookResponse isBookIdPresent = restTemplate.getForObject("http://BOOKSTORE-BOOKSERVICE:8081/bookdetails/updatebookquantity/" +
					bookId +"/"+ cartDTO.getQuantity(), BookResponse.class);
					return cartModel;
				}
				throw new CartNotFoundException(500, cartDTO.getQuantity() + "Books are not available, now only" 
						+isBookPresent.getObject().getBookQuantity() +"Books Are Available");
			}
			throw new CartNotFoundException(500, "Book is Not Available");
		}
		throw new CartNotFoundException(500, "User is Not Available");
	}


	@Override
	public CartModel removingCart(String token, Long cartId) {
		Long userId = tokenUtil.decodeToken(token);
		UserResponse isUserPresent = restTemplate.getForObject("http://BOOKSTORE-USERSERVICE:8081/bookstoreuser/validateuser/" +
		userId, UserResponse.class);
		if (isUserPresent.getErrorCode() == 200) {
			Optional<CartModel> isCartPresent = cartServiceRepository.findById(cartId);
			if (isCartPresent.isPresent()) {
				if (isCartPresent.get().getUserId() == userId) {
					cartServiceRepository.delete(isCartPresent.get());
					BookResponse isBookIdPresent = restTemplate.getForObject("http://BOOKSTORE-BOOKSERVICE:8082/bookdetails/updatequantity/" +
					isCartPresent.get().getBookId() +"/"+ isCartPresent.get().getQuantity(), BookResponse.class);
					return isCartPresent.get();
				}
				throw new CartNotFoundException(500, "Invalid User");
			}
			throw new CartNotFoundException(500, "Cart Id is Not Available");
		}
		throw new CartNotFoundException(500, "User is Not Available");
	}

	@Override
	public CartModel updateQuantity(Long cartId, String token, Integer quantity) {
		Long userId = tokenUtil.decodeToken(token);
		UserResponse isUserPresent = restTemplate.getForObject("http://BOOKSTORE-USERSERVICE:8081/bookstoreuser/validateuser/" + userId, UserResponse.class);
		if (isUserPresent.getErrorCode() == 200) {
			Optional<CartModel> isCartPresent = cartServiceRepository.findById(cartId);
			if (isCartPresent.isPresent()) {
				BookResponse isBookPresent = restTemplate.getForObject("http://BOOKSTORE-BOOKSERVICE:8082/bookdetails/validatebookId/" +
			isCartPresent.get().getBookId(), BookResponse.class);
				if (isCartPresent.get().getUserId() == userId) {
					if (isCartPresent.get().getQuantity() > quantity) {
						int bookQuantity = isCartPresent.get().getQuantity()-quantity;
						isCartPresent.get().setQuantity(quantity);
						isCartPresent.get().setTotalPrice(quantity * isBookPresent.getObject().getBookPrice());
						cartServiceRepository.save(isCartPresent.get());
						BookResponse isBookIdPresent = restTemplate.getForObject("http://BOOKSTORE-BOOKSERVICE:8082/bookdetails/updatequantity/" +
						isCartPresent.get().getBookId() +"/"+ bookQuantity, BookResponse.class);
						return isCartPresent.get();
					}
					else {
						int bookQuantity = quantity-isCartPresent.get().getQuantity();
						isCartPresent.get().setQuantity(quantity);
						isCartPresent.get().setTotalPrice(quantity * isBookPresent.getObject().getBookPrice());
						cartServiceRepository.save(isCartPresent.get());
						BookResponse isBookIdPresent = restTemplate.getForObject("http://BOOKSTORE-BOOKSERVICE:8082/bookdetails/updatebookquantity/" +
						isCartPresent.get().getBookId() +"/"+ bookQuantity, BookResponse.class);
						return isCartPresent.get();
					}
				}
			}
			throw new CartNotFoundException(500, "Cart id is Not Available");
		}
		throw new CartNotFoundException(500, "User is Not Available");
	}

	@Override
	public List<CartModel> getAllCartItems(String token) {
		Long userId = tokenUtil.decodeToken(token);
		UserResponse isUserPresent = restTemplate.getForObject("http://BOOKSTORE-USERSERVICE:8081/bookstoreuser/validateuser/" + userId, UserResponse.class);
		if (isUserPresent.getErrorCode() == 200) {
			List<CartModel> isCartPresent = cartServiceRepository.findAll();
			if (isCartPresent.size()>0) {
				return isCartPresent;
			}
			throw new CartNotFoundException(500, "Cart item list is empty");
		}
		throw new CartNotFoundException(500, "User is Not Available");
	}
	@Override
	public List<CartModel> getAllCartItemsForUser(String token) {
		Long userId = tokenUtil.decodeToken(token);
		UserResponse isUserPresent = restTemplate.getForObject("http://BOOKSTORE-USERSERVICE:8081/bookstoreuser/validateuser/" + userId, UserResponse.class);
		if (isUserPresent.getErrorCode() == 200) {
			List<CartModel> isCartPresent = cartServiceRepository.findByUserId(userId);
			if (isCartPresent.size()>0) {
				return isCartPresent;
			}
			throw new CartNotFoundException(500, "Cart item list is empty");
		}
		throw new CartNotFoundException(500, "User is Not Available");
	}



}
