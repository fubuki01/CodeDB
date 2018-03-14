package com.readboy.mentalcalculation.other;

import com.readboy.mentalcalculation.R;
import com.readboy.mentalcalculation.game.MyActivity.GameActivity;
import com.readboy.mentalcalculation.game.MyActivity.GameActivity_fen;
import com.readboy.mentalcalculation.game.MyActivity.GameActivity_fraction_integer;
import com.readboy.mentalcalculation.game.MyActivity.GameActivity_fraction_pre;
import com.readboy.mentalcalculation.game.MyActivity.GameActivity_yu;
import com.readboy.mentalcalculation.game.SupplyThread.Supply_Grade_1_down;
import com.readboy.mentalcalculation.game.SupplyThread.Supply_Grade_1_top;
import com.readboy.mentalcalculation.game.SupplyThread.Supply_Grade_2_down;
import com.readboy.mentalcalculation.game.SupplyThread.Supply_Grade_2_top;
import com.readboy.mentalcalculation.game.SupplyThread.Supply_Grade_3_down;
import com.readboy.mentalcalculation.game.SupplyThread.Supply_Grade_3_top;
import com.readboy.mentalcalculation.game.SupplyThread.Supply_Grade_4_down;
import com.readboy.mentalcalculation.game.SupplyThread.Supply_Grade_4_top;
import com.readboy.mentalcalculation.game.SupplyThread.Supply_Grade_5_down;
import com.readboy.mentalcalculation.game.SupplyThread.Supply_Grade_5_top;
import com.readboy.mentalcalculation.game.SupplyThread.Supply_Grade_6_down;
import com.readboy.mentalcalculation.game.SupplyThread.Supply_Grade_6_top;

import java.util.HashMap;

public class myIndex {

    class value {

        Class activity;
        int layout;
        Class thread;
        int type;

        value(Class activity, int layout, Class thread, int type) {
            this.activity = activity;
            this.layout = layout;
            this.thread = thread;
            this.type = type;
        }
    }

    private HashMap<String,value> map = new HashMap<String,value>();

    private static myIndex index = new myIndex();

