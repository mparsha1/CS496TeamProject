package edu.ycp.cs.cs496.collegeplanner.controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class DepartmentParser {
	public DepartmentParser() {

	}

	public ArrayList<String> parseDepartments(File departmentFile) throws IOException {
		if(!departmentFile.exists()) {
			return null;
		}

		FileReader freader = new FileReader(departmentFile);
		BufferedReader breader = new BufferedReader(freader);

		ArrayList<String> departments = new ArrayList<String>();

		try {
			while(true) {
				String department = breader.readLine();
				
				if(department == null) {
					break;
				}
				
				departments.add(department);
				
			}
			
		System.out.println(departments.toString());
		return departments;
		} finally {
			breader.close();
		}
	}
}

