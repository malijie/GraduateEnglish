package com.english.activity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.english.English;
import com.english.ad.AdUtil;
import com.english.database.EnglishDBOperate;
import com.english.database.EnglishDatabaseHelper;
import com.english.inter.IDialogOnClickListener;
import com.english.media.EnglishMediaPlayer;
import com.english.model.WordInfo;
import com.english.phone.R;
import com.english.util.Logger;
import com.english.util.SharedPreferenceUtil;
import com.english.util.Util;

public class WordsDetailActivity extends Activity implements OnClickListener{
	private static final String TAG = WordsDetailActivity.class.getSimpleName();

	private TextView txtWord = null;
	private TextView txtSymbols = null;  
	private TextView txtContent = null;
	private TextView txtExample1 = null;
	private TextView txtExample2 = null;
	private TextView txtExample = null;
	private TextView txtProgress = null;
	 
	private LinearLayout layoutContent = null;
	private RelativeLayout layoutExample1 = null;  
	private RelativeLayout layoutExample2 = null;
	private LinearLayout layoutAd = null;
	private LinearLayout layoutRightOrWrong = null;
	private LinearLayout layoutKnownOrNot = null; 
	
	private Button butNext = null;
	private Button butKnown = null;
	private Button butNotKnown = null;
	private Button butRight = null;
	private Button butWrong = null;
	//���ʲ��Ű�ť
	private ImageButton butSound = null;
	//��ͷ��ʼѧϰ��ť
	private ImageButton butGoHead = null;
	//��ǰ�γ̵��ʽ���
	private int progress;
	//��ǰ�γ̺�
	private int lessonNum;
	private List<WordInfo> lessonWords = null; 
	private WordInfo mWordInfo = null;
	private static SimpleDateFormat sDateFormat = null;
	
