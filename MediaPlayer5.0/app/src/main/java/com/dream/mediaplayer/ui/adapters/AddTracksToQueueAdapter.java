package com.dream.mediaplayer.ui.adapters;

import static com.dream.mediaplayer.Constants.SIZE_THUMB;
import static com.dream.mediaplayer.Constants.SRC_FIRST_AVAILABLE;
import static com.dream.mediaplayer.Constants.TYPE_ALBUM;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.dream.mediaplayer.R;
import com.dream.mediaplayer.cache.ImageInfo;
import com.dream.mediaplayer.cache.ImageProvider;
import com.dream.mediaplayer.fragment.AddTracksToQueueFragment;
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
import android.os.RemoteException;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.SectionIndexer;
import android.widget.SimpleCursorAdapter;

/**
 * @author Andrew Neal
 */
@SuppressLint("NewApi")
public class AddTracksToQueueAdapter extends SimpleCursorAdapter implements SectionIndexer {

	private Activity activity;
	
	private WeakReference<ViewHolderAddTracksToQueueList> holderReference;

	private int[] mSectionIndices;
	private Character[] mSectionLetters;

	/**
	 */
	private Map<String, Integer> mIndexer = new HashMap<String, Integer>();

	private ArrayList<SortEntry> sortList;

	public AddTracksToQueueAdapter(Context context, int layout, SortCursor c,
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
	
	public ArrayList<SortEntry> getSortList() {
		return sortList;
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
		ViewHolderAddTracksToQueueList viewholder;
		if (view != null) {
			viewholder = new ViewHolderAddTracksToQueueList(view);
//			view.setTag(viewholder);
			holderReference = new WeakReference<ViewHolderAddTracksToQueueList>(viewholder);
			view.setTag(holderReference.get());
		} else {
			viewholder = (ViewHolderAddTracksToQueueList) convertView.getTag();
		}
		
//		viewholder.checkBox.setChecked(sortList.get(position).isSelect);
		if (sortList.get(position).isSelect) {
			viewholder.checkBox.setBackgroundResource(R.drawable.select_image_select);
		} else {
			viewholder.checkBox.setBackgroundResource(R.drawable.select_image_unselect);
		}

		// Track name
		String trackName = mCursor.getString(AddTracksToQueueFragment.mTitleIndex);
		viewholder.music_title.setText(trackName);

		// Artist name
		String artistName = mCursor.getString(AddTracksToQueueFragment.mArtistIndex);
		viewholder.music_Artist.setText(artistName);

		ImageInfo mInfo = new ImageInfo();
		mInfo.type = TYPE_ALBUM;
		mInfo.size = SIZE_THUMB;
		mInfo.source = SRC_FIRST_AVAILABLE;
		mInfo.data = new String[] { mCursor.getString(AddTracksToQueueFragment.mAlbumId),
				mCursor.getString(AddTracksToQueueFragment.mTitleIndex),
				mCursor.getString(AddTracksToQueueFragment.mArtistIndex) };
		
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
