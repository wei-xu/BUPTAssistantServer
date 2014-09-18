package com.ba.DBUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
	public static Connection getConnection(){
		Connection con = null;
		//JDBC����
		String JDriver = "com.mysql.jdbc.Driver";
		//���ؼ�����ϵ�MySQL���ݿ��URL
		String conURL = "jdbc:mysql://localhost:3306/geeno?useUnicode=true&characterEncoding=GBK";
		try{
			Class.forName(JDriver);
			System.out.println("connecting database");
		}
		catch(ClassNotFoundException cnf_e){//����Ҳ���������
			System.out.println("�Ҳ���������Driver Not Found: "+cnf_e);
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
