package com.readboy.mentalcalculation.game.SupplyThread;

import java.util.ArrayList;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.readboy.mentalcalculation.game.MyActivity.ActivityCollector;

public  class Supply_Grade_1_top implements Runnable  {
	Handler handler_program;
	Object Alock;
	int count=0;
	int type; //出题类型
	boolean stopThread;
	ArrayList<String> problem;
	ArrayList<Integer> answer;

	final private int ADDINFIVE=1;  //1~5的加法
	final private int SUBINFIVE=2;  //1~5的减法
	final private int SIXSEVENAS=3;  //6、7的加减法
	final private int EIGHTNINAS=4;  //8、9的加减法
	final private int TENAS=5;  //8、9的加减法
//  final private int ADDINSIXTOTEN = 2; //6~10的加减法
	final private int CONTINUEADD = 6; // 连加
	final private int CONTINUESUD=7;		//连减
	final private int ADDANDSUBINTEN = 8; //10以内的加减混合运算


	final private int FIVETOTWO=9; 	//2、3、4、5加几的进位加法
	final private int SEVENEIGHT=10;	//6、7、8加几的进位加法
	final private int NINEADDWHAT=11;		//9加几的计算方法
	

	
	public Supply_Grade_1_top(Handler handler_program,Object Alock,int type) {
		// TODO Auto-generated constructor stub
		this.handler_program=handler_program;
		this.Alock=Alock;
		this.type=type;
		stopThread=false;
	}
	
	public void setTag(boolean stopThread){
		this.stopThread=stopThread;
	}
	
	/*产生题目*/
	
	public  void CreateSubject(){
		int ele_one=-1;
		int ele_two=-1;
		int ele_three=-1;
		int choose=Math.random()>0.5?1:0;   //选择加减
		int choose2=Math.random()>0.5?1:0; //选择加减
		int choose_num=Math.random()>0.5?1:0;//选择数字
		switch (type) {
		case ADDINFIVE://1~5的加法
			ele_one=(int)(1+Math.random()*5);
			ele_two=(int)(1+Math.random()*5);
			while(ele_two+ele_one>5){
				ele_one=(int)(1+Math.random()*5);
				ele_two=(int)(1+Math.random()*5);
			}
			problem.add(ele_one+"+"+ele_two+"=");
			answer.add(ele_one+ele_two);
			break;
		case SUBINFIVE: //1~5的减法
			ele_one=(int)(1+Math.random()*5);
			ele_two=(int)(1+Math.random()*(ele_one-1));
			problem.add(ele_one+"-"+ele_two+"=");
			answer.add(ele_one-ele_two);
			break;
//			ele_one=(int)(Math.random()*11);
//			ele_two=(int)(Math.random()*11);
//
//			if (choose==1){
//				while((ele_one+ele_two>10)||((ele_one<6)&&(ele_two<6)&&(ele_one+ele_two<6))){
//					ele_one=(int)(Math.random()*11);
//					ele_two=(int)(Math.random()*11);
//				}
//				Log.e("1111111111111111", ele_one+"");
//				Log.e("2222222222222222222", ele_two+"");
//			}
//			if (choose==0){
//				while ((ele_one<6)||(ele_one<ele_two)){
//					ele_one=(int)(Math.random()*11);
//					ele_two=(int)(Math.random()*11);
//				}
//			}
//			Log.e("1111111111111111", ele_one+"");
//			Log.e("2222222222222222222", ele_two+"");
//			AddAndSubMethod(ele_one,ele_two,choose);
//			break;
		case SIXSEVENAS: //6~7的加减法
			if (choose==1){
				int hj = Math.random()>0.5?1:0;
				if (hj==1){
					ele_one=(int)(Math.random()*7);
					ele_two=7-ele_one;
					problem.add(ele_one+"+"+ele_two+"=");
					answer.add(ele_one+ele_two);
					break;
				}else {
					ele_one=(int)(Math.random()*6);
					ele_two=6-ele_one;
					problem.add(ele_one+"+"+ele_two+"=");
					answer.add(ele_one+ele_two);
					break;
				}
			}else {
				int kl=Math.random()>0.5?1:0;
				if(kl==1){
					ele_one=7;
					ele_two=(int)(Math.random()*8);
					problem.add(ele_one+"-"+ele_two+"=");
					answer.add(ele_one-ele_two);
					break;
				}else {
					ele_one=6;
					ele_two=(int)(Math.random()*7);
					problem.add(ele_one+"-"+ele_two+"=");
					answer.add(ele_one-ele_two);
					break;
				}
			}

		case EIGHTNINAS: //8~9的加减法
			if (choose==1){
				int hj = Math.random()>0.5?1:0;
				if (hj==1){
					ele_one=(int)(Math.random()*10);
					ele_two=9-ele_one;
					problem.add(ele_one+"+"+ele_two+"=");
					answer.add(ele_one+ele_two);
					break;
				}else {
					ele_one=(int)(Math.random()*9);
					ele_two=8-ele_one;
					problem.add(ele_one+"+"+ele_two+"=");
					answer.add(ele_one+ele_two);
					break;
				}
			}else {
				int kl=Math.random()>0.5?1:0;
				if(kl==1){
					ele_one=9;
					ele_two=(int)(Math.random()*10);
					problem.add(ele_one+"-"+ele_two+"=");
					answer.add(ele_one-ele_two);
					break;
				}else {
					ele_one=8;
					ele_two=(int)(Math.random()*9);
					problem.add(ele_one+"-"+ele_two+"=");
					answer.add(ele_one-ele_two);
					break;
				}
			}
		case TENAS: //10的加减法
			if (choose==1){
				ele_one=(int)(Math.random()*10);
				ele_two=10-ele_one;
				problem.add(ele_one+"+"+ele_two+"=");
				answer.add(ele_one+ele_two);
			}else {
				ele_one=10;
				ele_two=(int)(Math.random()*11);
				problem.add(ele_one+"-"+ele_two+"=");
				answer.add(ele_one-ele_two);
			}
			break;

		case CONTINUEADD://连加
			ele_one=(int)(Math.random()*4);
			ele_two=(int)(Math.random()*4);
			ele_three=(int)(Math.random()*2);
			problem.add(ele_one+"+"+ele_two+"+"+ele_three+"=");
			answer.add(ele_one+ele_two+ele_three);
			break;

		case CONTINUESUD://连减
			ele_one=2+(int)(Math.random()*8);
			ele_two=(int)(Math.random()*ele_one);
			ele_three=(int)(Math.random()*(ele_one-ele_two));
			problem.add(ele_one+"-"+ele_two+"-"+ele_three+"=");
			answer.add(ele_one-ele_two-ele_three);
			break;

		case ADDANDSUBINTEN:///10以内的加减混合运算
			ele_one=(int)(Math.random()*11);
			ele_two=(int)(Math.random()*11);
			ele_three =(int)(Math.random()*11);
			if (choose==0){
				while (ele_one-ele_two<0){
					ele_two = (int)(Math.random()*11);
				}
				if (choose2==0){
					while (ele_one-ele_two-ele_three<0){
						ele_three =(int)(Math.random()*11);
					}
				}
			}
			if ((choose==1)&&(choose2==0)){
				ele_two=(int)(Math.random()*(11-ele_one));
				while ((ele_one+ele_two-ele_three<0)){
					ele_three=(int)(Math.random()*11);
				}
			}
			if ((choose==1)&&(choose2==1)){
				while (ele_one+ele_two>10){
					ele_one=(int)(Math.random()*11);
					ele_two=(int)(Math.random()*11);
				}
				while (ele_one+ele_two+ele_three>10){
					ele_three=(int)(Math.random()*11);
				}
			}
			AddAndSubMethod2(ele_one,ele_two,ele_three,choose,choose2);
			break;




		case FIVETOTWO:			//2、3、4、5加几的进位加法
			if(choose==1&&choose_num==1){
				ele_one=5;
				ele_two=5+(int)(Math.random()*4);
			}
			else if(choose==0&&choose_num==1){
				ele_one=4;
				ele_two=6+(int)(Math.random()*3);
			}
			else if(choose==1&&choose_num==0){
				ele_one=3;
				ele_two=7+(int)(Math.random()*2);
			}
			else{
				ele_one=2;
				ele_two=8+(int)(Math.random()*1);
			}
			AddAndSubMethod(ele_one,ele_two,1);
			break;

		case SEVENEIGHT:		//6、7、8加几的进位加法
			if(choose==1&&choose_num==1){
				ele_one=8;
			}
			else if(choose==0&&choose_num==1){
				ele_one=7;
			}
			else if(choose==1&&choose_num==0){
				ele_one=6;
			}
			else{
				ele_one=6;
			}
			ele_two=4+(int)(Math.random()*5);
			AddAndSubMethod(ele_one,ele_two,1);
			break;
		case NINEADDWHAT:		//9加几的进位加法
			ele_one=9;
			ele_two=1+(int)(Math.random()*9);
			AddAndSubMethod(ele_one,ele_two,1);
			break;

		default:
			break;
		}
	}

	
	
