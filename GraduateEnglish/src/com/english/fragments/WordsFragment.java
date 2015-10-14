package com.english.fragments;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.english.activity.WordsDetailActivity;
import com.english.adapter.WordLessonAdapter;
import com.english.database.EnglishDBOperate;
import com.english.database.EnglishDatabaseHelper;
import com.english.model.WordInfo;
import com.english.phone.R;

public class WordsFragment extends Fragment{

	private ListView wordsList = null;
	private View viewWords = null;
	  
	private EnglishDatabaseHelper eHelper = null;
	private EnglishDBOperate eOperate = null;
	private Context mContext = null;
	private List<WordInfo> lessonWords = null;
	
	/** 每课中最近访问时间*/
	private List<String> visitDateList = null;
	
	/** 每课中单词信息*/
	private List<Map<String, String>> abList = null;
	
	/** 每课中单词正确数 */
	private List<Integer> acList = null; 
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
		viewWords = inflater.inflate(R.layout.words_layout, container, false);
		 
		initView();
		initData(viewWords.getContext());
		setWordsLesson();
		
		return viewWords; 
	}
 
	private void initData(Context context) {
		mContext = context;
		if(eHelper == null){
			eHelper = new EnglishDatabaseHelper(mContext);
			eOperate = new EnglishDBOperate(eHelper.getReadableDatabase(), context);
		}
		 
	}

	private void initView() {
		wordsList = (ListView) viewWords.findViewById(R.id.listview_words);
		wordsList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				lessonWords = eOperate.getWordsByLesson(position);
				Intent it = new Intent(mContext,WordsDetailActivity.class);
				it.putExtra("lesson_num", position);
				it.putExtra("lesson_words", (Serializable)lessonWords);
				startActivity(it);
				
			}
		});
	}
	 
	private void setWordsLesson(){
		int lessonSize = eOperate.getLessonsSize()%100-1;
		visitDateList = eOperate.getLastVisitDateListByLesson(lessonSize);

		List<Map<String,String>> allAbList = new ArrayList<Map<String,String>>();
		for(int i=0;i<lessonSize;i++){
			abList = eOperate.test(i);
			for(int j=0;j<abList.size();j++){
				allAbList.add(abList.get(j));
			}
		}
		int total = 0;
		List<Integer> allAcList = new ArrayList<Integer>();
		for(int i=0;i<lessonSize;i++){
			total = eOperate.test2(i);
			allAcList.add(total);
		}
		acList = eOperate.getAccuracyCountByListen(lessonSize);  
		WordLessonAdapter wAdapter = new WordLessonAdapter(getActivity(), lessonSize, visitDateList, allAbList, allAcList);//lessonSize, visitDateList, abList, acList);
		wordsList.setAdapter(wAdapter);
	}

	@Override 
	public void onStop() {
		if(eHelper != null){
			eHelper.close();
			eHelper = null;
		}
		super.onStop();
	}

	@Override
	public void onResume() {
		if(eHelper == null){
			initData(mContext);
		}
		super.onResume();
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
