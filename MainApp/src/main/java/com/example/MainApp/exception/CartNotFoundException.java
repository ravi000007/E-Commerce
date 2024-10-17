package com.example.MainApp.exception;

public class CartNotFoundException extends RuntimeException {

	public  CartNotFoundException(String msg)
	{
		super(msg);
	}
	
}
