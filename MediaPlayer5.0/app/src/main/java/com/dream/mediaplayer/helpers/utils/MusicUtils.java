
package com.dream.mediaplayer.helpers.utils;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.SearchManager;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.BaseColumns;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio;
import android.provider.MediaStore.Audio.AlbumColumns;
import android.provider.MediaStore.Audio.ArtistColumns;
import android.provider.MediaStore.Audio.AudioColumns;
import android.provider.MediaStore.Audio.Genres;
import android.provider.MediaStore.Audio.GenresColumns;
import android.provider.MediaStore.Audio.Playlists;
import android.provider.MediaStore.Audio.PlaylistsColumns;
import android.provider.MediaStore.MediaColumns;
import android.provider.Settings;
import android.text.format.Time;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.dream.mediaplayer.IApolloService;
import com.dream.mediaplayer.R;
import com.dream.mediaplayer.activity.AddTracksToQueueActivity;
import com.dream.mediaplayer.activity.SearchActivity;
import com.dream.mediaplayer.service.ApolloService;
import com.dream.mediaplayer.service.ServiceBinder;
import com.dream.mediaplayer.service.ServiceToken;
import com.dream.mediaplayer.views.MarqueeTextView;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static android.content.ContentValues.TAG;
import static com.dream.mediaplayer.Constants.ADD_TO_PALYLIST;
import static com.dream.mediaplayer.Constants.EXTERNAL;
import static com.dream.mediaplayer.Constants.GENRES_DB;
import static com.dream.mediaplayer.Constants.PLAYLIST_NAME_FAVORITES;
import static com.dream.mediaplayer.Constants.PLAYLIST_NEW;
import static com.dream.mediaplayer.Constants.PLAYLIST_QUEUE;

//import android.drm.DrmHelper;

/**
 * Various methods used to help with specific music statements
 */
public class MusicUtils {

    private final static String tag = "MusicUtils --- ";

    // Used to make number of albums/songs/time strings
    private final static StringBuilder sFormatBuilder = new StringBuilder();

    private final static Formatter sFormatter = new Formatter(sFormatBuilder, Locale.getDefault());

    public static IApolloService mService = null;

    private static HashMap<Context, ServiceBinder> sConnectionMap = new HashMap<Context, ServiceBinder>();

    private final static long[] sEmptyList = new long[0];

    private static final Object[] sTimeArgs = new Object[5];

    private static ContentValues[] sContentValuesCache = null;

    public static boolean mPlayAllFromMenu = false;

    public final static int RINGTONE_SUB_0 = 0;
    public final static int RINGTONE_SUB_1 = 1;

    public interface Defs {
        public final static int OPEN_URL = 0;
        public final static int ADD_TO_PLAYLIST = 1;
        public final static int USE_AS_RINGTONE = 2;
        public final static int PLAYLIST_SELECTED = 3;
        public final static int NEW_PLAYLIST = 4;
        public final static int PLAY_SELECTION = 5;
        public final static int GOTO_START = 6;
        public final static int GOTO_PLAYBACK = 7;
        public final static int PARTY_SHUFFLE = 8;
        public final static int SHUFFLE_ALL = 9;
        public final static int DELETE_ITEM = 10;
        public final static int SCAN_DONE = 11;
        public final static int QUEUE = 12;
        public final static int EFFECTS_PANEL = 13;
        public final static int MORE_MUSIC = 14;
        public final static int MORE_VIDEO = 15;
        public final static int USE_AS_RINGTONE_2 = 16;
        public final static int DRM_LICENSE_INFO = 17;
        public final static int CLOSE = 18;
        public final static int CHILD_MENU_BASE = 19;// this should be the last item;
        public final static int REAL_REMOVE = 20;
        public final static int REMOVE = 21;
        public final static int SEARCH = 22;
    }

    /**
     * @param context
     * @return
     */
    public static ServiceToken bindToService(Activity context) {
        return bindToService(context, null);
}

    /**
     * @param context
     * @param callback
     * @return
     */
    public static ServiceToken bindToService(Context context, ServiceConnection callback) {
        Activity realActivity = ((Activity)context).getParent();
        if (realActivity == null) {
            realActivity = (Activity)context;
        }
        ContextWrapper cw = new ContextWrapper(realActivity);
        cw.startService(new Intent(cw, ApolloService.class));
        ServiceBinder sb = new ServiceBinder(callback);
        if (cw.bindService((new Intent()).setClass(cw, ApolloService.class), sb, 0)) {
            sConnectionMap.put(cw, sb);
            return new ServiceToken(cw);
        }
        return null;
    }

    /**
     * @param token
     */
    public static void unbindFromService(ServiceToken token) {
        if (token == null) {
            return;
        }
        ContextWrapper cw = token.mWrappedContext;
        ServiceBinder sb = sConnectionMap.remove(cw);
        if (sb == null) {
            return;
        }
        cw.unbindService(sb);
        if (sConnectionMap.isEmpty()) {
            mService = null;
        }
    }

    /**
     * @param numalbums
     * @param numsongs
     * @param isUnknown
     * @return a string based on the number of albums for an artist or songs for
     *         an album
     */
    public static String makeAlbumsLabel(Context mContext, int numalbums, int numsongs,
                                         boolean isUnknown) {

        StringBuilder songs_albums = new StringBuilder();

        Resources r = mContext.getResources();
        if (isUnknown) {
            String f = r.getQuantityText(R.plurals.Nsongs, numsongs).toString();
            sFormatBuilder.setLength(0);
            sFormatter.format(f, Integer.valueOf(numsongs));
            songs_albums.append(sFormatBuilder);
        } else {
            String f = r.getQuantityText(R.plurals.Nalbums, numalbums).toString();
            sFormatBuilder.setLength(0);
            sFormatter.format(f, Integer.valueOf(numalbums));
            songs_albums.append(sFormatBuilder);
            songs_albums.append("\n");
        }
        return songs_albums.toString();
    }

