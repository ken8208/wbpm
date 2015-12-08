package com.wbpm.demo;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Collections;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

import com.wbpm.common.VerifyCodeUtils;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MyResource
{
	@Context
    HttpServletRequest req;

    @Context
    ServletConfig servletConfig;

    @Context
    ServletContext servletContext;
	/**
	 * Method handling HTTP GET requests. The returned object will be sent to the client as
	 * "text/plain" media type.
	 *
	 * @return String that will be returned as a text/plain response.
	 */
	@GET
	@Path("/got")
	@Produces(MediaType.TEXT_PLAIN)
	public String getIt()
	{
		return "Got it!";
	}

	@GET
	@Path("/msg")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response printMsg(@PathParam("msg")String msg)
	{
		System.out.println("msg : " + msg);
		return Response.ok().entity(Collections.emptyMap()).build();
	}
	
	@POST
	@Path("/user")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response saveUser(@FormParam("name")String name, @FormParam("age")Integer age, @FormParam("address")String address) {
//		Assert.notNull(user, "user为空");
		System.out.println("name : "+name);
		System.out.println("age : "+age);
		System.out.println("address : "+address);
//		System.out.println("user : " +ToStringBuilder.reflectionToString(user));
		return Response.ok().entity(Collections.emptyMap()).build();
	}
	@POST
	@Path("/user2")
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response saveUser(User user) {
		Assert.notNull(user, "user为空");
		System.out.println("user : " +ToStringBuilder.reflectionToString(user));
		try
		{
			Thread.sleep(1000*60);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		System.out.println("user end ");
		return Response.ok().entity(user).build();
	}
	
	@GET
	@Path("/export_file")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response exportAgentProfiles() throws Exception
	{
		String str = "1,aa,bb,cc\r2,dd,ee,ff\r";
		
		 return buildExportFileResponse("AAAA.csv", str.getBytes());
	}

	@GET
	@Path("/verification/random_code")
	@Produces("image/jpeg")
	public void getImageVerifyCode(@Context HttpServletResponse response) throws Exception
	{
		String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
		int w = 66, h = 28;
		// 生成图片
		VerifyCodeUtils.outputImage(w, h, response.getOutputStream(), verifyCode);
	}
	
	private Response buildExportFileResponse(final String fileName, byte[] content) throws UnsupportedEncodingException
	{
		return Response
                .ok(content, MediaType.APPLICATION_OCTET_STREAM_TYPE)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename="+URLDecoder.decode(fileName, "UTF-8"))
                .header(HttpHeaders.CACHE_CONTROL, "no-cache")
                .header(HttpHeaders.CONTENT_LENGTH, content.length)
                .header("Pragma", "no-cache").build();
	}

}
