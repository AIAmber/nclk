package cn.com.sparknet.common.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间戳
 * @author chenxy
 * 
 */
public final class TimeStampUtil {
	
	private TimeStampUtil(){
	}
	
	/**
	 * 获取时间戳
	 * @return
	 */
	public static String getTimeStamp() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssms");
		return sdf.format(new Date());
	}

	/**
	 * 获取带格式时间戳
	 * @return
	 */
	public static String getFormatTimeStamp() {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(1);
		int month = 1 + cal.get(2);
		int day = cal.get(5);
		int hour = cal.get(11);
		int minute = cal.get(12);
		int second = cal.get(13);
		int milliSecond = cal.get(14);
		return year + "-" + 
		(month<10?"0"+month:month) + "-" + 
		(day<10?"0"+day:day) + " " + 
		(hour<10?"0"+hour:hour) + ":" + 
		(minute<10?"0"+minute:minute) + ":" + 
		(second<10?"0"+second:second) + "." + 
		milliSecond;
	}
	
}
