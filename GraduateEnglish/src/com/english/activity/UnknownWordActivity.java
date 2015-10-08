package com.english.activity;

import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.english.adapter.UnknownWordsAdapter;
import com.english.database.EnglishDBOperate;
import com.english.database.EnglishDatabaseHelper;
import com.english.model.WordInfo;
import com.english.phone.R;

public class UnknownWordActivity extends Activity{
	private ListView unknownListView = null;
	private List<WordInfo> uWordsList = null;
	private EnglishDatabaseHelper eHelper = null;
	private EnglishDBOperate eOperate = null;
	private UnknownWordsAdapter uAdapter = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.unknown_words_layout);
		
		initData();
		initView();
		initActionBar();
	}

	private void initActionBar() {
		ActionBar mainActionBar = getActionBar();
		mainActionBar.show();
		
	}

	private void initView() {
		unknownListView = (ListView) super.findViewById(R.id.unkonwn_words_listview);
		UnknownWordsAdapter uAdapter = new UnknownWordsAdapter(this, uWordsList);
		unknownListView.setAdapter(uAdapter);
		
		unknownListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				WordInfo mWordInfo = uWordsList.get(position);
				Intent it = new Intent(UnknownWordActivity.this,UnknownWordDetailActivity.class);
				it.putExtra("wordinfo", mWordInfo);
				startActivity(it);
			}
		}); 
	}

	private void initData() {
		initDB();
		uWordsList = eOperate.getAllUnknownWords();
		
		if(uWordsList.size() == 0){
			Toast.makeText(UnknownWordActivity.this,"还没有加入生词哦 ！", Toast.LENGTH_SHORT).show();
			
		}
	}
	
	private void initDB(){
		eHelper = new EnglishDatabaseHelper(this);
		eOperate = new EnglishDBOperate(eHelper.getReadableDatabase(), this);
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case R.id.actionbar_search:
			Intent it = new Intent(UnknownWordActivity.this,SearchActivity.class);
			startActivity(it);
			break; 
		case R.id.actionbar_review: //此处不应是review
			
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
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		switch(keyCode){
		case KeyEvent.KEYCODE_BACK:
			UnknownWordActivity.this.finish();
			break;
		}
		return super.onKeyUp(keyCode, event);
	}
	
	
	@Override
	protected void onRestart() {
		refreshData();
		super.onRestart();
	}

	private void refreshData() {
		initData();
		uAdapter = new UnknownWordsAdapter(UnknownWordActivity.this, uWordsList);
		unknownListView.setAdapter(uAdapter);
	}
	
	
	
}
