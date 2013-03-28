package com.example.sendhubtest;


import java.util.ArrayList;

import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Intent;
import android.content.Loader;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sendhubtest.adapter.ContactAdapter;
import com.example.sendhubtest.api.Constants;
import com.example.sendhubtest.api.SendHubApi;
import com.example.sendhubtest.api.response.Contact;
import com.example.sendhubtest.database.DatabaseControl;
import com.example.sendhubtest.loader.DataLoader;

/**
 * The main activity which houses a list view of all the contacts
 * 
 * @author vpenemetsa
 *
 */
public class ContactsActivity extends Activity implements LoaderCallbacks<ArrayList<Contact>> {

	ListView list;
	ContactAdapter contactAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contacts);

		if (getIntent().hasExtra(Constants.RESULT_STATE)) {
			Toast.makeText(getApplicationContext(), getIntent().getStringExtra(Constants.RESULT_STATE), Toast.LENGTH_SHORT).show();
		}
		
		contactAdapter = new ContactAdapter(getApplicationContext(), 0, getLayoutInflater());
		list = (ListView) findViewById(R.id.contact_list);
		list.setAdapter(contactAdapter);

		Button createButton = (Button) findViewById(R.id.button_create);
		createButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(), AddContactActivity.class);
				startActivity(i);
			}
		});
 		
		new GetContactsTask().execute();
		getLoaderManager().initLoader(1, new Bundle(), this);
	}

	@Override
	public Loader<ArrayList<Contact>> onCreateLoader(int id, Bundle args) {
		return new DataLoader(getApplicationContext(), new DatabaseControl(getApplicationContext()), args);
	}

	@Override
	public void onLoadFinished(Loader<ArrayList<Contact>> loader, ArrayList<Contact> data) {
		contactAdapter.setData(data);
	}

	@Override
	public void onLoaderReset(Loader<ArrayList<Contact>> loader) {
		contactAdapter.setData(null);
	}
	
	public void restartLoaders() {
		getLoaderManager().restartLoader(1, new Bundle(), this);
	}
	
	private class GetContactsTask extends AsyncTask<Integer, Void, Void> {
		@Override
		protected Void doInBackground(Integer... params) {
			SendHubApi sha = new SendHubApi(getApplicationContext());
			sha.getContacts(Constants.CONTACTS_API);
			
			restartLoaders();
			return null;
		}
	}

}
