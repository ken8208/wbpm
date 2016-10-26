/**
 * Copyright 2016 Welab, Inc. All rights reserved.
 * WELAB PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.wbpm.test.rabbitmq;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.AMQP.Queue.DeclareOk;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author <a href="mailto:ken.wu@wolaidai.com">ken.wu</a>
 */
public class ExchangeProducerDemo
{

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception
	{
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		factory.setPort(5566);
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		channel.exchangeDeclare("test_direct", "direct", true);
//		DeclareOk queueDeclare = channel.queueDeclare("test_queue", true, false, false, null);
//		channel.queueBind(queueDeclare.getQueue(), "test_direct", "test_rounting");
//		channel.exchangeBind(destination, source, routingKey);
		String content = "发送 rounting key one";
		System.out.println("send message ... ");
		BasicProperties.Builder builder = new BasicProperties.Builder();
		builder.deliveryMode(2);
		channel.basicPublish("test_direct", "one", builder.build(), content.getBytes("utf-8"));
	}

}
