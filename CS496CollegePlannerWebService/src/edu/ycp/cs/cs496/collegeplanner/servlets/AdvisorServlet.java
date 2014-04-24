package edu.ycp.cs.cs496.collegeplanner.servlets;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs.cs496.collegeplanner.controllers.GetAdvisorForUserController;
import edu.ycp.cs.cs496.collegeplanner.controllers.GetAdvisorsController;
import edu.ycp.cs.cs496.collegeplanner.controllers.GetUsersController;
import edu.ycp.cs.cs496.collegeplanner.controllers.SetAdvisorForUserController;
import edu.ycp.cs.cs496.collegeplanner.json.JSON;
import edu.ycp.cs.cs496.collegeplanner.models.Advisor;
import edu.ycp.cs.cs496.collegeplanner.models.User;

public class AdvisorServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		System.out.println("advisor-servlet-get");
		
		String pathInfo = req.getPathInfo(); 
		Writer writer = resp.getWriter();		
		
		if (pathInfo == null || pathInfo.equals("") || pathInfo.equals("/")) {
			// get all advisors
			GetAdvisorsController gac = new GetAdvisorsController();
			ArrayList<Advisor> advisors = gac.getAdvisors();				
			
			if(!advisors.isEmpty()) {
				resp.setStatus(HttpServletResponse.SC_OK); 
				resp.setContentType("application/json"); 
				JSON.getObjectMapper().writeValue(writer, advisors);	
				return;
			} else {			
				resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
				resp.setContentType("text/plain");
				return;
			}	
		}
		// get advisor for user
		else {
			Advisor advisor = new Advisor();
			
			GetAdvisorForUserController gafuc = new GetAdvisorForUserController();
			
			String username = pathInfo.substring(1);
			
			GetUsersController guc = new GetUsersController();
			
			ArrayList<User> users = guc.getUsers();
			
			for(User usr : users) {
				if(usr.getUsername().equals(username)) {
					advisor = gafuc.getAdvisorForUser(usr);
				}
			}
			
			if(advisor.getName() != null) {
				resp.setStatus(HttpServletResponse.SC_OK); 
				resp.setContentType("application/json"); 
				JSON.getObjectMapper().writeValue(writer, advisor);	
				return;
			} else {			
				resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
				resp.setContentType("text/plain");
				return;
			}	
		}
		
		
			
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		System.out.println("advisor-servlet-post");
		
		String pathInfo = req.getPathInfo(); 
		System.out.println("ok");
		String username = pathInfo.substring(1);
		
		GetUsersController guc = new GetUsersController();
		
		ArrayList<User> users = guc.getUsers();
		User user = new User();
		for(User usr : users) {
			if(usr.getUsername().equals(username)) {
				user = usr;
			}
		}
		Advisor advisor = JSON.getObjectMapper().readValue(req.getReader(), Advisor.class);
		
		System.out.println(user.getUsername() + advisor.getName());
		SetAdvisorForUserController sac = new SetAdvisorForUserController();
		boolean result = sac.setAdvisorForUser(advisor, user);
		
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