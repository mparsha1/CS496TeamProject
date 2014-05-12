package edu.ycp.cs.cs496.collegeplanner;

import java.io.IOException;
import java.net.URISyntaxException;

import mobileControllers.ChangeUsername;
import mobileControllers.GetAdvisorForUser;
import mobileControllers.GetMajor;
import mobileControllers.GetNameOfUser;
import mobileControllers.GetPassword;
import mobileControllers.SetPassword;

import org.apache.http.client.ClientProtocolException;

import edu.ycp.cs.cs496.collegeplanner.models.Advisor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AccountPage extends Activity{
	String username;	

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
	private void displayChangePasswordBox() {
		//Add Linear layout
		final LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);
		LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.FILL_PARENT);
		layout.setBackgroundColor(getResources().getColor(R.color.lightGreyBackground));
		
		//Add edit text to change username
		final EditText oldPasswordBox = new EditText(this);
		oldPasswordBox.setMinHeight(1);
		oldPasswordBox.setMinWidth(250);
		oldPasswordBox.setLayoutParams(new LayoutParams(
				LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		
		final EditText newPasswordBox = new EditText(this);
		newPasswordBox.setMinHeight(1);
		newPasswordBox.setMinWidth(250);
		newPasswordBox.setLayoutParams(new LayoutParams(
				LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		
		final EditText confirmNewPasswordBox = new EditText(this);
		confirmNewPasswordBox.setMinHeight(1);
		confirmNewPasswordBox.setMinWidth(250);
		confirmNewPasswordBox.setLayoutParams(new LayoutParams(
				LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		
		//Add confirm button to confirm change username
		Button confirmButton = new Button(this);
		confirmButton.setText("Confirm");
		confirmButton.setLayoutParams(new LayoutParams(
				LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));	
		
		TextView oldText = new TextView(this);
		oldText.setText("    --- Enter your old Password---");
		oldText.setTextColor(getResources().getColor(R.color.darkGreen));
		oldText.setGravity(Gravity.CENTER_HORIZONTAL);
		
		TextView newText = new TextView(this);
		newText.setText("    --- Enter your new Password---");
		newText.setTextColor(getResources().getColor(R.color.darkGreen));
		newText.setGravity(Gravity.CENTER_HORIZONTAL);
		
		TextView confirmText = new TextView(this);
		confirmText.setText("    --- Confirm your new Password---");
		confirmText.setTextColor(getResources().getColor(R.color.darkGreen));
		confirmText.setGravity(Gravity.CENTER_HORIZONTAL);

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
		
		confirmButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			// when password button is clicked ..
			public void onClick(View v) {
				// get old password 
				String oldPassword = oldPasswordBox.getText().toString();
				GetPassword gp = new GetPassword();
				String currentPassword;
				
				try {
					currentPassword = gp.getPassword(username);
					if(!oldPassword.equals(currentPassword)){
						Toast.makeText(AccountPage.this, "Old passwords do not match", Toast.LENGTH_SHORT).show();		
						return;
					}	
				} catch (Exception e1) {					
					e1.printStackTrace();
				} 							
				
				String newPassword = newPasswordBox.getText().toString();
				String confirmPassword = confirmNewPasswordBox.getText().toString();
				
				if(!newPassword.equals(confirmPassword)) {
					Toast.makeText(AccountPage.this, "New passwords do not match", Toast.LENGTH_SHORT).show();		
					return;
				}
				SetPassword sp = new SetPassword();
				try {
					boolean result = sp.setPassword(username, newPassword);
					if(result == true) {						
						Toast.makeText(AccountPage.this, "Success", Toast.LENGTH_SHORT).show();						
					}
					else {
						Toast.makeText(AccountPage.this, "Failed to change", Toast.LENGTH_SHORT).show();
					}
					setDefaultView();
				} catch (Exception e) {					
					e.printStackTrace();
				} 
				
			}
		});

		layout.addView(backButton);
		layout.addView(oldText);
		layout.addView(oldPasswordBox);
		layout.addView(newText);
		layout.addView(newPasswordBox);
		layout.addView(confirmText);
		layout.addView(confirmNewPasswordBox);
		layout.addView(confirmButton);
		
		setContentView(layout,llp);
	}

	private void displayChangeUsernameBox() {
		//Add Linear layout
		final LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);
		LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.FILL_PARENT);
		layout.setBackgroundColor(getResources().getColor(R.color.lightGreyBackground));
		
		//Add edit text to change username
		final EditText usernameBox = new EditText(this);
		usernameBox.setMinHeight(1);
		usernameBox.setMinWidth(250);
		usernameBox.setLayoutParams(new LayoutParams(
				LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		
		//Add confirm button to confirm change username
		Button confirmButton = new Button(this);
		confirmButton.setText("Confirm");
		confirmButton.setLayoutParams(new LayoutParams(
				LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));	
		
		TextView text = new TextView(this);
		text.setText("    --- Enter a new Username ---");
		text.setTextColor(getResources().getColor(R.color.darkGreen));
		text.setGravity(Gravity.CENTER_HORIZONTAL);

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
		
		confirmButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String newUsername = usernameBox.getText().toString();
				ChangeUsername cu = new ChangeUsername();
				try {
					boolean result = cu.changeUsername(username, newUsername);
					if(result == true) {
						username = newUsername;
						Toast.makeText(AccountPage.this, "Success", Toast.LENGTH_SHORT).show();						
					}
					else {
						Toast.makeText(AccountPage.this, "Failed to change", Toast.LENGTH_SHORT).show();
					}
					setDefaultView();
				} catch (Exception e) {					
					e.printStackTrace();
				} 
				
			}
		});

		layout.addView(backButton);
		layout.addView(text);
		layout.addView(usernameBox);
		layout.addView(confirmButton);
		
		setContentView(layout,llp);
	}

	private void setDefaultView() throws ClientProtocolException, URISyntaxException, IOException {
		setContentView(R.layout.account_view);

		TextView nameText = (TextView) findViewById(R.id.NameTextView_AcntPg);
		TextView majorText = (TextView) findViewById(R.id.MajorTextView_2);
		TextView usernameText = (TextView) findViewById(R.id.UsernameTextView_2);
		TextView advisorText = (TextView) findViewById(R.id.advisorTextView);
		TextView advisorEmail = (TextView) findViewById(R.id.AdvisorEmail);
		TextView advisorPhone = (TextView) findViewById(R.id.AdvisorPhone);
		TextView advisorLocation = (TextView) findViewById(R.id.AdvisorLocation);
		
		Button signOutButton = (Button) findViewById(R.id.signOutButtonAccountPg);
		Button backButton = (Button) findViewById(R.id.backButtonAccountPg);

		Button editUsernameButton = (Button) findViewById(R.id.editUsernameAcntPgBtn);
		Button editPasswordButton = (Button) findViewById(R.id.editPasswordAcntPgBtn);		

		GetNameOfUser gnou = new GetNameOfUser();
		String nameOfUser =  gnou.getNameOfUser(username);
		nameText.setText("Name: " + nameOfUser);
		
		usernameText.setText("Username: " + username);

		GetMajor gm = new GetMajor();
		majorText.setText("Major: " + gm.getMajor(username));

		GetAdvisorForUser gafu = new GetAdvisorForUser();
		Advisor advisor = gafu.getAdvisorForUser(username);
		advisorText.setText("Advisor: " + advisor.getName());
		advisorEmail.setText("Email: " + advisor.getEmail());
		advisorPhone.setText("Phone: " + advisor.getPhone());
		advisorLocation.setText("Office Locaton: " + advisor.getLocation());
		
		editUsernameButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				displayChangeUsernameBox();
			}
		});

		editPasswordButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				displayChangePasswordBox();
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
	}
}
