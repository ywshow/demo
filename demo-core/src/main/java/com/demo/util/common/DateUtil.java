package com.demo.util.common;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

/**
 * 日期时间工具类
 * Calendar的月份是从0开始的
 * 
 */
public class DateUtil {
	
	private static String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";

	public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd hh:mm:ss";
	
	private static long ND = 1000*24*60*60;//一天的毫秒数

	/**
	 * 获得默认的 date pattern
	 */
	public static String getDatePattern() {
		return DEFAULT_DATE_PATTERN;
	}
	
	/**
	 * 获得日期时间pattern
	 * @return
	 */
	public static String getDateTimePattern() {
		return YYYY_MM_DD_HH_MM_SS;
	}

	/**
	 * 返回预设Format的当前日期字符串
	 */
	public static String getToday() {
		final Date today = new Date();
		return format(today);
	}

	/**
	 * 根据传入的模式参数返回当天的日期
	 * 
	 * @param pattern
	 *            传入的模式
	 * @return 按传入的模式返回一个字符串
	 */
	public static String getToday(final String pattern) {
		if (!StringUtils.isNotEmpty(pattern)) {
			return getToday();
		}
		final Date date = new Date();
		return format(date, pattern);
	}

	/**
	 * 使用预设Format格式化Date成字符串
	 */
	public static String format(final Date date) {
		return date == null ? "" : format(date, getDatePattern());
	}

	/**
	 * 使用参数Format格式化Date成字符串
	 */
	public static String format(final Date date, final String pattern) {
		return date == null ? "" : new SimpleDateFormat(pattern).format(date);
	}

	/**
	 * 使用预设格式将字符串转为Date
	 */
	public static Date parse(final String strDate) {
		return !StringUtils.isNotEmpty(strDate) ? null : parse(strDate,
				getDatePattern());
	}

	/**
	 * 使用参数Format将字符串转为Date
	 */
	public static Date parse(final String strDate, final String pattern) {
		try {
			return !StringUtils.isNotEmpty(strDate) ? null
					: new SimpleDateFormat(pattern).parse(strDate);
		} catch (final ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 在日期上增加数个小时
	 */
	public static Date addHOUR(final Date date, final int n) {
		final Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR, n);
		return cal.getTime();
	}

	/**
	 * 在日期上增加数分钟
	 */
	public static Date addMinutes(final Date date, final int n) {
		final Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MINUTE, n);
		return cal.getTime();
	}
	
