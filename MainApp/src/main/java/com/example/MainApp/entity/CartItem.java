package com.example.MainApp.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cart_item")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CartItem {
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long id;
	 
	 @ManyToOne
	 @JoinColumn(name="product_id")
	 @JsonIgnore
	 private Product product;
	 
	 private Long quantity;
	 
	 private BigDecimal price;
	 
	 private Date createdAt;
	 
	 private Date updatedAt;
	 
	 @ManyToOne
	 @JoinColumn(name = "cart_id",nullable = false)
	 @JsonIgnore
	 private Cart cart;
}
