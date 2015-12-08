package com.wbpm.core;

import java.util.List;

/**
 * 查询对象基础接口
 * @author wuyunjia
 * @createDate 2015-5-14
 * @param <T> 查询对象泛型
 * @param <U> 数据对象泛型
 */
public interface Query<T extends Query<T, U>, U> {

	/**
	 * 升序排序
	 * 该方法是在调用orderByXxxx方法后调用的
	 * @return T 查询对象
	 */
	T asc();
	/**
	 * 降序排序
	 * 该方法是在调用orderByXxxx方法后调用的
	 * @return T 查询对象
	 */
	T desc();
	/** 
	 * 根据设置的查询条件统计结果集总数
	 * @return long 结果集总数
	 */
	long count();
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
	/** 
	 * 根据设置的查询条件查找分页列表记录
	 * @param start 开始记录索引号
	 * @param pageSize 每页显示记录数
	 * @return Page<U> 分页列表记录
	 */
	Page<U> listPage(int start, int pageSize);
}