	/*计算两个数的加减法*/
	public void AddAndSubMethod(int num1,int num2,int choose){
		if(choose==1){
			problem.add(num1+"+"+num2+"=");
			answer.add(num1+num2);
			Log.e("33333333333333", num1+num2+"");
		}
		else{
			problem.add(num1+"-"+num2+"=");
			answer.add(num1-num2);
			Log.e("4444444444444444", num1-num2+"");
		}

	}
	/*计算三个数加减法*/
	public void AddAndSubMethod2(int num1,int num2,int num3,int choose,int choose2){
		if((choose==1)&&(choose2==1)){
			problem.add(num1+"+"+num2+"+"+num3+"=");
			answer.add(num1+num2+num3);
		}
		if ((choose==1)&&(choose2==0)){
			problem.add(num1+"+"+num2+"-"+num3+"=");
			answer.add(num1+num2-num3);
		}
		if ((choose==0)&&(choose2==1)){
			problem.add(num1+"-"+num2+"+"+num3+"=");
			answer.add(num1-num2+num3);
		}
		if ((choose==0)&&(choose2==0)){
			problem.add(num1+"-"+num2+"-"+num3+"=");
			answer.add(num1-num2-num3);
		}

	}

	
	public void run() {
		problem=new ArrayList<String>();
		answer=new ArrayList<Integer>();

		while(!stopThread){
			CreateSubject();
			boolean isequ = false;
			while(ActivityCollector.mylist.size()<5)
				ActivityCollector.mylist.add("xx");
			for (int i = 0; i < 5; i++){
				if(problem.get(count).equals(ActivityCollector.mylist.get(i))){
					isequ = true;
					problem.remove(count);
					answer.remove(count);
					break;
				}
			}

			if(isequ)   //跳出当前循环
				continue;
			ActivityCollector.mylist.remove(0);
			ActivityCollector.mylist.add(problem.get(count));

			Message message = new Message(); 
			Bundle bundle=new Bundle();
			bundle.putString("problem",problem.get(count));
			bundle.putString("answer", answer.get(count)+"");
			message.setData(bundle);
			handler_program.sendMessage(message);
			count++;
			synchronized(Alock) {
				try {
					Alock.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				}
		}
	}

}
