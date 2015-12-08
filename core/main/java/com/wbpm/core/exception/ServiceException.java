/**
 * 
 */
package com.wbpm.core.exception;

/**
 * 服务层异常类
 * @author wuyunjia
 * @createDate 2015-5-29
 */
public class ServiceException extends TounaRuntimeException {

	private static final long serialVersionUID = 4256538571396350314L;
	
	public ServiceException(Throwable cause) {
		super(cause);
	}
	
	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public ServiceException(String message) {
		super(message);
	}

}
