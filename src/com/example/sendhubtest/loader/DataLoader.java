package com.example.sendhubtest.loader;

import java.util.ArrayList;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.Bundle;

import com.example.sendhubtest.api.response.Contact;
import com.example.sendhubtest.database.DatabaseControl;

/**
 * Async loader for the list view. Loads data from the db
 * 
 * @author vpenemetsa
 *
 */
public class DataLoader extends AsyncTaskLoader<ArrayList<Contact>> {

	Context mContext;
	ArrayList<Contact> mData;
	DatabaseControl dbControl;
	Bundle mBundle;
	
	public DataLoader(Context context, DatabaseControl dc, Bundle bundle) {
		super(context);
		mBundle = bundle;
		dbControl = dc;
	}

	@Override
	public ArrayList<Contact> loadInBackground() {
		return dbControl.getContacts();
	}
	
	@Override
	public void deliverResult(ArrayList<Contact> data) {
		mData = data;

		if (isStarted()) {
			super.deliverResult(data);
		}
	}
	
	@Override
	protected void onStartLoading() {
		if (mData != null) {
			deliverResult(mData);
		}
		
		if (takeContentChanged() || mData == null) {
			forceLoad();
		}
	}
	
	@Override
	protected void onStopLoading() {
		cancelLoad();
	}
	
	@Override
	public void onCanceled(ArrayList<Contact> data) {
		super.onCanceled(data);
	}
	
	@Override
	protected void onReset() {
		super.onReset();
	}
}
