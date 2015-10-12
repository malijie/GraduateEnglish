package com.english.config;

import android.os.Environment;

import java.io.File;

/**
 * 相关常量
 * @author malijie
 *
 */
public class Const {
	//单词压缩文件名称
	public static final String UNZIP_WORDS_FILE_NAME = "words.zip";


	public static final int BOTTOM_SELECT_WORD = 0;
	public static final int BOTTOM_SELECT_READING = 1;
	public static final int BOTTOM_SELECT_WRITTING =2;
	public static final int BOTTOM_SELECT_SETTING = 3;
	
	/**	 阅读理解起始年份	 */
	public static final int READING_BEGIN_DATE = 1994;
	
	/**	 阅读理解结束年份	 */
	public static final int READING_END_DATE = 2014;

	//----------------对话框相关常量---------------------
	/**	退出对话框标题	 */
	public static final String DIALOG_EXIT_TITLE = "退出？";
	/**	退出对话框内容	 */
	public static final String DIALOG_EXIT_MSG = "确定要退出吗？";
	/**	支付对话框标题	 */
	public static final String DIALOG_PAY_TITLE = "支付";
	/**	支付对话框内容	 */
	public static final String DIALOG_PAY_MSG = "支付1元便可查看最新试题";



}
