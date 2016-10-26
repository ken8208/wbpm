/**
 * Copyright 2015 Welab, Inc. All rights reserved.
 * WELAB PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.wbpm.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * @author <a href="mailto:ken.wu@wolaidai.com">ken.wu</a>
 */
public class HttpClientTest
{
	public static void doRequest() throws Exception
	{
		String mobile = "13988888888";
		String partnerName="ABC";
		long timestamp = System.currentTimeMillis();
		String url = "http://ijapi1.wolaidai.com/jrocket2/api/partner/ABC/check_blacklist"; //partnerName=ABC
		HttpClient httpClient = HttpClients.createDefault();
//		String signData = sign(partnerName+"&"+mobile+"&"+timestamp);//sign方法实现参考1. 数据签名加密样例
		HttpPost post = new HttpPost(url);
		post.addHeader("X-User-Mobile", mobile);
//		post.addHeader("X-Sign-Data", signData);
		post.addHeader("X-User-Mobile", String.valueOf(timestamp));
		post.addHeader("X-Partner-Name", partnerName);
		List <BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
    	params.add(new BasicNameValuePair("mobile", mobile));
    	params.add(new BasicNameValuePair("name", "张三"));
    	params.add(new BasicNameValuePair("cnid", "441103198809085512"));
    	post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
    	HttpResponse response = httpClient.execute(post);
    	if(response.getStatusLine().getStatusCode() == 200)  
        {  
            HttpEntity httpEntity = response.getEntity();  
            String result = EntityUtils.toString(httpEntity);//取出应答字符串
            //result = {"resultCode": "S0000", "message" : "", "data" : {"matched" : true}}
            //接下去根据返回结果解析数据并做相应的业务逻辑处理
        }  
    	
	}
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
