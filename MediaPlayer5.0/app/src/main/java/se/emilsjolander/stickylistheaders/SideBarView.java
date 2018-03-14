package se.emilsjolander.stickylistheaders;

import com.dream.mediaplayer.R;
import com.dream.mediaplayer.helpers.utils.ApolloUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.Typeface;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * 
 * @author gj
 * 
 */
public class SideBarView extends View {

	private static final String TAG = "SideBarView";

	private static final String[] Item = { "#", "A", "B", "C", "D", "E", "F",
			"G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S",
			"T", "U", "V", "W", "X", "Y", "Z" };

	private Paint mPaint;
	private Paint mLinePaint;

	private float mFontSize = 0;

	private int mWidth, mHeight;
	private float mItemHeight;

	private int mChoosePos = -1;

	/**
	 * 用于选中条目时覆盖在条目上方的位图
	 */
	private Bitmap bitmapCover;

	private TextView mPopupText;
	private PopupWindow mPopupWindow;
	private Handler handler = new Handler();

	private OnItemClickListener mItemClickListener;
	private ViewTreeObserver mVto;

	public SideBarView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public SideBarView(Context context) {
		super(context);
		init();
	}

	private void init() {
		mPaint = new Paint();
		mLinePaint = new Paint();
		mLinePaint
				.setColor(getResources().getColor(R.color.sidebar_line_color));
		setViewTreeObserver();
		/*
		 * vto.addOnPreDrawListener(new OnPreDrawListener() {
		 * 
		 * @Override public boolean onPreDraw() { if (mFontSize == 0) { mWidth =
		 * getMeasuredWidth(); mHeight = getMeasuredHeight(); mItemHeight =
		 * (float) mHeight / (float) Item.length; mFontSize =
		 * measureFontSize(mItemHeightmItemHeight
		 * -ApolloUtils.sp2px(getContext(), 3));
		 * 
		 * bitmapCover = Bitmap.createBitmap(mWidth,
		 * (int)(mItemHeight+4*(mItemHeight/5)), Config.ARGB_4444); Canvas
		 * canvas = new Canvas(bitmapCover);
		 * canvas.drawColor(getResources().getColor
		 * (R.color.sidebar_select_bitmap_color));
		 * 
		 * Log.i(TAG, "mWidth=" + mWidth + " mHeight=" + mHeight +
		 * " mItemHeight=" + mItemHeight + " mFontSize=" + mFontSize); } return
		 * true; } });
		 */
	}

//    @Override  
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {  
//        // TODO Auto-generated method stub  
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);  
//        Log.i("AAA","LinearLayout onMeasure Width:"+getWidth() +" Height:"+getHeight());  
//        Log.i("AAA","LinearLayout onMeasure MeasuredWidth:"+getMeasuredWidth() +" MeasuredHeight:"+getMeasuredHeight());  
//        setViewTreeObserver();
//    }  	
	
	private void setViewTreeObserver() {
//		if (mVto == null) {
			mVto = getViewTreeObserver();
			mVto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

				@Override
				public void onGlobalLayout() {
					// TODO Auto-generated method stub
					if (mFontSize == 0) {
						mWidth = getWidth();//getMeasuredWidth();
						mHeight = getMeasuredHeight();
						if (mWidth == 0 || mHeight == 0||mHeight>1920) {
							getViewTreeObserver().removeOnGlobalLayoutListener(
									this);

							return;
						}
						

						mItemHeight = (float) mHeight / (float) Item.length;
						mFontSize = measureFontSize(/* mItemHeight */mItemHeight
								- ApolloUtils.sp2px(getContext(), 3));

//						if (bitmapCover == null) {
							bitmapCover = Bitmap
									.createBitmap(
											mWidth,
											(int) (mItemHeight + 4 * (mItemHeight / 5)),
											Config.ARGB_4444);
//						}
						Canvas canvas = new Canvas(bitmapCover);
						canvas.drawColor(getResources().getColor(
								R.color.sidebar_select_bitmap_color));

						Log.i(TAG, "mWidth=" + mWidth + " mHeight=" + mHeight
								+ " mItemHeight=" + mItemHeight + " mFontSize="
								+ mFontSize);

						getViewTreeObserver()
								.removeOnGlobalLayoutListener(this);
					}
				}
			});
