
package com.dream.mediaplayer.fragment;



import se.emilsjolander.stickylistheaders.SideBarView;

import com.dream.mediaplayer.NowPlayingCursor;
import com.dream.mediaplayer.R;
import com.dream.mediaplayer.activity.LongClickDialog;
import com.dream.mediaplayer.app.App;
import com.dream.mediaplayer.helpers.utils.MenuColorFix;
import com.dream.mediaplayer.helpers.utils.MusicUtils;
import com.dream.mediaplayer.helpers.utils.SortCursor;
import com.dream.mediaplayer.service.ApolloService;
import com.dream.mediaplayer.ui.adapters.TrackAdapter;


import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.BaseColumns;
import android.provider.MediaStore.Audio;
import android.provider.MediaStore.Audio.Albums;
import android.provider.MediaStore.Audio.Artists;
import android.provider.MediaStore.Audio.AudioColumns;
import android.provider.MediaStore.Audio.Genres;
import android.provider.MediaStore.Audio.Playlists;
import android.provider.MediaStore.MediaColumns;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.dream.mediaplayer.NowPlayingCursor;
import com.dream.mediaplayer.R;
import com.dream.mediaplayer.helpers.utils.MenuColorFix;
import com.dream.mediaplayer.helpers.utils.MusicUtils;
import com.dream.mediaplayer.helpers.utils.SortCursor;
import com.dream.mediaplayer.service.ApolloService;
import com.dream.mediaplayer.ui.adapters.TrackAdapter;

import java.util.HashMap;

import se.emilsjolander.stickylistheaders.SideBarView;

import static com.dream.mediaplayer.Constants.EXTERNAL;
import static com.dream.mediaplayer.Constants.INTENT_ADD_TO_PLAYLIST;
import static com.dream.mediaplayer.Constants.INTENT_PLAYLIST_LIST;
import static com.dream.mediaplayer.Constants.MIME_TYPE;
import static com.dream.mediaplayer.Constants.PLAYLIST_FAVORITES;
import static com.dream.mediaplayer.Constants.PLAYLIST_QUEUE;
import static com.dream.mediaplayer.app.App.SHUFFLE_ALL_INTERVAL_TIME;
import static com.dream.mediaplayer.app.App.clickTimes;
import static com.dream.mediaplayer.app.App.isFastClickShuffleAll;

/**
 */
