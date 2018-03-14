package com.readboy.mentalcalculation.game.SupplyThread;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class Supply_Grade_3_down implements Runnable {
    Handler handler_program;
    Object Alock;
    int count = 0;
    int count_float = 0;
    int type; //出题类型
    boolean is_float = false;
    boolean is_middle = false;
    private ArrayList<String> problem = new ArrayList<String>();
    private ArrayList<Integer> answer = new ArrayList<Integer>();
    private ArrayList<String> answer1 = new ArrayList<String>();
    private ArrayList<String> problem1 = new ArrayList<String>();
    boolean stopThread;

    final private int DIVWAY = 25;         //一位数除两位数（被除数各数位上都能被整除）的计算方法
    final private int MULDIV = 26;         //用一位数除商是整十、整百、整千的口算方法
    final private int MULANDADD = 27;        //有关0的除法


    final private int NULANDSUB = 28;        //用乘法两步计算解决问题


    final private int ADDANDSUB = 29;        //两位数乘两位数（不进位）的笔算方法


    final private int SMALLBRACKET = 20;        //平均数的含义及求平均数的方法


    final private int BIGADD = 21;                //小数加法的计算方法
    final private int BIGDIV = 22;                //小数减法的计算方法

    ////////////////
    final private int ZHENGSHIDIVIDEONE = 1;  //整十整百除以一位数
    final private int BAIADDSHIDIVIDE = 2;   //几百几十、几千几百除以一位数
    final private int TWODIVIDEONE = 3;    //二位数除以一位数（最高位能被整除）

    final private int THREEDIVIDEONE = 4;    //三位数除以一位数（最高位能被整除）
    final private int MULANDDIV = 6;    //商末尾有0的除法的计算方法（商中有余数）
    final private int CANDIVALL = 8;     //商末尾有0的除法  商没有余数
    final private int SEVENDIV = 5;     //商中间有0的除法有余数
    final private int DIVSPACE = 7;          //商中间有0的除法的计算方法（除的过程中没有余数）
    final private int TWOMULZHENGSHI = 9;  //两位数乘以整十
    final private int TWOMULTWOSHU = 10;  //两位数乘两位数

    //final private int NOTWODIVIDEONE = 23;   //二位数除以一位数（最高位不能被整除）
    final private int NOTHREEDIVIDEONE = 24;  //三位数除以一位数（最高位不能被整除）
    final private int TWOMULTWO = 30;          //两位数乘两位数（进位）


    public Supply_Grade_3_down(Handler handler_program, Object Alock, int type) {
        // TODO Auto-generated constructor stub
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
        int choose_num = Math.random() > 0.5 ? 1 : 0;//选择数字
        switch (type) {
            case MULANDDIV://商末尾有0的除法的计算方法（商中有余数）
                is_middle = true;
                is_float = false;
                ele_two = (int) (2 + Math.random() * 8);   //第二位数2--9
                ele_one = (int) (11 + Math.random() * 88);  //生成一个二位数
                ele_one = ele_one * 10;    //变成三位数，并且末尾是0
                while (ele_one % ele_two == 0) {  //整除的情况不符合
                    ele_two = (int) (2 + Math.random() * 8);   //第二位数2--9
                }

                int yushu = ele_one % ele_two;  //得到余数
                int shang = ele_one / ele_two;  //得到商
                Log.e("shang0", shang + "");
                Log.e("yushu0", yushu + "");
                problem.add(ele_one + "÷" + ele_two + "=");
                Log.e("11111111", ele_one + "÷" + ele_two + "=" + "");
                answer.add(shang * 10 + yushu);
                break;
            case CANDIVALL://商末尾有0的除法计算方法（商没有余数）
                is_middle = false;
                ele_one = (int) (1 + Math.random() * 98) * 10;
                ele_two = (int) (1 + Math.random() * 8);
                problem.add(ele_one * ele_two + "÷" + ele_two + "=");
                answer.add((ele_one * ele_two) / ele_two);
                break;
            case SEVENDIV://商中间有0的除法的计算方法（有余数）
                is_middle = true;
                is_float = false;
                ele_two = (int) (2 + Math.random() * 8);   //第二位数2--9
                while (true) {
                    ele_one = (int) (1 + Math.random() * 9);
                    ele_one = ele_one * 100 + (int) (1 + Math.random() * 9); //生成一个中间是0的三位数
                    if (ele_one % ele_two != 0) {
                        break;
                    }
                }
                int otheryushu = ele_one % ele_two;  //得到余数
                int othershang = ele_one / ele_two;  //得到商
                problem.add(ele_one + "÷" + ele_two + "=");
                answer.add(othershang * 10 + otheryushu);

                break;
            case DIVSPACE://商中间有0的除法的计算方法（没有余数）
                is_middle = false;
                ele_one = (int) (1 + Math.random() * 8) * 100;
                ele_two = (int) (1 + Math.random() * 8) + ele_one;
                ele_three = (int) (1 + Math.random() * 8);
                problem.add(ele_three * ele_two + "÷" + ele_three + "=");
                answer.add((ele_three * ele_two) / ele_three);
                break;
            case DIVWAY://一位数除两位数（被除数各数位上都能被整除）的计算方法
                is_middle = false;
                ele_one = (int) (1 + Math.random() * 8);
                ele_two = ele_one * (int) (Math.random() * (int) (10 / ele_one));
                ele_three = ele_one * (int) (1 + Math.random() * ((int) (10 / ele_one) - 1));
                while (ele_two == 10 || ele_three == 10) {
                    ele_two = ele_one * (int) (Math.random() * (int) (10 / ele_one));
                    ele_three = ele_one * (int) (1 + Math.random() * ((int) (10 / ele_one) - 1));
                }
                problem.add(ele_three * 10 + ele_two + "÷" + ele_one + "=");
                answer.add((ele_three * 10 + ele_two) / ele_one);
                break;

            case ZHENGSHIDIVIDEONE://整十,整百除以一位数
                is_middle = false;
                ele_one = (int) (1 + Math.random() * 9);            //除数
                boolean isfuheyiweizhengchu = false;
                while (isfuheyiweizhengchu == false) {
                    if (choose_num == 1) {
                        ele_two = (int) (1 + Math.random() * 9) * 100;  //得到整百
                    } else if (choose_num == 0) {
                        ele_two = (int) (1 + Math.random() * 9) * 10;  //得到整十
                    }
                    if (ele_two % ele_one == 0) {
                        isfuheyiweizhengchu = true;
                    }
                }
                problem.add(ele_two + "÷" + ele_one + "=");
                answer.add((ele_two) / ele_one);
                break;

            case MULANDADD://有关0的除法
                is_middle = false;
                ele_one = 0;
                ele_two = (int) (1 + Math.random() * 8);
                problem.add(ele_one + "÷" + ele_two + "=");
                answer.add(ele_one / ele_two);
                break;


            case ADDANDSUB://两位数乘两位数（不进位）的笔算方法
                is_middle = false;
                ele_one = (int) (2 + Math.random() * 7);
                ele_two = (int) (Math.random() * (int) (10 / ele_one));
                ele_three = (int) (2 + Math.random() * 7);
                ele_four = (int) (1 + Math.random() * ((int) (10 / ele_three) - 1));
                int temp = ele_two + ele_four * 10;
                problem.add(ele_one + ele_three * 10 + "×" + temp + "=");
                answer.add((ele_one + ele_three * 10) * (ele_two + ele_four * 10));
                break;

            ////////////新增的题目类型
            case BAIADDSHIDIVIDE: //几百几十、几千几百除以一位数
                is_middle = false;
                ele_one = (int) (1 + Math.random() * 9);            //除数
                boolean isfuhezhengshu = false;
                while (isfuhezhengshu == false) {
                    if (choose_num == 1) {
                        ele_two = (int) (1 + Math.random() * 9) * 1000;  //整千
                        ele_three = (int) (1 + Math.random() * 9) * 100;  //整百
                    } else if (choose_num == 0) {
                        ele_two = (int) (1 + Math.random() * 9) * 100;  //整百
                        ele_three = (int) (1 + Math.random() * 9) * 10;  //整十
                    }
                    if ((ele_three + ele_two) % ele_one == 0) {  //表示是整除的情况才行
                        isfuhezhengshu = true;
                    }
                }
                problem.add((ele_three + ele_two) + "÷" + ele_one + "=");
                answer.add((ele_three + ele_two) / ele_one);
                break;
            case THREEDIVIDEONE:  //三位数除以一位数（最高位能被整除）
                ele_one = (int) (1 + Math.random() * 9);            //除数
                boolean isfuhesanzhengchu = false;
                while (isfuhesanzhengchu == false) {
                    ele_two = (int) (Math.random() * 900) + 100;  //得到三位数
                    int MaxHighWei = ele_two / 100;
                    if (MaxHighWei % ele_one == 0 && (ele_two % ele_one) == 0) {  //表示最高位能被整除
                        isfuhesanzhengchu = true;
                    }
                }
                is_middle = false;
                problem.add(ele_two + "÷" + ele_one + "=");
                Log.e("555555555555556", ele_two / ele_one + "");
                answer.add(ele_two / ele_one);
                break;
            case NOTHREEDIVIDEONE: //三位数除以一位数（最高位不能被整除）
                int ck = Math.random() > 0.5 ? 1 : 0;
                ele_one = (int) (1 + Math.random() * 9);            //除数
                while (true) {
                    ele_two = (int) (Math.random() * 900) + 100;  //得到三位数
                    int MaxHighWei = ele_two / 100;   //得到最高位
                    if (MaxHighWei % ele_one != 0 && (ele_two % ele_one) == 0) {  //表示最高位不能被整除
                        break;
                    }
                }
                Log.e("555555555555556", ck + "");
                if (ck == 1) {
                    is_middle = false;
                    problem.add(ele_two + "÷" + ele_one + "=");
                    Log.e("555555555555556", ele_two / ele_one + "");
                    answer.add(ele_two / ele_one);
                } else {
                    is_middle = true;
                    problem.add(ele_two + "÷" + "  " + "=" + ele_one);
                    Log.e("555555555555556", ele_two / ele_one + "");
                    answer.add(ele_two / ele_one);
                }
//				problem.add(ele_two+"÷"+ele_one+"=");
//				answer.add(ele_two/ele_one);
                break;
            case TWOMULTWO:        //两位数乘两位数（进位）
                int df = 1 + (int) (Math.random() * 9);
                int uk = 1 + (int) (Math.random() * 9);
                int pi = 1 + (int) (Math.random() * 9);
                int wr = 1 + (int) (Math.random() * 9);
                ele_one = df + uk * 10;
                ele_two = pi + wr * 10;
                while (((((df * pi) / 10) + ((uk * pi) % 10)) % 10) + ((wr * df) % 10) < 10) {
                    df = 1 + (int) (Math.random() * 9);
                    uk = 1 + (int) (Math.random() * 9);
                    pi = 1 + (int) (Math.random() * 9);
                    wr = 1 + (int) (Math.random() * 9);
                    ele_one = df + uk * 10;
                    ele_two = pi + wr * 10;
                }
                Log.e("555555555555556", ((((df * pi) / 10) + ((uk * pi) % 10)) % 10) + ((wr * df) % 10) + "");
                problem.add(ele_one + "×" + ele_two + "=");
                answer.add(ele_one * ele_two);
                break;
            //两位数乘以整十
            case TWOMULZHENGSHI:
                int first = (int) (1 + Math.random() * 9);
                int second = (int) (1 + Math.random() * 9);
                ele_one = first * 10 + second;  //第一位数
                ele_two = (int) (1 + Math.random() * 9) * 10; //整十数
                problem.add(ele_one + "×" + ele_two + "=");
                answer.add(ele_one * ele_two);
                break;
            //两位数乘以两位数
            case TWOMULTWOSHU:
                if (Math.random() * 9 > 5) {   //要么十位是1至3
                    int one1 = (int) (1 + Math.random() * 3);
                    int two1 = (int) (1 + Math.random() * 9);
                    ele_one = one1 * 10 + two1;  //第一位数
                } else {   //要么整数
                    int one1 = (int) (1 + Math.random() * 9);
                    ele_one = one1 * 10;  //第一位数
                }
                int one2 = (int) (1 + Math.random() * 5);
                int two2 = (int) (1 + Math.random() * 9);
                ele_two = one2 * 10 + two2;  //第二位数
                problem.add(ele_one + "×" + ele_two + "=");
                answer.add(ele_one * ele_two);
                break;
            //两位数除以一位数(整除)
            case TWODIVIDEONE:
                while (true) {
                    ele_one = (int) (11 + (Math.random() * 89)); //生成一个两位数
                    //随机出一位数能被两位数整除的数
                    int zhengchunumber = getZhengChuNumber(ele_one);
                    if (zhengchunumber!=-1 ) {
                        problem.add(ele_one + "÷" + zhengchunumber + "=");
                        answer.add(ele_one / zhengchunumber);
                        break;
                    }
                }
                break;
            default:
                break;
        }
    }

    //找到输入数据能够被整除的数
    private int getZhengChuNumber(int ele_one) {
        List<Integer> shuzu = new ArrayList<Integer>();  //存储数据
        for (int i = 2; i < Math.sqrt(ele_one); i++) {
            if (ele_one % i == 0) {
                shuzu.add(i);
            }
        }
        //随机出一个数
        if (shuzu.size() == 0) {  //表示没有能整除的数
            return -1;
        } else {
            int result = shuzu.get((int) (Math.random() * shuzu.size()));  //在规定的长度随机一个数
            return result;
        }
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
    public void addStrElement(int d, int e) {
        String str = String.valueOf((d * 1.0 / (e * 1.0)));
        int index = str.indexOf(".");
        if (d % e != 0) {
            is_float = true;
            problem1.add(d + "÷" + e + "=");
            if (index == 1 && str.length() == 3)
                answer1.add(str.substring(0, 3));
            else if (index == 1 && str.length() >= 4) {
                answer1.add(str.substring(0, 4));
            } else if (index == 2 && str.length() == 4)
                answer1.add(str.substring(0, 4));
            else if (index == 2 && str.length() >= 5)
                answer1.add(str.substring(0, 5));
            else if (index == 3 && str.length() == 5)
                answer1.add(str.substring(0, 5));
            else if (index == 3 && str.length() >= 6)
                answer1.add(str.substring(0, 6));
        } else {
            answer.add((int) d / (int) e);
            problem.add(d + "÷" + e + "=");
            is_float = false;
            is_middle = false;
        }
    }


    public void run() {
        problem = new ArrayList<String>();
        answer = new ArrayList<Integer>();
        while (!stopThread) {
            is_float = false;
            CreateSubject();
            Message message = new Message();
            Bundle bundle = new Bundle();
            if ((!is_float) && (!is_middle)) {
                bundle.putString("problem", problem.get(count));
                bundle.putString("answer", answer.get(count)+"");
                message.setData(bundle);
                handler_program.sendMessage(message);
                count++;
            }
            if ((!is_float) && (is_middle)) {
                bundle.putString("problem", problem.get(count));
                bundle.putString("answer", answer.get(count)+"");
                bundle.putBoolean("is_float", false);
                bundle.putBoolean("is_middle", true);
                message.setData(bundle);
                handler_program.sendMessage(message);
                count++;
            }
            if ((is_float) && (!is_middle)) {
                bundle.putString("problem", problem1.get(count_float));
                bundle.putString("answer", answer1.get(count_float));
                bundle.putBoolean("is_float", true);
                bundle.getBoolean("is_middle", false);
                message.setData(bundle);
                handler_program.sendMessage(message);
                count_float++;
            }
//			if ((is_float)&&(is_middle)){
//				bundle.putString("problem",problem1.get(count_float));
//				bundle.putString("answer", answer1.get(count_float));
//				bundle.putBoolean("is_float",true);
//				bundle.getBoolean("is_middle",true);
//				message.setData(bundle);
//				handler_program.sendMessage(message);
//				count_float++;
//			}
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

