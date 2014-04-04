package edu.ycp.cs.cs496.collegeplanner.tests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import edu.ycp.cs.cs496.collegeplanner.controllers.CourseParser;
import edu.ycp.cs.cs496.collegeplanner.models.Course;

public class Main {	
	
	@Test
	public void test() throws IOException {
		System.out.println("Testing...");
		CourseParser cp = new CourseParser();
		File courseFile = new File("/coCoursele.txt");
		ArrayList<Course> courses = cp.parseCoursesFromFile(courseFile);
		System.out.println(courses.toString());
	}

}
