/**
 * 
 */
package com.wbpm.cache.core;

/**
 * 缓存服务
 * @author wuyunjia
 * @createDate 2015-6-4
 */
public interface BaseCache {
	
	/**
	 * 添加缓存数据
	 * @param key
	 * @param value
	 */
	void put(String key, Object value);
	/**
	 * 根据key获取相应的缓存数据
	 * @param key
	 * @return
	 */
	Object get(String key);
	/**
	 * 根据key移除相应的缓存数据
	 * @param key
	 */
	void remove(String key);
	
}
