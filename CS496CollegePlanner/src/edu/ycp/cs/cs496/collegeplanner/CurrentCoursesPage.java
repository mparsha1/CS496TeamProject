package edu.ycp.cs.cs496.collegeplanner;

import java.util.ArrayList;


import edu.ycp.cs.cs496.collegeplanner.models.Advisor;

import mobileControllers.CurrentSchedule;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class CurrentCoursesPage extends Activity{
	
	String username;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent i = getIntent();
		username = i.getStringExtra("username");
		
		setDefaultView();
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

				Intent goToAdd = new Intent(v.getContext(), AddCurrentClassPage.class);
				goToAdd.putExtra("username", username);					
				startActivity(goToAdd);

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
		
		lv.setOnItemClickListener( new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, final View v, int index,
					long id) {
				
				final String selected = lv.getItemAtPosition(index).toString();
				AlertDialog.Builder builder =  new AlertDialog.Builder(CurrentCoursesPage.this);
				builder.setMessage("Remove this item from current Schedule?");
				builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {

						boolean verify = false;
						CurrentSchedule controller = new CurrentSchedule();
						Advisor a = new Advisor();
						a.setName(selected);
						try {
							
							verify = controller.removeClassFromCurrentSchedule(username, selected);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 

						if(verify) {
							Toast.makeText(CurrentCoursesPage.this, "This item has been removed from your current schedule", Toast.LENGTH_SHORT).show();
							ArrayList<String> courses = new ArrayList<String>();
							try {
								courses = controller.getCurrentSchedule(username);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} 
							displayCourses(courses);
						} 
						else {
							Toast.makeText(CurrentCoursesPage.this, "Error removing item from your current schedule", Toast.LENGTH_SHORT).show();
						}
					}
				});
				builder.setNegativeButton("Cancel", null);
				builder.show();
			}
			
		});

		
	}
	

}
