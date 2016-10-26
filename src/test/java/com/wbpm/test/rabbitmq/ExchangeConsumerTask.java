/**
 * Copyright 2016 Welab, Inc. All rights reserved.
 * WELAB PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.wbpm.test.rabbitmq;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;
import com.rabbitmq.client.AMQP.BasicProperties;

/**
 * @author <a href="mailto:ken.wu@wolaidai.com">ken.wu</a>
 */
public class ExchangeConsumerTask implements Runnable
{
	private final String name;

	public ExchangeConsumerTask(String name)
	{
		this.name = name;
	}
	/** 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run()
	{
		try
		{
			accept();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private void accept() throws Exception
	{
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		factory.setPort(5566);
		Connection connection = factory.newConnection();
		final Channel channel = connection.createChannel();
		channel.exchangeDeclare("test_direct", "direct", true);
		Thread.sleep(3000);
		while(true)
		{
			System.out.println("Task ["+ name + "] begin accept ...");
			channel.basicConsume("queue_one", false, new Consumer()
			{
				@Override
				public void handleShutdownSignal(String consumerTag, ShutdownSignalException sig)
				{
					// TODO Auto-generated method stub
				}
				
				@Override
				public void handleRecoverOk(String consumerTag)
				{
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
						throws IOException
				{
					System.out.println("Task ["+ name + "] content : " + new String(body, "utf-8"));
					channel.basicAck(envelope.getDeliveryTag(), true);
				}
				
				@Override
				public void handleConsumeOk(String consumerTag)
				{
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void handleCancelOk(String consumerTag)
				{
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void handleCancel(String consumerTag) throws IOException
				{
					// TODO Auto-generated method stub
					
				}
			});
			Thread.sleep(3000);
		}
	}

}
