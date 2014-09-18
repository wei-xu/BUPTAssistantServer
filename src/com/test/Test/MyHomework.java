package com.test.Test;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import com.ba.coming.userComing;
import com.ba.implement.userComingImpl;

public class MyHomework extends HttpServlet {

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
	@SuppressWarnings("null")
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		ArrayList<HashMap<String,String>> list = new ArrayList<>();
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		System.out.println("服务器被访问");
		String username = request.getParameter("username");
		userComing myhomework = new userComingImpl();
		ArrayList<HashMap<String, String>> list = myhomework.MyHomework(username);
		for(int i = 0; i < list.size();i++)
			System.out.println(list.get(i));
		PrintWriter out = response.getWriter();
		try{
			
			StringBuffer sb = new StringBuffer();
			
			sb.append('[');
			
			for(int i = 0; i< list.size();i++){
				sb.append('{').append("\"classname\":").append("\""+list.get(i).get("classname")+"\"").append(",");
				sb.append("\"deadline\":").append("\""+list.get(i).get("deadline")+"\"");
				sb.append('}').append(","); 
			}
			sb.deleteCharAt(sb.length()-1);
			sb.append(']');
			System.out.println(new String(sb));
			out.write(new String(sb));
			out.flush();
			out.close();
		}catch(Exception e){
			System.out.println(e);
			e.printStackTrace();
		}finally{
			out.close();
		}
		
//		String jsonString = "{\"classname\":\"tongxin\",\"deadline\":\"2014-05-21\"}";
//		HashMap<String, String> map = new HashMap<String, String>();
//		HashMap<String, String> map2 = new HashMap<String, String>();
//		HashMap<String, String> map3 = new HashMap<String, String>();
//		map.put("classname", "tongxinyuanli");
//		map.put("deadline", "Deadline:2014-05-14");
//		map.put("classname", "jisuanji");
//		map.put("deadline", "Deadline:2014-05-20");
//		map.put("classname", "jisuanji");
//		map.put("deadline", "Deadline:2014-05-01");
//		list.add(map1);
//		list.add(map2);
//		list.add(map3);
//		PrintWriter out= response.getWriter();
//		out.println(list);
////		
//		out.flush();
//		out.close();
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

		this.doGet(request, response);
	}

}
