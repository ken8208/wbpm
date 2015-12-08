package com.wbpm.core.exception;

public class BaseException extends Exception {
	
	public BaseException(Throwable cause) {
		super(cause);
	}
	
	public BaseException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public BaseException(String message) {
		super(message);
	}
}
