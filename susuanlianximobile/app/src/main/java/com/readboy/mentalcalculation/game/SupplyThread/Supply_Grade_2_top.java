package com.readboy.mentalcalculation.game.SupplyThread;

import java.util.ArrayList;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.readboy.mentalcalculation.game.MyActivity.ActivityCollector;


public class Supply_Grade_2_top implements Runnable{
	Handler handler_program;
	Object Alock;
	int count=0;
	int type; //出题类型
	ArrayList<String> problem;
	ArrayList<Integer> answer;
	boolean stopThread;

	final private int ADDANDSUB=5;  //100以内的加减混合
	final private int INADD=1;		//两位数与两位数的进位加法
	final private int ADDADD=3;		//连加
	final private int SUBSUB=4;		//连减
	final private int INSUB=2;		//两位数与两位数的退位减法

	final private int MULFORSEVEN=12;		//7的乘法口诀
	final private int MULFOREIGHT=13;		//8的乘法口诀
	final private int MULFORNINE=14;		//9的乘法口诀
	final private int MULFORTWO=6;		//2的乘法口诀
	final private int MULFORTHREE=7;		//3的乘法口诀
	final private int MULFORFOUR=8;		//4的乘法口诀
	final private int MULFORFIVE=9;		//5的乘法口诀
	final private int MULFORSIX=11;		//6的乘法口诀
	final private int NULLADDSUB=10;			//乘加乘减

//	  final private int NOINADD=1;  //100以内不进位加法
//	  final private int NOINSUB=2;	//100以内不退位减法
//	  final private int ADDANDSUB=3;  //加减混合
//	  final private int INADD=4;		//两位数与两位数的进位加法
//	  final private int ADDADD=5;		//连加
//	  final private int SUBSUB=6;		//连减
//	  final private int  INSUB=7;		//两位数与两位数的退位减法
//
//
//	  final private int MULFORSEVEN=8;		//7的乘法口诀
//	  final private int MULFOREIGHT=9;		//8的乘法口诀
//	  final private int MULFORNINE=10;		//9的乘法口诀
//	  final private int MULTABLE=11;				//乘法口诀表
//	  final private int NUMDOUBLE=12;			//求一个数的几倍是多少的问题的计算方法
//
//
//	  final private int MULFORTWO=13;		//2的乘法口诀
//	  final private int MULFORTHREE=14;		//3的乘法口诀
//	  final private int MULFORFOUR=15;		//4的乘法口诀
//	  final private int MULFORFIVE=16;		//5的乘法口诀
//	  final private int MULFORSIX=17;		//6的乘法口诀
//	  final private int MULISADD=18;				//乘法是相同的数相加的简便运算
//	  final private int NULLADDSUB=19;			//乘加乘减
	
