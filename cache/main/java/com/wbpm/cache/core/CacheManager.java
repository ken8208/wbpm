/**
 * 
 */
package com.wbpm.cache.core;

/**
 * 缓存管理接口
 * @author wuyunjia
 * @createDate 2015-6-4
 */
public interface CacheManager {
	
	/**
	 * 初始化缓存配置
	 */
	void init();
	/**
	 * 获取缓存服务
	 * @return
	 */
	BaseCache getCache();
	/**
	 * 销毁缓存配置
	 */
	void destroy();
}
