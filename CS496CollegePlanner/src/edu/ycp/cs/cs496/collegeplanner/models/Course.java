package edu.ycp.cs.cs496.collegeplanner.models;

import java.util.ArrayList;
/**
 * 
 * @author dholtzap
 * store all information pertaining to courses
 */
public class Course {
	private Time startTime;
	private Time endTime;
	private String name;
	private ArrayList<String> prerequisites; // FIXME: could this have a better data-type?
	private String instructor;
	private String location; // TODO: should location be a class we make?
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	public String getInstructor() {
		return instructor;
	}
	
	public void setInstructor(String instructor) {
		this.instructor = instructor;
	}
	
	public Time getStartTime() {
		return startTime;
	}
	
	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}
	
	public Time getEndTime() {
		return endTime;
	}
	
	public void setEndTime(Time endTime) {
		this.endTime = endTime;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public ArrayList<String> getPrerequisites() {
		return prerequisites;
	}
	
	public void setPrerequisites(ArrayList<String> prerequisites) {
		this.prerequisites = prerequisites;
	}
}
