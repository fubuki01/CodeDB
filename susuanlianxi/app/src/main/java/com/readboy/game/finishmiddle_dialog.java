package com.readboy.game;

import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.readboy.mentalcalculation.R;

public class finishmiddle_dialog {
	Context context;
    android.app.AlertDialog ad;
    ImageView back_bt;
    ImageButton finish_middle_canle;
    ImageButton finish_middle_quit;
	public finishmiddle_dialog(Context context) {
		this.context = context;
		ad=new android.app.AlertDialog.Builder(context).create();
        Window window = ad.getWindow();
        ad.show();
        window.setContentView(R.layout.finishmiddle_dialog_g);
//        back_bt = (ImageView) window.findViewById(R.id.back_bt);
        finish_middle_canle = (ImageButton) window.findViewById(R.id.finish_middle_canle);
        finish_middle_quit = (ImageButton) window.findViewById(R.id.finish_middle_quit);
        ad.setCancelable(false);
	}
	
	/*点击继续按钮*/
    public void setClickContinueEvent(final View.OnClickListener listener){
    	finish_middle_canle.setOnClickListener(listener);
//        back_bt.setOnClickListener(listener);
    }
    
    
    /*点击退出按钮*/
    public void setClickQuitEvent(final View.OnClickListener listener){
    	finish_middle_quit.setOnClickListener(listener);

    	
    }
    
    public void dismiss() {
        ad.dismiss();
    } 
    
    public void show() {
		ad.show();
	}
    
}
