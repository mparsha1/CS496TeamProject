package edu.ycp.cs.cs496.collegeplanner.controllers;

import edu.ycp.cs.cs496.collegeplanner.model.persist.DatabaseProvider;
import edu.ycp.cs.cs496.collegeplanner.model.persist.IDatabase;
import edu.ycp.cs.cs496.collegeplanner.models.User;

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
		IDatabase db = DatabaseProvider.getInstance();
		if(db.getUser(username) != null && db.getUser(username).getPassword().equals(password)) {
			return true;
		}		
		return false;		
	}
}
