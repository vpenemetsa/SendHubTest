package com.example.sendhubtest.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Creates and maintains db
 * 
 * @author vpenemetsa
 *
 */
public class CustomSQLiteOpenHelper extends SQLiteOpenHelper {
	
	private final static String DB_NAME = "sendhubdb";
	private final static int DB_VERSION = 7;

	public final static String TABLE_NAME = "contacts";
	public final static String TABLE_ROW_ID = "_id";
	public final static String TABLE_PHONE_NUMBER = "phonenumber";
	public final static String TABLE_USERNAME = "name";
	public final static String TABLE_USER_ID = "userid";
	
	public CustomSQLiteOpenHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase pum) {
		String newTableQueryString1 = "create table " + TABLE_NAME + " ("
				+ TABLE_ROW_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
				+ TABLE_PHONE_NUMBER + " TEXT, " + TABLE_USERNAME
				+ " TEXT, " + TABLE_USER_ID + " TEXT" + ");";
		Log.d("Creating Database", newTableQueryString1);
		pum.execSQL(newTableQueryString1);
		Log.d("Database Created", "DB created and ready for use");
	}

	@Override
	public void onUpgrade(SQLiteDatabase pum, int oldVersion, int newVersion) {
		pum.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(pum);
	}

}
