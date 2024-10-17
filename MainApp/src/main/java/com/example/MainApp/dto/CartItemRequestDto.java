package com.example.MainApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemRequestDto {
	
	 private Long cartId;
//	 private Long userId;
	 private Long productId;
	 private Long quantity;
}
