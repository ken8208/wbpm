/**
 * Copyright 2016 Welab, Inc. All rights reserved.
 * WELAB PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.wbpm.demo;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.wbpm.common.VerifyCodeUtils;

/**
 * @author <a href="mailto:ken.wu@wolaidai.com">ken.wu</a>
 */
public class AuthImage extends HttpServlet
{
	private static final long serialVersionUID = -7462489372582375036L;

	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		System.out.println("AuthImage ......");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/png");

		// 生成随机字串
		String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
		// 生成图片
		int w = 100, h = 40;
		VerifyCodeUtils.outputImage(w, h, response.getOutputStream(), verifyCode);
	}
}
