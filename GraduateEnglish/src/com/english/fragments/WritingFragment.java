package com.english.fragments;

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

import com.english.activity.WrittingDetailActivity;
import com.english.adapter.WrittingAdapter;
import com.english.database.EnglishDBOperate;
import com.english.database.EnglishDatabaseHelper;
import com.english.model.WrittingInfo;
import com.english.phone.R;

public class WritingFragment extends Fragment{
	private ListView writtingList = null;
	private View viewWriting = null;
	private EnglishDatabaseHelper eHelper = null;
	private EnglishDBOperate eOperate = null;
	private List<WrittingInfo> writtingInfos = null;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		viewWriting = inflater.inflate(R.layout.writting_layout, container,false);
		initData();
		initView();
		setData();
		return viewWriting;
	}
	
	
	private void setData() {
		WrittingAdapter wAdapter = new WrittingAdapter(getActivity(), writtingInfos);
		writtingList.setAdapter(wAdapter);
	}


	private void initData() {
		eHelper = new EnglishDatabaseHelper(getActivity());
		eOperate = new EnglishDBOperate(eHelper.getReadableDatabase(), getActivity());
		writtingInfos = eOperate.getAllWrittingInfoByDate();
	}


	private void initView() {
		writtingList = (ListView) viewWriting.findViewById(R.id.listview_writting);
		writtingList.setOnItemClickListener(new OnItemClickListener() {
 
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				Intent it = new Intent(getActivity(),WrittingDetailActivity.class);
				it.putExtra("writting_info", writtingInfos.get(position));
				startActivity(it);
			}
		});
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
