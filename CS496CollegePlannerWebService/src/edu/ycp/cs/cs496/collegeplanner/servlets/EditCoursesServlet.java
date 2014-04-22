package edu.ycp.cs.cs496.collegeplanner.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs.cs496.collegeplanner.controllers.CoursesController;
import edu.ycp.cs.cs496.collegeplanner.json.JSON;
import edu.ycp.cs.cs496.collegeplanner.models.User;

public class EditCoursesServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
		
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		User user = JSON.getObjectMapper().readValue(req.getReader(), User.class);
		
		CoursesController controller = new CoursesController();
		boolean verify = controller.addClassToUser(user.getUsername(), user.getMajor());
		
		if(verify) {
			resp.setStatus(HttpServletResponse.SC_OK);
			return;
		}
		
		resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
		return;	
		
	}
	
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		User user = JSON.getObjectMapper().readValue(req.getReader(), User.class);
		CoursesController controller = new CoursesController();
		
		boolean verify = controller.deleteClassFromUser(user.getUsername(), user.getMajor());
		
		if(verify) {
			resp.setStatus(HttpServletResponse.SC_OK);
			return;
		}
		
		resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
		return;	
		
	}
	
}
