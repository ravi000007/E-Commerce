package com.example.userService.service;

import java.util.Base64;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.userService.dto.LoginResponseDTO;
import com.example.userService.dto.SignupRequestDTO;
import com.example.userService.dto.loginRequestDTO;
import com.example.userService.entity.User;
import com.example.userService.exception.UserAlreadyExistsException;
import com.example.userService.exception.UserNotFoundException;
import com.example.userService.jwt.JwtAuthenticationHelper;
import com.example.userService.repository.UserRepository;

@Service
public class AuthService {
	
	private AuthenticationManager authenticationManager;
	private final UserRepository userRepository;
	private final UserDetailsService userDetailsService;
	private final JwtAuthenticationHelper jwtHelper ;
	
	@Autowired
	public AuthService(
			UserRepository userRepository,
			AuthenticationManager authenticationManager,
			UserDetailsService userDetailsService,
			JwtAuthenticationHelper jwtHelper
			) {
		this.userRepository = userRepository;
		this.authenticationManager = authenticationManager;
		this.userDetailsService = userDetailsService;
		this.jwtHelper = jwtHelper;
	}
	
	//1. signin
	
	public LoginResponseDTO authenticateUser(loginRequestDTO loginRequestDTO)
	{
		
		//(--------------------------------------------------------------------------------)
//		//authenticating user
//		Authentication  authToken = new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsernameOrEmail(), loginRequestDTO.getPassword());
//		Authentication authentication =  authenticationManager.authenticate(authToken);
//		
//		//Maintaining session
//		SecurityContextHolder.getContext().setAuthentication(authentication);
//		
//		LoginResponseDTO responseDTO = new LoginResponseDTO();
//		
//		//setting email to responsedto
//		responseDTO.setEmail(authentication.getName());
//		
//		//all details of the user 
//		Optional<User> userOptional = userRepository.findByEmail(authentication.getName());
//		
//		if(userOptional.isPresent()) {
//			User user = userOptional.get();
//			responseDTO.setId(user.getId());
//			
//			//Now for token 
//			String usernamePassword = user.getUsername() + loginRequestDTO.getPassword();
//			String byteEncoded = Base64.getEncoder().encodeToString(usernamePassword.getBytes());
//			
//			String token = "Basic " + byteEncoded.toString();
//			responseDTO.setToken(token);
//		}else {
//			throw new  UserNotFoundException("User is not Found");
//		}
//		
//		return responseDTO;
		//(--------------------------------------------------------------------------------)	
		Authentication authentication  = this.doAuthentication(loginRequestDTO.getUsernameOrEmail(), loginRequestDTO.getPassword());
		
		//Generating token
		UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequestDTO.getUsernameOrEmail());
		String token = jwtHelper.generateToken(userDetails);
		
		//Storing in loginResponseDto
		User user = userRepository.findByUsernameOrEmail(authentication.getName(),authentication.getName()).get();
		LoginResponseDTO responseDTO = new LoginResponseDTO();
		responseDTO.setId(user.getId());
		responseDTO.setEmail(user.getEmail());
		responseDTO.setUsername(user.getUsername());
		responseDTO.setToken(token);	
		return responseDTO ;
	}
     	
	private Authentication doAuthentication(String usernameOrEmail, String password) {
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
			  new UsernamePasswordAuthenticationToken(usernameOrEmail, password);
		try {
			return authenticationManager.authenticate(usernamePasswordAuthenticationToken);
		}catch(BadCredentialsException e){
			throw new BadCredentialsException("Invalid Username and Password");
		}
		
	}

	//2.signup
	public ResponseEntity signupUser(SignupRequestDTO signupDTO)
	{
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		
		//1. verify if user already exists
		if(userRepository.existsByUsername(signupDTO.getUsername())) {
			throw new UserAlreadyExistsException("Username has already been taken");
//			  return new ResponseEntity<>("Username has already been taken", HttpStatus.BAD_REQUEST);
		}
		if(userRepository.existsByEmail(signupDTO.getEmail())) {
			throw new UserAlreadyExistsException("Email ID has already been taken");
//			  return new ResponseEntity<>("Email ID has already been taken", HttpStatus.BAD_REQUEST);
		}
		//2.if not then create this user , and response HTTP.ok
		
		User user = new User();
		user.setEmail(signupDTO.getEmail());
		user.setUsername(signupDTO.getUsername());
		//need to encode the password before setting
		user.setPassword(passwordEncoder.encode(signupDTO.getPassword()));
		user.setName(signupDTO.getName());
		userRepository.save(user);
		
		
		return ResponseEntity.ok("User Has been Registered");
		
		
		//3.Role Mapping for new user
	}

}
