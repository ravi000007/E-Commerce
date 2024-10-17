package com.example.userService.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class UserDTO {
 
	@NotEmpty
	private String name;
	private String username;
	private String email;
}
