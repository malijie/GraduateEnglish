package com.english.database;

public class DatabaseProfile {
	public static final String DATABASENAME = "english.db";
	public static final int DATABASEVERSION = 1;
	public static final String VOC_TABLENAME = "vocabulary"; //单词表
	public static final String DIC_TABLENAME = "dictionary";

	
	public static String getSQLCreateDB(){
		String sql = "CREATE TABLE " + VOC_TABLENAME + "(" + 
					 "id	INTEGER	PRIMARY KEY," + 
					 "symbols VARCHAR(50)," +
					 "word VARCHAR(50) NOT NULL," + 
					 "content VARCHAR(100) NOT NULL," + 
					 "example VARCHAR(400)," + 
					 "audio VARCHAR(100))"; 
		return sql;
	}
	
	public static String getSQLUpdateDB(){
		String sql = "DROP TABLE IF EXISTS" + VOC_TABLENAME;
		return sql; 
	}
	   
}
