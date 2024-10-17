package com.example.MainApp.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cart")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Cart {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	 private Long userId;
	 
	 
	 @Column(name = "created_at", updatable = false)
	 private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;
	 
	 @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL,orphanRemoval = true)
	 private List<CartItem> items = new ArrayList<>();

}
