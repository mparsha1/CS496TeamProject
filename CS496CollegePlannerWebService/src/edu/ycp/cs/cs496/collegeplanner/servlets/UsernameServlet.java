package edu.ycp.cs.cs496.collegeplanner.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs.cs496.collegeplanner.controllers.ChangeUsernameController;
import edu.ycp.cs.cs496.collegeplanner.json.JSON;

public class UsernameServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		
		String pathInfo = req.getPathInfo();
		String username = pathInfo.substring(1);
		
		String newUsername = JSON.getObjectMapper().readValue(req.getReader(), String.class);
		
		ChangeUsernameController cuc = new ChangeUsernameController();
		boolean result = cuc.changeUsername(username, newUsername);
		
		if(result == true) {
			resp.setStatus(HttpServletResponse.SC_OK); 
			resp.setContentType("text/plain"); 			
			return;
		}
		resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
		resp.setContentType("text/plain");
		return;		
	}
}
