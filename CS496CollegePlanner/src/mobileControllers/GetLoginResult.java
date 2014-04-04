package mobileControllers;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.impl.client.DefaultHttpClient;



public class GetLoginResult {
	
	public boolean getLoginResult(String username, String password) throws ClientProtocolException, URISyntaxException, IOException {
		return makeLoginRequest(username, password);
	}
	
	private boolean makeLoginRequest(String username, String password) throws URISyntaxException, ClientProtocolException, IOException
	{
		
		// Create HTTP client
		 HttpClient client = new DefaultHttpClient();
		 
		// Construct URI
			URI uri;
			
			String location = "/login";
			uri = URIUtils.createURI("http", "10.0.2.2", 8081, location, 
					    null, null);
			
			// Construct request
			HttpGet request = new HttpGet(uri);
			
			HttpResponse response = client.execute(request);
			
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				// Copy the response body to a string
				HttpEntity entity = response.getEntity();
				
				// Parse JSON
				//return JSON.getObjectMapper().readValue(entity.getContent(), Item.class);
			} 
			
		
		// Return false if invalid response
		return false;
	}
}
