package com.english.activity;

import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.english.config.Config;
import com.english.config.Const;
import com.english.inter.IDialogOnClickListener;
import com.english.media.EnglishMediaPlayer;
import com.english.model.ReadingInfo;
import com.english.pay.PayConst;
import com.english.pay.PayManager;
import com.english.phone.R;
import com.english.util.SharedPreferenceUtil;
import com.english.util.Util;

public class ReadingDetailActivity extends Activity implements OnClickListener{
	public static final String TAG = ReadingDetailActivity.class.getSimpleName();
	//闃呰鐞嗚В鏍囬鎺т欢
	private TextView textTitle = null;
	//闃呰鐞嗚В鍐呭鎺т欢
	private TextView textContent = null;
	//闃呰鐞嗚В绛旀鎺т欢
	private TextView textAnswer = null;
	//涓嬩竴绡囨寜閽?
	private ImageButton buttonNext = null;
	//涓婁竴绡囨寜閽?
	private ImageButton buttonPrevious = null;
	//闊抽鎸夐挳
	private ImageButton buttonVolume = null;

	private EnglishMediaPlayer mEnglishMediaPlayer = null;
	//支付
	private PayManager payManager = null;

	//绛旀鎸夐挳
	private Button buttonAnswer = null;
	private ReadingInfo readingInfo = null;
	private List<ReadingInfo> readingInfos = null;
	private int index = 0;
//	private LinearLayout adLayout = null;

	@Override 
	protected void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.reading_detail_layout); 
		
		initView();
		initData();
		initActionBar();
	}

	private void initData() {
		readingInfos = (List<ReadingInfo>) getIntent().getSerializableExtra("reading_info");
		readingInfo = readingInfos.get(index);
		mEnglishMediaPlayer = EnglishMediaPlayer.getInstance(this);
		textTitle.setText(readingInfo.getTitle());
		textContent.setText(Html.fromHtml(readingInfo.getContent()));
		textContent.setMovementMethod(ScrollingMovementMethod.getInstance());
		payManager = new PayManager(this);
	}

	private void initView() {
		textTitle = (TextView) super.findViewById(R.id.reading_detail_text_title);
		textContent = (TextView) super.findViewById(R.id.reading_detail_text_content);
		textAnswer = (TextView) super.findViewById(R.id.reading_detail_text_answer);
		buttonNext = (ImageButton) super.findViewById(R.id.reading_detail_button_next);
		buttonPrevious = (ImageButton) super.findViewById(R.id.reading_detail_button_previous);
		buttonAnswer = (Button) super.findViewById(R.id.reading_detail_button_answer);
		buttonVolume = (ImageButton) super.findViewById(R.id.reading_detail_button_volume);
//		adLayout = (LinearLayout) super.findViewById(R.id.reading_detail_layout_ad2);

		
		buttonNext.setOnClickListener(this);
		buttonPrevious.setOnClickListener(this);
		buttonAnswer.setOnClickListener(this);
		buttonVolume.setOnClickListener(this);

		textContent.setTextSize(new SharedPreferenceUtil(this).getFontSize("reading_size"));
		
//		AdUtil.showBannerAd(this, adLayout);
		
	}

	@Override 
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.reading_detail_button_previous:
			mEnglishMediaPlayer.stopPlay();
			if(index == 0){
				buttonPrevious.setVisibility(View.INVISIBLE);
				return;
			}
			buttonAnswer.setVisibility(View.VISIBLE);
			textAnswer.setVisibility(View.GONE);
			buttonNext.setVisibility(View.VISIBLE);
			buttonPrevious.setVisibility(View.VISIBLE);
			index --;
			readingInfo = readingInfos.get(index); 
			textTitle.setText(readingInfo.getTitle());
			textContent.scrollTo(0, 0);
			textContent.setText(Html.fromHtml(readingInfo.getContent()));
			 
			break;
		case R.id.reading_detail_button_next:
			mEnglishMediaPlayer.stopPlay();
			if(index == readingInfos.size()-1){ 
				buttonNext.setVisibility(View.INVISIBLE);
				return;
			}
			buttonAnswer.setVisibility(View.VISIBLE);
			textAnswer.setVisibility(View.GONE);
			buttonNext.setVisibility(View.VISIBLE);
			buttonPrevious.setVisibility(View.VISIBLE);
			index ++;
			textContent.scrollTo(0, 0);
			readingInfo = readingInfos.get(index);
			textTitle.setText(readingInfo.getTitle());
			textContent.setText(Html.fromHtml(readingInfo.getContent()));
			break;
		case R.id.reading_detail_button_answer:
			buttonAnswer.setVisibility(View.GONE);
			textAnswer.setVisibility(View.VISIBLE);
			textAnswer.setText(readingInfo.getAnswer());
			break;
		case R.id.reading_detail_button_volume:
			handlePlayPassageEvent();
			break;
		}
	}

	/**
	 * 播放考研真题音频
	 */
	private void handlePlayPassageEvent() {
		boolean isPayed = checkPayedForReadingVoice();
		if(!isPayed){
			//没有支付弹出支付对话框
			Util.showAlertDialog(this, Const.DIALOG_PAY_TITLE, Const.DIALOG_PAY_VOICE_MSG,
					new IDialogOnClickListener() {
						@Override
						public void onClick() {
							//用户点击付费，跳转到付费界面
							payManager.handlePayEvent(PayConst.PAY_TYPE_READING_VOICE, PayConst.PRICE_READING_VOICE);
						}
					});
		}else{
			Toast.makeText(this,"开始播放", Toast.LENGTH_SHORT).show();
			String playFile = Config.PLAY_READING_VOLUME_PATH + Const.READING_PART_A
					+ readingInfo.getDate() + "_" + (index+1) + Const.READING_VOICE_SUFFIX;
			mEnglishMediaPlayer.playThePassage(playFile);
		}


	}

	/**
	 * 返回是否支付
	 * @return
	 */
	private boolean checkPayedForReadingVoice() {
		if(payManager != null){
			payManager.isCompleteReadingVoicePay();
		}
		return false;
	}

	private void initActionBar() {
		ActionBar mainActionBar = getActionBar();
		mainActionBar.show();
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case R.id.actionbar_search:
			Intent it1 = new Intent(ReadingDetailActivity.this, SearchActivity.class); 
			startActivity(it1); 
			break; 
		case R.id.actionbar_review: 
			Intent it = new Intent(ReadingDetailActivity.this, UnknownWordActivity.class); 
			startActivity(it);
			break;
		}
		return super.onOptionsItemSelected(item);
	}
 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.actionbar_item_detail, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	protected void onDestroy() {
		mEnglishMediaPlayer.stopPlay();
		super.onDestroy();
	}
}
