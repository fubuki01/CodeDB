package com.readboy.mentalcalculation.game.SupplyThread;

import java.util.ArrayList;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.readboy.mentalcalculation.game.MyActivity.ActivityCollector;
//import android.renderscript.Program;

public class Supply_Grade_4_top implements Runnable{
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

		final private int THREEDIV=1;  //三位数乘以两位数
		final private int CANDIVALL=2;	//整十数除以整十数
		final private int MULANDDIV=3;  //几百几十除以整十数
		final private int TWODIVTWO=4;		//两位数除以两位数
		final private int THREEDIVTWO=5;		//三位数除以两位数
		final private int FAO=6;		//整数四则混合运算
		
		public Supply_Grade_4_top(Handler handler_program,Object Alock,int type) {
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

			switch (type) {
				case TWODIVTWO:
					ele_three=(int)(2+Math.random()*8);
					ele_one=(int)(10+Math.random()*90);
					while (ele_three*ele_one>=100){
						ele_three=(int)(2+Math.random()*8);
						ele_one=(int)(10+Math.random()*90);
					}
					MulAndDivMethod(ele_one,ele_three,0);
					break;
				case THREEDIVTWO:
					ele_one=(int)(10+Math.random()*90);
					ele_two=(int)(1+Math.random()*50);
					while ((ele_one*ele_two<100)||(ele_one*ele_two>=1000)){
						ele_one=(int)(10+Math.random()*90);
						ele_two=(int)(1+Math.random()*50);
					}
					MulAndDivMethod(ele_one,ele_two,0);
					break;
				case MULANDDIV://几百几十除以整十数
					ele_one=(int)(1+Math.random()*8);
					ele_two=(int)((int)(10/ele_one)+1+Math.random()*8);
					MulAndDivMethod(ele_one*10,ele_two,0);
					break;
				case CANDIVALL://整十数除以整十数
					ele_one=(int)(1+Math.random()*4);
					if(10%ele_one==0){
						ele_two=2+(int)((int)(10/ele_one-2)*Math.random());
					}
					else{
						ele_two=2+(int)((int)(10/ele_one-1)*Math.random());
					}
					MulAndDivMethod(ele_one*10,ele_two,0);
					break;
				case THREEDIV://三位数乘以两位数
					int choose=Math.random()>0.3?1:0;   //选择情况
					if (choose==1){
						ele_one=(int)(200+Math.random()*800);
						ele_two=(int)(3+Math.random()*7)*10;
					}else {
						ele_one=(int)(100+Math.random()*100);
						ele_two=(int)(10+Math.random()*11);
					}
					problem.add(ele_two+"×"+ele_one+"=");
					answer.add(ele_two*ele_one);
					break;
				case FAO://整数四则混合运算
					myFAO dofao = new myFAO();
					String[] out = dofao.faogo().split("=");
					problem.add(out[0]+"=");
					answer.add(Integer.valueOf(out[1]));
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
		
		/*具体题目添加*/
		public void addStrElement(int d,int e){
			String str = String.valueOf((d*1.0/(e*1.0)));
			int index=str.indexOf(".");
			if(d%e!=0){
				problem1.add(d+"÷"+e+"=");
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
			}
			else{
				answer.add((int)d/(int)e);
				problem.add(d+"÷"+e+"=");
				is_float=false;
			}
		}
		
		
		public void run() {
			while(!stopThread){
				is_float=false;
				CreateSubject();
				Message message = new Message(); 
				Bundle bundle=new Bundle();
				if(!is_float){
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

					bundle.putString("problem",problem.get(count));
					bundle.putString("answer", answer.get(count)+"");
					bundle.putBoolean("is_float",false);
					message.setData(bundle);
					handler_program.sendMessage(message);
					count++;
				}
				else{
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

					bundle.putString("problem",problem1.get(count_float));
					bundle.putString("answer", answer1.get(count_float));
					Log.e("myaaa", ""+problem.get(count)+answer.get(count) );
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

class myFAO {
	private static char[] symble_list = {'+','-','×','÷'};
	private node root;
	private int num_symble;
	private double pl = 0.4 , pr = 0.4;
	boolean hasmutordiv=false;

	String faogo()
	{
		String ans="";
		while(true)
		{
			num_symble=0;
			root=new node( symble_list[(int)(Math.random()*4)], (int)(Math.random()*1000) );
			pl = 0.4 ; pr = 0.4;
			factor(root);
			if(num_symble>=2)
			{
				int j=0;
				ans=visit(root,'r',' ');
				String kh="";
				for(int i=0;i<ans.length();i++)
					if(ans.charAt(i)=='('||ans.charAt(i)==')')
						kh+=ans.charAt(i);
				for(int i=0;i<4;i++)
					if(ans.indexOf(symble_list[i])!=-1)
						j++;
				if(j>=2&&!kh.equals("(())"))
					break;
			}
		}
		System.out.println(ans+"="+root.ele);
		System.out.println();
		return ans+"="+root.ele;
	}

	private String visit(node top,char fsymb,char lr)
	{
		if(top.lc==top.rc)
			return top.ele+"";
		if((top.symble=='+' || top.symble=='-') && (fsymb=='×' || fsymb=='÷'))
			return "("+visit(top.lc,top.symble,'l')+top.symble+visit(top.rc,top.symble,'r')+")";
		if((fsymb=='÷') && (top.symble=='×' || top.symble=='÷') && (lr=='r'))
			return "("+visit(top.lc,top.symble,'l')+top.symble+visit(top.rc,top.symble,'r')+")";
		if((fsymb=='-') && (top.symble=='+' || top.symble=='-') && (lr=='r'))
			return "("+visit(top.lc,top.symble,'l')+top.symble+visit(top.rc,top.symble,'r')+")";

		return visit(top.lc,top.symble,'l')+top.symble+visit(top.rc,top.symble,'r');
	}

	private void factor(node top)
	{
		if(num_symble>=3)
			return;
		addnode(top);
		num_symble++;
		if(Math.random()<0.5)
		{
			if(Math.random()<pl)
			{
				factor(top.lc);
				pl/=2;
			}

			if(Math.random()<pr)
			{
				factor(top.rc);
				pr/=2;
			}

		}
		else
		{
			if(Math.random()<pr)
			{
				factor(top.rc);
				pr/=2;
			}

			if(Math.random()<pl)
			{
				factor(top.lc);
				pl/=2;
			}

		}
		return;
	}

	private void addnode(node top)
	{
		int l=-1,r=-1;
		float t=-1;
		switch(top.symble)
		{
			case '+' :
				r=(int)(Math.random()*top.ele);
				l=top.ele-r;
				break;

			case '-' :
				l=1000;
				while(l>=1000)
				{
					r=(int)(Math.random()*1000);
					l=top.ele+r;
				}
				break;

			case '×' :
				while(true)
				{
					r=(int)(1+Math.random()*top.ele);
					t=(float)top.ele/r;
					l=top.ele/r;
					if(l==t)
						break;
				}
				hasmutordiv=true;
				break;

			case '÷' :
				l=1000;
				while(l>=1000)
				{
					r=(int)(1+Math.random()*999);
					l=top.ele*r;
				}
				hasmutordiv=true;
				break;
		}
		if(hasmutordiv)
		{
			top.lc = new node(symble_list[(int)(Math.random()*2)],l);
			top.rc = new node(symble_list[(int)(Math.random()*2)],r);
		}
		else
		{
			top.lc = new node(symble_list[(int)(Math.random()*4)],l);
			top.rc = new node(symble_list[(int)(Math.random()*4)],r);
		}
	}
}

class node {
	node lc;
	node rc;
	char symble;
	int ele;

	node(char symble,int ele)
	{
		this.symble=symble;
		this.ele=ele;
		lc=null;
		rc=null;
	}
}
