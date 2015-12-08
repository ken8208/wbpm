package com.wbpm.core.exception;

public class NoSuchInstanceException extends BaseException{
	
	public NoSuchInstanceException(Throwable cause) {
		super(cause);
	}
	
	public NoSuchInstanceException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public NoSuchInstanceException(String message) {
		super(message);
	}
}
