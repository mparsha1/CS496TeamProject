package edu.ycp.cs.cs496.collegeplanner.model.persist;

import java.util.ArrayList;
import edu.ycp.cs.cs496.collegeplanner.models.Course;
import edu.ycp.cs.cs496.collegeplanner.models.User;

/**
 * @author dholtzap
 * 
 *  methods to manipulate user, course objects in the fake database
 */
public class FakeDatabase implements IDatabase {	
	ArrayList<String> majors;
	
	ArrayList<User> users;
	
	ArrayList<Course> courses;
	
	public FakeDatabase() {
		
		majors = new ArrayList<String>();		
		majors.add("Computer Science");
		majors.add("Computer Engineering");
		majors.add("Biology");
		majors.add("Chemistry");
		users = new ArrayList<User>();
		
		User Misty = new User();
		Misty.setMajor("Computer Science");
		User Drew = new User();
		Drew.setMajor("Computer Science");
		users.add(Misty);
		users.add(Drew);
		
	}
	
	public boolean addUser(User user) {
		if(users.contains(user)){
			return false;
		}
		
		User newUser = new User();
		newUser.setUsername(user.getUsername());
		newUser.setPassword(user.getPassword());
		users.add(newUser);
		return true;
	}
	
	public User getUser(String username) {
		User user = new User();
		
		for(int i = 0; i < users.size(); i++) {
			
			if(users.get(i).getUsername().equals(username)) {
				user.setUsername(users.get(i).getUsername());
				user.setPassword(users.get(i).getPassword());
			}
		}
		
		return user;	
	}
	
	public ArrayList<User> getUsers() {
		return new ArrayList<User>(users);
	}
	
	public ArrayList<String> getMajors() {
		return new ArrayList<String>(majors);
	}
	
	public boolean setMajor(String username, String major) {
		
		User user = getUser(username);
		
		if(user != null) {
			user.setMajor(major);
			return true;
		}
		
		return false;
	}
	
	public boolean deleteUser(User user) {
		if(users.contains(user)){
			users.remove(user);
			return true;
		}
		return false;
	}

	@Override
	public boolean addCourse(Course course) {
		if(courses.contains(course)) {
			return false;
		}
		
		courses.add(course);
		return true;
		
	}

	@Override
	public ArrayList<Course> getCourses() {
		return new ArrayList<Course>(courses);
	}
}