    /**
     * @param mContext
     * @return
     */
    public static int getCardId(Context mContext) {

        ContentResolver res = mContext.getContentResolver();
        Cursor c = res.query(Uri.parse("content://media/external/fs_id"), null, null, null, null);
        int id = -1;
        if (c != null) {
            c.moveToFirst();
            id = c.getInt(0);
            c.close();
        }
        return id;
    }

    /**
     * @param context
     * @param uri
     * @param projection
     * @param selection
     * @param selectionArgs
     * @param sortOrder
     * @param limit
     * @return
     */
    public static Cursor query(Context context, Uri uri, String[] projection, String selection,
                               String[] selectionArgs, String sortOrder, int limit) {
        try {
            ContentResolver resolver = context.getContentResolver();
            if (resolver == null) {
                return null;
            }
            if (limit > 0) {
                uri = uri.buildUpon().appendQueryParameter("limit", "" + limit).build();
            }
            return resolver.query(uri, projection, selection, selectionArgs, sortOrder);
        } catch (UnsupportedOperationException ex) {
            return null;
        }
    }

    /**
     * @param context
     * @param uri
     * @param projection
     * @param selection
     * @param selectionArgs
     * @param sortOrder
     * @return
     */
    public static Cursor query(Context context, Uri uri, String[] projection, String selection,
                               String[] selectionArgs, String sortOrder) {
        return query(context, uri, projection, selection, selectionArgs, sortOrder, 0);
    }

    /**
     * @param context
     * @param cursor
     */
    public static void shuffleAll(Context context, Cursor cursor) {
        playAll(context, cursor, 0, true);
    }

    /**
     * @param context
     * @param cursor
     */
    public static void playAll(Context context, Cursor cursor) {
        mPlayAllFromMenu = true;
        playAll(context, cursor, 0, false);
    }

    /**
     * @param context
     * @param cursor
     * @param position
     */
    public static void playAll(Context context, Cursor cursor, int position) {
        playAll(context, cursor, position, false);
    }

    /**
     * @param context
     * @param list
     * @param position
     */
    public static void playAll(Context context, long[] list, int position) {
        playAll(context, list, position, false);
    }

    /**
     * @param context
     * @param cursor
     * @param position
     * @param force_shuffle
     */
    private static void playAll(Context context, Cursor cursor, int position, boolean force_shuffle) {

        long[] list = getSongListForCursor(cursor);
        playAll(context, list, position, force_shuffle);
    }

    /**
     * @param cursor
     * @return
     */
    public static long[] getSongListForCursor(Cursor cursor) {
        if (cursor == null) {
            return sEmptyList;
        }
        int len = cursor.getCount();
        long[] list = new long[len];
        cursor.moveToFirst();
        int colidx = -1;
        try {
            colidx = cursor.getColumnIndexOrThrow(Audio.Playlists.Members.AUDIO_ID);
        } catch (IllegalArgumentException ex) {
            colidx = cursor.getColumnIndexOrThrow(BaseColumns._ID);
        }
        for (int i = 0; i < len; i++) {
            list[i] = cursor.getLong(colidx);
            cursor.moveToNext();
        }
        return list;
    }

