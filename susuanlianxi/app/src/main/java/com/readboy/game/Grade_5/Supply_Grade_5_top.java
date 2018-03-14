package com.readboy.game.Grade_5;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import static android.content.ContentValues.TAG;
//import android.renderscript.Program;

public class Supply_Grade_5_top implements Runnable{
		Handler handler_program;
		Object Alock;
		int count=0;
		int count_float=0;
		int type; //出题类型
		int mycount=0;
		boolean is_float=false;  //判别是浮点数还是整数
		boolean is_keep=false;  //判别是浮点数还是整数

		private ArrayList<String> problem=new ArrayList<String>();
		private ArrayList<Integer> answer=new ArrayList<Integer>();
		private ArrayList<String> answer1=new ArrayList<String>();
		private ArrayList<String> problem1=new ArrayList<String>();
		boolean stopThread;

	final private int SMALLBRACKET=1;		//小数乘整数
	final private int UNKOWNNUM=2;		//小数乘小数

	final private int MULADD=3;		//小数乘加
	final private int MULSUB=4;		//小数乘减


	final private int TENADD=5; //小数除以整数
	final private int DECDIV=6; //小数除以小数
	final private int INTDIVDEC=7; //整数除以小数
	final private int INTDIVINT=8; //整数除以整数 商是小数

//	final private int DIVSPACE=11;		//形如ax+ab=c的方程的解法及其应用
//	final private int DIVWAY=12;		//形如ax+b=c的方程的解法及其应用
//	final private int MULDIV=13;		//形如ax=b的方程的解法
//	final private int  MULANDADD=14;		//形如ax±bx=c的方程的解法
//	final private int NULANDSUB=15;		//形如ax-b=c的方程的解法及其应用
//	final private int ADDANDSUB=16;		//形如x±a=b的方程的解法数的计算方法
//	//	final private int ADDTHENSUB=3;		//小数连乘
		
