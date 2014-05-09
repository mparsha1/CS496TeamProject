package edu.ycp.cs.cs496.collegeplanner.servlets;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs.cs496.collegeplanner.controllers.GetSuggestedCourseSequence;
import edu.ycp.cs.cs496.collegeplanner.json.JSON;
import edu.ycp.cs.cs496.collegeplanner.models.CourseSequencePairs;
import edu.ycp.cs.cs496.collegeplanner.models.User;

public class CourseSequenceServlet extends HttpServlet {


	private static final long serialVersionUID = 1L;
	
	@Override 
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		User u = JSON.getObjectMapper().readValue(req.getReader(), User.class);
		GetSuggestedCourseSequence controller = new GetSuggestedCourseSequence();
		System.out.println("username in Servlet: " + u.getUsername());
		ArrayList<CourseSequencePairs> result =  controller.getSuggestedSequence(u);
		ArrayList<String> formattedResult = new ArrayList<String>();
		for(int i = 0; i < result.size(); i++) {
			formattedResult.add("Course: " + result.get(i).getCourseName() + " Credits: " + result.get(i).getCredits());
		}
		
		Writer writer = resp.getWriter();
		
		if(result.isEmpty()) {
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
			resp.setContentType("text/plain");
			return;
		}
		
		resp.setStatus(HttpServletResponse.SC_OK); 
		resp.setContentType("application/json"); 
		JSON.getObjectMapper().writeValue(writer, formattedResult);	
		return;
	}
	
	
	
}
