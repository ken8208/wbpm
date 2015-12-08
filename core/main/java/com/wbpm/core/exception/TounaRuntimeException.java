/**
 * 
 */
package com.wbpm.core.exception;

/**
 * 系统异常基类
 * @author wuyunjia
 * @createDate 2015-6-5
 */
public abstract class TounaRuntimeException extends RuntimeException {

	private static final long serialVersionUID = -5409342677537007792L;

	protected TounaRuntimeException(Throwable cause) {
		super(cause);
	}
	
	protected TounaRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}
	
	protected TounaRuntimeException(String message) {
		super(message);
	}
}
