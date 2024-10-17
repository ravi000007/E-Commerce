package com.example.MainApp.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.example.MainApp.entity.CartItem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CartResponseDto {

	private Long cartId;
	private Long userId;
	private Date createdAt;
	private Date updatedAt;
	private BigDecimal totalPrice;
	private List<CartItem> cartItem ;
}
