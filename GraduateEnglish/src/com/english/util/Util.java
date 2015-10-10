package com.english.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.english.English;
import com.english.inter.IDialogOnClickListener;
import com.english.phone.R;
import com.wanpu.pay.PayConnect;

/**
 * 工具类,完成相关文件的操作 
 * @author malijie
 *
 */
public class Util {
	private final static String DATABASEPATH = "/data/data/com.english.phone/databases/";
	private final static String DATABASENAME = "english.db";

	 
	public static boolean isDBExist(){
		File dbFile = new File(DATABASEPATH + DATABASENAME);
		if(!dbFile.exists()){
			dbFile.getParentFile().mkdirs();
		}
		return true;
	}
	
	/**
	 * 将assets里的数据库文件拷贝到系统中
	 */
	public static void copyDB2Phone(Context context){
		String dbFileName = DATABASEPATH + DATABASENAME;
    	File dbFile = new File(dbFileName);
    	InputStream is = null;
    	FileOutputStream os = null;
  
    	if(!dbFile.exists()){//判断文件夹是否存在，不存在就新建一个
    	Toast.makeText(context, "首次启动，数据加载中...", Toast.LENGTH_LONG).show();
    		dbFile.getParentFile().mkdirs();
	    	try{
	    		os = new FileOutputStream(dbFileName);//得到数据库文件的写入流
	    		is = context.getResources().getAssets().open(DATABASENAME);//得到数据库文件的数据流
	    		  byte[] buffer = new byte[1024];
	    	        int count = 0; 
	    	        	while((count=is.read(buffer))>0){
	    	        		os.write(buffer, 0, count);
	    	        		os.flush(); 
	    	        	}
	    	}catch(FileNotFoundException e){
	    		e.printStackTrace();
	    	} catch (IOException e) {
				e.printStackTrace();
			}finally{   
	    		try { 
					is.close();
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				} 
			}
    	}
   }

	/**
	 * 从AndroidManifest.xml中读取mete-data的数据
	 * @return
	 */
	public static String getAppID(){
		return "469378b902a681c98f5a095ddccf8223";
	}

	/**
	 * 生产订单号
	 * @return
	 */
	public static String generateOrderId(){
		return String.valueOf(System.currentTimeMillis());
	}

	/**
	 * 生产用户id
	 * @return
	 */
	public static String generateUserId(){
		return PayConnect.getInstance(English.mContext)
				         .getDeviceId(English.mContext);
	}

	/**
	 * 根据资源文件获取view
	 * @param res
	 * @return
	 */
	public static View getView(int res){
		LayoutInflater inflater = LayoutInflater.from(English.mContext);
		View v = inflater.inflate(res,null);
		return  v;
	}

	/**
	 * 弹出对话框
	 * @param title	对话框标题
	 * @param msg 对话框内容
	 * @param listener 点击确定事件
	 */
	public static void showAlertDialog(Context context,String title, String msg, IDialogOnClickListener listener){
		AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.DialogTheme);

		View v = Util.getView(R.layout.alert_dialog_layout);
		builder.setView(v);

		Button buttonConfirm = (Button) v.findViewById(R.id.dialog_button_confirm);
		Button buttonCancel = (Button) v.findViewById(R.id.dialog_button_cancel);
		TextView textTitle = (TextView) v.findViewById(R.id.dialog_text_title);
		TextView textMsg = (TextView) v.findViewById(R.id.dialog_text_content);

		textTitle.setText(title);
		textMsg.setText(msg);

		AlertDialog dialog = builder.create();
		//退出
		buttonConfirm.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if(listener != null){
					listener.onClick();
					dialog.dismiss();
				}
			}
		});
		//取消
		buttonCancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				dialog.dismiss();
			}
		});
		dialog.show();
	}

}