	/**
	 * 在日期上增加天数
	 */
	public static Date addDate(final Date date, final int n) {
		final Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, n);
		return cal.getTime();
	}
	
	/**
	 * 在日期上增加数月
	 */
	public static Date addMonth(final Date date, final int n) {
		final Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, n);
		return cal.getTime();
	}

	/**
	 * 获取某时间的中文星期（如：星期一、星期二），每星期的第一天是星期日
	 * 
	 * @param date：日期
	 * @return
	 */
	public static String getWeekCS(final Date date) {
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		final String[] week = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		return week[calendar.get(Calendar.DAY_OF_WEEK) - 1];
	}

	/**
	 * 获取两个日期之间所差的天数
	 * 
	 * @param from：开始日期
	 * @param to：结束日期
	 * @return：所差的天数(非负整数)
	 */
	public static int daysBetween(final Date from, final Date to) {
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(from);
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		final Date fromDate = calendar.getTime();

		calendar.setTime(to);
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		final Date toDate = calendar.getTime();
		final int diff = Math
				.abs((int) ((fromDate.getTime() - toDate.getTime()) / (24 * 3600 * 1000)));
		return diff;
	}

	/**
	 * 获取date前或后nDay天的日期
	 * 
	 * @param date ：开始日期
	 * @param nDay ：天数
	 * @param type：正为后nDay天的日期，否则为前nDay天的日期。只能为1或-1
	 * @return
	 */
	public static Date getDate(final Date date, final int nDay, final int type) {
		if ((type != 1) && (type != -1)) {
			throw new IllegalArgumentException("type的值只能为1或-1");
		}
		long millisecond = date.getTime(); // 从1970年1月1日00:00:00开始算起所经过的微秒数
		final long msel = nDay * 24 * 3600 * 1000l; // 获取nDay天总的毫秒数
		millisecond = millisecond + ((type > 0) ? msel : (-msel));
		final Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millisecond);
		return calendar.getTime();
	}

	/**
	 * 获取n月之前或之后的日期
	 * 
	 * @param date
	 * @param nMonth
	 * @param type (只能为-1或1)
	 * @return
	 */
	public static Date addDateMonth(final Date date, final int nMonth, final int type) {
		if ((type != 1) && (type != -1)) {
			throw new IllegalArgumentException("type的值只能为1或-1");
		}
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		final int nYear = nMonth / 12;
		final int month = nMonth % 12;
		calendar.add(Calendar.YEAR, nYear * type);
		final Date desDate = calendar.getTime();
		calendar.add(Calendar.MONTH, month * type);
		if (type < 0) {
			while (!calendar.getTime().before(desDate)) {
				calendar.add(Calendar.YEAR, type);
			}
		} else {
			while (!calendar.getTime().after(desDate)) {
				calendar.add(Calendar.YEAR, type);
			}
		}
		return calendar.getTime();
	}


	/**
	 * 创建日期date
	 * 
	 * @param year ：年
	 * @param month ：月
	 * @param day ：日
	 * @return
	 */
	public static Date createDate(final int year, final int month, final int day) {
		if((1>month)||(month>=12) || (day<1) || (day >31)){
			throw new IllegalArgumentException("month的值在1和12之间，day的值在1和31之间");
		}
		final Calendar calendar = Calendar.getInstance();
		calendar.set(year, month-1, day);
		return calendar.getTime();
	}

	public static Timestamp getSqlTimestamp(final Date dateValue) {
		try {
			if ((dateValue == null) || ("".equals(dateValue))) {
				return null;
			}
			final Timestamp newDate = new Timestamp(dateValue.getTime());
			return newDate;
		} catch (final Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	/**
     * 以友好的方式显示时间
     * @param time
     * @return
     */
    public static String friendlyTime(final Date time) {
        //获取time距离当前的秒数
        final int ct = (int)((System.currentTimeMillis() - time.getTime())/1000);
        if(ct == 0){
            return "刚刚";
        }
        if((ct > 0) && (ct < 60)) {
            return ct + "秒前";
        }
        if((ct >= 60) && (ct < 3600)){
            return Math.max(ct / 60,1) + "分钟前";
        }
        if((ct >= 3600) && (ct < 86400)){
			return (ct / 3600) + "小时前";
		}
        if((ct >= 86400) && (ct < 2592000)){ //86400 * 30
            final int day = ct / 86400 ;           
            return day + "天前";
        }
        if((ct >= 2592000) && (ct < 31104000)) { //86400 * 30
            return (ct / 2592000) + "月前";
        }
        return (ct / 31104000) + "年前";
    }
    
    /**
     * 得到昨天的开始时间和结束时间
     * @return 返回一个数组，第一个是开始时间，第二个是结束时间
     */
    public static Date[] getYesterdayStartTimeAndEndTime(){
    	try {
    		final Date[] arrData = new Date[2];
			final Calendar ca = Calendar.getInstance();
			ca.add(Calendar.DATE, -1);
			final SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_PATTERN);
			final SimpleDateFormat sdf2 = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
			final Date startDate = sdf2.parse(sdf.format(ca.getTime()) + " 00:00:00");
			final Date endDate = sdf2.parse(sdf.format(ca.getTime()) + " 23:59:59");
			arrData[0] = startDate;
			arrData[1] = endDate;
			return arrData;
		} catch (final ParseException e) {
			e.printStackTrace();
		}
    	return null;
    }
    
    /**
     * 得到昨天的开始时间和结束时间，以字符串形式返回
     * @return 返回一个数组，第一个是开始时间，第二个是结束时间
     */
    public static String[] getYesterdayStartTimeAndEndTimeToString(){
		final String[] arrData = new String[2];
		final Calendar ca=Calendar.getInstance();
		ca.add(Calendar.DATE, -1);
		final SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_PATTERN);
		arrData[0] = sdf.format(ca.getTime()) + " 00:00:00";
		arrData[1] = sdf.format(ca.getTime()) + " 23:59:59";
		return arrData;
    }
    
    /**
     * 得到本月的开始时间和结束时间
     * @return 返回一个数组，第一个是开始时间，第二个是结束时间
     */
    public static Date[] getMonthStartTimeAndEndTime(){
    	try {
			final Date[] arrData = new Date[2];
			final Calendar calendar = Calendar.getInstance();     
			calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));     
			final SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_PATTERN);
			final SimpleDateFormat sdf2 = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
			final Date startDate = sdf2.parse(sdf.format(calendar.getTime()) + " 00:00:00");
			calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));  
			final Date endDate = sdf2.parse(sdf.format(calendar.getTime()) + " 23:59:59");
			arrData[0] = startDate;
			arrData[1] = endDate;
			return arrData;
    	} catch (final ParseException e) {
			e.printStackTrace();
		}
    	return null;
    }
    
    /**
     * 根据两个日期获得这两个日期相差几天几时几分
     * @param startDate
     * @param endDate
     * @return
     */
    public static String dateDiff(final Date startDate, final Date endDate) {
    	final long nh = 1000*60*60;//一小时的毫秒数
    	final long nm = 1000*60;//一分钟的毫秒数
    	final Calendar calender = Calendar.getInstance();
    	calender.setTime(startDate);
    	final Long millis = calender.getTimeInMillis();
    	calender.setTime(endDate);
    	final Long millis2 = calender.getTimeInMillis();
    	final Long diff = millis2 - millis;  //获得两个时间的毫秒时间差异
    	final long day = diff/ND;//计算差多少天
    	final long hour = (diff%ND)/nh;//计算差多少小时
    	final long min = (diff%ND%nh)/nm;//计算差多少分钟
    	return day+"天"+hour+"小时"+min+"分钟";
    }
    
   /**
    * 时间大小比较
    * @param date1
    * @param date2
    * @return date1<date2 小于0
    *         date1=date2 0
    *         date1>date2 大于0
    */
   public static int compareTo(final Date date1, final Date date2){
	   final Calendar dateCa1 = java.util.Calendar.getInstance();
	   dateCa1.setTime(date1);
	   final Calendar dateCa2 = java.util.Calendar.getInstance();
	   dateCa2.setTime(date2);
	   final int result = dateCa1.compareTo(dateCa2);
	   return result;
   }


	// 时间校验（type=0 年月日/type=1 时分秒） by 20170116
	public static boolean checkDateTime(String str, int type) {
		if (str.length() != 8 && type == 0)
			return false;
		if (str.length() != 6 && type == 1)
			return false;
		if (type == 0) {
			String year = str.substring(0, 4);
			String month = str.substring(4, 6);
			String day = str.substring(6, 8);
			str = year + "-" + month + "-" + day;
			if (!str.matches(
					"(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)")) {
				return false;
			}
			return true;
		}
		if (type == 1) {
			String str1 = str.substring(0, 2);
			String str2 = str.substring(2, 4);
			String str3 = str.substring(4, 6);
			str = str1 + ":" + str2 + ":" + str3;
			if (!str.matches("^([0-1]?[0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$")) {
				return false;
			}
			return true;
		}
		return false;
	}
    
}