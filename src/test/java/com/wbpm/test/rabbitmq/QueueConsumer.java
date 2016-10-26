/**
 * Copyright 2016 Welab, Inc. All rights reserved.
 * WELAB PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.wbpm.test.rabbitmq;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.SerializationUtils;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;

/**
 * @author <a href="mailto:ken.wu@wolaidai.com">ken.wu</a>
 */
public class QueueConsumer extends EndPoint implements Consumer
{
	private String name;
	
	public QueueConsumer(String endpointName, String name) throws Exception
	{
		super(endpointName);
		this.name = name;
	}

	public void acceptMessage() throws IOException
	{
        channel.basicConsume(endPointName, true,this);
	}
	/** 
	 * @see com.rabbitmq.client.Consumer#handleConsumeOk(java.lang.String)
	 */
	@Override
	public void handleConsumeOk(String consumerTag)
	{
//		System.out.println(name + " handleConsumeOk : "+consumerTag);
	}

	/** 
	 * @see com.rabbitmq.client.Consumer#handleCancelOk(java.lang.String)
	 */
	@Override
	public void handleCancelOk(String consumerTag)
	{
//		System.out.println(name + " handleCancelOk : "+consumerTag);
	}

	/** 
	 * @see com.rabbitmq.client.Consumer#handleCancel(java.lang.String)
	 */
	@Override
	public void handleCancel(String consumerTag) throws IOException
	{
//		System.out.println(name + " handleCancel : "+consumerTag);
	}

	/** 
	 * @see com.rabbitmq.client.Consumer#handleDelivery(java.lang.String, com.rabbitmq.client.Envelope, com.rabbitmq.client.AMQP.BasicProperties, byte[])
	 */
	@Override
	public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
			throws IOException
	{
//		System.out.println(name + " handleDelivery : "+consumerTag);
		Map map = (HashMap)SerializationUtils.deserialize(body);
        System.out.println(name + " Message Number "+ map.get("message number") + " received.");
	}

	/** 
	 * @see com.rabbitmq.client.Consumer#handleShutdownSignal(java.lang.String, com.rabbitmq.client.ShutdownSignalException)
	 */
	@Override
	public void handleShutdownSignal(String consumerTag, ShutdownSignalException sig)
	{
//		System.out.println(name + " handleShutdownSignal : "+consumerTag);
	}

	/** 
	 * @see com.rabbitmq.client.Consumer#handleRecoverOk(java.lang.String)
	 */
	@Override
	public void handleRecoverOk(String consumerTag)
	{
//		System.out.println(name + " handleRecoverOk : "+consumerTag);
	}

	public static void main(String[] args) throws Exception
	{
		QueueConsumer consumer = new QueueConsumer("messgeQueue", "Consumer1");
		QueueConsumer consumer2 = new QueueConsumer("messgeQueue", "Consumer2");
		System.out.println("consumer accept message start.");
		while(true)
		{
			consumer.acceptMessage();
			consumer2.acceptMessage();
			Thread.sleep(2000);
		}
	}
}
