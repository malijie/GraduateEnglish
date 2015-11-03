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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.english.ad.AdUtil;
import com.english.model.WrittingInfo;
import com.english.phone.R;
import com.english.util.SharedPreferenceUtil;

public class WrittingAnswerActivity extends Activity{
	private TextView textTitle = null;
	private TextView textAnswer = null;
	private WrittingInfo writtingInfo= null;
//	private LinearLayout adLayout = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.answer_layout);
		initData();
		initView();
		setData();
		initActionBar();
	}


	private void setData() { 
		textTitle.setText(writtingInfo.getDate() + "ÄêÐ´×÷" + writtingInfo.getTitle() + "²Î¿¼·¶ÎÄ");
		textAnswer.setText(Html.fromHtml(writtingInfo.getAnswer()));
		textAnswer.setMovementMethod(ScrollingMovementMethod.getInstance()); //ï¿½ï¿½ï¿½Ã´ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½
		textAnswer.setMovementMethod(ScrollingMovementMethod.getInstance());
		
		textAnswer.setTextSize(new SharedPreferenceUtil(this).getFontSize("writting_size"));
	}


	private void initData() {
		writtingInfo = (WrittingInfo) getIntent().getSerializableExtra("writting_info");
	}


	private void initView() {
		textTitle = (TextView) super.findViewById(R.id.writting_answer_detail_text_title);
		textAnswer = (TextView) super.findViewById(R.id.writting_answer_detail_text_answer);
//		adLayout = (LinearLayout) super.findViewById(R.id.writting_answer_detail_layout_ad);

//		AdUtil.showBannerAd(WrittingAnswerActivity.this, adLayout);
	}
	
	private void initActionBar() {
		ActionBar mainActionBar = getActionBar();
		mainActionBar.show();
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case R.id.actionbar_search:
			Intent it1 = new Intent(WrittingAnswerActivity.this, SearchActivity.class); 
			startActivity(it1); 
			break; 
		case R.id.actionbar_review: 
			Intent it = new Intent(WrittingAnswerActivity.this, UnknownWordActivity.class); 
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
