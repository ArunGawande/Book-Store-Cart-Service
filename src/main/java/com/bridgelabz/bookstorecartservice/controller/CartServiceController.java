package com.bridgelabz.bookstorecartservice.controller;

import com.bridgelabz.bookstorecartservice.dto.CartDTO;
import com.bridgelabz.bookstorecartservice.model.CartModel;
import com.bridgelabz.bookstorecartservice.service.ICartService;
import com.bridgelabz.bookstorecartservice.util.CartResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
 * Purpose : All CartService Data API's
 */

@RestController
@RequestMapping("/cartservice")
public class CartServiceController {
	@Autowired
	ICartService cartService;
	

	
	@PostMapping("/addcart")
	public ResponseEntity<CartResponse> addCart(@RequestBody CartDTO cartDTO, @RequestHeader String token, @RequestParam Long bookId) {
		CartModel cartModel = cartService.addCart(cartDTO,token,bookId);
		CartResponse cartResponse = new CartResponse(200, "addcart successfully", cartModel);
		return new ResponseEntity<CartResponse>(cartResponse,HttpStatus.OK);
	}
	

	
	@DeleteMapping("/removingcart")
	public ResponseEntity<CartResponse> removingCart(@RequestHeader String token, @RequestParam Long cartId) {
		CartModel cartModel = cartService.removingCart(token,cartId);
		CartResponse cartResponse = new CartResponse(200, "remove book from cart successfully", cartModel);
		return new ResponseEntity<CartResponse>(cartResponse,HttpStatus.OK);
	}
	

	
	@PutMapping("/updatequantity/{cartId}")
	public ResponseEntity<CartResponse> updateQuantity(@PathVariable Long cartId, @RequestHeader String token, @RequestParam Integer quantity) {
		CartModel cartModel = cartService.updateQuantity(cartId,token,quantity);
		CartResponse cartResponse = new CartResponse(200, "quantity updated successfully", cartModel);
		return new ResponseEntity<CartResponse>(cartResponse,HttpStatus.OK);
	}

	@GetMapping("/getallcartitems")
	public ResponseEntity<CartResponse> getAllCartItems(@RequestHeader String token) {
		List<CartModel> cartModel = cartService.getAllCartItems(token);
		CartResponse cartResponse = new CartResponse(200, "all cart items fetched successfully", cartModel);
		return new ResponseEntity<CartResponse>(cartResponse,HttpStatus.OK);
	}
	
	@GetMapping("/getallcartitemsforuser")
	public ResponseEntity<CartResponse> getAllCartItemsForUser(@RequestHeader String token) {
		List<CartModel> cartModel = cartService.getAllCartItemsForUser(token);
		CartResponse cartResponse = new CartResponse(200, "User all cart items fetched successfully", cartModel);
		return new ResponseEntity<CartResponse>(cartResponse,HttpStatus.OK);
	}


}
