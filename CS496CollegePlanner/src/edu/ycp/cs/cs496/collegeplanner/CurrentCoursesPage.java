package edu.ycp.cs.cs496.collegeplanner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
		
		
		
		
	}
	
	
	

}
