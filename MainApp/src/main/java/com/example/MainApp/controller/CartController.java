package com.example.MainApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.MainApp.dto.CartItemRequestDto;
import com.example.MainApp.dto.CartItemResponseDto;
import com.example.MainApp.dto.CartResponseDto;
import com.example.MainApp.jwt.JwtAuthenticationHelper;
import com.example.MainApp.service.CartService;

@RestController
@RequestMapping("/cart")
public class CartController {
	
	private final CartService cartService;
	
	@Autowired
	private JwtAuthenticationHelper jwtHelper;
	
	@Autowired
	public CartController(CartService cartService) {
		this.cartService = cartService;
	}
	
	@PostMapping("/add")
	@PreAuthorize("hasAnyRole('ADMIN', 'NORMAL')")
	public CartItemResponseDto addToCart(@RequestBody CartItemRequestDto request, @RequestHeader("Authorization") String token)
	{
		
		Long userId = Long.parseLong(jwtHelper.getUserIdFromToken(token.substring(7)));
		
		return cartService.addToCart(request, userId);
	}
	
	@GetMapping("/get")
	@PreAuthorize("hasAnyRole('ADMIN', 'NORMAL')")
	public ResponseEntity<CartResponseDto> getCart( @RequestHeader("Authorization") String token)
	{
		Long userId = Long.parseLong(jwtHelper.getUserIdFromToken(token.substring(7)));
		
		return cartService.getCartByUserId(userId);
	}
	
	@DeleteMapping("/delete/{cartItemId}")
	@PreAuthorize("hasAnyRole('ADMIN', 'NORMAL')")
	public ResponseEntity deleteCartItem(@PathVariable("cartItemId") Long cartItemId,  @RequestHeader("Authorization") String token )
	{
		Long userId = Long.parseLong(jwtHelper.getUserIdFromToken(token.substring(7)));
		
		return cartService.deleteCartItem(cartItemId, userId);
	}

}
