package com.english.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import cn.waps.AppConnect;

import com.english.activity.HelpAndTipActivity;
import com.english.pay.PayManager;
import com.english.phone.R;
import com.english.util.Logger;
import com.english.widget.FontDialog;

public class SettingFragment extends Fragment implements OnClickListener{
	private View viewSetting = null;
	private Button buttonSetWord = null;
	private Button buttonSetReading = null;
	private Button buttonSetWritting = null;
	private ImageButton buttonMoreWord = null;
	private ImageButton buttonMoreReading = null;
	private ImageButton buttonMoreWritting = null;
	private Button buttonSetHelp = null;
	private Button buttonUpdate = null;
	private ImageButton buttonMoreHelp = null;
	private Button buttonExit = null;
	private Button buttonFeedback = null;
	private static final int MODE_WORD = 0;
	private static final int MODE_READING = 1;
	private static final int MODE_WRITTING = 2;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) { 
		viewSetting = inflater.inflate(R.layout.setting_layout, null, false);
		initView();
		return viewSetting;
	}  
	private void initView() {
		buttonSetWord = (Button) viewSetting.findViewById(R.id.setting_button_font_word);
		buttonSetReading = (Button) viewSetting.findViewById(R.id.setting_button_font_reading);
		buttonSetWritting = (Button) viewSetting.findViewById(R.id.setting_button_font_writting);
		buttonMoreWord = (ImageButton) viewSetting.findViewById(R.id.setting_button_more_word);
		buttonMoreReading = (ImageButton) viewSetting.findViewById(R.id.setting_button_more_reading);
		buttonMoreWritting = (ImageButton) viewSetting.findViewById(R.id.setting_button_more_writting);
		buttonSetHelp = (Button) viewSetting.findViewById(R.id.setting_button_help);
		buttonMoreHelp = (ImageButton) viewSetting.findViewById(R.id.setting_button_more_help);
		buttonExit = (Button) viewSetting.findViewById(R.id.setting_button_exit);
		buttonUpdate = (Button) viewSetting.findViewById(R.id.setting_button_update);
		buttonFeedback = (Button) viewSetting.findViewById(R.id.setting_button_feedback);
		
		buttonSetWord.setOnClickListener(this);
		buttonSetReading.setOnClickListener(this);
		buttonSetWritting.setOnClickListener(this);
		buttonMoreWord.setOnClickListener(this);
		buttonMoreReading.setOnClickListener(this);
		buttonMoreWritting.setOnClickListener(this);
		buttonSetHelp.setOnClickListener(this);
		buttonMoreHelp.setOnClickListener(this);
		buttonExit.setOnClickListener(this);
		buttonUpdate.setOnClickListener(this);
		buttonFeedback.setOnClickListener(this);
	}
	
	
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.setting_button_font_word:
			setWordFont();
			break;
		case R.id.setting_button_font_reading:
			setReadingFont();
			break;
		case R.id.setting_button_font_writting:
			setWrittingFont();
			break;
		case R.id.setting_button_more_word:
			setWordFont();
			break;
		case R.id.setting_button_more_reading:
			setReadingFont();
			break;
		case R.id.setting_button_more_writting:
			setWrittingFont();
			break;
		case R.id.setting_button_help:
			showHelpTip();
			break;
		  
		case R.id.setting_button_more_help:
			showHelpTip();
			break;
		
		case R.id.setting_button_exit:
			getActivity().finish();
			break;
		case R.id.setting_button_update:
			AppConnect.getInstance(getActivity()).checkUpdate(getActivity());
			break;
		case R.id.setting_button_feedback:
			AppConnect.getInstance(getActivity()).showFeedback(getActivity()	); //��ʾ�û�����
			break;
		}  
	}
	private void showHelpTip() {
		Intent it = new Intent(getActivity(),HelpAndTipActivity.class);
		startActivity(it); 
	}
	private void setWrittingFont() {
		showFontDialog(MODE_WRITTING);
	}
	private void setReadingFont(){
		showFontDialog(MODE_READING);
	}
	
	private void setWordFont() {
		showFontDialog(MODE_WORD);
	}
	
	private void showFontDialog(int MODE){
		FontDialog fDialog = new FontDialog(getActivity(),MODE);
		fDialog.show();
	}
}
