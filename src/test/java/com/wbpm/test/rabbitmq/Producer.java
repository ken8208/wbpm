/**
 * Copyright 2016 Welab, Inc. All rights reserved.
 * WELAB PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.wbpm.test.rabbitmq;

import java.io.Serializable;
import java.util.HashMap;

import org.apache.commons.lang3.SerializationUtils;

/**
 * @author <a href="mailto:ken.wu@wolaidai.com">ken.wu</a>
 */
public class Producer extends EndPoint
{

	public Producer(String endpointName) throws Exception
	{
		super(endpointName);
	}

	public void sendMessage(Serializable object) throws Exception
	{
		channel.basicPublish("", endPointName, null, SerializationUtils.serialize(object));
	}

	public static void main(String[] args) throws Exception
	{
		Producer producer = new Producer("messgeQueue");
		 for (int i = 0; i < 1000; i++) {
            HashMap<String, String> message = new HashMap<String, String>();
            message.put("message number", "send message ["+i+"]. ");
            producer.sendMessage(message);
            Thread.sleep(3000);
        }
	}
}
