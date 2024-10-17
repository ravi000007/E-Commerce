package com.example.userService.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.userService.dto.UserDTO;
import com.example.userService.entity.User;
import com.example.userService.exception.NoUsersFoundException;
import com.example.userService.repository.UserRepository;
import com.example.userService.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	
	private final UserService userService;
	
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	
	@GetMapping("/all")
	@PreAuthorize("hasRole('ADMIN')")
    public List<User> getAllUsers() 
	{
       return  userService.getAllUsers();

    }
	
	//Endpoint to retrieve a specific user by ID
	@GetMapping("/{userId}")
	@PreAuthorize("hasAnyRole('NORMAL', 'ADMIN')")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) 
	{
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(user); // Return the user and a 200 OK status
    }
	
	@GetMapping("/search/{keywords}")
	@PreAuthorize("hasAnyRole('NORMAL', 'ADMIN')")
	public List<User> serachUsersByKeyword(@PathVariable String keywords)
	{
		return userService.searchUsersByKeyword(keywords);
	}
	
	@PutMapping("/{userId}")
	@PreAuthorize("hasAnyRole('NORMAL', 'ADMIN')")
	public ResponseEntity<Map<String,String>> updateUser(@RequestBody UserDTO userDTO, @PathVariable Long userId )
	{
		userService.updateUser(userId, userDTO);
		Map<String, String> response = new HashMap<>();
		response.put("message" , "User has been updated Successfully !");
		return ResponseEntity.ok(response);
	}
	
	
	@DeleteMapping("/{userId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Map<String,String>> deleteUser(@PathVariable Long userId )
	{
		
		userService.deleteUserByUserId(userId); 
		Map<String, String> response = new HashMap<>();
		response.put("message" , "User has been deleted Successfully !");
		return ResponseEntity.ok(response);
		 
		
	}
 
}
