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

public class CoursesServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		ArrayList<String> classes = new ArrayList<String>();
		CoursesController controller = new CoursesController();
		Writer writer = resp.getWriter();
		
		String pathInfo = req.getPathInfo(); 
		
		if (pathInfo == null || pathInfo.equals("") || pathInfo.equals("/")) { 
			//return all courses
			
			classes = controller.getCategories();
			resp.setStatus(HttpServletResponse.SC_OK); 
			resp.setContentType("application/json"); 
			JSON.getObjectMapper().writeValue(writer, classes);
			return;
			
		}
		
		if (pathInfo.startsWith("/")) { 
			pathInfo = pathInfo.substring(1); 
		} 
		
		if(pathInfo.equals("categories")) {
			
			classes = controller.getCategories();
			resp.setStatus(HttpServletResponse.SC_OK); 
			resp.setContentType("application/json"); 
			JSON.getObjectMapper().writeValue(writer, classes);
			return;
			
		} else {
			//return all classes taken by user
			classes = controller.getClassesTakenByUser(pathInfo);
			resp.setStatus(HttpServletResponse.SC_OK); 
			resp.setContentType("application/json"); 
			JSON.getObjectMapper().writeValue(writer, classes);
			return;
		}
			
		
		
	}

}
