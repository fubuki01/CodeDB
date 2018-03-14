package com.dream.mediaplayer.helpers.utils;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

public class MenuColorFix implements android.view.LayoutInflater.Factory {
	static int mColor = Color.rgb(88, 88, 88);

	static final Class<?>[] constructorSignature = new Class[] { Context.class,
			AttributeSet.class };

	public View onCreateView(String name, Context context, AttributeSet attrs) {
		if (name.equalsIgnoreCase("com.android.internal.view.menu.ListMenuItemView"))
			try {
				Class<? extends ViewGroup> clazz = context.getClassLoader()
						.loadClass(name).asSubclass(ViewGroup.class);
				Constructor<? extends ViewGroup> constructor = clazz
						.getConstructor(constructorSignature);
				final ViewGroup view = constructor.newInstance(new Object[] {
						context, attrs });
				new Handler().post(new Runnable() {
					public void run() {
						try {
							List<View> children = getAllChildren(view);
							int sum = children.size();
							for (View child : children) {
								if (child instanceof TextView) {
									((TextView) child).setTextColor(Color.rgb(
											88, 88, 88)
									);
								}
							}
						} catch (Exception e) {
							// Log.i(TAG, "Caught Exception!",e);
						}

					}
				});
				return view;
			} catch (Exception e) {
				// Log.i(TAG, "Caught Exception!",e);
			}
		return null;
	}

	public List<View> getAllChildren(ViewGroup vg) {
		ArrayList<View> result = new ArrayList<View>();
		int sum = vg.getChildCount();
		for (int i = 0; i < sum; i++) {
			View child = vg.getChildAt(i);
			if (child instanceof ViewGroup) {
				result.addAll(getAllChildren((ViewGroup) child));
			} else {
				result.add(child);
			}
		}
		return result;
	}

	static public void addMenu(Menu menu, int groupID, int itemID, int orderID,
			String title) {
		menu.add(groupID, itemID, orderID, title);
		setMenuTextColor(menu, itemID, title);
	}

	static public void setMenuTextColor(Menu menu, int menuResource, String str) {
		// int color = mColor;//Color.rgb(42, 232, 255);
		MenuItem item = menu.findItem(menuResource);
	
		SpannableString s = new SpannableString(str);
		s.setSpan(new ForegroundColorSpan(mColor), 0, s.length(), 0);
		item.setTitle(s);
	}

	static public int getTextColor() {
		return mColor;
	}
}
