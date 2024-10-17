package com.example.MainApp.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.MainApp.dto.CartResponseDto;
import com.example.MainApp.dto.OrderItemRequestDto;
import com.example.MainApp.dto.ProductDto;
import com.example.MainApp.entity.Cart;
import com.example.MainApp.entity.CartItem;
import com.example.MainApp.entity.Order;
import com.example.MainApp.entity.OrderItem;
import com.example.MainApp.entity.Product;
import com.example.MainApp.exception.SomeThingsError;
import com.example.MainApp.repository.CartRepository;
import com.example.MainApp.repository.OrderItemRepository;
import com.example.MainApp.repository.OrderRepository;
import com.example.MainApp.repository.ProductRepository;

@Service
public class OrderService {

	@Autowired
	private CartService cartService ;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	@Autowired
	private CartRepository cartRepository;
	
	
	public ResponseEntity<Order> placeOrder(Long userId, OrderItemRequestDto orderItemsRequest) {
		final Order savedOrder ; 
		Order order = new Order();
		//1.Get Cart from userId;
		ResponseEntity<CartResponseDto> cartResponse = cartService.getCartByUserId(userId);
		
		//2.Get CartItem to this particular cart
		List<CartItem> cartItemsList = cartResponse.getBody().getCartItem();
		
		//3.Place Order like: create Order and OrderItem then set all necessary field like address,totalPrice etc
		try {
			
			order.setUserId(userId);
			order.setTotalPrice(cartResponse.getBody().getTotalPrice());
			order.setOrderDate(new Timestamp(System.currentTimeMillis()));
			order.setStatus("PENDING");
			order.setAddress(orderItemsRequest.getAddress());
		    savedOrder = orderRepository.save(order);
		}catch (Exception e) {
			throw new SomeThingsError("Order can not be placed");
		}
		
		//4. Now Placing each order in the inventory
		cartItemsList.stream().forEach(cartItem -> {
			OrderItem orderItem = new OrderItem();
			orderItem.setCreatedDate(new Timestamp(System.currentTimeMillis()));
			
			//it will handle if product is not there
			ProductDto productResponse = productService.getProductById(cartItem.getProduct().getId());
			orderItem.setProduct(cartItem.getProduct());
			orderItem.setPrice(BigDecimal.valueOf(productResponse.getPrice()));
			orderItem.setQuantity(cartItem.getQuantity());
			orderItem.setOrder(savedOrder);
			order.getOrderItems().add(orderItem);
			orderItemRepository.save(orderItem);	 
		});
		//5. Now clear the cart Item for the user
		
		cartRepository.deleteById(cartResponse.getBody().getCartId());
	
		return ResponseEntity.ok(order);
	}

}
