package com.example.MainApp.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.MainApp.dto.CartItemRequestDto;
import com.example.MainApp.dto.CartItemResponseDto;
import com.example.MainApp.dto.ProductDto;
import com.example.MainApp.entity.Cart;
import com.example.MainApp.entity.CartItem;
import com.example.MainApp.entity.Product;
import com.example.MainApp.exception.CartNotFoundException;
import com.example.MainApp.exception.ProductNotFoundException;
import com.example.MainApp.jwt.JwtAuthenticationHelper;
import com.example.MainApp.repository.CartItemRepository;
import com.example.MainApp.repository.CartRepository;
import com.example.MainApp.repository.ProductRepository;
import com.example.MainApp.dto.CartResponseDto;

@Service
public class CartService {
	
	@Autowired
	private JwtAuthenticationHelper jwtHelper;
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CartItemRepository cartItemRepository;
	
	@Autowired
	private ProductService productService;
	

	public CartItemResponseDto addToCart(CartItemRequestDto request, Long userId) {
		//checking product is found or not 
		Product product = productRepository.findById(request.getProductId())
						.orElseThrow(() -> new ProductNotFoundException("Product not found"));
		
		
		CartItemResponseDto responseDto = new CartItemResponseDto();
		//checking if the particular user have there cart or not
		Cart cart = cartRepository.findByUserId(userId);
		
		//if not then creating cart
		if(cart == null) {
			 cart = new Cart();
			 cart.setUserId(userId);
			 cart.setItems(new ArrayList<>());
			 cart.setCreatedAt(new Timestamp(System.currentTimeMillis()));
			 cartRepository.save(cart);
		}
		
		
		
		//Check CartItem is already in the cartItem with cart And productId
		CartItem existingCartItem = cartItemRepository.findByCartAndProduct(cart,product);
		
		if(existingCartItem != null) {
			existingCartItem.setQuantity(existingCartItem.getQuantity() + request.getQuantity());
			existingCartItem.setPrice(existingCartItem.getPrice().add(BigDecimal.valueOf(product.getPrice()).multiply(BigDecimal.valueOf(request.getQuantity()))));
			cartItemRepository.save(existingCartItem);
			
			//Response
			responseDto.setQuantity(existingCartItem.getQuantity());
			responseDto.setPrice(existingCartItem.getPrice());
			responseDto.setCartItemId(existingCartItem.getId());	
		}else {
			// Create a new CartItem and add it to the cart
			CartItem cartItem = new CartItem();
			cartItem.setPrice( BigDecimal.valueOf(product.getPrice() * request.getQuantity()));
			cartItem.setProduct(product);
			cartItem.setQuantity(request.getQuantity());
			
			 // Add the item to the cart
			cart.getItems().add(cartItem);		
			cartItem.setCart(cart);
			
			cartItemRepository.save(cartItem);
			cartRepository.save(cart);
			
			responseDto.setCartItemId(cartItem.getId());
			responseDto.setQuantity(request.getQuantity());
			responseDto.setPrice(cartItem.getPrice());
		}
		
		//Returning ItemResponseDTo 
		
		responseDto.setCartId(cart.getId());
		responseDto.setUserId(userId);
		responseDto.setProductId(request.getProductId());
		
		
		ProductDto productDto = productService.getProductById(request.getProductId());
		responseDto.setProduct(productDto);
		
		return responseDto;
	}


	public ResponseEntity<CartResponseDto> getCartByUserId(Long userId) {
		
		//Getting cart with the userId
		Cart cart = cartRepository.findByUserId(userId);
		if(cart == null) {
			throw new CartNotFoundException("There is no cart found for this user");
		}
		
		BigDecimal calculatedPrice = BigDecimal.ZERO;
		CartResponseDto response = new CartResponseDto();
		response.setCartId(cart.getId());
		response.setUserId(userId);
		calculatedPrice = cart.getItems().stream().map(cartItem -> cartItem.getPrice())
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		response.setTotalPrice(calculatedPrice);
		response.setCartItem(cart.getItems());
		
		return ResponseEntity.ok(response);
	}


	public ResponseEntity deleteCartItem(Long cartItemId, Long userId) {
		
		//Getting cart By  userId
		Cart cart = cartRepository.findByUserId(userId);
		
		if(cart == null ) { 
			 return ResponseEntity.badRequest().body("Cart not found");
		}
		
		// Find the cartItem by itemId and ensure it belongs to the user's cart
        CartItem cartItem = cartItemRepository.findByIdAndCart(cartItemId, cart);
        if (cartItem == null) {
            return ResponseEntity.badRequest().body("Cart item not found or does not belong to the user");
        }
		
        cartItemRepository.deleteById(cartItemId);
		
		return ResponseEntity.ok("Item Deleted Successfull");
	}
	
	

}
