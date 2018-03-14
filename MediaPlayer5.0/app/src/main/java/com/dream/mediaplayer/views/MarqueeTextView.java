package com.dream.mediaplayer.views;

import android.content.Context;
import android.graphics.Rect;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.TextView;

public class MarqueeTextView extends TextView {
	public MarqueeTextView(Context paramContext) {
		super(paramContext, null);
	}

	public MarqueeTextView(Context paramContext, AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
		setFocusable(true);
		setFocusableInTouchMode(true);
		setSingleLine();
		setEllipsize(TruncateAt.MARQUEE);
		setMarqueeRepeatLimit(-1);
		setLayerType(1, null);
	}

	public MarqueeTextView(Context paramContext,
			AttributeSet paramAttributeSet, int paramInt) {
		super(paramContext, paramAttributeSet, paramInt);
		setFocusable(true);
		setFocusableInTouchMode(true);
		setSingleLine();
		setEllipsize(TruncateAt.MARQUEE);
		setMarqueeRepeatLimit(-1);
		setLayerType(1, null);
	}

	public boolean isFocused() {
		return true;
	}

	protected void onFocusChanged(boolean paramBoolean, int paramInt,
			Rect paramRect) {
		if (paramBoolean)
			super.onFocusChanged(paramBoolean, paramInt, paramRect);
	}

	public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent) {
		return false;
	}

	public void onWindowFocusChanged(boolean paramBoolean) {
		if (paramBoolean)
			super.onWindowFocusChanged(paramBoolean);
	}
}
