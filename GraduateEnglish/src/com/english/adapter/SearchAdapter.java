package com.english.adapter;

import java.util.List;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.english.model.WordInfo;
import com.english.phone.R;

public class SearchAdapter extends BaseAdapter{
	private Context mContext = null;
	private List<WordInfo> mWordInfo = null;
	private LayoutInflater inflater = null;
	public SearchAdapter(Context context, List<WordInfo> wordInfos){
		this.mContext = context;
		this.mWordInfo = wordInfos;
	}
	
	@Override
	public int getCount() {
		return mWordInfo.size();
	}
	@Override
	public Object getItem(int position) {
		return mWordInfo.get(position);
	}
	@Override
	public long getItemId(int position) {
		return mWordInfo.get(position).getId();
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if(convertView == null){
			viewHolder = new ViewHolder();
			inflater = LayoutInflater.from(mContext);
			convertView = inflater.inflate(R.layout.search_list_item, null);
			viewHolder.textWord = (TextView) convertView.findViewById(R.id.search_item_text_word);
			viewHolder.textSymbols = (TextView) convertView.findViewById(R.id.search_item_text_symbols);
			viewHolder.textExample = (TextView) convertView.findViewById(R.id.search_item_text_example);
			viewHolder.textContent = (TextView) convertView.findViewById(R.id.search_item_text_content);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		} 
		viewHolder.textWord.setText(mWordInfo.get(position).getWord());
		viewHolder.textSymbols.setText(mWordInfo.get(position).getSymbols());
		viewHolder.textExample.setText(Html.fromHtml(mWordInfo.get(position).getExample().toString()));
		viewHolder.textContent.setText(mWordInfo.get(position).getContent());
		return convertView;
	}
	

	private static class ViewHolder{
		private TextView textWord;
		private TextView textSymbols;
		private TextView textContent;
		private TextView textExample;
	}

}
