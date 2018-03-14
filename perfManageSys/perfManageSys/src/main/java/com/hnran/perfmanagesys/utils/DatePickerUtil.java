package com.hnran.perfmanagesys.utils;

import java.util.Calendar;


import luojie.hnulab.librarydemo.picker.DatePicker;

import com.hnran.perfmanagesys.view.CustomHeaderAndFooterPicker;

import android.app.Activity;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class DatePickerUtil {

	public final static int CHANGE_YEAR_MONTH_DAY = 40000;
	public final static int CHANGE_YEAR_MONTH = 40001;
	public final static int CHANGE_YEAR = 40002;

	// 年月日选择器
	public static void showYearMonthDayPicker(Activity activity, final View view, boolean is, final Handler handler) {
		final CustomHeaderAndFooterPicker picker = new CustomHeaderAndFooterPicker(activity, DatePicker.YEAR_MONTH_DAY);
		//设置弹框居中
		picker.setGravity(Gravity.CENTER);
		//设置触摸关闭
		picker.setCanceledOnTouchOutside(true);
		//设置结宽度
		picker.setUseWeight(true);
		//设置结束时间
		picker.setRangeEnd(2030, 12, 31);

		//设置滚轮条目数量（上下各2个） 默认是3
		picker.setOffset(2);
		//设置滚轮左右间隔
		picker.setPadding(19);
		//设置滚轮上下间隔
		picker.setLineSpaceMultiplier(2.2f);
		//        //设置位置在View下
		//        picker.setDisplayPosition(findViewById(R.id.yearmonthday),getTitleBarHeight());


		//设置开始时间
		Calendar cale = Calendar.getInstance();
		int startY = 2010, startM = 1, startD = 1;
		if(is){		//选择预约日期，需要大于当前日期
			cale.add(Calendar.DAY_OF_YEAR, 1); 
			startY = cale.get(Calendar.YEAR);
			startM = cale.get(Calendar.MONTH) + 1;
			startD = cale.get(Calendar.DAY_OF_MONTH);
			picker.setRangeStart(startY, startM, startD);
		}else{
			picker.setRangeStart(startY, startM, startD);
			//设置当前时间
			picker.setSelectedItem(cale.get(Calendar.YEAR), cale.get(Calendar.MONTH) + 1, cale.get(Calendar.DAY_OF_MONTH));
		}

		//选择监听
		picker.setOnDatePickListener(new CustomHeaderAndFooterPicker.OnYearMonthDayPickListener() {
			@Override
			public void onDatePicked(String year, String month, String day) {
				if(view instanceof TextView){
					((TextView)view).setText(year + "-" + month + "-" + day);
				}else if(view instanceof EditText){
					((EditText)view).setText(year + "-" + month + "-" + day);
				}

				if(handler != null) handler.sendEmptyMessage(CHANGE_YEAR_MONTH_DAY);
			}
		});

		picker.show();
	}

	// 年月选择器
	public static void showYearMonthPicker(Activity activity, final View view, final Handler handler) {
		CustomHeaderAndFooterPicker picker = new CustomHeaderAndFooterPicker(activity, DatePicker.YEAR_MONTH);
		//弹框居中
		picker.setGravity(Gravity.CENTER);
		//设置宽度
		picker.setWidth((int) (picker.getScreenWidthPixels() * 0.6));
		//设置开始时间
		picker.setRangeStart(2010, 1, 1);
		//设置结束时间
		picker.setRangeEnd(2030, 12, 31);
		//选择当前时间
		Calendar cale = Calendar.getInstance();
		picker.setSelectedItem(cale.get(Calendar.YEAR), cale.get(Calendar.MONTH)+1);
		//选择监听
		picker.setOnDatePickListener(new CustomHeaderAndFooterPicker.OnYearMonthPickListener() {
			@Override
			public void onDatePicked(String year, String month) {
				if(view instanceof TextView){
					((TextView)view).setText(year + "-" + month);
				}else if(view instanceof EditText){
					((EditText)view).setText(year + "-" + month);
				}
				if(handler != null) handler.sendEmptyMessage(CHANGE_YEAR_MONTH);
			}
		});
		picker.show();
	}

	//年份选择器
	public static void showYearPicker(Activity activity, final View view, final Handler handler) {

		CustomHeaderAndFooterPicker picker = new CustomHeaderAndFooterPicker(activity, DatePicker.YEAR);

		//弹框居中
		picker.setGravity(Gravity.CENTER);
		//设置开始时间
		picker.setRangeStart(2010);
		//设置结束时间
		picker.setRangeEnd(2030);
		//选择当前时间
		Calendar cale = Calendar.getInstance();
		picker.setSelectedItem(cale.get(Calendar.YEAR));

		picker.setPadding(50);
		picker.setOnDatePickListener(new DatePicker.OnYearPickListener() {
			@Override
			public void onDatePicked(String year) {
				if(view instanceof TextView){
					((TextView)view).setText(year + "");
				}else if(view instanceof EditText){
					((EditText)view).setText(year + "");
				}
				if(handler != null) handler.sendEmptyMessage(CHANGE_YEAR);
			}
		});
		picker.show();

	}

}
