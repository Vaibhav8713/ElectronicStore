package com.lcwd.electronic.store.exceptions;

public class ResourceNotFoundException extends RuntimeException {
	
	//default constructor
	public ResourceNotFoundException() {
		super("resouce not found !!");
	}
	
	//parameterized constructor for custom message
	public ResourceNotFoundException(String msg) {
		super(msg);
	}
}
