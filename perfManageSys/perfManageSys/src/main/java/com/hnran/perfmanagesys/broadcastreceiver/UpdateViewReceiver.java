package com.hnran.perfmanagesys.broadcastreceiver;

import java.lang.ref.SoftReference;

import com.hnran.perfmanagesys.fragment.market.MarketCommonFragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class UpdateViewReceiver extends BroadcastReceiver{

	public final static String UPDATE_VIEW = "com.hnran.perfmanagesys.broadcastreceiver.UPDATE";

	public final static String CLOSE_SOCKET = "com.hnran.perfmanagesys.broadcastreceiver.CLOSE_SOCKET";

	private SoftReference<MarketCommonFragment> mSoftReference;


	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Log.d("UpdateViewReceiver","onReceive");
		//在实际使用场景中，重写方法
		String str = intent.getAction();
		switch(str){
		case UPDATE_VIEW:
//			Bundle bundle = intent.getExtras();
//			MarketCommonFragment market = bundle.getParcelable("");
//			mSoftReference = new SoftReference<>(market);
//			mSoftReference.get().updateInMarketHome();
			break;

		case CLOSE_SOCKET:
			break;

		}


	}

}
