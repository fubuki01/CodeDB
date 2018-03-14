package com.dream.mediaplayer.helpers.utils;

import junit.framework.Assert;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class BrandTextView extends TextView {

    public BrandTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
   public BrandTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
   public BrandTextView(Context context) {
        super(context);
   }
   /*
   public void setTypeface(Typeface tf, int style) {
      super.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/DroidSansFallback.ttf"));
         
    }
    */
   public void init(Context context){
	   AssetManager assetManager=context.getAssets();
	   Typeface front=Typeface.createFromAsset(assetManager, "fonts/DroidSansFallback.ttf");
	   setTypeface(front);
   }
}
