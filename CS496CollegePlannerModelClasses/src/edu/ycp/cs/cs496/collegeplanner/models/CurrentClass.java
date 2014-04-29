package edu.ycp.cs.cs496.collegeplanner.models;

public class CurrentClass {
	
	private int userId;
	private String nameAndInfo;
	private String courseName;
	
	public CurrentClass() {
		
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getNameAndInfo() {
		return nameAndInfo;
	}

	public void setNameAndInfo(String nameAndInfo) {
		this.nameAndInfo = nameAndInfo;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
}
