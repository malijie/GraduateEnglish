package com.english.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.english.model.WrittingInfo;
import com.english.phone.R;

public class WrittingAdapter extends BaseAdapter{
	private List<WrittingInfo> mWrittingInfos = null;
	private Context mContext = null;
	private LayoutInflater inflater = null;
	public WrittingAdapter(Context context, List<WrittingInfo> writtingInfos){
		this.mContext = context;
		this.mWrittingInfos = writtingInfos;
	}
	
	@Override
	public int getCount() {
		 
		return mWrittingInfos.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mWrittingInfos.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return mWrittingInfos.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if(convertView == null){
			viewHolder = new ViewHolder();
			inflater = LayoutInflater.from(mContext);
			convertView = inflater.inflate(R.layout.writting_list_item, null);
			viewHolder.imageIcon = (ImageView) convertView.findViewById(R.id.writting_list_item_image_icon);
			viewHolder.textDate = (TextView) convertView.findViewById(R.id.writting_list_item_text_date);
			viewHolder.texttTitle = (TextView) convertView.findViewById(R.id.writting_list_item_text_title);
			viewHolder.textContent = (TextView) convertView.findViewById(R.id.writting_list_item_text_content);
			viewHolder.textPart = (TextView) convertView.findViewById(R.id.writting_list_item_text_part);
			convertView.setTag(viewHolder);
			
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		//viewHolder.imageIcon.setImageResource(R.drawable.)
		viewHolder.textDate.setText(mWrittingInfos.get(position).getDate() + "/01");
		viewHolder.texttTitle.setText(mWrittingInfos.get(position).getDate()+"年考研英语写作真题 ");
		viewHolder.textContent.setText(mWrittingInfos.get(position).getQuestion()); 
		viewHolder.textPart.setText(mWrittingInfos.get(position).getTitle());
		return convertView; 
	}
	 
	private static class ViewHolder{
		private ImageView imageIcon;
		private TextView textDate;
		private TextView texttTitle;
		private TextView textContent;
		private TextView textPart;
	}
	
}
