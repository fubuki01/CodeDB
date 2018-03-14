
package com.dream.mediaplayer.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dream.mediaplayer.R;
import com.dream.mediaplayer.helpers.utils.ApolloUtils;
import com.dream.mediaplayer.helpers.utils.MenuColorFix;
import com.dream.mediaplayer.helpers.utils.MusicUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.ActionBarOverlayLayout;
import android.support.v7.widget.ViewUtils;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import static com.androidquery.util.AQUtility.getContext;
import static com.dream.mediaplayer.Constants.INTENT_ADD_TO_PLAYLIST;
import static com.dream.mediaplayer.Constants.INTENT_CREATE_PLAYLIST;
import static com.dream.mediaplayer.Constants.INTENT_PLAYLIST_LIST;
import static com.dream.mediaplayer.Constants.PLAYLIST_NEW;
import static com.dream.mediaplayer.Constants.PLAYLIST_QUEUE;

public class PlaylistPicker extends Activity implements DialogInterface.OnClickListener,
        DialogInterface.OnCancelListener {

    private ListView mlist;

    private AlertDialog mPlayListPickerDialog;

    List<Map<String, String>> mAllPlayLists = new ArrayList<Map<String, String>>();

    List<String> mPlayListNames = new ArrayList<String>();

    long[] mList = new long[] {};

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
            if (listId >= 0) {
                MusicUtils.addToPlaylist(this, mList, listId);
            } else if (listId == PLAYLIST_QUEUE) {
                MusicUtils.addToCurrentPlaylist(this, mList);
            } else if (listId == PLAYLIST_NEW) {
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
                List<Map <String, Object>> ss = new ArrayList<Map<String, Object>>();
                for (int i = 0; i < s.length; i++){
                	s[i] = new SpannableString(mPlayListNames.get(i));
                	s[i].setSpan(new ForegroundColorSpan(MenuColorFix.getTextColor()), 0, s[i].length(), 0);
                    Map<String,Object> item = new HashMap<String, Object>();
                    item.put("string",s[i].toString());
                    ss.add(item);
                }
                
                mPlayListPickerDialog = new AlertDialog.Builder(this).create();
                mPlayListPickerDialog.show();
//                        .setTitle(R.string.add_to_playlist)
//                        .setItems(s/*mPlayListNames.toArray(new CharSequence[mPlayListNames.size()])*/,
//                                this).setOnCancelListener(this).show();
                mlist = (ListView) findViewById(R.id.longClickDialogList);
                SimpleAdapter mAdapter = new SimpleAdapter(
                        this,ss,R.layout.long_click_dialog_item,
                        new String[]{"string"},new int[]{R.id.item});
                //mlist.setAdapter( mAdapter);
//                mlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        onClick(null,position);
//                    }
//                });

//                Window window = mPlayListPickerDialog.getWindow();
//                WindowManager.LayoutParams lp = window.getAttributes();
//                window.setLayout(ApolloUtils.dp2px(this, 384), ApolloUtils.dp2px(this, 478));
                WindowManager.LayoutParams lp = mPlayListPickerDialog.getWindow().getAttributes();
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.MATCH_PARENT;
                Window window = mPlayListPickerDialog.getWindow();
                window.setAttributes(lp);
                window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
                window.setContentView(R.layout.long_click_dialog);
                mPlayListPickerDialog.setCancelable(false);

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
