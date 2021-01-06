package com.exception.custom;

public class DataNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public DataNotFoundException() {
		super();
	}
	public DataNotFoundException(String message) {
		super(message);
	}

}
