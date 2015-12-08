/**
 * 
 */
package com.wbpm.core;

import java.util.List;

/**
 * 缓存查询对象基础接口
 * @author wuyunjia
 * @createDate 2015-6-9
 */
public interface CacheQuery<T extends CacheQuery<?, U>, U> {
	
	/** 
	 * 根据设置的查询条件查找单条记录
	 * @return U 单条记录
	 */
	U single();
	/** 
	 * 根据设置的查询条件查找列表记录
	 * @return List<U> 列表记录
	 */
	List<U> list();
	
}
