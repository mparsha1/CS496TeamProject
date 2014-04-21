package edu.ycp.cs.cs496.collegeplanner.controllers;

import java.util.ArrayList;

import edu.ycp.cs.cs496.collegeplanner.model.persist.DatabaseProvider;
import edu.ycp.cs.cs496.collegeplanner.model.persist.IDatabase;
import edu.ycp.cs.cs496.collegeplanner.models.User;

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

}
