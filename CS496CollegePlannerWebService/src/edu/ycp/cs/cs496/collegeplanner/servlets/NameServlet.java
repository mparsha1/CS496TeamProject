package edu.ycp.cs.cs496.collegeplanner.servlets;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs.cs496.collegeplanner.controllers.GetNameOfUserController;
import edu.ycp.cs.cs496.collegeplanner.json.JSON;

public class NameServlet extends HttpServlet{
	//TODO: do and add servlets to web.xml
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String pathInfo = req.getPathInfo();
		String username = pathInfo.substring(1);
		
		if(username.length() > 0) {
			GetNameOfUserController gnouc = new GetNameOfUserController();
			String name = gnouc.getNameOfUser(username);
			
			Writer writer = resp.getWriter();
			
			resp.setStatus(HttpServletResponse.SC_OK); 
			resp.setContentType("application/json"); 
			JSON.getObjectMapper().writeValue(writer, name);
			
			return;
		} 
		else {
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
			resp.setContentType("text/plain");
			return;
		}
		
		
	}
}
