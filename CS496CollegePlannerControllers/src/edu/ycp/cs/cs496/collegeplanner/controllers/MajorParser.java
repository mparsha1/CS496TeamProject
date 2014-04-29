package edu.ycp.cs.cs496.collegeplanner.controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MajorParser {
	public MajorParser() {

	}

	public ArrayList<String> parseMajors(File majorFile) throws IOException {
		if(!majorFile.exists()) {
			return null;
		}

		FileReader freader = new FileReader(majorFile);
		BufferedReader breader = new BufferedReader(freader);

		ArrayList<String> majors = new ArrayList<String>();

		try {
			while(true) {
				String major = breader.readLine();

				if(major == null) {
					break;
				}
				
				majors.add(major);
			}
			
		System.out.println(majors.toString());
		return majors;
		} finally {
			breader.close();
		}
	}
}
