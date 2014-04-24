package edu.ycp.cs.cs496.collegeplanner.controllers;

import edu.ycp.cs.cs496.collegeplanner.persist.DatabaseProvider;
import edu.ycp.cs.cs496.collegeplanner.persist.IDatabase;

public class ChangeUsernameController {
	public boolean changeUsername(String username, String newUserName) {
		IDatabase db = DatabaseProvider.getInstance();		
		return db.changeUsernameOfUser(username, newUserName); 
	}
}
