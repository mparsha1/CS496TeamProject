package mobileControllers;

import java.io.IOException;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

import edu.ycp.cs.cs496.collegeplanner.json.JSON;

public class SetPassword {
	public boolean setPassword(String username, String password) throws JsonGenerationException, JsonMappingException, URISyntaxException, IOException {
		return makeSetPasswordRequest(username, password);
	}

	private boolean makeSetPasswordRequest(String username, String password) throws URISyntaxException, JsonGenerationException, JsonMappingException, IOException {
		HttpClient client = new DefaultHttpClient();
		
		URI uri;
		
		String location = "/password/" + username;
		uri = URIUtils.createURI("http", "10.0.2.2", 8081, location, null, null);
		
		HttpPost req = new HttpPost(uri);
		
		StringWriter sw = new StringWriter();
		
		JSON.getObjectMapper().writeValue(sw, password);
		
		StringEntity reqEntity = new StringEntity(sw.toString());
		reqEntity.setContentType("application/json");
		req.setEntity(reqEntity);
		
		HttpResponse response = client.execute(req);
		
		if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			return true;
		}
		
		return false;
	}
}
