package com.readboy.game;

import java.io.IOException;
import java.net.ContentHandler;
import java.util.HashMap;

import com.readboy.mentalcalculation.R;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.util.Log;

public class PlaySound {
	final int FAIL=2;
	final int SUCCESS=1;
	private Context content;
	//private SoundPool mSoundPool = null;  
    //private HashMap<Integer, Integer> soundID = new HashMap<Integer, Integer>();  
    private MediaPlayer mp_success;
    private MediaPlayer mp_fail;  
    
    
	public PlaySound(Context context) {
		super();
		// TODO Auto-generated constructor stub
		content=context;
		Log.i("mentalwubingchao", "sound init success");
		try {
			initSP();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.i("mentalwubingchao", "sound init fail");
		}
	}

	@SuppressWarnings("static-access")
	private void initSP() throws Exception {   
//        mSoundPool = new SoundPool(2, 0, 5);  
//        soundID.put(1, mSoundPool.load(content,R.raw.success,1));
//        soundID.put(2, mSoundPool.load(content,R.raw.fail,1));  //需要捕获IO异常
		mp_success=MediaPlayer.create(content, R.raw.success);
		mp_fail=MediaPlayer.create(content,R.raw.fail);
    }  
	
	
	
	
	/**
	 * 播放音效
	 * 
	 * @param num_sound：1代表成功的音效 2代表失败音效
	 */
	public void PlaySoundSuccessOrFail(int num_sound){
		switch (num_sound){
		case SUCCESS:
			if(mp_success==null){
				Log.i("mentalwubingchao", "mp_success is null");
				return;
			}
			try {
				mp_success.stop();
				Log.i("mentalwubingchao", "prepare1 success sound wrong");
				mp_success.prepare();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				Log.i("mentalwubingchao", "prepare1 success sound wrong");
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.i("mentalwubingchao", "prepare2 success sound wrong");
			}
			mp_success.start();
			Log.i("mentalwubingchao", "start success sound wrong");
			break;
		case FAIL:
			if (mp_fail==null)
				return;
			try {
				mp_fail.stop();
				mp_fail.prepare();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.i("mentalwubingchao", "prepare fail sound wrong");
			}
			mp_fail.start();
			break;
		}
	}
	
	
	public void StopSoundSuccessOrFail(int num_sound){
		switch (num_sound){
		case SUCCESS:
			if(mp_success!=null){
				mp_success.pause();
				mp_success.seekTo(0);
			}
			break;
		case FAIL:
			if(mp_fail!=null){
				mp_fail.pause();
				mp_fail.seekTo(0);
			}
			break;
		}
	}
}
