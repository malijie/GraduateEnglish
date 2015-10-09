package com.english.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

import cn.waps.AppConnect;

import com.english.phone.R;
import com.english.util.Util;

public class WelcomeActivity extends Activity{ 

	private ImageView imgBackgroud = null;
	private static final String APP_ID = "469378b902a681c98f5a095ddccf8223";
	
	@Override 
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
    	this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//全屏显示
    	super.setContentView(R.layout.welcome_layout);  
    	 
    	AppConnect.getInstance(APP_ID, "default", this); 
		AppConnect.getInstance(this);//初始化    
    	AppConnect.getInstance(this).initUninstallAd(this);//卸载展示广告
    	AppConnect.getInstance(this).setCrashReport(true); //开启错误报告
//String value=AppConnect.getInstance(this).getConfig("", "");
    	 
    	imgBackgroud = (ImageView) super.findViewById(R.id.welcome_image);
    	AlphaAnimation alpha = new AlphaAnimation(0.1f, 1.0f);
    	alpha.setDuration(2000);
    	imgBackgroud.setAnimation(alpha);
    	alpha.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				initData();
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			 
			@Override
			public void onAnimationEnd(Animation animation) {
				
				Intent itMain = new Intent(WelcomeActivity.this,MainActivity.class);
				//itMain.putExtra("networkavailable", networkAvailable);
				startActivity(itMain);
				WelcomeActivity.this.finish();
			}
		});
    	
	}
	private void initData() {
		Util.copyDB2Phone(WelcomeActivity.this);
	}
}
