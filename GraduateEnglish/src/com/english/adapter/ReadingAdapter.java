package com.english.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.english.model.ReadingInfo;
import com.english.pay.PayManager;
import com.english.phone.R;

public class ReadingAdapter extends BaseAdapter{

	private List<List<ReadingInfo>> mAllReadingInfoList = null;
	private LayoutInflater inflater = null;
	private Context mContext = null;
	
	public ReadingAdapter(Context context, List<List<ReadingInfo>> allReadingInfoList){
		mContext = context;
		mAllReadingInfoList = allReadingInfoList;
	}
	
	@Override
	public int getCount() {
		return mAllReadingInfoList.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView == null){
			holder = new ViewHolder();
			inflater = LayoutInflater.from(mContext);
			convertView = inflater.inflate(R.layout.reading_list_item, null);
			holder.image_icon = (ImageView) convertView.findViewById(R.id.reading_list_item_image_icon);
			holder.image_lock = (ImageView) convertView.findViewById(R.id.reading_list_item_image_lock);
			holder.text_date = (TextView) convertView.findViewById(R.id.reading_list_item_text_date);
			holder.text_title = (TextView) convertView.findViewById(R.id.reading_list_item_text_title);
			holder.text_content = (TextView) convertView.findViewById(R.id.reading_list_item_text_content);

			convertView.setTag(holder);
		}else{ 
			holder = (ViewHolder) convertView.getTag();
		}

		//真题付费后设置解锁图标，否则显示上锁
//		if(position == 0){
//			holder.image_lock.setVisibility(View.VISIBLE);
//			if(PayManager.isCompleteReadingPay()){
//				holder.image_lock.setImageResource(R.drawable.lock_select);
//			}else{
//				holder.image_lock.setImageResource(R.drawable.lock_normal);
//			}
//
//		}

		holder.image_icon.setImageResource(R.drawable.listview_reading);
		holder.text_date.setText(mAllReadingInfoList.get(position).get(0).getDate() + "/01");
		holder.text_title.setText(mAllReadingInfoList.get(position).get(0).getDate() + "年考研英语阅读真题");
		holder.text_content.setText(mAllReadingInfoList.get(position).get(0).getContent().substring(0,300)
									.replace("<P>","").replace("</P>", "")
									.replace("<p>","").replace("</p>", ""));
		
		return convertView;
	}
	
	private static class ViewHolder{
		private ImageView image_icon = null;
		private ImageView image_lock = null;
		private TextView text_date = null;
		private TextView text_title = null;
		private TextView text_content = null;
	}
	
}
