/**
 * 
 */
package com.wbpm.core;

import java.util.List;

/**
 * 缓存查询对象抽象基类
 * @author wuyunjia
 * @createDate 2015-6-9
 */
public abstract class AbstractCacheQuery<T extends CacheQuery<T, U>, U> implements CacheQuery<T, U> {
	
	
	protected abstract List<U> executeList();

	@Override
	public final U single() {
		List<U> list = executeList();
		if (list == null || list.isEmpty()) {
			return null;
		}
		if (list.size() > 1) {
			throw new IllegalArgumentException("Query return " + list.size() + " results instead of max 1");
		}
		return list.get(0);
	}

	@Override
	public final List<U> list() {
		return executeList();
	}

}
