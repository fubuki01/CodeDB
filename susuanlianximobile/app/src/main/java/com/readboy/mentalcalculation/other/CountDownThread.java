package com.readboy.mentalcalculation.other;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class CountDownThread implements Runnable {

	int count_time=180;
	int start_time;
	Handler mHandler;
	boolean stopThread;
	boolean stop=false;
	public static boolean destory = false;
	int stop_time;
	public CountDownThread(int count_time, Handler time_hander) {
		this.count_time=count_time;
		mHandler=time_hander;
		this.start_time=count_time;
		stopThread=false;
		destory = false;
	}

	public void setStartTime(int startTime){
		count_time=startTime;
	}

	public void setStop(boolean is_stop,int stop_time){
		stop=is_stop;
		this.stop_time=stop_time;
	}
	public void setTag(boolean stopThread){
		this.stopThread=stopThread;
	}
	@Override
	public void run() {
		while(!stopThread){
			while(count_time!=0&&(!destory)){
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				count_time--;
				Message message = new Message();
				Bundle bundle=new Bundle();
				if(stop==false)
					bundle.putInt("time", count_time);
				else
					bundle.putInt("time", stop_time);
				message.setData(bundle);
				mHandler.sendMessage(message);
			}
		}
	}
}