//		}
	}

	/**
	 * 
	 * @param realHeight
	 * @return
	 */
	private float measureFontSize(float realHeight) {
		float fontSize = realHeight;
		float measureHeight = measureFontHeight(fontSize);
		while (measureHeight > realHeight) {
			fontSize -= 1.0;
			measureHeight = measureFontHeight(fontSize);
		}
		return fontSize;
	}

	/**
	 * 
	 * @param fontSize
	 * @return
	 */
	private float measureFontHeight(float fontSize) {
		Paint paint = new Paint();
		paint.setTextSize(fontSize);
		FontMetrics fm = paint.getFontMetrics();
		return fm.descent - fm.ascent;
	}

	private void drawText(Canvas canvas, String text, int x, int y, int width,
			int height, Paint paint) {
		FontMetrics fontMetrics = paint.getFontMetrics();
		float fontHeight = fontMetrics.bottom - fontMetrics.top;
		float textBaseY = height - (height - fontHeight) / 2
				- fontMetrics.bottom;
		float newX = x + width / 2;
		float newY = y + textBaseY;

		paint.setTextAlign(Align.CENTER);
		canvas.drawText(text, newX, newY, paint);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		for (int i = 0; i < Item.length; i++) {
			mPaint.setTextSize(mFontSize);
			mPaint.setColor(getResources().getColor(R.color.sidebar_text_color));
			mPaint.setTypeface(Typeface.DEFAULT);
			mPaint.setFakeBoldText(false);
			mPaint.setAntiAlias(true);
			if (i == mChoosePos) {
				mPaint.setColor(getResources().getColor(
						R.color.sidebar_seclect_charactor_color));
			}
			drawText(canvas, Item[i], 0, (int) (mItemHeight * i), mWidth,
					(int) mItemHeight, mPaint);
			mPaint.reset();

			// 画横线
			// canvas.drawLine(0, (int) (mItemHeight * i), mWidth, (int)
			// (mItemHeight * i), mLinePaint);
			// if (i == (Item.length-1)) {
			// canvas.drawLine(0, (int) (mItemHeight * i)+mItemHeight, mWidth,
			// (int) (mItemHeight * i)+mItemHeight, mLinePaint);
			// }
			// 画两条竖线
			// canvas.drawLine(0, (int) (mItemHeight * i), 0, (int) (mItemHeight
			// * (i+1)), mLinePaint);
			// canvas.drawLine(mWidth, (int) (mItemHeight * i), mWidth, (int)
			// (mItemHeight * (i+1)), mLinePaint);

			// 画半透明的选中栏
			// if (i == mChoosePos) {
			// canvas.drawBitmap(bitmapCover, 0, (int) (mItemHeight *
			// i)-2*mItemHeight/5, null);
			// }
		}
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		int choosePos = (int) (event.getY() / mItemHeight);
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if (mChoosePos != choosePos) {
				if (choosePos >= 0 && choosePos < Item.length) {
					if (mItemClickListener != null) {
						mItemClickListener.onItemClick(Item[choosePos]);
					}
					// showPopupText(choosePos);
					mChoosePos = choosePos;
					invalidate();
				}
			}
			break;
		case MotionEvent.ACTION_MOVE:
			if (mChoosePos != choosePos) {
				if (choosePos >= 0 && choosePos < Item.length) {
					if (mItemClickListener != null) {
						mItemClickListener.onItemClick(Item[choosePos]);
					}
					// showPopupText(choosePos);
					mChoosePos = choosePos;
					invalidate();
				}
			}
			break;
		case MotionEvent.ACTION_UP:
			mChoosePos = -1;
			if (mItemClickListener != null) {
				mItemClickListener.onItemClick(null);
			}
			// dismissPopupText();
			invalidate();
			break;

		case MotionEvent.ACTION_CANCEL:
			mChoosePos = -1;
			// dismissPopupText();
			invalidate();
			break;

		default:
			break;
		}
		return true;
	}

	private Runnable dismissRunnable = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			if (mPopupWindow != null) {
				mPopupWindow.dismiss();
			}
		}
	};

	private void showPopupText(int item) {
		if (mPopupWindow == null) {
			handler.removeCallbacks(dismissRunnable);
			mPopupText = new TextView(getContext());
			mPopupText.setBackgroundResource(R.drawable.popup_corner);
			mPopupText.setTextColor(getResources().getColor(
					R.color.listview_item_textview_select_color));
			int size = (int) (2.5f * mFontSize);
			mPopupText.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
			mPopupText.setGravity(Gravity.CENTER);
			int height = (int) (measureFontHeight(size) + 2.5f * mFontSize);
			mPopupWindow = new PopupWindow(mPopupText, height, height);
		}

		mPopupText.setText(Item[item]);
		if (mPopupWindow.isShowing()) {
			mPopupWindow.update();
		} else {
			// mPopupWindow.showAtLocation(getRootView(),
			// Gravity.RIGHT|Gravity.TOP, this.getWidth(), this.getTop());
			mPopupWindow
					.showAtLocation(getRootView(), Gravity.CENTER, /*
																	 * mPopupWindow.
																	 * getWidth
																	 * ()
																	 */0, 0);
		}
	}

	private void dismissPopupText() {
		handler.postDelayed(dismissRunnable, 500);
	}

	public void setOnItemClickListener(OnItemClickListener listener) {
		mItemClickListener = listener;
	}

	public interface OnItemClickListener {
		void onItemClick(String item);
	}

}
