package com.wbpm.demo;

import java.util.Collections;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

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
	public Response printMsg(@Valid @PathParam("msg")String msg)
	{
		System.out.println("msg : " + msg);
		return Response.ok().entity(Collections.emptyMap()).build();
	}
	
	@POST
	@Path("/user")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response saveUser(@FormParam("name")String name, @FormParam("age")Integer age, @FormParam("address")String address) {
//		Assert.notNull(user, "user为空");
//		System.out.println("user : " +ToStringBuilder.reflectionToString(user));
		return Response.ok().entity(Collections.emptyMap()).build();
	}
	@POST
	@Path("/user2")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response saveUser(@BeanParam User user) {
		Assert.notNull(user, "user为空");
		System.out.println("user : " +ToStringBuilder.reflectionToString(user));
		return Response.ok().entity(Collections.emptyMap()).build();
	}

}
