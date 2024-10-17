package com.example.MainApp.dto;

import com.example.MainApp.entity.Category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ProductDto {

	private String name;
	private Long price;
	private String description;
	private String categoryName;

}
