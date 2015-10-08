package com.english.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.english.phone.R;

public class HelpAndTipActivity extends Activity{
	private TextView textHelp = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.help_or_tip_layout);
		   
		textHelp = (TextView) super.findViewById(R.id.text_help);
		String strHelp = "<P>����һ��ר��Ϊ����ѧ���������Ŀ���Ӣ��APP������Ҫ���ܰ�����</P>"
+ "<P>1. ���ʡ� �ṩ��������Ӣ���������е��ʣ���������һʧ��</P>"
+ "<P>2. �Ķ��� �����˴�1994�굽2014���������⣬����������𰸣�������ʱ���Ҳ�ܸ�ϰ�������⣡</P>"
+ "<P>3. д���� ������1994�굽2014������д�����⣬���ܴ����Ļ���С���ģ�����ͳͳ����һ���򾡣������и߷ֱ�׼���ģ�����д�����ٵ��ģ�</P>"
+ "<P>4. ���ʱ�����������ѧ������������ʶ���߼������ĵ���ͳһ���ൽ���ʱ��У��������ĸ�ϰЧ�ʣ�ǿ�����ļ��䣡����������Ϊ���ʵ��ġ�</P>"
+ "<P>5. �����ʡ������ʿ��ṩ��ȫ�����£�����ȷ�ĵ��ʽ��ͣ����������������</P>"
+ "<P align=center>Copyright malijie </P>"
+ "<P>All Rights Reserved.  </P>"
+ "<P>��ϵ��ʽ��190223629@qq.com </P>";
		textHelp.setText(Html.fromHtml(strHelp));
		textHelp.setMovementMethod(ScrollingMovementMethod.getInstance());
	}
	
}
