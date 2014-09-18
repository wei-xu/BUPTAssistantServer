package com.test.Test;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ba.coming.userComing;
import com.ba.implement.userComingImpl;

public class UserRegister extends HttpServlet {

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doPost(request, response);
		
	}

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
		String username = new String(request.getParameter("username").getBytes("ISO-8859-1"),"UTF-8");
		String password = new String(request.getParameter("password").getBytes("ISO-8859-1"),"UTF-8");
		String nickname = new String(request.getParameter("nickname").getBytes("ISO-8859-1"),"UTF-8");
		String school = new String(request.getParameter("school").getBytes("ISO-8859-1"),"UTF-8");
		String classNumber = new String(request.getParameter("classnumber").getBytes("ISO-8859-1"),"UTF-8");
		
		response.setContentType("text/html");
		userComing coming = new userComingImpl();
		String flag = coming.register(username, password, nickname, school, classNumber);
		PrintWriter out = response.getWriter();
		out.println(flag);
		out.flush();
		out.close();
	}

}
