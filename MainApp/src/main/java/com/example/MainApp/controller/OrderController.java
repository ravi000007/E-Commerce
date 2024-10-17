package com.example.MainApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.MainApp.dto.OrderItemRequestDto;
import com.example.MainApp.entity.Order;
import com.example.MainApp.jwt.JwtAuthenticationHelper;
import com.example.MainApp.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {
	
	@Autowired
	private JwtAuthenticationHelper jwtHelper;
	
	@Autowired
	private OrderService orderService;
	
	@PostMapping
    public ResponseEntity<Order> createOrder(@RequestHeader("Authorization") String token, @RequestBody OrderItemRequestDto orderItemsRequest) {
       
	  Long userId =Long.parseLong(jwtHelper.getUserIdFromToken(token.substring(7)));
	  
	  return orderService.placeOrder(userId, orderItemsRequest);
	  
    }

}
