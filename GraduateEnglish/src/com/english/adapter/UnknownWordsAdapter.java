package com.english.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.english.model.WordInfo;
import com.english.phone.R;

public class UnknownWordsAdapter extends BaseAdapter{

	private List<WordInfo> mWordsList = null;
	private LayoutInflater inflater = null;
	private Context mContext = null;
	
	public UnknownWordsAdapter(Context context, List<WordInfo> wordsList){
		this.mWordsList = wordsList;
		this.mContext = context;
	}
	
	@Override
	public int getCount() {
		return mWordsList.size();
	}

	@Override
	public Object getItem(int position) {
		return mWordsList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if(convertView == null){
			holder = new ViewHolder();
			inflater = LayoutInflater.from(mContext);
			convertView = inflater.inflate(R.layout.unknown_words_list_item, null);
			holder.textWord = (TextView) convertView.findViewById(R.id.unkonwn_words_item_text_word);
			holder.textSymbol = (TextView) convertView.findViewById(R.id.unkonwn_words_item_text_symbol);
			holder.textContent = (TextView) convertView.findViewById(R.id.unkonwn_words_item_text_content);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.textWord.setText(mWordsList.get(position).getWord());
		holder.textSymbol.setText(mWordsList.get(position).getSymbols());
		holder.textContent.setText(mWordsList.get(position).getContent());
		
		return convertView;
	}
	
	private static class ViewHolder{
		private TextView textWord = null;
		private TextView textSymbol = null;
		private TextView textContent = null;
	}

}
