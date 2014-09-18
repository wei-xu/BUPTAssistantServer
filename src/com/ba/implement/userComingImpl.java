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
	//��ʼʵ��userComing����һ���û����󵽴�ĺ����������е�¼��ע�ᣬ�Լ���ɾ������ݵȣ�
	//��¼������ʵ���˻�����������֤��������һ��flag���ж���֤״̬
	@Override
	public String login(String username, String password) {//���û�������û������봫�ݽ���
		String passwordString = password;
		String pString = "";//������ʱ���潫Ҫ��ѯ�������룻
		String flag = "0";
		try{
			conn = DBUtil.getConnection();//�������������������ݿ�
			pstmt = conn.createStatement();//��������Ӵ���һ��statement��ִ��sql���
			//ͨ�����pstmtִ�в�ѯ������������rs�����������Ӧ��ֻ�᷵��һ�������Ϊusername��Ψһ��
			rs = pstmt.executeQuery("SELECT *FROM account where username = '"+ username +"'");
			while(rs.next()){
				pString = rs.getString("password");//���������password�ֶ����ݸ���pString���ڴ���Ƚϣ��������ѭ����䲻�Ӿͻ����
			}
			if (pString.equals("")) {
				//flag = "�û��������ڣ����������룡"; 
				flag = "2";
			} 
			else {
				if (pString.equals(passwordString)) {
					//flag = "��½�ɹ���";
					flag="1";
				} else if (pString.equals(passwordString) == false) {
					//flag = "����������������룡"; 
					flag="3";
				}
			}
		}
		catch(SQLException e){
			flag = "4";//�������ݿ��ѯ�û�����
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
	
	/**ע�ắ��
	 * 
	 * ע��ɹ�����1
	 * �û����Ѿ����ڣ�����2
	 * �뽫��Ϣ��д����������3
	 * ���ӻ�δ֪���󣬷���4
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
					+ username + "'"); //��ѯһ�����ݿ⿴����û������
			 System.out.println("����������");
			while (rs.next()) { 
				pPass = rs.getString("password");
			}
			if (pPass.equals("")){//pPassΪ�գ�����û���������
			   
				if (username.equals("")||classnumber.equals("")) {
			    	 flag="3";//�뽫������Ϣ��д����	
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
					System.out.println("�������û�"+"username"+"���");
					flag = "1";//ע��ɹ������¼	
				}		
			}else{	
				flag = "2";//�û����Ѵ��ڣ�����������
				System.out.println("����");
			}
	
		} catch (SQLException sql_e) {
			sql_e.printStackTrace();
			System.out.println("SQL�쳣��");
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
	 * �ύ��ҵʵ��
	 * ����1Ϊ�ύ�ɹ�
	 * ����2Ϊ�ظ��ύ
	 * ����3Ϊ��������
	 */
	public String addHomework(String classname, String teacher, String deadline, String details){
		String isEmpty = "";
		String flag = "0";
		try {
			conn = DBUtil.getConnection();
			pstmt = conn.createStatement();
			rs = pstmt.executeQuery("select *from homework where classname='"+ classname + "'" + " AND "
						+ "teacher='"+ teacher + "'" + " AND "
						+ "deadline='"+ deadline + "'"); //��ѯһ�����ݿ⿴����û���ظ�
			System.out.println("��ҵ�������");
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
		          flag = "1";//��ҵ�ύ�ɹ�
		          System.out.println("��������ҵ���");
			}
			else{
				flag = "2";//�벻Ҫ�ظ��ύ
			}
			
		}catch(SQLException e){
			e.printStackTrace();
			System.out.println("���ݿ��ѯ����1");
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
	 * ����ѡ����Ϣ
	 * ����1Ϊ�ɹ����
	 * ����2Ϊ����
	 */
	@Override
	public String ChooseClass(String username, String allClass) {
		String tmp = "";
		if(allClass != null) tmp = allClass;//������ʱ���潫Ҫ��ѯ�������룻
		System.out.println("tmp="+tmp);
		String flag = "0";
		String sql = "0";
		try{
			conn = DBUtil.getConnection();//�������������������ݿ�
			pstmt = conn.createStatement();//��������Ӵ���һ��statement��ִ��sql���
			System.out.println("���ݿ����ӳɹ���");
			//ͨ�����pstmtִ�в�ѯ������������rs�����������Ӧ��ֻ�᷵��һ�������Ϊusername��Ψһ��
			int countDelete = pstmt.executeUpdate("DELETE FROM user_class WHERE username = '"+ username +"'");
			System.out.println("�ɹ�ɾ����"+countDelete+"������");
			ArrayList<String> list = new ArrayList<>();
			//��allClass�ַ�����Ŀγ���Ϣ��ȡ����������һ��arraylist��
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
			System.out.println("�ɹ�����"+ countInsert + "������");
			flag = "1";
			System.out.println("����flag="+flag);
		}
		catch(SQLException e){
			flag = "2";//�������ݿ����
			System.out.println("�������ݿ����");
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
			conn = DBUtil.getConnection();//�������������������ݿ�
			pstmt = conn.createStatement();//��������Ӵ���һ��statement��ִ��sql���
			Statement pstmt1 = conn.createStatement();
			System.out.println("���ݿ����ӳɹ���");
			sql = "SELECT *FROM user_class WHERE username = '" + username +"'";
			ArrayList<String> al = new ArrayList<>();
			System.out.println(sql);
			rs = pstmt.executeQuery(sql);
			while(rs.next()){
				System.out.println("����rsѭ��,username="+username);
				int cid = rs.getInt("cid");//��һ��û��
				String cidsql = "SELECT *FROM class WHERE cid=" + cid;
				System.out.println(cidsql);
				resultsetforcid = pstmt1.executeQuery(cidsql);
				System.out.println("done execute cidsql");
				String classname = "";
				while(resultsetforcid.next()){
					classname = resultsetforcid.getString(2);///���״�������䣿
					System.out.println("done getString classname="+classname);
					al.add(classname);
				}
			}
			System.out.println("��ӡal��");
			for(int i = 0; i < al.size();i++)
				System.out.println(al.get(i));
			Statement pstmt2 = conn.createStatement();
			for(int i = 0; i < al.size();i++){
				System.out.println(al.get(i));
				//���տγ�������Ӧ����ҵ
				String searchHomeworkSql = "SELECT *FROM homework WHERE classname ='" + al.get(i) + "'";
				System.out.println("searchHomeworkSql="+searchHomeworkSql);
				resultsetforclassname = pstmt2.executeQuery(searchHomeworkSql);
				System.out.println("done executeQuery");
				//׼�����ձ���
				String classname = "";
				String deadline = "";
				
				while(resultsetforclassname.next()){
					//ֻҪ������Ӧ����ҵ
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
				System.out.println("��ӡlist��");
				
			}
			for(int j = 0; j < al.size();j++)
					System.out.println(al.get(j));
		}catch(SQLException e){
			flag = "2";//�������ݿ����
			e.printStackTrace();
			System.out.println("�������ݿ����");
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
