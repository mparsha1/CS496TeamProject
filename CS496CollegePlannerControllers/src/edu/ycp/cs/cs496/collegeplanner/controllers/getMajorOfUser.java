package edu.ycp.cs.cs496.collegeplanner.controllers;

import edu.ycp.cs.cs496.collegeplanner.persist.DatabaseProvider;
import edu.ycp.cs.cs496.collegeplanner.persist.IDatabase;

public class getMajorOfUser {
	
		public String getMajor(String username) {
			IDatabase db = DatabaseProvider.getInstance();
			
			return db.getMajor(username);
		}
}
