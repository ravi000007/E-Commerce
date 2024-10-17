package com.example.MainApp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.MainApp.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	
	
	@Query("SELECT p FROM Product p WHERE p.category.id = :categoryId")
    List<Product> getProductByCategoryId(@Param("categoryId") Long categoryId);
	
	@Query("SELECT p FROM Product p WHERE p.category.name = :categoryName")
	List<Product> getAllProductByCategoryName(@Param("categoryName") String categoryName);

}
