package com.english.fragments;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.english.activity.ReadingDetailActivity;
import com.english.adapter.ReadingAdapter;
import com.english.pay.PayConst;
import com.english.database.EnglishDBOperate;
import com.english.database.EnglishDatabaseHelper;
import com.english.inter.IDialogOnClickListener;
import com.english.model.ReadingInfo;
import com.english.pay.PayManager;
import com.english.phone.R;
import com.english.config.Const;
import com.english.util.Logger;
import com.english.util.Util;

public class ReadingFragment extends Fragment{

	private ListView readingList = null;
	private View viewReading = null;
	private EnglishDatabaseHelper eHelper = null;
	private EnglishDBOperate eOperate = null;
	private List<ReadingInfo> readingInfoByDate = null;
	private List<List<ReadingInfo>> allReadingInfo = null;
	private ReadingAdapter rAdapter = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		  
		viewReading = inflater.inflate(R.layout.reading_layout, container, false);
		
		initDB();
		initView();
	
		return viewReading;
	}


	private void initDB() {
		eHelper = new EnglishDatabaseHelper(ReadingFragment.this.getActivity());
		eOperate = new EnglishDBOperate(eHelper.getReadableDatabase(), ReadingFragment.this.getActivity());
		allReadingInfo = new ArrayList<List<ReadingInfo>>();
		for(int i= Const.READING_END_DATE ;i>= Const.READING_BEGIN_DATE ; i--){
			readingInfoByDate = eOperate.getAllReadingInfoByDate(i);
			allReadingInfo.add(readingInfoByDate);
		}
	}

	private void initView() {
		readingList = (ListView) viewReading.findViewById(R.id.listview_reading);
 
		rAdapter = new ReadingAdapter(ReadingFragment.this.getActivity(), allReadingInfo);
		readingList.setAdapter(rAdapter);
		
		readingList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				PayManager payManager = new PayManager(getActivity());
				//最新的考研试题且没有购买过则弹出对话框
				if (position == 0 && !payManager.isCompleteReadingPaperPay()) {
						//点击的是最新的试题，若没付费则弹出付费对话框
						Util.showAlertDialog(getActivity(), Const.DIALOG_PAY_TITLE, Const.DIALOG_PAY_READING_MSG,
								new IDialogOnClickListener() {
									@Override
									public void onClick() {
										//用户点击付费，跳转到付费界面
										payManager.handlePayEvent(PayConst.PAY_TYPE_READING, PayConst.PRICE_READING_EXMINATION);
									}
								});

					} else {
						Intent it = new Intent(ReadingFragment.this.getActivity(), ReadingDetailActivity.class);
						it.putExtra("reading_info", (Serializable) allReadingInfo.get(position));
						startActivity(it);
					}

				}


			}

			);
		}


		@Override
	public void onDestroy() {
		if(eHelper != null){
			eHelper.close();
			eHelper = null;
		}
		super.onDestroy();
	}
	
	
	
}
