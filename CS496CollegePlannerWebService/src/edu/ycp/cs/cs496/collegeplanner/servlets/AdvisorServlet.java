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
import edu.ycp.cs.cs496.collegeplanner.controllers.GetDepartmentsController;
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
			GetDepartmentsController controller = new GetDepartmentsController();
			
			ArrayList<String> departments = controller.getDepartments();				
			
			if(!departments.isEmpty()) {
				resp.setStatus(HttpServletResponse.SC_OK); 
				resp.setContentType("application/json"); 
				JSON.getObjectMapper().writeValue(writer, departments);	
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
					System.out.println("Advisor: " + advisor);
				}
			}
			if(advisor == null) {
				Advisor none = new Advisor();
				none.setDepartment("N/A");
				none.setEmail("N/A");
				none.setId(-1);
				none.setLocation("N/A");
				none.setPhone("N/A");
				none.setName("Not Specified");
				resp.setStatus(HttpServletResponse.SC_OK); 
				resp.setContentType("application/json"); 
				JSON.getObjectMapper().writeValue(writer, none);	
				return;
			}
			
			if(advisor.getName() != null) {
				System.out.println("Inside not null in advisor get");
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
	
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Writer writer = resp.getWriter();
		
		System.out.println("in put thingy");
		String department = JSON.getObjectMapper().readValue(req.getReader(), String.class);
		GetAdvisorsController controller = new GetAdvisorsController();
		ArrayList<Advisor> advisors = controller.getAdvisors(department);
		ArrayList<String> advNames = new ArrayList<String>();
		for(int i = 0; i < advisors.size(); i++) {
			advNames.add(advisors.get(i).getName());
		}
		
		
		
		if(!advisors.isEmpty()) {
			System.out.println("advisors is not empty! First one: " + advisors.get(0).getName());
			resp.setStatus(HttpServletResponse.SC_OK); 
			resp.setContentType("text/plain"); 			
			JSON.getObjectMapper().writeValue(writer, advNames);	
			return;
		} else {
			System.out.println("advisors is empty from the database! :(");
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
			resp.setContentType("text/plain");
			return;
		}
		
	}

}
