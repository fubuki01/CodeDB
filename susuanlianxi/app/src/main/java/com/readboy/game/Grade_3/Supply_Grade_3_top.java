package com.readboy.game.Grade_3;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import static android.content.ContentValues.TAG;
//import android.renderscript.Program;

public class Supply_Grade_3_top implements Runnable{
		Handler handler_program;
		Object Alock;
		int count=0;
		int count_float=0;
		int type; //出题类型
		boolean is_float=false;
		private ArrayList<String> problem=new ArrayList<String>();
		private ArrayList<Integer> answer=new ArrayList<Integer>();
		private ArrayList<String> answer1=new ArrayList<String>();
		private ArrayList<String> problem1=new ArrayList<String>();
		boolean stopThread;
		
		
//		  final private int MULANDDIV=1;  //两、三位数乘一位数不连续进位的笔算乘法
//		  final private int CANDIVALL=2;	//两位数乘一位数不进位的笔算乘法
//		  final private int SEVENDIV=3;  //末尾带0的三位数乘一位数
//		  final private int DIVSPACE=4;		//中间带0 的三位数乘一位数
//
//
//		  final private int DIVWAY=5;		//同分母分数加法
//		  final private int MULDIV=6;		//同分母分数减法
//		  final private int  MULANDADD=7;		//一减去几分之几
//
//
//		  final private int NULANDSUB=8;		//长方形的周长
//
//
//		  final private int ADDANDSUB=9;		//两位数加两位数的连续进位加法
//
//
//		  final private int SMALLBRACKET=10;		//余数和除数的关系

	  final private int ADDANDTWO=1;		//两位数加两位数的连续进位加法
	  final private int SUBANDTHREE=2;		//三位数减三位数的连续退位减法
	  final private int THREEADD=3;       //三位数连续加

	  final private int  THADDTW=4;		//三位数加三位数
	  final private int  THSUBTW=5;		//三位数减三位数

	  final private int CANDIVALL=6;  	//两位数乘一位数不进位乘法
	  final private int NCANDIVALL=7;	//两位数乘一位数进位乘法

	  final private int MULANDDIV=8;      //三位数乘一位数
	  final private int THDIVMI=9;  	     //末尾带0 的三位数乘一位数
	  final private int DIVSPACE=10;		//中间带0 的三位数乘一位数

	  final private int DIVWAY=11;		//同分母分数加法
	  final private int MULDIV=12;		//同分母分数减法
		
