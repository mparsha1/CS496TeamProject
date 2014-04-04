package edu.ycp.cs.cs496.collegeplanner.controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import edu.ycp.cs.cs496.collegeplanner.models.Course;

/**
 * @author dholtzap
 * GG
 */
public class CourseParser {
	
	public CourseParser() {
		
	}
	// TODO: TEST ME
	public static void main(String[] args) throws IOException{
		Scanner keyboard = new Scanner(System.in);
		System.out.println("course file?:");
		String fileString = keyboard.next();
		File file = new File(fileString);
		CourseParser cp = new CourseParser();
		cp.parseCoursesFromFile(file);
	}
	
	
	public ArrayList<Course> parseCoursesFromFile(File courseFile) throws IOException {
		FileReader fileReader = new FileReader(courseFile);
		BufferedReader bReader = new BufferedReader(fileReader);

		try {
			ArrayList<Course> courses = new ArrayList<Course>();

			while(true) {
				String line = bReader.readLine();
				
				if(line == null) {
					break;
				}
				
				String type = line.substring(0, ' ');
				
				if(type.equals("Course")) {
					Course course = new Course();
					courses.add(course);
				}				
				else if(type.equals("id")){
					String idAsString = line.substring(line.indexOf(',') + 1, line.indexOf('/') - 1);
					int id = Integer.parseInt(idAsString);
					courses.get(courses.size() - 1).setId(id);
				}
				/*else if(type.equals("start-time")) {
					String startTimeAsString = line.substring(line.indexOf(',') + 1, line.indexOf('/') - 1);
					int startTime = Integer.parseInt(startTimeAsString);
					courses.get(courses.size() - 1).setStartTime(startTime);
				}
				else if(type.equals("finish-time")) {
					String finishTimeAsString = line.substring(line.indexOf(',') + 1, line.indexOf('/') - 1);
					int finishTime = Integer.parseInt(finishTimeAsString);
					courses.get(courses.size() - 1).setEndTime(finishTime);
				}*/
				else if(type.equals("name")) {
					String name = line.substring(line.indexOf(',') + 1, line.indexOf('/') - 1);
					courses.get(courses.size() - 1).setName(name);
				}
				else if(type.equals("instructor")) {
					String instructor = line.substring(line.indexOf(',') + 1, line.indexOf('/') - 1);
					courses.get(courses.size() - 1).setInstructor(instructor);
				}
				else if(type.equals("location")) {
					// TODO;
				}
				else if(type.equals("prereq-id")) {
					String prereqIdAsString = line.substring(line.indexOf(',') + 1, line.indexOf('/') - 1);
					int prereq_id = Integer.parseInt(prereqIdAsString);
					courses.get(courses.size() - 1).setPrerequisites(prereq_id);
				}
					
					
				
				
					
			}
				

			System.out.println(courses.toString());
			bReader.close();
			return courses;
		} finally {			
			bReader.close();
		}

	}	
}