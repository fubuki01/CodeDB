package com.hnran.perfmanagesys.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.Scroller;

public class ScrollerLayout extends RelativeLayout{
	
	private Scroller mScroller;
	
	public ScrollerLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}

	public ScrollerLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init();
	}
	
	@SuppressLint("NewApi")
	public ScrollerLayout(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		init();
	}
	
	@SuppressLint("NewApi")
	public ScrollerLayout(Context context, AttributeSet attrs,
			int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		// TODO Auto-generated constructor stub
		init();
	}

	
	private void init() {
        if (mScroller == null) {
            mScroller = new Scroller(getContext());
        }
    }
	
	/**
     * 滑动
     * @param dx
     * @param dy
     */
    public void smoothScrollBy(int dx, int dy) {
    	
        mScroller.startScroll(getScrollX(), getScrollY(), dx, dy, 400);
        invalidate();
    }
    
    public void smoothScrollBy(int dx, int dy, int t) {
    	
        mScroller.startScroll(getScrollX(), getScrollY(), dx, dy, t);
        invalidate();
    }

    @Override
    public void computeScroll() {
    	super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }
}
