/**
 * Copyright 2016 Welab, Inc. All rights reserved.
 * WELAB PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.wbpm.test.rabbitmq;


/**
 * @author <a href="mailto:ken.wu@wolaidai.com">ken.wu</a>
 */
public class ExchangeConsumerDemo
{

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception
	{
		Thread one = new Thread(new ExchangeConsumerTask("one"));
		Thread two = new Thread(new ExchangeConsumerTask("two"));
		one.start();
		two.start();
	}

}
