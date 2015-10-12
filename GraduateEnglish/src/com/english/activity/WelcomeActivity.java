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

import com.english.config.Config;
import com.english.config.Const;
import com.english.phone.R;
import com.english.util.Logger;
import com.english.util.Util;

import java.io.IOException;

public class WelcomeActivity extends Activity {
	private static final String TAG = WelcomeActivity.class.getSimpleName();

	private ImageView imgBackgroud = null;
	private static final String APP_ID = "469378b902a681c98f5a095ddccf8223";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//ȫ����ʾ
		super.setContentView(R.layout.welcome_layout);

		AppConnect.getInstance(APP_ID, "default", this);
		AppConnect.getInstance(this);//��ʼ��    
		AppConnect.getInstance(this).initUninstallAd(this);//ж��չʾ���
		AppConnect.getInstance(this).setCrashReport(true); //�������󱨸�
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

				Intent itMain = new Intent(WelcomeActivity.this, MainActivity.class);
				//itMain.putExtra("networkavailable", networkAvailable);
				startActivity(itMain);
				WelcomeActivity.this.finish();
			}
		});

	}

	private void initData() {
		//�������ݿ⵽�ֻ���
		Util.copyDB2Phone(WelcomeActivity.this);
		//��ѹ���ʵ�sd��
		Util.unZipTheWordsVoice2SdCard(WelcomeActivity.this,
				Const.UNZIP_WORDS_FILE_NAME, Config.UNZIP_WORDS_FILE_PATH);
	}
}

