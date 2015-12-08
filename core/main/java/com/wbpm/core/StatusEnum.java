/**
 * 
 */
package com.wbpm.core;

/**
 * 状态有效性枚举类（通用）
 * @author wuyunjia
 * @createDate 2015-6-1
 */
public enum StatusEnum implements PropertyEnum {
	
	VALID("1", "有效"),
	INVALID("0", "无效");
	
	private final String value;
	private final String text;
	
	private StatusEnum(String value, String text) {
		this.value = value;
		this.text = text;
	}
	
	@Override
	public String value() {
		return value;
	}
	
	public String text() {
		return text;
	}
	
	public static StatusEnum getEnum(String value) {
		for(StatusEnum status : values()){
			if(status.value().equals(value)){
				return status;
			}
		}
		return null;
	}
}
