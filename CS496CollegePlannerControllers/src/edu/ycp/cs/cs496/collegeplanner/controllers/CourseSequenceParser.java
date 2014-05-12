package edu.ycp.cs.cs496.collegeplanner.controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import edu.ycp.cs.cs496.collegeplanner.models.CourseSequencePairs;

public class CourseSequenceParser {

	public CourseSequenceParser() {

	}

	public ArrayList<CourseSequencePairs> parseCourseSequences(File courseSequenceFile) throws IOException {
		if(!courseSequenceFile.exists()) {
			return null;
		}

		FileReader freader = new FileReader(courseSequenceFile);
		BufferedReader breader = new BufferedReader(freader);

		ArrayList<CourseSequencePairs> courseSequences = new ArrayList<CourseSequencePairs>();

		try {
			while(true) {
				String line = breader.readLine();

				if(line == null) {
					break;
				}

				String type = line.substring(0, line.indexOf(','));

				if(type.equals("CourseSequencePair")) {
					CourseSequencePairs csp = new CourseSequencePairs();
					courseSequences.add(csp);
				}
				else if(type.equals("yearNum")) {
					String yearNumAsString = line.substring(line.indexOf(',') + 1, line.length());
					int yearNum = Integer.parseInt(yearNumAsString);
					courseSequences.get(courseSequences.size() - 1).setYearNum(yearNum);
				}
				else if(type.equals("credits")) {
					String creditsAsString = line.substring(line.indexOf(',') + 1, line.length());
					int credits = Integer.parseInt(creditsAsString);
					courseSequences.get(courseSequences.size() - 1).setCredits(credits);
				}
				else if(type.equals("sequenceName")) {
					String sequenceNameAsString = line.substring(line.indexOf(',') + 1, line.length());		
					courseSequences.get(courseSequences.size() - 1).setSequenceName(sequenceNameAsString);
				}
				else if(type.equals("courseName")) {
					String courseNameAsString = line.substring(line.indexOf(',') + 1, line.length());	
					courseNameAsString.replaceAll(" ", "");
					courseSequences.get(courseSequences.size() - 1).setCourseName(courseNameAsString);
				}
				else if(type.equals("prereq")) {
					String prereqAsString = line.substring(line.indexOf(',') + 1, line.length());
					prereqAsString.replaceAll(" ", "");
					courseSequences.get(courseSequences.size() - 1).setPrereq(prereqAsString);
				}				
			}
			
			System.out.println(courseSequences.toString());
			return courseSequences;
		} finally {
			breader.close();
		}
	}
}
