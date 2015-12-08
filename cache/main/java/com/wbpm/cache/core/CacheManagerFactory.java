/**
 * 
 */
package com.wbpm.cache.core;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 缓存管理实现的静态工厂类
 * @author wuyunjia
 * @createDate 2015-6-5
 */
public abstract class CacheManagerFactory {

	private static final Log log = LogFactory.getLog(CacheManagerFactory.class);
	
	private static Map<String, CacheManager> cacheManagerMap = new HashMap<String, CacheManager>();
	
	private static ReentrantLock lock = new ReentrantLock();
	
	private CacheManagerFactory(){}
	
	public static CacheManager getTounaCacheManager(String className) {
		if (cacheManagerMap.containsKey(className)) {
			return cacheManagerMap.get(className);
		}
		lock.lock();
		try {
			Class<?> forName = Class.forName(className);
			cacheManagerMap.put(className, (CacheManager) forName.newInstance());
			return cacheManagerMap.get(className);
		} catch (ClassNotFoundException e) {
			log.error(className+" 未找到!", e);
			throw new CacheException(className+" 未找到");
		} catch (InstantiationException e) {
			log.error(className+" 实例化错误!", e);
			throw new CacheException(className+" 实例化错误");
		} catch (IllegalAccessException e) {
			log.error(className+" 实例化参数不对!", e);
			throw new CacheException(className+" 实例化参数不对");
		} finally {
			lock.unlock();
		}
	}
}
