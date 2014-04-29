package edu.ycp.cs.cs496.collegeplanner.controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import edu.ycp.cs.cs496.collegeplanner.models.Advisor;

public class AdvisorParser {
	
	public AdvisorParser() {
		
	}
	
	public ArrayList<Advisor> parseAdvisors(File advisorFile) throws IOException {
		
		if(!advisorFile.exists()) {
			return null;
		}
		
		FileReader freader = new FileReader(advisorFile);
		BufferedReader breader = new BufferedReader(freader);
		
		ArrayList<Advisor> advisors = new ArrayList<Advisor>();
		
		try {
			while(true) {
				
				String line = breader.readLine();
				
				if(line == null) {
					break;
				}
				
				String type = line.substring(0, line.indexOf(','));
				
				if(type.equals("Advisor")) {
					Advisor advisor = new Advisor();
					advisors.add(advisor);
				}
				else if(type.equals("id")) {
					String idAsString = line.substring(line.indexOf(',') + 1, line.length());
					int id = Integer.parseInt(idAsString);
					advisors.get(advisors.size() - 1).setId(id);
				}
				else if(type.equals("name")) {
					String nameAsString = line.substring(line.indexOf(',') + 1, line.length());					
					advisors.get(advisors.size() - 1).setName(nameAsString);
				}
				else if(type.equals("department")) {
					String departmentAsString = line.substring(line.indexOf(',') + 1, line.length());					
					advisors.get(advisors.size() - 1).setDepartment(departmentAsString);
				}
				else if(type.equals("location")) {
					String locationAsString = line.substring(line.indexOf(',') + 1, line.length());					
					advisors.get(advisors.size() - 1).setLocation(locationAsString);
				}
				else if(type.equals("email")) {
					String emailAsString = line.substring(line.indexOf(',') + 1, line.length());					
					advisors.get(advisors.size() - 1).setEmail(emailAsString);
				}				
				else if(type.equals("phone")) {
					String phoneAsString = line.substring(line.indexOf(',') + 1, line.length());					
					advisors.get(advisors.size() - 1).setName(phoneAsString);
				}
			}
		} finally {
			breader.close();
		}
		
		return advisors;
	}
}