		public Supply_Grade_5_top(Handler handler_program,Object Alock,int type) {
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
			int ele_seven=-1;
			double tmp1;
			double tmp2;
			double tmp3;

			int ele_one=-1;
			int ele_two=-1;
			int ele_three=-1;
			int ele_four=-1;
			int ele_five=-1;
			int ele_six = -1;
			int denominator=1;
			String str;
			double temp;
			int length=-1;
			boolean is_string;
			int choose=Math.random()>0.5?1:0;   //选择加减
			int choose_num=Math.random()>0.5?1:0;//选择数字
			DecimalFormat df = new DecimalFormat( "00.00 ");  
			switch (type) {
			case SMALLBRACKET://小数乘整数的计算方法
				if(choose==1)
				{
					int a1,a2,b;
					String ans="";
					while(true) {
						is_float = true;
						a1 = (int) (0 + Math.random() * 9);
						double c = Math.random();
						if (c <= 0.33)
							a2 = (int) (100 + Math.random() * 900);
						else if (c <= 0.66)
							a2 = (int) (10 + Math.random() * 90);
						else
							a2 = (int) (1 + Math.random() * 9);
						while (a2 % 10 == 0)
							a2 = a2 / 10;
						int[] bb = {1, 10, 100};
						b = ((int) (1 + Math.random() * 9)) * bb[(int) (Math.random() * 3)];

						BigDecimal b1 = new BigDecimal(a1 + "." + a2);
						ans = Double.toString(b1.multiply(new BigDecimal(Integer.toString(b))).doubleValue());
						Log.i("myi", ans);
						String[] strtemp = ans.split("\\.");
						if (!strtemp[1].equals("0"))
							break;
					}
					problem1.add(a1+"."+a2+"×"+b+"=");
					answer1.add(ans);
					Log.i("myi", a1+"."+a2+"X"+b+"="+ans);
					break;
				}
				else
				{
					int b;
					String a="",ans = "";
					while(true) {
						is_float = true;
						int[] bb = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 20, 25, 30};
						b = bb[(int) (Math.random() * 18)];
						String[] aa = {"0.01", "0.02", "0.03", "0.04", "0.05", "0.06", "0.07", "0.08", "0.09", "0.1", "0.2"};
						a = aa[(int) (Math.random() * 11)];
						BigDecimal b1 = new BigDecimal(a);
						ans = Double.toString(b1.multiply(new BigDecimal(Integer.toString(b))).doubleValue());
						String[] strtemp = ans.split("\\.");
						if (!strtemp[1].equals("0"))
							break;
					}
					problem1.add(a+"×"+b+"=");
					answer1.add(ans);
					Log.i("myi", a+"X"+b+"="+ans);
					break;
				}

				
			case UNKOWNNUM: //小数乘小数的算理及计算方法
			{
				is_float=true;
				String a,b,ans;
				while(true)
				{
					int aa = (int)(1+Math.random()*9);
					a = 0+"."+(Math.random()>0.5?"":"0")+aa;

					int b1 = (int)(0+Math.random()*9);
					int b2 = Math.random()>0.5?(int)(10+Math.random()*90):(int)(1+Math.random()*9);
					while(b2%10==0)
						b2=b2/10;
					b=b1+"."+b2;

					BigDecimal bd1 = new BigDecimal(a);
					ans = Double.toString(bd1.multiply(new BigDecimal(b)).doubleValue());
					String[] strtemp = ans.split("\\.");
					if(!strtemp[1].equals("0"))
						break;
				}


				problem1.add(a+"×"+b+"=");
				answer1.add(ans);
				Log.e("myi", a+"X"+b+"="+ans);
				break;
			}

			case MULADD: //乘加
			{
				is_float = true;
//				int isaddmethod=1;  //表示出乘加法
//				makeQuestion(isaddmethod);
				int b;
				String a,cc,ans;
				while(true)
				{
					int a1 = (int) (1 + Math.random() * 19);
					int a2;
					double c = Math.random();
					if (c >= 0.5)
						a2 = (int) (10 + Math.random() * 90);
					else
						a2 = (int) (1 + Math.random() * 9);
					while (a2 % 10 == 0)
						a2 = a2 / 10;
					a = a1 + "." + a2;

					int[] bb = {1, 10, 100};
					b = ((int) (1 + Math.random() * 9)) * bb[(int) (Math.random() * 3)];

					int p = (int)(1+Math.random()*99);
					while (p%10==0){
						p = (int)(1+Math.random()*99);
					}
					cc = Integer.toString((int) Math.random() * 100) + "." + Integer.toString(p);

					BigDecimal bd1 = new BigDecimal(a);
					ans = Double.toString(bd1.multiply(new BigDecimal(b)).doubleValue());
					bd1 = new BigDecimal(ans);
					ans = Double.toString(bd1.add(new BigDecimal(cc)).doubleValue());
					Log.e("8888888888888", ans+"");
					String[] strtemp = ans.split("\\.");
					if (!strtemp[1].equals("0"))
						break;
				}

				problem1.add(a+"×"+b+"+"+cc+"=");
				answer1.add(ans);
				Log.e("6666666666", a+"×"+b+"+"+cc+"="+ans+"");
				break;
			}

			case MULSUB: //乘减
			{
				is_float=true;
				int a1=1,a2=1,b=1;
				String a="1";
				String c="100000";
				String ans;
				while(true)
				{
					while(Double.valueOf(c)>Double.valueOf(a)*b)
					{
						a1 = (int)(1+Math.random()*19);
						double cc = Math.random();
						if(cc>=0.5)
							a2 = (int)(10+Math.random()*90);
						else
							a2 = (int)(1+Math.random()*9);
						while(a2%10==0)
							a2=a2/10;
						a=a1+"."+a2;

						int[] bb = {1,10,100};
						b = ((int)(1+Math.random()*9))*bb[(int)(Math.random()*3)];

						int g = (int)(1+Math.random()*99);
						while (g%10==0){
							g = (int)(1+Math.random()*99);
						}
							c=Integer.toString((int)(Math.random()*10))+"."+Integer.toString(g);
					}

					BigDecimal bd1 = new BigDecimal(a);
					ans = Double.toString(bd1.multiply(new BigDecimal(b)).doubleValue());
					bd1 = new BigDecimal(ans);
					ans = Double.toString(bd1.subtract(new BigDecimal(c)).doubleValue());
//					Log.e("8888888888888", ans+"");
					String[] strtemp = ans.split("\\.");
					if(!strtemp[1].equals("0"))
						break;
				}
				problem1.add(a+"×"+b+"-"+c+"=");
				answer1.add(ans);
//				Log.e("6666666666", a+"×"+b+"-"+c+"="+ans+"");
				break;
			}

			case TENADD:  		//小数除以整数
			{
				is_keep = true;
				is_float=true;
				int a1,a2,b;
				String a;
				String ans;
				while(true)
				{
					a1 = (int)(1+Math.random()*50);
					double cc = Math.random();
					if(cc<=0.33)
						a2 = (int)(100+Math.random()*900);
					else if(cc<=0.66)
						a2 = (int)(10+Math.random()*90);
					else
						a2 = (int)(1+Math.random()*9);
					while(a2%10==0)
						a2=a2/10;
					a=a1+"."+a2;

					int[] bb = {1,10,100};
					b = ((int)(1+Math.random()*9))*bb[1+(int)(Math.random()*2)];

					BigDecimal bd1 = new BigDecimal(a);
					ans = Double.toString(bd1.divide(new BigDecimal(b),5,BigDecimal.ROUND_HALF_UP).doubleValue());
					String[] strtemp = ans.split("\\.");

					if(!strtemp[1].equals("0")&&strtemp[1].length()<=3)
						break;
				}

				problem1.add(a+"÷"+b+"=");
				Log.e("原始答案：", ans);
				answer1.add(ans);
				break;
			}

			case DECDIV: //小数除以小数
			{
				is_keep = true;
				is_float=true;
				int a1,a2,b1,b2;
				String a,b;
				String ans;
				while(true)
				{
					a1 = (int)(Math.random()*10);
					double cc = Math.random();
					if(cc<=0.33)
						a2 = (int)(100+Math.random()*900);
					else if(cc<=0.66)
						a2 = (int)(10+Math.random()*90);
					else
						a2 = (int)(1+Math.random()*9);
					while(a2%10==0)
						a2=a2/10;
					a=a1+"."+a2;

					b1 = (int)(Math.random()*10);
					cc = Math.random();
					if(cc<=0.33)
						b2 = (int)(100+Math.random()*900);
					else if(cc<=0.66)
						b2 = (int)(10+Math.random()*90);
					else
						b2 = (int)(1+Math.random()*9);
					while(b2%10==0)
						b2=b2/10;
					b=b1+"."+b2;

					BigDecimal bd1 = new BigDecimal(a);
					ans = Double.toString(bd1.divide(new BigDecimal(b),5,BigDecimal.ROUND_HALF_UP).doubleValue());
					String[] strtemp = ans.split("\\.");
					Log.e("11111111111", ans);

					if(!strtemp[1].equals("0")&&strtemp[1].length()<=3){
						problem1.add(a+"÷"+b+"=");
						answer1.add(ans);
						Log.e("原始答案：", ans);
						break;
					}
					else {
						problem1.add(a+"÷"+b+"=");
						answer1.add(cut(ans));
						Log.e("原始答案：", ans);
						Log.e("四舍五入答案：", cut(ans));
						break;
					}
				}

//				problem1.add(a+"÷"+b+"=");
//				answer1.add(ans);
				break;
			}

			case INTDIVDEC: //整数除以小数
			{
				is_keep = true;
				is_float=true;
				int b1,b2,a;
				String b;
				String ans;
				while(true)
				{
					b1 = (int)(Math.random()*10);
					double cc = Math.random();
					if(cc<=0.5)
						b2 = (int)(10+Math.random()*90);
					else
						b2 = (int)(1+Math.random()*9);
					while(b2%10==0)
						b2=b2/10;
					b=b1+"."+b2;

					a = (int)(1+Math.random()*100);

					BigDecimal bd1 = new BigDecimal(a);
					ans = Double.toString(bd1.divide(new BigDecimal(b),5,BigDecimal.ROUND_HALF_UP).doubleValue());
					Log.e("5555555555555555", ans+"");
					String[] strtemp = ans.split("\\.");
					if (strtemp[1].length()>2){
						if ((!strtemp[1].equals("0"))&&!((strtemp[1].indexOf(2)>4)&&(strtemp[1].indexOf(1)==9))){
							ans = Double.toString(bd1.divide(new BigDecimal(b),2,BigDecimal.ROUND_HALF_UP).doubleValue());
							Log.e("66666666666666", ans+"");
							break;
						}
					}else {
						if(!strtemp[1].equals("0"))
						{
							break;
						}
					}
				}
				Log.e("zxzxzx",a+"÷"+b+"="+ans );
				problem1.add(a+"÷"+b+"=");
				answer1.add(ans);
				Log.e("原始答案：", ans);
				break;
			}

			case INTDIVINT://整数除以整数 商是小数
			{
				is_keep = true;
				is_float=true;
				int b,a;
				String ans;

				while(true)
				{
					if(mycount>5)
					{
						a = (int)(1+Math.random()*100);
						b = (int)(1+Math.random()*100);
					}
					else
					{
						a = (int)(1+Math.random()*1000);
						b = (int)(1+Math.random()*1000);
					}
					if(a==b)
						continue;
					BigDecimal bd1 = new BigDecimal(a);
					ans = Double.toString(bd1.divide(new BigDecimal(b),4,BigDecimal.ROUND_HALF_UP).doubleValue());
					String[] strtemp = ans.split("\\.");
					Log.e("6666666666666：", ans);
					if(Double.valueOf(ans)>=10)
						continue;
					if(strtemp[1].equals("0"))
						continue;
					if(strtemp[1].length()<=2)
					{
						if(a>=100||b>=100){
							mycount++;
						}
						Log.e("7777777777777：", ans+"   "+strtemp[1]);
						break;
					}
				}
				Log.e("yyyyyyyyyyyy",a+"÷"+b+"="+ans );
				problem1.add(a+"÷"+b+"=");
				answer1.add(ans);
				Log.e("原始答案：", ans);
				break;
			}


			default:
				break;
			}
		}

	//乘加和乘减出题的方法
	private void makeQuestion(int panduanmethod) {
		double fisrtnumber;
		int secondnumber;
		double threenumber;
		double firstpoint;  //小数位的数
		fisrtnumber = (int)(1+Math.random()*20); //1-20的数
		double cc = Math.random();
		if(cc<=0.5){   //则A为一位小数
			firstpoint=(int)(1+Math.random()*9);
			firstpoint=(double)firstpoint/10;      //得到小数部分
		}
		else{          //则A为两位小数
			firstpoint=(int)(0+Math.random()*9);   //第一位小数
			firstpoint=firstpoint*10+(int)(1+Math.random()*9);  //二位小数
			firstpoint=(double)firstpoint/100;
		}
		fisrtnumber=fisrtnumber+firstpoint;   //生成第一个数(因为是double可能会有多位小数导致不准确，所以进行只有两位数处理)
		if(fisrtnumber+"".length()>5){  //超过了二位小数的情况，由于double计算不准确的原因
			BigDecimal bg = new BigDecimal(fisrtnumber).setScale(2, RoundingMode.UP);
			fisrtnumber=bg.doubleValue();  //将大数转为double
		}
		int[] bb = {1,10,100};
		secondnumber = ((int)(1+Math.random()*9))*bb[(int)(Math.random()*3)]; //生成第二个数

		threenumber=(int)(Math.random()*9);  //0-9的数
		double weishu=Math.random();
		if(weishu<=0.5){                    //出一位小数
			firstpoint=(int)(1+Math.random()*9);
			firstpoint=(double)firstpoint/10;   //得到小数部分
		}
		else{                              //出两位小数
			firstpoint=(int)(0+Math.random()*9);   //第一位小数
			firstpoint=firstpoint*10+(int)(1+Math.random()*9);  //二位小数
			firstpoint=(double)firstpoint/100;
		}
		threenumber=threenumber+firstpoint;   //生成第三位数
		if(threenumber+"".length()>5){      //超过了二位小数的情况，由于double计算不准确的原因
			BigDecimal bg = new BigDecimal(threenumber).setScale(2, RoundingMode.UP);
			threenumber=bg.doubleValue();  //将大数转为double
			Log.e("bg",bg+"");
			Log.e("bg2222",threenumber+"");
		}
		//判断结果是否是整数（整数不符合）
		String lateresult=(fisrtnumber*secondnumber-threenumber)+"";
		String[] every=lateresult.split("\\.");
		Log.e("every",every.length+"");
		if(every[1].equals("0")||every[1].equals("00")){   //如果是整数，那么让第三个数+0.1,这样肯定不会是整数了
			threenumber=threenumber+0.1;
		}
		if(panduanmethod==0) {  //表示乘减
			BigDecimal b1 = new BigDecimal(Double.toString(fisrtnumber));
			BigDecimal b2 = new BigDecimal(Integer.toString(secondnumber));
			lateresult=b1.multiply(b2).subtract(new BigDecimal(Double.toString(threenumber)))+"";
			char thefinally=lateresult.charAt(lateresult.length()-1);
			Log.e("thefinally",thefinally+"");
			if(thefinally=='0'){
				lateresult=lateresult.substring(0,lateresult.length()-2);
			}
			problem1.add(fisrtnumber + "×" + secondnumber + "-" + threenumber + "=");
			answer1.add(lateresult);
		}
		else if(panduanmethod==1){   //表示乘加
			BigDecimal b1 = new BigDecimal(Double.toString(fisrtnumber));
			BigDecimal b2 = new BigDecimal(Integer.toString(secondnumber));
			lateresult=b1.multiply(b2).subtract(new BigDecimal(Double.toString(threenumber)))+"";
			int thefinally=lateresult.charAt(lateresult.length()-1);
			if(thefinally==0){
				lateresult=lateresult.substring(0,lateresult.length()-2);
			}
			problem1.add(fisrtnumber + "×" + secondnumber + "+" + threenumber + "=");
			answer1.add(lateresult);
		}
		Log.e("answer", lateresult);
	}


	/*计算加减法*/
		public void AddAndSubMethod(int num1,int num2,int choose){
			if(choose==1){
				problem.add(num1+"+"+num2+"=");
				answer.add(num1+num2);
			}
			else{
				int ele_sum=num1+num2;
				problem.add(ele_sum+"-"+num1+"=");
				answer.add(num2);
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
		/*具体题目添加*/
		public boolean addStrElement(double d,double e,boolean mul_or_div){
			String str=null;
			if(mul_or_div==true){
				str = String.valueOf((d*1.0/(e*1.0)));
			}
			else{
				str = String.valueOf((d*1.0*(e*1.0)));
			}
			int index=str.indexOf(".");
			if(d%e!=0){
				if(index==1 && str.length()==3)
					answer1.add(str.substring(0, 3));
				else if(index==1 && str.length()>=4){
					answer1.add(str.substring(0, 4));
				}
				else if(index ==2 && str.length()==4)
					answer1.add(str.substring(0, 4));
				else if(index ==2 && str.length()>=5)
					answer1.add(str.substring(0, 5));
				else if(index==3 && str.length()==5)
					answer1.add(str.substring(0,5));
				else if(index==3 && str.length()>=6)
					answer1.add(str.substring(0,6));
				else if(index==4 && str.length()==6)
					answer1.add(str.substring(0,6));
				else if(index==4 && str.length()>=7)
					answer1.add(str.substring(0,7));
				return true;
			}
			else{
				answer.add((int)d/(int)e);
				is_float=false;
				return false;
			}
		}


	public boolean addStrElement1(double d,double e,double f,boolean add_or_sub){
		String str=null;

		if(add_or_sub==true){
			double we2 = (d*1.0*(e*1.0)+(f*1.0));
			BigDecimal b1 = new BigDecimal(we2);
			we2 =(double) b1.setScale(2,
					BigDecimal.ROUND_HALF_UP).doubleValue();
			str = String.valueOf(we2);
		}
		else{
			double we2 = (d*1.0*(e*1.0)-(f*1.0));
			BigDecimal b1 = new BigDecimal(we2);
			we2 =(double) b1.setScale(2,
					BigDecimal.ROUND_HALF_UP).doubleValue();
			str = String.valueOf(we2);
		}
		int index=str.indexOf(".");
		if(d%e!=0){
			if(index==1 && str.length()==3)
				answer1.add(str.substring(0, 3));
			else if(index==1 && str.length()>=4){
				answer1.add(str.substring(0, 4));
			}
			else if(index ==2 && str.length()==4)
				answer1.add(str.substring(0, 4));
			else if(index ==2 && str.length()>=5)
				answer1.add(str.substring(0, 5));
			else if(index==3 && str.length()==5)
				answer1.add(str.substring(0,5));
			else if(index==3 && str.length()>=6)
				answer1.add(str.substring(0,6));
			else if(index==4 && str.length()==6)
				answer1.add(str.substring(0,6));
			else if(index==4 && str.length()>=7)
				answer1.add(str.substring(0,7));
			return true;
		}
		else{
			answer.add((int)d/(int)e);
			is_float=false;
			return false;
		}
	}

	public void run() {
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
				if (is_keep){
					bundle.putBoolean("is_keep",true);
				}else {
					bundle.putBoolean("is_keep",false);
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
	private String cut(String ans) //四舍五入
	{
		BigDecimal b = new BigDecimal(ans);
		return b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue()+"";
	}

}