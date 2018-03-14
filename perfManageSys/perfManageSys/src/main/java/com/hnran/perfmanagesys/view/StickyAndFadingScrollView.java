package com.hnran.perfmanagesys.view;

import java.util.LinkedList;
import java.util.List;

import com.hnran.perfmanagesys.R;
import com.hnran.perfmanagesys.view.FadingScrollView.ScrollChangeChildListener;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

/**
 * 结合StickyScro 和 FadingScrollView
 * 2017-5-19
 *
 */
public class StickyAndFadingScrollView extends ScrollView {
	private static final String STICKY = "sticky";
	private static final String NO_STICKY = "NoStiChild";
	private View mCurrentStickyView ;
	private Drawable mShadowDrawable;
	private List<View> mStickyViews;
	private int mStickyViewTopOffset;
	private int defaultShadowHeight = 10;
	private float density;
	private boolean redirectTouchToStickyView;

	/**
	 * 
	 */
	//渐变view
	private View fadingView;
	//滑动view的高度，如果这里fadingHeightView是一张图片，
	// 那么就是这张图片上滑至完全消失时action bar 完全显示，
	// 过程中透明度不断增加，直至完全显示
	private View fadingHeightView;
	private int oldY;
	//滑动距离，默认设置滑动500 时完全显示，根据实际需求自己设置
	private int fadingHeight = 300;


	/**
	 * 当点击Sticky的时候，实现某些背景的渐变
	 */
	private Runnable mInvalidataRunnable = new Runnable() {

		@Override
		public void run() {
			if(mCurrentStickyView != null){
				int left = mCurrentStickyView.getLeft();
				int top = mCurrentStickyView.getTop();
				int right = mCurrentStickyView.getRight();
				int bottom = getScrollY() + (mCurrentStickyView.getHeight() + mStickyViewTopOffset);

				invalidate(left, top, right, bottom);
			}

			postDelayed(this, 16);


		}
	};
	
	public StickyAndFadingScrollView(Context context) {
		super(context);
	}


