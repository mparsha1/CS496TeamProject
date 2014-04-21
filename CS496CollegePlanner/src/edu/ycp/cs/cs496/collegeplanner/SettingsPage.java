package edu.ycp.cs.cs496.collegeplanner;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;


import mobileControllers.CoursesController;
import mobileControllers.GetMajor;
import mobileControllers.GetMajors;
import mobileControllers.SetMajor;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class SettingsPage extends Activity {
	
	String username;
	String major;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent i = getIntent();
		username = i.getStringExtra("username");
		
		try {
			setDefaultView();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private void setDefaultView() throws ClientProtocolException, URISyntaxException, IOException {
		setContentView(R.layout.settings_view);
		
		TextView majorText = (TextView) findViewById(R.id.MajorTextView);
		TextView usernameText = (TextView) findViewById(R.id.UsernameTextView);
		
		Button signOutButton = (Button) findViewById(R.id.signOutButtonSettingsPg);
		Button backButton = (Button) findViewById(R.id.backButtonSettingsPg);
		Button changeMajorButton = (Button) findViewById(R.id.ChangeMajorBtn);
		Button manageClassesButton = (Button) findViewById(R.id.ManageClassesBtn);
		
		manageClassesButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				
				CoursesController controller = new CoursesController();
				ArrayList<String> categories = new ArrayList<String>();
				try {
					categories = controller.getCategories();
				} catch (Exception e) {
					
					e.printStackTrace();
				} 
				
				displayCategoriesView(categories);
				
			}	
		});
		
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
		
		changeMajorButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				
				//Go to major selection page
				GetMajors controller = new GetMajors();
				ArrayList<String> majorsList = new ArrayList<String>();
				try {
					majorsList = controller.getMajors();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 

				displayMajorsView(majorsList);
				
			}	
		});
		
	
		GetMajor controller = new GetMajor();
		major = controller.getMajor(username);
		
		majorText.setText("Major: " + major);
		usernameText.setText("Username: " + username);

	
		
		
		
	}
	
	public void displayCategoriesView(ArrayList<String> categories) {
		
		//Add Linear layout
		LinearLayout layout = new LinearLayout(this);
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
		
		String[] categoryList = new String[categories.size()];
		
		for(int i = 0; i < categories.size(); i++) {
			categoryList[i] = categories.get(i);
		}
		
		ListAdapter la = new ArrayAdapter<String>(this, R.layout.list_view, categoryList);
		final ListView lv = new ListView(this);
		lv.setAdapter(la);  
		layout.addView(lv);
		setContentView(layout,llp);
		
		lv.setOnItemClickListener( new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int index,
					long id) {
				
				String selected = lv.getItemAtPosition(index).toString();
				
				
			}
			
		});
				
				
		
	}
	
	public void displayMajorsView(ArrayList<String> majors) {
		
		int backgroundColor = R.color.lightGreyBackground;
		int darkGreen = R.color.darkGreen;
		int white = R.color.white;
		
		//Add Linear layout
		LinearLayout layout = new LinearLayout(this);
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
		
		String[] majorsList = new String[majors.size()];
		
		for(int i = 0; i < majors.size(); i++) {
			majorsList[i] = majors.get(i);
		}
		
		ListAdapter la = new ArrayAdapter<String>(this, R.layout.list_view, majorsList);
		final ListView lv = new ListView(this);
		lv.setAdapter(la);  
		layout.addView(lv);
		setContentView(layout,llp);
		
		
		lv.setOnItemClickListener( new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int index,
					long id) {
				
				
				String selected = lv.getItemAtPosition(index).toString();
				SetMajor controller = new SetMajor();
				boolean verify = false;
				try {
					verify = controller.setMajor(username, selected);
				} catch (Exception e) {
					e.printStackTrace();
				} 
				
				try {
					setDefaultView();
				} catch (Exception e) {
					e.printStackTrace();
				} 
				
			}
			
		});

	}
	
	
	
}
