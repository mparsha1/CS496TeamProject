package mobileControllers;

import java.io.IOException;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import edu.ycp.cs.cs496.collegeplanner.User;
import edu.ycp.cs.cs496.collegeplanner.json.JSON;

public class CoursesController {
	
	public ArrayList<String> getCategories() throws ClientProtocolException, URISyntaxException, IOException {
		// Create HTTP client
		 HttpClient client = new DefaultHttpClient();
		 
		// Construct URI
			URI uri;
			
			String location = "/courses";
			uri = URIUtils.createURI("http", "10.0.2.2", 8081, location, 
					    null, null);
			
			HttpGet req = new HttpGet(uri);
			
			HttpResponse resp = client.execute(req);
			
			if(resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = resp.getEntity();
				
				ArrayList<String> categories = new ArrayList<String>();
				
				//Is this okay?? Not sure.
				categories = JSON.getObjectMapper().readValue(entity.getContent(), ArrayList.class);
				
				return categories;
			} else {
			
				return null;	
				
			}
			
	}
	
	public ArrayList<String> getCoursesByCategory(String category) throws ClientProtocolException, URISyntaxException, IOException {
		// Create HTTP client
				 HttpClient client = new DefaultHttpClient();
				 
				// Construct URI
					URI uri;
					
					String location = "/courses" ;
					uri = URIUtils.createURI("http", "10.0.2.2", 8081, location, 
							    null, null);
					
					HttpPost req = new HttpPost(uri);
					
					StringWriter sw = new StringWriter();
					JSON.getObjectMapper().writeValue(sw, category);
					StringEntity reqEntity = new StringEntity(sw.toString());
					reqEntity.setContentType("application/json");
					req.setEntity(reqEntity);
					
					HttpResponse resp = client.execute(req);
					
					HttpEntity entity = resp.getEntity();
						
					ArrayList<String> courses = new ArrayList<String>();
					if(resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
						courses = JSON.getObjectMapper().readValue(entity.getContent(), ArrayList.class);
					}	
						
					return courses;
					
					
	}
	
	public ArrayList<String> getCoursesByUser(String username) throws ClientProtocolException, URISyntaxException, IOException {
		
		HttpClient client = new DefaultHttpClient();
		 
		// Construct URI
			URI uri;
			
			String location = "/courses" ;
			uri = URIUtils.createURI("http", "10.0.2.2", 8081, location, 
					    null, null);
			
			HttpPut req = new HttpPut(uri);
			
			
			StringWriter sw = new StringWriter();
			JSON.getObjectMapper().writeValue(sw, username);
			StringEntity reqEntity = new StringEntity(sw.toString());
			reqEntity.setContentType("application/json");
			req.setEntity(reqEntity);
			
			HttpResponse resp = client.execute(req);
			
			HttpEntity entity = resp.getEntity();
				
			ArrayList<String> courses = new ArrayList<String>();
			
			if(resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				courses = JSON.getObjectMapper().readValue(entity.getContent(), ArrayList.class);
			}	
			
			return courses;
		
	}
	
	public boolean addCourseToUser(String username, String courseName) throws ClientProtocolException, URISyntaxException, IOException {
		
		 HttpClient client = new DefaultHttpClient();
		 
			// Construct URI
				URI uri;
				
				String location = "/userCourses" ;
				uri = URIUtils.createURI("http", "10.0.2.2", 8081, location, 
						    null, null);
				
				HttpPost req = new HttpPost(uri);
				
				StringWriter sw = new StringWriter();
				
				//Only way to send data is to put in in a field so i used the major field.
				User u = new User();
				u.setUsername(username);
				u.setMajor(courseName);
				
				JSON.getObjectMapper().writeValue(sw, u);
				StringEntity reqEntity = new StringEntity(sw.toString());
				reqEntity.setContentType("application/json");
				req.setEntity(reqEntity);
				
				HttpResponse resp = client.execute(req);
				
				if(resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					return true;
				}
				
				return false;
		
	}
	
	public boolean deleteCourseFromUser(String username, String courseName) throws ClientProtocolException, URISyntaxException, IOException {
		 HttpClient client = new DefaultHttpClient();
		 
			// Construct URI
				URI uri;
				
				String location = "/userCourses" ;
				uri = URIUtils.createURI("http", "10.0.2.2", 8081, location, 
						    null, null);
				
				HttpPut req = new HttpPut(uri);
				
				StringWriter sw = new StringWriter();
				
				//Only way to send data is to put in in a field so i used the major field.
				User u = new User();
				u.setUsername(username);
				u.setMajor(courseName);
				
				JSON.getObjectMapper().writeValue(sw, u);
				StringEntity reqEntity = new StringEntity(sw.toString());
				reqEntity.setContentType("application/json");
				req.setEntity(reqEntity);
				
				HttpResponse resp = client.execute(req);
				
				if(resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					return true;
				}
				
				return false;
	}
	
	

}
