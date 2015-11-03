package com.english.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.english.ad.AdUtil;
import com.english.adapter.SearchAdapter;
import com.english.database.EnglishDBOperate;
import com.english.database.EnglishDatabaseHelper;
import com.english.model.WordInfo;
import com.english.phone.R;

public class SearchActivity extends Activity implements OnClickListener{
//	private LinearLayout adLayout = null;
	private ListView searchListView = null;
	private ImageButton buttonSearch = null;
	private EditText editInput = null;
	private EnglishDatabaseHelper eHelper = null;
	private EnglishDBOperate eOperate = null;
	private List<WordInfo> wordInfos = null;
	private SearchAdapter sAdapter = null;
	private String keyWord;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.search_layout);
		
		initView();
		initDatabase();
		initActionBar();
	}

	private void initDatabase() {
		eHelper = new EnglishDatabaseHelper(this);
		eOperate = new EnglishDBOperate(eHelper.getReadableDatabase(), this);
	}
	
	private void searchWordInfos(String keyWord){
		wordInfos.clear(); 
		wordInfos = eOperate.getSearchResult(keyWord);
	}
	 
	
	private void initView() {
		searchListView = (ListView) super.findViewById(R.id.search_layout_listview_result);
		buttonSearch = (ImageButton) super.findViewById(R.id.search_layout_button_search);
		editInput = (EditText) super.findViewById(R.id.search_layout_edittext_input);
		buttonSearch.setOnClickListener(this); 
//		adLayout = (LinearLayout) super.findViewById(R.id.search_layout_layout_ad);

		wordInfos = new ArrayList<WordInfo>();
		searchListView.setOnItemClickListener(new OnItemClickListenerImpl());
//		AdUtil.showMiniAd(this, adLayout,15);
	}

	@Override
	public void onClick(View v) { 
		switch(v.getId()){
		case R.id.search_layout_button_search: 
			searchListView.setVisibility(View.VISIBLE);
			keyWord = editInput.getText().toString().trim();

			if(keyWord == null || keyWord.toString().trim().equals("")){
				Toast.makeText(this, "请输入要查找的单词...", Toast.LENGTH_SHORT).show();
				return;
			} 
			searchWordInfos(keyWord);
			if(wordInfos.size() > 0){
				sAdapter = new SearchAdapter(SearchActivity.this, wordInfos);
				searchListView.setAdapter(sAdapter);
			}else{
				Toast.makeText(this, "没有查找到相关单词...", Toast.LENGTH_SHORT).show();
				wordInfos.clear(); 
			}
			hideInput();
			break;
		}
	}
	
	private void hideInput(){
		((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                                .hideSoftInputFromWindow(SearchActivity.this
                                                .getCurrentFocus().getWindowToken(),
                                                InputMethodManager.HIDE_NOT_ALWAYS);
	}

	@Override
	public void finish() {
		if(eHelper != null){
			eHelper.close();
			eHelper = null;
		}
		super.finish();
	}
	
	private class OnItemClickListenerImpl implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
			WordInfo wordInfo = wordInfos.get(position);
			Intent it = new Intent(SearchActivity.this,SearchDetailActivity.class);
			it.putExtra("word_info", wordInfo);
			startActivity(it);
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
			break; 
		case R.id.actionbar_review:  
			Intent it = new Intent(SearchActivity.this, UnknownWordActivity.class); 
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
