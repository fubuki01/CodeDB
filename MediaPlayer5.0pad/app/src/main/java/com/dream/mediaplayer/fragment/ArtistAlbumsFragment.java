/**
 * 
 */

package com.dream.mediaplayer.fragment;

import se.emilsjolander.stickylistheaders.SideBarView;

import com.dream.mediaplayer.R;
import com.dream.mediaplayer.activity.AlbumOfTracksActivity;
import com.dream.mediaplayer.activity.LongClickDialog;
import com.dream.mediaplayer.helpers.utils.MenuColorFix;
import com.dream.mediaplayer.helpers.utils.MusicUtils;
import com.dream.mediaplayer.helpers.utils.SortCursor;
import com.dream.mediaplayer.service.ApolloService;
import com.dream.mediaplayer.ui.adapters.ArtistAlbumAdapter;

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
import android.provider.BaseColumns;
import android.provider.MediaStore.Audio;
import android.provider.MediaStore.Audio.AlbumColumns;
import android.util.Pair;
import android.view.*;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import static com.dream.mediaplayer.Constants.*;

/**
 * @author Andrew Neal
 * @Note This is used in the @TracksBrowser after touching an artist from @ArtistsFragment
 */
public class ArtistAlbumsFragment extends Fragment implements LoaderCallbacks<Cursor>,
        OnItemClickListener {

    // Adapter
    private ArtistAlbumAdapter mArtistAlbumAdapter;

    // Audio columns
    public static int mAlbumIdIndex, mAlbumNameIndex, mSongCountIndex, mArtistNameIndex;

    // ListView
    private ListView mListView;

    // Options
    private final int PLAY_SELECTION = 15;

    private final int ADD_TO_PLAYLIST = 16;

    private final int SEARCH = 17;

    // Album ID
    private String mCurrentAlbumId;

    // Cursor
    private Cursor mCursor;
    private SideBarView mSideBar;

    public ArtistAlbumsFragment() {
    }

    @SuppressLint({"NewApi", "ValidFragment"})
    public ArtistAlbumsFragment(Bundle args) {
        setArguments(args);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // AlbumAdapter
        mArtistAlbumAdapter = new ArtistAlbumAdapter(getActivity(), R.layout.artist_albums_list_item_layout, null,
                new String[] {}, new int[] {}, 0);
        mListView.setOnCreateContextMenuListener(this);
        mListView.setAdapter(mArtistAlbumAdapter);
        mListView.setOnItemClickListener(this);

        // Important!
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.artist_albums_fragment_layout, container, false);
        mListView = (ListView)root.findViewById(R.id.list);
        mSideBar = (SideBarView) root.findViewById(R.id.sidebar);
		mSideBar.setOnItemClickListener(new SideBarView.OnItemClickListener() {

			@Override
			public void onItemClick(String s) {
				if (mArtistAlbumAdapter.getIndexer().get(s) != null) {
					mListView.setSelection(mArtistAlbumAdapter.getIndexer().get(s)
							+ mListView.getHeaderViewsCount());
				}
			}
		});
        return root;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                BaseColumns._ID, AlbumColumns.ALBUM, AlbumColumns.NUMBER_OF_SONGS,
                AlbumColumns.ARTIST
        };
        if (getArguments() != null) {
            long artistId = getArguments().getLong((BaseColumns._ID));
            Uri uri = Audio.Artists.Albums.getContentUri(EXTERNAL, artistId);
            String sortOrder = Audio.Albums.DEFAULT_SORT_ORDER;
            return new CursorLoader(getActivity(), uri, projection, null, null, sortOrder);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Check for database errors
        if (data == null) {
            return;
        }

        mAlbumIdIndex = data.getColumnIndexOrThrow(BaseColumns._ID);
        mAlbumNameIndex = data.getColumnIndexOrThrow(AlbumColumns.ALBUM);
        mSongCountIndex = data.getColumnIndexOrThrow(AlbumColumns.NUMBER_OF_SONGS);
        mArtistNameIndex = data.getColumnIndexOrThrow(AlbumColumns.ARTIST);
        SortCursor sortCursor = new SortCursor(data, AlbumColumns.ALBUM);
        mArtistAlbumAdapter.swapCursor(sortCursor);
        mListView.invalidateViews();
        mCursor = sortCursor;
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        if (mArtistAlbumAdapter != null)
            mArtistAlbumAdapter.swapCursor(null);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putAll(getArguments() != null ? getArguments() : new Bundle());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        tracksBrowser(v, id);
    }

    /**
     * Update the list as needed
     */
    private final BroadcastReceiver mMediaStatusReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (mListView != null) {
                mArtistAlbumAdapter.notifyDataSetChanged();
            }
        }

    };

    @Override
    public void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ApolloService.META_CHANGED);
        filter.addAction(ApolloService.PLAYSTATE_CHANGED);
        getActivity().registerReceiver(mMediaStatusReceiver, filter);
    }

    @Override
    public void onStop() {
        getActivity().unregisterReceiver(mMediaStatusReceiver);
        super.onStop();
    }

    /**
     * @param index
     * @param id
     */
    private void tracksBrowser(View v, long id) {
        String artistName = mCursor.getString(mArtistNameIndex);
        String albumName = mCursor.getString(mAlbumNameIndex);
        String albumId = mCursor.getString(mAlbumIdIndex);

        Bundle bundle = new Bundle();
        bundle.putString(MIME_TYPE, Audio.Albums.CONTENT_TYPE);
        bundle.putString(ALBUM_KEY, albumName);
        bundle.putString(ALBUM_ID_KEY, albumId);
        bundle.putString(ARTIST_KEY, artistName);
        bundle.putLong(BaseColumns._ID, id);

//        Intent intent = new Intent(Intent.ACTION_VIEW);
////        intent.setClass(getActivity(), TracksBrowser.class);
//        intent.putExtras(bundle);
//        getActivity().startActivity(intent);
        
        Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setClass(getActivity(), AlbumOfTracksActivity.class);
		intent.putExtras(bundle);
		getActivity().startActivity(intent);
		
//		MusicUtils.screenTransitAnimByPair(getActivity(), 
//				intent, null);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {     
    	MenuColorFix.addMenu(menu, 0, PLAY_SELECTION, 0, getResources().getString(R.string.play_all));
    	MenuColorFix.addMenu(menu, 0, ADD_TO_PLAYLIST, 0, getResources().getString(R.string.add_to_playlist));
        
//    	menu.add(0, PLAY_SELECTION, 0, getResources().getString(R.string.play_all));
//        menu.add(0, ADD_TO_PLAYLIST, 0, getResources().getString(R.string.add_to_playlist));
//        menu.add(0, SEARCH, 0, getResources().getString(R.string.search));

        mCurrentAlbumId = mCursor.getString(mCursor.getColumnIndexOrThrow(BaseColumns._ID));

//        menu.setHeaderView(setHeaderLayout());
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case PLAY_SELECTION: {
                long[] list = MusicUtils.getSongListForAlbum(getActivity(),
                        Long.parseLong(mCurrentAlbumId));
                MusicUtils.playAll(getActivity(), list, 0);
                break;
            }
            case ADD_TO_PLAYLIST: {
//                Intent intent = new Intent(INTENT_ADD_TO_PLAYLIST);
                long[] list = MusicUtils.getSongListForAlbum(getActivity(),
                        Long.parseLong(mCurrentAlbumId));
                new LongClickDialog(getContext(),INTENT_ADD_TO_PLAYLIST,list);
//                intent.putExtra(INTENT_PLAYLIST_LIST, list);
//                getActivity().startActivity(intent);
                break;
            }
            case SEARCH: {
                MusicUtils.doSearch(getActivity(), mCursor, mAlbumNameIndex);
                break;
            }
            default:
                break;
        }
        return super.onContextItemSelected(item);
    }
}
