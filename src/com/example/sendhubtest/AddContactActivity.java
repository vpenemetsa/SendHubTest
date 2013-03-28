package com.example.sendhubtest;

import org.springframework.http.HttpStatus;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.example.sendhubtest.api.Constants;
import com.example.sendhubtest.api.SendHubApi;

/**
 * Activity to add a new contact
 * 
 * @author vpenemetsa
 *
 */
public class AddContactActivity extends Activity {

	EditText et1, et2;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_contact);
		
		et1 = (EditText) findViewById(R.id.name);
		et2 = (EditText) findViewById(R.id.phonenumber);
		
		Button addButton = (Button) findViewById(R.id.button_add);
		addButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				boolean error = false;
				if (et1.getText().toString().trim().length() == 0) {
					et1.setError("Enter the name of the contact");
					error = true;
				}
				
				if (et2.getText().toString().trim().length() != 10) {
					et2.setError("Please enter a valid 10 digit phone number");
					error = true;
				}
				
				if (!error) {
					new AddContactTask().execute();
				}
			}
		});
	}
	
	private class AddContactTask extends AsyncTask<Integer, Void, Void>{
		@Override
		protected Void doInBackground(Integer... params) {
			SendHubApi sha = new SendHubApi(getApplicationContext());
			HttpStatus result = sha.postNewUser(et1.getText().toString(), et2.getText().toString());
			
			if (result.equals(HttpStatus.CREATED)) {
				Intent i = new Intent(getApplicationContext(), ContactsActivity.class);
				i.putExtra(Constants.RESULT_STATE, "You have succesfully added " + et1.getText().toString());
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
			} else {
				et2.post(new Runnable() {
					@Override
					public void run() {
						et2.setError("Invalid phone number");
					}
				});
			}
			
			return null;
		}
		
	}
}
