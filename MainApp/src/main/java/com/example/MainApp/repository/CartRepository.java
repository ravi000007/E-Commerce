package com.example.MainApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.example.MainApp.entity.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

	Cart findByUserId(Long userId);

	
	
}
