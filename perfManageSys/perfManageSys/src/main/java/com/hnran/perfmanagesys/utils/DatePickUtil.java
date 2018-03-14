package com.hnran.perfmanagesys.utils;

import java.text.DecimalFormat;
import java.util.Calendar;

import com.hnran.perfmanagesys.activity.warning.WarningCommonActivity;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.os.Handler;
import android.widget.DatePicker;
import android.widget.TextView;

public class DatePickUtil {

	private static DecimalFormat df=new DecimalFormat("00");
	public static void showDatePickDlg(final Context cnt, final TextView tv, final boolean isNotCompare, final Handler handler) {  
		Calendar calendar = Calendar.getInstance();  
		DatePickerDialog datePickerDialog = new DatePickerDialog(cnt, new OnDateSetListener() {  
			@Override  
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {  
				//                MainActivity.this.mEditText.setText(year + "-" + monthOfYear + "-" + dayOfMonth);  

				/**
				 * 判断时间是否大于当前
				 */
				String date = year + "-" + df.format(monthOfYear+1) + "-" + df.format(dayOfMonth);
				if(isNotCompare || DateUtil.compareDate(date)){
					tv.setText(date);  
					
//					if(handler != null) handler.sendEmptyMessage(CHANGE_YEAR_MONTH);
					
				}else{
					ToastUtil.showToast(cnt, "预约时间必须大于当前日期");
				}

			}  
		}, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));  
		datePickerDialog.show();  
	}  


	public static void showDatePickDlgYearAndMonth(final Context cnt, final TextView tv, final Handler handler) {  
		Calendar calendar = Calendar.getInstance();  
		DatePickerDialog datePickerDialog = new DatePickerDialog(cnt, new OnDateSetListener() {  
			@Override  
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {  
				//	                MainActivity.this.mEditText.setText(year + "-" + monthOfYear + "-" + dayOfMonth);  

				String date = year + "-" + df.format(monthOfYear+1);
				
				tv.setText(date); 
				
//				handler.sendEmptyMessage(CHANGE_YEAR);
				
			}  
		}, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));  
		datePickerDialog.show();  

	}  
}
