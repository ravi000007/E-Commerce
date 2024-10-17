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

import com.example.MainApp.dto.CategoryDto;
import com.example.MainApp.dto.ProductDto;
import com.example.MainApp.entity.Category;
import com.example.MainApp.entity.Product;
import com.example.MainApp.service.CategoryService;

@RestController
@RequestMapping("/categories")
public class CategoryController {

	private final CategoryService categoryService ; 
	
	@Autowired
	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity createCategory(@RequestBody CategoryDto  categoryDto)
	{
		return categoryService.createCategory(categoryDto);
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN', 'NORMAL')")
	public Category getCategoryById(@PathVariable Long id)
	{
		return categoryService.getCategoryById(id);
	}
	
	@GetMapping
	@PreAuthorize("hasAnyRole('ADMIN', 'NORMAL')")
	public List<Category> getAllCategories()
	{
		return categoryService.getAllCategories();
	}
	
	
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity updateCategoryId(@RequestBody CategoryDto categoryDto, @PathVariable Long id)
	{
		return categoryService.updateCategory(categoryDto, id);
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity deleteCategoryById(@PathVariable Long id)
	{
		return categoryService.deleteCategory(id);
	}
	
}
