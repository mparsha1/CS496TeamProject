package edu.ycp.cs.cs496.collegeplanner.servlets;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs.cs496.collegeplanner.controllers.CoursesController;
import edu.ycp.cs.cs496.collegeplanner.json.JSON;
import edu.ycp.cs.cs496.collegeplanner.models.User;

public class CoursesServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		ArrayList<String> classes = new ArrayList<String>();
		CoursesController controller = new CoursesController();
		Writer writer = resp.getWriter();

		classes = controller.getCategories();
			
			
		
		if(classes.size() > 0 ) {
			resp.setStatus(HttpServletResponse.SC_OK); 
			resp.setContentType("application/json"); 
			JSON.getObjectMapper().writeValue(writer, classes);
			return;
		}
		
		else {
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		
		
	}
	
	@Override 
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		
		ArrayList<String> classes = new ArrayList<String>();
		CoursesController controller = new CoursesController();
		Writer writer = resp.getWriter();
		
		String category = JSON.getObjectMapper().readValue(req.getReader(), String.class);
		classes = controller.getClassesInCategory(category);
		
		if(!classes.isEmpty()) {
			resp.setStatus(HttpServletResponse.SC_OK); 
			resp.setContentType("application/json"); 
			JSON.getObjectMapper().writeValue(writer, classes);	
			return;
		} else {
			
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
			resp.setContentType("text/plain");
			return;
		}
		
	}
	
	@Override 
	protected void doPut(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		
		ArrayList<String> classes = new ArrayList<String>();
		CoursesController controller = new CoursesController();
		Writer writer = resp.getWriter();
		
		String username = JSON.getObjectMapper().readValue(req.getReader(), String.class);
		classes = controller.getClassesTakenByUser(username);
		
		
		if(!classes.isEmpty()) {
			resp.setStatus(HttpServletResponse.SC_OK); 
			resp.setContentType("application/json"); 
			JSON.getObjectMapper().writeValue(writer, classes);	
			return;
		} else {
			
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
			resp.setContentType("text/plain");
			return;
		}
		
	}

}
