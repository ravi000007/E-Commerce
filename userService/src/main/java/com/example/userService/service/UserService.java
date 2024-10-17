package com.example.userService.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.userService.dto.UserDTO;
import com.example.userService.entity.User;
import com.example.userService.exception.NoAnyUserFoundException;
import com.example.userService.exception.NoUserFoundByKeywordException;
import com.example.userService.exception.UserNotFoundException;
import com.example.userService.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    public List<User> getAllUsers()
    {
    	List<User> users = userRepository.findAll();
    	
    	if(users.isEmpty()) {
    		throw new NoAnyUserFoundException("There is no user present");
    	}
    	return users;
    }
    

    //Retrieves a user by their ID
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with userId: " + userId));
    }
    
    
    public List<User> searchUsersByKeyword(String keyword)
    {
    	List<User> users = userRepository.findByKeyword(keyword);
    	if(users.isEmpty()) {
    		throw new NoUserFoundByKeywordException("There is no any user found with keyword :" + keyword);
    	}
    	
    	return users;
    }
    
    public User updateUser(Long userId, UserDTO userDTO )
    {
    	User user =  userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with userId: " + userId));
    	
    	user.setName(userDTO.getName());
    	user.setUsername(userDTO.getUsername());
    	user.setEmail(userDTO.getEmail());
    	System.out.println("User updated-----**********************************************************------------" + user.toString() +"::::" +userDTO.getName());
    	return userRepository.save(user);
    }
    
    public User deleteUserByUserId(Long userId) 
    {
    	User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found to delete"));
    	userRepository.deleteById(userId);
    	
    	return user;
    }
    
    
    
    
}
