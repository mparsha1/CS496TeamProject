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
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

import edu.ycp.cs.cs496.collegeplanner.json.JSON;
import edu.ycp.cs.cs496.collegeplanner.models.CourseSequencePairs;
import edu.ycp.cs.cs496.collegeplanner.models.User;

public class GetCourseSequence {
	public ArrayList<String> getSequence(User u) throws ClientProtocolException, URISyntaxException, IOException{
		HttpClient client = new DefaultHttpClient();
		
		URI uri;
		
		String location = "/courseSequence";
		uri = URIUtils.createURI("http", "10.0.2.2", 8081, location, 
				    null, null);
		
		HttpPost req = new HttpPost(uri);
		
		StringWriter sw = new StringWriter();
		JSON.getObjectMapper().writeValue(sw, u);
		
		StringEntity reqEntity = new StringEntity(sw.toString());
		reqEntity.setContentType("application/json");
		req.setEntity(reqEntity);
		
		HttpResponse response = client.execute(req);
		ArrayList<CourseSequencePairs> result = new ArrayList<CourseSequencePairs>();
		ArrayList<String> newResult = new ArrayList<String>();
		
		
		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			
			HttpEntity entity = response.getEntity();
			newResult = JSON.getObjectMapper().readValue(entity.getContent(), ArrayList.class);
			
			for(int j = 0; j < result.size(); j++) {
				newResult.add(result.get(j).getCourseName());
				Log.println(1, "what", "made it in ok");
			}
			return newResult;
			
		} 
		
		return newResult;
	}
}
