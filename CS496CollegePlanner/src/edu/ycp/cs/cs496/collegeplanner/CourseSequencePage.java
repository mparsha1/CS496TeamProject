package edu.ycp.cs.cs496.collegeplanner;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;

import edu.ycp.cs.cs496.collegeplanner.models.CourseSequencePairs;
import edu.ycp.cs.cs496.collegeplanner.models.User;

import mobileControllers.GetCourseSequence;
import mobileControllers.GetMajor;
import mobileControllers.getAdvisorDepartments;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class CourseSequencePage extends Activity {
	
	String username;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent i = getIntent();
		username = i.getStringExtra("username");
		
		setDefaultView();
		//setContentView(R.layout.activity_main);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}	

	public void setDefaultView() {
		setContentView(R.layout.course_sequence);
		
		Button signOutButton = (Button) findViewById(R.id.signOutButtonCoursesPg);
		Button backButton = (Button) findViewById(R.id.backButtonCoursesPg);
		Button generate = (Button) findViewById(R.id.GenerageCoursesBtn);
		final EditText maxCredits = (EditText) findViewById(R.id.maxCredits);
		
		backButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent backToHome = new Intent(v.getContext(), HomePage.class);
				backToHome.putExtra("username", username);
				startActivity(backToHome);

			}	
		});

		signOutButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent signOut = new Intent(v.getContext(), MainActivity.class);
				startActivity(signOut);

			}	
		});
		
		generate.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Editable max = maxCredits.getText();
				int maxCredits = Integer.parseInt(max.toString());
				User u = new User();
				GetMajor gmController = new GetMajor();
				String major = "";
				try {
					major = gmController.getMajor(username);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
				
				u.setUsername(username);
				u.setMajor(major);
				u.setMaxCredits(maxCredits);
				GetCourseSequence controller = new GetCourseSequence();
				ArrayList<String> list = new ArrayList<String>();
				try {
					 list = controller.getSequence(u);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				
				ArrayList<String> courses = new ArrayList<String>();
				
				for(int i = 0; i < list.size(); i++) {
					String string = "Course: ";
					courses.add(string);
				}
				
				displaySequence(list);
				
				
			}	
		});
		
		
		
	}
	
	private void displaySequence(final ArrayList<String> sequence) {
		//Add Linear layout
		final LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);
		LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.FILL_PARENT);
		layout.setBackgroundColor(getResources().getColor(R.color.lightGreyBackground));

		//Add Back Button
		Button backButton = new Button(this);
		backButton.setText("Back");
		backButton.setLayoutParams(new LayoutParams(
				LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));

		backButton.setBackgroundColor(getResources().getColor(R.color.darkGreen));
		backButton.setTextColor(getResources().getColor(R.color.white));


		//add back click listener
		backButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try {			
					setDefaultView();
				}

				catch(Exception e) {
					e.printStackTrace();
				}   		
			}
		});

		layout.addView(backButton);

		String[] sequenceList = new String[sequence.size()];

		for(int i = 0; i < sequence.size(); i++) {
			sequenceList[i] = sequence.get(i);
		}

		ListAdapter la = new ArrayAdapter<String>(this, R.layout.list_view, sequenceList);
		final ListView lv = new ListView(this);
		lv.setAdapter(la); 
		layout.addView(lv);
		setContentView(layout,llp);

		
	}
}
