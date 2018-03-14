package com.hnran.perfmanagesys.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

/**
 * Created by ZHL on 2016/12/19.
 */

/**
 * 实现上方导航栏下拉出现，滑动到顶端消失
 * http://blog.csdn.net/u011343735/article/details/53761170
 * 2017-05-19
 *
 */

/**
 * 将透明度修改为动画，
 * @date 2017-08-09
 * @author Tan
 *
 */

public class FadingScrollView extends ScrollView {

    private static String TAG = "-----------FadingScrollView----------";
    //渐变view
    private View fadingView;
    //滑动view的高度，如果这里fadingHeightView是一张图片，
    // 那么就是这张图片上滑至完全消失时action bar 完全显示，
    // 过程中透明度不断增加，直至完全显示
//    private View fadingHeightView;
//    private int oldY;
    //滑动距离，默认设置滑动500 时完全显示，根据实际需求自己设置
//    private int fadingHeight = 300;
    
    //
    private int fadingViewState = GONE;
    
    private int firstChildHeight = 300;
    
    //标记第一次进来的时候
    private boolean isFirst = true;
    
    public FadingScrollView(Context context) {
        super(context);
        init();
    }

    public FadingScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FadingScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    
    private void init() {
    }

    public void setFadingView(View view){this.fadingView = view;}
//    public void setFadingHeightView(View v){this.fadingHeightView = v;}

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    	if(isFirst){
    		fadingView.setVisibility(VISIBLE);
    	}
    	
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        if(fadingHeightView != null)
//        	fadingHeight = fadingHeightView.getMeasuredHeight() * 2 / 3;
        
        
    }
    
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
    	// TODO Auto-generated method stub
    	super.onLayout(changed, l, t, r, b);
    	
    	ViewGroup vg = (ViewGroup) getChildAt(0);
    	if(vg.getChildCount() > 1){
        	firstChildHeight = vg.getChildAt(0).getHeight() / 2;
        	
        }
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
    	// TODO Auto-generated method stub
    	super.onDraw(canvas);
    	if(isFirst){
    		isFirst = false;
//    		fadingView.scrollTo(0, -fadingView.getHeight());
    		((ScrollerLayout)fadingView).smoothScrollBy(0, fadingView.getHeight(), 800);
    	}
    	
//    	Log.d("tt", "X:"+fadingView.getX() + "Y:"+fadingView.getY());
    }
    

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
    	super.onScrollChanged(l, t, oldl, oldt);
    	//        l,t  滑动后 xy位置，
    	//        oldl lodt 滑动前 xy 位置-----
    	
//    	Log.d(TAG, "onScrollChanged  l:" + l + ",t:" + t + ",oldl:" + oldl + ",oldt" + oldt);
    	
//    	float fading = t > fadingHeight ? fadingHeight : (t > fadingHeight * 2 / 3 ? t : 0);
//    	updateActionBarAlpha( fading / fadingHeight);

    	if(t > firstChildHeight){
    		if(fadingViewState == GONE){
    			fadingViewState = VISIBLE;
//    			fadingView.scrollTo(0, 0);
    			((ScrollerLayout)fadingView).smoothScrollBy(0, -fadingView.getHeight());
    		}
    	}else{
    		if(fadingViewState == VISIBLE){
    			fadingViewState = GONE;
//    			fadingView.scrollTo(0, -fadingView.getHeight());
    			((ScrollerLayout)fadingView).smoothScrollBy(0, fadingView.getHeight());
    		}
    	}
    	
    	//回调，通知外边标题栏更换位置
    	if(scrollChangeChildListener != null)
    		scrollChangeChildListener.onScorllChangeChild(l, t, oldl, oldt);
    	
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
    
    public void updateActionBarLoaction(int t){
      fadingView.scrollTo(0, t);
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
    
}
