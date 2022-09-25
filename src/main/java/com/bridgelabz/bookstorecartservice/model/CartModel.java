package com.bridgelabz.bookstorecartservice.model;

import com.bridgelabz.bookstorecartservice.dto.CartDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "cartmodel")
@AllArgsConstructor
@NoArgsConstructor
public class CartModel {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long cartId;
    private long userId;
    private long bookId;
    private int quantity;
    private int totalPrice;
    
    public CartModel(CartDTO cartDTO) {
    	this.quantity = cartDTO.getQuantity();
    }
}
