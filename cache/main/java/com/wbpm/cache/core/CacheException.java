/**
 * 
 */
package com.wbpm.cache.core;


/**
 * 缓存异常类
 * @author wuyunjia
 * @createDate 2015-6-5
 */
public class CacheException extends RuntimeException {

	private static final long serialVersionUID = -1244934272887103826L;

	public CacheException(Throwable cause) {
		super(cause);
	}
	
	public CacheException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public CacheException(String message) {
		super(message);
	}
	
	

}
