package com.lcwd.electronic.store.exceptions;

public class BadApiRequest extends RuntimeException{
	public BadApiRequest(String msg) {
		super(msg);
	}
	public BadApiRequest() {
		super("=> bad request !");
	}
}
