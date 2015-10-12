package com.english.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferenceUtil {
	private SharedPreferences sp = null;
	private Editor e = null;
	//SharePreference名称
	private static final String PREF_DATA_NAME = "english_lesson_data";
	//解压状态sp
	private static final String PREF_UNZIP_NAME = "english_unzip_status";


	//sp课程名称key
	private static final String PREF_LESSON_KEY = "lesson";
	//sp阅读支付结果key
	private static final String PREF_PAY_READING_RESULT_KEY = "pay_reading_result";
	//sp单词解压记录
	private static final String PREF_WORDS_UNZIP_STATUS_KEY = "unzip_words_status";


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
	 * @param lessonNum 课程
	 * @param progress 当前单词进度
	 */
	public static void saveLessonProgress(Context context, int lessonNum, int progress){
		context.getSharedPreferences(PREF_DATA_NAME,Context.MODE_PRIVATE).edit().putInt(PREF_LESSON_KEY + lessonNum,progress).commit();
	}

	/**
	 * 读取当前课程进度
	 * @param context
	 * @param lessonNum
	 * @return
	 */
	public static int loadLessonProgress(Context context,int lessonNum){
		return context.getSharedPreferences(PREF_DATA_NAME,Context.MODE_PRIVATE).getInt(PREF_LESSON_KEY + lessonNum,0);
	}

	/**
	 * 保存阅读支付结果
	 * @param context
	 * @param result 支付结果
	 */
	public static void saveReadingPayResult(Context context,boolean result){
		context.getSharedPreferences(PREF_DATA_NAME,Context.MODE_PRIVATE).edit().putBoolean(PREF_PAY_READING_RESULT_KEY,result).commit();
	}

	/**
	 * 获取阅读支付结果
	 * @param context
	 * @return
	 */
	public static boolean getReadingPayResult(Context context){
		return context.getSharedPreferences(PREF_DATA_NAME,Context.MODE_PRIVATE).getBoolean(PREF_PAY_READING_RESULT_KEY,false);
	}

	/**
	 * 保存解压单词记录
	 * @param context
	 * @param result
	 */
	public static void saveUnzipWordsStatus(Context context,boolean result){
		context.getSharedPreferences(PREF_UNZIP_NAME,Context.MODE_PRIVATE).edit().putBoolean(PREF_WORDS_UNZIP_STATUS_KEY,result).commit();
	}

	/**
	 * 获取单词是否已经解压状态
	 * @param context
	 * @return
	 */
	public static boolean getWordsUnzipStatus(Context context){
		return context.getSharedPreferences(PREF_UNZIP_NAME,Context.MODE_PRIVATE).getBoolean(PREF_WORDS_UNZIP_STATUS_KEY,false);
	}

}
