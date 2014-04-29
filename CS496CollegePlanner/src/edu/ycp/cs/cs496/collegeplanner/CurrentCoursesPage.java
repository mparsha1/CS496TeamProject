package edu.ycp.cs.cs496.collegeplanner;

import java.util.ArrayList;

import mobileControllers.CurrentSchedule;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

public class CurrentCoursesPage extends Activity{
	
	String username;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent i = getIntent();
		username = i.getStringExtra("username");
		
		setDefaultView();
		//setContentView(R.layout.activity_main);
	}

	public void setDefaultView() {
		
		setContentView(R.layout.current_class_schedule);
		
		Button addButton = (Button) findViewById(R.id.addClassToSchedule);
		Button backButton = (Button) findViewById(R.id.backButtonSchedulePg);
		Button signOutButton = (Button) findViewById(R.id.signOutButtonSchedulePg);
		Button viewCoursesButton = (Button) findViewById(R.id.ViewCurrentCoursesBtn);
		
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
		
		addButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				//Go to add class view

			}	
		});
		
		viewCoursesButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				CurrentSchedule controller = new CurrentSchedule();
				
				ArrayList<String> result = new ArrayList<String>();
				
				try {
					result = controller.getCurrentSchedule(username);
					displayCourses(result);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				
				

			}	
		});
		
		
	}
	
	private void displayCourses(final ArrayList<String> courses) {
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

		String[] courseList = new String[courses.size()];

		for(int i = 0; i < courses.size(); i++) {
			courseList[i] = courses.get(i);
		}

		ListAdapter la = new ArrayAdapter<String>(this, R.layout.list_view, courseList);
		final ListView lv = new ListView(this);
		lv.setAdapter(la); 
		layout.addView(lv);
		setContentView(layout,llp);

		
	}
	

}
