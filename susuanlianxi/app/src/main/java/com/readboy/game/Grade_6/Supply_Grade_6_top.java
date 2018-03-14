package com.readboy.game.Grade_6;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.readboy.game.FAO;

import static android.content.ContentValues.TAG;
//import android.renderscript.Program;

public class Supply_Grade_6_top implements Runnable{
	Handler handler_program;
	Object Alock;
	int count=0;
	int count_float=0;
	int type; //出题类型
	boolean is_float=false;  //判别是浮点数还是整数
	private ArrayList<String> problem=new ArrayList<String>();
	private ArrayList<Integer> answer=new ArrayList<Integer>();
	private ArrayList<String> answer1=new ArrayList<String>();
	private ArrayList<String> problem1=new ArrayList<String>();
	boolean stopThread;


	final private int SEVENDIV=1;  //分数乘整数的计算方法
	final private int MULANDDIV=2;  //分数乘分数的计算方法

	final private int DIVWAY=3;		//分数除以整数的计算方法
	final private int  MULANDADD=4;		//一个数除以分数的计算方法

	final private int SIZEASDM=25;     //分数四则混合运算

	public Supply_Grade_6_top(Handler handler_program,Object Alock,int type) {
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
		int ele_four=-1;
		int ele_five=-1;
		int denominator=1;
		int divmax=0;
		int choose=Math.random()>0.5?1:0;   //选择加减
		int choose_num=Math.random()>0.5?1:0;//选择数字
		switch (type) {
			case SEVENDIV://分数乘整数的计算方法
				is_float = true;
				while (true) {
					ele_one = (int) (1 + Math.random() * 49);
					if (ele_one >= 30) {   //设置分子小于30且小于分母
						ele_two = (int) (1 + Math.random() * 30);
					} else {
						ele_two = (int) (1 + Math.random() * ele_one);
					}
					//出乘数
					double chengshu = Math.random();
					if (0 <= chengshu && chengshu <= 0.33) { //出整十数
						ele_three = (int) (1 + Math.random() * 9) * 10;
					} else if (0.33 < chengshu && chengshu <= 0.66) {  //出个位数
						ele_three = (int) (1 + Math.random() * 9);
					} else {            //出约分的数
						int gongyueshu = computegongyueshu(ele_one);
						ele_three = gongyueshu;
					}
					if ((ele_two * ele_three) % ele_one != 0) {  //表示不是整数的时候
						break;
					}
				}
				divmax = greatestCommonDivisor(ele_two * ele_three, ele_one);//求最大公约数
				problem1.add(ele_two + "/" + ele_one + "×" + ele_three + "=");
				answer1.add((ele_two * ele_three) / divmax + "/" + ele_one / divmax);
				break;

			case MULANDDIV://分数乘分数的计算方法
				is_float = true;
				while (true) {
					ele_one = (int) (1 + Math.random() * 20);
					ele_two = (int) (1 + Math.random() * ele_one);
					if (ele_one >= 10) { //则后面一个分数不能为两位数
						ele_four = (int) (2 + Math.random() * 9);
						ele_three = (int) (1 + Math.random() * (ele_four - 1));
					} else {        //则后面一个分数可以为两位数也可以一位数
						ele_four = (int) (1 + Math.random() * 20);
						ele_three = (int) (1 + Math.random() * ele_four);
					}
					if ((ele_two * ele_three) % (ele_one * ele_four) != 0) {  //表示不是整数的结果
						break;
					}
				}
				divmax = greatestCommonDivisor(ele_two * ele_three, ele_one * ele_four);
				problem1.add(ele_two + "/" + ele_one + "×" + ele_three + "/" + ele_four + "=");
				answer1.add((ele_two * ele_three) / divmax + "/" + ele_one * ele_four / divmax);
				//problem.add(ele_two+"/"+ele_one+"×"+ele_three+"/"+ele_four+"=");
				//answer2.add((float)(ele_two/ele_one+ele_three/ele_four));
				break;

			case DIVWAY://分数除以整数的计算方法
				is_float = true;
				while(true){
				ele_one = (int) (1 + Math.random() * 20);
				ele_two = (int) (1 + Math.random() * 30);
				//出除数
				double chushu = Math.random();
				if (0 <= chushu && chushu <= 0.33) { //出整十数
					ele_three = (int) (1 + Math.random() * 9) * 10;
				} else if (0.33 < chushu && chushu <= 0.66) {  //出个位数
					ele_three = (int) (1 + Math.random() * 9);
				} else {            //出约分的数
					int gongyueshu = computegongyueshu(ele_two);
					ele_three = gongyueshu;
				}
					if(ele_two%(ele_one*ele_three)!=0){  //表示不是整数的结果
						break;
					}
		}
				divmax=greatestCommonDivisor(ele_two,ele_one*ele_three);
				problem1.add(ele_two+"/"+ele_one+"÷"+ele_three+"=");
				answer1.add((ele_two)/divmax+"/"+ele_one*ele_three/divmax);
				//problem.add(ele_two+"/"+ele_one+"÷"+ele_three+"=");
				//answer2.add((float)(ele_two/ele_one/ele_three));
				break;

			case MULANDADD://一个数除以分数的计算方法
				is_float=true;
				while(true) {
					ele_one = (int) (1 + Math.random() * 20);
					ele_two = (int) (1 + Math.random() * 30);
					//出被除数
					double beichushu = Math.random();
					if (0 <= beichushu && beichushu <= 0.33) { //出整十数
						ele_three = (int) (1 + Math.random() * 9) * 10;
					} else if (0.33 < beichushu && beichushu <= 0.66) {  //出个位数
						ele_three = (int) (1 + Math.random() * 9);
					} else {            //出约分的数
						int gongyueshu = computegongyueshu(ele_two);
						ele_three = gongyueshu;
					}
					if((ele_one*ele_three)%ele_two!=0){  //表示不是整数的时候
						break;
					}
				}
				divmax=greatestCommonDivisor(ele_one*ele_three,ele_two); //求最大公约数
				problem1.add(ele_three+"÷"+ele_two+"/"+ele_one+"=");
				answer1.add((ele_one*ele_three)/divmax+"/"+ele_two/divmax);
				//problem.add(ele_three+"÷"+ele_two+"/"+ele_one+"=");
				//answer2.add((float)(ele_three/(ele_two/ele_one)));
				break;

			case SIZEASDM://分数的四则运算
				is_float=true;
				FAO ko = new FAO();
				ko.faogo();
//				ele_one=(int)(11+Math.random()*38);
//				ele_two=(int)(1+Math.random()*49);
//				ele_three=(int)(1+Math.random()*48);
//				ele_four=(int)(11+Math.random()*38);
//				divmax=greatestCommonDivisor(ele_two*ele_three,ele_one);
				problem1.add(ko.pro+"=");
				Log.e("00000000000000000", ko.pro+"="+"");
				answer1.add(ko.ans1);
				Log.e("00000000000000000", ko.ans1+"");
				break;


			default:
				break;
		}
	}

