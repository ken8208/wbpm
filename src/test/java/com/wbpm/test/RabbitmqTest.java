/**
 * Copyright 2016 Welab, Inc. All rights reserved.
 * WELAB PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.wbpm.test;

import com.rabbitmq.client.ConnectionFactory;

/**
 * @author <a href="mailto:ken.wu@wolaidai.com">ken.wu</a>
 */
public class RabbitmqTest
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");

	}

}
