package edu.ycp.cs.cs496.collegeplanner.models;

public class Event {
	private Date date;
	private String title;
	private String body;
	private Notification notification;
	private int id;
	private Time startTime;
	private Time endTime;
	
	public Event() {
		
	}
	
	public Event(Date date, String title, String body, Notification notification) {
		this.date = date;
		this.title = title;
		this.body = body;
		this.notification = notification;
		
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public int getId() {
		return id;
	}
	
	public Notification getNotification() {
		return notification;
	}
	
	public void setNotification(Notification notification) {
		this.notification = notification;
	}
	
	public void setStartTime(Time time) {
		this.startTime = time;
	}
	
	public void setEndTime(Time time) {
		this.endTime = time;
	}
	
	public Time getStartTime() {
		return startTime;
	}
	
	public Time getEndTime() {
		return endTime;
	}

	

}
