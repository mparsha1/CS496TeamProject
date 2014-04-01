package edu.ycp.cs.cs496.collegeplanner.persistence;

import java.util.HashMap;

import edu.ycp.cs.cs496.collegeplanner.models.User;

/**
 * @author dholtzap
 * (addUser, getUser, deleteUser)
 *  methods to manipulate user objects in the fake database
 */
public class FakeDatabase implements IDatabase {
	HashMap<String, String> users = new HashMap<String, String>();
	
	public boolean addUser(User user) {
		if(users.containsKey(user.getUsername())){
			return false;
		}
		users.put(user.getUsername(), user.getPassword());
		return true;
	}
	
	public User getUser(String username) {
		User user = new User();
		if(!users.containsKey(username)){
			return null;
		}
		user.setUsername(username);
		user.setPassword(users.get(username));
		return user;
	}
	
	public boolean deleteUser(String username) {
		if(users.containsKey(username)){
			users.remove(username);
			return true;
		}
		return false;
	}
}
