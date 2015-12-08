/**
 * Copyright 2016 Welab, Inc. All rights reserved.
 * WELAB PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.wbpm.demo;

import org.springframework.beans.factory.InitializingBean;

/**
 * @author <a href="mailto:ken.wu@wolaidai.com">ken.wu</a>
 */
public class Person implements InitializingBean
{
	private Book book;

	public void setBook(Book book)
	{
		this.book = book;
	}

	@Override
	public void afterPropertiesSet() throws Exception
	{
		book.test();
	}
	

}