    /**
     * 全部随机播放
     * @param context
     * @param list
     * @param position
     * @param force_shuffle
     */
    private static void playAll(Context context, long[] list, int position, boolean force_shuffle) {
        if (list.length == 0 || mService == null) {
            Log.d(tag, "attempt to play empty song list, list.length = "+list.length+", mService = "+mService);
            // Don't try to play empty playlists. Nothing good will come of it.
            String message = context.getString(R.string.emptyplaylist, list.length);
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            if (force_shuffle) {
                mService.setShuffleMode(ApolloService.SHUFFLE_NORMAL);
                mService.setRepeatMode(ApolloService.REPEAT_NONE);
                //  if the mode is SHUFFLE_NORMAL,change the repeat mode to REPEAT_NONE
                //  change the repeat mode's ImageButton
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.activity_play,null);
                ImageButton mRepeat = (ImageButton) view.findViewById(R.id.btn_repeat);
                mRepeat.setImageResource(R.drawable.btn_play_repeat_close_selector);

                //If the repeat mode is REPEAT_CURRENT, we should change mode to REPEAT_ALL
//                if (mService.getRepeatMode() == ApolloService.REPEAT_CURRENT) {
//                	mService.setRepeatMode(ApolloService.REPEAT_ALL);
//                }

            }

            if (mPlayAllFromMenu){
                mService.setShuffleMode(ApolloService.SHUFFLE_NONE);

                //If the repeat mode is REPEAT_CURRENT, we should change mode to REPEAT_ALL
//                if (mService.getRepeatMode() == ApolloService.REPEAT_CURRENT) {
//                	mService.setRepeatMode(ApolloService.REPEAT_ALL);
//                }

                mPlayAllFromMenu = false;
            }

            long curid = mService.getAudioId();
            int curpos = mService.getQueuePosition();
            Log.e("onItemClick", "playAll: "+curid+"    "+curpos+"    "+position+"    "+list[position]);
            //lcl
            if (position != -1 && curpos == position && curid == list[position]) {
                // The selected file is the file that's currently playing;
                // figure out if we need to restart with a new playlist,
                // or just launch the playback activity.
                long[] playlist = mService.getQueue();
                if (Arrays.equals(list, playlist)) {
                    Log.e("onItemClick", "playAll: "+list+"    "+playlist);
                    // we don't need to set a new list, but we should resume
                    // playback if needed
                    mService.open(list, force_shuffle ? -1 : position);
//                    Log.e("nihao", "playAll: "+list.length+"   "+position);
                    mService.play();
                    return;
                }
            }
            Log.e("onItemClick", "playAll: "+"没走"+position);
            if (position < 0) {
                position = 0;
            }
            Log.e("onItemClick", "playAll: "+list.length+"   "+(force_shuffle ? -1 : position));
            if (list.length<=2){
                mService.open(list, force_shuffle ? -0 : position);
            }else {
                mService.open(list, force_shuffle ? -1 : position);
            }

            mService.play();

        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * @return
     */
    public static long[] getQueue() {

        if (mService == null)
            return sEmptyList;

        try {
            return mService.getQueue();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return sEmptyList;
    }

    /**
     * @param context
     * @param name
     * @param def
     * @return number of weeks used to create the Recent tab
     */
    public static int getIntPref(Context context, String name, int def) {
        SharedPreferences prefs = context.getSharedPreferences(context.getPackageName(),
                Context.MODE_PRIVATE);
        return prefs.getInt(name, def);
    }

    /**
     * @param context
     * @param id
     * @return
     */
    public static long[] getSongListForArtist(Context context, long id) {
        final String[] projection = new String[] {
                BaseColumns._ID
        };
        String selection = AudioColumns.ARTIST_ID + "=" + id + " AND " + AudioColumns.IS_MUSIC
                + "=1";
        String sortOrder = AudioColumns.ALBUM_KEY + "," + AudioColumns.TRACK;
        Uri uri = Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = query(context, uri, projection, selection, null, sortOrder);
        if (cursor != null) {
            long[] list = getSongListForCursor(cursor);
            cursor.close();
            return list;
        }
        return sEmptyList;
    }

    /**
     * @param context
     * @param id
     * @return
     */
    public static long[] getSongListForAlbum(Context context, long id) {
        final String[] projection = new String[] {
                BaseColumns._ID
        };
        String selection = AudioColumns.ALBUM_ID + "=" + id + " AND " + AudioColumns.IS_MUSIC
                + "=1";
        String sortOrder = AudioColumns.TRACK;
        Uri uri = Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = query(context, uri, projection, selection, null, sortOrder);
        if (cursor != null) {
            long[] list = getSongListForCursor(cursor);
            cursor.close();
            return list;
        }
        return sEmptyList;
    }

    /**
     * @param context
     * @param id
     * @return
     */
    public static long[] getSongListForGenre(Context context, long id) {
        String[] projection = new String[] {
                BaseColumns._ID
        };
        StringBuilder selection = new StringBuilder();
        selection.append(AudioColumns.IS_MUSIC + "=1");
        selection.append(" AND " + MediaColumns.TITLE + "!=''");
        Uri uri = Genres.Members.getContentUri(EXTERNAL, id);
        Cursor cursor = context.getContentResolver().query(uri, projection, selection.toString(),
                null, null);
        if (cursor != null) {
            long[] list = getSongListForCursor(cursor);
            cursor.close();
            return list;
        }
        return sEmptyList;
    }

    /**
     * @param context
     * @param id
     * @return
     */
    public static long[] getSongListForPlaylist(Context context, long id) {
        final String[] projection = new String[] {
                Audio.Playlists.Members.AUDIO_ID
        };
        String sortOrder = Playlists.Members.DEFAULT_SORT_ORDER;
        Uri uri = Playlists.Members.getContentUri(EXTERNAL, id);
        Cursor cursor = query(context, uri, projection, null, null, sortOrder);
        if (cursor != null) {
            long[] list = getSongListForCursor(cursor);
            cursor.close();
            return list;
        }
        return sEmptyList;
    }

    /**
     * @param context
     * @param name
     * @return
     */
    public static long createPlaylist(Context context, String name) {

        if (name != null && name.length() > 0) {
            ContentResolver resolver = context.getContentResolver();
            String[] cols = new String[] {
                    PlaylistsColumns.NAME
            };
           //String whereclause = PlaylistsColumns.NAME + " = '" + name + "'";
            String whereclause = PlaylistsColumns.NAME + " = ?";
            Cursor cur = resolver.query(Audio.Playlists.EXTERNAL_CONTENT_URI, cols, whereclause,
                    new String[]{name}, null);
            if (cur == null) {
                return -1;
            }

            if (cur.getCount() <= 0) {
                cur.close();

                ContentValues values = new ContentValues(1);
                values.put(PlaylistsColumns.NAME, name);
                Uri uri = resolver.insert(Audio.Playlists.EXTERNAL_CONTENT_URI, values);
                if (uri != null) {
                    return Long.parseLong(uri.getLastPathSegment());
                } else {
                    return -1;
                }
            }

            cur.close();

            return -1;
        }
        return -1;
    }

    /**
     * @param context
     * @return
     */
    public static long getFavoritesId(Context context) {
        long favorites_id = -1;
        String favorites_where = PlaylistsColumns.NAME + "='" +R.string.favorite  + "'";
        String[] favorites_cols = new String[] {
                BaseColumns._ID
        };
        Uri favorites_uri = Audio.Playlists.EXTERNAL_CONTENT_URI;
        Cursor cursor = query(context, favorites_uri, favorites_cols, favorites_where, null, null);
        if (cursor == null) {
            return favorites_id;
        }

        if (cursor.getCount() <= 0) {
            cursor.close();

            favorites_id = createPlaylist(context, PLAYLIST_NAME_FAVORITES);
        } else {
            cursor.moveToFirst();
            favorites_id = cursor.getLong(0);
            cursor.close();
        }
        return favorites_id;
    }


    /**
     * @param context
     * @param id
     */
    public static void setRingtone(Context context,String id){
        ContentResolver contentResolver=context.getContentResolver();
        Uri uri=ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, Long.parseLong(id));
        try {
            ContentValues values = new ContentValues(2);
            values.put(MediaStore.Audio.Media.IS_RINGTONE, true);
            values.put(MediaStore.Audio.Media.IS_ALARM, false);
            contentResolver.update(uri, values, null, null);
        } catch (UnsupportedOperationException ex) {
            return;
        }

        String[] cols = new String[] {
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.TITLE
        };

        String where = MediaStore.Audio.Media._ID + "=" + id;
        Cursor cursor = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                cols, where , null, null);
        try {
            if (cursor != null && cursor.getCount() == 1) {
                cursor.moveToFirst();
                Settings.System.putString(contentResolver, Settings.System.RINGTONE, uri.toString());
                Toast.makeText(context, "设置成功！", Toast.LENGTH_SHORT).show();
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }


    public static void RealdeletSong(Context context,long id,int postion){
        ContentResolver contentResolver=context.getContentResolver();
        String[] projection=new String[]{
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.DATA
        };
        Cursor cursor=contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection, "_id="+id, null, null);

        int count=0;
        if(cursor != null){
            count=cursor.getCount();
            // step 1: remove selected tracks from the current playlist, as well
            // as from the album art cache
            try {
                cursor.moveToFirst();
                while (! cursor.isAfterLast()) {
                    // remove from current playlist
                    long _id = cursor.getLong(0);
                    mService.removeTrack(_id);
                    // remove from album art cache
//                    long artIndex = c.getLong(2);
//                    synchronized(sArtCache) {
//                        sArtCache.remove(artIndex);
//                    }
                    cursor.moveToNext();
                }
            } catch (RemoteException ex) {
            }

            // step 2: remove selected tracks from the database
            contentResolver.delete(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, "_id="+id, null);

            // step 3: remove files from card
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                final String name=cursor.getString(1);
                final File file=new File(name);
                try {
//					if(!file.delete()){
//						Log.e("MusicUtils --- ", "RealdeletSong() --- error !");
//					}

                    new Thread(new Runnable(){
                        public void run(){
                            if (!file.delete()) {
                                // I'm not sure if we'd ever get here (deletion would
                                // have to fail, but no exception thrown)
                                Log.e("MusicUtils", "Failed to delete file " + name);
                            }
                        }
                    }).start();
                    cursor.moveToNext();
                } catch (SecurityException e) {
                    // TODO: handle exception
                    cursor.moveToNext();
                }
            }
            cursor.close();
        }

        Toast.makeText(context, "共删除了"+count+"首歌曲", Toast.LENGTH_SHORT).show();
        contentResolver.notifyChange(Uri.parse("content://media"), null);
    }
    public static String songPathName;
    public static void getSongPathName(Context context,long id,int postion){
        ContentResolver contentResolver=context.getContentResolver();
        String[] projection=new String[]{
                MediaStore.Audio.Media.DATA
        };
        Cursor cursor=contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection, "_id="+id, null, null);

