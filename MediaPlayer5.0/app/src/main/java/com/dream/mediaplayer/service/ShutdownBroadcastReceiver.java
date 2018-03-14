package com.dream.mediaplayer.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.dream.mediaplayer.activity.MainActivity;

/**
 * Created by Wyd on 2017/6/29.
 */

public class ShutdownBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = "ShutdownBroadcastReceiver";
    private static final String ACTION_SHUTDOWN = "android.intent.action.ACTION_SHUTDOWN";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ACTION_SHUTDOWN)) {
            System.exit(0);
        }
    }
}
