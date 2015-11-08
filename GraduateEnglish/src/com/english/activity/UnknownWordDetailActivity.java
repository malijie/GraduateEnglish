package com.english.activity;

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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.english.ad.AdUtil;
import com.english.database.EnglishDBOperate;
import com.english.database.EnglishDatabaseHelper;
import com.english.media.EnglishMediaPlayer;
import com.english.model.WordInfo;
import com.english.phone.R;

public class UnknownWordDetailActivity extends Activity implements OnClickListener{
	private WordInfo mWordInfo = null;
	private TextView textWord = null;
	private TextView textSymbol = null;
	private TextView textContent = null;
	private TextView textExample = null;
 	private Button butDelete = null;
	//单词音频
	private ImageButton buttonVoice = null;
// 	private LinearLayout adLayout = null;
	//音频播放媒体类
	EnglishMediaPlayer mPlayer = null;
 	private EnglishDatabaseHelper eHelper = null;
 	private EnglishDBOperate eOperate = null;
 	private boolean isDeleted = false;
 	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.unknown_words_detail_layout);
		
		initData();
		initView();
		setData();
		initActionBar();
		
	} 
	
	private void setData() {
		textWord.setText(mWordInfo.getWord());
		textSymbol.setText(mWordInfo.getSymbols());
		textExample.setText(Html.fromHtml(mWordInfo.getExample())); 
		textContent.setText(mWordInfo.getContent());
		textExample.setMovementMethod(ScrollingMovementMethod.getInstance());
//		AdUtil.showMiniAd(this, adLayout,10);
	}
 
	private void initView() {
		textWord = (TextView) super.findViewById(R.id.unkonwn_words_detail_text_word);
		textExample = (TextView) super.findViewById(R.id.unkonwn_words_detail_text_example);
		textSymbol = (TextView) super.findViewById(R.id.unkonwn_words_detail_text_symbol);
		textContent = (TextView) super.findViewById(R.id.unkonwn_words_detail_text_content);
		butDelete = (Button) super.findViewById(R.id.unkonwn_words_detail_button_delete);
		buttonVoice = (ImageButton) super.findViewById(R.id.unkonwn_words_detail_button_volume);
		buttonVoice.setOnClickListener(this);
//		adLayout = (LinearLayout) super.findViewById(R.id.unkonwn_words_detail_layout_ad);
		butDelete.setOnClickListener(this);
	}

	private void initData() {
		mWordInfo = (WordInfo) getIntent().getSerializableExtra("wordinfo");
		eHelper = new EnglishDatabaseHelper(this);
		eOperate = new EnglishDBOperate(eHelper.getWritableDatabase(), this);
		mPlayer = EnglishMediaPlayer.getInstance(UnknownWordDetailActivity.this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.unkonwn_words_detail_button_delete:
			if(!isDeleted){
				eOperate.updateUnknownWordStatusById(mWordInfo.getId());
				Toast.makeText(UnknownWordDetailActivity.this,"已删除!", Toast.LENGTH_SHORT).show();
				isDeleted = true;
				UnknownWordDetailActivity.this.finish();
				Intent it = new Intent(UnknownWordDetailActivity.this,UnknownWordActivity.class);
				startActivity(it);	
				UnknownWordDetailActivity.this.finish();
			}
			break;
		case R.id.unkonwn_words_detail_button_volume:
				mPlayer.playTheWordTune(mWordInfo.getWord());
			break;
		}
	}

	@Override
	protected void onDestroy() {
		//关闭数据库
		if(eHelper != null){
			eHelper.close();
			eHelper = null;
		}
		//关闭媒体
		mPlayer.stopPlay();
		super.onDestroy();
	}
	
	private void initActionBar() {
		ActionBar mainActionBar = getActionBar();
		mainActionBar.show();
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case R.id.actionbar_search:
			Intent it1 = new Intent(UnknownWordDetailActivity.this, SearchActivity.class); 
			startActivity(it1); 
			break; 
		case R.id.actionbar_review: 
			Intent it = new Intent(UnknownWordDetailActivity.this, UnknownWordActivity.class); 
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



}
