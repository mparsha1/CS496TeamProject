package edu.ycp.cs.cs496.collegeplanner.controllers;

import java.util.ArrayList;

import edu.ycp.cs.cs496.collegeplanner.persist.DatabaseProvider;
import edu.ycp.cs.cs496.collegeplanner.persist.IDatabase;

public class GetDepartmentsController {
		public ArrayList<String> getDepartments() {
			IDatabase db = DatabaseProvider.getInstance();
			
			return db.getAdvisorDepartments();
		}
}
