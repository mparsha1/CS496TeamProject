package edu.ycp.cs.cs496.collegeplanner.tests;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import edu.ycp.cs.cs496.collegeplanner.controllers.AdvisorParser;
import edu.ycp.cs.cs496.collegeplanner.controllers.CourseParser;
import edu.ycp.cs.cs496.collegeplanner.controllers.DepartmentParser;
import edu.ycp.cs.cs496.collegeplanner.models.Advisor;
import edu.ycp.cs.cs496.collegeplanner.models.Course;

public class Main {	
	
	//@Test
	public static void main(String[] args) throws IOException {
		System.out.println("Testing...");
		CourseParser cp = new CourseParser();
		File courseFile = new File("courseFile.txt");
		ArrayList<Course> courses = cp.parseCoursesFromFile(courseFile);
		for(Course course : courses) {
			System.out.println("id: " + course.getId() + " name: " + course.getName() + 
					" instructor: " + course.getInstructor() + " pre-req: " + course.getPrerequisites() + " category " +
					course.getCategory() + " time: " + course.getStartTime() + "-" + course.getEndTime());
					
					
		}
		
		AdvisorParser ap = new AdvisorParser();
		File advisorFile = new File("advisorFile.txt");
		ArrayList<Advisor> advisors = ap.parseAdvisors(advisorFile);
		for(Advisor advisor : advisors) {
			System.out.println(advisor.getName());
		}
		
		DepartmentParser dp = new DepartmentParser();
		File departmentFile = new File("departmentsFile.txt");
		ArrayList<String> departments = dp.parseDepartments(departmentFile);
		for(String department : departments) {
			System.out.println(department);
		}
	}

}
