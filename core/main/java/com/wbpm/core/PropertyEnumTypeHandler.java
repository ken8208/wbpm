/**
 * 
 */
package com.wbpm.core;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

/**
 * javabean中属性为枚举类型的mybatis类型转换器
 * @author wuyunjia
 * @createDate 2015-6-1
 */
public class PropertyEnumTypeHandler <T extends PropertyEnum> extends BaseTypeHandler<PropertyEnum> {

	private final Class<? extends PropertyEnum> type;

	public PropertyEnumTypeHandler(Class<? extends PropertyEnum> type) {
		if (type == null){
			throw new IllegalArgumentException("Type argument cannot be null");
		}
		this.type = type;
	}

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i,
			PropertyEnum parameter, JdbcType jdbcType) throws SQLException {
		ps.setString(i, parameter.value());
	}

	@Override
	public PropertyEnum getNullableResult(ResultSet rs, String columnName)
			throws SQLException {
		String columnValue = rs.getString(columnName);
	    return convert(columnValue);
	}

	@Override
	public PropertyEnum getNullableResult(ResultSet rs, int columnIndex)
			throws SQLException {
		String columnValue = rs.getString(columnIndex);
	    return convert(columnValue);
	}

	@Override
	public PropertyEnum getNullableResult(CallableStatement cs, int columnIndex)
			throws SQLException {
		String columnValue = cs.getString(columnIndex);
	    return convert(columnValue);
	}
	
	private PropertyEnum convert(String columnValue) {
		PropertyEnum[] enumConstants = type.getEnumConstants();
		for(PropertyEnum e : enumConstants){
			if(e.value().equals(columnValue)){
				return e;
			}
		}
		return null;
	}

}
