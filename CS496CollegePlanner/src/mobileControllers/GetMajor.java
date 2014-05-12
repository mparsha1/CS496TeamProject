package mobileControllers;

import java.io.IOException;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import edu.ycp.cs.cs496.collegeplanner.json.JSON;
import edu.ycp.cs.cs496.collegeplanner.models.User;

public class GetMajor {
	
	public String getMajor(String username) throws ClientProtocolException, URISyntaxException, IOException {
		return getMajorRequest(username);
	}

	private String getMajorRequest(String username) throws ClientProtocolException, URISyntaxException, IOException {
		
		HttpClient client = new DefaultHttpClient();
		
		URI uri;
		
		String location = "/majors";
		uri = URIUtils.createURI("http", "10.0.2.2", 8081, location, 
				    null, null);
		
		HttpPost req = new HttpPost(uri);
		
		User user = new User();
		user.setUsername(username);
		StringWriter sw = new StringWriter();
		JSON.getObjectMapper().writeValue(sw, user);
		
		StringEntity reqEntity = new StringEntity(sw.toString());
		reqEntity.setContentType("application/json");
		req.setEntity(reqEntity);
		
		HttpResponse response = client.execute(req);
		
		
		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			
			HttpEntity entity = response.getEntity();
			user = JSON.getObjectMapper().readValue(entity.getContent(), User.class);
			return user.getMajor();
			
		} 
		
		return null;
	}

}
