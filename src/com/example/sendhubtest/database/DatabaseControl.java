package com.example.sendhubtest.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.sendhubtest.api.response.BaseContactResponse;
import com.example.sendhubtest.api.response.Contact;

/**
 * This class contains helper methods for reading and writing into the db
 * 
 * @author vpenemetsa
 *
 */
public class DatabaseControl {
	static Context mContext;
		
	public DatabaseControl(Context context) {
		mContext = context;
	}
	
	public ArrayList<Contact> getContacts() {
		CustomSQLiteOpenHelper helper = new CustomSQLiteOpenHelper(mContext);
		SQLiteDatabase db = helper.getReadableDatabase();
		ArrayList<Contact> result = new ArrayList<Contact>();
		String query = "select *" + " from " + CustomSQLiteOpenHelper.TABLE_NAME;
		try {
			Cursor cursor = db.rawQuery(query, null);
			if (cursor.getCount() > 0) {
				cursor.moveToFirst();
				while (!cursor.isAfterLast()) {
					Contact contact = new Contact();
					contact.setNumber(cursor.getString(1));
					contact.setName(cursor.getString(2));
					contact.setUserId(cursor.getString(3));
					result.add(contact);
					cursor.moveToNext();
				}
			}
			cursor.close();
		} catch (SQLException e){
			Log.e("DB ERROR", e.toString());
			e.printStackTrace();
		}

		db.close();
		helper.close();
		return result;
	}
	
	public boolean getContact(String id) {
		CustomSQLiteOpenHelper helper = new CustomSQLiteOpenHelper(mContext);
		SQLiteDatabase db = helper.getReadableDatabase();
		
		String query = "select *" + " from " + CustomSQLiteOpenHelper.TABLE_NAME 
				+ " where " + CustomSQLiteOpenHelper.TABLE_USER_ID + "=" + id;
		Cursor c = db.rawQuery(query, null);
		
		if (c.getCount()>0) {
			c.close();
			db.close();
			helper.close();
			return true;
		} else {
			c.close();
			db.close();
			helper.close();
			return false;
		}
	}

	public void saveContacts(BaseContactResponse bcr) {
		CustomSQLiteOpenHelper helper = new CustomSQLiteOpenHelper(mContext);
		SQLiteDatabase db = helper.getWritableDatabase();
		
		List<Contact> contacts = bcr.getObjects();
		for (Contact contact : contacts) {
			ContentValues values = new ContentValues();
			values.put(CustomSQLiteOpenHelper.TABLE_PHONE_NUMBER, contact.getNumber());
			values.put(CustomSQLiteOpenHelper.TABLE_USER_ID, contact.getId());
			values.put(CustomSQLiteOpenHelper.TABLE_USERNAME, contact.getName());
			
			try {
				if (!getContact(contact.getId())) {
					db.insert(CustomSQLiteOpenHelper.TABLE_NAME, null, values);
				}
			} catch (SQLException e){
				Log.e("DB ERROR", e.toString());
				e.printStackTrace();
			}
		}
		
		db.close();
		helper.close();
	}
	
	

}
