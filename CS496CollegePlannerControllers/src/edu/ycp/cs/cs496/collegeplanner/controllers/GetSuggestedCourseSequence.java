package edu.ycp.cs.cs496.collegeplanner.controllers;

import java.util.ArrayList;

import edu.ycp.cs.cs496.collegeplanner.models.Course;
import edu.ycp.cs.cs496.collegeplanner.models.User;
import edu.ycp.cs.cs496.collegeplanner.models.CourseSequencePairs;
import edu.ycp.cs.cs496.collegeplanner.persist.DatabaseProvider;
import edu.ycp.cs.cs496.collegeplanner.persist.IDatabase;

public class GetSuggestedCourseSequence {
		
	public ArrayList<CourseSequencePairs> getSuggestedSequence(User u) {
		IDatabase db = DatabaseProvider.getInstance();
		
		ArrayList<CourseSequencePairs> sequence = db.getCourseSequence(u.getMajor());
		System.out.println("user in controller: " + u.getUsername());
		ArrayList<Course> taken = db.getCoursesTakenByUser(u.getUsername());
		
		
		if(taken!=null){
			for(int i = 0; i < taken.size(); i++) {
				for(int j = 0; j < sequence.size(); j++) {
					if(taken.get(i).getType().equals(sequence.get(i).getCourseName())) {
						sequence.remove(i);
					}
				}
			}
		}
		
		for(int i = 0; i < sequence.size(); i++) {
			String prereq = sequence.get(i).getPrereq();
			if(!prereq.equals("None")) {
				boolean tookIt = false;
				for(int j = 0; i < taken.size(); j++) {
					if(taken.get(i).getType().equals(prereq)) {
						tookIt = true;
					}
				}
				
				if(tookIt == false) {
					sequence.remove(i);
				}
			}
		}
		
		int count = 0;
		ArrayList<CourseSequencePairs> result = new ArrayList<CourseSequencePairs>();
		
		for(int i = 0; i < sequence.size(); i++) {	
			
				if(sequence.get(i).getCredits() + count <= u.getMaxCredits()) {
					System.out.println(sequence.get(i).getCourseName());
					result.add(sequence.get(i));
					count+= sequence.get(i).getCredits();
					
				}
		}
		
	
	
		System.out.println("Out of loop");
		
		
		return result;
		
	}
}
