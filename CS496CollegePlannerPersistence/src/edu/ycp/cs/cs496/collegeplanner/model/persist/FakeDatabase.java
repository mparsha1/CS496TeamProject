package edu.ycp.cs.cs496.collegeplanner.model.persist;

import java.util.ArrayList;
import java.util.HashMap;

import edu.ycp.cs.cs496.collegeplanner.models.Advisor;
import edu.ycp.cs.cs496.collegeplanner.models.Course;
import edu.ycp.cs.cs496.collegeplanner.models.CourseSequencePairs;
import edu.ycp.cs.cs496.collegeplanner.models.CurrentClass;
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
	
	ArrayList<String>  departments;
	
	ArrayList<IntegerPairs> user_advisor;
	
	ArrayList<CourseSequencePairs> sequence;
	ArrayList<CurrentClass> currentClasses;
	
	public FakeDatabase() {
		
		currentClasses = new ArrayList<CurrentClass>();
		CurrentClass c1 = new CurrentClass();
		c1.setNameAndInfo("Class: HIS101\nDay(s): MWF\nLocation: HUM12");
		c1.setUserId(0);
		c1.setCourseName("HIS101");
		currentClasses.add(c1);
		CurrentClass c2 = new CurrentClass();
		c2.setNameAndInfo("Class: CS101\nDay(s): TR\nLocation: KEC119");
		c2.setUserId(0);
		c2.setCourseName("CS101");
		currentClasses.add(c2);

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
		babcock.setDepartment("Physical Sciences");
		babcock.setId(0);
		babcock.setEmail("babcock@ycp.edu");
		babcock.setPhone("717-717-7171");
		
		Advisor hovemeyer = new Advisor();
		hovemeyer.setName("Dr. Hovemeyer");
		hovemeyer.setDepartment("Physical Sciences");
		hovemeyer.setLocation("KEC 112");
		hovemeyer.setId(1);
		hovemeyer.setEmail("hovemeyer@ycp.edu");
		hovemeyer.setPhone("717-777-7777");
		
		Advisor ad1 = new Advisor();
		ad1.setName("Advisor");
		ad1.setDepartment("Behavioral Sciences");
		
		Advisor ad2 = new Advisor();
		ad2.setName("Advisor Too");
		ad2.setDepartment("Behavioral Sciences");
		
		Advisor ad3 = new Advisor();
		ad3.setName("Advisor As well");
		ad3.setDepartment("Nursing");
		
		advisors.add(babcock);
		advisors.add(hovemeyer);
		advisors.add(ad1);
		advisors.add(ad2);
		advisors.add(ad3);
		
		departments = new ArrayList<String>();
		departments.add("Physical Sciences");
		departments.add("English and Humanities");
		departments.add("Behavioral Sciences");
		departments.add("Biological Sciences");
		departments.add("History and Political Science");
		departments.add("Nursing");
		


		Course a = new Course();
		Course b = new Course();
		Course c = new Course();
		Course d = new Course();
		Course e = new Course();
		Course f = new Course();
		Course g = new Course();
		a.setName("BIO 101");
		a.setCategory("Lab Science");
		a.setType("LAB");
		a.setId(0);
		b.setName("CHEM201");
		b.setCategory("Lab Science");
		b.setType("LAB");
		b.setId(1);
		c.setName("CS101");
		c.setCategory("Computer Science");
		c.setType("CS101");
		c.setId(2);
		d.setName("HIS101");
		d.setCategory("History");
		d.setType("ADR");
		d.setId(3);
		e.setName("PHY160");
		e.setCategory("Lab Science");
		e.setType("LAB");
		e.setId(4);
		f.setName("ART300");
		f.setCategory("Arts and Music");
		f.setCategory("ADR");
		f.setId(5);
		g.setName("MAT171");
		g.setCategory("Mathematics");
		g.setId(6);
		g.setType("MAT171");
		

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
		Misty.setName("Misty Parshall");
		Misty.setId(0);
		User Drew = new User();
		Drew.setUsername("dholtzap");
		Drew.setPassword("7");
		Drew.setMajor("Computer Science");
		Drew.setName("Drew Holtzapple");
		Drew.setId(1);
		users.add(Misty);
		users.add(Drew);

		course_user = new ArrayList<IntegerPairs>();

		course_user.add(new IntegerPairs(0,1));
		course_user.add(new IntegerPairs(0,2));
		
		user_advisor = new ArrayList<IntegerPairs>();
		user_advisor.add(new IntegerPairs(0,0));
		user_advisor.add(new IntegerPairs(1,1));
		
		sequence = new ArrayList<CourseSequencePairs>();
		
		sequence.add(new CourseSequencePairs("Computer Science", "CS100", 1, 2, "None"));
		sequence.add(new CourseSequencePairs("Computer Science", "MAT171", 1, 4, "None"));
		sequence.add(new CourseSequencePairs("Computer Science", "LAB", 1, 4, "None"));
		sequence.add(new CourseSequencePairs("Computer Science", "WRT102", 1, 3, "None"));
		sequence.add(new CourseSequencePairs("Computer Science", "ADR", 1, 3, "None"));
		sequence.add(new CourseSequencePairs("Computer Science", "CS101", 1, 2, "CS100"));
		sequence.add(new CourseSequencePairs("Computer Science", "MAT172", 1, 4, "MAT171"));
		sequence.add(new CourseSequencePairs("Computer Science", "LAB", 1, 4, "None"));
		sequence.add(new CourseSequencePairs("Computer Science", "WRT202", 1, 3, "None"));
		sequence.add(new CourseSequencePairs("Computer Science", "IFL100", 1, 2, "None"));
		
		sequence.add(new CourseSequencePairs("Computer Science", "CS201", 2, 4, "CS101"));
		sequence.add(new CourseSequencePairs("Computer Science", "MAT280", 2, 3, "MAT171"));
		sequence.add(new CourseSequencePairs("Computer Science", "LAB", 2, 4, "None"));
		sequence.add(new CourseSequencePairs("Computer Science", "FREE ELECTIVE", 2, 3, "None"));
		sequence.add(new CourseSequencePairs("Computer Science", "PHYS ED", 2, 1, "None"));
		sequence.add(new CourseSequencePairs("Computer Science", "CS320", 2, 3, "CS201"));
		sequence.add(new CourseSequencePairs("Computer Science", "ECE260", 2, 4, "CS201"));
		sequence.add(new CourseSequencePairs("Computer Science", "MAT272", 2, 4, "MAT172"));
		sequence.add(new CourseSequencePairs("Computer Science", "HC101", 2, 3, "None"));
		sequence.add(new CourseSequencePairs("Computer Science", "ADR", 2, 3, "None"));

	}
	
	public ArrayList<String> getCurrentClassSchedule(String username) {
		int userId = getUserID(username);
		ArrayList<String> result = new ArrayList<String>();
		System.out.println("made it to database");
		
		if(userId != -1) {
			for(int i = 0; i < currentClasses.size(); i++) {
				if(currentClasses.get(i).getUserId() == userId) {
					System.out.println("found Course: " + currentClasses.get(i).getCourseName());
					result.add(currentClasses.get(i).getNameAndInfo());
				}
			}
		}
		
		return result;
	}
	
	public boolean addClassToSchedule(String username, String classInfo, String courseName) {
		int userId = getUserID(username);
		boolean validate = true;
		
		if(userId != -1) {
			for(int i = 0; i < currentClasses.size(); i++) {
				if(currentClasses.get(i).getCourseName() == courseName) {
					validate = false;
				}
			}
		}
		if(validate) {
			CurrentClass c = new CurrentClass();
			c.setCourseName(courseName);
			c.setNameAndInfo(classInfo);
			c.setUserId(userId);
			
			currentClasses.add(c);
			return true;
		}
		return false;
		
	}
	
	public boolean removeClassFromSchedule(String username, String courseName) {
		int userId = getUserID(username);
		
		if(userId != -1) {
			for(int i = 0; i < currentClasses.size(); i++) {
				if(currentClasses.get(i).getCourseName() == courseName && currentClasses.get(i).getUserId() == userId) {
					currentClasses.remove(i);
					return true;
				}
			}
		}
		
		return false;
	}
	
	public ArrayList<CourseSequencePairs> getCourseSequence(String major) {
		
		ArrayList<CourseSequencePairs> result = new ArrayList<CourseSequencePairs>();
		for(int i = 0; i < sequence.size(); i++) {
			if(sequence.get(i).getSequenceName().equals(major)) {
				result.add(sequence.get(i));
			}
		}
		
		return result;
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
	
	public ArrayList<Course> getCoursesTakenByUser(String username) {

		int ID = getUserID(username);

		ArrayList<Integer> classes = new ArrayList<Integer>();

		for(int i = 0; i < course_user.size(); i++) {
			if(course_user.get(i).getFirst() == ID) {
				classes.add(course_user.get(i).getSecond());
			}
		}

		ArrayList<Course> classNames = new ArrayList<Course>();

		for(int i = 0; i < classes.size(); i++) {
			Course course = getCourseByID(classes.get(i));
			if(course != null) {
				classNames.add(course);
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
	
	public String getNameOfUser(String username) {
		for(int i = 0; i < users.size(); i++) {
			if(users.get(i).getUsername().equals(username)) {
				return users.get(i).getName();
			}
		}
		
		return null;
	}
	
	public boolean setNameOfUser(String username, String newUsername) {
		for(int i = 0; i < users.size(); i++) {
			if(users.get(i).getUsername().equals(username)) {
				users.get(i).setName(newUsername);
				return true;
			}
		}
		
		return false;
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
	
	public ArrayList<String> getAdvisorDepartments() {
		return new ArrayList<String>(departments);
	}

	@Override
	public boolean setAdvisorForUser(String advisor, String username) {		
		int usernameId = -1;
		int advisorId = -1;
		
		for(int i = 0; i < users.size(); i++) {
			if(users.get(i).getUsername().equals(username)){
				usernameId = users.get(i).getId();
			}
		}
		for(int i = 0; i < advisors.size(); i++) {
			if(advisors.get(i).getName().equals(advisor)){
				advisorId = advisors.get(i).getId();
			}
		}
		for(int i = 0; i < user_advisor.size(); i++) {
			if(user_advisor.get(i).getFirst() == usernameId) {
				user_advisor.get(i).setSecond(advisorId);
				return true;
			}
		}
		
		user_advisor.add(new IntegerPairs(usernameId, advisorId));
		return true;
	}

	@Override
	public Advisor getAdvisorForUser(User user) {
		int userId = -1;
		for(int i = 0; i < users.size(); i++) {
			if(users.get(i).getUsername().equals(user.getUsername())){
				userId = users.get(i).getId();
			}
		}
		int advisorId = -1;
		for(int i = 0; i < user_advisor.size(); i++) {
			if(user_advisor.get(i).getFirst() == userId) {
				advisorId = user_advisor.get(i).getSecond();
			}
		}
		
		for(int i = 0; i < advisors.size(); i++) {
			if(advisors.get(i).getId() == advisorId) {
				return advisors.get(i);
			}
		}
		
		return null;
	}
	
	@Override
	public ArrayList<Advisor> getAdvisorsByDepartment(String department) {
		ArrayList<Advisor> result = new ArrayList<Advisor>();
		
		for(int i = 0; i < advisors.size(); i++) {
			if(advisors.get(i).getDepartment().equals(department)) {
				result.add(advisors.get(i));
			}
		}
		
		return result;
	}

	@Override
	public boolean setPasswordOfUser(String username, String password) {
		for(int i = 0; i < users.size(); i++) {
			if(users.get(i).getUsername().equals(username)) {
				users.get(i).setPassword(password);
				return true;
			}
		}
		return false;
	}

	@Override
	public String getPasswordOfUser(String username) {
		for(int i = 0; i < users.size(); i++) {
			if(users.get(i).getUsername().equals(username)) {
				return users.get(i).getPassword();
			}
		}
		return null;
	}

	@Override
	public boolean changeUsernameOfUser(String username, String newUsername) {
		if(getUser(newUsername) != null) {
			return false;
		}
		for(int i = 0; i < users.size(); i++) {
			if(users.get(i).getUsername().equals(username)) {
				users.get(i).setUsername(newUsername);
				return true;
			}
		}
		return false;
	}
}
