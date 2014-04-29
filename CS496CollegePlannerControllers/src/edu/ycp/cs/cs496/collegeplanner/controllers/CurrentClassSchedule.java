package edu.ycp.cs.cs496.collegeplanner.controllers;

import java.util.ArrayList;

import edu.ycp.cs.cs496.collegeplanner.persist.DatabaseProvider;
import edu.ycp.cs.cs496.collegeplanner.persist.IDatabase;

public class CurrentClassSchedule {
	
	public ArrayList<String> getCurrentClassSchedule(String username) {
		IDatabase db = DatabaseProvider.getInstance();
		
		return db.getCurrentClassSchedule(username);
	}
	
	
	public boolean addClassToCurrentSchedule(String username, String courseInfo, String courseName) {
		IDatabase db = DatabaseProvider.getInstance();
		
		return db.addClassToSchedule(username, courseInfo, courseName);
	}
	
	public boolean removeClassFromCurrentSchedule(String username, String courseName) {
		IDatabase db = DatabaseProvider.getInstance();
		
		return db.removeClassFromSchedule(username, courseName);
	}
}
