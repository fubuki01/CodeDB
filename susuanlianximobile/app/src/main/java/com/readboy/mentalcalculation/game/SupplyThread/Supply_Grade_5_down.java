package com.readboy.mentalcalculation.game.SupplyThread;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class Supply_Grade_5_down implements Runnable {
    Handler handler_program;
    Object Alock;
    int count = 0;
    int count_float = 0;
    int type; //出题类型
    boolean is_float = false;
    boolean is_keep = false;
    boolean is_middle = false;
    private ArrayList<String> problem = new ArrayList<String>();
    private ArrayList<Integer> answer = new ArrayList<Integer>();
    private ArrayList<String> answer1 = new ArrayList<String>();
    private ArrayList<String> problem1 = new ArrayList<String>();
    boolean stopThread;


    final private int MULANDDIV = 4;  //同分母分数加法
    final private int SEVENDIV = 8;  //分数加减混合运算
    final private int DIVSPACE = 5;        //同分母分数减法
    final private int DIVWAY = 6;        //同分母分数连加、连减
    final private int MULDIV = 7;        //异分母分数加、减


    final private int MULANDADD = 3;        //分数与小数互化
    final private int NULANDSUB = 1;        //求两个数最大公因数
    final private int ADDANDSUB = 2;        //求两个数最小公倍数

    public Supply_Grade_5_down(Handler handler_program, Object Alock, int type) {
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
        int ele_five = -1;
        int six = -1;
        int denominator = 1;
        int divmax;
        int temp;
        int choose = Math.random() > 0.5 ? 1 : 0;   //选择加减
        int choose_num = Math.random() > 0.5 ? 1 : 0;//选择数字
        DecimalFormat df = new DecimalFormat("00.00 ");
        switch (type) {
            case MULANDDIV://分数加法的含义和同分母分数加法的计算方法
                is_float = true;
                is_middle = true;
                ele_one = (int) (1 + Math.random() * 29);
                ele_two = (int) (1 + Math.random() * 19);
                ele_three = (int) (1 + Math.random() * 29);
                while ((ele_one + ele_three) % ele_two == 0) {
                    ele_one = (int) (1 + Math.random() * 29);
                    ele_two = (int) (1 + Math.random() * 19);
                    ele_three = (int) (1 + Math.random() * 29);
                }
                divmax = greatestCommonDivisor(ele_one + ele_three, ele_two);
                problem1.add(ele_one + "/" + ele_two + "+" + ele_three + "/" + ele_two + "=");
                answer1.add((ele_one + ele_three) / divmax + "/" + ele_two / divmax);
                break;
//			case CANDIVALL://分数加法的简算
//				is_float=true;
//				is_middle=true;
//				ele_one=(int)(10+Math.random()*49);
//				ele_two=(int)(1+Math.random()*49);
//				ele_three=(int)(1+Math.random()*49);
//				ele_four=(int)(10+Math.random()*49);
//				ele_five=(int)(1+Math.random()*49);
//				temp=ele_two+ele_three;
//				divmax=greatestCommonDivisor(temp*ele_four+ele_one*ele_five,ele_one*ele_four);
//				problem1.add(ele_two+"/"+ele_one+"+"+ele_five+"/"+ele_four+"+"+ele_three+"/"+ele_one+"=");
//				answer1.add((temp*ele_four+ele_one*ele_five)/divmax+"/"+ele_one*ele_four/divmax);
//				//problem.add(ele_two+"/"+ele_one+"+"+ele_five+"/"+ele_four+ele_three+"/"+ele_one+"=");
//				//answer2.add((float)(ele_two/ele_one+ele_three/ele_one+ele_five/ele_four));
//				break;
            case SEVENDIV://分数加减混合运算的运算顺序
                is_float = true;
                is_middle = true;
                int ch0 = Math.random() > 0.7 ? 1 : 0;   //选择加减
                if (ch0 == 1) {
                    int [] h = new int[]{2, 3, 4, 5, 6, 7, 8, 9};
                    int t = (int) Math.random() * 8;
                    int u = 19/h[t];
                    if (h[t]==2){
                        int[] a4 = new int[]{5, 6, 7, 8, 9};
                        int g4 = (int) (Math.random() * 5);
                        ele_one = h[t]*a4[g4];
                        int[] a5 = new int[]{1, 2, 3, 4};
                        int g5 = (int) (Math.random() * 4);
                        ele_four = h[t]*a5[g5];
                    }else if (h[t]==3){
                        int[] a4 = new int[]{4, 5, 6};
                        int g4 = (int) (Math.random() * 3);
                        ele_one = h[t]*a4[g4];
                        int[] a5 = new int[]{1, 2, 3};
                        int g5 = (int) (Math.random() * 3);
                        ele_four = h[t]*a5[g5];
                    }else {
                        ele_one = u*h[t];
                        ele_four = (u-1)*h[t];
                    }
//                    else if (a[g]==3){
//                        int[] a4 = new int[]{4, 5, 6};
//                        int g4 = (int) (Math.random() * 3);
//                        ele_one = a[g]*f;
//                        int[] a5 = new int[]{1, 2, 3};
//                        int g5 = (int) (Math.random() * 3);
//                        ele_two = a[g]*a5[g5];
//                    }else {
//                        ele_one = a[g]*f;
//                        ele_two = (f-1)*a[g];
//                    }
//                    ele_one = u*h[t];
                    ele_two = (int) (1 + Math.random() * 29);
                    ele_three = (int) (1 + Math.random() * ele_two);
//                    ele_four = (u-1)*h[t];
                    ele_five = (int) (1 + Math.random() * 29);
                    temp = ele_two - ele_three;
                    divmax = greatestCommonDivisor(temp * ele_four + ele_one * ele_five, ele_one * ele_four);
                    while (((temp * ele_four + ele_one * ele_five) / divmax) % (ele_one * ele_four / divmax) == 0) {
                        ele_five = (int) (1 + Math.random() * 29);
                        temp = ele_two - ele_three;
                        divmax = greatestCommonDivisor(temp * ele_four + ele_one * ele_five, ele_one * ele_four);
                    }
                    Log.e("0000000000000", ele_two + "/" + ele_one + "+" + ele_five + "/" + ele_four + "-" + ele_three + "/" + ele_one + "="+"");
                    problem1.add(ele_two + "/" + ele_one + "+" + ele_five + "/" + ele_four + "-" + ele_three + "/" + ele_one + "=");
                    answer1.add((temp * ele_four + ele_one * ele_five) / divmax + "/" + ele_one * ele_four / divmax);
                } else {
                    int[] p = new int[]{2, 3, 4, 5, 6};
                    int d = (int) Math.random() * 3;
                    int u = 19/p[d];
                    if (p[d]==2){
                        int[] a4 = new int[]{5, 6, 7, 8, 9};
                        int g4 = (int) (Math.random() * 5);
                        ele_one = p[d]*a4[g4];
                        ele_four = (a4[g4]-1)*p[d];
                        six = (a4[g4]-2)*p[d];
                    }else {
                        ele_one = u*p[d];
                        ele_four = (u-1)*p[d];
                        six = (u-2)*p[d];
                    }
//                    ele_one = u*p[d];
//                    ele_four = (u-1)*p[d];
//                    six = (u-2)*p[d];
                    ele_two = (int) (1 + Math.random() * 29);
                    ele_five = (int) (1 + Math.random() * 29);
                    ele_three = (int) (1 + Math.random() * ele_two);
                    int w = ((ele_two * ele_four * six) + (ele_five * ele_one * six) - (ele_three * ele_one * ele_four)) % (ele_one * ele_four * six);
                    while (w==0){
                        ele_five = (int) (1 + Math.random() * 29);
                        w = ((ele_two * ele_four * six) + (ele_five * ele_one * six) - (ele_three * ele_one * ele_four)) % (ele_one * ele_four * six);
                    }
                    divmax = greatestCommonDivisor((ele_two * ele_four * six) + (ele_five * ele_one * six) - (ele_three * ele_one * ele_four), ele_one * ele_four * six);
                    Log.e("0000000000000", ele_two + "/" + ele_one + "+" + ele_five + "/" + ele_four + "-" + ele_three + "/" + six + "="+"");
                    problem1.add(ele_two + "/" + ele_one + "+" + ele_five + "/" + ele_four + "-" + ele_three + "/" + six + "=");
                    answer1.add(((ele_two * ele_four * six) + (ele_five * ele_one * six) - (ele_three * ele_one * ele_four)) / divmax + "/" + (ele_one * ele_four * six) / divmax);
                }
                break;
            case DIVSPACE://分数减法的含义和同分母分数减法的计算方法
                is_float = true;
                is_middle = true;
                ele_one = (int) (1 + Math.random() * 29);
                ele_two = (int) (1 + Math.random() * 19);
                ele_three = (int) (1 + Math.random() * (ele_one - 1));
                while ((ele_one - ele_three) % ele_two == 0) {
                    ele_one = (int) (1 + Math.random() * 29);
                    ele_two = (int) (1 + Math.random() * 19);
                    ele_three = (int) (1 + Math.random() * (ele_one - 1));
                }
                divmax = greatestCommonDivisor(ele_one - ele_three, ele_two);
                problem1.add(ele_one + "/" + ele_two + "-" + ele_three + "/" + ele_two + "=");
                answer1.add((ele_one - ele_three) / divmax + "/" + ele_two / divmax);
                break;
            case DIVWAY://同分母分数连加、连减的计算方法
                if (choose == 1) {
                    is_middle = true;
                    is_float = true;
                    ele_one = (int) (1 + Math.random() * 19);
                    ele_two = (int) (1 + Math.random() * 29);
                    ele_three = (int) (1 + Math.random() * 29);
                    ele_four = (int) (1 + Math.random() * 29);
                    while ((ele_two + ele_three + ele_four) % ele_one == 0) {
                        ele_one = (int) (1 + Math.random() * 19);
                        ele_two = (int) (1 + Math.random() * 29);
                        ele_three = (int) (1 + Math.random() * 29);
                        ele_four = (int) (1 + Math.random() * 29);
                    }
                    divmax = greatestCommonDivisor(ele_two + ele_three + ele_four, ele_one);
                    problem1.add(ele_two + "/" + ele_one + "+" + ele_three + "/" + ele_one + "+" + ele_four + "/" + ele_one + "=");
                    answer1.add((ele_two + ele_three + ele_four) / divmax + "/" + ele_one / divmax);
                } else {
                    is_middle = true;
                    is_float = true;
                    ele_one = (int) (1 + Math.random() * 19);
                    ele_two = (int) (8 + Math.random() * 22);
                    ele_three = (int) (2 + Math.random() * (ele_two - 4));
                    ele_four = (int) (1 + Math.random() * (ele_two - ele_three - 1));
                    while ((ele_two - ele_three - ele_four) % ele_one == 0) {
                        ele_one = (int) (1 + Math.random() * 19);
                        ele_two = (int) (8 + Math.random() * 22);
                        ele_three = (int) (2 + Math.random() * (ele_two - 4));
                        ele_four = (int) (1 + Math.random() * (ele_two - ele_three - 1));
                    }
                    divmax = greatestCommonDivisor(ele_two - ele_three - ele_four, ele_one);
                    problem1.add(ele_two + "/" + ele_one + "-" + ele_three + "/" + ele_one + "-" + ele_four + "/" + ele_one + "=");
                    answer1.add((ele_two - ele_three - ele_four) / divmax + "/" + ele_one / divmax);
                }
                break;

            case MULDIV://异分母分数加、减法的计算方法
                if (choose == 1) {
                    is_float = true;
                    is_middle = true;
                    int[] a = new int[]{2, 3, 4, 5, 6, 7, 8, 9};
                    int g = (int) (Math.random() * 8);
                    Log.e("88888888888", g+"");
                    int f = 19/a[g];
                    if (a[g]==2){
                        int[] a4 = new int[]{5, 6, 7, 8, 9};
                        int g4 = (int) (Math.random() * 5);
                        ele_one = a[g]*f;
                        int[] a5 = new int[]{1, 2, 3, 4};
                        int g5 = (int) (Math.random() * 4);
                        ele_two = a[g]*a5[g5];
                    }
                    else if (a[g]==3){
                        int[] a4 = new int[]{4, 5, 6};
                        int g4 = (int) (Math.random() * 3);
                        ele_one = a[g]*f;
                        int[] a5 = new int[]{1, 2, 3};
                        int g5 = (int) (Math.random() * 3);
                        ele_two = a[g]*a5[g5];
                    }else {
                        ele_one = a[g]*f;
                        ele_two = (f-1)*a[g];
                    }
                    ele_three = (int) (1 + Math.random() * 29);
                    ele_four = (int) (1 + Math.random() * 29);
                    while ((ele_three * ele_two + ele_four * ele_one) % (ele_one * ele_two) == 0) {
                        ele_three = (int) (1 + Math.random() * 29);
                        ele_four = (int) (1 + Math.random() * 29);
                    }
                    divmax = greatestCommonDivisor(ele_three * ele_two + ele_one * ele_four, ele_one * ele_two);
                    problem1.add(ele_three + "/" + ele_one + "+" + ele_four + "/" + ele_two + "=");
                    answer1.add((ele_three * ele_two + ele_one * ele_four) / divmax + "/" + ele_one * ele_two / divmax);
                } else {
                    is_float = true;
                    is_middle = true;
                    int[] a1 = new int[]{2, 3, 4, 5, 6, 7, 8, 9};
                    int g1 = (int) Math.random() * 8;
                    int f1 = 19/a1[g1];
                    if (a1[g1]==2){
                        int[] a4 = new int[]{5, 6, 7, 8, 9};
                        int g4 = (int) (Math.random() * 5);
                        ele_one = a1[g1]*f1;
                        int[] a5 = new int[]{1, 2, 3, 4};
                        int g5 = (int) (Math.random() * 4);
                        ele_two = a1[g1]*a5[g5];
                    }
                    else if (a1[g1]==3){
                        int[] a4 = new int[]{4, 5, 6};
                        int g4 = (int) (Math.random() * 3);
                        ele_one = a1[g1]*f1;
                        int[] a5 = new int[]{1, 2, 3};
                        int g5 = (int) (Math.random() * 3);
                        ele_two = a1[g1]*a5[g5];
                    }else {
                        ele_one = a1[g1]*f1;
                        ele_two = (f1-1)*a1[g1];
                    }
                    ele_three = (int) (1 + Math.random() * 29);
                    temp = ele_two * ele_three / ele_one;
                    ele_four = (int) (1 + Math.random() * (temp - 1));
                    divmax = greatestCommonDivisor(ele_three * ele_two - ele_four * ele_one, ele_one * ele_two);
                    problem1.add(ele_three + "/" + ele_one + "-" + ele_four + "/" + ele_two + "=");
                    answer1.add((ele_three * ele_two - ele_four * ele_one) / divmax + "/" + ele_one * ele_two / divmax);
                }
                break;

            case MULANDADD://分数化成小数的方法
                is_keep = true;
                is_float = true;
                ele_one = (int) (1 + Math.random() * 100);
                ele_two = (int) (1 + Math.random() * 10);
                while (ele_one%ele_two==0){
                    ele_one = (int) (1 + Math.random() * 100);
                    ele_two = (int) (1 + Math.random() * 10);
                }
                double h1 = ((double) ele_one)/ele_two;
                BigDecimal b2 = new BigDecimal(h1);
                double f1 = b2.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                int e = (int)f1*10;
                if (f1*10==e){
                    f1 = ((double) e)/10;
                }
                Log.e("5555555555", f1+"");
                problem1.add(ele_one + "/" + ele_two);
                answer1.add(f1+"");
                break;


            case NULANDSUB://求两个数最大公因数的方法
                is_middle = false;
                ele_one = (int) (10 + Math.random() * 89);
                ele_two = (int) (9 + Math.random() * (ele_one - 10));
                int a = ele_one;
                int b = ele_two;
                int c = 0;
                int k = 0;
                while (b != 0)  /* 余数不为0，继续相除，直到余数为0 */ {
                    c = a % b;
                    a = b;
                    b = c;
                    k++;
                    if (k >= 100) {
                        a = 1;
                        k = 0;
                        break;
                    }
                }
                while ((b == 0) && (a >= 30)) {
                    ele_one = (int) (10 + Math.random() * 89);
                    ele_two = (int) (9 + Math.random() * (ele_one - 10));
                    a = ele_one;
                    b = ele_two;
                    c = 0;
                    while (b != 0)  /* 余数不为0，继续相除，直到余数为0 */ {
                        c = a % b;
                        a = b;
                        b = c;
                        k++;
                        if (k >= 100) {
                            a = 1;
                            k = 0;
                            break;
                        }
                    }
                }
                problem.add(ele_one + "和" + ele_two + "的最大公约数是:");
                answer.add(a);
                break;
            case ADDANDSUB://求两个数最小公倍数的方法
                is_middle = false;
                ele_one = (int) (1 + Math.random() * 99);
                ele_two = (int) (2 + Math.random() * 98);
                int ab = lowest_common_multiple(ele_one, ele_two);
                while (ab >= 100) {
                    ele_one = (int) (1 + Math.random() * 99);
                    ele_two = (int) (2 + Math.random() * 98);
                    ab = lowest_common_multiple(ele_one, ele_two);
                }
                problem.add(ele_one + "和" + ele_two + "的最小公倍数是:");
                answer.add(ab);
                break;
            default:
                break;
        }
    }

    /*最小公倍数*/
    public int lowest_common_multiple(int m, int n) {
        int remainder, m1, n1;
        m1 = m;
        n1 = n;
        while (n != 0) {
            remainder = m % n;
            m = n;
            n = remainder;
        }
        return m1 * n1 / m;
    }

    /*计算加减法*/
    public void AddAndSubMethod(int num1, int num2, int choose) {
        if (choose == 1) {
            problem.add(num1 + "+" + num2);
            answer.add(num1 + num2);
        } else {
            int ele_sum = num1 + num2;
            problem.add(ele_sum + "-" + num1);
            answer.add(num2);
        }
    }


    /*最大公约数*/
    public int greatestCommonDivisor(int a, int b) {
        int c = a % b;
        while (c != 0) {
            a = b;
            b = c;
            c = a % b;
        }
        return b;
    }


    /*计算乘除*/
    public void MulAndDivMethod(int num1, int num2, int choose) {
        if (choose == 1) {
            problem.add(num1 + "×" + num2);
            answer.add(num1 * num2);
        } else {
            int ele_sum = num1 * num2;
            problem.add(ele_sum + "÷" + num1);
            answer.add(num2);
        }
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

    /*具体题目添加*/
    public boolean addStrElement(double d, double e, boolean mul_or_div) {
        String str = null;
        if (mul_or_div == true) {
            str = String.valueOf((d * 1.0 / (e * 1.0)));
        } else {
            str = String.valueOf((d * 1.0 * (e * 1.0)));
        }
        int index = str.indexOf(".");
        if (d % e != 0) {
            if (index == 1 && str.length() == 3) {
                answer1.add(str.substring(0, 3));
//				Log.e("00000000",str.substring(0, 3)+"");
            } else if (index == 1 && str.length() >= 4) {
                answer1.add(str.substring(0, 4));
//				Log.e("00000000",str.substring(0, 4)+"");
            } else if (index == 2 && str.length() == 4) {
                answer1.add(str.substring(0, 4));
//				Log.e("00000000",str.substring(0, 4)+"");
            } else if (index == 2 && str.length() >= 5) {
                answer1.add(str.substring(0, 5));
//				Log.e("00000000",str.substring(0, 5)+"");
            } else if (index == 3 && str.length() == 5) {
                answer1.add(str.substring(0, 5));
//				Log.e("00000000",str.substring(0, 5)+"");
            } else if (index == 3 && str.length() >= 6) {
                answer1.add(str.substring(0, 6));
//				Log.e("00000000",str.substring(0, 6)+"");
            } else if (index == 4 && str.length() == 6) {
                answer1.add(str.substring(0, 6));
//				Log.e("00000000",str.substring(0, 6)+"");
            } else if (index == 4 && str.length() >= 7) {
                answer1.add(str.substring(0, 7));
//				Log.e("00000000",str.substring(0, 7)+"");
            }
            return true;
        } else {
            answer.add((int) d / (int) e);
//			Log.e("00000000", (int)d/(int)e+"");
            is_float = false;
            return false;
        }
    }

    public void run() {
        while (!stopThread) {
            is_float = false;
            CreateSubject();
            Message message = new Message();
            Bundle bundle = new Bundle();
            if (!is_float) {
                bundle.putString("problem", problem.get(count));
                bundle.putString("answer", answer.get(count)+"");
                bundle.putBoolean("is_float", false);
                message.setData(bundle);
                handler_program.sendMessage(message);
                count++;
            } else {
                bundle.putString("problem", problem1.get(count_float));
                bundle.putString("answer", answer1.get(count_float));
                bundle.putBoolean("is_float", true);
                bundle.putBoolean("is_middle", true);
                message.setData(bundle);
                handler_program.sendMessage(message);
                count_float++;
            }
            if (is_keep){
                bundle.putBoolean("is_keep", true);
            }else {
                bundle.putBoolean("is_keep", false);
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

