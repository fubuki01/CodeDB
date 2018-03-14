package com.dream.mediaplayer.views;


import com.dream.mediaplayer.R;
import com.lg.lrcview_master.LrcView;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.FrameLayout;

public class FadeFrameLayoutImageView extends FrameLayout {
	
	private String TAG = "FadeFrameLayout -- ";

	private AlphaAnimation alphaOut;
    private AlphaAnimation alphaIn;
    
    private boolean isAnimEnd = true;

    private Context context;
    
    /**
	 * 显示封面的imageview
	 */
	private FadeImageView albumImageView;
	
    public FadeFrameLayoutImageView(Context context) {
        super(context);
        this.context = context;
    }

    public FadeFrameLayoutImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public FadeFrameLayoutImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
    }

    private AlphaAnimation getAlphaOut(){
        if(null != alphaOut){
            return alphaOut;
        }
        alphaOut = new AlphaAnimation(1.0f, 0f);
        alphaOut.setFillAfter(true);
        alphaOut.setDuration(400);
        return alphaOut;
    }

    private AlphaAnimation getAlphaIn(){
        if(null != alphaIn){
            return alphaIn;
        }
        alphaIn = new AlphaAnimation(0f, 1.0f);
        alphaIn.setFillAfter(true);
        alphaIn.setDuration(400);
        return alphaIn;
    }
    
    @Override
    protected void onFinishInflate() {
    	// TODO Auto-generated method stub
    	super.onFinishInflate();
    	
    	albumImageView = (FadeImageView) findViewById(R.id.imageCover);
    }
    
    private void setVisibilityOfElements(int visibility) {
    	albumImageView.setVisibility(visibility);
    }
    
    public void setVisiblityVisible() {
    	if (!isAnimEnd) {
//    		return;
    	}
    	
    	isAnimEnd = false;
    	
    	setVisibility(View.VISIBLE);
    	setVisibilityOfElements(View.VISIBLE);
    	
    	getAlphaIn().setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				isAnimEnd = true;
		    	
		    	invalidate();
			}
		});
    	this.startAnimation(getAlphaIn());
    }
    
    public void setVisiblityGone() {
    	if (!isAnimEnd) {
//    		return;
    	}
    	
    	isAnimEnd = false;
    	
    	setVisibility(View.INVISIBLE);
    	
    	getAlphaOut().setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				isAnimEnd = true;
				setVisibilityOfElements(View.INVISIBLE);
		    	
		    	invalidate();
			}
		});
    	this.startAnimation(getAlphaOut());
    }
}
