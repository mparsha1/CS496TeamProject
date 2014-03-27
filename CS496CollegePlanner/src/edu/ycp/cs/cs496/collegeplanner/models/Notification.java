package edu.ycp.cs.cs496.collegeplanner.models;

public class Notification {
	private String message;
	private Date date;
	private Time time;
	private String method;
	
	public Notification(){
		
	}
	
	public Notification(String message, Date date, Time time, String method) {
		this.message = message;
		this.date = date;
		this.time = time;
		this.method = method;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Time getTime() {
		return time;
	}

	public void setTime(Time time) {
		this.time = time;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
}
