/**
 * 
 */
package com.wbpm.core.exception;

/**
 * 控制层异常类
 * @author wuyunjia
 * @createDate 2015-6-5
 */
public class ActionException extends TounaRuntimeException {

	private static final long serialVersionUID = 6604302561603467738L;

	public ActionException(Throwable cause) {
		super(cause);
	}
	
	public ActionException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public ActionException(String message) {
		super(message);
	}

}
