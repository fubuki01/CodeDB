/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dream.mediaplayer.service;

import com.dream.mediaplayer.activity.PlayActivity;
import com.dream.mediaplayer.helpers.utils.SystemSettingsUtils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;


public class MediaButtonIntentReceiver extends BroadcastReceiver {

    private static final int MSG_LONGPRESS_TIMEOUT = 1;

    private static final int LONG_PRESS_DELAY = 1000;

    private static long mLastClickTime = 0;

    private static boolean mDown = false;

    private static boolean mLaunched = false;
    private static long mHits[] = new long[3];
    private static Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LONGPRESS_TIMEOUT:
                    if (!mLaunched) {
                       	Context context = (Context)msg.obj;
                        Intent i = new Intent();
                        i.putExtra("autoshuffle", "true");
                        i.setClass(context, PlayActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(i);
                        mLaunched = true;
                    }
                    break;
            }
        }
    };

    @Override
    public void onReceive(Context context, Intent intent) {
        int systemMode = SystemSettingsUtils.getCurSysMode(context,0);
/*        Log.i("MODE1",systemMode+"");*/
        // if the current system mode is 1 or 3 ,then ignore the clicks of media button
        if(systemMode == 1 || systemMode == 3){
            return;
        }
        String intentAction = intent.getAction();
        if (AudioManager.ACTION_AUDIO_BECOMING_NOISY.equals(intentAction)) {
        	/*if(ErJiContrlerActivity.StopErJIvalue==1){
        		Toast.makeText(context,R.string.erji_outstop, Toast.LENGTH_SHORT).show();
                Intent i = new Intent(context, ApolloService.class);
                i.setAction(ApolloService.SERVICECMD);
                i.putExtra(ApolloService.CMDNAME, ApolloService.CMDPAUSE);
                context.startService(i);
        	}*/

			Intent i = new Intent(context, ApolloService.class);
            i.setAction(ApolloService.SERVICECMD);
            i.putExtra(ApolloService.CMDNAME, ApolloService.CMDPAUSE);
            context.startService(i);

        } else if (Intent.ACTION_MEDIA_BUTTON.equals(intentAction)) {
            Log.i("MODE1","222");
        	KeyEvent event = (KeyEvent)intent.getParcelableExtra(Intent.EXTRA_KEY_EVENT);
            if (event == null) {
                return;
            }

            int keycode = event.getKeyCode();
            int action = event.getAction();
            long eventtime = event.getEventTime();

            // single quick press: pause/resume.
            // double press: next track
            // trible press: previous track
            // long press: start auto-shuffle mode.

            String command = null;
            switch (keycode) {
                case KeyEvent.KEYCODE_MEDIA_STOP:
                    command = ApolloService.CMDSTOP;
                    break;
                case KeyEvent.KEYCODE_HEADSETHOOK:
                case KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE:
                    command = ApolloService.CMDTOGGLEPAUSE;
                    break;
                case KeyEvent.KEYCODE_MEDIA_NEXT:
                    command = ApolloService.CMDNEXT;
                    break;
                case KeyEvent.KEYCODE_MEDIA_PREVIOUS:
                    command = ApolloService.CMDPREVIOUS;
                    break;
                case KeyEvent.KEYCODE_MEDIA_PAUSE:
                    command = ApolloService.CMDPAUSE;
                    break;
                case KeyEvent.KEYCODE_MEDIA_PLAY:
                    command = ApolloService.CMDPLAY;
                    break;
            }

            if (command != null) {

                if (action == KeyEvent.ACTION_DOWN) {
                    System.arraycopy(mHits,1,mHits,0,mHits.length-1);
                    mHits[mHits.length-1] = SystemClock.uptimeMillis();
                    if (mDown) {
                        if ((ApolloService.CMDTOGGLEPAUSE.equals(command) ||
                                ApolloService.CMDPLAY.equals(command))
                                && mLastClickTime != 0
                                && eventtime - mLastClickTime > LONG_PRESS_DELAY) {
                            mHandler.sendMessage(mHandler.obtainMessage(MSG_LONGPRESS_TIMEOUT,
                                    context));
                        }
                    } else if (event.getRepeatCount() == 0) {

                        // only consider the first event in a sequence, not the
                        // repeat events,
                        // so that we don't trigger in cases where the first
                        // event went to
                        // a different app (e.g. when the user ends a phone call
                        // by
                        // long pressing the headset button)

                        // The service may or may not be running, but we need to
                        // send it
                        // a command.

                        Intent i = new Intent(context, ApolloService.class);
                        i.setAction(ApolloService.SERVICECMD);

                        if (keycode == KeyEvent.KEYCODE_HEADSETHOOK && eventtime - mLastClickTime < 500) {
                            Log.i("XX",(mHits[2]-mHits[1])+" "+(mHits[1]-mHits[0])+"");
                            if(mHits[2]-mHits[1] < 500 && mHits[1]-mHits[0] <= 1200){
                                i.putExtra(ApolloService.CMDNAME, ApolloService.CMDPREVIOUS);
                                context.startService(i);
//                                Log.i("XX","3");
                            }else if(mHits[2]-mHits[1] < 500 && mHits[1]-mHits[0] > 1200){
                                i.putExtra(ApolloService.CMDNAME, ApolloService.CMDNEXT);
                                context.startService(i);
//                                Log.i("XX","2");
                            }

                            mLastClickTime = 0;
                        } else {
                            i.putExtra(ApolloService.CMDNAME, command);
                            context.startService(i);
                            Log.i("ApolloService","启动");
                            mLastClickTime = eventtime;
                        }
                        mLaunched = false;
                        mDown = true;
                    }
                } else {
                    mHandler.removeMessages(MSG_LONGPRESS_TIMEOUT);
                    mDown = false;
                }
                if (isOrderedBroadcast()) {
                    abortBroadcast();
                }
            }
        }
    }
}
