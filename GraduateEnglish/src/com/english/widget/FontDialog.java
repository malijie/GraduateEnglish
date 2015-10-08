package com.english.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.english.phone.R;
import com.english.util.SharedPreferenceUtil;

public class FontDialog extends AlertDialog implements android.view.View.OnClickListener{
	private TextView textTitle = null;
	private Button buttonConfirm = null;
	private Button buttonCancel = null;
	private RadioButton radioBig = null;
	private RadioButton radioSmall = null;
	private RadioButton radioMiddle = null;
	private Context mContext = null;
	private SharedPreferenceUtil spUtil= null;
	private int mMode;
	private int fontSize = 17;
	
	public FontDialog(Context context, int mode) {
		super(context);
		this.mContext = context;
		this.mMode = mode;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.font_dialog);
		initView();
	}
	
	
	private void initView() {
		buttonConfirm = (Button) super.findViewById(R.id.dialog_font_button_confirm);
		buttonCancel = (Button) super.findViewById(R.id.dialog_font_button_cancel);
		radioBig = (RadioButton) super.findViewById(R.id.dialog_font_radio_big);
		radioMiddle = (RadioButton) super.findViewById(R.id.dialog_font_radio_middle);
		radioSmall = (RadioButton) super.findViewById(R.id.dialog_font_radio_small);
		textTitle = (TextView) super.findViewById(R.id.dialog_font_text_title);
		buttonConfirm.setOnClickListener(this);
		buttonCancel.setOnClickListener(this);
		radioSmall.setOnClickListener(this);
		radioMiddle.setOnClickListener(this);
		radioBig.setOnClickListener(this);
		
		if(mMode == 0){
			textTitle.setText("设置单词字体");
		}else if(mMode == 1){
			textTitle.setText("设置阅读字体");
		}else if(mMode == 2){
			textTitle.setText("设置写作字体");
		}
		radioMiddle.setChecked(true);
		spUtil = new SharedPreferenceUtil(mContext);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){ 
		case R.id.dialog_font_button_confirm:
			buttonConfirm.setBackgroundColor(mContext.getResources().getColor(R.color.textview_light_green));
			if(mMode == 0){	//设置word单词字体大小 
				spUtil.setFontSize("word_size",fontSize);
			}else if(mMode == 1){
				spUtil.setFontSize("reading_size",fontSize);
			}else if(mMode == 2){
				spUtil.setFontSize("writting_size",fontSize);
			}
			Toast.makeText(mContext, "设置成功",Toast.LENGTH_SHORT).show();
			this.dismiss();
			break;
		case R.id.dialog_font_button_cancel:
			buttonCancel.setBackgroundColor(mContext.getResources().getColor(R.color.textview_light_green));
			this.dismiss();
			break;
		case R.id.dialog_font_radio_small:
			if(mMode == 0){	//设置word单词字体大小 
				fontSize = 13;
			}else if(mMode == 1){
				fontSize = 13;
			}else if(mMode == 2){
				fontSize = 13;
			}
			
			break;
		case R.id.dialog_font_radio_middle: 
			if(mMode == 0){	//设置word单词字体大小 
				fontSize = 20;
			}else if(mMode == 1){
				fontSize = 17; 
			}else if(mMode == 2){
				fontSize = 17;
			}
			break; 
		case R.id.dialog_font_radio_big:
			if(mMode == 0){	//设置word单词字体大小 
				fontSize = 24;
			}else if(mMode == 1){
				fontSize = 22;
			}else if(mMode == 2){
				fontSize = 22;
			}
			break;
		}
	}

}
