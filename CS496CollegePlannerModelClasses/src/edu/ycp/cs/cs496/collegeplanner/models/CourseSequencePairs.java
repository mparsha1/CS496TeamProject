package edu.ycp.cs.cs496.collegeplanner.models;

public class CourseSequencePairs {
	
	

	private String sequenceName;
	private String courseName;
	private int yearNum;
	private int credits;
	private String prereq;
	
	public CourseSequencePairs() {
		
	}
	
	public CourseSequencePairs(String sequenceName, String courseName,
			int yearNum, int credits, String prereq) {
		this.sequenceName = sequenceName;
		this.courseName = courseName;
		this.yearNum = yearNum;
		this.credits = credits;
		this.prereq = prereq;
	}

	public String getSequenceName() {
		return sequenceName;
	}

	public void setSequenceName(String sequenceName) {
		this.sequenceName = sequenceName;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public int getYearNum() {
		return yearNum;
	}

	public void setYearNum(int yearNum) {
		this.yearNum = yearNum;
	}

	public String getPrereq() {
		return prereq;
	}

	public void setPrereq(String prereq) {
		this.prereq = prereq;
	}

	public int getCredits() {
		return credits;
	}

	public void setCredits(int credits) {
		this.credits = credits;
	}
	
	

}
