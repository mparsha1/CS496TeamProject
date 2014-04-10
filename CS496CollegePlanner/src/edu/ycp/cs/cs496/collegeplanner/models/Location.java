package edu.ycp.cs.cs496.collegeplanner.models;

/**
 * @author dholtzap
 * class to keep track of information pertaining to locations 
 * at college/university
 */
public class Location {
	private int id;
	private String campus;
	private String building;
	private int roomNumber;
	
	public Location() {
		
	}
	
	public String getCampus() {
		return campus;
	}
	
	public void setCampus(String campus) {
		this.campus = campus;
	}
	
	public int getRoomNumber() {
		return roomNumber;
	}
	
	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}
	
	public String getBuilding() {
		return building;
	}
	
	public void setBuilding(String building) {
		this.building = building;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
