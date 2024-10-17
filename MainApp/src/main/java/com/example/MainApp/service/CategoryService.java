package com.example.MainApp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.MainApp.dto.CategoryDto;
import com.example.MainApp.entity.Category;
import com.example.MainApp.entity.Product;
import com.example.MainApp.exception.ProductNotFoundException;
import com.example.MainApp.repository.CategoryRepository;

@Service
public class CategoryService {
	
	private final CategoryRepository categoryRepository;
	
	@Autowired
	public CategoryService(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	public ResponseEntity createCategory(CategoryDto categoryDto) {
		Category category = new Category();
		category.setName(categoryDto.getName());
		try {
			categoryRepository.save(category);
		} catch (Exception e) {
			throw new ProductNotFoundException("Somethings went wrong in saving ");
		}
		
		return ResponseEntity.ok(category);
	}

	public Category getCategoryById(Long id) {
		Category category = categoryRepository.findById(id)
				.orElseThrow(()-> new ProductNotFoundException("There is no any Category found with category id : " + id));
		
		return category;
	}

	public List<Category> getAllCategories() {
		List<Category> categories = categoryRepository.findAll();
		if(categories.isEmpty()) {
			throw new ProductNotFoundException("There is no any Categories found ");
		}
		
		return categories;
	}

	public ResponseEntity updateCategory(CategoryDto categoryDto, Long id) {
		if(categoryRepository.existsById(id)){
			throw new ProductNotFoundException("There is no any Category found with the id : " + id);
		}
		Category category = categoryRepository.findById(id).get();		
		
		try {
			categoryRepository.save(category);
		} catch (Exception e) {
			throw new ProductNotFoundException("Category cannot be save");
		}
		
		return ResponseEntity.ok(category);
	}

	public ResponseEntity deleteCategory(Long id) {
		if(categoryRepository.existsById(id)){
			throw new ProductNotFoundException("There is no any category found with the id : " + id);
		}
		
		try {
			categoryRepository.deleteById(id);
		} catch (Exception e) {
			throw new ProductNotFoundException("Error!!");
		}
		
		return ResponseEntity.ok("Category Delete Successfully");
	}
}
