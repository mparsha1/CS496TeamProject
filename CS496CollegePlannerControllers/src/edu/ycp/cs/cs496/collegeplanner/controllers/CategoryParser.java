package edu.ycp.cs.cs496.collegeplanner.controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CategoryParser {
	public CategoryParser() {

	}

	public ArrayList<String> parseCategories(File categoryFile) throws IOException {
		if(!categoryFile.exists()) {
			return null;
		}

		FileReader freader = new FileReader(categoryFile);
		BufferedReader breader = new BufferedReader(freader);

		ArrayList<String> categories = new ArrayList<String>();

		// just add the strings to an array, since they only have a name parameter
		
		try {
			while(true) {
				String category = breader.readLine();

				if(category == null) {
					break;
				}
				
				categories.add(category);
			}
			
		System.out.println(categories.toString());
		return categories;
		} finally {
			breader.close();
		}
	}
}

