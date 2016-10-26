/**
 * Copyright 2016 Welab, Inc. All rights reserved.
 * WELAB PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.wbpm.test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

import org.apache.commons.configuration.ConfigurationUtils;

/**
 * @author <a href="mailto:ken.wu@wolaidai.com">ken.wu</a>
 */
public class ImageBase64Test
{

	// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
	public static String imageToBase64(String path)
	{
		byte[] data = null;
		// 读取图片字节数组
		try
		{
			InputStream in = new FileInputStream(path);
			data = new byte[in.available()];
			in.read(data);
			in.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		// 对字节数组Base64编码
		return Base64.getEncoder().encodeToString(data);// 返回Base64编码过的字节数组字符串
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		String file = ConfigurationUtils.locate("files/sample.jpg").getFile();
		System.out.println(file);
		System.out.println(ImageBase64Test.imageToBase64(file));
	}

}
