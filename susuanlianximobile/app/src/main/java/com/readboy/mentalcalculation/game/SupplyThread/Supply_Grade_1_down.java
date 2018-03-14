package com.readboy.mentalcalculation.game.SupplyThread;

import java.util.ArrayList;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.readboy.mentalcalculation.game.MyActivity.ActivityCollector;

public  class Supply_Grade_1_down implements Runnable  {

	Handler handler_program;
	Object Alock;
	int count=0;
	int type; //出题类型
	ArrayList<String> problem;
	ArrayList<Integer> answer;
	boolean stopThread;

	final private int TENSUBNINE=1;  //十几减9的退位减法
	final private int TENSUBWHAT=2;	//十几减几的退位减法
	final private int TWELVEMAX=3;   //20以内的加减混合
	final private int TENADDTEN=4;		//整十数加整十数的计算方法
	final private int TENSUBTEN=5;		//整十数减整十数的计算方法
	final private int NOTWOADDONE=6;  //两位数加一位数（不进位）的计算方法
	final private int TWOADDONE=7;		//两位数加一位数（进位）的计算方法
	final private int NOTWOSUBONE=8;		//两位数减一位数（不退位）的计算方法
	final private int  TWOSUBONE=9;		//两位数减一位数（退位）的计算方法
	final private int TWOADDTEN=10;		//两位数加整十数的计算方法
	final private int NOTWOADDTWONUMBER=11;   //两位数加两位数不进位
	final private int TWOADDTWONUMBER=12;   //两位数减两位数不退位

	final private int TWOSUBTEN=13;		//两位数减整十数的计算方法
	final private int TWOSUBTHIS=14;	//与整十数加一位数对应的减法计算方法
	final private int TENADDONE=15;		//整十数加一位数的计算方法

	//////////////////////

	public Supply_Grade_1_down(Handler handler_program,Object Alock,int type) {
		// TODO Auto-generated constructor stub
		this.handler_program=handler_program;
		this.Alock=Alock;
		this.type=type;
		stopThread=false;
	}

	/*产生题目*/

