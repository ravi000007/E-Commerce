package com.example.userService.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignupRequestDTO {
	
	@NotNull(message = "Please provide vaild username")
	@NotBlank(message = "Username is required")
	@Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
	private String username;
	
	@NotNull
	private String name;
	
	
	@NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
	private String email;
	
	  @NotBlank(message = "Password is required")
	  @Size(min = 8, message = "Password must be at least 8 characters long")
	  @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$", 
	             message = "Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character")
	  private String password;

}
