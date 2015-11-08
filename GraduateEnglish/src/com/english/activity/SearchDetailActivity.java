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
import com.english.util.Logger;

public class SearchDetailActivity extends Activity implements OnClickListener{
	private TextView textWord = null;
	private TextView textContent = null;
	private TextView textSymbols = null;
	private TextView textExample1 = null;
//	private LinearLayout ad1Layout = null;
//	private LinearLayout ad2Layout = null;
	private ImageButton buttonAdd = null;
	//点击播放单词音频文件
	private ImageButton buttonPlay = null;

	private WordInfo wordInfo = null;
	private EnglishDatabaseHelper eHelper = null;
	private EnglishDBOperate eOperate = null;
	private EnglishMediaPlayer mPlayer = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.search_detail_layout);
		initData();
		initView();
		setData();
		initDatabase();
		initActionBar();
	}

	private void initDatabase() {
		eHelper = new EnglishDatabaseHelper(this);
		eOperate = new EnglishDBOperate(eHelper.getWritableDatabase(), this);
	}

	private void setData() {
		textWord.setText(wordInfo.getWord());
		textSymbols.setText(wordInfo.getSymbols());
		textContent.setText(wordInfo.getContent());
		textExample1.setText(Html.fromHtml(wordInfo.getExample()));
//		AdUtil.showMiniAd(this, ad1Layout,15);
//		AdUtil.showBannerAd(this, ad2Layout);
	}

	private void initView() {
		textWord = (TextView) super.findViewById(R.id.search_detail_text_word);
		textContent = (TextView) super.findViewById(R.id.search_detail_text_content);
		textSymbols = (TextView) super.findViewById(R.id.search_detail_text_symbols);
		textExample1 = (TextView) super.findViewById(R.id.search_detail_text_example1);
//		ad1Layout = (LinearLayout) super.findViewById(R.id.search_detail_layout_ad1);
//		ad2Layout = (LinearLayout) super.findViewById(R.id.search_detail_layout_ad2);
		buttonAdd = (ImageButton) super.findViewById(R.id.search_detail_button_add);
		textExample1.setMovementMethod(ScrollingMovementMethod.getInstance());
		textContent.setMovementMethod(ScrollingMovementMethod.getInstance());
		buttonPlay = (ImageButton)findViewById(R.id.search_detail_button_volume);
		
		buttonAdd.setOnClickListener(this);
		buttonPlay.setOnClickListener(this);
	}
	
	private void initData(){
		wordInfo = (WordInfo) getIntent().getSerializableExtra("word_info");
		mPlayer = EnglishMediaPlayer.getInstance(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.search_detail_button_add:
			eOperate.updateWordIsKnownById(false, wordInfo.getId());
			Toast.makeText(SearchDetailActivity.this, "���ӳɹ���", Toast.LENGTH_SHORT).show();
			break;
		case R.id.search_detail_button_volume:
			mPlayer.playTheWordTune(wordInfo.getWord());
		break;
		}
	}

	@Override
	protected void onDestroy() {
		if(eHelper != null){
			eHelper.close();
			eHelper = null;
		}
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
			Intent it1 = new Intent(SearchDetailActivity.this, SearchActivity.class); 
			startActivity(it1); 
			break; 
		case R.id.actionbar_review: 
			Intent it = new Intent(SearchDetailActivity.this, UnknownWordActivity.class); 
			startActivity(it);
			break;
		}
		return super.onOptionsItemSelected(item);
	}
 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.actionbar_item, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
}
