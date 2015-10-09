package com.english.activity;


import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.english.ad.AdUtil;
import com.english.fragments.ReadingFragment;
import com.english.fragments.SettingFragment;
import com.english.fragments.WordsFragment;
import com.english.fragments.WritingFragment;
import com.english.phone.R;
import com.english.config.Profile;
import com.english.widget.ExitDialog;

public class MainActivity extends Activity implements OnClickListener{
	
	private WordsFragment wordsFragment = null;
	private ReadingFragment readingFragment = null;
	private WritingFragment writingFragment = null;
	private SettingFragment settingFragment = null;
	 
	private RelativeLayout wordsLayout = null;
	private RelativeLayout settingLayout = null;
	private RelativeLayout readingLayout = null;
	private RelativeLayout wrtingLayout = null;
	
	private ImageView readImage = null;
	private ImageView wordsImage = null;
	private ImageView setImage = null;
	private ImageView writeImage = null;
	
	private TextView readText = null;
	private TextView wordText = null;
	private TextView writeText = null;
	private TextView setText = null;
	private long exitTime = 0;
	
	private FragmentManager fragmentManager = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initView();
		initActionBar();
		setTabSelection(0);
	} 
	
	

	private void initView() {
		wordsLayout = (RelativeLayout) super.findViewById(R.id.main_layout_relativelayout_words);
		settingLayout = (RelativeLayout) super.findViewById(R.id.main_layout_relativelayout_setting);
		readingLayout = (RelativeLayout) super.findViewById(R.id.main_layout_relativelayout_reading);
		wrtingLayout = (RelativeLayout) super.findViewById(R.id.main_layout_relativelayout_writing);
		
		readImage = (ImageView) super.findViewById(R.id.main_layout_button_reading);
		writeImage = (ImageView) super.findViewById(R.id.main_layout_button_writing);
		wordsImage = (ImageView) super.findViewById(R.id.main_layout_button_words);
		setImage = (ImageView) super.findViewById(R.id.main_layout_button_setting);
		
		wordText = (TextView) super.findViewById(R.id.main_layout_textview_words);
		readText = (TextView) super.findViewById(R.id.main_layout_textview_reading);
		writeText = (TextView) super.findViewById(R.id.main_layout_textview_writing);
		setText = (TextView) super.findViewById(R.id.main_layout_textview_setting); 
		
		wordsLayout.setOnClickListener(this);
		readingLayout.setOnClickListener(this);
		wrtingLayout.setOnClickListener(this);
		settingLayout.setOnClickListener(this);
		
		fragmentManager = getFragmentManager();
		
		
	}
	
	/** 
	 * 设置tab选中的界面
	 * @param index 选中参数
	 */
	private void setTabSelection(int index) {
		clearSelection();
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		hideFragments(transaction);
		
		switch(index){
		case 0:
			wordsImage.setImageResource(R.drawable.bottom_button_words_selected);
			wordText.setTextColor(getResources().getColor(R.color.bottom_textview_selected));
			if(wordsFragment == null){ 
				wordsFragment = new WordsFragment();
				transaction.add(R.id.main_layout_frame_content, wordsFragment);
			}else{
				transaction.show(wordsFragment);
			}
			break;
		case 1:
			readImage.setImageResource(R.drawable.bottom_button_reading_selected);
			readText.setTextColor(getResources().getColor(R.color.bottom_textview_selected));
			if(readingFragment == null){
				readingFragment = new ReadingFragment();
				transaction.add(R.id.main_layout_frame_content, readingFragment);
			}else{
				transaction.show(readingFragment);
			}
			break;
		case 2:
			writeImage.setImageResource(R.drawable.bottom_button_writting_selected);
			writeText.setTextColor(getResources().getColor(R.color.bottom_textview_selected));
			if(writingFragment == null){
				writingFragment = new WritingFragment();
				transaction.add(R.id.main_layout_frame_content, writingFragment);
			}else{
				transaction.show(writingFragment);
			}
			break;
		case 3:
			setImage.setImageResource(R.drawable.bottom_button_setting_selected);
			setText.setTextColor(getResources().getColor(R.color.bottom_textview_selected));
			if(settingFragment == null){
				settingFragment = new SettingFragment();
				transaction.add(R.id.main_layout_frame_content, settingFragment);
			}else{
				transaction.show(settingFragment);
			}
			break;
		}
		transaction.commit(); 
		
	}

	
	/**
	 * 隐藏Fragment
	 * @param transaction
	 */
	private void hideFragments(FragmentTransaction transaction) {
		if(wordsFragment != null){
			transaction.hide(wordsFragment);
		}
		if(readingFragment != null){
			transaction.hide(readingFragment);
		}
		if(writingFragment != null){
			transaction.hide(writingFragment);
		}
		if(settingFragment != null){
			transaction.hide(settingFragment);
		}
	}

	/**
	 * 清除选中状态
	 */
	private void clearSelection() {
		writeImage.setImageResource(R.drawable.bottom_button_writting_normal);
		readImage.setImageResource(R.drawable.bottom_button_reading_normal);
		setImage.setImageResource(R.drawable.bottom_button_setting_normal);
		wordsImage.setImageResource(R.drawable.bottom_button_words_normal);
		wordText.setTextColor(getResources().getColor(R.color.bottom_textview_normal));
		readText.setTextColor(getResources().getColor(R.color.bottom_textview_normal));
		writeText.setTextColor(getResources().getColor(R.color.bottom_textview_normal));
		setText.setTextColor(getResources().getColor(R.color.bottom_textview_normal));
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.main_layout_relativelayout_words:
			setTabSelection(Profile.BOTTOM_SELECT_WORD);
			break;
		
		case R.id.main_layout_relativelayout_reading:
			setTabSelection(Profile.BOTTOM_SELECT_READING);
			break;
			
		case R.id.main_layout_relativelayout_writing:
			setTabSelection(Profile.BOTTOM_SELECT_WRITTING);
			break;
		case R.id.main_layout_relativelayout_setting:
			setTabSelection(Profile.BOTTOM_SELECT_SETTING);
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
			Intent it1 = new Intent(MainActivity.this, SearchActivity.class); 
			startActivity(it1); 
			break; 
		case R.id.actionbar_review: 
			Intent it = new Intent(MainActivity.this, UnknownWordActivity.class); 
			startActivity(it);
			break;
		case R.id.actionbar_setting:
			setTabSelection(Profile.BOTTOM_SELECT_SETTING);
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



	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		  if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){   
//		        if((System.currentTimeMillis()-exitTime) > 2000){
//		            Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
//		            exitTime = System.currentTimeMillis();
//		        } else {
//		        	AdUtil.closeAd(MainActivity.this);
//		            finish();
//		            System.exit(0);
//		        }
//		        return true;

			  ExitDialog exitDialog = new ExitDialog(this);
			  exitDialog.show();
		    }
		
		return super.onKeyDown(keyCode, event);
	}
	
}