	public Supply_Grade_2_top(Handler handler_program,Object Alock,int type) {
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
		int choose_num=Math.random()>0.5?1:0;//选择数字
		int temp;
		switch (type) {
//		case NOINADD://100以内不进位加法
//			ele_one=(int)(10+Math.random()*89);
//			int judge_num1=ele_one%10;
//			temp=ele_one/10;
//			ele_two=(int)(Math.random()*(9-judge_num1))+(int)(Math.random()*(9-temp))*10;
//			AddAndSubMethod(ele_one,ele_two,1);
//			break;
//		case NOINSUB://100以内不退位减法
//			ele_one=(int)(10+Math.random()*89);
//			int judge_num3=ele_one%10;
//			temp=ele_one/10;
//			ele_two=(int)(Math.random()*(judge_num3))+(int)(Math.random()*(temp))*10;
//			AddAndSubMethod(ele_one,ele_two,0);
//			break;
		case ADDANDSUB:////加减混合
			ele_one=(int)(10+Math.random()*79);
			ele_two=(int)(10+Math.random()*(90-ele_one));
			ele_three=(int)(10+Math.random()*(ele_one+ele_two-10));
			problem.add(ele_one+"+"+ele_two+"-"+ele_three+"=");
			answer.add(ele_one+ele_two-ele_three);
			break;
		case INADD://两位数与两位数的进位加法
			int judge_num2=0;
			while(judge_num2==0){
				ele_one=(int)(10+Math.random()*70);
				judge_num2=ele_one%10;
			}
			temp=ele_one/10;

			int ele_two_gw=0;
			while(ele_two_gw+judge_num2<10){
				ele_two_gw=(int)(1+Math.random()*9);
			}
			int ele_two_sw=10;
			while(ele_two_sw+temp+1>=10){
				ele_two_sw=(int)(1+Math.random()*7);
			}
			ele_two=ele_two_gw+ele_two_sw*10;
			AddAndSubMethod(ele_one,ele_two,1);
			break;

		case ADDADD://连加
			ele_one=(int)(10+Math.random()*69);
			ele_two=(int)(10+Math.random()*(80-ele_one));
			ele_three=(int)(10+Math.random()*(90-ele_one-ele_two));
			problem.add(ele_one+"+"+ele_two+"+"+ele_three+"=");
			answer.add(ele_one+ele_two+ele_three);
			break;
		
		case SUBSUB://连减
			ele_one=(int)(30+Math.random()*70);
			ele_two=(int)(10+Math.random()*(ele_one-30));
			ele_three=(int)(10+Math.random()*(ele_one-ele_two-10));
			problem.add(ele_one+"-"+ele_two+"-"+ele_three+"=");
			answer.add(ele_one-ele_two-ele_three);
			break;
			
			
		case INSUB://两位数与两位数的退位减法
			int judge_num4=9;
			while(judge_num4==9){
				ele_one=(int)(20+Math.random()*80);
				judge_num4=ele_one%10;
			}
			temp=ele_one/10;

			int ele_two_gwx=0;
			while(ele_two_gwx<=judge_num4){
				ele_two_gwx=(int)(1+Math.random()*9);
			}
			int ele_two_swx=10;
			while(ele_two_swx>=temp){
				ele_two_swx=(int)(1+Math.random()*9);
			}
			ele_two=ele_two_gwx+ele_two_swx*10;
			AddAndSubMethod(ele_one,ele_two,0);
			break;
			
			
		case MULFORSEVEN://7的乘法口诀
			ele_one=7;
			ele_two=(int)(1+Math.random()*8);
			if(ele_two<ele_one){
				MulAndDivMethod(ele_two,ele_one,1);
			}
			else{
				MulAndDivMethod(ele_one,ele_two,1);
			}
			break;
			
		case MULFOREIGHT://8的乘法口诀
			ele_one=8;
			ele_two=(int)(1+Math.random()*8);
			if(ele_two<ele_one){
				MulAndDivMethod(ele_two,ele_one,1);
			}
			else{
				MulAndDivMethod(ele_one,ele_two,1);
			}
			break;
			
		case MULFORNINE://9的乘法口诀
			ele_one=9;
			ele_two=(int)(1+Math.random()*8);
			MulAndDivMethod(ele_two,ele_one,1);
			break;
			
			

			

			
		case MULFORTWO://2的乘法口诀
			ele_one=2;
			ele_two=(int)(1+Math.random()*8);
			if(ele_two<ele_one){
				MulAndDivMethod(ele_two,ele_one,1);
			}
			else{
				MulAndDivMethod(ele_one,ele_two,1);
			}
			break;
			
		case MULFORTHREE://3的乘法口诀
			ele_one=3;
			ele_two=(int)(1+Math.random()*8);
			if(ele_two<ele_one){
				MulAndDivMethod(ele_two,ele_one,1);
			}
			else{
				MulAndDivMethod(ele_one,ele_two,1);
			}
			break;
			
		case MULFORFOUR://4的乘法口诀
			ele_one=4;
			ele_two=(int)(1+Math.random()*8);
			if(ele_two<ele_one){
				MulAndDivMethod(ele_two,ele_one,1);
			}
			else{
				MulAndDivMethod(ele_one,ele_two,1);
			}
			break;
		case MULFORFIVE://5的乘法口诀
			ele_one=5;
			ele_two=(int)(1+Math.random()*8);
			if(ele_two<ele_one){
				MulAndDivMethod(ele_two,ele_one,1);
			}
			else{
				MulAndDivMethod(ele_one,ele_two,1);
			}
			break;
			
		case MULFORSIX://6的乘法口诀
			ele_one=6;
			ele_two=(int)(1+Math.random()*8);
			if(ele_two<ele_one){
				MulAndDivMethod(ele_two,ele_one,1);
			}
			else{
				MulAndDivMethod(ele_one,ele_two,1);
			}
			break;
			

		case NULLADDSUB://乘加乘减
			ele_one=(int)(1+Math.random()*8);
			ele_two=(int)(1+Math.random()*8);
			ele_three=(int)(Math.random()*9);
			if(choose==1){
				problem.add(ele_one+"×"+ele_two+"+"+ele_three+"=");
				answer.add(ele_one*ele_two+ele_three);
			}
			else{
				ele_three=(int)(Math.random()*(ele_one*ele_two));
				problem.add(ele_one+"×"+ele_two+"-"+ele_three+"=");
				answer.add(ele_one*ele_two-ele_three);
			}
			break;
		default:
			break;
		}
	}

	
	
	/*计算加减法*/
	public void AddAndSubMethod(int num1,int num2,int choose){
		if(choose==1){
			problem.add(num1+"+"+num2+"=");
			answer.add(num1+num2);
		}
		else{
			problem.add(num1+"-"+num2+"=");
			answer.add(num1-num2);
		}
	}
	
	
	
	/*计算乘除*/
	public void MulAndDivMethod(int num1,int num2,int choose){
		if(choose==1){
			problem.add(num1+"×"+num2+"=");
			answer.add(num1*num2);
		}
		else{
			int ele_sum=num1*num2;
			problem.add(ele_sum+"÷"+num1+"=");
			answer.add(num2);
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
			if(isequ)
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
