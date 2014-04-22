package edu.ycp.cs.cs496.collegeplanner.models;

import java.util.ArrayList;
/**
 * 
 * @author dholtzap
 * store all information pertaining to users
 */
public class User {
	private int id;
	private String username;
	private String password;
	private String emailAddress;
	private ArrayList<Course> courses; //FIXME: other possible better data-type?
	private String major;
	// TODO: field for subscriptions after subscriptions class made
	public User() {
		
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getEmailAddress() {
		return emailAddress;
	}
	
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public ArrayList<Course> getCourses() {
		return courses;
	}

	public void setCourses(ArrayList<Course> courses) {
		this.courses = courses;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}	
	
	public String getMajor() {
		return major;
	}
	
	public void setMajor(String major) {
		this.major = major;
	}
	
}
