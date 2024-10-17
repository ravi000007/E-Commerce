package com.example.userService.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.userService.dto.LoginResponseDTO;
import com.example.userService.dto.SignupRequestDTO;
import com.example.userService.dto.SignupResponseDTO;
import com.example.userService.dto.loginRequestDTO;
import com.example.userService.service.AuthService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	private final AuthService authService;
	
	Logger logger = LoggerFactory.getLogger(AuthController.class);
	
	@Autowired
	public AuthController(AuthService authService) {
		this.authService = authService;
	}
	
	//testing
	@GetMapping("/test")
	public String test()
	{
		return "Hello world";
	}
	
	//sign in 
	@PostMapping("/signin")
	public LoginResponseDTO authenticateUser(@RequestBody @Valid loginRequestDTO loginDTO)
	{
		return authService.authenticateUser(loginDTO);
	}
	
	
	//sign up
	@PostMapping("/signup")
	public ResponseEntity registerUser(@Valid @RequestBody SignupRequestDTO signupDTO)
	{
		return authService.signupUser(signupDTO);	
	}
	

}
