package com.example.userService.security;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.userService.entity.User;
import com.example.userService.exception.UserNotFoundException;
import com.example.userService.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	private UserRepository userRepository ; 
	
	public CustomUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository ;
	}

	@Override
	public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
	
		 User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
	                .orElseThrow(() -> new UsernameNotFoundException("User not found : " + usernameOrEmail));
		 
		 
		return user;
	}

}
