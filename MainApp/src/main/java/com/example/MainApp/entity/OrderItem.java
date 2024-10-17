package com.example.MainApp.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

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
@Table(name = "order_items")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class OrderItem {
	
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long id;
	 
	 @ManyToOne
	 @JoinColumn(name = "order_id")
	 @JsonIgnore
	 private Order order;
	 
	 @ManyToOne
	 @JoinColumn(name = "product_id")
	 @JsonIgnore
	 private Product product;
	 
	 private Long quantity;
	 
	 private BigDecimal price;
	 
	 private Timestamp createdDate;
}
