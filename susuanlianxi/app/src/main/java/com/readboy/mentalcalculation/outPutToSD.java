package com.readboy.mentalcalculation;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * @author wbc
 *
 */
public class outPutToSD implements Runnable{

	
	Context context;
	Handler handler;
	
	
	public outPutToSD(Context context,Handler handler) {
		super();
		this.context = context;
		this.handler=handler;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		//数据开始输入之前，返回值110给主界面
		Message message_start = new Message(); 
		message_start.arg1=110;
		handler.sendMessage(message_start);
		String path = null ;
		path = getInerSDcard();
		path+="/Aiwrite.dat";   //路径是文件的路径，outputStream会自己创建的。
		
		
		try {
			copyBigDataToSD(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.i("mentalcalculation", "outputtoSdWrong");
		}
		
		//数据输入完成之后，就返回101值给主界面
		Message message = new Message(); 
		message.arg1=101;
		handler.sendMessage(message);
	}
	
	
	/**
     * 将asset中的数据在第一次运行的时候copy到根目录中（线程实现）
     * @param strOutFileName  将文件输出的位置
     * @throws IOException
     */
    private void copyBigDataToSD(String strOutFileName) throws IOException   
    {  
        InputStream myInput;    
        OutputStream myOutput = new FileOutputStream(strOutFileName);  
        myInput = context.getAssets().open("Aiwrite.dat");    
        byte[] buffer = new byte[1024];    
        int length = myInput.read(buffer);  
        while(length > 0)  
        {  
            myOutput.write(buffer, 0, length);   
            length = myInput.read(buffer);
        }
		Log.i("mentalcalculation", "copy finish");
        myOutput.flush();    
        myInput.close();    
        myOutput.close();          
    }  

    
    /**
     * 获取到内置sd卡的路径
     * @return 返回内置sd卡的路径
     */
    private String getInerSDcard(){
    	return Environment.getExternalStorageDirectory().getPath();
    }
}
