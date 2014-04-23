package edu.ycp.cs.cs496.collegeplanner.controllers;

import edu.ycp.cs.cs496.collegeplanner.models.Advisor;
import edu.ycp.cs.cs496.collegeplanner.models.User;
import edu.ycp.cs.cs496.collegeplanner.persist.DatabaseProvider;
import edu.ycp.cs.cs496.collegeplanner.persist.IDatabase;

public class GetAdvisorForUserController {
	public Advisor getAdvisorForUser(User user) {
		IDatabase db = DatabaseProvider.getInstance();
		
		return db.getAdvisorForUser(user);
	}
}
