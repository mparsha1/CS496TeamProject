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
		
		for(int i = 0; i < taken.size(); i++) {
			for(int j = 0; j < sequence.size(); j++) {
				if(taken.get(i).getType().equals(sequence.get(i).getCourseName())) {
					sequence.remove(i);
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
		int i = 0;
		ArrayList<CourseSequencePairs> result = new ArrayList<CourseSequencePairs>();
		
		while(count < 19) {
			result.add(sequence.get(i));
			count+= sequence.get(i).getCredits();
			i++;
			
		}
		
	
		
		
		
		return result;
		
	}
}
