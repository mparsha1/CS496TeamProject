package edu.ycp.cs.cs496.collegeplanner.persist;

import java.util.ArrayList;

import edu.ycp.cs.cs496.collegeplanner.models.Advisor;
import edu.ycp.cs.cs496.collegeplanner.models.Course;
import edu.ycp.cs.cs496.collegeplanner.models.CourseSequencePairs;
import edu.ycp.cs.cs496.collegeplanner.models.User;

/**
 * @author dholtzap
 * (addUser, getUser, deleteUser)
 * methods to manipulate user objects in the database
 */
public interface IDatabase {
	
	public boolean addUser(User user);
	
	public boolean setNameOfUser(String username, String newUsername);
	
	public boolean setPasswordOfUser(String username, String password);
	
	public String getPasswordOfUser(String username);
	
	public boolean changeUsernameOfUser(String username, String newUsername);
	
	public User getUser(String username);
	
	public ArrayList<User> getUsers();
	
	public boolean addAdvisor(Advisor advsr);
	
	public boolean setAdvisorForUser(String advisor, String user);
	
	public Advisor getAdvisorForUser(User user);
	
	public Advisor getAdvisor(String advisorName);
	
	public ArrayList<Advisor> getAdvisors();
	public ArrayList<String> getAdvisorDepartments();
	public ArrayList<Advisor> getAdvisorsByDepartment(String department);
	
	public boolean deleteUser(User user);
	public String getNameOfUser(String username);
	
	public ArrayList<String> getMajors();
	
	public boolean setMajor(String username, String major);
	
	public boolean addCourse(Course course);
	
	public ArrayList<Course> getCourses();
	
	public String getMajor(String username);
	
	public ArrayList<String> getClassesTakenByUser(String username);
	public ArrayList<Course> getCoursesTakenByUser(String username);
	
	public ArrayList<String> getClassesInCategory(String category);
	
	public ArrayList<String> getClassCategories();
	
	public boolean addClassToUser(String username, String className);
	
	public boolean deleteClassFromUser(String username, String className);
	
	public ArrayList<CourseSequencePairs> getCourseSequence(String major);
	
	public ArrayList<String> getCurrentClassSchedule(String username);
	public boolean addClassToSchedule(String username, String classInfo, String courseName);
	public boolean removeClassFromSchedule(String username, String courseName);
	
	
}
