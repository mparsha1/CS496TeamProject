package edu.ycp.cs.cs496.collegeplanner.controllers;

import java.util.ArrayList;

import edu.ycp.cs.cs496.collegeplanner.persist.DatabaseProvider;
import edu.ycp.cs.cs496.collegeplanner.persist.IDatabase;

public class getMajorsController {
		public ArrayList<String> getMajors() {
			IDatabase db = DatabaseProvider.getInstance();
			
			return db.getMajors();
		}
		
		
}
