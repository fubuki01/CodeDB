package com.hnran.perfmanagesys.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;
public class LabelTextView extends TextView {
	public LabelTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	@Override
	protected void onTextChanged(CharSequence text, int start, int before,
			int after) {
		super.onTextChanged(text, start, before, after);
		int len = text.toString().length();
		if( len <= 2){
			this.setTextSize(TypedValue.COMPLEX_UNIT_DIP , 12);
		}else if(len > 2){
			this.setTextSize(TypedValue.COMPLEX_UNIT_DIP , 9);
		}
	}
	
}
