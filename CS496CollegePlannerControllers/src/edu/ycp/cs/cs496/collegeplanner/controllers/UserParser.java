package edu.ycp.cs.cs496.collegeplanner.controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import edu.ycp.cs.cs496.collegeplanner.models.User;

public class UserParser {
	
	public UserParser() {
		
	}
	
	public ArrayList<User> parseUsers(File userFile) throws IOException {
		if(!userFile.exists()) {
			System.out.println("could not find user file");
			return null;
		}
		
		FileReader freader = new FileReader(userFile);
		BufferedReader breader = new BufferedReader(freader);
		
		ArrayList<User> users = new ArrayList<User>();
		
		try {
			while(true) {
				String line = breader.readLine();
				
				if(line == null) {
					break;
				}
				
				String type = line.substring(0, line.indexOf(','));
				
				if(type.equals("User")) {
					User user = new User();
					users.add(user);
				}
				else if(type.equals("id")) {
					String idAsString = line.substring(line.indexOf(',') + 1, line.length());
					int id = Integer.parseInt(idAsString);
					users.get(users.size() - 1).setId(id);
				}
				else if(type.equals("username")) {
					String usernameAsString = line.substring(line.indexOf(',') + 1, line.length());					
					users.get(users.size() - 1).setUsername(usernameAsString);
				}
				else if(type.equals("password")) {
					String passwordAsString = line.substring(line.indexOf(',') + 1, line.length());					
					users.get(users.size() - 1).setPassword(passwordAsString);
				}
				else if(type.equals("name")) {
					String nameAsString = line.substring(line.indexOf(',') + 1, line.length());					
					users.get(users.size() - 1).setName(nameAsString);
				}
				else if(type.equals("emailAddress")) {
					String emailAddressAsString = line.substring(line.indexOf(',') + 1, line.length());					
					users.get(users.size() - 1).setEmailAddress(emailAddressAsString);
				}
				else if(type.equals("major")) {
					String majorAsString = line.substring(line.indexOf(',') + 1, line.length());					
					users.get(users.size() - 1).setMajor(majorAsString);
				}
			}
			
			return users;
		} finally {
			breader.close();
		}
		
		
	}
}
