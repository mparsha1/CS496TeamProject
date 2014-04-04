package edu.ycp.cs.cs496.collegeplanner.model.persist;

public class DatabaseProvider {
	private static IDatabase theInstance;
	
	public static void setInstance(IDatabase db){
		theInstance = db;
	}

	/** 
	 * Get the singleton {@link IDatabase} implementation. 
	 *  
	 * @return the singleton {@link IDatabase} implementation 
	 */ 
	public static IDatabase getInstance() {
		if (theInstance == null) {
			throw new IllegalStateException("There is no database instance yet!");
		}
		return theInstance; 
	}
}
