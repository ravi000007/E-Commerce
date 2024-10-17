package com.example.userService.exception;

public class NoUserFoundByKeywordException extends RuntimeException {

	public NoUserFoundByKeywordException(String msg) {
		super(msg);
	}
}
