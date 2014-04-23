package edu.ycp.cs.cs496.collegeplanner.model.persist;

import java.util.ArrayList;
import java.util.HashMap;

import edu.ycp.cs.cs496.collegeplanner.models.Advisor;
import edu.ycp.cs.cs496.collegeplanner.models.Course;
import edu.ycp.cs.cs496.collegeplanner.models.IntegerPairs;
import edu.ycp.cs.cs496.collegeplanner.models.User;
import edu.ycp.cs.cs496.collegeplanner.persist.IDatabase;

/**
 * @author dholtzap
 * 
 *  methods to manipulate user, course objects in the fake database
 */
public class FakeDatabase implements IDatabase {	
	ArrayList<String> majors;

	ArrayList<User> users;

	ArrayList<Course> courses;

	ArrayList<IntegerPairs> course_user;

	ArrayList<String> categories;

	ArrayList<Advisor> advisors;
	
	HashMap<User, Advisor> user_advisor;
	
	public FakeDatabase() {

		majors = new ArrayList<String>();		
		majors.add("Computer Science");
		majors.add("Computer Engineering");
		majors.add("Biology");
		majors.add("Chemistry");
		users = new ArrayList<User>();

		categories = new ArrayList<String>();
		categories.add("Lab Science");
		categories.add("Computer Science");
		categories.add("History");
		categories.add("Arts and Music");

		advisors = new ArrayList<Advisor>();
		Advisor babcock = new Advisor();
		babcock.setName("Dr. Babcock");
		babcock.setLocation("KEC 101");
		babcock.setDepartment("Computer Science");
		Advisor hovemeyer = new Advisor();
		hovemeyer.setName("Dr. Hovemeyer");
		hovemeyer.setDepartment("Computer Science");
		hovemeyer.setLocation("KEC 112");
		advisors.add(babcock);
		advisors.add(hovemeyer);


		Course a = new Course();
		Course b = new Course();
		Course c = new Course();
		Course d = new Course();
		Course e = new Course();
		Course f = new Course();
		a.setName("BIO 101");
		a.setCategory("Lab Science");
		a.setId(0);
		b.setName("CHEM201");
		b.setCategory("Lab Science");
		b.setId(1);
		c.setName("CS101");
		c.setCategory("Computer Science");
		c.setId(2);
		d.setName("HIS101");
		d.setCategory("History");
		d.setId(3);
		e.setName("PHY160");
		e.setCategory("Lab Science");
		e.setId(4);
		f.setName("ART300");
		f.setCategory("Arts and Music");
		a.setId(5);

		courses = new ArrayList<Course>();
		courses.add(a);
		courses.add(b);
		courses.add(c);
		courses.add(d);
		courses.add(e);
		courses.add(f);


		User Misty = new User();
		Misty.setUsername("mparsha");
		Misty.setPassword("abc123");
		Misty.setMajor("Undeclared");
		User Drew = new User();
		Drew.setUsername("dholtzap");
		Drew.setPassword("7");
		Drew.setMajor("Computer Science");
		users.add(Misty);
		users.add(Drew);

		course_user = new ArrayList<IntegerPairs>();

		course_user.add(new IntegerPairs(0,1));
		course_user.add(new IntegerPairs(0,2));
		
		user_advisor = new HashMap<User, Advisor>();
		user_advisor.put(Drew, hovemeyer);
		user_advisor.put(Misty, babcock);

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

	private int getUserID(String username) {
		int ID = -1;

		for(int i = 0; i < users.size(); i++) {

			if(users.get(i).getUsername().equals(username)) {
				ID = users.get(i).getId();	
			}
		}

		return ID;

	}

	private int getCourseId(String courseName) {

		int ID = -1;

		for(int i = 0; i < courses.size(); i++) {

			if(courses.get(i).getName().equals(courseName)) {
				ID = courses.get(i).getId();	
			}
		}

		return ID;

	}

	private Course getCourseByID(int ID) {
		for(int i = 0; i < courses.size(); i++) {
			if(courses.get(i).getId() == ID) {
				return courses.get(i);
			}
		}

		return null;
	}

	public User getUser(String username) {
		User user = new User();

		for(int i = 0; i < users.size(); i++) {

			if(users.get(i).getUsername().equals(username)) {
				user.setUsername(users.get(i).getUsername());
				user.setPassword(users.get(i).getPassword());
				user.setMajor(users.get(i).getMajor());
				return user;	
			}
		}
		return null;

	}

	public boolean deleteClassFromUser(String username, String className) {
		int userId = getUserID(username);
		int courseId = getCourseId(className);

		if(userId != -1 && courseId != -1) {
			for(int i = 0; i < course_user.size(); i++) {
				if(course_user.get(i).getFirst() == userId && course_user.get(i).getSecond() == courseId) {
					course_user.remove(i);
					return true;
				}
			}

		}

		return false;

	}

	public boolean addClassToUser(String username, String className) {

		//TODO:  check to make sure the class is not a duplicate!
		int userId = getUserID(username);
		int courseId = getCourseId(className);
		boolean noDuplicates = true;

		for(int i = 0; i < course_user.size(); i++) {
			if(course_user.get(i).getFirst() == userId && course_user.get(i).getSecond() == courseId) {
				noDuplicates = false;
			}
		}


		if(userId != -1 && courseId != -1 && noDuplicates) {
			course_user.add(new IntegerPairs(userId, courseId));
			return true;
		}

		return false;
	}

	public ArrayList<String> getClassCategories() {

		return new ArrayList<String>(categories);

	}

	public ArrayList<String> getClassesInCategory(String category) {

		ArrayList<String> classes = new ArrayList<String>();

		for(int i = 0; i < courses.size(); i++) {

			if( courses.get(i).getCategory().equals(category)) {

				classes.add(courses.get(i).getName());
			}
		}


		return classes;
	}

	public ArrayList<String> getClassesTakenByUser(String username) {

		int ID = getUserID(username);

		ArrayList<Integer> classes = new ArrayList<Integer>();

		for(int i = 0; i < course_user.size(); i++) {
			if(course_user.get(i).getFirst() == ID) {
				classes.add(course_user.get(i).getSecond());
			}
		}

		ArrayList<String> classNames = new ArrayList<String>();

		for(int i = 0; i < classes.size(); i++) {
			Course course = getCourseByID(classes.get(i));
			if(course != null) {
				classNames.add(course.getName());
			}
		}

		return classNames;

	}
	public String getMajor(String username) {

		System.out.println("Database username" + username);
		User u = getUser(username);

		if(u != null) {
			return u.getMajor();
		}	


		return null;
	}

	public ArrayList<User> getUsers() {
		return new ArrayList<User>(users);
	}

	public ArrayList<String> getMajors() {
		return new ArrayList<String>(majors);
	}

	public boolean setMajor(String username, String major) {

		int index = -1;

		for(int i = 0; i < users.size(); i++) {

			if(users.get(i).getUsername().equals(username)) {
				index = i;	
			}
		}

		if(index != -1) {
			users.get(index).setMajor(major);
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

	@Override
	public boolean addAdvisor(Advisor advsr) {
		if(advisors.contains(advsr)) {
			return false;
		}
		advisors.add(advsr);
		return true;
	}

	@Override
	public Advisor getAdvisor(String advisorName) {
		for(Advisor advisor : advisors) {
			if(advisor.getName().equals(advisorName)) {
				return advisor;
			}
		}
		return null;
	}

	@Override
	public ArrayList<Advisor> getAdvisors() {		
		return advisors;
	}

	@Override
	public boolean setAdvisorForUser(Advisor advsr, User usr) {		
		user_advisor.put(usr, advsr);
		return true;
	}

	@Override
	public Advisor getAdvisorForUser(User user) {
		return user_advisor.get(user);		
	}
}
