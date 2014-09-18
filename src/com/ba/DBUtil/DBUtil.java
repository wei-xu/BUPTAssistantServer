package com.ba.DBUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
	public static Connection getConnection(){
		Connection con = null;
		//JDBC驱动
		String JDriver = "com.mysql.jdbc.Driver";
		//本地计算机上的MySQL数据库的URL
		String conURL = "jdbc:mysql://localhost:3306/geeno?useUnicode=true&characterEncoding=GBK";
		try{
			Class.forName(JDriver);
			System.out.println("connecting database");
		}
		catch(ClassNotFoundException cnf_e){//如果找不到驱动类
			System.out.println("找不到驱动类Driver Not Found: "+cnf_e);
		}
		try{
			con = DriverManager.getConnection(conURL,"user","920325");
		}
		catch(SQLException sql_e){
			System.out.println("sql_e");
		}
		return con;
	}
}
