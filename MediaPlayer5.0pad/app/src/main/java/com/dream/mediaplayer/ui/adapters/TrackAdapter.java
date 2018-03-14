package com.dream.mediaplayer.ui.adapters;

import static com.dream.mediaplayer.Constants.SIZE_THUMB;
import static com.dream.mediaplayer.Constants.SRC_FIRST_AVAILABLE;
import static com.dream.mediaplayer.Constants.TYPE_ALBUM;

import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.dream.mediaplayer.R;
import com.dream.mediaplayer.cache.ImageInfo;
import com.dream.mediaplayer.cache.ImageProvider;
import com.dream.mediaplayer.fragment.TracksFragment;
import com.dream.mediaplayer.helpers.utils.MusicUtils;
import com.dream.mediaplayer.helpers.utils.SortCursor;
import com.dream.mediaplayer.helpers.utils.SortCursor.SortEntry;
import com.dream.mediaplayer.helpers.utils.VisualizerUtils;
import com.dream.mediaplayer.ui.widgets.VisualizerView;
import com.dream.mediaplayer.views.ViewHolderAddTracksToQueueList;
import com.dream.mediaplayer.views.ViewHolderList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.AnimationDrawable;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SectionIndexer;
import android.widget.SimpleCursorAdapter;

/**
 * @author Andrew Neal
 */
@SuppressLint("NewApi")
public class TrackAdapter extends SimpleCursorAdapter implements SectionIndexer {

	private Activity activity;
	
	private WeakReference<ViewHolderList> holderReference;

	private int[] mSectionIndices;
	private Character[] mSectionLetters;

	/**
	 */
	private Map<String, Integer> mIndexer = new HashMap<String, Integer>();

	private ArrayList<SortEntry> sortList;

	public TrackAdapter(Context context, int layout, SortCursor c,
			String[] from, int[] to, int flags) {
		super(context, layout, c, from, to, flags);

		this.activity = (Activity) context;
	}

	@Override
	public void changeCursor(Cursor cursor) {
		// TODO Auto-generated method stub
		super.changeCursor(cursor);

		if (cursor != null) {
			sortList = ((SortCursor) cursor).getSortList();
			mIndexer.clear();

			getSectionIndices();
		}
	}
	
	@Override
	public Cursor swapCursor(Cursor c) {
		// TODO Auto-generated method stub
		if (c != null) {
			sortList = ((SortCursor) c).getSortList();
			mIndexer.clear();

			getSectionIndices();
		}
		
		return super.swapCursor(c);
	}
	
	public Map<String, Integer> getIndexer() {
		return mIndexer;
	}

