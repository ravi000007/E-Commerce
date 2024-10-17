package com.example.MainApp.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponseDto {
	private Long id;
	private String createdDate;
	private String address;
	private String payment;
	private BigDecimal totalPrice;
}