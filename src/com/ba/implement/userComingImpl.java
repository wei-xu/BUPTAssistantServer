package com.ba.implement;

import java.awt.List;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import com.ba.DBUtil.DBUtil;
import com.ba.coming.userComing;

public class userComingImpl implements userComing{
	Statement pstmt = null;
	ResultSet rs = null;
	Connection conn = null;
	//开始实现userComing，即一个用户请求到达的函数，操作有登录、注册，以及增删查改数据等；
	//登录方法，实现账户名和密码认证，将返回一个flag来判定认证状态
	@Override
	public String login(String username, String password) {//将用户输入的用户名密码传递进来
		String passwordString = password;
		String pString = "";//用来临时保存将要查询到的密码；
		String flag = "0";
		try{
			conn = DBUtil.getConnection();//来个请求首先链接数据库
			pstmt = conn.createStatement();//用这个连接创建一个statement来执行sql语句
			//通过这个pstmt执行查询命令，将结果返回rs，如果不出错应该只会返回一条结果因为username是唯一的
			rs = pstmt.executeQuery("SELECT *FROM account where username = '"+ username +"'");
			while(rs.next()){
				pString = rs.getString("password");//将条结果的password字段内容赋给pString用于待会比较，好像这个循环语句不加就会出错！
			}
			if (pString.equals("")) {
				//flag = "用户名不存在，请重新输入！"; 
				flag = "2";
			} 
			else {
				if (pString.equals(passwordString)) {
					//flag = "登陆成功！";
					flag="1";
				} else if (pString.equals(passwordString) == false) {
					//flag = "密码错误，请重新输入！"; 
					flag="3";
				}
			}
		}
		catch(SQLException e){
			flag = "4";//访问数据库查询用户出错！
		}
		finally{
			try{
				rs.close();
				pstmt.close();
				conn.close();
			}
			catch(SQLException e){
				e.printStackTrace();
			}
		}
		return flag;
	}
	