	private void getSectionIndices() {
		if (sortList.size() == 0) {
			return;
		}

		ArrayList<Integer> sectionIndices = new ArrayList<Integer>();
		if (sortList.size() > 0) {
			String[] alphatableb = { "#", "A", "B", "C", "D", "E", "F", "G",
					"H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S",
					"T", "U", "V", "W", "X", "Y", "Z" };

			ArrayList<String> formatList = new ArrayList<String>();
			for (int i = 0; i < sortList.size(); i++) {
				formatList.add(sortList.get(i).key);
			}

			for (int i = 0; i < alphatableb.length; i++) {
				int firstIndex = formatList.indexOf(alphatableb[i]);
				if (firstIndex != -1) {
					sectionIndices.add(firstIndex);
					mIndexer.put(alphatableb[i], firstIndex);
				}
			}

			mSectionLetters = new Character[mIndexer.size()];
			for (int i = 0; i < mSectionLetters.length; i++) {
				mSectionLetters[i] = formatList.get(sectionIndices.get(i))
						.charAt(0);
			}

			mSectionIndices = new int[sectionIndices.size()];
			for (int i = 0; i < sectionIndices.size(); i++) {
				mSectionIndices[i] = sectionIndices.get(i);
			}
		}
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final View view = super.getView(position, convertView, parent);
		Cursor mCursor = (Cursor) getItem(position);
		mCursor.moveToPosition(position);
		// ViewHolderList
		ViewHolderList viewholder;
		if (view != null) {
			viewholder = new ViewHolderList(view);
//			view.setTag(viewholder);
			holderReference = new WeakReference<ViewHolderList>(viewholder);
			view.setTag(holderReference.get());
		} else {
			viewholder = (ViewHolderList) convertView.getTag();
		}

		// Track name
		String trackName = mCursor.getString(TracksFragment.mTitleIndex);
		viewholder.music_title.setText(trackName);


		// Artist name
		String artistName = mCursor.getString(TracksFragment.mArtistIndex);
		viewholder.music_Artist.setText(artistName);

		// Now playing indicator
		long currentaudioid = MusicUtils.getCurrentAudioId();
		long audioid = mCursor.getLong(TracksFragment.mMediaIdIndex);
		if (currentaudioid == audioid) {
			viewholder.music_title.setTextColor(activity.getResources().getColor(R.color.listview_item_textview_select_color));
			viewholder.music_Artist.setTextColor(activity.getResources().getColor(R.color.listview_item_textview_select_color));
			
//			viewholder.isPlayIV.setVisibility(View.VISIBLE);
			viewholder.visualizerView.setVisibility(View.VISIBLE);
			
//			WeakReference<VisualizerView> mView = new WeakReference<VisualizerView>(viewholder.visualizerView);
//	        VisualizerUtils.updateVisualizerView(mView);
			
			viewholder.visualizerView.setImageResource(R.drawable.visualizer_anim);
	        AnimationDrawable animationDrawable = (AnimationDrawable)viewholder.visualizerView.getDrawable();
	        try {
                if (MusicUtils.mService.isPlaying()) {
                	animationDrawable.start();
                } else {
                	animationDrawable.stop();
                }
            } catch (RemoteException e) {
                e.printStackTrace();
                viewholder.visualizerView.setVisibility(View.INVISIBLE);
    			viewholder.visualizerView.setImageResource(0);
            }
		} else {
			viewholder.music_title.setTextColor(activity.getResources().getColor(R.color.listitem_big_text_color));
			viewholder.music_Artist.setTextColor(activity.getResources().getColor(R.color.listitem_little_text_color));
			
//			holderReference.get().isPlayIV.setVisibility(View.INVISIBLE);
			viewholder.visualizerView.setVisibility(View.INVISIBLE);
			viewholder.visualizerView.setImageResource(0);
		}

		ImageInfo mInfo = new ImageInfo();
		mInfo.type = TYPE_ALBUM;
		mInfo.size = SIZE_THUMB;
		mInfo.source = SRC_FIRST_AVAILABLE;
		try {
			mInfo.data = new String[]{mCursor.getString(TracksFragment.mAlbumId),
					mCursor.getString(TracksFragment.mTitleIndex),
					mCursor.getString(TracksFragment.mArtistIndex)};
		}catch(Exception e){
			Log.e("TrackAdapter", "error："+TracksFragment.mAlbumId+":" +
					+TracksFragment.mAlbumId+":"+TracksFragment.mArtistIndex
					+mCursor.getCount()+":"+mCursor.getColumnCount());
		}
		
		ImageProvider.getInstance(activity).loadImage(holderReference.get().imageView,
				mInfo);

		return view;
	}

	@Override
	public int getPositionForSection(int section) {
		if (mSectionIndices != null) {
			if (section >= mSectionIndices.length) {
				section = mSectionIndices.length - 1;
			} else if (section < 0) {
				section = 0;
			}
			return mSectionIndices[section];
		}

		return 0;
	}

	@Override
	public int getSectionForPosition(int position) {
		if (mSectionIndices != null) {
			for (int i = 0; i < mSectionIndices.length; i++) {
				if (position < mSectionIndices[i]) {
					return i - 1;
				}
			}
			return mSectionIndices.length - 1;
		}

		return 0;
	}

	@Override
	public Object[] getSections() {
		if (mSectionLetters != null) {
			return mSectionLetters;
		}

		return new String[] { " " };
	}

}
