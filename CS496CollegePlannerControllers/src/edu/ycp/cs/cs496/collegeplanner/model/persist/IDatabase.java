package edu.ycp.cs.cs496.collegeplanner.model.persist;

import java.util.ArrayList;
import edu.ycp.cs.cs496.collegeplanner.models.Course;
import edu.ycp.cs.cs496.collegeplanner.models.User;

/**
 * @author dholtzap
 * (addUser, getUser, deleteUser)
 * methods to manipulate user objects in the database
 */
public interface IDatabase {
	
	public boolean addUser(User user);
	
	public User getUser(String username);
	
	public ArrayList<User> getUsers();
	
	public boolean deleteUser(User user);
	
	public ArrayList<String> getMajors();
	
	public boolean setMajor(String username, String major);
	
	public boolean addCourse(Course course);
	
	public ArrayList<Course> getCourses();
	
	public String getMajor(String username);
	
	public ArrayList<String> getClassesTakenByUser(String username);
	
	public ArrayList<String> getClassesInCategory(String category);
	
	public ArrayList<String> getClassCategories();
}
