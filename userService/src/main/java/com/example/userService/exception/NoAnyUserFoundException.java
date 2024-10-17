package com.example.userService.exception;


public class NoAnyUserFoundException extends RuntimeException {

	public NoAnyUserFoundException(String message) {
		super(message);
	}
}
