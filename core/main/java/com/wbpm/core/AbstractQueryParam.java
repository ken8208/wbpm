/**
 * 
 */
package com.wbpm.core;

/**
 * 查询参数基础类
 * @author wuyunjia
 * @createDate 2015-5-28
 */
public abstract class AbstractQueryParam {
	
	private String orderBy;
	private Integer pageStart;
	private Integer pageSize;
	
	public String getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	public Integer getPageStart() {
		return pageStart;
	}
	public void setPageStart(Integer pageStart) {
		this.pageStart = pageStart;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

}
