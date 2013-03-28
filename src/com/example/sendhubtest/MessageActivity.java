package com.example.sendhubtest;

import java.util.ArrayList;

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
 * This activity handles messaging
 * 
 * @author vpenemetsa
 *
 */
public class MessageActivity extends Activity {

	EditText et;
	ArrayList<Integer> contacts = new ArrayList<Integer>();
	String message;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.message);
		String contactId = getIntent().getStringExtra(Constants.CONTACT_ID);
		contacts.add(Integer.parseInt(contactId));
		
		et = (EditText) findViewById(R.id.message);
		Button sendButton = (Button) findViewById(R.id.button_send);
		
		sendButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				message = et.getText().toString().trim();
				if (message.length()>0) {
					new GetContactsTask().execute();
				} else {
					et.setError("Please enter a message");
				}
			}
		});
	}
	
	private class GetContactsTask extends AsyncTask<Integer, Void, Void> {
		@Override
		protected Void doInBackground(Integer... params) {
			SendHubApi sha = new SendHubApi(getApplicationContext());
			HttpStatus result = sha.postMessage(contacts, message);
			if (result.equals(HttpStatus.CREATED)) {
				Intent i = new Intent(getApplicationContext(), ContactsActivity.class);
				i.putExtra(Constants.RESULT_STATE, "Succesfully sent message!!");
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
			} else {
				et.post(new Runnable() {
					@Override
					public void run() {
						et.setError("There was an error. Try again");
					}
				});
			}
			
			return null;
		}
	}
}
