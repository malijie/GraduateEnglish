package com.english.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
 
public class EnglishDatabaseHelper extends SQLiteOpenHelper{
	
	
	public EnglishDatabaseHelper(Context context) {
		super(context, DatabaseProfile.DATABASENAME, null, DatabaseProfile.DATABASEVERSION);
	} 

	@Override
	public void onCreate(SQLiteDatabase db) {
		String createSQL = DatabaseProfile.getSQLCreateDB();
		db.execSQL(createSQL);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String updateSQL = DatabaseProfile.getSQLUpdateDB();
		db.execSQL(updateSQL);
		this.onCreate(db);
	}
	
}
