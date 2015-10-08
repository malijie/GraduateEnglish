package com.english.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.english.phone.R;

public class WordLessonAdapter extends BaseAdapter{
	
	private int mLessonSize;
	private List<String> mVisitDateList = null;
	private List<Map<String,String>> mAbstractList = null;
	private Context mContext = null;
	private List<Integer> mAccuracyList = null;
	
	public WordLessonAdapter(Context context, int lessonSize, List<String> listDate, 
							List<Map<String, String>> abList, List<Integer> acList){
		this.mLessonSize = lessonSize;
		this.mVisitDateList = listDate;
		this.mAbstractList = abList;
		this.mAccuracyList = acList;
		mContext = context;
	}

	@Override
	public int getCount() {
		return mLessonSize;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if(convertView == null){  
			holder = new ViewHolder(); 
			LayoutInflater layoutInflater = LayoutInflater.from(mContext);
			convertView = layoutInflater.inflate(R.layout.words_list_item, null);
			holder.textLesson = (TextView) convertView.findViewById(R.id.list_item_text_lesson);
			holder.textAccuracy = (TextView) convertView.findViewById(R.id.list_item_text_accuracy);
			holder.textDate = (TextView) convertView.findViewById(R.id.list_item_text_date);
			holder.textAbastract = (TextView) convertView.findViewById(R.id.list_item_text_abstract);
			convertView.setTag(holder);
			    
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
	
 		
		holder.textLesson.setText("Lesson " + (position+1)); 
		holder.textAbastract.setText(Html.fromHtml(mAbstractList.get(position).get("word") + " "
									+ mAbstractList.get(position).get("symbols") + "<br>" 
									+ mAbstractList.get(position).get("content") + "<br>")
									+ mAbstractList.get(position).get("example").replace("<br>", " ").replace("<FONT color=red>", " ").replace("</FONT>", " "));
		holder.textDate.setText(mVisitDateList.get(position)!=null && !mVisitDateList.get(position).equals("") ? mVisitDateList.get(position):"还未学习本课内容");

		holder.textAccuracy.setText(position==55 ? "正确率:" + (mAccuracyList.get(position)/57) * 100 + "%" : "正确率:" + mAccuracyList.get(position) + "%");
		return convertView; 
		 
	} 

	private static class ViewHolder{ 
		public TextView textLesson = null;
		public TextView textAccuracy = null; 
		public TextView textDate = null;
		public TextView textAbastract = null;
	}
	
}
