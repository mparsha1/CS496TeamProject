package edu.ycp.cs.cs496.collegeplanner.servlets;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import edu.ycp.cs.cs496.collegeplanner.model.persist.DatabaseProvider;
import edu.ycp.cs.cs496.collegeplanner.model.persist.FakeDatabase;

public class DatabaseInitListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		DatabaseProvider.setInstance(new FakeDatabase()); // TODO: use real database
		System.out.println("Database initialized!");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}

}
