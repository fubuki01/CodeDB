package com.dream.mediaplayer.ui.adapters;

import static com.dream.mediaplayer.Constants.SIZE_THUMB;
import static com.dream.mediaplayer.Constants.SRC_FIRST_AVAILABLE;
import static com.dream.mediaplayer.Constants.TYPE_ALBUM;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SectionIndexer;
import android.widget.SimpleCursorAdapter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.dream.mediaplayer.R;
import com.dream.mediaplayer.cache.ImageInfo;
import com.dream.mediaplayer.cache.ImageProvider;
import com.dream.mediaplayer.fragment.ArtistAlbumsFragment;
import com.dream.mediaplayer.helpers.utils.MusicUtils;
import com.dream.mediaplayer.helpers.utils.SortCursor;
import com.dream.mediaplayer.helpers.utils.SortCursor.SortEntry;
import com.dream.mediaplayer.views.ViewHolderAddTracksToQueueList;
import com.dream.mediaplayer.views.ViewHolderArtistAlbumList;

/**
 * @author Andrew Neal
 */
public class ArtistAlbumAdapter extends SimpleCursorAdapter implements SectionIndexer {

	private Context mContext;
	
	private WeakReference<ViewHolderArtistAlbumList> holderReference;
	
	private int[] mSectionIndices;
	private Character[] mSectionLetters;

	/**
	 */
	private Map<String, Integer> mIndexer = new HashMap<String, Integer>();

	private ArrayList<SortEntry> sortList;

	private ImageProvider mImageProvider;

	public ArtistAlbumAdapter(Context context, int layout, Cursor c,
			String[] from, int[] to, int flags) {
		super(context, layout, c, from, to, flags);
		mContext = context;
		mImageProvider = ImageProvider.getInstance((Activity) mContext);
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
		// ViewHolderList
		ViewHolderArtistAlbumList viewholder;

		if (view != null) {
			viewholder = new ViewHolderArtistAlbumList(view);
//			view.setTag(viewholder);
			holderReference = new WeakReference<ViewHolderArtistAlbumList>(viewholder);
			view.setTag(holderReference.get());
		} else {
			viewholder = (ViewHolderArtistAlbumList) convertView.getTag();
		}

		// Album name
		String albumName = mCursor
				.getString(ArtistAlbumsFragment.mAlbumNameIndex);
		viewholder.albumName.setText(albumName);

		// Number of songs
		int songs_plural = mCursor.getInt(ArtistAlbumsFragment.mSongCountIndex);
		String template = mContext.getString(R.string.album_num_of_tracks_text);
		String text = String.format(template, songs_plural);
		viewholder.tracksCount.setText(text);

		// Artist name
		String artistName = mCursor
				.getString(ArtistAlbumsFragment.mArtistNameIndex);

		String albumId = mCursor.getString(ArtistAlbumsFragment.mAlbumIdIndex);

		ImageInfo mInfo = new ImageInfo();
		mInfo.type = TYPE_ALBUM;
		mInfo.size = SIZE_THUMB;
		mInfo.source = SRC_FIRST_AVAILABLE;
		mInfo.data = new String[] { albumId, artistName, albumName };

		mImageProvider.loadImage(holderReference.get().imageView, mInfo);

		// Now playing indicator
		long currentalbumid = MusicUtils.getCurrentAlbumId();
		long albumid = mCursor.getLong(ArtistAlbumsFragment.mAlbumIdIndex);
		if (currentalbumid == albumid) {

		} else {

		}

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
