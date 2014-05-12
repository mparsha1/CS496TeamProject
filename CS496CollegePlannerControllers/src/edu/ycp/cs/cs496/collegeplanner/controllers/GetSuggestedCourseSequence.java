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
		ArrayList<Course> taken = db.getCoursesTakenByUser(u.getUsername());
		
		
		if(taken!=null){
			for(int i = 0; i < taken.size(); i++) {
				for(int j = 0; j < sequence.size(); j++) {
					if(taken.get(i).getType().equals(sequence.get(j).getCourseName())) {
						//remove if it has been taken
						sequence.remove(j);
					}
				}
			}
		}
		
		for(int i = 0; i < sequence.size(); i++) {
			
			String prereq = sequence.get(i).getPrereq();
			
			if(!prereq.equals("None")) {
				boolean tookIt = false;
				for(int j = 0; j < taken.size(); j++) {
					
					//remove if the prereq has not been taken
					if(taken.get(j).getType().equals(prereq)) {
						
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
			
			//Only add up to the maximum desired credits
				if(sequence.get(i).getCredits() + count <= u.getMaxCredits()) {
					result.add(sequence.get(i));
					count+= sequence.get(i).getCredits();
					
				}
		}
		
		return result;
		
	}
}
