package edu.ycp.cs.cs496.collegeplanner.servlets;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs.cs496.collegeplanner.controllers.GetPasswordController;
import edu.ycp.cs.cs496.collegeplanner.controllers.SetPasswordController;
import edu.ycp.cs.cs496.collegeplanner.json.JSON;

public class PasswordServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		System.out.println("password-servlet-get");

		Writer writer = resp.getWriter();

		String pathInfo = req.getPathInfo();
		String username = pathInfo.substring(1);

		GetPasswordController gpc = new GetPasswordController();
		String password = gpc.getPassword(username);


		if(password.length() > 0) {
			resp.setStatus(HttpServletResponse.SC_OK); 
			resp.setContentType("application/json"); 
			JSON.getObjectMapper().writeValue(writer, password);
			return;
		}
		else {
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
			resp.setContentType("text/plain");
			return;
		}
	}

		@Override
		protected void doPost(HttpServletRequest req, HttpServletResponse resp)
				throws ServletException, IOException {

			System.out.println("password-servlet-post");
			
			String pathInfo = req.getPathInfo();
			String username = pathInfo.substring(1);
			String password = JSON.getObjectMapper().readValue(req.getReader(), String.class);
			
			SetPasswordController spc = new SetPasswordController();
			boolean result = spc.setPassword(username, password);
			
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