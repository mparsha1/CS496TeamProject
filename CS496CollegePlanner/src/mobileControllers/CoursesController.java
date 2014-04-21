package mobileControllers;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.impl.client.DefaultHttpClient;

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
	
	

}
