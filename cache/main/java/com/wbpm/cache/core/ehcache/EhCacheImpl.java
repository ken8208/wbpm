/**
 * 
 */
package com.wbpm.cache.core.ehcache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import com.wbpm.cache.core.CacheException;
import com.wbpm.cache.core.BaseCache;

/**
 * ehcache框架实现缓存服务
 * @author wuyunjia
 * @createDate 2015-6-4
 */
public final class EhCacheImpl implements BaseCache {
	
	private final Cache cache;
	
	public EhCacheImpl(Cache cache) {
		this.cache = cache;
	}

	@Override
	public void put(String key, Object value) {
		if (key == null || "".equals(key)) {
			throw new CacheException("缓存key为空");
		}
		Element element = new Element(key, value);
		cache.put(element);
	}

	@Override
	public Object get(String key) {
		if (key == null || "".equals(key)) {
			throw new CacheException("缓存key为空");
		}
		Element element = cache.get(key);
		return element != null ? element.getObjectValue() : null;
	}

	@Override
	public void remove(String key) {
		if (key != null && !"".equals(key)) {
			cache.remove(key);
		}
	}

}
