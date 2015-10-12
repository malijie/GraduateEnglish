package com.english.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

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
	private static final String TAG = Util.class.getSimpleName();

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
	 * 检查是否已经解压过了
	 * @return
	 */
	private static boolean checkUnZipStatus(){
		return SharedPreferenceUtil.getWordsUnzipStatus(English.mContext);
	}

	/**
	 * 保存是否当前解压状态
	 * @param status
	 */
	public static void saveUnZipStatus(boolean status){
		SharedPreferenceUtil.saveUnzipWordsStatus(English.mContext,status);
	}

	/**
	 * 解压单词读音到sd卡
	 */
	public static void unZipTheWordsVoice2SdCard(Context context, String assetName,String outputDirectory){
		//已经解压过就不再解压了
		if(checkUnZipStatus()){
			return;
		}

		new Thread(new Runnable() {
			@Override
			public void run() {
				try{
					Logger.d(TAG, "start unzip words to sd card");
//					Process p = Runtime.getRuntime().exec("chmod 777 " +  outputDirectory );
//					int status = p.waitFor();
//					Logger.d(TAG,"create file status=" + status);

					//创建解压目标目录
					File file = new File(outputDirectory);
					//如果目标目录不存在，则创建
					if (!file.exists()) {
						file.mkdirs();
					}
					InputStream inputStream = null;
					//打开压缩文件
					inputStream = context.getAssets().open(assetName);

					ZipInputStream zipInputStream = new ZipInputStream(inputStream);

					//读取一个进入点
					ZipEntry zipEntry = zipInputStream.getNextEntry();

					//使用1M buffer
					byte[] buffer = new byte[1024 * 1024];
					//解压时字节计数
					int count = 0;

					//如果进入点为空说明已经遍历完所有压缩包中文件和目录
					while (zipEntry != null) {
						//如果是一个目录
						if (zipEntry.isDirectory()) {
							file = new File(outputDirectory + File.separator + zipEntry.getName());
							file.mkdir();
						} else {
							//如果是文件
							file = new File(outputDirectory + File.separator
									+ zipEntry.getName());
							//创建该文件
							file.createNewFile();
							FileOutputStream fileOutputStream = new FileOutputStream(file);
							while ((count = zipInputStream.read(buffer)) > 0) {
								fileOutputStream.write(buffer, 0, count);
							}
							fileOutputStream.close();
						}
						//定位到下一个文件入口
						zipEntry = zipInputStream.getNextEntry();
					}
					zipInputStream.close();
					Logger.d(TAG, "complete unzip words to sd card");
					//保存当前解压状态
					saveUnZipStatus(true);

				}catch(IOException e){
					e.printStackTrace();
				}

			}
		}).start();
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