public class TracksFragment extends Fragment implements LoaderCallbacks<Cursor>,
        OnItemClickListener, MusicUtils.Defs {

    //private boolean islongclick = false;

	private String tag = "TracksFragment --- ";
	
    // Adapter
    private TrackAdapter mTrackAdapter;

    // ListView
    private ListView mListView;
    
    /**
     */
    private TextView mTextViewCount;

    // Cursor
    private Cursor mCursor;

    // Playlist ID
    private long mPlaylistId = -1;

    // Selected position
    private int mSelectedPosition;

    // Used to set ringtone
    private long mSelectedId;

    private boolean mEditMode = false;

    // Audio columns
    public static int mTitleIndex, mAlbumIndex, mArtistIndex, mMediaIdIndex, mAlbumId;
    private SideBarView mSideBar;

    // Bundle
    public TracksFragment() {
    }

    @SuppressLint({"NewApi", "ValidFragment"})
    public TracksFragment(Bundle args) {
        setArguments(args);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        isEditMode();
        
        // Adapter
        mTrackAdapter = new TrackAdapter(getActivity(), R.layout.tracks_list_item_layout, null,
                new String[] {}, new int[] {}, 0);
        mListView.setOnCreateContextMenuListener(this);
        mListView.setOnItemClickListener(this);
        mListView.setAdapter(mTrackAdapter);

        //绑定长按监听
//        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                islongclick = true;
//                showdialog(((TextView)view.findViewById(R.id.music_title)).getText().toString());
//                return false;
//            }
//        });

        // Important!
        getLoaderManager().initLoader(0, null, this);
    }

//    @Override
    public void refresh() {
        // The data need to be refreshed
        if( mListView != null ) {
            getLoaderManager().restartLoader(0, null, this);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.tracks_fragment_layout, container, false);
        mListView = (ListView)root.findViewById(R.id.list);
        View view = LayoutInflater.from(getActivity()).inflate(
				R.layout.tracks_list_header_layout, null);
        mTextViewCount = (TextView) view.findViewById(R.id.song_count_tv);
        mListView.addHeaderView(view, null, true);
        
        mSideBar = (SideBarView) root.findViewById(R.id.sidebar);
		mSideBar.setOnItemClickListener(new SideBarView.OnItemClickListener() {

			@Override
			public void onItemClick(String s) {
				if (mTrackAdapter.getIndexer().get(s) != null) {
					mListView.setSelection(mTrackAdapter.getIndexer().get(s)
							+ mListView.getHeaderViewsCount());
				}
			}
		});

        // Align the track list with the header, in other words,OCD.
//        TextView mHeader = (TextView)root.findViewById(R.id.title);
//        int eight = (int)getActivity().getResources().getDimension(
//                R.dimen.list_separator_padding_left_right);
//        mHeader.setPadding(eight, 0, 0, 0);

        // Set the header while in @TracksBrowser
//        String header = getActivity().getResources().getString(R.string.track_header);
//        int left = getActivity().getResources().getInteger(R.integer.listview_padding_left);
//        int right = getActivity().getResources().getInteger(R.integer.listview_padding_right);
//        ApolloUtils.listHeader(this, root, header);
//        ApolloUtils.setListPadding(this, mListView, left, 0, right, 0);

        // Hide the extra spacing from the Bottom ActionBar in the queue
        // Fragment in @AudioPlayerHolder
        Log.e(tag, "onCreateView() --- 1 - ");
        if (getArguments() != null) {
        	Log.e(tag, "onCreateView() --- 2 - ");
            mPlaylistId = getArguments().getLong(BaseColumns._ID);
            String mimeType = getArguments().getString(MIME_TYPE);
            if (Audio.Playlists.CONTENT_TYPE.equals(mimeType)) {
                switch ((int)mPlaylistId) {
                    case (int)PLAYLIST_QUEUE:
//                        LinearLayout emptyness = (LinearLayout)root.findViewById(R.id.empty_view);
//                        emptyness.setVisibility(View.GONE);
                }
            }
        }
        return root;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = new String[]{
                BaseColumns._ID, MediaColumns.TITLE, AudioColumns.ALBUM, AudioColumns.ARTIST, AudioColumns.ALBUM_ID
        };
        StringBuilder where = new StringBuilder();
        String sortOrder = Audio.Media.DEFAULT_SORT_ORDER;
        where.append(AudioColumns.IS_MUSIC + "=1").append(" AND " + MediaColumns.TITLE + " != ''");
        Uri uri = Audio.Media.EXTERNAL_CONTENT_URI;
        if (getArguments() != null) {
            mPlaylistId = getArguments().getLong(BaseColumns._ID);
            String mimeType = getArguments().getString(MIME_TYPE);
            Log.e(tag, "onCreateLoader() --- 1 mimeType = "+mimeType+", mPlaylistId = "+mPlaylistId);
            if (Audio.Playlists.CONTENT_TYPE.equals(mimeType)) {
                where = new StringBuilder();
                where.append(AudioColumns.IS_MUSIC + "=1");
                where.append(" AND " + MediaColumns.TITLE + " != ''");
                switch ((int)mPlaylistId) {
                    case (int)PLAYLIST_QUEUE:
                        uri = Audio.Media.EXTERNAL_CONTENT_URI;
                        long[] mNowPlaying = MusicUtils.getQueue();
                        if (mNowPlaying.length == 0)
                            return null;
                        where = new StringBuilder();
                        where.append(BaseColumns._ID + " IN (");
                        if (mNowPlaying == null || mNowPlaying.length <= 0)
                            return null;
                        for (long queue_id : mNowPlaying) {
                            where.append(queue_id + ",");
                        }
                        where.deleteCharAt(where.length() - 1);
                        where.append(")");
                        break;
                    case (int)PLAYLIST_FAVORITES:
                        long favorites_id = MusicUtils.getFavoritesId(getActivity());
                        projection = new String[] {
                                Playlists.Members._ID, Playlists.Members.AUDIO_ID,
                                MediaColumns.TITLE, AudioColumns.ALBUM, AudioColumns.ARTIST, AudioColumns.ALBUM_ID
                        };
                        uri = Playlists.Members.getContentUri(EXTERNAL, favorites_id);
                        sortOrder = Playlists.Members.DEFAULT_SORT_ORDER;
                        break;
                    default:
                        if (id < 0)
                            return null;
                        projection = new String[] {
                                Playlists.Members._ID, Playlists.Members.AUDIO_ID,
                                MediaColumns.TITLE, AudioColumns.ALBUM, AudioColumns.ARTIST,
                                AudioColumns.DURATION, AudioColumns.ALBUM_ID
                        };

                        uri = Playlists.Members.getContentUri(EXTERNAL, mPlaylistId);
                        sortOrder = Playlists.Members.DEFAULT_SORT_ORDER;
                        break;
                }
            } else if (Audio.Genres.CONTENT_TYPE.equals(mimeType)) {
                long genreId = getArguments().getLong(BaseColumns._ID);
                uri = Genres.Members.getContentUri(EXTERNAL, genreId);
                projection = new String[] {
                        BaseColumns._ID, MediaColumns.TITLE, AudioColumns.ALBUM,
                        AudioColumns.ARTIST
                };
                where = new StringBuilder();
                where.append(AudioColumns.IS_MUSIC + "=1").append(
                        " AND " + MediaColumns.TITLE + " != ''");
                sortOrder = Genres.Members.DEFAULT_SORT_ORDER;
            } else {
                if (Audio.Albums.CONTENT_TYPE.equals(mimeType)) {
                    long albumId = getArguments().getLong(BaseColumns._ID);
                    where.append(" AND " + AudioColumns.ALBUM_ID + "=" + albumId);
                    sortOrder = Audio.Media.TRACK + ", " + sortOrder;
                } else if (Audio.Artists.CONTENT_TYPE.equals(mimeType)) {
                    sortOrder = MediaColumns.TITLE;
                    long artist_id = getArguments().getLong(BaseColumns._ID);
                    where.append(" AND " + AudioColumns.ARTIST_ID + "=" + artist_id);
                }
            }
        }
        return new CursorLoader(getActivity(), uri, projection, where.toString(), null, sortOrder);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Check for database errors
        if (data == null) { 
            return;
        }
       
        if (getArguments() != null
                && Playlists.CONTENT_TYPE.equals(getArguments().getString(MIME_TYPE))
                && (getArguments().getLong(BaseColumns._ID) >= 0 || getArguments().getLong(
                        BaseColumns._ID) == PLAYLIST_FAVORITES)) {
            mMediaIdIndex = data.getColumnIndexOrThrow(Playlists.Members.AUDIO_ID);
            mTitleIndex = data.getColumnIndexOrThrow(MediaColumns.TITLE);
            mAlbumIndex = data.getColumnIndexOrThrow(AudioColumns.ALBUM);
            
//            mMediaIdIndex = data.getColumnIndexOrThrow(BaseColumns._ID);
//            mTitleIndex = data.getColumnIndexOrThrow(MediaColumns.TITLE);
            mArtistIndex = data.getColumnIndexOrThrow(AudioColumns.ARTIST);
//            mAlbumIndex = data.getColumnIndexOrThrow(AudioColumns.ALBUM);
            mAlbumId = data.getColumnIndexOrThrow(Playlists.Members.ALBUM_ID);
           
            // FIXME
//             mArtistIndex =
//             data.getColumnIndexOrThrow(Playlists.Members.ARTIST);
        } else if (getArguments() != null
                && Genres.CONTENT_TYPE.equals(getArguments().getString(MIME_TYPE))) {
            mMediaIdIndex = data.getColumnIndexOrThrow(BaseColumns._ID);
            mTitleIndex = data.getColumnIndexOrThrow(MediaColumns.TITLE);
            mArtistIndex = data.getColumnIndexOrThrow(AudioColumns.ARTIST);
            mAlbumIndex = data.getColumnIndexOrThrow(AudioColumns.ALBUM);
            
            
        } else if (getArguments() != null
                && Artists.CONTENT_TYPE.equals(getArguments().getString(MIME_TYPE))) {
            mTitleIndex = data.getColumnIndexOrThrow(MediaColumns.TITLE);
            // mArtistIndex is "line2" of the ListView
            mArtistIndex = data.getColumnIndexOrThrow(AudioColumns.ALBUM);
        } else if (getArguments() != null
                && Albums.CONTENT_TYPE.equals(getArguments().getString(MIME_TYPE))) {
            mMediaIdIndex = data.getColumnIndexOrThrow(BaseColumns._ID);
            mTitleIndex = data.getColumnIndexOrThrow(MediaColumns.TITLE);
            mArtistIndex = data.getColumnIndexOrThrow(AudioColumns.ARTIST);
           
        } else {
        	 mMediaIdIndex = data.getColumnIndexOrThrow(BaseColumns._ID);
             mTitleIndex = data.getColumnIndexOrThrow(MediaColumns.TITLE);
             mArtistIndex = data.getColumnIndexOrThrow(AudioColumns.ARTIST);
             mAlbumIndex = data.getColumnIndexOrThrow(AudioColumns.ALBUM);
             mAlbumId = data.getColumnIndexOrThrow(AudioColumns.ALBUM_ID);
        }
        
        SortCursor sortCursor = new SortCursor(data, MediaColumns.TITLE);
        
        mTrackAdapter.swapCursor(sortCursor);
        updateCountTextView(sortCursor.getSortList().size());
        mListView.invalidateViews();
        mCursor = sortCursor;
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        if (mTrackAdapter != null)
            mTrackAdapter.swapCursor(null);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putAll(getArguments() != null ? getArguments() : new Bundle());
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
    	 AdapterContextMenuInfo mi = (AdapterContextMenuInfo)menuInfo;
         if (mi.position == 0) {
         	super.onCreateContextMenu(menu, v, menuInfo);
         	
         	return;
         }
         
//         LayoutInflater lInflater = getActivity().getLayoutInflater();
//         if ( lInflater.getFactory() == null ) {
//             lInflater.setFactory(new MenuColorFix());
//         }          
         
         MenuColorFix.addMenu(menu, 0, PLAY_SELECTION, 0, getResources().getString(R.string.play_all));
         MenuColorFix.addMenu(menu, 0, ADD_TO_PLAYLIST, 0, getResources().getString(R.string.add_to_playlist));         
//        menu.add(0, PLAY_SELECTION, 0, getResources().getString(R.string.play_all));
//        menu.add(0, ADD_TO_PLAYLIST, 0, getResources().getString(R.string.add_to_playlist));
//        if (TelephonyManager.getDefault().isMultiSimEnabled()) {
//            int[] ringtones = { USE_AS_RINGTONE, USE_AS_RINGTONE_2 };
//            int[] menuStrings = { R.string.ringtone_menu_1,
//                                  R.string.ringtone_menu_2 };
//            for (int i = 0; i < TelephonyManager.getDefault().getPhoneCount(); i++) {
//                menu.add(0, ringtones[i], 0, menuStrings[i]);
//            }
//        } else {
//            menu.add(0, USE_AS_RINGTONE, 0, R.string.ringtone_menu);
//        }
//        menu.add(0, USE_AS_RINGTONE, 0, getResources().getString(R.string.use_as_ringtone));
//        menu.add(0, REAL_REMOVE, 0, getResources().getString(R.string.real_remove));
//        if (mEditMode) {
//            menu.add(0, REMOVE, 0, getResources().getString(R.string.remove));
//        }
         //MenuColorFix.addMenu(menu, 0, USE_AS_RINGTONE, 0, getResources().getString(R.string.use_as_ringtone));
         MenuColorFix.addMenu(menu, 0, REAL_REMOVE, 0, getResources().getString(R.string.real_remove));
         if (mEditMode) {
        	 MenuColorFix.addMenu(menu, 0, REMOVE, 0, getResources().getString(R.string.remove));
         }         
         
//        menu.add(0, SEARCH, 0, getResources().getString(R.string.search));

        mSelectedPosition = mi.position-mListView.getHeaderViewsCount();
        mCursor.moveToPosition(mSelectedPosition);

        try {
            mSelectedId = mCursor.getLong(mMediaIdIndex);
        } catch (IllegalArgumentException ex) {
            mSelectedId = mi.id;
        }

        String title = mCursor.getString(mTitleIndex);
        menu.setHeaderTitle(title);
        super.onCreateContextMenu(menu, v, menuInfo);
    }
    public static  Cursor tomCursor;
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case PLAY_SELECTION:
                int position = mSelectedPosition;
                MusicUtils.playAll(getActivity(), mCursor, position);
                break;
            case ADD_TO_PLAYLIST: {
//                Intent intent = new Intent(INTENT_ADD_TO_PLAYLIST);
                long[] list = new long[] {
                    mSelectedId
                };
                new LongClickDialog(getContext(),INTENT_ADD_TO_PLAYLIST,list);
//                intent.putExtra(INTENT_PLAYLIST_LIST, list);
//                getActivity().startActivity(intent);
                break;
            }
//            case USE_AS_RINGTONE:
//                // Set the system setting to make this the current ringtone
//                MusicUtils.setRingtone(getActivity(), mSelectedId);
//                break;
//
//            case USE_AS_RINGTONE_2:
//                // Set the system setting to make this the current ringtone for SUB_1
//                MusicUtils.setRingtone(getActivity(), mSelectedId, MusicUtils.RINGTONE_SUB_1);
//                break;
            case USE_AS_RINGTONE:
                MusicUtils.setRingtone(getActivity(), Integer.toString((int)mSelectedId));
                break;
            case REAL_REMOVE:
                MusicUtils.RealdeletSong(getActivity(), mSelectedId,mSelectedPosition);
                break;
            case REMOVE: {
                removePlaylistItem(mSelectedPosition);
                break;
            }
            case SEARCH: {
                MusicUtils.doSearch(getActivity(), mCursor, mTitleIndex);
                break;
            }
            default:
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
//    	Log.e(tag, "onItemClick() -1- position = "+position);
//        if(islongclick)
//            return;
    	if ((mCursor == null) || (mCursor.getCount() == 0)) {
            return;
        }
    	if (position == 0) {
            if(isFastClickShuffleAll()){
                //fast click shuffleAll,then ignore this click
                return;
            }
            tomCursor= mCursor;
            MusicUtils.shuffleAll(getActivity(), mCursor);
    		return;
    	}
    	
        if (mCursor instanceof NowPlayingCursor) {
            if (MusicUtils.mService != null) {
                MusicUtils.setQueuePosition(position-mListView.getHeaderViewsCount());
                MusicUtils.getSongPathName(getActivity(),id,position-mListView.getHeaderViewsCount());
                return;
            }
        }
        MusicUtils.playAll(getActivity(), mCursor, position-mListView.getHeaderViewsCount());
        tomCursor= mCursor;
    }


    /**
     * Update the list as needed
     */
    private final BroadcastReceiver mMediaStatusReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
