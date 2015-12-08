/**
 * 
 */
package com.wbpm.cache.core.ehcache;

import java.net.URL;

import com.wbpm.cache.core.BaseCache;
import com.wbpm.cache.core.CacheManager;
import com.wbpm.core.utils.ClassLoaderUtils;

/**
 * ehCache框架实现的缓存管理类
 * @author wuyunjia
 * @createDate 2015-6-4
 */
public class EhCacheManager implements CacheManager {
	
	private static final String CACHE_NAME = "tounaDefaultCache";
	private static final String CACHE_XML_FILE = "cache.xml";

	private net.sf.ehcache.CacheManager cacheManager;
	
	public EhCacheManager() {
		URL url = ClassLoaderUtils.getResource(CACHE_XML_FILE, EhCacheManager.class);
		this.cacheManager = net.sf.ehcache.CacheManager.create(url);
	}
	
	public void init() {
	}
	
	public BaseCache getCache() {
		return new EhCacheImpl(cacheManager.getCache(CACHE_NAME)); 
	}

	public void destroy() {
		cacheManager.shutdown();
	}

}
