package com.english;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;

import com.english.pay.PayManager;
import com.english.util.Logger;
import com.wanpu.pay.PayConnect;

/**
 * Created by vic_ma on 15/10/9.
 */
public class English extends Application{
    public static Context mContext = null;

    @Override
    public void onCreate() {
        super.onCreate();
        //获取上下文
        mContext = getApplicationContext();
        //初始化支付接口
        PayManager.init(getApplicationContext());
    }
}