	private EnglishDatabaseHelper eHelper = null;
	private EnglishDBOperate eOperate = null;
	//������Ƶ���ſؼ�
	private EnglishMediaPlayer mEnglishMediaPlayer = null;
	
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.words_detail_layout);

		initData();
		initDatabase();
		initActionBar();
		initView();
		showWordsKnownOrNotUI(); //��ʾ��ʶ������ʶ���ʽ���
		setWordData(progress);
		
	}
	
	
	private void initDatabase() {
		eHelper = new EnglishDatabaseHelper(this);
		eOperate = new EnglishDBOperate(eHelper.getWritableDatabase(),this);
	}

	private void initActionBar() {
		ActionBar mainActionBar = getActionBar();
		mainActionBar.show();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.actionbar_item, menu);
		return super.onCreateOptionsMenu(menu);
	}

	private void initData() {

		lessonNum = getIntent().getIntExtra("lesson_num", 0);
		lessonWords = (List<WordInfo>) getIntent().getSerializableExtra("lesson_words");
		//��ȡ��ǰ�γ̽���
		progress = SharedPreferenceUtil.loadLessonProgress(WordsDetailActivity.this,lessonNum);
		sDateFormat = getSimpleDateFormatInstance();
		//��ʼ����Ƶ����
		mEnglishMediaPlayer = EnglishMediaPlayer.getInstance(this);
	}
	
	private SimpleDateFormat getSimpleDateFormatInstance(){
		if(sDateFormat == null){
			sDateFormat = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
		}
		return sDateFormat;
	}
	
	private void initView() {
		txtWord = (TextView) super.findViewById(R.id.word_detail_word);
		txtSymbols = (TextView) super.findViewById(R.id.word_detail_symbol);
		txtContent = (TextView) super.findViewById(R.id.word_detail_content);
		txtExample1 = (TextView) super.findViewById(R.id.word_detail_example1);
		txtExample2 = (TextView) super.findViewById(R.id.word_detail_example2);
		txtExample = (TextView) super.findViewById(R.id.word_detail_text_example);
		txtProgress = (TextView) super.findViewById(R.id.word_detail_text_progress);
		
		layoutContent = (LinearLayout) super.findViewById(R.id.word_detail_content_layout);
		layoutExample1 = (RelativeLayout) super.findViewById(R.id.word_detail_example1_layout);
		layoutExample2 = (RelativeLayout) super.findViewById(R.id.word_detail_example2_layout);
//		layoutAd = (LinearLayout) super.findViewById(R.id.word_detail_ad_layout);
		layoutRightOrWrong = (LinearLayout) super.findViewById(R.id.word_detail_layout_rightorwrong);
		layoutKnownOrNot = (LinearLayout) super.findViewById(R.id.word_detail_layout_knownornot);
		
		butNext = (Button) super.findViewById(R.id.word_detail_button_next);
		butKnown = (Button) super.findViewById(R.id.word_detail_button_known);
		butNotKnown = (Button) super.findViewById(R.id.word_detail_button_notknown);
		butRight = (Button) super.findViewById(R.id.word_detail_button_right);
		butWrong = (Button) super.findViewById(R.id.word_detail_button_wrong);
		butSound = (ImageButton) super.findViewById(R.id.word_detail_button_volume);
		butGoHead = (ImageButton) super.findViewById(R.id.word_detail_button_gohaed);
		
		butNext.setOnClickListener(this);
		txtExample1.setOnClickListener(this);
		txtExample2.setOnClickListener(this); 
		butKnown.setOnClickListener(this);
		butNotKnown.setOnClickListener(this);
		butRight.setOnClickListener(this);
		butWrong.setOnClickListener(this);
		butSound.setOnClickListener(this);
		butGoHead.setOnClickListener(this);
		
		SharedPreferenceUtil spUtil = new SharedPreferenceUtil(this);
		txtWord.setTextSize(spUtil.getFontSize("word_size"));
		
//		AdUtil.showMiniAd(this, layoutAd,30);

	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
			//��һ����ť
		case R.id.word_detail_button_next:
			showWordsKnownOrNotUI();
			showNextWord();
			break;
		case R.id.word_detail_example1:
			showExampleDetail();
			break;
		case R.id.word_detail_example2:
			showExampleDetail();
			break;
		case R.id.word_detail_button_known:
			//���浱ǰ����
			saveCurrentProgress();
			//��ʾ��ȷ/����UI����
			showRightOrWrongUI();
			break;
		case R.id.word_detail_button_notknown:
			//���浱ǰ����
			saveCurrentProgress();
			//���²���ʶ���ʱ�
			eOperate.updateWordIsKnownById(false, mWordInfo.getId());
			showNextWordUI();
			setWordData(progress);
			break;
		case R.id.word_detail_button_right:
			eOperate.updateWordIsKnownById(true, mWordInfo.getId());
			showWordsKnownOrNotUI();
			showNextWord();
			break;
		case R.id.word_detail_button_wrong: 
			eOperate.updateWordIsKnownById(false, mWordInfo.getId());
			showWordsKnownOrNotUI(); 
			showNextWord(); 
			break;
		case R.id.word_detail_button_volume:
			//���ŵ�����Ƶ
			mEnglishMediaPlayer.playTheWordTune(mWordInfo.getWord());
			break;
		case R.id.word_detail_add:
			
			break;
		case R.id.word_detail_button_gohaed:
			//��ͷ��ʼѧϰ
			Util.showAlertDialog(WordsDetailActivity.this,
					"��ͷ��ʼѧϰ", "��ǰѧϰ���Ƚ����ᱣ�棬ȷ����ô����", new IDialogOnClickListener() {
				@Override
				public void onClick() {
					//��ǰ�γ���ȷ����0
					eOperate.resumeAccuracyCount(lessonNum);
					//����ǰ�γ̵Ľ�����Ϊ0
					SharedPreferenceUtil.saveLessonProgress(English.mContext,lessonNum,0);
					//�رյ�ǰActivity
					WordsDetailActivity.this.finish();
				}
			});

			break;
		}
	}
	
	private void showNextWord(){
		progress ++;
		setWordData(progress);
	}
	
	/**
	 * ��ʾ����ʶ����UI
	 */
	private void showNextWordUI() {
		layoutRightOrWrong.setVisibility(View.GONE);
		layoutKnownOrNot.setVisibility(View.VISIBLE);
		layoutContent.setVisibility(View.VISIBLE);
		txtExample.setVisibility(View.VISIBLE);
		layoutExample1.setVisibility(View.VISIBLE);
		layoutExample2.setVisibility(View.VISIBLE);
		txtExample.setVisibility(View.INVISIBLE);
		butNext.setVisibility(View.VISIBLE);
		butKnown.setVisibility(View.GONE);
		butNotKnown.setVisibility(View.GONE);
	}

	/**
	 * ��ʾ"��ʶ/����ʶ"�����UI
	 */
	private void showWordsKnownOrNotUI(){
		layoutRightOrWrong.setVisibility(View.GONE);
		layoutKnownOrNot.setVisibility(View.VISIBLE);
		layoutContent.setVisibility(View.INVISIBLE);
		layoutExample1.setVisibility(View.INVISIBLE);
		layoutExample2.setVisibility(View.INVISIBLE);
		txtExample.setVisibility(View.INVISIBLE);
		butNext.setVisibility(View.GONE);
		butKnown.setVisibility(View.VISIBLE);
		butNotKnown.setVisibility(View.VISIBLE);
	}
	
	/**
	 * ��ʾ����ȷ/���󡱵��ʽ���
	 */
	private void showRightOrWrongUI(){
		layoutKnownOrNot.setVisibility(View.GONE);
		butNext.setVisibility(View.GONE);
		layoutRightOrWrong.setVisibility(View.VISIBLE);
		layoutContent.setVisibility(View.VISIBLE);
		txtExample.setVisibility(View.VISIBLE);
		layoutExample1.setVisibility(View.VISIBLE);
		layoutExample2.setVisibility(View.VISIBLE);
		 
	}
	
	private void showExampleDetail(){
		Intent it = new Intent(WordsDetailActivity.this, WordExampleDetailActivity.class);
		it.putExtra("id", lessonWords.get(progress).getId());
		it.putExtra("symbols", lessonWords.get(progress).getSymbols());
		it.putExtra("word", lessonWords.get(progress).getWord());
		it.putExtra("content", lessonWords.get(progress).getContent());
		it.putExtra("example", lessonWords.get(progress).getExample());
		startActivity(it);
	}
	
	/**
	 * Ϊ�ؼ���������
	 * @param mIndex
	 */
	private void setWordData(int mIndex){
		if(mIndex > lessonWords.size()-1){
			Toast.makeText(WordsDetailActivity.this, "���ε�����ѧϰ��������ѡ����һ�Ρ�", Toast.LENGTH_SHORT).show();
			return;
		}
		mWordInfo = lessonWords.get(mIndex);
		txtSymbols.setText(lessonWords.get(mIndex).getSymbols());
		txtWord.setText(lessonWords.get(mIndex).getWord());
		txtContent.setText(lessonWords.get(mIndex).getContent());
		txtProgress.setText("Lesson " + (lessonNum + 1) + "  (" + (progress + 1) + "/" + lessonWords.size() + ")");
		setExampleData(lessonWords.get(mIndex).getExample());
		updateLastVisitDate(mWordInfo.getId());
	}
	
	private void updateLastVisitDate(int id) {
		String date = sDateFormat.format(new Date());
		eOperate.updateLastVisitDateById(date, id);
	}


	/**
	 * �����ݿ��ж���example���ݲ����д���
	 */
	private void setExampleData(String example){
	   String examples[] = example.split("2.");
	   if(examples.length <= 1){
		   txtExample1.setText(Html.fromHtml(examples[0]));
		   return;
	   } 
	   txtExample1.setText(Html.fromHtml(examples[0]));
	   txtExample2.setText(Html.fromHtml("2." + examples[1]));
	}
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch(keyCode){
		case KeyEvent.KEYCODE_BACK:
//			ExitDialog eDialog = new ExitDialog(WordsDetailActivity.this, eHelper);
//			eDialog.show();

			break;
		}
		
		return super.onKeyDown(keyCode, event);
	}

	public void closeDB(EnglishDatabaseHelper eHelper){
		if(eHelper != null){
			eHelper.close();
			eHelper = null;
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case R.id.actionbar_search:
			Intent it1 = new Intent(WordsDetailActivity.this, SearchActivity.class); 
			startActivity(it1); 
			break; 
		case R.id.actionbar_review: 
			Intent it = new Intent(WordsDetailActivity.this, UnknownWordActivity.class); 
			startActivity(it);
			break;
//		case R.id.actionbar_setting:
//			setTabSelection(Profile.BOTTOM_SELECT_SETTING);
//			break;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * ���浱ǰ����
	 */
	private void saveCurrentProgress(){
		SharedPreferenceUtil.saveLessonProgress(this,lessonNum,progress);
	}

	@Override
	protected void onStop() {
		super.onStop();
		//ֹͣ������Ƶ
		mEnglishMediaPlayer.stopPlay();
	}
}
