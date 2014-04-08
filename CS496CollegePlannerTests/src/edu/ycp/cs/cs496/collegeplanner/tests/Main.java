package edu.ycp.cs.cs496.collegeplanner.tests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import edu.ycp.cs.cs496.collegeplanner.controllers.CourseParser;
import edu.ycp.cs.cs496.collegeplanner.models.Course;

public class Main {	
	
	//@Test
	public static void main(String[] args) throws IOException {
		System.out.println("Testing...");
		CourseParser cp = new CourseParser();
		File courseFile = new File("courseFile.txt");
		ArrayList<Course> courses = cp.parseCoursesFromFile(courseFile);
		for(Course course : courses) {
			System.out.println("id: " + course.getId() + "name: " + course.getName() + 
					"instructor: " + course.getInstructor() + "pre-req: " + course.getPrerequisites() + "category" +
					course.getCategory());
					
					
		}
	}

}