		public Supply_Grade_3_top(Handler handler_program,Object Alock,int type) {
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
			int denominator=1;
			int choose=Math.random()>0.5?1:0;   //选择加减
			int choose_num=Math.random()>0.5?1:0;//选择数字
			int maxdivide;//最大公约数
			switch (type) {
			case ADDANDTWO://两位数加两位数的连续进位加法
				ele_one=(int)(11+Math.random()*89);
				int remainder=ele_one%10;
				while(remainder==0){
					ele_one=(int)(11+Math.random()*89);
					remainder=ele_one%10;
				}
				int div=ele_one/10;
				ele_two=10-remainder+(int)(Math.random()*(remainder-1))+(9-div+(int)(Math.random()*div))*10;
				AddAndSubMethod(ele_one,ele_two,1);
				break;

			case SUBANDTHREE://三位数减三位数的连续退位减法
				ele_one=100+(int)(Math.random()*900);
				ele_two=100+(int)(Math.random()*900);
				int yu1 = ele_one%10;
				int yu2 = ele_two%10;
				int ch1 = ele_one/10;
				int ch2 = ele_two/10;
				int wy1 = ch1%10;
				int wy2 = ch2%10;
				while ((ele_one<ele_two)||(yu1>=yu2)||(wy1>=wy2)){
					ele_one=100+(int)(Math.random()*900);
					ele_two=100+(int)(Math.random()*900);
					yu1 = ele_one%10;
					yu2 = ele_two%10;
					ch1 = ele_one/10;
					ch2 = ele_two/10;
					wy1 = ch1%10;
					wy2 = ch2%10;
				}
				AddAndSubMethod(ele_one,ele_two,0);
				break;

			case THSUBTW://三位数减三位数
				ele_one=(int)(10+Math.random()*89);
				ele_two=(int)(10+Math.random()*(ele_one-10));
				AddAndSubMethod(ele_one*10,ele_two*10,0);
				break;

				case THADDTW://三位数加三位数
				ele_one=(int)(10+Math.random()*89)*10;
				ele_two=(int)(10+Math.random()*89)*10;
				AddAndSubMethod(ele_one,ele_two,1);
				break;


				case CANDIVALL://两位数乘一位数不进位的乘法
				ele_one=10+(int)(Math.random()*90);
				//Log.e("66545555454555444455646", ele_one+"");
				int u = ele_one%10;
				ele_two=1+(int)(Math.random()*9);
				while (u*ele_two>=10){
					ele_one=10+(int)(Math.random()*90);
					u = ele_one%10;
				}
				MulAndDivMethod(ele_one,ele_two,1);
				break;

			case NCANDIVALL://两位数乘一位数进位乘法
				int w1=(int)(1+Math.random()*9);
				int w2=(int)(2+Math.random()*8);
				ele_one=10*w1+w2;
				//Log.e("0000000000000000000", ele_one+"");

				int m = (int)(Math.ceil(10.0/w2));
				ele_two=(int)(m+Math.random()*(int)(10-m));
				//Log.e("888888888888888", ele_two+"");
				MulAndDivMethod(ele_one,ele_two,1);
				break;

			case MULANDDIV:  //三位数乘一位数不连续进位乘法
				ele_one=(int)(100+Math.random()*899); //生成一个三位数
				ele_two=(int)(1+Math.random()*9); //生成一个一位数
				problem.add(ele_one+"×"+ele_two+"=");
				answer.add(ele_one*ele_two);
//				ele_one=(int)(10+Math.random()*89);
//				ele_two=(int)(100+Math.random()*899);
//				if(choose==1){
//					ele_three=(int)(1+Math.random()*((int)(10/(ele_one%10))-1));
//					MulAndDivMethod(ele_one,ele_three,1);
//				}
//				else{
//					ele_three=(int)(1+Math.random()*((int)(10/(ele_one%10))-1));
//					MulAndDivMethod(ele_two,ele_three,1);
//				}
				break;

			case THDIVMI://末尾带0 的三位数乘一位数
				ele_one=(int)(10+Math.random()*89)*10;
				ele_two=(int)(1+Math.random()*8);
				MulAndDivMethod(ele_one,ele_two,1);
				break;

			case DIVSPACE://中间带0 的三位数乘一位数
				ele_one=(int)(1+Math.random()*8)*100+(int)(1+Math.random()*8);
				ele_two=(int)(1+Math.random()*8);
				MulAndDivMethod(ele_one,ele_two,1);
				break;
			case DIVWAY://同分母分数加法
				is_float=true;
				denominator=(int)(10+Math.random()*89);
				ele_one=(int)(1+Math.random()*(denominator-1));
				ele_two=(int)(1+Math.random()*(denominator-1));
				ele_three=(int)(1+Math.random()*(denominator-1));
				maxdivide=greatestCommonDivisor(ele_one+ele_two+ele_three,denominator);
				problem1.add(ele_one+"/"+denominator+"+"+ele_two+"/"+denominator+"+"+ele_three+"/"+denominator+"=");
				answer1.add((ele_one+ele_two+ele_three)/maxdivide+"/"+denominator/maxdivide);
				break;

			case MULDIV://同分母分数减法
				is_float=true;
				denominator=(int)(10+Math.random()*89);
				ele_one=(int)(10+Math.random()*(denominator-1));
				ele_two=(int)(1+Math.random()*(ele_one-1));
				ele_three=(int)(1+Math.random()*(ele_one-ele_two-1));
				maxdivide=greatestCommonDivisor(ele_one-ele_two-ele_three,denominator);
				problem1.add(ele_one+"/"+denominator+"-"+ele_two+"/"+denominator+"-"+ele_three+"/"+denominator+"=");
				answer1.add((ele_one+ele_two+ele_three)/maxdivide+"/"+denominator/maxdivide);
				break;

				case THREEADD: //三位数连续进位相加
					int one=(int)(1+Math.random()*9); //个位（不能为0）
					int two=(int)(Math.random()*9); //十位
					int three=(int)(1+Math.random()*9); //百位(不能为0)
					ele_one=three*100+two*10+one;
					//保证每个位都能进位
					int first=(int)((10-one)+Math.random()*(one));
					int second=(int)((9-two)+Math.random()*(two));
					int thread=(int)((9-three)+Math.random()*(three));
					ele_two=thread*100+second*10+first;
					AddAndSubMethod(ele_one,ele_two,1);
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
				//int ele_sum=num1+num2;
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
		
		/*整数因式分解*/
		public int IntFactor(int num){
			int []result=new int[num];
			int factor_num=0;
			int choose_which=0;
			for(int i=1;i<=num;i++){
				if(num%i==0){
					result[factor_num]=i;
					factor_num++;
				}
			}
			choose_which=(int)(Math.random()*(factor_num-1));
			return result[choose_which];
		}
		
		
		
		/*最大公约数*/
		public int greatestCommonDivisor(int a,int b){
			 int c = a%b;
		        while(c!=0){
		                a = b;
		                b = c;
		                c = a % b;
		        }
		        return b;
		}
		
		
		public void run() {
			problem=new ArrayList<String>();
			answer=new ArrayList<Integer>();
			while(!stopThread){
				is_float=false;
				CreateSubject();
				Message message = new Message(); 
				Bundle bundle=new Bundle();
				if(!is_float){
					bundle.putString("problem",problem.get(count));
					bundle.putInt("answer", answer.get(count));
					bundle.putBoolean("is_float",false);
					message.setData(bundle);
					handler_program.sendMessage(message);
					count++;
				}
				else{
					bundle.putString("problem",problem1.get(count_float));
					bundle.putString("answer", answer1.get(count_float));
					bundle.putBoolean("is_float",true);
					message.setData(bundle);
					handler_program.sendMessage(message);
					count_float++;
				}
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

