package com.test.Test;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ba.coming.userComing;
import com.ba.implement.userComingImpl;

public class AddHomework extends HttpServlet {
	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String classname = new String(request.getParameter("classname").getBytes("ISO-8859-1"),"UTF-8"); 
		String teacher = new String(request.getParameter("teacher").getBytes("ISO-8859-1"),"UTF-8"); 
		String deadline = new String(request.getParameter("deadline").getBytes("ISO-8859-1"),"UTF-8"); 
		String details = new String(request.getParameter("details").getBytes("ISO-8859-1"),"UTF-8");

		userComing coming = new userComingImpl();
		String flag = coming.addHomework(classname, teacher, deadline, details);
		System.out.println(flag);
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println(flag);
		out.flush();
		out.close();
	}

}
