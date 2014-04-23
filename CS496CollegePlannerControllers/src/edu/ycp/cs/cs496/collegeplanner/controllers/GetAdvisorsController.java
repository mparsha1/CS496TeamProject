package edu.ycp.cs.cs496.collegeplanner.controllers;

import java.util.ArrayList;

import edu.ycp.cs.cs496.collegeplanner.models.Advisor;
import edu.ycp.cs.cs496.collegeplanner.persist.DatabaseProvider;
import edu.ycp.cs.cs496.collegeplanner.persist.IDatabase;

public class GetAdvisorsController {
	public ArrayList<Advisor> getAdvisors() {
		IDatabase db = DatabaseProvider.getInstance();
		return db.getAdvisors();
	}
}
