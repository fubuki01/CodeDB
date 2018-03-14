package com.dream.mediaplayer.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;

import com.dream.mediaplayer.helpers.utils.MusicUtils;

/**
 * Created by LCL on 2017/4/26.
 */

public class AlarmReceiver extends BroadcastReceiver {

    private final String  pauseMusicActionName = "com.android.music.musicservicecommand.pause";
    private final String  playMusicActionName = "com.android.music.musicservicecommand.togglepause";

    @Override
    public void onReceive(Context context, Intent intent) {
        String intentAction  = intent.getAction();
        if(intentAction.equals(pauseMusicActionName) || intentAction.equals(playMusicActionName)){
            Intent i = new Intent(context, ApolloService.class);
            i.setAction(intentAction);
            i.putExtra(ApolloService.CMDNAME, ApolloService.CMDALARM);
            context.startService(i);
        }
    }
}
