/**
 * Copyright 2015 Welab, Inc. All rights reserved.
 * WELAB PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.wbpm.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;

/**
 * @author <a href="mailto:ken.wu@wolaidai.com">ken.wu</a>
 */
public class HttpClientTest
{
	public static void doPost()
	{
		String url = "http://localhost:8080/wbpm/api/myresource/user2";
		HttpClient httpClient = HttpClients.createDefault();
		HttpPost request = new HttpPost(url);
		try
		{
			final int timeout = 1000*10;
			String json = "{\"name\":\"ken\", \"address\":\"abcdef\",\"age\":33}";
			StringEntity entity = new StringEntity(json, "UTF-8");
			request.setEntity(entity);
			entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            RequestConfig config = RequestConfig.custom().setConnectionRequestTimeout(timeout).setConnectTimeout(timeout).build();
            request.setConfig(config);
//			request.setHeader("ContentType", "application/json");
			HttpResponse response = httpClient.execute(request);
			BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity()  
                    .getContent()));
			String line = null;
			while ((line = reader.readLine()) !=null)
			{
				System.out.println(line);
			}
		}
		catch (ClientProtocolException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void doGet()
	{
		final int timeout = 1000*10;
		String url = "http://test.com";
		HttpClient httpClient = HttpClients.createDefault();
		HttpGet request = new HttpGet(url);
		request.addHeader("X-User-Mobile", "1234567890");
		request.addHeader("X-User-Token", "1234abcd");
		RequestConfig config = RequestConfig.custom().setConnectionRequestTimeout(timeout).setConnectTimeout(timeout).build();
        request.setConfig(config);
		try
		{
			HttpResponse response = httpClient.execute(request);
			BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity()  
                    .getContent()));
			String line = null;
			while ((line = reader.readLine()) !=null)
			{
				System.out.println(line);
			}
		}
		catch (ClientProtocolException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		long start = System.currentTimeMillis();
//		HttpClientTest.doGet();
//		System.out.println("aaaaaaaaaaaa");
		
		HttpClientTest.doPost();
		System.out.println("bbbbbbbbbbbb");
		System.out.println("time : "+ (System.currentTimeMillis() - start));
	}

}
