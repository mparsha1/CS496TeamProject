package edu.ycp.cs.cs496.collegeplanner.json;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JSON {
	private static final ObjectMapper objectMapper = new ObjectMapper();
	
	public static ObjectMapper getObjectMapper() {
		return objectMapper;
	}
}
