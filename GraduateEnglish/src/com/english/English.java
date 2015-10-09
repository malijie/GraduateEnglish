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
        PayConnect.getInstance("469378b902a681c98f5a095ddccf8223", "469378b902a681c98f5a095ddccf8223", getApplicationContext());

    }
}
