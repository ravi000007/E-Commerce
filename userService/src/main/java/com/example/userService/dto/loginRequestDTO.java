package com.example.userService.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class loginRequestDTO {
	
	@NotNull(message = "Provide Username or Email")
	private String usernameOrEmail;
	
	@NotNull(message = "Provide correct password")
	private String password;

}
