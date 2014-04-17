package edu.ycp.cs.cs496.collegeplanner.servlets;

import java.io.Console;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs.cs496.collegeplanner.controllers.LoginController;
import edu.ycp.cs.cs496.collegeplanner.json.JSON;
import edu.ycp.cs.cs496.collegeplanner.models.User;

public class LoginServlet extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		
		LoginController controller = new LoginController();
		
		User user = new User();
		user = JSON.getObjectMapper().readValue(req.getReader(), User.class);
		
		boolean result = controller.login(user);
		
		resp.setContentType("text/plain");
		
		
		if(result == true) {	
			
			//set to SC_OK if it worked
			resp.setStatus(HttpServletResponse.SC_OK);
			return;
		}
		
		//set to SC_NOT_FOUND if there is a problem with the username and password.
		resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
		return;	
		
	}
}
