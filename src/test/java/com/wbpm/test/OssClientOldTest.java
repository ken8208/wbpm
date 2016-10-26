/**
 * Copyright 2016 Welab, Inc. All rights reserved.
 * WELAB PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.wbpm.test;

import java.io.IOException;
import java.net.URL;
import java.util.Date;

import com.wolaidai.oss.client.core.OssClientHandler;
import com.wolaidai.oss.client.core.OssClientHandlerBuilder;

/**
 * @author <a href="mailto:ken.wu@wolaidai.com">ken.wu</a>
 */
public class OssClientOldTest
{
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws Exception
	{
		String bucketName = "kentest";
		String endpoint = "http://oss-cn-shenzhen.aliyuncs.com";
		String accessKeyId = "0SbXcBb0oTHoE5EW";
		String accessKeySecret = "vX67ON5yhBVw1GThQWIm85m05fzByu";
		for (int i = 0; i < 50; i++ )
		{
//			InputStream openStream = ConfigurationUtils.locate("files/sample.jpg").openStream();
			OssClientHandler handler = OssClientHandlerBuilder.create().setBucketName(bucketName).setEndPoint(endpoint).setAccessKeyId(accessKeyId).setAccessKeySecret(accessKeySecret).build();
//			handler.putObject("tmp/sample"+i+".jpg", openStream);
			URL presignedUrl = handler.generatePresignedUrl("tmp/sample"+i+".jpg", new Date());
			System.out.println(presignedUrl.toString());
			System.out.println("****************");
			Thread.sleep(3000);
		}
		System.out.println("-----------------------");
		Thread.sleep(100000);
	}

}
