package com.english.config;

import android.os.Environment;

import java.io.File;

/**
 * 配置类
 * Created by vic_ma on 15/10/8.
 */
public class Config {

    //单词解压路径
//    public static final String UNZIP_WORDS_FILE_PATH = Environment.getExternalStorageDirectory()
//                                                    .getAbsolutePath() + File.separator + "GraduateEnglish" + File.separator;

    public static final String UNZIP_WORDS_FILE_PATH = Environment.getExternalStorageDirectory()
                                                        .getAbsolutePath() + File.separator + "GraduateEnglish"
                                                        + File.separator + "words" + File.separator;

    //正式线，测试线配置
    public static boolean DEBUG_MODE = true;

}
