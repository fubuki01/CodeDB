package com.dream.mediaplayer.ui.adapters;

import java.util.ArrayList;

import com.dream.mediaplayer.R;

import android.content.Context;
import android.text.TextUtils.TruncateAt;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SpinnerAdapter extends ArrayAdapter<SpinnerItemBean> {

	private Context context;

	private final LayoutInflater mInflater;

	ArrayList<SpinnerItemBean> spinnerDataList;

	public SpinnerAdapter(Context context,
			ArrayList<SpinnerItemBean> spinnerDataList) {
		super(context, 0);

		this.context = context;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		this.spinnerDataList = spinnerDataList;

		for (int i = 0; i < spinnerDataList.size(); i++) {
			add(spinnerDataList.get(i));
		}
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// return createViewFromResource(
		// position, convertView, parent, android.R.layout.simple_spinner_item,
		// false);

		View view;
		TextView textView;

		if (convertView == null) {
			view = (View) mInflater.inflate(R.layout.spinner_item_layout,
					parent, false);
			textView = (TextView) view.findViewById(R.id.text);
			textView.setAllCaps(true);
			textView.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
			textView.setTextColor(context.getResources()
					.getColor(R.color.white));
			textView.setEllipsize(TruncateAt.MIDDLE);
		} else {
			view = (View) convertView;
			textView = (TextView) view.findViewById(R.id.text);
		}

		String text = getItem(position).getName();
		textView.setText(text);

		return view;
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
//		return createViewFromResource(position, convertView, parent,
//				android.R.layout.simple_spinner_dropdown_item);
		
		return createViewFromResource(position, convertView, parent,
				R.layout.spinner_checked_text);
	}

	private View createViewFromResource(int position, View convertView,
			ViewGroup parent, int resource) {
		TextView textView;

		if (convertView == null) {
			textView = (TextView) mInflater.inflate(resource, parent, false);
			textView.setAllCaps(true);
			textView.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
			textView.setEllipsize(TruncateAt.MIDDLE);
		} else {
			textView = (TextView) convertView;
		}

		if (spinnerDataList.get(position).getIsSelected()) {
			textView.setTextColor(context.getResources().getColor(
					R.color.listview_item_textview_select_color));
		} else {
			textView.setTextColor(context.getResources()
					.getColor(R.color.black));
		}

		String text = getItem(position).getName();
		textView.setText(text);

		return textView;
	}

}