	public StickyAndFadingScrollView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public StickyAndFadingScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mShadowDrawable = context.getResources().getDrawable(R.drawable.sticky_shadow_default);
		mStickyViews = new LinkedList<View>();
		density = context.getResources().getDisplayMetrics().density;
	}


	public void setFadingView(View view){this.fadingView = view;}
	public void setFadingHeightView(View v){this.fadingHeightView = v;}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		if(fadingHeightView != null)
			fadingHeight = fadingHeightView.getMeasuredHeight() * 2 / 3;
	}



	/**
	 * 找到设置tag的View
	 * @param viewGroup
	 */
	private void findViewByStickyTag(ViewGroup viewGroup){
		int childCount = ((ViewGroup)viewGroup).getChildCount();
		for(int i=0; i<childCount; i++){
			View child = viewGroup.getChildAt(i);

			String tag = getStringTagForView(child);
			
			if(tag.contains(STICKY)){
				mStickyViews.add(child);
			}

			if(child instanceof ViewGroup && !tag.contains(NO_STICKY)){
				findViewByStickyTag((ViewGroup)child);
			}
		}

	}



	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		if(changed){
			findViewByStickyTag((ViewGroup)getChildAt(0));
		}
		showStickyView();
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);

		/**
		 * 
		 */
		float fading = t>fadingHeight ? fadingHeight : (t > fadingHeight * 2 / 3 ? t : 0);
		updateActionBarAlpha( fading / fadingHeight);
		scrollChangeChildListener.onScorllChangeChild(l, t, oldl, oldt);


		/**
		 * 
		 */
		showStickyView();

	}


	void updateActionBarAlpha(float alpha){
		try {
			setActionBarAlpha(alpha);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setActionBarAlpha(float alpha) throws Exception{
		if(fadingView==null){
			throw new Exception("fadingView is null...");
		}
		fadingView.setAlpha(alpha);
	}

	/**
	 * 定义回调接口，当ScrollView滑动到某个子控件时，外边导航栏切换到相应位置
	 * @author tan
	 *
	 */
	public interface ScrollChangeChildListener{
		public void onScorllChangeChild(int l, int t, int oldl, int oldt);
	}
	private ScrollChangeChildListener scrollChangeChildListener = null;
	public void setScrollChangeChildListener(ScrollChangeChildListener scrollChangeChildListener){
		this.scrollChangeChildListener = scrollChangeChildListener;
	}



	/**
	 * 
	 */
	private void showStickyView(){
		View curStickyView = null;
		View nextStickyView = null;

		for(View v : mStickyViews){
			int topOffset = v.getTop() - getScrollY();

			if(topOffset <= 0){
				if(curStickyView == null || topOffset > curStickyView.getTop() - getScrollY()){
					curStickyView = v;
				}
			}else{
				if(nextStickyView == null || topOffset < nextStickyView.getTop() - getScrollY()){
					nextStickyView = v;
				}
			}
		}

		if(curStickyView != null){
			mStickyViewTopOffset = nextStickyView == null ? 0 : Math.min(0, nextStickyView.getTop() - getScrollY() - curStickyView.getHeight());
			mCurrentStickyView = curStickyView;
			post(mInvalidataRunnable);
		}else{
			mCurrentStickyView = null;
			removeCallbacks(mInvalidataRunnable);

		}

	}


	private String getStringTagForView(View v){
		Object tag = v.getTag();
		return String.valueOf(tag);
	}


	/**
	 * 将sticky画出来
	 */
	@Override
	protected void dispatchDraw(Canvas canvas) {
		super.dispatchDraw(canvas);
		if(mCurrentStickyView != null){
			//先保存起来
			canvas.save();
			//将坐标原点移动到(0, getScrollY() + mStickyViewTopOffset)
			canvas.translate(0, getScrollY() + mStickyViewTopOffset);

			if(mShadowDrawable != null){
				int left = 0;
				int top = mCurrentStickyView.getHeight() + mStickyViewTopOffset;
				int right = mCurrentStickyView.getWidth();
				int bottom = top + (int)(density * defaultShadowHeight + 0.5f);
				mShadowDrawable.setBounds(left, top, right, bottom);
				mShadowDrawable.draw(canvas);
			}


			canvas.clipRect(0, mStickyViewTopOffset, mCurrentStickyView.getWidth(), mCurrentStickyView.getHeight());

			mCurrentStickyView.draw(canvas);

			//重置坐标原点参数
			canvas.restore();
		}
	}


	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if(ev.getAction() == MotionEvent.ACTION_DOWN){
			redirectTouchToStickyView = true;
		}

		if(redirectTouchToStickyView){
			redirectTouchToStickyView = mCurrentStickyView != null;

			if(redirectTouchToStickyView){
				redirectTouchToStickyView = ev.getY() <= (mCurrentStickyView
						.getHeight() + mStickyViewTopOffset)
						&& ev.getX() >= mCurrentStickyView.getLeft()
						&& ev.getX() <= mCurrentStickyView.getRight();
			}
		}

		if (redirectTouchToStickyView) {
			ev.offsetLocation(0, -1 * ((getScrollY() + mStickyViewTopOffset) - mCurrentStickyView.getTop()));
		}
		return super.dispatchTouchEvent(ev);
	}



	private boolean hasNotDoneActionDown = true;

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if(redirectTouchToStickyView){
			ev.offsetLocation(0, ((getScrollY() + mStickyViewTopOffset) - mCurrentStickyView.getTop()));
		} 

		if(ev.getAction()==MotionEvent.ACTION_DOWN){
			hasNotDoneActionDown = false;
		}

		if(hasNotDoneActionDown){
			MotionEvent down = MotionEvent.obtain(ev);
			down.setAction(MotionEvent.ACTION_DOWN);
			super.onTouchEvent(down);
			hasNotDoneActionDown = false;
		}

		if(ev.getAction()==MotionEvent.ACTION_UP || ev.getAction()==MotionEvent.ACTION_CANCEL){
			hasNotDoneActionDown = true;
		}
		return super.onTouchEvent(ev);
	}

}
