package mobileControllers;

import java.io.IOException;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;


import edu.ycp.cs.cs496.collegeplanner.User;
import edu.ycp.cs.cs496.collegeplanner.json.JSON;

public class SetMajor {
	
	public boolean setMajor(String username, String major) throws ClientProtocolException, URISyntaxException, IOException {
		
		return makeSetMajorRequest(username, major);
	}
	
	
	private boolean makeSetMajorRequest(String username, String major) throws ClientProtocolException, URISyntaxException, IOException {
		
		 HttpClient client = new DefaultHttpClient();
		 
		 URI uri;
			
			String location = "/majors";
			uri = URIUtils.createURI("http", "10.0.2.2", 8081, location, 
					    null, null);
			
		HttpPut req = new HttpPut(uri);
		
		User user = new User();
		user.setUsername(username);
		user.setMajor(major);
		
		StringWriter sw = new StringWriter();
		JSON.getObjectMapper().writeValue(sw, user);
		
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
