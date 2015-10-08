package com.english.activity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.english.ad.AdUtil;
import com.english.model.WrittingInfo;
import com.english.phone.R;
import com.english.util.SharedPreferenceUtil;

public class WrittingDetailActivity extends Activity implements OnClickListener{
	private TextView textTitle = null;
	private TextView textContent = null;
	private Button buttonAnswer = null;
	private ImageView imageContent = null;
	private WrittingInfo writtingInfo = null;
	private LinearLayout  adLayout = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.writting_detail_layout);

		initView();
		initData();
		initActionBar();
	}

	private void initData() {
		writtingInfo = (WrittingInfo) getIntent().getSerializableExtra("writting_info");
		textTitle.setText(writtingInfo.getTitle());
		textContent.setText(writtingInfo.getQuestion());
		textContent.setMovementMethod(ScrollingMovementMethod.getInstance()); //设置触摸滑动滚动条


		if(writtingInfo.getHaveImage().equals("true")){
			try {
				imageContent.setVisibility(View.VISIBLE);
				Bitmap bitmap = BitmapFactory.decodeStream(getResources().getAssets().open(writtingInfo.getImagePath())); 
				imageContent.setImageBitmap(bitmap);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
 
	private void initView() {
		textTitle = (TextView) super.findViewById(R.id.writting_detail_text_title);
		textContent = (TextView) super.findViewById(R.id.writting_detail_text_content);
		imageContent = (ImageView) super.findViewById(R.id.writting_detail_image_content);
		buttonAnswer = (Button) super.findViewById(R.id.writting_detail_button_answer);
		adLayout = (LinearLayout) super.findViewById(R.id.writting_detail_layout_ad);
		buttonAnswer.setOnClickListener(this);
		
		textContent.setTextSize(new SharedPreferenceUtil(this).getFontSize("writting_size"));
		AdUtil.showBannerAd(this, adLayout);
		
	}


	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.writting_detail_button_answer:
			 Intent it = new Intent(WrittingDetailActivity.this,WrittingAnswerActivity.class);
			 it.putExtra("writting_info", writtingInfo);
			 startActivity(it);
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
			Intent it1 = new Intent(WrittingDetailActivity.this, SearchActivity.class); 
			startActivity(it1); 
			break; 
		case R.id.actionbar_review: 
			Intent it = new Intent(WrittingDetailActivity.this, UnknownWordActivity.class); 
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
