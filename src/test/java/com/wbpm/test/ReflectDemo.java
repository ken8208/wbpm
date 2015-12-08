/**
 * Copyright 2016 Welab, Inc. All rights reserved.
 * WELAB PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.wbpm.test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import javax.ws.rs.PathParam;

/**
 * @author <a href="mailto:ken.wu@wolaidai.com">ken.wu</a>
 */
public class ReflectDemo
{

	public void test(@PathParam("mobile") String mobile)
	{
		System.out.println(mobile);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception
	{
		ReflectDemo demo = new ReflectDemo();
		Class<? extends ReflectDemo> clazz = demo.getClass();
		Method test = clazz.getMethod("test", String.class);
		Annotation[][] parameterAnnotations = test.getParameterAnnotations();
		Annotation annotation = parameterAnnotations[0][0];
		if (PathParam.class.isAssignableFrom(annotation.annotationType()) 
				&& "mobile".equals(((PathParam) annotation).value()))
		{
			PathParam pathParam = (PathParam) annotation;
			System.out.println(pathParam.value());
		}
		System.out.println(annotation);
	}

}
