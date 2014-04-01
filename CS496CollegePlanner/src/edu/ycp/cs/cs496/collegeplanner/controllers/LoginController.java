package edu.ycp.cs.cs496.collegeplanner.controllers;

import edu.ycp.cs.cs496.collegeplanner.models.User;
import edu.ycp.cs.cs496.collegeplanner.persistence.Database;
import edu.ycp.cs.cs496.collegeplanner.persistence.IDatabase;

/**
 * 
 * @author dholtzap
 * Controller to validate the user
 */
public class LoginController {
	public boolean login(User user) {		
		return validateUser(user.getUsername(), user.getPassword());		
	}
	
	private boolean validateUser(String username, String password) {
		IDatabase db = Database.getInstance();
		if(db.getUser(username).getPassword().equals(password)) {
			return true;
		}		
		return false;		
	}
}
