package com.example.MainApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.MainApp.entity.Cart;
import com.example.MainApp.entity.CartItem;
import com.example.MainApp.entity.Product;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
	
	  @Query("SELECT ci FROM CartItem ci WHERE ci.cart = :cart AND ci.product = :product")
	  CartItem findByCartAndProduct(@Param("cart") Cart cart, @Param("product") Product product);

	CartItem findByIdAndCart(Long cartItemId, Cart cart);

}
