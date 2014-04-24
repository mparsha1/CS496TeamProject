package edu.ycp.cs.cs496.collegeplanner.controllers;

import edu.ycp.cs.cs496.collegeplanner.persist.DatabaseProvider;
import edu.ycp.cs.cs496.collegeplanner.persist.IDatabase;

public class SetNameOfUserController {
	public boolean setNameOfUser(String username, String name) {
		IDatabase db = DatabaseProvider.getInstance();
		return db.setNameOfUser(username, name);
	}
}
