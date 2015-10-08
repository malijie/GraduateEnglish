package com.english.util;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.english.activity.WelcomeActivity;
import com.english.phone.R;

/**
 * 工具类,完成相关文件的操作 
 * @author malijie
 *
 */
public class FileUtil {
	private final static String DATABASEPATH = "/data/data/com.english.phone/databases/";
	private final static String DATABASENAME = "english.db";

	 
	public static boolean isDBExist(){
		File dbFile = new File(DATABASEPATH + DATABASENAME);
		if(!dbFile.exists()){
			dbFile.getParentFile().mkdirs();
		}
		return true;
	}
	
	/**
	 * 将assets里的数据库文件拷贝到系统中
	 */
	public static void copyDB2Phone(Context context){
		String dbFileName = DATABASEPATH + DATABASENAME;
    	File dbFile = new File(dbFileName);
    	InputStream is = null;
    	FileOutputStream os = null;
  
    	if(!dbFile.exists()){//判断文件夹是否存在，不存在就新建一个
    	Toast.makeText(context, "首次启动，数据加载中...", Toast.LENGTH_LONG).show();
    		dbFile.getParentFile().mkdirs();
	    	try{
	    		os = new FileOutputStream(dbFileName);//得到数据库文件的写入流
	    		is = context.getResources().getAssets().open(DATABASENAME);//得到数据库文件的数据流
	    		  byte[] buffer = new byte[1024];
	    	        int count = 0; 
	    	        	while((count=is.read(buffer))>0){
	    	        		os.write(buffer, 0, count);
	    	        		os.flush(); 
	    	        	}
	    	}catch(FileNotFoundException e){
	    		e.printStackTrace();
	    	} catch (IOException e) {
				e.printStackTrace();
			}finally{   
	    		try { 
					is.close();
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				} 
			}
    	}
   }


}