	/**注册函数
	 * 
	 * 注册成功返回1
	 * 用户名已经存在，返回2
	 * 请将信息填写完整，返回3
	 * 连接或未知错误，返回4
	 */
	@Override
	public String register(String username, String password, String nickname, String school,
			String classnumber) {
		String pPass = "";
		String flag = "0";
		try{
			 conn = DBUtil.getConnection();
			 pstmt = conn.createStatement(); 
			 rs = pstmt.executeQuery("select *from account where username='"
					+ username + "'"); //查询一遍数据库看看有没有重名
			 System.out.println("检查重名完毕");
			while (rs.next()) { 
				pPass = rs.getString("password");
			}
			if (pPass.equals("")){//pPass为空，则该用户名不存在
			   
				if (username.equals("")||classnumber.equals("")) {
			    	 flag="3";//请将所有信息填写完整	
				}
				else {
					pstmt.executeUpdate("insert into account(username,password,nickname,school,classnumber) values('"
							+ username
							+ "','"
							+ password
							+ "','"
							+ nickname
							+ "','"
							+ school
							+ "','"
							+ classnumber + "')");
					System.out.println("插入新用户"+"username"+"完毕");
					flag = "1";//注册成功！请登录	
				}		
			}else{	
				flag = "2";//用户名已存在！请重新输入
				System.out.println("重名");
			}
	
		} catch (SQLException sql_e) {
			sql_e.printStackTrace();
			System.out.println("SQL异常！");
		}
		finally{
			try {
				rs.close();
				pstmt.close();
				conn.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return flag;
	}
	
	/**
	 * 提交作业实现
	 * 返回1为提交成功
	 * 返回2为重复提交
	 * 返回3为发生错误
	 */
	public String addHomework(String classname, String teacher, String deadline, String details){
		String isEmpty = "";
		String flag = "0";
		try {
			conn = DBUtil.getConnection();
			pstmt = conn.createStatement();
			rs = pstmt.executeQuery("select *from homework where classname='"+ classname + "'" + " AND "
						+ "teacher='"+ teacher + "'" + " AND "
						+ "deadline='"+ deadline + "'"); //查询一遍数据库看看有没有重复
			System.out.println("作业查重完毕");
			while(rs.next()){
				isEmpty = rs.getString("classname");
			}
			if(isEmpty==""){
				pstmt.executeUpdate("insert into homework(classname,teacher,deadline,details) values('"
						+ classname
						+ "','"
						+ teacher
						+ "','"
						+ deadline
						+ "','"
						+ details + "')"); 
		          flag = "1";//作业提交成功
		          System.out.println("插入新作业完毕");
			}
			else{
				flag = "2";//请不要重复提交
			}
			
		}catch(SQLException e){
			e.printStackTrace();
			System.out.println("数据库查询错误1");
			flag = "3";
		}
		finally{
			try{
				rs.close();
				pstmt.close();
				conn.close();
			}
			catch(SQLException e){
				e.printStackTrace();
				flag = "3";
			}
		}
		return flag;
	}

	
	/**
	 * 插入选课信息
	 * 返回1为成功添加
	 * 返回2为出错
	 */
	@Override
	public String ChooseClass(String username, String allClass) {
		String tmp = "";
		if(allClass != null) tmp = allClass;//用来临时保存将要查询到的密码；
		System.out.println("tmp="+tmp);
		String flag = "0";
		String sql = "0";
		try{
			conn = DBUtil.getConnection();//来个请求首先链接数据库
			pstmt = conn.createStatement();//用这个连接创建一个statement来执行sql语句
			System.out.println("数据库连接成功！");
			//通过这个pstmt执行查询命令，将结果返回rs，如果不出错应该只会返回一条结果因为username是唯一的
			int countDelete = pstmt.executeUpdate("DELETE FROM user_class WHERE username = '"+ username +"'");
			System.out.println("成功删除了"+countDelete+"条数据");
			ArrayList<String> list = new ArrayList<>();
			//将allClass字符串里的课程信息提取出来，存在一个arraylist里
			while(!tmp.equals("")){
				list.add((String)tmp.subSequence(0, tmp.indexOf('$')));
				tmp = tmp.substring(tmp.indexOf('$')+1);
			}
			for(int i = 0; i < list.size();i++)
				System.out.println(list.get(i));
			int countInsert = 0;
//			if(!tmp.equals("")){
				for(int i = 0; i < list.size(); i++){
					sql = "INSERT INTO user_class (username, cid) values(" + "'"+ username + "'," + "'" + list.get(i) +"')";
					System.out.println(sql);
					countInsert += pstmt.executeUpdate(sql);
				}
//			}
			System.out.println("成功插入"+ countInsert + "条数据");
			flag = "1";
			System.out.println("返回flag="+flag);
		}
		catch(SQLException e){
			flag = "2";//访问数据库出错！
			System.out.println("访问数据库出错");
		}
		finally{
			try{
				pstmt.close();
				conn.close();
			}
			catch(SQLException e){
				e.printStackTrace();
			}
		}
		return flag;
	}

	@Override
	public ArrayList<HashMap<String, String>> MyHomework(String username) {
		String flag = "0";
		String sql ="0";
		ResultSet resultsetforcid= null;
		ResultSet resultsetforclassname = null;
		ArrayList<HashMap<String, String>> list = new ArrayList<>();
		try{
			conn = DBUtil.getConnection();//来个请求首先链接数据库
			pstmt = conn.createStatement();//用这个连接创建一个statement来执行sql语句
			Statement pstmt1 = conn.createStatement();
			System.out.println("数据库连接成功！");
			sql = "SELECT *FROM user_class WHERE username = '" + username +"'";
			ArrayList<String> al = new ArrayList<>();
			System.out.println(sql);
			rs = pstmt.executeQuery(sql);
			while(rs.next()){
				System.out.println("进入rs循环,username="+username);
				int cid = rs.getInt("cid");//这一句没错
				String cidsql = "SELECT *FROM class WHERE cid=" + cid;
				System.out.println(cidsql);
				resultsetforcid = pstmt1.executeQuery(cidsql);
				System.out.println("done execute cidsql");
				String classname = "";
				while(resultsetforcid.next()){
					classname = resultsetforcid.getString(2);///到底错哪了这句？
					System.out.println("done getString classname="+classname);
					al.add(classname);
				}
			}
			System.out.println("打印al：");
			for(int i = 0; i < al.size();i++)
				System.out.println(al.get(i));
			Statement pstmt2 = conn.createStatement();
			for(int i = 0; i < al.size();i++){
				System.out.println(al.get(i));
				//按照课程名找相应的作业
				String searchHomeworkSql = "SELECT *FROM homework WHERE classname ='" + al.get(i) + "'";
				System.out.println("searchHomeworkSql="+searchHomeworkSql);
				resultsetforclassname = pstmt2.executeQuery(searchHomeworkSql);
				System.out.println("done executeQuery");
				//准备接收变量
				String classname = "";
				String deadline = "";
				
				while(resultsetforclassname.next()){
					//只要还有相应的作业
					HashMap<String, String> hm = new HashMap<>();
					System.out.println("hashmap created");
					classname = resultsetforclassname.getString("classname");
					System.out.println("resultforclassname="+classname);
					deadline = resultsetforclassname.getString("deadline");
					System.out.println("resultforclassname="+deadline);
					hm.put("classname", classname);
					hm.put("deadline", deadline);
					list.add(hm);
				}
				System.out.println("打印list：");
				
			}
			for(int j = 0; j < al.size();j++)
					System.out.println(al.get(j));
		}catch(SQLException e){
			flag = "2";//访问数据库出错！
			e.printStackTrace();
			System.out.println("访问数据库出错");
		}
		finally{
			try{
				rs.close();
				pstmt.close();
				conn.close();
			}
			catch(SQLException e){
				e.printStackTrace();
			}
		}
		return list;
	}
}
