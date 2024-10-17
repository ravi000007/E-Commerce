package com.example.MainApp.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "product")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Product {
	
	@Id 	 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private Long price;
	private String description; 
	
	@ManyToOne
	@JoinColumn(name = "categories_id", nullable = false)
	@JsonIgnore
	private Category category;
	
	@OneToMany(mappedBy = "product" , cascade = CascadeType.ALL)
	@Builder.Default
	List<CartItem> cartItems =  new ArrayList<>() ; 
	
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL) 
	@Builder.Default
	List<OrderItem> orderitem = new ArrayList<>();
	
	
}
