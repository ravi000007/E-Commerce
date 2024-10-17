package com.example.userService.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class LoginResponseDTO {

	private String username;
	private Long id;
	private String email;
	private String name;
	private String token;
}
