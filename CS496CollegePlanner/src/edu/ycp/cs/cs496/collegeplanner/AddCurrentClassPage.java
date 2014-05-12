package edu.ycp.cs.cs496.collegeplanner;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.http.client.ClientProtocolException;

import mobileControllers.CurrentSchedule;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddCurrentClassPage extends Activity{
	
	String username;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent i = getIntent();
		username = i.getStringExtra("username");
		
		setDefaultView();  
		
	} 

	private void setDefaultView() {
		setContentView(R.layout.add_current_class);
		
		Button backButton = (Button) findViewById(R.id.backButtonAddCurrentCoursePg);
		Button signOutButton = (Button) findViewById(R.id.signOutButtonAddCurrentPg);
		Button addButton = (Button) findViewById(R.id.SubmitCurrentBtn);
		
		backButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent backToCurrent = new Intent(v.getContext(), CurrentCoursesPage.class);
				backToCurrent.putExtra("username", username);
				startActivity(backToCurrent);

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

				EditText name = (EditText) findViewById(R.id.ClassNameTxt);
				EditText days = (EditText) findViewById(R.id.ClassDaysTxt);
				EditText time = (EditText) findViewById(R.id.ClassTimeText);
				EditText location = (EditText) findViewById(R.id.ClassLocationTxt);
				
				String formattedString = "Name: " + name.getText().toString() + "\nDays: " + days.getText().toString()
						+ "\nTime: " + time.getText().toString() + 
						"\nLocation: " + location.getText().toString();
				boolean verify = false;
				
				//Call controller to add the class! 
				CurrentSchedule controller = new CurrentSchedule();
				
				try {
					verify = controller.addClassToCurrentSchedule(username, formattedString, name.getText().toString());
					Toast.makeText(AddCurrentClassPage.this, "Added Class: " + name.getText().toString(), Toast.LENGTH_SHORT).show();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				
				//Go back to the old view!
				if(verify) {
					Intent goBack = new Intent(v.getContext(), CurrentCoursesPage.class);
					goBack.putExtra("username", username);
					startActivity(goBack);
				} 
				
				else {
					Toast.makeText(AddCurrentClassPage.this, "Error occured while adding " + name.getText().toString(), Toast.LENGTH_SHORT).show();
				}
				

			}	
		});
		
		
	}

}
