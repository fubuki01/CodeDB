package com.readboy.game;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import com.readboy.HandWrite.DraftView;
import com.readboy.HandWrite.startThread;
import com.readboy.mentalcalculation.R;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class draftActivity extends Activity{
	protected DraftView d_v; 
	private Button close;
	private Button draft_cancle;
	private Button draft_redo;
	private Button draft_clear;


	private Button write_button;
	private Button earse_button;
	private Button move_button;
	private Button clear_button;
	private Button end_button;
	private Button delete_button;
	private Button left_button;
	private Button right_button;
	private TextView draft_current;
	private TextView draft_all;
	private int currentState;
	
	final private int WRITE=1;// 写状态
	final private int ERASE=2;//橡皮擦状态
	final private int MOVE=3;//移动状态
	final private int ADD=4;
	final private int LEFT=5;
	final private int RIGHT=6;
	final private int NONE=7;
	
	
	private int temp_left=0;
	private int temp_add=0;
	private int temp_right=0;
	private int temp_move=0;
	private int state=-1;
	
	private Timer timer = new Timer();
	
	private boolean is_ok=false;
	
	private startThread startthread;
	
	
	
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);  
        setContentView(R.layout.draft_layout);
        
        init();
        listenerEvent();
        
    }
	
	
	protected void onDestroy(){
		Log.i("mentalwubingchao", "on destorys");
		
		//清空本地文件中存放的bitmap
		Thread thread = new Thread(new Runnable() {
			String path = Environment.getExternalStorageDirectory().getPath() +"/BitMapCacheFiles/"; 
			File file = new File(path);
			@Override
			public void run() {
				// TODO Auto-generated method stub
				delete(file);
			}
		});
		thread.start();
		
		//清空当前存在的currentBitmap
		d_v.clearCurrentBitMap();
		super.onDestroy();
	}
	
	protected void init(){
		close=(Button) findViewById(R.id.close);
		d_v = (DraftView) findViewById(R.id.draft_screen);
		d_v.paint.setColor(Color.argb(255, 0, 149, 255));//设置画笔颜色 wyd
		d_v.paint.setStrokeWidth(8);//设置空心线宽 即设置线的宽度 wyd

		write_button=(Button) findViewById(R.id.draft_write_button);
		earse_button=(Button) findViewById(R.id.draft_erase_button);
		move_button=(Button) findViewById(R.id.draft_move_button);
		clear_button=(Button) findViewById(R.id.draft_clean_button);
		end_button=(Button) findViewById(R.id.draft_finished_button);
//		d_v = (DraftView) findViewById(R.id.draft_screen);
		delete_button=(Button) findViewById(R.id.draft_remove_button);
		left_button=(Button) findViewById(R.id.draft_left_button);
		right_button=(Button) findViewById(R.id.draft_right_button);
		draft_current=(TextView) findViewById(R.id.draft_current);
		draft_all=(TextView) findViewById(R.id.draft_all);
		whichButtonShow(0);
		
		//清空本地文件中存放的bitmap
				Thread thread = new Thread(new Runnable() {
					String path = Environment.getExternalStorageDirectory().getPath() +"/BitMapCacheFiles/"; 
					File file = new File(path);
					@Override
					public void run() {
						// TODO Auto-generated method stub
						delete(file);
					}
				});
				thread.start();
	}
	
	/**
	 * 给按钮添加监听事件
	 */
	protected void listenerEvent(){
		//完成按钮
		close.setOnClickListener(new OnClickListener() {
					public void onClick(View arg0) {
						draftActivity.this.finish();
					}
				});
		//清除按钮
		draft_clear.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				d_v.reSetPath();

			}
		});	
		
		//移动按钮
		move_button.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						whichButtonShow(2);
						d_v.move();
						currentState=MOVE;
					}
				});
		//擦除按钮
		earse_button.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						whichButtonShow(1);
						d_v.erase();
						currentState=ERASE;
					}
				});
		//练习按钮
		write_button.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				whichButtonShow(0);
				d_v.write();
				currentState=WRITE;  
				
			}
		});
		
		//删除草稿纸
		delete_button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				int current_num=Integer.parseInt(draft_current.getText().toString());
				int all_num=Integer.parseInt(draft_all.getText().toString());
				if(all_num>=2 && current_num!=1){
					if(d_v.deleteButton(LEFT)){
						draft_current.setText(current_num-1+"");
						draft_all.setText(all_num-1+"");
					}
				}
				
				if(all_num>=2 && current_num==1){
					if(d_v.deleteButton(RIGHT)){
						draft_current.setText("1");
						draft_all.setText(all_num-1+"");
					}
				}
				
				
			
			}
		});
		
		//查看left草稿纸
		left_button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				
				/*将屏幕的计数改变，但是暂时不显示bitmap的内容*/
				int current_num=Integer.parseInt(draft_current.getText().toString())-1;
				if(current_num!=0){
					saveThreadJudge();
				}
				if(current_num>=1){
					draft_current.setText(current_num+"");
					temp_left++;   //用来计数移动了多少位置
				}
				else{
					draft_current.setText("1");
					return;
				}
				
				
				showScreenContent();
				
	    		
				//保存当前状态
				currentState(currentState);
			}
		});
		
		//查看right草稿纸
		right_button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				int current_num=Integer.parseInt(draft_current.getText().toString())+1;
				
				int all_num=Integer.parseInt(draft_all.getText().toString());
				
				//没有增加，只是简单的切换到右边的bitmap
				if(current_num<=all_num){
					saveThreadJudge();
					/*将屏幕的计数改变，但是暂时不显示bitmap的内容*/
					draft_current.setText(current_num+"");
					temp_right++;   //用来计数移动了多少位置
					
					showScreenContent();
					
					
				//新增加一个草稿纸
				}else{ 
					//当第一次点击的时候保存当前图片
					saveThreadJudge();
					
					/*判断到了第几张图片了*/
					temp_add++;
					
					int add_all_num=Integer.parseInt(draft_all.getText().toString())+1;
					draft_current.setText(current_num+"");
					draft_all.setText(add_all_num+"");
					
					
					showScreenContent();
				}
				
				
				//保存当前状态
				currentState(currentState);
			}
		});
	}
	
	public void saveThreadJudge(){
		/*第一次按键的时候，存储图片*/
		if(temp_left==0 && temp_right==0 && temp_add==0){
			d_v.savaThread();
		}
		
	}
	
	/**
	 * 接收到向左or向右线程的反馈结果
	 */
	final Handler handler=new Handler(){
		 public void handleMessage(Message msg) {
			Bundle b = msg.getData();
			int time = b.getInt("type");
			d_v.setVisibility(View.VISIBLE);
			switch (time) {
			case ADD:
				Log.i("", "handler add in");
				try {
					if(d_v.addButton(temp_move)){
						temp_add=0;
						temp_right=0;
						temp_left=0;
						temp_move=0;
						Log.i("mentalwubingchao", "handler add success");
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Log.i("mentalwubingchao", "ADD 创建失败 ");
				}
				break;
			case LEFT:
				if(d_v.leftButton(-temp_move)){
					temp_add=0;
					temp_right=0;
					temp_left=0;
					temp_move=0;
					Log.i("mentalwubingchao", "handler leftmove success");
				}
				else{
					Log.i("mentalwubingchao", "leftbutton读取错误");
				}
				break;
				
			case RIGHT:
				d_v.max_bitmap_index=Integer.parseInt(draft_all.getText().toString());
				if(d_v.rightButton(temp_move)){
					temp_add=0;
					temp_right=0;
					temp_left=0;
					temp_move=0;
					Log.i("mentalwubingchao", "handler rightmove success");
				}
				else{
					Log.i("mentalwubingchao", "rightbutton读取错误");
				}
				break;
				
			case NONE:
				if(d_v.moveToCurrentPosition()==true){
					temp_add=0;
					temp_right=0;
					temp_left=0;
					temp_move=0;
					Log.i("mentalwubingchao", "handler moveCurrent success");
				}
				break;
			default:
				break;
			}
		 }
	};
	
	/**手指离开一段时间后，屏幕中从sd卡中加载内容
	 * state_temp:1代表向右，2代表增加
	 */
	public void showScreenContent(){
		/*当手指释放一秒钟的时候，显示bitmap的内容*/
		int current_num=Integer.parseInt(draft_current.getText().toString());
		int all_num=Integer.parseInt(draft_all.getText().toString());
		//d_v.setClickable(false);
		d_v.setVisibility(View.INVISIBLE);
		if(current_num==all_num){
			state=2;
		}
		else{
			state=1;
		}
		timer.cancel();
		timer = new Timer(); 
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				//d_v.setClickable(true);
				Log.i("mentalwubingchao", "run is start");
				temp_move=temp_right+temp_add-temp_left;//大于等于0表示想右移动
				Log.i("mentalwubingchao", "really move "+temp_move+" steps");
				if(temp_move==0){
					//do nothing 
					startthread=new startThread(NONE,handler);
					new Thread(startthread).start();
				}
				else if(temp_move<0){
					startthread=new startThread(LEFT,handler);
					new Thread(startthread).start();
					Log.i("mentalwubingchao", "step into leftmove");
				}
				else {
					if(state==1){
						startthread=new startThread(RIGHT,handler);
						new Thread(startthread).start();
						Log.i("mentalwubingchao", "step into rightmove");
					}
					else if(state==2){
						startthread=new startThread(ADD,handler);
						new Thread(startthread).start();
						Log.i("mentalwubingchao", "step into addmove");
					}
				}
			}
		}, 1000);
	}
	
	 /**
	  * 清空草稿本
	 * @param file
	 */
	public static void delete(File file) {
	        if (file.isFile()) {  
	            file.delete();  
	            return;  
	        }  
	          if(file.isDirectory()){  
	            File[] childFiles = file.listFiles();  
	            if (childFiles == null || childFiles.length == 0) {  
	                file.delete();  
	                return;  
	            }        
	            for (int i = 0; i < childFiles.length; i++) {  
	            	delete(childFiles[i]);  
	            }  
	            file.delete();  
	        }  
	    } 
	/*显示当前按钮状态*/
	@SuppressWarnings("deprecation")
	public void whichButtonShow(int type){
		Resources resources=getBaseContext().getResources();
		Drawable bWrite1 = resources.getDrawable(R.drawable.write2);
		Drawable bErase1 = resources.getDrawable(R.drawable.erase2);
		Drawable bMove1= resources.getDrawable(R.drawable.hand2);
		Drawable bWrite2 = resources.getDrawable(R.drawable.write1);
		Drawable bErase2 = resources.getDrawable(R.drawable.erase1);
		Drawable bMove2= resources.getDrawable(R.drawable.hand1);
		switch(type){
		case 0:
			write_button.setBackgroundDrawable(bWrite2);
			earse_button.setBackgroundDrawable(bErase1);
			move_button.setBackgroundDrawable(bMove1);
			break;
		case 1:
			write_button.setBackgroundDrawable(bWrite1);
			earse_button.setBackgroundDrawable(bErase2);
			move_button.setBackgroundDrawable(bMove1);
			break;
		case 2:
			write_button.setBackgroundDrawable(bWrite1);
			earse_button.setBackgroundDrawable(bErase1);
			move_button.setBackgroundDrawable(bMove2);
			break;
		}
	}
	
	
	/**
	 * 判断当前状态
	 */
	
	public void currentState(int state){
		switch (state) {
		case WRITE:
			whichButtonShow(0);
			d_v.write();
			break;
		case ERASE:
			whichButtonShow(1);
			d_v.erase();
			break;
			
		case MOVE:
			whichButtonShow(2);
			d_v.move();
			break;
		default:
			break;
		}
	}
   
}
