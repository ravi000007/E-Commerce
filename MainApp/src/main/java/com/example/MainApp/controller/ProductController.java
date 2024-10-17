	package com.example.MainApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.MainApp.dto.ProductDto;
import com.example.MainApp.entity.Product;
import com.example.MainApp.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {
	
	private final ProductService productService; 
	
	@Autowired
	public ProductController(ProductService productService) {
		this.productService = productService;
	}
	
	@GetMapping
	@PreAuthorize("hasAnyRole('ADMIN', 'NORMAL')")
	public List<Product> getAllProducts()
	{
		return productService.getAllProducts();
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN', 'NORMAL')")
	public ProductDto getProductById(@PathVariable Long id)
	{
		return productService.getProductById(id);
	}
	
	@GetMapping("/{categoryId}")
	@PreAuthorize("hasAnyRole('ADMIN', 'NORMAL')")
	public List<Product> getProductByCategory(@PathVariable Long id)
	{
		return productService.getAllProductByCategoryId(id);
	}
	
	@GetMapping("/{categoryName}")
	@PreAuthorize("hasAnyRole('ADMIN', 'NORMAL')")
	public List<Product> getProductByCategory(@PathVariable String categoryName )
	{
		return productService.getAllProductByCategoryName(categoryName);
	}
	
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity createProduct(@RequestBody Product productDto)
	{
		
		return productService.createProduct(productDto);
		
	}
		
		@PutMapping("/{id}")
		@PreAuthorize("hasRole('ADMIN')")
		public ResponseEntity updateProductById(@RequestBody ProductDto productDto, @PathVariable Long id)
		{
			return productService.updateProduct(productDto, id);
		}
		
		@DeleteMapping("/{id}")
		@PreAuthorize("hasRole('ADMIN')")
		public ResponseEntity deleteProductById(@PathVariable Long id)
		{
			return productService.deleteProduct(id);
		}
		
	

}
