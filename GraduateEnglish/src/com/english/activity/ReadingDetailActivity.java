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
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.waps.AppConnect;

import com.english.ad.AdUtil;
import com.english.model.ReadingInfo;
import com.english.phone.R;
import com.english.util.SharedPreferenceUtil;
    
public class ReadingDetailActivity extends Activity implements OnClickListener{
	private TextView textTitle = null;
	private TextView textContent = null;
	private TextView textAnswer = null;
	private ImageButton buttonNext = null;
	private ImageButton buttonPrevious = null;
	private Button buttonAnswer = null;
	private ReadingInfo readingInfo = null;
	private List<ReadingInfo> readingInfos = null;
	private int index = 0;
	private LinearLayout adLayout = null;
	
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
		adLayout = (LinearLayout) super.findViewById(R.id.reading_detail_layout_ad2);

		
		buttonNext.setOnClickListener(this);
		buttonPrevious.setOnClickListener(this);
		buttonAnswer.setOnClickListener(this);
		
		textContent.setTextSize(new SharedPreferenceUtil(this).getFontSize("reading_size"));
		
		AdUtil.showBannerAd(this, adLayout);
		
	}

	@Override 
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.reading_detail_button_previous:
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
		}
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
}
