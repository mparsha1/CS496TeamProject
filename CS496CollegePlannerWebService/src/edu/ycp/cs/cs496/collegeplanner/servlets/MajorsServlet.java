package edu.ycp.cs.cs496.collegeplanner.servlets;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs.cs496.collegeplanner.controllers.getMajorsController;
import edu.ycp.cs.cs496.collegeplanner.controllers.setMajorController;
import edu.ycp.cs.cs496.collegeplanner.json.JSON;

public class MajorsServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		getMajorsController controller = new getMajorsController();
		
		ArrayList<String> majorsList = new ArrayList<String>();
		
		majorsList = controller.getMajors();
		
		Writer writer = resp.getWriter(); 
		
		if(!majorsList.isEmpty()){
			resp.setStatus(HttpServletResponse.SC_OK); 
			resp.setContentType("application/json"); 
			JSON.getObjectMapper().writeValue(writer, majorsList);	
			return;
		} else {
			
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
			resp.setContentType("text/plain");
			return;
		}
		
		
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		//TODO!!!  Handle when a user changes/selects their major!!! 
		
		setMajorController controller = new setMajorController();
		
		String username = req.getParameter("username");
		String major = req.getParameter("major");
		
		boolean result = controller.setMajor(username, major);
		
		resp.setContentType("text/plain");
		
		if(result) {	
			//set to SC_OK if it worked
			resp.setStatus(HttpServletResponse.SC_OK);
			return;
		}
		
		//set to SC_NOT_FOUND if there is a problem 
		resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
		return;	
		
		
	}

}