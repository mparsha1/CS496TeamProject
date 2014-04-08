package edu.ycp.cs.cs496.collegeplanner.model.persist;

import java.util.ArrayList;
import java.util.HashMap;

import edu.ycp.cs.cs496.collegeplanner.models.User;

/**
 * @author dholtzap
 * (addUser, getUser, deleteUser)
 *  methods to manipulate user objects in the fake database
 */
public class FakeDatabase implements IDatabase {
	HashMap<String, String> users;
	ArrayList<String> majors;
	
	ArrayList<User> users_2;
	
	public FakeDatabase() {
		users = new HashMap<String, String>();
		majors = new ArrayList<String>();
		users.put("mparsha", "abc123");
		users.put("dholtzap", "7");
		majors.add("Computer Science");
		majors.add("Computer Engineering");
		majors.add("Biology");
		majors.add("Chemistry");
		users_2 = new ArrayList<User>();
		
		User Misty = new User();
		Misty.setMajor("Computer Science");
		User Drew = new User();
		Drew.setMajor("Computer Science");
		users_2.add(Misty);
		users_2.add(Drew);
		
	}
	
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
	
	public User getUser_2(String username) {
		
		User user = null;
		
		for(int i = 0; i < users_2.size(); i++) {
			
			if(users_2.get(i).getUsername().equals(username)) {
				user = users_2.get(i);
			}
		}
		
		return user;
	}
	
	public ArrayList<String> getMajors() {
		return majors;
	}
	
	public boolean setMajor(String username, String major) {
		
		User user = getUser_2(username);
		
		if(user != null) {
			user.setMajor(major);
			return true;
		}
		
		return false;
	}
	
	public boolean deleteUser(String username) {
		if(users.containsKey(username)){
			users.remove(username);
			return true;
		}
		return false;
	}
}
