package com.dream.mediaplayer.activity;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.dream.mediaplayer.R;
import com.dream.mediaplayer.helpers.utils.ApolloUtils;
import com.dream.mediaplayer.helpers.utils.MenuColorFix;
import com.dream.mediaplayer.helpers.utils.MusicUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_OK;
import static android.content.Intent.ACTION_CREATE_SHORTCUT;
import static com.dream.mediaplayer.Constants.INTENT_ADD_TO_PLAYLIST;
import static com.dream.mediaplayer.Constants.INTENT_CREATE_PLAYLIST;
import static com.dream.mediaplayer.Constants.INTENT_PLAYLIST_LIST;
import static com.dream.mediaplayer.Constants.PLAYLIST_NEW;
import static com.dream.mediaplayer.Constants.PLAYLIST_QUEUE;

/**
 * Created by Administrator on 2017/6/15.
 */

public class LongClickDialog {

    private Context context;
    String action;
    long[] list;
    private android.app.AlertDialog ad;
    private Window window;
    List<Map<String, String>> mAllPlayLists = new ArrayList<Map<String, String>>();
    private ListView mlist;
    List<String> mPlayListNames = new ArrayList<String>();
    public  static  boolean enterPlaylistFormAlbum =false;
    public LongClickDialog(Context context , String action, long[] list) {
        this.context = context;
        this.action = action;
        this.list = list;
        ad=new android.app.AlertDialog.Builder(context,R.style.LongClickDialog).create();
        ad.show();
        WindowManager.LayoutParams lp=ad.getWindow().getAttributes();
        lp.width= ApolloUtils.dp2px(context, 384);
        lp.height=WindowManager.LayoutParams.WRAP_CONTENT;

        window = ad.getWindow();
        window.setAttributes(lp);
        window.setGravity(Gravity.CENTER);
        window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        window.setContentView(R.layout.long_click_dialog);
        init();
    }

    private void init() {
        if (action != null) {

            if (INTENT_ADD_TO_PLAYLIST.equals(action)
                    && list != null) {

                MusicUtils.makePlaylistList(this.context, false, mAllPlayLists);
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

                mlist = (ListView) ad.findViewById(R.id.longClickDialogList);
                SimpleAdapter mAdapter = new SimpleAdapter(
                        context,ss,R.layout.long_click_dialog_item,
                        new String[]{"string"},new int[]{R.id.item});
                mlist.setAdapter( mAdapter);
                mlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        onClick(ad,position);
                    }
                });

            } else if (action.equals(ACTION_CREATE_SHORTCUT)) {
                MusicUtils.makePlaylistList(context, true, mAllPlayLists);
                for (int i = 0; i < mAllPlayLists.size(); i++) {
                    mPlayListNames.add(mAllPlayLists.get(i).get("name"));
                }
//                int color = Color.rgb(42, 232, 255);
                SpannableString[] s = new SpannableString[mPlayListNames.size()];
                for (int i = 0; i < s.length; i++){
                    s[i] = new SpannableString(mPlayListNames.get(i));
                    s[i].setSpan(new ForegroundColorSpan(MenuColorFix.getTextColor()), 0, s[i].length(), 0);
                }
            }
        } else {
            Toast.makeText(context, R.string.error, Toast.LENGTH_SHORT).show();
            ad.dismiss();
        }
    }

    public void onClick(DialogInterface dialog, int which) {

        long listId = Long.valueOf(mAllPlayLists.get(which).get("id"));
        String listName = mAllPlayLists.get(which).get("name");
        Intent intent;
        boolean mCreateShortcut = action.equals(Intent.ACTION_CREATE_SHORTCUT);

        if (mCreateShortcut) {
            final Intent shortcut = new Intent();
            // shortcut.setAction(INTENT_PLAY_SHORTCUT);
            shortcut.putExtra("id", listId);

            intent = new Intent();
            intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcut);
            intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, listName);
//            intent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
//                    Intent.ShortcutIconResource.fromContext(this, R.drawable.app_icon));
            //setResult(RESULT_OK, intent);
        } else {
            if (listId >= 0) {
                MusicUtils.addToPlaylist(context, list, listId);
            } else if (listId == PLAYLIST_QUEUE) {
                MusicUtils.addToCurrentPlaylist(context, list);
            } else if (listId == PLAYLIST_NEW) {
                enterPlaylistFormAlbum = true;
                intent = new Intent(INTENT_CREATE_PLAYLIST);
                intent.putExtra(INTENT_PLAYLIST_LIST, list);
                context.startActivity(intent);
            }
        }
        ad.dismiss();
    }
}
