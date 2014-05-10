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
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import edu.ycp.cs.cs496.collegeplanner.User;
import edu.ycp.cs.cs496.collegeplanner.json.JSON;

public class CurrentSchedule {
	
	public ArrayList<String> getCurrentSchedule(String username) throws ClientProtocolException, URISyntaxException, IOException {
		// Create HTTP client
				 HttpClient client = new DefaultHttpClient();
				 
				// Construct URI
					URI uri;
					
					String location = "/currentSchedule/" + username;
					uri = URIUtils.createURI("http", "10.0.2.2", 8081, location, 
							    null, null);
					
					HttpGet req = new HttpGet(uri);
					HttpResponse resp = client.execute(req);
					ArrayList<String> courses = new ArrayList<String>();
					
					if(resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
						HttpEntity entity = resp.getEntity();
						
						//Is this okay?? Not sure.
						courses = JSON.getObjectMapper().readValue(entity.getContent(), ArrayList.class);
						
					} 
					
		return courses;	
						
					
	}
	
	public boolean addClassToCurrentSchedule(String username, String classInfo, String className) throws ClientProtocolException, URISyntaxException, IOException {
		
		HttpClient client = new DefaultHttpClient();
		 
			// Construct URI
				URI uri;
				
				String location = "/currentSchedule";
				uri = URIUtils.createURI("http", "10.0.2.2", 8081, location, 
						    null, null);
				
				HttpPut req = new HttpPut(uri);
				
				StringWriter sw = new StringWriter();
				
				User u = new User();
				u.setUsername(username);
				
				//major holds classInfo and className is in email. Weird, I know.
				u.setMajor(classInfo);
				u.setEmailAddress(className);
				
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
	
	public boolean removeClassFromCurrentSchedule(String username, String className) throws ClientProtocolException, URISyntaxException, IOException {
		 HttpClient client = new DefaultHttpClient();
		 
			// Construct URI
				URI uri;
				
				String location = "/currentSchedule";
				uri = URIUtils.createURI("http", "10.0.2.2", 8081, location, 
						    null, null);
				
				HttpPost req = new HttpPost(uri);
				StringWriter sw = new StringWriter();
				User u = new User();
				u.setUsername(username);
				//className is in email again
				u.setEmailAddress(className);
				
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