	//求该数的公约数
	private int computegongyueshu(int ele_one) {
		List<Integer> shuzu=new ArrayList<Integer>();  //装该数的公约数
		for(int i=2;i<Math.sqrt(ele_one);i++){
			if(ele_one%i==0){
				shuzu.add(i);
			}
		}
		//随机出一个公约数
		int gongyushu=0;
		if(shuzu.size()==0) { //表示没有公约数
			 gongyushu=(int)(1+Math.random()*9);  //就随机出一个一位数
		}
		else{
			gongyushu = shuzu.get((int) (Math.random() * (shuzu.size())));
		}
		return gongyushu;
	}


	/*计算加减法*/
	public void AddAndSubMethod(int num1,int num2,int choose){
		if(choose==1){
			problem.add(num1+"+"+num2);
			answer.add(num1+num2);
		}
		else{
			int ele_sum=num1+num2;
			problem.add(ele_sum+"-"+num1);
			answer.add(num2);
		}
	}


//		public void AddAndSubMethodFloat(float num1,float num2,int choose){
//			if(choose==1){
//				problem.add(num1+"+"+num2);
//				answer2.add(num1+num2);
//			}
//			else{
//				float ele_sum=num1+num2;
//				problem.add(ele_sum+"-"+num1);
//				answer2.add(num2);
//			}
//		}

	/*计算乘除*/
	public void MulAndDivMethod(int num1,int num2,int choose){
		if(choose==1){
			problem.add(num1+"×"+num2);
			answer.add(num1*num2);
		}
		else{
			int ele_sum=num1*num2;
			problem.add(ele_sum+"÷"+num1);
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

