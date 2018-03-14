
package com.dream.mediaplayer.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dream.mediaplayer.R;
import com.dream.mediaplayer.helpers.utils.ApolloUtils;
import com.dream.mediaplayer.helpers.utils.MenuColorFix;
import com.dream.mediaplayer.helpers.utils.MusicUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.Toast;
import static com.dream.mediaplayer.Constants.INTENT_ADD_TO_PLAYLIST;
import static com.dream.mediaplayer.Constants.INTENT_CREATE_PLAYLIST;
import static com.dream.mediaplayer.Constants.INTENT_PLAYLIST_LIST;
import static com.dream.mediaplayer.Constants.PLAYLIST_NEW;
import static com.dream.mediaplayer.Constants.PLAYLIST_QUEUE;

public class PlaylistPicker extends Activity implements DialogInterface.OnClickListener,
        DialogInterface.OnCancelListener {

    private AlertDialog mPlayListPickerDialog;

    List<Map<String, String>> mAllPlayLists = new ArrayList<Map<String, String>>();

    List<String> mPlayListNames = new ArrayList<String>();

    long[] mList = new long[] {};
    public static  boolean enterPlaylistFormAlbum = false;
    @Override
    public void onCancel(DialogInterface dialog) {
        finish();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

        long listId = Long.valueOf(mAllPlayLists.get(which).get("id"));
        String listName = mAllPlayLists.get(which).get("name");
        Intent intent;
        boolean mCreateShortcut = getIntent().getAction().equals(Intent.ACTION_CREATE_SHORTCUT);

        if (mCreateShortcut) {
            final Intent shortcut = new Intent();
            // shortcut.setAction(INTENT_PLAY_SHORTCUT);
            shortcut.putExtra("id", listId);

            intent = new Intent();
            intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcut);
            intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, listName);
//            intent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
//                    Intent.ShortcutIconResource.fromContext(this, R.drawable.app_icon));
            setResult(RESULT_OK, intent);
        } else {
            if (listId >= 0) {        //点击的是已经存在的播放列表
                MusicUtils.addToPlaylist(this, mList, listId);
            } else if (listId == PLAYLIST_QUEUE) {  //点击的是音乐队列,即添加到当前播放列表中
                MusicUtils.addToCurrentPlaylist(this, mList);
            } else if (listId == PLAYLIST_NEW) {    //点击的是新建播放列表
                enterPlaylistFormAlbum = true ; //表示是从专辑过去到新建列表中的添加歌曲
                intent = new Intent(INTENT_CREATE_PLAYLIST);
                intent.putExtra(INTENT_PLAYLIST_LIST, mList);
                startActivity(intent);
            }
        }
        finish();
    }

    @Override
    public void onCreate(Bundle icicle) {

        super.onCreate(icicle);
        
        ApolloUtils.setStatusBar(this);
        
        setContentView(new LinearLayout(this));

        if (getIntent().getAction() != null) {

            if (INTENT_ADD_TO_PLAYLIST.equals(getIntent().getAction())
                    && getIntent().getLongArrayExtra(INTENT_PLAYLIST_LIST) != null) {

                MusicUtils.makePlaylistList(this, false, mAllPlayLists);
                mList = getIntent().getLongArrayExtra(INTENT_PLAYLIST_LIST);
                for (int i = 0; i < mAllPlayLists.size(); i++) {
                    mPlayListNames.add(mAllPlayLists.get(i).get("name"));
                }
//                int color = Color.rgb(42, 232, 255);
                SpannableString[] s = new SpannableString[mPlayListNames.size()];
                for (int i = 0; i < s.length; i++){
                	s[i] = new SpannableString(mPlayListNames.get(i));
                	s[i].setSpan(new ForegroundColorSpan(MenuColorFix.getTextColor()), 0, s[i].length(), 0);
                }          
                
                mPlayListPickerDialog = new AlertDialog.Builder(this)
                        .setTitle(R.string.add_to_playlist)
                        .setItems(s/*mPlayListNames.toArray(new CharSequence[mPlayListNames.size()])*/,
                                this).setOnCancelListener(this).show();
                
                mPlayListPickerDialog.getWindow().setLayout(ApolloUtils.dp2px(this, 316), ApolloUtils.dp2px(this, 374));  
            } else if (getIntent().getAction().equals(Intent.ACTION_CREATE_SHORTCUT)) {
                MusicUtils.makePlaylistList(this, true, mAllPlayLists);            
                for (int i = 0; i < mAllPlayLists.size(); i++) {
                    mPlayListNames.add(mAllPlayLists.get(i).get("name"));
                }
//                int color = Color.rgb(42, 232, 255);
                SpannableString[] s = new SpannableString[mPlayListNames.size()];
                for (int i = 0; i < s.length; i++){
                	s[i] = new SpannableString(mPlayListNames.get(i));
                	s[i].setSpan(new ForegroundColorSpan(MenuColorFix.getTextColor()), 0, s[i].length(), 0);
                }                  
                mPlayListPickerDialog = new AlertDialog.Builder(this)
                        .setItems(s/*mPlayListNames.toArray(new CharSequence[mPlayListNames.size()])*/,
                                this).setOnCancelListener(this).show();
                mPlayListPickerDialog.getWindow().setLayout(ApolloUtils.dp2px(this, 316), ApolloUtils.dp2px(this, 374));  
            }
        } else {
            Toast.makeText(this, R.string.error, Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public void onPause() {

        if (mPlayListPickerDialog != null && mPlayListPickerDialog.isShowing()) {
            mPlayListPickerDialog.dismiss();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {

        super.onResume();
        if (mPlayListPickerDialog != null && !mPlayListPickerDialog.isShowing()) {
            mPlayListPickerDialog.show();
        }
    }

}
