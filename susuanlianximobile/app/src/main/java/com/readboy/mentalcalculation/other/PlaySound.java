package com.readboy.mentalcalculation.other;

import java.io.IOException;

import android.content.Context;
import android.media.MediaPlayer;

import com.readboy.mentalcalculation.R;

public class PlaySound {
    final int FAIL = 2;
    final int SUCCESS = 1;
    private Context content;
    public MediaPlayer mp_success;
    public MediaPlayer mp_fail;


    public PlaySound(Context context) {
        super();
        content = context;
        try {
            initSP();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("static-access")
    private void initSP() throws Exception {
        mp_success = MediaPlayer.create(content, R.raw.success);
        mp_fail = MediaPlayer.create(content, R.raw.fail);
    }

    /**
     * 播放音效
     *
     * @param num_sound：1代表成功的音效 2代表失败音效
     */
    public void PlaySoundSuccessOrFail(int num_sound) {
        switch (num_sound) {
            case SUCCESS:
//			if(mp_success==null){
//				return;
//			}
                try {
                    if (mp_success != null) {
                        mp_success.stop();
                        mp_success.release();
                        mp_success = null;
                    }
                    mp_success = MediaPlayer.create(content, R.raw.success);
                    mp_success.prepare();
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mp_success.start();
                break;
            case FAIL:
//			if (mp_fail==null)
//				return;
                try {
                    if (mp_fail != null) {
                        mp_fail.stop();
                        mp_fail.release();
                        mp_fail = null;
                    }
                    mp_fail = MediaPlayer.create(content, R.raw.fail);
                    mp_fail.prepare();
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mp_fail.start();
                break;
        }
    }


}
