package com.ba.coming;

import java.util.ArrayList;
import java.util.HashMap;

public interface userComing {
	public String login(String username, String password);
	public String register(String username, String password, String nickname, String school, String classNumber);
	public String addHomework(String classname, String teacher, String deadline, String details);
	public String ChooseClass(String username, String allClass);
	public ArrayList<HashMap<String, String>> MyHomework(String username);
}
