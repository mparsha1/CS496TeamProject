package edu.ycp.cs.cs496.collegeplanner.model.persist;

import java.util.ArrayList;

import edu.ycp.cs.cs496.collegeplanner.models.Advisor;
import edu.ycp.cs.cs496.collegeplanner.models.Course;
import edu.ycp.cs.cs496.collegeplanner.models.CourseSequencePairs;
import edu.ycp.cs.cs496.collegeplanner.models.User;
import edu.ycp.cs.cs496.collegeplanner.persist.IDatabase;

public class DerbyDatabase implements IDatabase{
	static {
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
		} catch (Exception e) {
			throw new IllegalStateException("Could not load Derby JDBC driver");
		}
	}
	
	//TODO: GOOD LUCK
	
	@Override
	public boolean addUser(User user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean setNameOfUser(String username, String newUsername) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean setPasswordOfUser(String username, String password) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getPasswordOfUser(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean changeUsernameOfUser(String username, String newUsername) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public User getUser(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<User> getUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addAdvisor(Advisor advsr) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean setAdvisorForUser(String advisor, String user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Advisor getAdvisorForUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Advisor getAdvisor(String advisorName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Advisor> getAdvisors() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<String> getAdvisorDepartments() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Advisor> getAdvisorsByDepartment(String department) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteUser(User user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getNameOfUser(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<String> getMajors() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean setMajor(String username, String major) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addCourse(Course course) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<Course> getCourses() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getMajor(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<String> getClassesTakenByUser(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Course> getCoursesTakenByUser(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<String> getClassesInCategory(String category) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<String> getClassCategories() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addClassToUser(String username, String className) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteClassFromUser(String username, String className) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<CourseSequencePairs> getCourseSequence(String major) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<String> getCurrentClassSchedule(String username) {
		// TODO Auto-generated method stub
		return null;
	}
}
