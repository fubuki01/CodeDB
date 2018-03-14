package com.readboy.mentalcalculation.game.SupplyThread;

import java.text.DecimalFormat;
import java.util.ArrayList;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class Supply_Grade_6_down implements Runnable{
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
    public static boolean pointChangeBaifenshu=false;
    final private int DECTOPER=1;  //小数化为百分数
    final private int FRATOPER = 2; //分数化为百分数
    final private int CALPER = 3; // 百分数的计算

    public Supply_Grade_6_down(Handler handler_program,Object Alock,int type) {
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
        double ele_four=-1;
        int ele_five=-1;
        int denominator=1;
        String str;
        double temp;
        int length=-1;
        boolean is_string;
        int choose=Math.random()>0.5?1:0;   //选择加减
        int choose_num=Math.random()>0.5?1:0;//选择数字
        DecimalFormat df = new DecimalFormat( "00.00 ");
        switch (type) {
            case DECTOPER://小数化为百分数或者百分数化成小数
                is_float=true;
                    ele_one = Math.random() > 0.5 ? 1 : 0;
                    String s1 = "";
                    s1 = s1 + ele_one + ".";
                    ele_two = 1 + (int) (Math.random() * 2);
                    for (int i = 0; i < ele_two; i++) {
                        if (i < ele_two - 1) {
                            ele_three = (int) (Math.random() * 10);
                            s1 = s1 + ele_three;
                        } else {
                            ele_three = 1 + (int) (Math.random() * 9);
                            s1 = s1 + ele_three;
                        }
                    }
                    Float f = Float.valueOf(s1);
                    int j = (int) (f * 100);
                    problem1.add(s1 + "=");
                    answer1.add(j+"%");
                    break;

            case FRATOPER: ///分数化为百分数
                is_float=true;
                String finallyresult="";
                while(true) {
                    ele_one = (int) (1 + Math.random() * 19);       //分母(1-19)
                    ele_two = (int) (1 + Math.random() * 29);  //分子(1-29)
                    if(ele_two%ele_one==0){  //当是整除的情况
                        finallyresult=((ele_two/ele_one)*100)+""; //得到答案
                        break;
                    }
                    else {                   //非整除情况
                        float reallyanswer = (float) (ele_two) / ele_one;
                        String jieguo=String.valueOf(reallyanswer);  //转换成字符串
                        String [] a = jieguo.split("\\.");

                        if (a[1].length() <= 2) {             //表示小数最多两位
                           finallyresult=(int)(reallyanswer*100)+"";   //避免float小数点的影响,所以先转为int
                            break;
                        }
                    }
                }
                problem1.add(ele_two+"/"+ele_one);
                answer1.add(finallyresult);
                break;


            case CALPER:  		// 百分数的计算
                is_float=true;
                String result="";
                ele_one=(int)(1+Math.random()*99);  //取得一个数
                Log.e("first1",ele_one+"");
                if(ele_one>=10){   //第一个数是大于10的，那么又要保证相乘结果是小数，则第二个要为个位
                    ele_two=(int)(1+Math.random()*9);  //得到第二个数
                    int zhuangtemp=(ele_two*ele_one);
                    float jieguo=(float) zhuangtemp/100;
                    result=String.valueOf(jieguo);
                    Log.e("result",result);
                }
                else{  //则后面的数随便出
                    ele_two=(int)(1+Math.random()*99);
                    int zhuangtemp=(ele_two*ele_one);
                    float jieguo=(float) zhuangtemp/100;
                    result=String.valueOf(jieguo);
                    Log.e("result",result);
                }
                problem1.add(ele_one+"×"+ele_two+"%"+"=");
                answer1.add(result);
                break;

            default:
                break;
        }
    }

    public void run() {
        while(!stopThread){
            is_float=false;
            CreateSubject();
            Message message = new Message();
            Bundle bundle=new Bundle();
                bundle.putString("problem",problem1.get(count_float));
                bundle.putString("answer", answer1.get(count_float));
                bundle.putBoolean("is_float",true);
                message.setData(bundle);
                handler_program.sendMessage(message);
                count_float++;
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

