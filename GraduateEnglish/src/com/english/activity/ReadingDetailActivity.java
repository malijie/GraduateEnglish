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

import com.english.config.Config;
import com.english.config.Const;
import com.english.media.EnglishMediaPlayer;
import com.english.model.ReadingInfo;
import com.english.phone.R;
import com.english.util.SharedPreferenceUtil;

public class ReadingDetailActivity extends Activity implements OnClickListener{
	public static final String TAG = ReadingDetailActivity.class.getSimpleName();
	//阅读理解标题控件
	private TextView textTitle = null;
	//阅读理解内容控件
	private TextView textContent = null;
	//阅读理解答案控件
	private TextView textAnswer = null;
	//下一篇按钮
	private ImageButton buttonNext = null;
	//上一篇按钮
	private ImageButton buttonPrevious = null;
	//音频按钮
	private ImageButton buttonVolume = null;

	private EnglishMediaPlayer mEnglishMediaPlayer = null;

	//答案按钮
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
	 * 处理播放阅读理解文章事件
	 */
	private void handlePlayPassageEvent() {
		//构造播放文件名称
		String playFile = Config.PLAY_READING_VOLUME_PATH + Const.READING_PART_A
					+ readingInfo.getDate() + "_" + (index+1) + Const.READING_VOICE_SUFFIX;
		mEnglishMediaPlayer.playThePassage(playFile);
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
