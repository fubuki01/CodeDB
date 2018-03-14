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
public class ViewHolderList {

	public FadeImageView imageView;
//	public VisualizerView visualizerView;
	public ImageView visualizerView;
	public TextView music_title;
	public TextView music_Artist;
//	public ImageView isPlayIV;
	public View lineView;
	
	
	public ViewHolderList(View view) {
		imageView = (FadeImageView) view.findViewById(R.id.item_cover);
//		visualizerView = (VisualizerView) view.findViewById(R.id.visualizerView);
		visualizerView = (ImageView) view.findViewById(R.id.visualizerView);
		music_title = (TextView) view.findViewById(R.id.music_title);
		music_Artist = (TextView) view.findViewById(R.id.music_Artist);
//		isPlayIV = (ImageView) view.findViewById(R.id.isPlayImageView);
		lineView = (View) view.findViewById(R.id.view_line);
	}
}
