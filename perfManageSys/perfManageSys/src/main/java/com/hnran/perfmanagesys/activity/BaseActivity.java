package com.hnran.perfmanagesys.activity;

import com.hnran.perfmanagesys.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

/**
 * BaseActivity基类
 * @author tan
 * 2017/05/04
 *
 */
public abstract class BaseActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        
        initVariables();
        initViews(savedInstanceState);
   
		loadData();
        
    }
    
    /**
     * 初始化变量
     */
    protected abstract void initVariables();
    
    /**
     * 加载布局文件，初始化控件，添加监听事件
     * @param savedInstanceState
     */
    protected abstract void initViews(Bundle savedInstanceState);
    
    /**
     * 加载数据
     */
    protected abstract void loadData();
    
    /**
     * 刷新页面
     */
    protected abstract void notifyView();
    
    @Override
    protected void onDestroy() {
    	// TODO Auto-generated method stub
    	super.onDestroy();
    }
    
    @Override
    public void startActivity(Intent intent) {  
        super.startActivity(intent);  
        overridePendingTransition(R.anim.transition_in_right, R.anim.transition_out_left);  
    }
    
    @Override  
    public void onBackPressed() {  
        super.onBackPressed();  
        overridePendingTransition(R.anim.transition_in_left, R.anim.transition_out_right);  
    } 
    
}
