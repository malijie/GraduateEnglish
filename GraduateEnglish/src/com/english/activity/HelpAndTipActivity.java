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
		String strHelp = "<P>这是一款专门为考研学子量身定做的考研英语APP。其主要功能包括：</P>"
+ "<P>1. 单词。 提供包含考研英语大纲内所有单词，让您万无一失！</P>"
+ "<P>2. 阅读。 囊括了从1994年到2014年所有真题，并附有真题答案，让您随时随地也能复习巩固真题！</P>"
+ "<P>3. 写作。 包含从1994年到2014年所有写作真题，不管大作文还是小作文，我们统统给您一网打尽！并附有高分标准范文，让您写作不再担心！</P>"
+ "<P>4. 生词本。将您在所学过程中所不认识或者记忆错误的单词统一归类到生词本中，提升您的复习效率，强化您的记忆！让您不必再为生词担心。</P>"
+ "<P>5. 查生词。海量词库提供最全，最新，最正确的单词解释，并配有例句解析。</P>"
+ "<P align=center>Copyright malijie </P>"
+ "<P>All Rights Reserved.  </P>"
+ "<P>联系方式：190223629@qq.com </P>";
		textHelp.setText(Html.fromHtml(strHelp));
		textHelp.setMovementMethod(ScrollingMovementMethod.getInstance());
	}
	
}
