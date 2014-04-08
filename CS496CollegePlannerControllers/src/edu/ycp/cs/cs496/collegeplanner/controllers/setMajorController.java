package edu.ycp.cs.cs496.collegeplanner.controllers;

import edu.ycp.cs.cs496.collegeplanner.model.persist.DatabaseProvider;
import edu.ycp.cs.cs496.collegeplanner.model.persist.IDatabase;

public class setMajorController {
		public boolean setMajor(String username, String major) {
			IDatabase db = DatabaseProvider.getInstance();
			
			boolean worked = db.setMajor(username, major);
			
			return worked;
			
		}
}
