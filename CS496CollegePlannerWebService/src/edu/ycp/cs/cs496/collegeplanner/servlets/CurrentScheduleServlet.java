package edu.ycp.cs.cs496.collegeplanner.servlets;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs.cs496.collegeplanner.controllers.CurrentClassSchedule;
import edu.ycp.cs.cs496.collegeplanner.json.JSON;
import edu.ycp.cs.cs496.collegeplanner.models.User;

public class CurrentScheduleServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String pathInfo = req.getPathInfo(); 
		Writer writer = resp.getWriter();	
		
		String username = pathInfo.substring(1);
		System.out.println("schedule pathInfo " + username);
		
		
		ArrayList<String> result = new ArrayList<String>();
		
		if(username!=null) {
			
			CurrentClassSchedule controller = new CurrentClassSchedule();
			result = controller.getCurrentClassSchedule(username);
			
			if(result.isEmpty()) {
				resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
				resp.setContentType("text/plain");
				return;
			}
		}
		
		resp.setStatus(HttpServletResponse.SC_OK); 
		resp.setContentType("application/json"); 
		JSON.getObjectMapper().writeValue(writer, result);	
		return;
		
		
	}

	@Override 
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		User u = JSON.getObjectMapper().readValue(req.getReader(), User.class);
		CurrentClassSchedule controller = new CurrentClassSchedule();
		
		boolean result = controller.removeClassFromCurrentSchedule(u.getUsername(), u.getEmailAddress());
		System.out.println("Servlet removing: " + u.getEmailAddress());
		
		Writer writer = resp.getWriter();
		
		if(result) {
			resp.setStatus(HttpServletResponse.SC_OK);
			resp.setContentType("text/plain");
			return;
		}
		
		resp.setStatus(HttpServletResponse.SC_NOT_FOUND); 
		resp.setContentType("text/plain"); 	
		return;
		
	}
	
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		User u = JSON.getObjectMapper().readValue(req.getReader(), User.class);
		CurrentClassSchedule controller = new CurrentClassSchedule();
		
		boolean result = controller.addClassToCurrentSchedule(u.getUsername(), u.getMajor(), u.getEmailAddress());
		System.out.println("Serlvet adding: " + u.getEmailAddress());
		
		if(result) {
			resp.setStatus(HttpServletResponse.SC_OK);
			resp.setContentType("text/plain");
			return;
		}
		
		resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
		resp.setContentType("text/plain");
		return;
	}

}
