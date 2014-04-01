package edu.ycp.cs.cs496.collegeplanner.persistence;

import edu.ycp.cs.cs496.collegeplanner.models.User;

/**
 * @author dholtzap
 * (addUser, getUser, deleteUser)
 * methods to manipulate user objects in the database
 */
public interface IDatabase {
	
	public boolean addUser(User user);
	
	public User getUser(String username);
	
	public boolean deleteUser(String username);
	
}
