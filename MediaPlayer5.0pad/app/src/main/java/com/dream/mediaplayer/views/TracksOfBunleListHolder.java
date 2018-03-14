/**
 * 
 */

package com.dream.mediaplayer.views;

import com.dream.mediaplayer.R;
import com.dream.mediaplayer.ui.widgets.VisualizerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * @author Andrew Neal
 */
public class TracksOfBunleListHolder {

//	public VisualizerView visualizerView;
	public ImageView visualizerView;
	public TextView music_title;
	public TextView music_duration;
	public View lineView;
	
	
	public TracksOfBunleListHolder(View view) {
//		visualizerView = (VisualizerView) view.findViewById(R.id.visualizerView);
		visualizerView = (ImageView) view.findViewById(R.id.visualizerView);
		music_title = (TextView) view.findViewById(R.id.music_title);
		music_duration = (TextView) view.findViewById(R.id.music_duration);
		lineView = (View) view.findViewById(R.id.view_line);
	}
}
