package edu.ycp.cs.cs496.collegeplanner.servlets;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import edu.ycp.cs.cs496.collegeplanner.model.persist.DerbyDatabase;
import edu.ycp.cs.cs496.collegeplanner.model.persist.FakeDatabase;
import edu.ycp.cs.cs496.collegeplanner.persist.DatabaseProvider;

public class DatabaseInitListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		//DatabaseProvider.setInstance(new FakeDatabase()); 
		
		//Were using the real one now!!!!!!!!!!!!
		DatabaseProvider.setInstance(new DerbyDatabase());
		System.out.println("Database initialized!");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}

}
