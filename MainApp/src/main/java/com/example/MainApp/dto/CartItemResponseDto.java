package com.example.MainApp.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CartItemResponseDto {

	 private Long cartItemId ; 
	 private Long cartId;
	 private Long userId;
	 private Long productId;
	 private Long quantity;
	 private BigDecimal price;
	 private ProductDto product;
}
