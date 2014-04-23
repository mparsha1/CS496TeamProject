package edu.ycp.cs.cs496.collegeplanner.controllers;

import java.util.ArrayList;

import edu.ycp.cs.cs496.collegeplanner.models.User;
import edu.ycp.cs.cs496.collegeplanner.persist.DatabaseProvider;
import edu.ycp.cs.cs496.collegeplanner.persist.IDatabase;

public class GetUsersController {
	public ArrayList<User> getUsers() {
		IDatabase db = DatabaseProvider.getInstance();
		return db.getUsers();
	}
}
