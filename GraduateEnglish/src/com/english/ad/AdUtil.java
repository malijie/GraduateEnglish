package com.english.ad;

import android.content.Context;
import android.widget.LinearLayout;
import cn.waps.AppConnect;

/**
 * 广告工具类
 * @author malijie
 *
 */ 
public class AdUtil {
	
	
//	/**
//	 * 显示迷你广告
//	 * @param context
//	 * @param adLayout
//	 */
//	public static void showMiniAd(Context context, LinearLayout adLayout, int time){
////		AppConnect.getInstance(APP_ID, "waps", context);
//		AppConnect.getInstance(context).initAdInfo();
//		AppConnect.getInstance(context).showMiniAd(context, adLayout, time);
//	}
	 
//	public static void showBannerAd(Context context, LinearLayout adLayout){
////		AppConnect.getInstance(APP_ID, "waps", context);
//		AppConnect.getInstance(context).initAdInfo();
//		AppConnect.getInstance(context).showBannerAd(context, adLayout);
//	}

	public static void closeAd(Context context){
		AppConnect.getInstance(context).close();
	}
}

