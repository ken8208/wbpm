/**
 * 
 */
package com.wbpm.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * 查询对象抽象基类
 * @author wuyunjia
 * @createDate 2015-5-14
 * @param <T> 查询对象泛型
 * @param <U> 数据对象泛型
 */
public abstract class AbstractQuery<T extends Query<T, U>, U> implements Query<T, U> {
	
	/**
	 * 排序字段集合
	 */
	private final List<Order> orders;
	/**
	 * 排序字段
	 */
	private OrderField field;
	
	private Lock lock = new ReentrantLock();
	
	protected AbstractQuery() {
		orders = new ArrayList<Order>();
	}

	/**
	 * 执行统计业务数据数量
	 * @return
	 */
	protected abstract long executeCount();

	/**
	 * 根据查询参数执行sql查询，由具体的业务查询类实现该方法
	 * 注意：如果有排序的话要AbstractQuery的hasOrder()来判断是否要组装order by语句，
	 *     若需要则调用AbstractQuery的parseOrderby()组装order by语句。
	 * @param page
	 * @return
	 */
	protected abstract List<U> executeList(Page<U> page);
	
	@Override
	public final U single() {
		List<U> list = executeList(null);
		if (list == null || list.isEmpty()) {
			return null;
		}
		if (list.size() > 1) {
			throw new IllegalArgumentException("Query return " + list.size() + " results instead of max 1");
		}
		return list.get(0);
	}
	
	@Override
	public final long count() {
		return executeCount();
	}
	
	@Override
	public final List<U> list() {
		return executeList(null);
	}
	
	@Override
	public final Page<U> listPage(int start, int pageSize) {
		Page<U> page = new Page<U>(start, pageSize);
		long count = count();
		List<U> list = null;
		if(count > 0){
			list = executeList(page);
		} else {
			list = Collections.emptyList();
		}
		page.setData(list);
		page.setTotalCount(count);
		return page;
	}
	
	@Override
	public final T asc() {
		return direction(Sort.ASC);
	}
	
	@Override
	public final T desc() {
		return direction(Sort.DESC);
	}
	
	/**
	 * 将设置的排序字段集合转换为order by后的片段
	 * @return
	 */
	public final String parseOrderby(){
		StringBuilder sb = new StringBuilder(" ");
		lock.lock();
		try {
			if (!orders.isEmpty()) {
				Iterator<Order> it = orders.iterator();
				while (it.hasNext()) {
					Order order = it.next();
					sb.append(order.getName()).append(" ").append(order.getSort());
					if(it.hasNext()){
						sb.append(" , ");
					}
				}
			}
		} finally {
			lock.unlock();
		}
		return sb.toString();
	}
	
	public final boolean hasOrder(){
		return !orders.isEmpty();
	}
	
	protected final T orderBy(OrderField field) {
		lock.lock();
		try {
			this.field = field;
			return addOrder(field, Sort.ASC);
		} finally {
			lock.unlock();
		}
		
	}
	
	/**
	 * 重设排序方式
	 * @param sort
	 * @return
	 */
	private T direction(Sort sort){
		lock.lock();
		try{
			OrderField field = this.field;
			if (field == null) {
				throw new IllegalArgumentException("You should call any of the orderByXxx methods first before specifying a direction");
		    }
			T t = addOrder(field, sort);
			this.field = null;
			return t;
		} finally {
			lock.unlock();
		}
	}
	
	@SuppressWarnings("unchecked")
	private T addOrder(OrderField field, Sort sort) {
		Order order = new Order(field.getName(), sort);
		if(orders.contains(order)){
			orders.remove(order);
		}
		orders.add(order);
		return (T)this;
	}
	
	/**
	 * 查询排序方式枚举类
	 * @author wuyunjia
	 * @createDate 2015-5-14
	 */
	private static enum Sort {
		ASC, //升序
		DESC;//降序
	}
	/**
	 * sql排序类
	 * @author wuyunjia
	 * @createDate 2015-5-14
	 */
	private final class Order {
		private final String name;//字段名
		private final Sort sort;//排序方式枚举类
		
		public Order(String name, Sort sort) {
			this.name = name;
			this.sort = sort;
		}
		
		public String getName(){
			return name;
		}
		
		public String getSort(){
			return sort.name();
		}
		
		@Override
		@SuppressWarnings("unchecked")
		public boolean equals(Object obj) {
			if(obj == null){
				return false;
			}
			Order order = (Order) obj;
			return name.equals(order.getName());
		}
	}

}
