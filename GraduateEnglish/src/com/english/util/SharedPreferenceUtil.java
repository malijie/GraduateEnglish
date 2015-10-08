package com.english.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferenceUtil {
	private SharedPreferences sp = null;
	private Editor e = null;
	public static final String SP_FILE_NAME = "english_lesson_data";


	public SharedPreferenceUtil(Context context){
		sp = context.getSharedPreferences("english_setting", Context.MODE_PRIVATE);
	}
	
	public void setFontSize(String key, int value){
		e = sp.edit();
		e.putInt(key, value);
		e.commit();
	}
	
	public int getFontSize(String key){
		if(sp != null){
			return sp.getInt(key, 17);
		}
		return 17;
	}

	/**
	 * 保存当前课程进度
	 * @param lesson 课程
	 * @param progress 当前单词进度
	 */
	public static void saveLessonProgress(Context context, int lesson, int progress){
		SharedPreferences sp = context.getSharedPreferences(SP_FILE_NAME,Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.putInt(lesson + "",progress);
		editor.commit();
	}

	/**
	 * 读取当前课程进度
	 * @param context
	 * @param lesson
	 * @return
	 */
	public static int loadLessonProgress(Context context,int lesson){
		SharedPreferences sp = context.getSharedPreferences(SP_FILE_NAME,Context.MODE_PRIVATE);
		return sp.getInt(lesson + "",0);
	}
}
