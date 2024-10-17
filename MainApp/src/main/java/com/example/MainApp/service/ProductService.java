package com.example.MainApp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.MainApp.dto.ProductDto;
import com.example.MainApp.entity.Category;
import com.example.MainApp.entity.Product;
import com.example.MainApp.exception.ProductNotFoundException;
import com.example.MainApp.repository.ProductRepository;

@Service
public class ProductService {
	
	private final ProductRepository productRepository;
	private final CategoryService categoryService;
	
	@Autowired
	public ProductService(ProductRepository productRepository, CategoryService categoryService) {
		this.productRepository = productRepository;
		this.categoryService = categoryService;
	}

	public List<Product> getAllProducts() {
		List<Product> users = productRepository.findAll();
		if(users.isEmpty()) {
			throw new ProductNotFoundException("There is no any Products found ");
		}
		
		return users;
	}

	public ProductDto getProductById(Long id) {
		Product product = productRepository.findById(id)
				.orElseThrow(()-> new ProductNotFoundException("There is no any product found with product id : " + id));
		
		ProductDto response = new ProductDto();
		response.setName(product.getName());
		response.setDescription(product.getDescription());
		response.setPrice(product.getPrice());
		response.setCategoryName(product.getCategory().getName());
		
		return response;
	}

	public List<Product> getAllProductByCategoryId(Long id) {
		List<Product> products  = productRepository.getProductByCategoryId(id);
		if(products.isEmpty()) {
			throw new ProductNotFoundException("There is no any product found for this Category Id : " + id);
		}
		return products;
	}

	public List<Product> getAllProductByCategoryName(String categoryName) {
		List<Product> products = productRepository.getAllProductByCategoryName(categoryName);
		if(products.isEmpty()) {
			throw new ProductNotFoundException("There is no any product found for this Category Name : " + categoryName);
		}
		return products;
	}

	public ResponseEntity createProduct(Product productDto) {
		Product product = new Product();
		product.setName(productDto.getName());
		product.setDescription(productDto.getDescription());
		product.setPrice(productDto.getPrice());
		
		//For Category 
		Category category = categoryService.getCategoryById(productDto.getCategory().getId());
		product.setCategory(category);
		
		try {
			productRepository.save(product);
		} catch (Exception e) {
			throw new ProductNotFoundException(e.getMessage());
		}
		
		return ResponseEntity.ok(product);
		
		
	}

	public ResponseEntity updateProduct(ProductDto productDto, Long id) {
		if(productRepository.existsById(id)){
			throw new ProductNotFoundException("There is no any product found with the id : " + id);
		}
		Product product = productRepository.findById(id).get();
		product.setName(productDto.getName());
		product.setDescription(product.getDescription());
		product.setPrice(productDto.getPrice());
		
		try {
			productRepository.save(product);
		} catch (Exception e) {
			throw new ProductNotFoundException("Product cannot be save");
		}
		
		return ResponseEntity.ok(product);
		
	}

	public ResponseEntity deleteProduct(Long id) {
		if(productRepository.existsById(id)){
			throw new ProductNotFoundException("There is no any product found with the id : " + id);
		}
		
		try {
			productRepository.deleteById(id);
		} catch (Exception e) {
			throw new ProductNotFoundException("Error!!");
		}
		
		return ResponseEntity.ok("Product Delete Successfully");
	}

}