        if(cursor != null){

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String name=cursor.getString(0);
                songPathName=name;
            }
            cursor.close();
        }


    }

    /**
     * @param context
     * @param plid
     */
    public static void clearPlaylist(Context context, int plid) {
        Uri uri = Audio.Playlists.Members.getContentUri(EXTERNAL, plid);
        context.getContentResolver().delete(uri, null, null);
        return;
    }

    /**
     * @param context
     * @param ids
     * @param playlistid
     */
    public static void addToPlaylist(Context context, long[] ids, long playlistid) {

        if (ids == null) {
        } else {
            int size = ids.length;
            ContentResolver resolver = context.getContentResolver();
            // need to determine the number of items currently in the playlist,
            // so the play_order field can be maintained.
            String[] cols = new String[] {
                    "count(*)"
            };
            Uri uri = Audio.Playlists.Members.getContentUri(EXTERNAL, playlistid);
            Cursor cur = resolver.query(uri, cols, null, null, null);
            if (cur == null) {
                return;
            }

            cur.moveToFirst();
            int base = cur.getInt(0);
            cur.close();
            int numinserted = 0;
            for (int i = 0; i < size; i += 1000) {
                makeInsertItems(ids, i, 1000, base);
                numinserted += resolver.bulkInsert(uri, sContentValuesCache);
            }

            if (numinserted > 0) {
                String message = context.getResources().getQuantityString(
                        R.plurals.NNNtrackstoplaylist, numinserted, numinserted);
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * @param ids
     * @param offset
     * @param len
     * @param base
     */
    private static void makeInsertItems(long[] ids, int offset, int len, int base) {

        // adjust 'len' if would extend beyond the end of the source array
        if (offset + len > ids.length) {
            len = ids.length - offset;
        }
        // allocate the ContentValues array, or reallocate if it is the wrong
        // size
        if (sContentValuesCache == null || sContentValuesCache.length != len) {
            sContentValuesCache = new ContentValues[len];
        }
        // fill in the ContentValues array with the right values for this pass
        for (int i = 0; i < len; i++) {
            if (sContentValuesCache[i] == null) {
                sContentValuesCache[i] = new ContentValues();
            }

            sContentValuesCache[i].put(Playlists.Members.PLAY_ORDER, base + offset + i);
            sContentValuesCache[i].put(Playlists.Members.AUDIO_ID, ids[offset + i]);
        }
    }

    /**
     * Toggle favorites
     */
    public static void toggleFavorite() {

        if (mService == null)
            return;
        try {
            mService.toggleFavorite();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param context
     * @param id
     */
    public static void addToFavorites(Context context, long id) {

        long favorites_id;

        if (id < 0) {

        } else {
            ContentResolver resolver = context.getContentResolver();

            String favorites_where = PlaylistsColumns.NAME + "='" + PLAYLIST_NAME_FAVORITES + "'";
            String[] favorites_cols = new String[] {
                    BaseColumns._ID
            };
            Uri favorites_uri = Audio.Playlists.EXTERNAL_CONTENT_URI;
            Cursor cursor = resolver.query(favorites_uri, favorites_cols, favorites_where, null,
                    null);
            if (cursor == null) {
                return;
            }

            if (cursor.getCount() <= 0) {
                cursor.close();

                favorites_id = createPlaylist(context, PLAYLIST_NAME_FAVORITES);
            } else {
                cursor.moveToFirst();
                favorites_id = cursor.getLong(0);
                cursor.close();
            }

            String[] cols = new String[] {
                    Playlists.Members.AUDIO_ID
            };
            Uri uri = Playlists.Members.getContentUri(EXTERNAL, favorites_id);
            Cursor cur = resolver.query(uri, cols, null, null, null);
            if (cur == null) {
                return;
            }

            int base = cur.getCount();
            cur.moveToFirst();
            while (!cur.isAfterLast()) {
                if (cur.getLong(0) == id) {
                    cur.close();
                    return;
                }
                cur.moveToNext();
            }
            cur.close();

            ContentValues values = new ContentValues();
            values.put(Playlists.Members.AUDIO_ID, id);
            values.put(Playlists.Members.PLAY_ORDER, base + 1);
            resolver.insert(uri, values);
        }
    }

    /**
     * @param context
     * @param id
     * @return
     */
    public static boolean isFavorite(Context context, long id) {
        long favorites_id;

        if (id < 0) {

        } else {
            ContentResolver resolver = context.getContentResolver();

            String favorites_where = PlaylistsColumns.NAME + "='" + PLAYLIST_NAME_FAVORITES + "'";
            String[] favorites_cols = new String[] {
                    BaseColumns._ID
            };
            Uri favorites_uri = Audio.Playlists.EXTERNAL_CONTENT_URI;
            Cursor cursor = resolver.query(favorites_uri, favorites_cols, favorites_where, null,
                    null);
            if (cursor == null) {
                return false;
            }

            if (cursor.getCount() <= 0) {
                cursor.close();

                favorites_id = createPlaylist(context, PLAYLIST_NAME_FAVORITES);
            } else {
                cursor.moveToFirst();
                favorites_id = cursor.getLong(0);
                cursor.close();
            }

            if (favorites_id == -1) {
                return false;
            }

            String[] cols = new String[] {
                    Playlists.Members.AUDIO_ID
            };
            Uri uri = Playlists.Members.getContentUri(EXTERNAL, favorites_id);
            Cursor cur = resolver.query(uri, cols, null, null, null);
            if (cur == null) {
                return false;
            }

            cur.moveToFirst();
            while (!cur.isAfterLast()) {
                if (cur.getLong(0) == id) {
                    cur.close();
                    return true;
                }
                cur.moveToNext();
            }
            cur.close();
            return false;
        }
        return false;
    }

    /**
     * @param context
     * @param id
     */
    public static void removeFromFavorites(Context context, long id) {
        long favorites_id;
        if (id < 0) {
        } else {
            ContentResolver resolver = context.getContentResolver();
            String favorites_where = PlaylistsColumns.NAME + "='" + PLAYLIST_NAME_FAVORITES + "'";
            String[] favorites_cols = new String[] {
                    BaseColumns._ID
            };
            Uri favorites_uri = Audio.Playlists.EXTERNAL_CONTENT_URI;
            Cursor cursor = resolver.query(favorites_uri, favorites_cols, favorites_where, null,
                    null);
            if (cursor == null) {
                return;
            }

            if (cursor.getCount() <= 0) {
                cursor.close();

                favorites_id = createPlaylist(context, PLAYLIST_NAME_FAVORITES);
            } else {
                cursor.moveToFirst();
                favorites_id = cursor.getLong(0);
                cursor.close();
            }
            Uri uri = Playlists.Members.getContentUri(EXTERNAL, favorites_id);
            resolver.delete(uri, Playlists.Members.AUDIO_ID + "=" + id, null);
        }
    }

    /**
     * @param mImageButton
     */
    public static void setFavoriteImage(ImageButton mImageButton) {
        try {
            if (MusicUtils.mService.isFavorite(MusicUtils.mService.getAudioId())) {
                mImageButton.setImageResource(R.drawable.apollo_holo_light_favorite_selected);
            } else {
                mImageButton.setImageResource(R.drawable.apollo_holo_light_favorite_normal);
                // Theme chooser
                ThemeUtils.setImageButton(mImageButton.getContext(), mImageButton,
                        "apollo_favorite_normal");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param mContext
     * @param id
     * @param name
     */
    public static void renamePlaylist(Context mContext, long id, String name) {

        if (name != null && name.length() > 0) {
            ContentResolver resolver = mContext.getContentResolver();
            ContentValues values = new ContentValues(1);
            values.put(PlaylistsColumns.NAME, name);
            resolver.update(Audio.Playlists.EXTERNAL_CONTENT_URI, values, BaseColumns._ID + "=?",
                    new String[] {
                            String.valueOf(id)
                    });
            Toast.makeText(mContext, R.string.playlist_renamed, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * @param mContext
     * @param list
     */
    public static void addToCurrentPlaylist(Context mContext, long[] list) {

        if (mService == null)
            return;
        try {
            mService.enqueue(list, ApolloService.LAST);
            String message = mContext.getResources().getQuantityString(
                    R.plurals.NNNtrackstoplaylist, list.length, Integer.valueOf(list.length));
            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
        } catch (RemoteException ex) {
        }
    }

    /**
     * @param context
     * @param secs
     * @return time String
     */
    public static String makeTimeString(Context context, long secs) {

        String durationformat = context.getString(secs < 3600 ? R.string.durationformatshort
                : R.string.durationformatlong);

        /*
         * Provide multiple arguments so the format can be changed easily by
         * modifying the xml.
         */
        sFormatBuilder.setLength(0);

        final Object[] timeArgs = sTimeArgs;
        timeArgs[0] = secs / 3600;
        timeArgs[1] = secs / 60;
        timeArgs[2] = secs / 60 % 60;
        timeArgs[3] = secs;
        timeArgs[4] = secs % 60;

        return sFormatter.format(durationformat, timeArgs).toString();
    }

    /**
     * @return current album ID
     */
    public static long getCurrentAlbumId() {

        if (mService != null) {
            try {
                return mService.getAlbumId();
            } catch (RemoteException ex) {
            }
        }
        return -1;
    }

    /**
     * @return current artist ID
     */
    public static long getCurrentArtistId() {

        if (MusicUtils.mService != null) {
            try {
                return mService.getArtistId();
            } catch (RemoteException ex) {
            }
        }
        return -1;
    }

    /**
     * @return current track ID
     */
    public static long getCurrentAudioId() {

        if (MusicUtils.mService != null) {
            try {
                return mService.getAudioId();
            } catch (RemoteException ex) {
            }
        }
        return -1;
    }

    /**
     * @return current artist name
     */
    public static String getArtistName() {

        if (mService != null) {
            try {
                return mService.getArtistName();
            } catch (RemoteException ex) {
            }
        }
        return null;
    }

    /**
     * @return current album name
     */
    public static String getAlbumName() {

        if (mService != null) {
            try {
                return mService.getAlbumName();
            } catch (RemoteException ex) {
            }
        }
        return null;
    }

    /**
     * @return current track name
     */
    public static String getTrackName() {

        if (mService != null) {
            try {
                return mService.getTrackName();
            } catch (RemoteException ex) {
            }
        }
        return null;
    }

    /**
     * @return duration of a track
     */
    public static long getDuration() {
        if (mService != null) {
            try {
                return mService.duration();
            } catch (RemoteException e) {
            }
        }
        return 0;
    }

    /**
     */
    public static void findSearch(Context mContext, String song_name) {
        CharSequence title = null;
        Intent i = new Intent();
        i.setAction(MediaStore.INTENT_ACTION_MEDIA_SEARCH);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        String query = song_name;
        title = "";
        i.putExtra("", query);
        title = title + " " + query;
        title = "" + title;
        i.putExtra(SearchManager.QUERY, query);
        mContext.startActivity(Intent.createChooser(i, title));
    }

    /**
     * Create a Search Chooser
     */
    public static void doSearch(Context mContext, Cursor mCursor, int index) {
        CharSequence title = null;
        Intent i = new Intent();
        i.setAction(MediaStore.INTENT_ACTION_MEDIA_SEARCH);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        String query = mCursor.getString(index);
        title = "";
        i.putExtra("", query);
        title = title + " " + query;
        title = "" + title;
        i.putExtra(SearchManager.QUERY, query);
        mContext.startActivity(Intent.createChooser(i, title));
    }

    /**
     * Method that removes all tracks from the current queue
     */
    public static void removeAllTracks() {
        try {
            if (mService != null) {
                long[] current = MusicUtils.getQueue();
                if (current != null) {
                	// mService = null ,47014
                		mService.removeTracks(0, current.length-1);
                }
            }
        } catch (RemoteException e) {
        }
    }

    /**
     * @param id
     * @return removes track from a playlist
     */
    public static int removeTrack(long id) {
        if (mService == null)
            return 0;

        try {
            return mService.removeTrack(id);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * @param index
     */
    public static void setQueuePosition(int index) {
        if (mService == null)
            return;
        try {
            mService.setQueuePosition(index);
        } catch (RemoteException e) {
        }
    }

    public static String getArtistName(Context mContext, long artist_id, boolean default_name) {
        String where = BaseColumns._ID + "=" + artist_id;
        String[] cols = new String[] {
                ArtistColumns.ARTIST
        };
        Uri uri = Audio.Artists.EXTERNAL_CONTENT_URI;
        Cursor cursor = mContext.getContentResolver().query(uri, cols, where, null, null);
        if (cursor == null){
            return MediaStore.UNKNOWN_STRING;
        }
        if (cursor.getCount() <= 0) {
            cursor.close();

            if (default_name)
                return mContext.getString(R.string.unknown);
            else
                return MediaStore.UNKNOWN_STRING;
        } else {
            cursor.moveToFirst();
            String name = cursor.getString(0);
            cursor.close();
            if (name == null || MediaStore.UNKNOWN_STRING.equals(name)) {
                if (default_name)
                    return mContext.getString(R.string.unknown);
                else
                    return MediaStore.UNKNOWN_STRING;
            }
            return name;
        }
    }

    /**
     * @param mContext
     * @param album_id
     * @param default_name
     * @return album name
     */
    public static String getAlbumName(Context mContext, long album_id, boolean default_name) {
        String where = BaseColumns._ID + "=" + album_id;
        String[] cols = new String[] {
                AlbumColumns.ALBUM
        };
        Uri uri = Audio.Albums.EXTERNAL_CONTENT_URI;
        Cursor cursor = mContext.getContentResolver().query(uri, cols, where, null, null);
        if (cursor == null){
            return MediaStore.UNKNOWN_STRING;
        }
        if (cursor.getCount() <= 0) {
            cursor.close();

            if (default_name)
                return mContext.getString(R.string.unknown);
            else
                return MediaStore.UNKNOWN_STRING;
        } else {
            cursor.moveToFirst();
            String name = cursor.getString(0);
            cursor.close();
            if (name == null || MediaStore.UNKNOWN_STRING.equals(name)) {
                if (default_name)
                    return mContext.getString(R.string.unknown);
                else
                    return MediaStore.UNKNOWN_STRING;
            }
            return name;
        }
    }

    /**
     * @param playlist_id
     * @return playlist name
     */
    public static String getPlaylistName(Context mContext, long playlist_id) {
        String where = BaseColumns._ID + "=" + playlist_id;
        String[] cols = new String[] {
                PlaylistsColumns.NAME
        };
        Uri uri = Audio.Playlists.EXTERNAL_CONTENT_URI;
        Cursor cursor = mContext.getContentResolver().query(uri, cols, where, null, null);
        if (cursor == null){
            return "";
        }
        if (cursor.getCount() <= 0) {
            cursor.close();

            return "";
        }
        cursor.moveToFirst();
        String name = cursor.getString(0);
        cursor.close();
        return name;
    }

    /**
     * @param mContext
     * @param genre_id
     * @param default_name
     * @return genre name
     */
    public static String getGenreName(Context mContext, long genre_id, boolean default_name) {
        String where = BaseColumns._ID + "=" + genre_id;
        String[] cols = new String[] {
                GenresColumns.NAME
        };
        Uri uri = Audio.Genres.EXTERNAL_CONTENT_URI;
        Cursor cursor = mContext.getContentResolver().query(uri, cols, where, null, null);
        if (cursor == null){
            return MediaStore.UNKNOWN_STRING;
        }
        if (cursor.getCount() <= 0) {
            cursor.close();

            if (default_name)
                return mContext.getString(R.string.unknown);
            else
                return MediaStore.UNKNOWN_STRING;
        } else {
            cursor.moveToFirst();
            String name = cursor.getString(0);
            cursor.close();
            if (name == null || MediaStore.UNKNOWN_STRING.equals(name)) {
                if (default_name)
                    return mContext.getString(R.string.unknown);
                else
                    return MediaStore.UNKNOWN_STRING;
            }
            return name;
        }
    }

    /**
     * @param genre
     * @return parsed genre name
     */
    public static String parseGenreName(Context mContext, String genre) {
        int genre_id = -1;

        if (genre == null || genre.trim().length() <= 0)
            return mContext.getResources().getString(R.string.unknown);

        try {
            genre_id = Integer.parseInt(genre);
        } catch (NumberFormatException e) {
            return genre;
        }
        if (genre_id >= 0 && genre_id < GENRES_DB.length)
            return GENRES_DB[genre_id];
        else
            return mContext.getResources().getString(R.string.unknown);
    }

    /**
     * @return if music is playing
     */
    public static boolean isPlaying() {
        if (mService == null)
            return false;

        try {
            return mService.isPlaying();
        } catch (RemoteException e) {
        }
        return false;
    }

    /**
     * @return current track's queue position
     */
    public static int getQueuePosition() {
        if (mService == null)
            return 0;
        try {
            return mService.getQueuePosition();
        } catch (RemoteException e) {
        }
        return 0;
    }

    public static void deleteTrack(ApolloService mpbService, long mid, long artIndex) {
        if (mpbService == null) {
            return;
        }
        try {
            mpbService.removeTrack(mid);
//             if (sArtCache != null) {
//                 synchronized(sArtCache) {
//                     sArtCache.remove(artIndex);
//                 }
//             }
            mpbService.getApplicationContext().getContentResolver().notifyChange(Uri.parse("content://media"), null);
        } catch (Exception e) {
            Log.e("MusicUtils", "Error occur when deleting music");
        }
    }

    public static void deleteTracks(Context context, long [] list) {

        String [] cols = new String [] { MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.DATA, MediaStore.Audio.Media.ALBUM_ID };
        StringBuilder where = new StringBuilder();
        where.append(MediaStore.Audio.Media._ID + " IN (");
        for (int i = 0; i < list.length; i++) {
            where.append(list[i]);
            if (i < list.length - 1) {
                where.append(",");
            }
        }
        where.append(")");
        Cursor c = query(context, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, cols,
                where.toString(), null, null);

        if (c != null) {

            // step 1: remove selected tracks from the current playlist, as well
            // as from the album art cache
            try {
                c.moveToFirst();
                while (! c.isAfterLast()) {
                    // remove from current playlist
                    long id = c.getLong(0);
                    mService.removeTrack(id);
                    // remove from album art cache
//                    long artIndex = c.getLong(2);
//                    synchronized(sArtCache) {
//                        sArtCache.remove(artIndex);
//                    }
                    c.moveToNext();
                }
            } catch (RemoteException ex) {
            }

            // step 2: remove selected tracks from the database
            context.getContentResolver().delete(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, where.toString(), null);

            // step 3: remove files from card
            c.moveToFirst();
            while (! c.isAfterLast()) {
                String name = c.getString(1);
                File f = new File(name);
                try {  // File.delete can throw a security exception
                    if (!f.delete()) {
                        // I'm not sure if we'd ever get here (deletion would
                        // have to fail, but no exception thrown)
                        Log.e("MusicUtils", "Failed to delete file " + name);
                    }
                    c.moveToNext();
                } catch (SecurityException ex) {
                    c.moveToNext();
                }
            }
            c.close();
        }

//        String message = context.getResources().getQuantityString(
//                R.plurals.NNNtracksdeleted, list.length, Integer.valueOf(list.length));
        String message = "共删除了"+list.length+"首歌曲";

        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        // We deleted a number of tracks, which could affect any number of things
        // in the media content domain, so update everything.
        context.getContentResolver().notifyChange(Uri.parse("content://media"), null);
    }

    /**
     * @param mContext
     * @param create_shortcut
     * @param list
     */
    public static void makePlaylistList(Context mContext, boolean create_shortcut,
                                        List<Map<String, String>> list) {

        Map<String, String> map;

        String[] cols = new String[] {
                Audio.Playlists._ID, Audio.Playlists.NAME
        };
        StringBuilder where = new StringBuilder();

        ContentResolver resolver = mContext.getContentResolver();
        if (resolver == null) {
            System.out.println("resolver = null");
        } else {
            where.append(Audio.Playlists.NAME + " != ''");
            where.append(" AND " + Audio.Playlists.NAME + " != '" + PLAYLIST_NAME_FAVORITES + "'");
            Cursor cur = resolver.query(Audio.Playlists.EXTERNAL_CONTENT_URI, cols,
                    where.toString(), null, Audio.Playlists.NAME);
            list.clear();

//             map = new HashMap<String, String>();
//             map.put("id", String.valueOf(PLAYLIST_FAVORITES));
//             map.put("name", mContext.getString(R.string.favorite));
//             list.add(map);

            map = new HashMap<String, String>();
            map.put("id", String.valueOf(PLAYLIST_QUEUE));
            map.put("name", mContext.getString(R.string.queue));
            list.add(map);

            map = new HashMap<String, String>();
            map.put("id", String.valueOf(PLAYLIST_NEW));
            map.put("name", mContext.getString(R.string.new_playlist));
            list.add(map);

            if (cur != null && cur.getCount() > 0) {
                cur.moveToFirst();
                while (!cur.isAfterLast()) {
                    map = new HashMap<String, String>();
                    map.put("id", String.valueOf(cur.getLong(0)));
                    map.put("name", cur.getString(1));
                    list.add(map);
                    cur.moveToNext();
                }
            }
            if (cur != null) {
                cur.close();
            }
        }
    }

    public static void notifyWidgets(String what){
        try {
            mService.notifyChange(what);
        } catch (Exception e) {
        }
    }

    public static void setRootbootBK(Context context, View view) {
        if (MusicUtils.mService != null) {
            Bitmap bitmap;
            try {
                bitmap = MusicUtils.mService.getAlbumBlurBitmap();
                if (bitmap == null) {
                    //显示默认封面
//					view.setBackgroundResource(R.drawable.rootboot_bk);

                    ApolloUtils.setPlayAcitivityStatusBar((Activity)context, context.getResources().getDrawable(R.drawable.rootboot_bk));
                } else {
                    //显示毛玻璃效果图片
                    BitmapDrawable bd = new BitmapDrawable(
                            context.getResources(), bitmap);
//					view.setBackground(bd);

                    ApolloUtils.setPlayAcitivityStatusBar((Activity)context, bd);
                }
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public static void screenTransitAnimByPair(Activity activity, Intent intent, Pair<View, String>... views) {
//		Intent intent = new Intent( activity, PlayActivity.class );
//		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

//		((ViewGroup) bottombarCover.getParent()).setTransitionGroup( false );

        ActivityOptions options;
        try {
            options = ActivityOptions.makeSceneTransitionAnimation( activity, views);
        } catch( NullPointerException e ) {
            Log.e( tag, "screenTransitAnimByPair() --- Did you set your ViewNames in the layout file?" );
            return;
        }
        if( options == null ) {
            Log.e(tag, "screenTransitAnimByPair() --- Options is null. Something broke. Good luck!");
        } else {
            activity.startActivity(intent, options.toBundle());
        }
    }

    public static void finishTranstion(Activity activity, boolean isBackPressed) {
        if (!isBackPressed) {
            activity.finish();
        }
    }

    // this method is to confirm the track's  file tyle
    public static boolean isSupportableFileType(String trackData){
        int index = trackData.lastIndexOf(".");
        //get the file's suffix
        String suffix="";
        if (index!=-1){
        	suffix = trackData.substring(index);
        }
        if(suffix.equalsIgnoreCase(".wma") || suffix.equalsIgnoreCase(".wv")){
            return  false;
        }
        return true;
    }

    public static void mPreBtnDeal() {
        if (MusicUtils.mService == null)
            return;
        try {// Here the number has been changed (<2000 changed to >0)
            // so can save the problem that can not PREV
            if (MusicUtils.mService.position() > 0) {
                MusicUtils.mService.prev();
            } else {
                MusicUtils.mService.seek(0);
                MusicUtils.mService.play();
            }
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
    }

    public static void mNextBtnDeal() {
        if (MusicUtils.mService == null)
            return;
        try {
            MusicUtils.mService.next();
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Play and pause music
     */
    public static void doPauseResume(ImageButton mPlay) {
        try {
            if (MusicUtils.mService != null) {
                if (MusicUtils.mService.isPlaying()) {
                    MusicUtils.mService.pause();
                } else {
                    MusicUtils.mService.play();
                }
            }
            setPauseButtonImage(mPlay);
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Set the play and pause image
     */
    public static void setPauseButtonImage(ImageButton mPlay) {
        try {
            if (MusicUtils.mService != null
                    && MusicUtils.mService.getAudioId() != -1
                    && MusicUtils.mService.getPath() != null) {
                if (MusicUtils.mService.isPlaying()) {
                    mPlay.setImageResource(R.drawable.btn_bottombar_pause_selector);
                } else {
                    mPlay.setImageResource(R.drawable.btn_bottombar_play_selector);
                }
            } else {
                mPlay.setImageResource(R.drawable.btn_bottombar_play_selector);
            }
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
    }

    public static void updateBottomActionBar(Context context, RelativeLayout bottomActionBar,
                                             MarqueeTextView bottombar_songname, MarqueeTextView bottombar_artistname,
                                             ImageView bottombarCover) {
        if (bottomActionBar == null) {
            return;
        }

        if (MusicUtils.mService != null && MusicUtils.getCurrentAudioId() != -1) {

            // Track name
            bottombar_songname.setText(MusicUtils.getTrackName());

            // Artist name
            bottombar_artistname.setText(MusicUtils.getArtistName());

            // Album art
            try {
                Bitmap bitmap = MusicUtils.mService.getAlbumBitmap();
                if (bitmap == null) {
                    bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.bottom_bar_default_cover);
                }
                ImageUtils.setLoadedBitmap(bottombarCover, bitmap);
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public static void changeToSearchActivity(Context context) {
        Intent intent = new Intent( context, SearchActivity.class );
        context.startActivity(intent);
    }

    public static  void addTracksToPlaylist(Context context, long playListId) {
        Intent intent = new Intent(context, AddTracksToQueueActivity.class);
        Bundle bundle = new Bundle();
        bundle.putLong(ADD_TO_PALYLIST, playListId);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public static final int REQUEST_CODE_CREATE_PALYLIST = 0x10000;
    public static final int RERULT_CODE_CREATE_PALYLIST = REQUEST_CODE_CREATE_PALYLIST+1;

    static class LogEntry {
        Object item;
        long time;

        LogEntry(Object o) {
            item = o;
            time = System.currentTimeMillis();
        }

        void dump(PrintWriter out) {
            sTime.set(time);
            out.print(sTime.toString() + " : ");
            if (item instanceof Exception) {
                ((Exception)item).printStackTrace(out);
            } else {
                out.println(item);
            }
        }
    }

    private static LogEntry[] sMusicLog = new LogEntry[100];
    private static int sLogPtr = 0;
    private static Time sTime = new Time();

    public static void debugLog(Object o) {

        sMusicLog[sLogPtr] = new LogEntry(o);
        sLogPtr++;
        if (sLogPtr >= sMusicLog.length) {
            sLogPtr = 0;
        }
    }

    static void debugDump(PrintWriter out) {
        for (int i = 0; i < sMusicLog.length; i++) {
            int idx = (sLogPtr + i);
            if (idx >= sMusicLog.length) {
                idx -= sMusicLog.length;
            }
            LogEntry entry = sMusicLog[idx];
            if (entry != null) {
                entry.dump(out);
            }
        }
    }

}