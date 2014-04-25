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
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import edu.ycp.cs.cs496.collegeplanner.json.JSON;
import edu.ycp.cs.cs496.collegeplanner.models.Advisor;
import edu.ycp.cs.cs496.collegeplanner.models.User;

public class getAdvisorDepartments {
	public ArrayList<String> getAdvisorDepartments() throws ClientProtocolException, URISyntaxException, IOException {
		return getAdvisorRequest();
	}

	private ArrayList<String> getAdvisorRequest() throws URISyntaxException, ClientProtocolException, IOException {
		HttpClient client = new DefaultHttpClient();
		
		URI uri;
		
		String location = "/advisor";
		uri = URIUtils.createURI("http", "10.0.2.2", 8081, location, null, null);
		
		HttpGet req = new HttpGet(uri);
		
		HttpResponse response = client.execute(req);
		
		if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			HttpEntity entity = response.getEntity();
			String[] departments = JSON.getObjectMapper().readValue(entity.getContent(), String[].class);
			
			ArrayList<String> advsrs = new ArrayList<String>();
			
			for(int i = 0; i < departments.length; i++) {
				advsrs.add(departments[i]);
			}
			
			return advsrs;
		}
		
		return null;
	}
	
	public ArrayList<String> getAdvisorsByDepartment(String department) throws URISyntaxException, ClientProtocolException, IOException {
		HttpClient client = new DefaultHttpClient();
		
		URI uri;
		
		String location = "/advisor";
		uri = URIUtils.createURI("http", "10.0.2.2", 8081, location, null, null);
		
		HttpPut req = new HttpPut(uri);
		
		StringWriter sw = new StringWriter();
		JSON.getObjectMapper().writeValue(sw, department);
		
		StringEntity reqEntity = new StringEntity(sw.toString());
		reqEntity.setContentType("application/json");
		req.setEntity(reqEntity);
		
		HttpResponse response = client.execute(req);
		
		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			
			HttpEntity entity = response.getEntity();
			ArrayList<String> advisors = JSON.getObjectMapper().readValue(entity.getContent(), ArrayList.class);
			System.out.println("made it to mobile advisor controller with SC_OK");
			return advisors;
			
		} 
	
		return null;
		
	}
}
