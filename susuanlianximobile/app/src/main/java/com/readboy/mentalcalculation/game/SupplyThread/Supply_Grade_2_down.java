package com.readboy.mentalcalculation.game.SupplyThread;

import java.util.ArrayList;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.readboy.mentalcalculation.game.MyActivity.ActivityCollector;


public class Supply_Grade_2_down implements Runnable  {
	Handler handler_program;
	Object Alock;
	int count=0;
//	boolean is_yu = false;
	int type; //出题类型
	ArrayList<String> problem;
	ArrayList<Integer> answer;
	boolean stopThread;
	

	final private int TWODIV=1;		//用2~6的乘法口诀求商

	final private int SIXDIV=2;    	//用7~8的乘法口诀求商
	final private int NINDIV=3;    	//用9的乘法口诀求商
	final private int MULANDDIV=4;  	//乘除混合运算

	final private int MOD=5;  //有余数的除法
	final private int MIDADD=6;				//两位数加两位数
	final private int MIDDIV=7;			//两位数减两位数
	final private int BIGSUB=8;				//三位数减两位数
	final private int BIGADD=9;			//三位数加两位数


	public Supply_Grade_2_down(Handler handler_program,Object Alock,int type) {
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
		switch (type) {
			case MULANDDIV://乘除混合运算
				if(choose==1)
				{
					float a;
					while(true)
					{
						ele_one=(int)(1+Math.random()*8);
						ele_two=(int)(1+Math.random()*8);
						ele_three=(int)(1+Math.random()*8);
						a=(float)ele_one*ele_two/ele_three;
						if((a>=1&&a<=9)&&(a==ele_one*ele_two/ele_three)&&!(ele_one==ele_two||ele_one==ele_three||ele_two==ele_three))
							break;
					}
					problem.add(ele_one+"×"+ele_two+"÷"+ele_three+"=");
					answer.add(ele_one*ele_two/ele_three);
				}
				else
				{
					float a;
					while(true)
					{
						ele_one=(int)(1+Math.random()*8);
						ele_two=(int)(1+Math.random()*8);
						ele_three=(int)(1+Math.random()*8);
						a=(float)ele_one/ele_two;
						if(a!=ele_one/ele_two)
							continue;
						a*=ele_three;
						if(a>=1&&a<=9&&!(ele_one==ele_two||ele_one==ele_three||ele_two==ele_three))
							break;
					}
					problem.add(ele_one+"÷"+ele_two+"×"+ele_three+"=");
					answer.add(ele_one/ele_two*ele_three);
				}
				break;
			case MOD://求一个数是另一个数几倍的解题方法
				ele_one=(int)(1+Math.random()*89);
				ele_two=(int)(1+Math.random()*9);
				float f = (float) (ele_one)/ele_two;
				int hg = (int)(f);
				int k = ele_one%ele_two;
				while (hg==f||k>9||hg>9){
					ele_one=(int)(1+Math.random()*89);
					ele_two=(int)(1+Math.random()*9);
					f = (float) (ele_one)/ele_two;
					hg = (int)(f);
					k =ele_one%ele_two;
				}
				problem.add(ele_one+"÷"+ele_two+"=");
				answer.add(hg*10+k);
				break;

			case NINDIV://用9的乘法口诀求商
				ele_one=9;
				ele_two=(int)(1+Math.random()*8);
				problem.add((ele_one*ele_two)+"÷"+ele_one+"=");
				answer.add(ele_two);
				break;

			case SIXDIV://用6、7、8、9的乘法口诀求商
				ele_one=(int)(7+Math.random()*2);
				ele_two=(int)(1+Math.random()*8);
				problem.add((ele_one*ele_two)+"÷"+ele_one+"=");
				answer.add(ele_two);
				break;

			case TWODIV://用2、3、4、5的乘法口诀求商
				ele_one=(int)(2+Math.random()*5);
				ele_two=(int)(1+Math.random()*8);
				problem.add((ele_one*ele_two)+"÷"+ele_one+"=");
				answer.add(ele_two);
				break;


			case BIGSUB: //3-2
				ele_one=100+(int)(Math.random()*900);
				ele_two=10+(int)(Math.random()*90);
				problem.add(ele_one+"-"+ele_two+"=");
				answer.add(ele_one-ele_two);
				break;

			case BIGADD://3+2
				while(ele_one+ele_two>1000)
				{
					ele_one=100+(int)(Math.random()*900);
					ele_two=10+(int)(Math.random()*90);
				}
				ele_one=100+(int)(Math.random()*900);
				ele_two=10+(int)(Math.random()*90);
				problem.add(ele_one+"+"+ele_two+"=");
				answer.add(ele_one+ele_two);
				break;

			case MIDADD: //两位数加两位数口算方法
				ele_one = 200;
				while(ele_one+ele_two>100)
				{
					ele_one=(int)(10+Math.random()*90);
					ele_two=(int)(10+Math.random()*90);
				}
				AddAndSubMethod(ele_one,ele_two,1);
				break;


			case MIDDIV: //两位数减两位数的口算方法
				ele_one=(int)(10+Math.random()*89);
				ele_two=(int)(10+Math.random()*(ele_one-10));
				AddAndSubMethod(ele_one,ele_two,0);
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
