package com.dream.mediaplayer.ui.adapters;

import java.util.ArrayList;

import com.dream.mediaplayer.R;
import com.dream.mediaplayer.views.VerticalSeekBar;
//import com.dream.mediaplayer.views.VerticalSeekBar.OnSeekBarChangeListener;

import android.content.Context;
import android.media.AudioManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class VolumeSpinnerAdapter extends ArrayAdapter<String> {

	private Context context;

	private final LayoutInflater mInflater;
	
	AudioManager audioManager;
	private static final int MULTIPLYING_POWER = 2;

	ArrayList<String> spinnerDataList;

	public VolumeSpinnerAdapter(Context context,
			ArrayList<String> spinnerDataList) {
		super(context, 0);

		this.context = context;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

		this.spinnerDataList = spinnerDataList;

		for (int i = 0; i < spinnerDataList.size(); i++) {
			add(spinnerDataList.get(i));
		}
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view;

		if (convertView == null) {
			view = (View) mInflater.inflate(R.layout.volume_spinner_item_layout,
					parent, false);
		} else {
			view = (View) convertView;
		}

		return view;
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
//		return createViewFromResource(position, convertView, parent,
//				android.R.layout.simple_spinner_dropdown_item);
		
		return createViewFromResource(position, convertView, parent,
				R.layout.volume_spinner_checked_text);
	}

	private View createViewFromResource(int position, View convertView,
			ViewGroup parent, int resource) {
		View view;

		if (convertView == null) {
			view = (View) mInflater.inflate(resource, parent, false);
		} else {
			view = (View) convertView;
		}

		VerticalSeekBar verticalSeekBar = (VerticalSeekBar) view.findViewById(R.id.volume_seekbar);
		verticalSeekBar.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC) * MULTIPLYING_POWER);
		verticalSeekBar.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC) * MULTIPLYING_POWER);
		
		verticalSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar VerticalSeekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar VerticalSeekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar VerticalSeekBar,
					int progress, boolean fromUser) {
				// TODO Auto-generated method stub
				
			}
		});
		
		return view;
	}

}
