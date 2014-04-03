package edu.ycp.cs.cs496.collegeplanner.controllers;

import java.util.ArrayList;

import edu.ycp.cs.cs496.collegeplanner.persistence.Database;
import edu.ycp.cs.cs496.collegeplanner.persistence.IDatabase;

public class getMajorsController {
		public ArrayList<String> getMajors() {
			IDatabase db = Database.getInstance();
			
			return db.getMajors();
		}
		
		
}
