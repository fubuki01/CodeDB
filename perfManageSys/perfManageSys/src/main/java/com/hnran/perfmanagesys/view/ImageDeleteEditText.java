package com.hnran.perfmanagesys.view;


import com.hnran.perfmanagesys.R;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

public class ImageDeleteEditText extends EditText {  

//	private static final int[] ATTRS = {android.R.attr.drawableLeft};
	
	private Context mContext;  
	private Drawable imgDel_Bule;  
	
	private Drawable imgLeft;
	private int imgLeftId = 0;
	
	private boolean isFocusable = true;

	public ImageDeleteEditText(Context context) {  
		super(context);  
		mContext = context;  
		init();  
	}  

	public ImageDeleteEditText(Context context, AttributeSet attrs, int defStyle) {  
		super(context, attrs, defStyle);  
		mContext = context;  	
		init();  
		
	}  

	public ImageDeleteEditText(Context context, AttributeSet attrs) {  
		super(context, attrs);  
		mContext = context;  
		
//		/**
//		 * 获取父类TextView属性
//		 */
//		TypedArray a = context.obtainStyledAttributes(attrs, ATTRS);
//		imgLeftId = a.getResourceId(0, 0);
//		a.recycle();
	
		init();  
	}  

	/*** 
	 * 初始化 
	 */  
	public void init() {  
		// TODO Auto-generated method stub  
		imgDel_Bule = mContext.getResources().getDrawable(R.drawable.ic_img_delete);  
//		imgLeft  = imgLeftId != 0 ? mContext.getResources().getDrawable(imgLeftId) : null;  
		setDrawble();  
		// 对EditText文本状态监听  
		this.addTextChangedListener(new TextWatcher() {  

			@Override  
			public void onTextChanged(CharSequence s, int start, int before,  
					int count) {  
				// TODO Auto-generated method stub  
			}  

			@Override  
			public void beforeTextChanged(CharSequence s, int start, int count,  
					int after) {  
				// TODO Auto-generated method stub  
			}  

			@Override  
			public void afterTextChanged(Editable s) {  
				// TODO Auto-generated method stub  
				setDrawble();  
				if(textChangedListener != null) textChangedListener.addTextNumber(ImageDeleteEditText.this.length());
			}  
		});  
	}  

	/*** 
	 * 设置图片 
	 */  
	public void setDrawble() {  
		if (this.length() < 1) {  
			/**** 
			 * 此方法意思是在EditText添加图片 参数： left - 左边图片id top - 顶部图片id right - 右边图片id 
			 * bottom - 底部图片id 
			 */  
			this.setCompoundDrawablesWithIntrinsicBounds(imgLeft, null,  
					null, null);  
		} else {
			if(isFocusable)
				this.setCompoundDrawablesWithIntrinsicBounds(imgLeft, null,  
					imgDel_Bule, null);  
		}  
	}  

	/*** 
	 * 设置删除事件监听 
	 */  
	@Override  
	public boolean onTouchEvent(MotionEvent event) { 
		// TODO Auto-generated method stub  
		if (imgDel_Bule != null && event.getAction() == MotionEvent.ACTION_UP) {  
			int eventX = (int) event.getRawX();  
			int eventY = (int) event.getRawY();  
			Rect rect = new Rect();  
			getGlobalVisibleRect(rect);  
			rect.left = rect.right - 50;  
			if (rect.contains(eventX, eventY) && isFocusable)  {
				setText("");
				return true;
			}
		}  
		return super.onTouchEvent(event);  
	}  
	
	
	/**
	 * 
	 */
	public interface TextChangedListener{
		void addTextNumber(int length);
	}
	private TextChangedListener textChangedListener = null;
	public void setTextChangedListener(TextChangedListener textChangedListener){
		this.textChangedListener = textChangedListener;
	}
	
	/**
	 * 设置为显示密码
	 */
	public void setPasswordType(){
		this.setTransformationMethod(PasswordTransformationMethod.getInstance());
	}
	
	@Override
	public void setFocusable(boolean focusable) {
		// TODO Auto-generated method stub
		super.setFocusable(focusable);
		isFocusable = focusable;
	}

}  