//        	Log.e(tag, "onReceive() --- intent.getAction() = "+intent.getAction()+", mPlaylistId = "+mPlaylistId);
            if (mListView != null) {
                mTrackAdapter.notifyDataSetChanged();
                // Scroll to the currently playing track in the queue
                if (mPlaylistId == PLAYLIST_QUEUE)
                    mListView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mListView.setSelection(MusicUtils.getQueuePosition());
                        }
                    }, 100);
            }
        }

    };

    
	
    @Override
    public void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ApolloService.META_CHANGED);
        filter.addAction(ApolloService.QUEUE_CHANGED);
        filter.addAction(ApolloService.PLAYSTATE_CHANGED);
        
        getActivity().registerReceiver(mMediaStatusReceiver, filter);
    }

    @Override
    public void onStop() {
        getActivity().unregisterReceiver(mMediaStatusReceiver);
        
        super.onStop();
    }
    
    /**
	 */
	private void updateCountTextView(int songsCount) {
		
		String template = App.getInstance().getResources().getString(R.string.tracks_list_header_text);
        String text = String.format(template, songsCount);
//		mTextViewCount.setText(text);
	}

    /**
     */
    private void removePlaylistItem(int which) {
        mCursor.moveToPosition(which);
        long id = mCursor.getLong(mMediaIdIndex);
        if (mPlaylistId >= 0) {
            Uri uri = Playlists.Members.getContentUri(EXTERNAL, mPlaylistId);
            getActivity().getContentResolver().delete(uri, Playlists.Members.AUDIO_ID + "=" + id,
                    null);
        } else if (mPlaylistId == PLAYLIST_QUEUE) {
            MusicUtils.removeTrack(id);
            reloadQueueCursor();
        } else if (mPlaylistId == PLAYLIST_FAVORITES) {
            MusicUtils.removeFromFavorites(getActivity(), id);
        }
        updateCountTextView(((SortCursor)mCursor).getSortList().size());
        mListView.invalidateViews();
    }

    /**
     * Reload the queue after we remove a track
     */
    private void reloadQueueCursor() {
        if (mPlaylistId == PLAYLIST_QUEUE) {
            String[] cols = new String[] {
                    BaseColumns._ID, MediaColumns.TITLE, MediaColumns.DATA, AudioColumns.ALBUM,
                    AudioColumns.ARTIST, AudioColumns.ARTIST_ID
            };
            StringBuilder selection = new StringBuilder();
            selection.append(AudioColumns.IS_MUSIC + "=1");
            selection.append(" AND " + MediaColumns.TITLE + " != ''");
            Uri uri = Audio.Media.EXTERNAL_CONTENT_URI;
            long[] mNowPlaying = MusicUtils.getQueue();
            if (mNowPlaying.length == 0) {
            }
            selection = new StringBuilder();
            selection.append(BaseColumns._ID + " IN (");
            for (int i = 0; i < mNowPlaying.length; i++) {
                selection.append(mNowPlaying[i]);
                if (i < mNowPlaying.length - 1) {
                    selection.append(",");
                }
            }
            selection.append(")");
            mCursor = MusicUtils.query(getActivity(), uri, cols, selection.toString(), null, null);
            
            SortCursor sortCursor = new SortCursor(mCursor, MediaColumns.TITLE);
            mTrackAdapter.swapCursor(sortCursor);
            updateCountTextView(sortCursor.getSortList().size());
            mListView.invalidateViews();
            mCursor = sortCursor;
        }
    }

    /**
     * Check if we're viewing the contents of a playlist
     */
    public void isEditMode() {
        if (getArguments() != null) {
            String mimetype = getArguments().getString(MIME_TYPE);
            if (Audio.Playlists.CONTENT_TYPE.equals(mimetype)) {
                mPlaylistId = getArguments().getLong(BaseColumns._ID);
                switch ((int)mPlaylistId) {
                    case (int)PLAYLIST_QUEUE:
                        mEditMode = true;
                        break;
                    case (int)PLAYLIST_FAVORITES:
                        mEditMode = true;
                        break;
                    default:
                        if (mPlaylistId > 0) {
                            mEditMode = true;
                        }
                        break;
                }
            }
        }
    }

    private void showdialog(String name) {
        Log.i("name", "showdialog: "+name);
    }
}
