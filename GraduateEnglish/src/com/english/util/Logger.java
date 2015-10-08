package com.english.util;

import android.util.Log;
import com.english.config.Config;

/**
 * Created by vic_ma on 15/10/8.
 * 日志类
 */
public class Logger {
    public static void d(String tag, String msg){
        if(Config.DEBUG_MODE){
            Log.d(tag,msg);
        }
    }

    public static void i(String tag, String msg){
        if(Config.DEBUG_MODE){
            Log.i(tag,msg);
        }
    }

    public static void e(String tag, String msg){
        if(Config.DEBUG_MODE){
            Log.e(tag,msg);
        }
    }

    public static void v(String tag, String msg){
        if(Config.DEBUG_MODE){
            Log.v(tag,msg);
        }
    }

    public static void w(String tag, String msg){
        if(Config.DEBUG_MODE){
            Log.w(tag,msg);
        }
    }

}
