package edu.ycp.cs.cs496.collegeplanner.controllers;

import edu.ycp.cs.cs496.collegeplanner.persist.DatabaseProvider;
import edu.ycp.cs.cs496.collegeplanner.persist.IDatabase;

public class SetPasswordController {
	public boolean setPassword(String username, String password) {
		IDatabase db = DatabaseProvider.getInstance();
		return db.setPasswordOfUser(username, password);
	}
}
