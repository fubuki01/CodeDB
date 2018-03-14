package com.readboy.HandWrite;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class startThread implements Runnable{

	int type;
	Handler handler;
	public startThread(int type,Handler handler) {
		// TODO Auto-generated constructor stub
		this.type=type;
		this.handler=handler;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		 Log.i("mentalwubingchao", "thread is start running");
		 Message message = new Message(); 
		 Bundle bundle=new Bundle();
		 bundle.putInt("type",type);
		 message.setData(bundle);
		 handler.sendMessage(message);
	}

}
