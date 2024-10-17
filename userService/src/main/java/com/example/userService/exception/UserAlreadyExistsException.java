package com.example.userService.exception;

public class UserAlreadyExistsException extends RuntimeException {
	public UserAlreadyExistsException(String msg) {
		super(msg);
	}
 
}
