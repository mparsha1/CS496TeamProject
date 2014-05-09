package edu.ycp.cs.cs496.collegeplanner.controllers;

import java.util.ArrayList;

import edu.ycp.cs.cs496.collegeplanner.persist.DatabaseProvider;
import edu.ycp.cs.cs496.collegeplanner.persist.IDatabase;

public class CoursesController {
	
	public ArrayList<String> getCategories() {
		IDatabase db = DatabaseProvider.getInstance();
		
		return db.getClassCategories();
	}
	
	public ArrayList<String> getClassesInCategory(String category) {
		IDatabase db = DatabaseProvider.getInstance();
		
		return db.getClassesInCategory(category);
		
	}
	
	public ArrayList<String> getClassesTakenByUser(String username) {
		IDatabase db = DatabaseProvider.getInstance();

		return db.getClassesTakenByUser(username);
	}
	
	public boolean addClassToUser(String username, String className) {
		IDatabase db = DatabaseProvider.getInstance();
		
		return db.addClassToUser(username, className);
	}
	
	public boolean deleteClassFromUser(String username, String className) {
		IDatabase db = DatabaseProvider.getInstance();
		
		return db.deleteClassFromUser(username, className);
	}

}
