package com.example.sendhubtest.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.sendhubtest.MessageActivity;
import com.example.sendhubtest.api.Constants;
import com.example.sendhubtest.api.response.Contact;

/**
 * Adapter class for the home list view
 * 
 * @author vpenemetsa
 *
 */
public class ContactAdapter extends ArrayAdapter<Contact> {

	Context mContext;
	LayoutInflater mLayoutInflater;
	ArrayList<Contact> checked = new ArrayList<Contact>();
	
	public ContactAdapter(Context context, int textViewResourceId, LayoutInflater layoutInflater) {
		super(context, textViewResourceId);
		mContext = context;
		mLayoutInflater = layoutInflater;
	}
	
	public void setData(List<Contact> data) {
		clear();
		if (data != null) {
			addAll(data);
		}
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final Contact item = getItem(position);
		if (item != null) {
			convertView = mLayoutInflater.inflate(android.R.layout.simple_list_item_1, parent, false);
			TextView tv = (TextView) convertView.findViewById(android.R.id.text1);
			tv.setText(item.getName());
			
			convertView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent i = new Intent(mContext, MessageActivity.class);
					i.putExtra(Constants.CONTACT_ID, item.getUserId());
					i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					mContext.startActivity(i);
				}
			});
		}
		
		return convertView;
	}
}
