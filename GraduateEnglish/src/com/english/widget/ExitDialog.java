package com.english.widget;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.english.database.EnglishDatabaseHelper;
import com.english.phone.R;

public class ExitDialog extends AlertDialog implements OnClickListener{

	private Button butConfirm = null;
	private Button butCancel = null;
	private Activity mActivity = null;
	private EnglishDatabaseHelper mHelper = null;
	
	public ExitDialog(Activity context, EnglishDatabaseHelper eHelper) {
		super(context);
		mActivity = context;
		mHelper = eHelper;
	}
	
	@Override 
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.exit_dialog);
		
		initView();
	}
	private void initView() {
		butConfirm = (Button) findViewById(R.id.dialog_exit_button_confirm);
		butCancel = (Button) findViewById(R.id.dialog_exit_button_cancel);
		
		butConfirm.setOnClickListener(this);
		butCancel.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.dialog_exit_button_confirm:
			butConfirm.setBackgroundColor(mActivity.getResources().getColor(R.color.textview_light_green));
			ExitDialog.this.dismiss();
			if(mHelper != null){
				mHelper.close();
				mHelper = null;
			}
			mActivity.finish();
			break;
		case R.id.dialog_exit_button_cancel: 
			butCancel.setBackgroundColor(mActivity.getResources().getColor(R.color.textview_light_green));
			ExitDialog.this.dismiss();
			break;
		}
		
	}
	
}
