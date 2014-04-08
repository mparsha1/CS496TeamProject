package edu.ycp.cs.cs496.collegeplanner.models;
/**
 * 
 * @author dholtzap
 * store all information pertaining to courses
 */
public class Course {
	private int id;
	private int startTime;
	private int endTime;
	private String name;
	private int prereq_id; 
	private String instructor;
	private String location;	// TODO: should location be a class we make?
	private String category;
	
	public Course() {
		
	}
	
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
	
	public int getStartTime() {
		return startTime;
	}
	
	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}
	
	public int getEndTime() {
		return endTime;
	}
	
	public void setEndTime(int endTime) {
		this.endTime = endTime;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getPrerequisites() {
		return prereq_id;
	}
	
	public void setPrerequisites(int prerequisites) {
		this.prereq_id= prerequisites;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
}
