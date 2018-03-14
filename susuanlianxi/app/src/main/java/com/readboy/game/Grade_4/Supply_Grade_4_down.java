package com.readboy.game.Grade_4;

import java.math.BigDecimal;
import java.util.ArrayList;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class Supply_Grade_4_down implements Runnable {
    Handler handler_program;
    Object Alock;
    int count = 0;
    int count_float = 0;
    int type; //出题类型
    boolean is_float = false;
    private ArrayList<String> problem = new ArrayList<String>();
    private ArrayList<Integer> answer = new ArrayList<Integer>();
    private ArrayList<String> answer1 = new ArrayList<String>();
    private ArrayList<String> problem1 = new ArrayList<String>();
    boolean stopThread;


    final private int DIVSPACE = 1;        //加法结合律
    final private int CANDIVALL = 2;    //乘法结合律
    final private int MULDIV = 3;            //乘法分配率
    final private int CONTINUSUB = 4;            //连减的简便运算
    final private int BIGADD = 5;                //小数加法
    final private int BIGDIV = 6;                //小数减法
    final private int ADDANDSUB = 7;            //小数的加减混合

    public Supply_Grade_4_down(Handler handler_program, Object Alock, int type) {
        this.handler_program = handler_program;
        this.Alock = Alock;
        this.type = type;
        stopThread = false;
    }

    public void setTag(boolean stopThread) {
        this.stopThread = stopThread;
    }

		
		/*产生题目*/

    public void CreateSubject() {
        int ele_one = -1;
        int ele_two = -1;
        int ele_three = -1;
        int ele_four = -1;
        int choose = Math.random() > 0.5 ? 1 : 0;   //选择加减
        switch (type) {
            case MULDIV:
                int ch2=Math.random()>0.5?1:0;
                if (ch2==1){
                    ele_four = (int) (1 + Math.random() *9)*100;
                    ele_two = (int) (1 + Math.random() *9)*10;//c
                    ele_one = ele_four/ele_two;//A
                    ele_three = (int) (1 + Math.random() *9)*10;//B
                }else {
                    ele_four = (int) (6 + Math.random() *4)*10;
                    ele_two = (int) (1 + Math.random() *5)*10;//c
                    ele_one = ele_four/ele_two;//A
                    ele_three = (int) (1 + Math.random() *9)*10;//B
                }
                problem.add("("+ele_one+"+"+ele_three+")"+"×"+ele_two+"=");
                answer.add(ele_one*ele_two+ele_three*ele_two);
                break;
            case CONTINUSUB:
                int ch3=Math.random()>0.5?1:0;
                if (ch3==1){
                    ele_four = (int) (1 + Math.random() *9)*100;
                    ele_two = (int) (8 + Math.random() *(ele_four-10));//c
                    ele_one = ele_four-ele_two;//B
                    ele_three = (ele_two+ele_one)+(int) (10+ Math.random()*600);//A
                }else {
                    ele_four = (int) (1 + Math.random() *9)*10;
                    ele_two = (int) (4 + Math.random()*(ele_four-6));//c
                    ele_one = ele_four-ele_two;//B
                    ele_three = (ele_two+ele_one)+(int)(10+ Math.random()*600);//A
                }
                problem.add(ele_three+"-"+ele_one+"-"+ele_two+"=");
                answer.add(ele_three-ele_one-ele_two);
                break;
            case ADDANDSUB:
            {
                is_float = true;
                int a1, a2, b1, b2,c1,c2;
                String a = "", b = "", c = "", ans = "";
                while (true) {
                    a1 = (int) (Math.random() * 10);
                    double cc = Math.random();
                    if (cc <= 0.5)
                        a2 = (int) (10 + Math.random() * 90);
                    else
                        a2 = (int) (1 + Math.random() * 9);
                    while (a2 % 10 == 0)
                        a2 = a2 / 10;
                    a = a1 + "." + a2;

                    b1 = (int) (Math.random() * 10);
                    cc = Math.random();
                    if (cc <= 0.5)
                        b2 = (int) (10 + Math.random() * 90);
                    else
                        b2 = (int) (1 + Math.random() * 9);
                    while (b2 % 10 == 0)
                        b2 = b2 / 10;
                    b = b1 + "." + b2;

                    c1 = (int) (Math.random() * 10);
                    cc = Math.random();
                    if (cc <= 0.5)
                        c2 = (int) (10 + Math.random() * 90);
                    else
                        c2 = (int) (1 + Math.random() * 9);
                    while (c2 % 10 == 0)
                        c2 = c2 / 10;
                    c = c1 + "." + c2;

                    if(a1+b1<c1)
                        continue;

                    BigDecimal bd1 = new BigDecimal(a);
                    ans = Double.toString(bd1.add(new BigDecimal(b)).doubleValue());
                    BigDecimal bd2 = new BigDecimal(ans);
                    ans = Double.toString(bd2.subtract(new BigDecimal(c)).doubleValue());
                    String[] strtemp = ans.split("\\.");

                    if (!strtemp[1].equals("0"))
                        break;
                }
                problem1.add(a + "+" + b + "-" + c + "=");
                answer1.add(ans);
                break;
            }

            case CANDIVALL://乘法结合律
                int j = 0;
                ele_one = (int) (1 + Math.random() * 99);
                ele_two = (int) (1 + Math.random() * 99);
                ele_three = (int) (1 + Math.random() * 99);
                while (!candivall(ele_two,ele_three)) {
                    ele_two = (int) (1 + Math.random() * 99);
                    ele_three = (int) (1 + Math.random() * 99);
                }
                problem.add(ele_one + "×" + ele_two + "×" + ele_three + "=");
                answer.add(ele_one * ele_two * ele_three);
                break;

            case DIVSPACE://加法结合律
                ele_one = (int) (1 + Math.random() * 999);
                ele_two = (int) (1 + Math.random() * 999);
                ele_three = (int) (1 + Math.random() * 999);
                int i = 0;
                while (((ele_one + ele_three) % 10 != 0) || ((ele_two + ele_three) % 10 != 0)||(ele_one + ele_three>=1000)||(ele_two + ele_three>1000)) {
                    ele_one = (int) (1 + Math.random() * 999);
                    ele_two = (int) (1 + Math.random() * 999);
                    ele_three = (int) (1 + Math.random() * 999);
                    i++;
                    if (i == 1000) {
                        int ch1 = Math.random() > 0.5 ? 1 : 0; //整百整十
                        if (ch1 == 1) {
                            ele_one = (int) (1 + Math.random() * 9) * 10;
                            ele_three = (int) (1 + Math.random() * 9) * 10;
                            ele_two = (int)(1 + Math.random() * 9)* 10;
                        } else {
                            ele_one = (int) (1 + Math.random() * 999);
                            ele_two = (int) (1 + Math.random() * 5)*100;
                            ele_three = (int) (1 + Math.random() * 4) * 100;
                        }
                        break;
                    }
                }
                problem.add(ele_one + "+" + ele_two + "+" + ele_three + "=");
                answer.add(ele_one + ele_two + ele_three);
//					ele_one= (int)(10+Math.random()*89)*10;
//					ele_two=(int)(10+Math.random()*(ele_one-10));
//					ele_three=ele_one-ele_two;
//					ele_four=(int)(10+Math.random()*989);
//					problem.add(ele_four+"+"+ele_two+"+"+ele_three+"=");
//					answer.add(ele_four+ele_two+ele_three);

                break;

            case BIGADD://小数加法的计算方法
            {
                is_float = true;
                int a1, a2, b1, b2;
                String a = "", b = "", ans = "";
                while (true) {
                    a1 = (int) (Math.random() * 10);
                    double cc = Math.random();
                    if (cc <= 0.5)
                        a2 = (int) (10 + Math.random() * 90);
                    else
                        a2 = (int) (1 + Math.random() * 9);
                    while (a2 % 10 == 0)
                        a2 = a2 / 10;
                    a = a1 + "." + a2;

                    b1 = (int) (Math.random() * 10);
                    cc = Math.random();
                    if (cc <= 0.5)
                        b2 = (int) (10 + Math.random() * 90);
                    else
                        b2 = (int) (1 + Math.random() * 9);
                    while (b2 % 10 == 0)
                        b2 = b2 / 10;
                    b = b1 + "." + b2;

                    if(a2==b2)
                        continue;

                    BigDecimal bd1 = new BigDecimal(a);
                    ans = Double.toString(bd1.add(new BigDecimal(b)).doubleValue());
                    String[] strtemp = ans.split("\\.");

                    if (!strtemp[1].equals("0"))
                        break;
                }
                problem1.add(a + "+" + b + "=");
                answer1.add(ans);
                break;
            }
            case BIGDIV: //小数减法的计算方法
            {
                is_float = true;
                int a1, a2, b1, b2;
                String a = "", b = "", ans = "";
                while (true) {
                    a1 = (int) (1+Math.random() * 9);
                    double cc = Math.random();
                    if (cc <= 0.5)
                        a2 = (int) (10 + Math.random() * 90);
                    else
                        a2 = (int) (1 + Math.random() * 9);
                    while (a2 % 10 == 0)
                        a2 = a2 / 10;
                    a = a1 + "." + a2;

                    b1 = (int) (Math.random() * a1);
                    cc = Math.random();
                    if (cc <= 0.5)
                        b2 = (int) (10 + Math.random() * 90);
                    else
                        b2 = (int) (1 + Math.random() * 9);
                    while (b2 % 10 == 0)
                        b2 = b2 / 10;
                    b = b1 + "." + b2;

                    if(a2==b2)
                        continue;

                    BigDecimal bd1 = new BigDecimal(a);
                    ans = Double.toString(bd1.subtract(new BigDecimal(b)).doubleValue());
                    String[] strtemp = ans.split("\\.");

                    if (!strtemp[1].equals("0"))
                        break;
                }
                problem1.add(a + "-" + b + "=");
                answer1.add(ans);
                break;
            }

            default:
                break;
        }
    }


    /*计算加减法*/
    public void AddAndSubMethod(int num1, int num2, int choose) {
        if (choose == 1) {
            problem.add(num1 + "+" + num2 + "=");
            answer.add(num1 + num2);
        } else {
            int ele_sum = num1 + num2;
            problem.add(ele_sum + "-" + num1 + "=");
            answer.add(num2);
        }
    }


    /*计算乘除*/
    public void MulAndDivMethod(int num1, int num2, int choose) {
        if (choose == 1) {
            problem.add(num1 + "×" + num2 + "=");
            answer.add(num1 * num2);
        } else {
            int ele_sum = num1 * num2;
            problem.add(ele_sum + "÷" + num1 + "=");
            answer.add(num2);
        }
    }
    /*用于判断乘法结合律出题条件*/
    private boolean candivall(int b,int c) {
        int ans = b*c;
        if(ans>=1000)
            return false;
        if(ans%10==0)
        {
            if(ans<100)
                return true;
            else if(ans%100==0)
                return true;
            else
                return false;
        }
        else
            return false;
    }

    /*整数因式分解*/
    public int IntFactor(int num) {
        int[] result = new int[num];
        int factor_num = 0;
        int choose_which = 0;
        for (int i = 1; i <= num; i++) {
            if (num % i == 0) {
                result[factor_num] = i;
                factor_num++;
            }
        }
        choose_which = (int) (Math.random() * (factor_num - 1));
        return result[choose_which];
    }

    public void run() {
        while (!stopThread) {
            is_float = false;
            CreateSubject();
            Message message = new Message();
            Bundle bundle = new Bundle();
            if (!is_float) {
                bundle.putString("problem", problem.get(count));
                bundle.putInt("answer", answer.get(count));
                bundle.putBoolean("is_float", false);
                message.setData(bundle);
                handler_program.sendMessage(message);
                count++;
            } else {
                bundle.putString("problem", problem1.get(count_float));
                bundle.putString("answer", answer1.get(count_float));
                bundle.putBoolean("is_float", true);
                message.setData(bundle);
                handler_program.sendMessage(message);
                count_float++;
            }
            synchronized (Alock) {
                try {
                    Alock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