    private myIndex() {
        map.put("1~5的加法", new value(GameActivity.class,R.layout.activity_gameview_integer_decimal,Supply_Grade_1_top.class,1));
        map.put("1~5的减法", new value(GameActivity.class,R.layout.activity_gameview_integer_decimal,Supply_Grade_1_top.class,2));
        map.put("6、7的加减法", new value(GameActivity.class,R.layout.activity_gameview_integer_decimal,Supply_Grade_1_top.class,3));
        map.put("8、9的加减法", new value(GameActivity.class,R.layout.activity_gameview_integer_decimal,Supply_Grade_1_top.class,4));
        map.put("10的加减法", new value(GameActivity.class,R.layout.activity_gameview_integer_decimal,Supply_Grade_1_top.class,5));
        map.put("10以内的连加", new value(GameActivity.class,R.layout.activity_gameview_integer_decimal,Supply_Grade_1_top.class,6));
        map.put("10以内的连减", new value(GameActivity.class,R.layout.activity_gameview_integer_decimal,Supply_Grade_1_top.class,7));
        map.put("10以内的加减混合运算", new value(GameActivity.class,R.layout.activity_gameview_integer_decimal,Supply_Grade_1_top.class,8));
        map.put("2、3、4、5加几的进位加法", new value(GameActivity.class,R.layout.activity_gameview_integer_decimal,Supply_Grade_1_top.class,9));
        map.put("6、7、8加几的进位加法", new value(GameActivity.class,R.layout.activity_gameview_integer_decimal,Supply_Grade_1_top.class,10));
        map.put("9加几的进位加法", new value(GameActivity.class,R.layout.activity_gameview_integer_decimal,Supply_Grade_1_top.class,11));
        map.put("十几减9的退位减法", new value(GameActivity.class,R.layout.activity_gameview_integer_decimal,Supply_Grade_1_down.class,1));
        map.put("十几减几的退位减法", new value(GameActivity.class,R.layout.activity_gameview_integer_decimal,Supply_Grade_1_down.class,2));
        map.put("20以内的加减混合运算", new value(GameActivity.class,R.layout.activity_gameview_integer_decimal,Supply_Grade_1_down.class,3));
        map.put("整十数加整十数", new value(GameActivity.class,R.layout.activity_gameview_integer_decimal,Supply_Grade_1_down.class,4));
        map.put("整十数减整十数", new value(GameActivity.class,R.layout.activity_gameview_integer_decimal,Supply_Grade_1_down.class,5));
        map.put("两位数与一位数的不进位加法", new value(GameActivity.class,R.layout.activity_gameview_integer_decimal,Supply_Grade_1_down.class,6));
        map.put("两位数与一位数的进位加法", new value(GameActivity.class,R.layout.activity_gameview_integer_decimal,Supply_Grade_1_down.class,7));
        map.put("两位数与一位数的不退位减法", new value(GameActivity.class,R.layout.activity_gameview_integer_decimal,Supply_Grade_1_down.class,8));
        map.put("两位数与一位数的退位减法", new value(GameActivity.class,R.layout.activity_gameview_integer_decimal,Supply_Grade_1_down.class,9));
        map.put("两位数加整十数", new value(GameActivity.class,R.layout.activity_gameview_integer_decimal,Supply_Grade_1_down.class,10));
        map.put("两位数与两位数的不进位加法", new value(GameActivity.class,R.layout.activity_gameview_integer_decimal,Supply_Grade_1_down.class,11));
        map.put("两位数与两位数的不退位减法", new value(GameActivity.class,R.layout.activity_gameview_integer_decimal,Supply_Grade_1_down.class,12));
        map.put("两位数与两位数的进位加法", new value(GameActivity.class,R.layout.activity_gameview_integer_decimal,Supply_Grade_2_top.class,1));
        map.put("两位数与两位数的退位减法", new value(GameActivity.class,R.layout.activity_gameview_integer_decimal,Supply_Grade_2_top.class,2));
        map.put("100以内的连加", new value(GameActivity.class,R.layout.activity_gameview_integer_decimal,Supply_Grade_2_top.class,3));
        map.put("100以内的连减", new value(GameActivity.class,R.layout.activity_gameview_integer_decimal,Supply_Grade_2_top.class,4));
        map.put("100以内的加减混合运算", new value(GameActivity.class,R.layout.activity_gameview_integer_decimal,Supply_Grade_2_top.class,5));
        map.put("2的乘法口诀", new value(GameActivity.class,R.layout.activity_gameview_integer_decimal,Supply_Grade_2_top.class,6));
        map.put("3的乘法口诀", new value(GameActivity.class,R.layout.activity_gameview_integer_decimal,Supply_Grade_2_top.class,7));
        map.put("4的乘法口诀", new value(GameActivity.class,R.layout.activity_gameview_integer_decimal,Supply_Grade_2_top.class,8));
        map.put("5的乘法口诀", new value(GameActivity.class,R.layout.activity_gameview_integer_decimal,Supply_Grade_2_top.class,9));
        map.put("乘加和乘减的计算", new value(GameActivity.class,R.layout.activity_gameview_integer_decimal,Supply_Grade_2_top.class,10));
        map.put("6的乘法口诀", new value(GameActivity.class,R.layout.activity_gameview_integer_decimal,Supply_Grade_2_top.class,11));
        map.put("7的乘法口诀", new value(GameActivity.class,R.layout.activity_gameview_integer_decimal,Supply_Grade_2_top.class,12));
        map.put("8的乘法口诀", new value(GameActivity.class,R.layout.activity_gameview_integer_decimal,Supply_Grade_2_top.class,13));
        map.put("9的乘法口诀", new value(GameActivity.class,R.layout.activity_gameview_integer_decimal,Supply_Grade_2_top.class,14));
        map.put("用2~6的乘法口诀求商", new value(GameActivity.class,R.layout.activity_gameview_integer_decimal,Supply_Grade_2_down.class,1));
        map.put("用7~8的乘法口诀求商", new value(GameActivity.class,R.layout.activity_gameview_integer_decimal,Supply_Grade_2_down.class,2));
        map.put("用9的乘法口诀求商", new value(GameActivity.class,R.layout.activity_gameview_integer_decimal,Supply_Grade_2_down.class,3));
        map.put("乘除混合运算", new value(GameActivity.class,R.layout.activity_gameview_integer_decimal,Supply_Grade_2_down.class,4));
        map.put("有余数的除法", new value(GameActivity_yu.class,R.layout.activity_gameview_integer_yu,Supply_Grade_2_down.class,5));
        map.put("两位数加两位数", new value(GameActivity.class,R.layout.activity_gameview_integer_decimal,Supply_Grade_2_down.class,6));
        map.put("两位数减两位数", new value(GameActivity.class,R.layout.activity_gameview_integer_decimal,Supply_Grade_2_down.class,7));
        map.put("三位数减两位数", new value(GameActivity.class,R.layout.activity_gameview_integer_decimal,Supply_Grade_2_down.class,8));
        map.put("三位数加两位数", new value(GameActivity.class,R.layout.activity_gameview_integer_decimal,Supply_Grade_2_down.class,9));
        map.put("两位数加两位数的连续进位加法", new value(GameActivity.class,R.layout.activity_gameview_integer_decimal,Supply_Grade_3_top.class,1));
        map.put("三位数减三位数的连续退位减法", new value(GameActivity.class,R.layout.activity_gameview_integer_decimal,Supply_Grade_3_top.class,2));
        map.put("三位数连续进位加", new value(GameActivity.class,R.layout.activity_gameview_integer_decimal,Supply_Grade_3_top.class,3));
        map.put("三位数加三位数", new value(GameActivity.class,R.layout.activity_gameview_integer_decimal,Supply_Grade_3_top.class,4));
        map.put("三位数减三位数", new value(GameActivity.class,R.layout.activity_gameview_integer_decimal,Supply_Grade_3_top.class,5));
        map.put("两位数乘一位数的不进位乘法", new value(GameActivity.class,R.layout.activity_gameview_integer_decimal,Supply_Grade_3_top.class,6));
        map.put("两位数乘一位数的进位乘法", new value(GameActivity.class,R.layout.activity_gameview_integer_decimal,Supply_Grade_3_top.class,7));
        map.put("三位数乘一位数", new value(GameActivity.class,R.layout.activity_gameview_integer_decimal,Supply_Grade_3_top.class,8));
        map.put("末尾有0的三位数乘以一位数", new value(GameActivity.class,R.layout.activity_gameview_integer_decimal,Supply_Grade_3_top.class,9));
        map.put("中间带0的三位数乘以一位数", new value(GameActivity.class,R.layout.activity_gameview_integer_decimal,Supply_Grade_3_top.class,10));
        map.put("同分母分数加法", new value(GameActivity_fen.class,R.layout.activity_gameview_fraction,Supply_Grade_3_top.class,11));
        map.put("同分母分数减法", new value(GameActivity_fen.class,R.layout.activity_gameview_fraction,Supply_Grade_3_top.class,12));
        map.put("整十、整百、整千除以一位数", new value(GameActivity.class,R.layout.activity_gameview_integer_decimal,Supply_Grade_3_down.class,1));
        map.put("几百几十、几千几百除以一位数", new value(GameActivity.class,R.layout.activity_gameview_integer_decimal,Supply_Grade_3_down.class,2));
        map.put("两位数除以一位数", new value(GameActivity.class,R.layout.activity_gameview_integer_decimal,Supply_Grade_3_down.class,3));
        map.put("三位数除以一位数", new value(GameActivity.class,R.layout.activity_gameview_integer_decimal,Supply_Grade_3_down.class,4));
        map.put("商中间有0的除法（商有余数）", new value(GameActivity_yu.class,R.layout.activity_gameview_integer_yu,Supply_Grade_3_down.class,5));
        map.put("商末尾有0的除法（商有余数）", new value(GameActivity_yu.class,R.layout.activity_gameview_integer_yu,Supply_Grade_3_down.class,6));
        map.put("商中间有0的除法（商没有余数）", new value(GameActivity.class,R.layout.activity_gameview_integer_decimal,Supply_Grade_3_down.class,7));
        map.put("商末尾有0的除法（商没有余数）", new value(GameActivity.class,R.layout.activity_gameview_integer_decimal,Supply_Grade_3_down.class,8));
        map.put("两位数乘整十数", new value(GameActivity.class,R.layout.activity_gameview_integer_decimal,Supply_Grade_3_down.class,9));
        map.put("两位数乘两位数", new value(GameActivity.class,R.layout.activity_gameview_integer_decimal,Supply_Grade_3_down.class,10));
        map.put("三位数乘以两位数", new value(GameActivity.class,R.layout.activity_gameview_integer_decimal,Supply_Grade_4_top.class,1));
        map.put("整十数除以整十数", new value(GameActivity.class,R.layout.activity_gameview_integer_decimal,Supply_Grade_4_top.class,2));
        map.put("几百几十除以整十数", new value(GameActivity.class,R.layout.activity_gameview_integer_decimal,Supply_Grade_4_top.class,3));
        map.put("两位数除以两位数", new value(GameActivity.class,R.layout.activity_gameview_integer_decimal,Supply_Grade_4_top.class,4));
        map.put("三位数除以两位数", new value(GameActivity.class,R.layout.activity_gameview_integer_decimal,Supply_Grade_4_top.class,5));
        map.put("整数四则混合运算", new value(GameActivity.class,R.layout.activity_gameview_integer_fouroperation,Supply_Grade_4_top.class,6));
        map.put("加法结合律", new value(GameActivity.class,R.layout.activity_gameview_integer_decimal,Supply_Grade_4_down.class,1));
        map.put("乘法结合律", new value(GameActivity.class,R.layout.activity_gameview_integer_decimal,Supply_Grade_4_down.class,2));
        map.put("乘法分配律", new value(GameActivity.class,R.layout.activity_gameview_integer_decimal,Supply_Grade_4_down.class,3));
        map.put("连减的简便运算", new value(GameActivity.class,R.layout.activity_gameview_integer_decimal,Supply_Grade_4_down.class,4));
        map.put("小数加法", new value(GameActivity.class,R.layout.activity_gameview_integer_decimal,Supply_Grade_4_down.class,5));
        map.put("小数减法", new value(GameActivity.class,R.layout.activity_gameview_integer_decimal,Supply_Grade_4_down.class,6));
        map.put("小数的加减混合", new value(GameActivity.class,R.layout.activity_gameview_integer_decimal,Supply_Grade_4_down.class,7));
        map.put("小数乘整数", new value(GameActivity.class,R.layout.activity_gameview_integer_decimal,Supply_Grade_5_top.class,1));
        map.put("小数乘小数", new value(GameActivity.class,R.layout.activity_gameview_integer_decimal,Supply_Grade_5_top.class,2));
        map.put("小数乘加", new value(GameActivity.class,R.layout.activity_gameview_integer_decimal,Supply_Grade_5_top.class,3));
        map.put("小数乘减", new value(GameActivity.class,R.layout.activity_gameview_integer_decimal,Supply_Grade_5_top.class,4));
        map.put("小数除以整数", new value(GameActivity.class,R.layout.activity_gameview_integer_decimal,Supply_Grade_5_top.class,5));
        map.put("小数除以小数", new value(GameActivity.class,R.layout.activity_gameview_integer_decimal,Supply_Grade_5_top.class,6));
        map.put("整数除以小数", new value(GameActivity.class,R.layout.activity_gameview_integer_decimal,Supply_Grade_5_top.class,7));
        map.put("整数除以整数，商是小数", new value(GameActivity.class,R.layout.activity_gameview_integer_decimal,Supply_Grade_5_top.class,8));
        map.put("求两个数的最大公因数", new value(GameActivity.class,R.layout.activity_gameview_integer_decimal,Supply_Grade_5_down.class,1));
        map.put("求两个数的最小公倍数", new value(GameActivity.class,R.layout.activity_gameview_integer_decimal,Supply_Grade_5_down.class,2));
        map.put("分数与小数互化", new value(GameActivity_fraction_pre.class,R.layout.activity_gameview_fraction_pre,Supply_Grade_5_down.class,3));
        map.put("同分母分数加法", new value(GameActivity_fen.class,R.layout.activity_gameview_fraction,Supply_Grade_5_down.class,4));
        map.put("同分母分数减法", new value(GameActivity_fen.class,R.layout.activity_gameview_fraction,Supply_Grade_5_down.class,5));
        map.put("同分母分数连加、连减", new value(GameActivity_fen.class,R.layout.activity_gameview_fraction,Supply_Grade_5_down.class,6));
        map.put("异分母分数加减", new value(GameActivity_fen.class,R.layout.activity_gameview_fraction,Supply_Grade_5_down.class,7));
        map.put("分数加减混合运算", new value(GameActivity_fen.class,R.layout.activity_gameview_fraction,Supply_Grade_5_down.class,8));
        map.put("分数乘以整数", new value(GameActivity_fraction_integer.class,R.layout.activity_gameview_fraction_integer,Supply_Grade_6_top.class,1));
        map.put("分数乘以分数", new value(GameActivity_fen.class,R.layout.activity_gameview_fraction,Supply_Grade_6_top.class,2));
        map.put("分数除以整数", new value(GameActivity_fraction_integer.class,R.layout.activity_gameview_fraction_integer,Supply_Grade_6_top.class,3));
        map.put("一个数除以分数", new value(GameActivity_fraction_integer.class,R.layout.activity_gameview_integer_fraction,Supply_Grade_6_top.class,4));
        map.put("小数与百分数互化", new value(GameActivity.class,R.layout.activity_gameview_integer_decimal,Supply_Grade_6_down.class,1));
        map.put("分数与百分数互化", new value(GameActivity_fraction_pre.class,R.layout.activity_gameview_fraction_pre,Supply_Grade_6_down.class,2));
        map.put("百分数的计算", new value(GameActivity.class,R.layout.activity_gameview_integer_decimal,Supply_Grade_6_down.class,3));
    }

    public static myIndex getIndex() {
        return index;
    }

    public Class getActivity(String name) {
        return map.get(name).activity;
    }

    public int getLayout(String name) {
        return map.get(name).layout;
    }

    public Class getThread(String name) {
        return map.get(name).thread;
    }

    public int gettype(String type) {
        return map.get(type).type;
    }
}