package edu.ycp.cs.cs496.collegeplanner.servlets;

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
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		LoginController controller = new LoginController();
		
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		
		User user = new User();
		
		user.setUsername(username);
		user.setPassword(password);
		
		boolean result = controller.login(user);
		
		resp.setContentType("text/plain");
		resp.setStatus(HttpServletResponse.SC_OK);
		
		if(result == true) {			
			
			return;
		}
		
		return;	
		
	}
}
