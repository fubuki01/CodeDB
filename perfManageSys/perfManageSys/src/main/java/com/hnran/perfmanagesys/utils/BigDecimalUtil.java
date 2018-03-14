package com.hnran.perfmanagesys.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class BigDecimalUtil {
	
	public static String formatBigDecimal(BigDecimal d, String format){
		if(d == null ) return "";
		DecimalFormat df = new DecimalFormat(format);
		return df.format(d);
	}
	
	/**
	 * 
	 * @param d
	 * @return
	 */
	public static String formatBigDecimal(BigDecimal d){
		return formatBigDecimal(d, "0.000000");
	}
	
	/**
	 * 返回两位小数
	 * @param d
	 * @return
	 */
	public static String formatBigDecimalTwo(BigDecimal d){
		return formatBigDecimal(d, "0.00");
	}

}
