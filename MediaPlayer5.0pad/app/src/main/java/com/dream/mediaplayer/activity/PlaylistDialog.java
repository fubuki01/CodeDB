/*
 *              Copyright (C) 2011 The MusicMod Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *            http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dream.mediaplayer.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnShowListener;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dream.mediaplayer.R;
import com.dream.mediaplayer.helpers.utils.ApolloUtils;
import com.dream.mediaplayer.helpers.utils.MusicUtils;

import static android.view.KeyEvent.KEYCODE_BACK;
import static com.dream.mediaplayer.Constants.INTENT_CREATE_PLAYLIST;
import static com.dream.mediaplayer.Constants.INTENT_KEY_DEFAULT_NAME;
import static com.dream.mediaplayer.Constants.INTENT_KEY_RENAME;
import static com.dream.mediaplayer.Constants.INTENT_PLAYLIST_LIST;
import static com.dream.mediaplayer.Constants.INTENT_RENAME_PLAYLIST;
import static com.dream.mediaplayer.Constants.PLAYLIST_NAME_FAVORITES;

public class PlaylistDialog extends Activity implements TextWatcher,
        OnCancelListener, OnShowListener, android.view.View.OnClickListener {

    private AlertDialog mPlaylistDialog;

    private String action;

    private EditText mPlaylist;
    private TextView message;

    private String mDefaultName, mOriginalName;

    private long mRenameId;

    private long[] mList = new long[] {};
    private final int MAX_NAME_LENGTH = 17;

    private Toast toast;//在类前面声明吐司，确保在这个页面只有一个吐司

    private void setToast(String msg) {
        if (toast == null) {
            toast = Toast.makeText(mPlaylistDialog.getContext(), msg, Toast.LENGTH_SHORT);
        } else {
            toast.cancel();//关闭吐司显示
            toast = Toast.makeText(mPlaylistDialog.getContext(), msg, Toast.LENGTH_SHORT);
        }
        toast.show();//重新显示吐司
    }

    //新建播放列表
    android.view.View.OnClickListener creatNewPlayList=new View.OnClickListener() {
        @Override
        public void onClick(View arg0) {
            String name = mPlaylist.getText().toString();
            if (name != null && name.length() > 0) {
                //当输入带有“/”，不能让用户进行创建
                if(name.indexOf("/")!=-1)
                {
                    setToast("不能带有“/”符号");
                    return;
                }
                //当输入的列表名称为最喜爱的，则不能让用户进行创建，因为这是系统默认的列表名称
                if("最喜爱的".equals(name.trim())){
                    setToast("不能创建该列表名，因为为系统默认列表名");
                    return;
                }
                boolean isCreatePlayList = false;
                int id = idForplaylist(name);
                if (id >= 0) {
                    MusicUtils.clearPlaylist(PlaylistDialog.this, id);
                    MusicUtils.addToPlaylist(PlaylistDialog.this, mList, id);
                    MusicUtils.addTracksToPlaylist(PlaylistDialog.this, id);
                } else {
                    long new_id = MusicUtils.createPlaylist(PlaylistDialog.this, name);
                    if (new_id >= 0) {
                        //判断是否是从专辑中进入到新建播放列表中
                        if(LongClickDialog.enterPlaylistFormAlbum == true){  //是从专辑中进入
                            //将点击的专辑中的歌添加到对应的新建列表中
                            //参数：new_id ：播放列表的ID ； mlist：歌曲ID
                            MusicUtils.addToPlaylist(PlaylistDialog.this, mList, new_id);
                            LongClickDialog.enterPlaylistFormAlbum = false; //复原设置标记(之前为false)
                        }
                        else {    //当是正常情况，从播放列表中进行创建的时候
                            isCreatePlayList = true;
                            //MusicUtils.addToPlaylist(PlaylistDialog.this, mList, new_id);
                            MusicUtils.addTracksToPlaylist(PlaylistDialog.this, new_id);
                        }
                    }
                }
                if (isCreatePlayList) {
                    Intent intent = new Intent();
                    intent.putExtra("isRestartLoader", true);
                    setResult(MusicUtils.RERULT_CODE_CREATE_PALYLIST, intent);
                    finish();
                } else {
                    finish();
                }
            }
        }
    };

    //重命名播放列表监听
    android.view.View.OnClickListener renamePlayList=new View.OnClickListener() {
        public void onClick(View arg0) {
            String name = mPlaylist.getText().toString();
            if(name.indexOf("/")!=-1)
            {
                setToast("不能带有“/”符号");
                return;
            }
            if(mOriginalName.equals("最喜爱的"))
            {
                setToast("不可修改此列表");
                return;
            }
            if (idForplaylist(name) >= 0 && !mOriginalName.equals(name))
            {
                setToast("此播放列表已存在");
                return;
            }
            MusicUtils.renamePlaylist(PlaylistDialog.this, mRenameId, name);
            finish();
        }

    };

    private Button buttonfalse;

    private TextView tvtitle;

    private Button  buttoncanse;

    @Override
    public void afterTextChanged(Editable s) {

        // don't care about this one
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        // don't care about this one
    }

    @Override
    public void onCancel(DialogInterface dialog) {

        if (dialog == mPlaylistDialog) {
            finish();
            overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        }
    }

    @Override
    public void onCreate(Bundle icicle) {

        super.onCreate(icicle);

        ApolloUtils.setStatusBar(this);



        setContentView(new LinearLayout(this));

        action = getIntent().getAction();

        mRenameId = icicle != null ? icicle.getLong(INTENT_KEY_RENAME) : getIntent().getLongExtra(
                INTENT_KEY_RENAME, -1);
        mList = icicle != null ? icicle.getLongArray(INTENT_PLAYLIST_LIST) : getIntent()
                .getLongArrayExtra(INTENT_PLAYLIST_LIST);
        if (INTENT_RENAME_PLAYLIST.equals(action)) {
            mOriginalName = nameForId(mRenameId);
            mDefaultName = icicle != null ? icicle.getString(INTENT_KEY_DEFAULT_NAME)
                    : mOriginalName;
        } else if (INTENT_CREATE_PLAYLIST.equals(action)) {
            mDefaultName = icicle != null ? icicle.getString(INTENT_KEY_DEFAULT_NAME)
                    : makePlaylistName();
            mOriginalName = mDefaultName;
        }

//        DisplayMetrics dm = new DisplayMetrics();
//        dm = getResources().getDisplayMetrics();

        mPlaylistDialog = new AlertDialog.Builder(PlaylistDialog.this,R.style.MyDialog).create();
        mPlaylistDialog.setVolumeControlStream(AudioManager.STREAM_MUSIC);

        if (action != null && mRenameId >= 0 && mOriginalName != null || mDefaultName != null) {
            mPlaylistDialog.show();
            //设置dialog大小
            WindowManager.LayoutParams lp = mPlaylistDialog.getWindow().getAttributes();
            lp.width = LayoutParams.MATCH_PARENT;
            lp.height = LayoutParams.WRAP_CONTENT;

            mPlaylistDialog.getWindow().setAttributes(lp);
            mPlaylistDialog.getWindow().setContentView(R.layout.playlist);
            mPlaylistDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);

            //  LayoutInflater mInflater = LayoutInflater.from(PlaylistDialog.this);
            //  View saveView = mInflater.inflate(R.layout.playlist, null);
            buttonfalse = (Button) mPlaylistDialog.getWindow().findViewById(R.id.btn_queding);
            buttoncanse = (Button) mPlaylistDialog.getWindow().findViewById(R.id.btn_quxiao);
            mPlaylist = (EditText) mPlaylistDialog.getWindow().findViewById(R.id.et_add);
            tvtitle = (TextView) mPlaylistDialog.getWindow().findViewById(R.id.tv_title);
            mPlaylist.addTextChangedListener(this);
            mPlaylist.setText(mDefaultName);
            mPlaylist.setSelection(mDefaultName.length());
            mPlaylist.setSingleLine(true);
            mPlaylist.setOnClickListener(this);
            String promptformat;
            String prompt = "";
            if (INTENT_RENAME_PLAYLIST.equals(action)) {
                promptformat = getString(R.string.rename_playlist);
                prompt = String.format(promptformat, mOriginalName, mDefaultName);
            } else if (INTENT_CREATE_PLAYLIST.equals(action)) {
                promptformat = getString(R.string.new_playlist);
                prompt = String.format(promptformat, mDefaultName);
            }
            tvtitle.setText(prompt);
            if (INTENT_RENAME_PLAYLIST.equals(action)) {
                buttonfalse.setOnClickListener(renamePlayList);
                mPlaylistDialog.setOnShowListener(this);

            } else if (INTENT_CREATE_PLAYLIST.equals(action)) {
                buttonfalse.setOnClickListener(creatNewPlayList);
                mPlaylistDialog.setOnShowListener(this);
            }
            buttoncanse.setOnClickListener(this);
            mPlaylistDialog.setOnCancelListener(this);

            //  mPlaylistDialog.setView(saveView);
            // mPlaylistDialog.show();
            //   mPlaylistDialog.setContentView(saveView);
        } else {
            Toast.makeText(this, R.string.error, Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    @Override
    public void onPause() {

        if (mPlaylistDialog != null && mPlaylistDialog.isShowing()) {
            mPlaylistDialog.dismiss();
        }
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outcicle) {

        if (INTENT_RENAME_PLAYLIST.equals(action)) {
            outcicle.putString(INTENT_KEY_DEFAULT_NAME, mPlaylist.getText().toString());
            outcicle.putLong(INTENT_KEY_RENAME, mRenameId);
        } else if (INTENT_CREATE_PLAYLIST.equals(action)) {
            outcicle.putString(INTENT_KEY_DEFAULT_NAME, mPlaylist.getText().toString());
        }
    }

    @Override
    public void onShow(DialogInterface dialog) {

        if (dialog == mPlaylistDialog) {
            setSaveButton();
        }
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        setSaveButton();
    }

    private int idForplaylist(String name) {
        Cursor cursor = MusicUtils.query(this, Audio.Playlists.EXTERNAL_CONTENT_URI, new String[] {
                Audio.Playlists._ID
        }, Audio.Playlists.NAME + "=?", new String[] {
                name
        }, Audio.Playlists.NAME, 0);
        int id = -1;
        if (cursor != null) {
            cursor.moveToFirst();
            if (!cursor.isAfterLast()) {
                id = cursor.getInt(0);
            }
            cursor.close();
        }

        return id;
    }

    private String makePlaylistName() {

        String template = "";
        int num = 1;
        String[] cols = new String[] {
                Audio.Playlists.NAME
        };
        ContentResolver resolver = getContentResolver();
        String whereclause = Audio.Playlists.NAME + " != ''";
        Cursor cursor = resolver.query(MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI, cols,
                whereclause, null, Audio.Playlists.NAME);
        if (cursor == null)
            return null;
        String suggestedname;
        suggestedname = String.format(template, num++);

        // Need to loop until we've made 1 full pass through without finding a
        // match. Looping more than once shouldn't happen very often, but will
        // happen if you have playlists named
        // "New Playlist 1"/10/2/3/4/5/6/7/8/9, where making only one pass would
        // result in "New Playlist 10" being erroneously picked for the new
        // name.
        boolean done = false;
        while (!done) {
            done = true;
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String playlistname = cursor.getString(0);
                if (playlistname.compareToIgnoreCase(suggestedname) == 0) {
                    suggestedname = String.format(template, num++);
                    done = false;
                }
                cursor.moveToNext();
            }
        }
        cursor.close();
        return suggestedname;
    };

    private String nameForId(long id) {

        Cursor cursor = MusicUtils.query(this, Audio.Playlists.EXTERNAL_CONTENT_URI, new String[] {
                Audio.Playlists.NAME
        }, Audio.Playlists._ID + "=?", new String[] {
                Long.valueOf(id).toString()
        }, Audio.Playlists.NAME);
        String name = null;
        if (cursor != null) {
            cursor.moveToFirst();
            if (!cursor.isAfterLast()) {
                name = cursor.getString(0);
            }
            cursor.close();
        }
        return name;
    }

    private void setSaveButton() {

        String typedname = mPlaylist.getText().toString();
        if (buttonfalse == null)
            return;
        if (typedname.length() == 0 ||  typedname.length() > MAX_NAME_LENGTH) {
            if(typedname.length() > MAX_NAME_LENGTH){
                setToast(getResources().getString(R.string.playlist_name_too_long));
            }
            buttonfalse.setEnabled(false);
            buttonfalse.setTextColor(Color.GRAY);
        } else {
            buttonfalse.setEnabled(true);
            int mycolor=getResources().getColor(R.color.btnnewlist_color);
            buttonfalse.setTextColor(mycolor);
            if (idForplaylist(typedname) >= 0 && !mOriginalName.equals(typedname) && INTENT_CREATE_PLAYLIST.equals(action)) {
                buttonfalse.setText(R.string.overwrite);
            } else {
                buttonfalse.setText(R.string.save);
            }
        }
        buttonfalse.invalidate();
    }

    @Override
    protected void onResume() {

        super.onResume();
        if (mPlaylistDialog != null) {
            mPlaylistDialog.show();
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Log.i("SSS","AAAAAAAAAA");
    }

    @Override
    public void onClick(View arg0) {
        switch (arg0.getId()) {
            case R.id.btn_quxiao:
                finish();
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                break;
            case R.id.et_add:
                //设置对话框显示的位置
                WindowManager.LayoutParams win = mPlaylistDialog.getWindow().getAttributes();
                win.y=90;
                mPlaylistDialog.getWindow().setAttributes(win);
                break;
        }

    }

}
