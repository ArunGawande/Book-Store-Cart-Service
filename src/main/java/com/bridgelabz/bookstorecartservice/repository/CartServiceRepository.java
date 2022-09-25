package com.bridgelabz.bookstorecartservice.repository;

import com.bridgelabz.bookstorecartservice.model.CartModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartServiceRepository extends JpaRepository<CartModel, Long>{

	List<CartModel> findByUserId(Long userId);

}