	public  void CreateSubject(){
		int choose_second=Math.random()>0.5?1:0;  //选择第二个符号
		int ele_one=-1;
		int ele_two=-1;
		int ele_three=-1;    //第三个数（用在混合运算）
		int choose=Math.random()>0.5?1:0;   //选择加减
		int choose_num=Math.random()>0.5?1:0;//选择数字
		switch (type) {
			case TENSUBNINE://十几减9的退位减法
				ele_one=11+(int)(Math.random()*8);
				ele_two=9;
				AddAndSubMethod(ele_one,ele_two,0);
				break;
			case TENSUBWHAT://十十几减几的退位减法
//				ele_one=(int)(10+Math.random()*9);
//				int j = ele_one%10;
//				while (ele_two<=j){
//					ele_two=(int)(Math.random()*9);
//				}
//				AddAndSubMethod(ele_one,ele_two,0);
//				break;
			{
				int ans = 10;
				while(ans>=10)
				{
					ele_one = (int)(10+Math.random()*9);
					ele_two = (int)(1+Math.random()*9);
					ans = ele_one-ele_two;
				}
				AddAndSubMethod(ele_one,ele_two,0);
				break;
			}
			case NOTWOADDONE://两位数加一位数（不进位）的计算方法
				ele_one=(int)(10+Math.random()*89);
				int judge_num1=ele_one%10;
				ele_two=(int)(Math.random()*(9-judge_num1));
				AddAndSubMethod(ele_one,ele_two,1);
				break;
			case TWOADDONE://两位数加一位数（进位）的计算方法
				ele_one=(int)(10+Math.random()*89);
				int judge_num2=ele_one%10;
				while(judge_num2==0){
					ele_one=(int)(10+Math.random()*89);
					judge_num2=ele_one%10;
				}
				ele_two=10-judge_num2+(int)(Math.random()*(judge_num2-1));
				AddAndSubMethod(ele_one,ele_two,1);
				break;
			case TWOADDTEN://两位数加整十数的计算方法
				ele_two=(int)(Math.random()*8)*10+10;
				ele_one=(int)(10+Math.random()*(90-ele_two));
				AddAndSubMethod(ele_one,ele_two,1);
				break;

			case NOTWOSUBONE://两位数减一位数（不退位）的计算方法
				ele_one=(int)(10+Math.random()*89);
				int judge_num3=ele_one%10;
				ele_two=(int)(Math.random()*(judge_num3));
				AddAndSubMethod(ele_one,ele_two,0);
				break;


			case TWOSUBONE://两位数减一位数（退位）的计算方法
				ele_one=(int)(10+Math.random()*89);
				int judge_num4=ele_one%10;
				while(judge_num4==9){
					ele_one=(int)(10+Math.random()*89);
					judge_num4=ele_one%10;
				}
				ele_two=judge_num4+1+(int)(Math.random()*(8-judge_num4));
				AddAndSubMethod(ele_one,ele_two,0);
				break;


			case TWOSUBTEN://两位数减整十数的计算方法
				ele_one=(int)(10+Math.random()*89);
				ele_two=(int)(Math.random()*(ele_one/10-1))*10+10;
				AddAndSubMethod(ele_one,ele_two,0);
				break;

			case TENADDTEN://整十数加整十数的计算方法
				ele_one=(int)(Math.random()*8)*10+10;
				ele_two=(int)(Math.random()*(9-ele_one/10))*10+10;
				AddAndSubMethod(ele_one,ele_two,1);
				break;

			case TENSUBTEN://整十数减整十数的计算方法
				ele_one=(int)(Math.random()*8)*10+10;
				ele_two=(int)(Math.random()*(ele_one/10-1))*10+10;
				AddAndSubMethod(ele_one,ele_two,0);
				break;


			case TWOSUBTHIS: //与整十数加一位数对应的减法计算方法
				ele_two=(int)(Math.random()*9);
				ele_one=(int)(Math.random()*8)*10+10+ele_two;
				AddAndSubMethod(ele_one,ele_two,0);
				break;


			case TENADDONE: //整十数加一位数的计算方法
				ele_one=(int)(Math.random()*8)*10+10;
				ele_two=(int)(Math.random()*9);
				AddAndSubMethod(ele_one,ele_two,1);
				break;

			//////////////
			case TWELVEMAX: //二十以内的混合运算
				boolean isfuhe=false;
				ele_one=(int) (Math.random()*21);
				while(isfuhe==false){
					if(choose==1){ //表示的是加法
						ele_two=(int) (Math.random()*21);
						int result=ele_one+ele_two;
						if((ele_one+ele_two)<20){
							ele_three=(int) (Math.random()*21);
							if(choose_second==1){  //第二个还是加法
								if((ele_one+ele_three+ele_two)<20){   //符合条件
									answer.add(ele_one+ele_three+ele_two);
									isfuhe=true;
								}
							}
							else { //第二个是减法
								if((ele_one+ele_two-ele_three)>0){
									answer.add((ele_one+ele_two-ele_three));
									isfuhe=true;
								}
							}
						}
					}
					else {  //表示的是减法
						ele_two=(int) (Math.random()*21);
						if((ele_two-ele_one)>0){ //不符合情况
						}
						else{
							ele_three=(int) (Math.random()*21);
							if(choose_second==1){ //表示是加法
								if((ele_one-ele_two+ele_three)<20){  //符合情况
									answer.add((ele_one-ele_two)+ele_three);
									isfuhe=true;
								}
							}
							else { //表示的是减法
								if((ele_one-ele_two-ele_three)>0){  //符合情况
									answer.add(ele_one-ele_two-ele_three);
									isfuhe=true;
								}
							}
						}
					}
				}
				AddAndSubMaxThreeMember(ele_one, ele_two, ele_three, choose, choose_second);
				//显示运算的字符
				break;

			case  NOTWOADDTWONUMBER:   //两位数加两位数不进位加法
				ele_one=(int)(10+Math.random()*89);  //生成两位数
				int firstgewei=ele_one%10;      //个位
				int firstshiwei=ele_one/10;     //十位
                int twogewei=(int)(Math.random()*(10-firstgewei));  //防止个位进位
                int twoshiwei=(int)(1+Math.random()*(9-firstshiwei));  //防止十位进位
				ele_two=twoshiwei*10+twogewei;
				AddAndSubMethod(ele_one,ele_two,1);
				break;

			case  TWOADDTWONUMBER:   //两位数减两位数不退位减法
				ele_one=(int)(10+Math.random()*89);
				int subfirst=ele_one%10;
				boolean isfuhesub=false;
				while(isfuhesub==false){
					ele_two=(int)(10+Math.random()*89);
					int subsecond=ele_two%10;
					if((subfirst-subsecond)>=0&&(ele_one-ele_two)>=0){
						AddAndSubMethod(ele_one,ele_two,0);
						isfuhesub=true;
					}
				}
				break;
			default:
				break;
		}
	}

	//三个数的混合加减法(二十以内)
	private void AddAndSubMaxThreeMember(int ele_one, int ele_two,
										 int ele_three, int choose, int choose_second) {
		//显示运算式子
		if(choose==0&&choose_second==0){
			problem.add(ele_one+"-"+ele_two+"-"+ele_three+"=");//显示进行的运算字符串
		}
		else if(choose==1&choose_second==1){
			problem.add(ele_one+"+"+ele_two+"+"+ele_three+"=");//显示进行的运算字符串
		}
		else if(choose==0&&choose_second==1){
			problem.add(ele_one+"-"+ele_two+"+"+ele_three+"=");//显示进行的运算字符串
		}
		else if(choose==1&&choose_second==0){
			problem.add(ele_one+"+"+ele_two+"-"+ele_three+"=");//显示进行的运算字符串
		}
	}

	public void setTag(boolean stopThread){
		this.stopThread=stopThread;
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

