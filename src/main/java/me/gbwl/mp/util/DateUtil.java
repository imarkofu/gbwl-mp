package me.gbwl.mp.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DateUtil {
	private static SimpleDateFormat formatter;

	public DateUtil() {
	}
	
	public static String shortYearAndMonth(Date aDate) {
		formatter = new SimpleDateFormat("yyyy-MM");
		return formatter.format(aDate);
	}

	public static String shortDate(Date aDate) {
		formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(aDate);
	}

	public static String dayDate(Date aDate) {
		formatter = new SimpleDateFormat("yyyyMMdd");
		return formatter.format(aDate);
	}

	public static Date chageDat2NoHMS(Date aDate) {
		formatter = new SimpleDateFormat("yyyy-MM-dd");	//2014-07-18 14:00 cgl修改了，原文：yyyyMMdd
		String fomatString = formatter.format(aDate);
		return getDateFull(fomatString);
	}

	public static String hourDate(Date aDate) {
		formatter = new SimpleDateFormat("yyyyMMddHH");
		return formatter.format(aDate);
	}

	public static String mailDate(Date aDate) {
		formatter = new SimpleDateFormat("yyyyMMddHHmm");
		return formatter.format(aDate);
	}

	public static String longLDAPDate(Date aDate) {
		formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		return formatter.format(aDate);
	}

	public static String longDate(Date aDate) {
		formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formatter.format(aDate);
	}

	public static String noSecondDate(Date aDate) {
		formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return formatter.format(aDate);
	}

	public static String longDateGB(Date aDate) {
		formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formatter.format(aDate);
	}

	public static String longDateGBK(Date aDate) {
		formatter = new SimpleDateFormat("yyyy锟斤拷MM锟斤拷dd锟斤拷");
		return formatter.format(aDate);
	}

	public static String formatDate(Date aDate, String formatStr) {
		formatter = new SimpleDateFormat(formatStr);
		return formatter.format(aDate);

	}

	public static String LDAPDate(Date aDate) {
		return formatDate(aDate, "yyyyMMddHHmm'Z'");
	}

	public static Date getDate(String yyyymmdd) {
		if ((null == yyyymmdd) || (yyyymmdd.trim().length() == 0))
			return null;
		try {
			int year = Integer.parseInt(yyyymmdd.substring(0,
					yyyymmdd.indexOf("-")));
			int month = Integer.parseInt(yyyymmdd.substring(
					yyyymmdd.indexOf("-") + 1, yyyymmdd.lastIndexOf("-")));
			int day = Integer.parseInt(yyyymmdd.substring(yyyymmdd
					.lastIndexOf("-") + 1));
			Calendar cal = Calendar.getInstance();
			cal.set(year, month - 1, day);
			return cal.getTime();
		} catch (Exception e) {
			return null;
		}

	}

	public static Date getDateFull(String yyyymmdd) {

		if ((null == yyyymmdd) || (yyyymmdd.trim().length() == 0))
			return null;
		try {
			int year = Integer.parseInt(yyyymmdd.substring(0,
					yyyymmdd.indexOf("-")));
			int month = Integer.parseInt(yyyymmdd.substring(
					yyyymmdd.indexOf("-") + 1, yyyymmdd.lastIndexOf("-")));
			int day = Integer.parseInt(yyyymmdd.substring(yyyymmdd
					.lastIndexOf("-") + 1));
			Calendar cal = Calendar.getInstance();
			cal.set(year, month - 1, day, 0, 0, 0);	//2014-07-18 14:05 cgl修改了，原文：24, 60, 60
			return cal.getTime();
		} catch (Exception e) {
			return null;
		}

	}

	public static Date parser(String strDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return sdf.parse(strDate);
		} catch (Exception e) {
			return null;
		}
	}

	public static Date parser(String strDate, String formatter) {
		SimpleDateFormat sdf = new SimpleDateFormat(formatter);
		try {
			return sdf.parse(strDate);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * get Date with only date num. baoxy add
	 * 
	 * @param yyyymmdd
	 * @return
	 */
	public static Date getDateOnly(String yyyymmdd) {
		if ((null == yyyymmdd) || (yyyymmdd.trim().length() == 0))
			return null;
		try {
			int year = Integer.parseInt(yyyymmdd.substring(0,
					yyyymmdd.indexOf("-")));
			int month = Integer.parseInt(yyyymmdd.substring(
					yyyymmdd.indexOf("-") + 1, yyyymmdd.lastIndexOf("-")));
			int day = Integer.parseInt(yyyymmdd.substring(yyyymmdd
					.lastIndexOf("-") + 1));
			Calendar cal = Calendar.getInstance();
			cal.set(year, month - 1, day, 0, 0, 0);
			return cal.getTime();
		} catch (Exception e) {
			return null;
		}
	}

	public static Date addMonth(Date myDate, int amount) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(myDate);
		boolean isEndDayOfMonth_old = cal
				.getActualMaximum(GregorianCalendar.DAY_OF_MONTH) == cal
				.get(GregorianCalendar.DAY_OF_MONTH);
		cal.add(GregorianCalendar.MONTH, amount);
		boolean isEndDayOfMonth_new = cal
				.getActualMaximum(GregorianCalendar.DAY_OF_MONTH) == cal
				.get(GregorianCalendar.DAY_OF_MONTH);
		if (isEndDayOfMonth_old && !isEndDayOfMonth_new) {
			cal.set(GregorianCalendar.DATE,
					cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH));
		}
		return cal.getTime();
	}

	public static Date addDay(Date myDate, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(parser(formatDate(myDate, "yyyy-MM-dd"), "yyyy-MM-dd"));
		cal.add(Calendar.DAY_OF_MONTH, amount);
		return cal.getTime();
	}

	public static Date addDate(Date myDate, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(myDate);
		cal.add(Calendar.DAY_OF_MONTH, amount);
		return cal.getTime();
	}

	public static Date removeDay(Date myDate, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(parser(formatDate(myDate, "yyyy-MM-dd"), "yyyy-MM-dd"));
		cal.add(Calendar.DAY_OF_MONTH, -amount);
		return cal.getTime();
	}

	public static Date addYear(Date myDate, int amount) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(myDate);
		boolean isEndDayOfMonth_old = cal
				.getActualMaximum(GregorianCalendar.DAY_OF_MONTH) == cal
				.get(GregorianCalendar.DAY_OF_MONTH);
		cal.add(GregorianCalendar.YEAR, amount);
		boolean isEndDayOfMonth_new = cal
				.getActualMaximum(GregorianCalendar.DAY_OF_MONTH) == cal
				.get(GregorianCalendar.DAY_OF_MONTH);
		if (isEndDayOfMonth_old && !isEndDayOfMonth_new) {
			cal.set(GregorianCalendar.DATE,
					cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH));
		}
		return cal.getTime();
	}

	public static Date getFirstDay(Date date) {
		Calendar cale = Calendar.getInstance();
		cale.set(Calendar.DAY_OF_MONTH, 1);
		return parser(formatDate(cale.getTime(), "yyyy-MM-dd"), "yyyy-MM-dd");
	}

	/*
	 * the mapping from jdk is : 1-Sun; 2-Mon;3-Tues; 4-Weds; 5-Thur;6-Fri;
	 * 7-Sat;
	 */
	public static int getWeekDay(Date myDate) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(myDate);
		return cal.get(GregorianCalendar.DAY_OF_WEEK);
	}

	/*
	 * the mapping from vas2005 is : 1-Mon;2-Tues; 3-Weds; 4-Thur;5-Fri;
	 * 6-Sat;7-Sun;
	 */
	public static int getConvertWeekDay(Date myDate) {
		int day = getWeekDay(myDate);
		int result = day - 1;
		if (result == 0)
			result = 7;
		return result;
	}

	public static int getTimeFromDate(Date myDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
		int result = Integer.parseInt(sdf.format(myDate));
		return result;
	}

	public static long previousXMonth(long current, int count) {
		Calendar aCalendar = Calendar.getInstance();
		aCalendar.setTime(new java.util.Date(current));
		aCalendar.getTime();
		aCalendar.add(Calendar.MONTH, -count);
		return aCalendar.getTime().getTime();
	}

	public static long nextXMonth(long current, int count) {
		Calendar aCalendar = Calendar.getInstance();
		aCalendar.setTime(new java.util.Date(current));
		aCalendar.getTime();
		aCalendar.add(Calendar.MONTH, count);
		return aCalendar.getTime().getTime();
	}

	public static Date getDayAfterWorkingDay(Date date, int days) {
		int num = 0;
		int i = 0;
		while (true) {
			date = addDay(new Date(), i);
			num = getWorkingDays(new Date(), date);
			if (num == days)
				break;
			i++;
		}
		return date;
	}

	/**
	 * 鑾峰彇浠绘剰鏃ユ湡涔嬮棿鐨勫伐浣滄棩
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static int getWorkingDays(Date startDate, Date endDate) {
		Calendar cal_start = Calendar.getInstance();
		Calendar cal_end = Calendar.getInstance();
		formatDate(startDate, "yyyy-MM-dd");
		formatDate(endDate, "yyyy-MM-dd");
		cal_start.setTime(startDate);
		cal_end.setTime(endDate);
		return getWorkingDay(cal_start, cal_end);
	}

	public static int getDaysBetween(java.util.Calendar d1,
			java.util.Calendar d2) {
		if (d1.after(d2)) { // swap dates so that d1 is start and d2 is end
			java.util.Calendar swap = d1;
			d1 = d2;
			d2 = swap;
		}
		int days = d2.get(java.util.Calendar.DAY_OF_YEAR)
				- d1.get(java.util.Calendar.DAY_OF_YEAR);
		int y2 = d2.get(java.util.Calendar.YEAR);
		if (d1.get(java.util.Calendar.YEAR) != y2) {
			d1 = (java.util.Calendar) d1.clone();
			do {
				days += d1.getActualMaximum(java.util.Calendar.DAY_OF_YEAR);
				d1.add(java.util.Calendar.YEAR, 1);
			} while (d1.get(java.util.Calendar.YEAR) != y2);
		}
		return days;
	}

	/**
	 * 璁＄畻2涓棩鏈熶箣闂寸殑鐩搁殧澶╂暟
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	private static int getWorkingDay(Calendar d1, Calendar d2) {
		int result = -1;
		if (d1.after(d2)) { // swap dates so that d1 is start and d2 is end
			Calendar swap = d1;
			d1 = d2;
			d2 = swap;
		}
		int charge_start_date = 0;// 寮�鏃ユ湡鐨勬棩鏈熷亸绉婚噺
		int charge_end_date = 0;// 缁撴潫鏃ユ湡鐨勬棩鏈熷亸绉婚噺
		// 鏃ユ湡涓嶅湪鍚屼竴涓棩鏈熷唴
		int stmp;
		int etmp;
		stmp = 7 - d1.get(Calendar.DAY_OF_WEEK);
		etmp = 7 - d2.get(Calendar.DAY_OF_WEEK);
		if (stmp != 0 && stmp != 6) {// 寮�鏃ユ湡涓烘槦鏈熷叚鍜屾槦鏈熸棩鏃跺亸绉婚噺涓�
			charge_start_date = stmp - 1;
		}
		if (etmp != 0 && etmp != 6) {// 缁撴潫鏃ユ湡涓烘槦鏈熷叚鍜屾槦鏈熸棩鏃跺亸绉婚噺涓�
			charge_end_date = etmp - 1;
		}
		result = (getDaysBetween(getNextMonday(d1), getNextMonday(d2)) / 7) * 5
				+ charge_start_date - charge_end_date; // 娉ㄦ剰绠楁硶
		return result;
	}

	/**
	 * 鑾峰緱鏃ユ湡鐨勪笅涓�釜鏄熸湡涓�殑鏃ユ湡
	 * 
	 * @param date
	 * @return
	 */
	private static Calendar getNextMonday(Calendar date) {
		Calendar result = null;
		result = date;
		do {
			result = (Calendar) result.clone(); // 鍙兘鏄湪while涓渶瑕佷繚鎸佸�鐨勪笉鍙榗lone
			result.add(Calendar.DATE, 1);
		} while (result.get(Calendar.DAY_OF_WEEK) != 2);
		return result;
	}

	/**
	 * @return yyyy-MM-dd 00:00:00 鏍煎紡
	 */
	public static Date getYesterday() {
		Calendar cale = Calendar.getInstance();
		cale.setTime(new Date());
		cale.add(Calendar.DATE, -1);
		return parser(formatDate(cale.getTime(), "yyyy-MM-dd"), "yyyy-MM-dd");
	}

	/**
	 * @return yyyy-MM-dd 00:00:00 鏍煎紡
	 */
	public static Date getYesterday(Date thisDate) {
		Calendar cale = Calendar.getInstance();
		cale.setTime(thisDate);
		cale.add(Calendar.DATE, -1);
		return parser(formatDate(cale.getTime(), "yyyy-MM-dd"), "yyyy-MM-dd");
	}

	/**
	 * @return yyyy-MM-dd 00:00:00 鏍煎紡
	 */
	public static Date getToday() {
		Calendar cale = Calendar.getInstance();
		cale.setTime(new Date());
		return parser(formatDate(cale.getTime(), "yyyy-MM-dd"), "yyyy-MM-dd");
	}

	/**
	 * @return yyyy-MM-dd 00:00:00 鏍煎紡
	 */
	public static Date getToday(Date thisDate) {
		Calendar cale = Calendar.getInstance();
		cale.setTime(thisDate);
		return parser(formatDate(cale.getTime(), "yyyy-MM-dd"), "yyyy-MM-dd");
	}

	/**
	 * @return yyyy-MM-dd 00:00:00 鏍煎紡
	 */
	public static Date getTomorrow() {
		Calendar cale = Calendar.getInstance();
		cale.setTime(new Date());
		cale.add(Calendar.DATE, 1);
		return parser(formatDate(cale.getTime(), "yyyy-MM-dd"), "yyyy-MM-dd");
	}

	/**
	 * @return yyyy-MM-dd 00:00:00 鏍煎紡
	 */
	public static Date getTomorrow(Date thisDate) {
		Calendar cale = Calendar.getInstance();
		cale.setTime(thisDate);
		cale.add(Calendar.DATE, 1);
		return parser(formatDate(cale.getTime(), "yyyy-MM-dd"), "yyyy-MM-dd");
	}

	/**
	 * 杩旓細浼犲叆鐢熸棩鏃跺�锛岃繑鍥炰汉褰撳墠鐨勫勾榫�
	 * 
	 * @param birthDay
	 * @return
	 * @throws Exception
	 */
	public static int getAge(Date birthDay) {
		Calendar cal = Calendar.getInstance();

		if (cal.before(birthDay)) {
			throw new IllegalArgumentException("鍑虹敓鏃堕棿澶т簬褰撳墠鏃堕棿!");
		}

		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH) + 1;// 娉ㄦ剰姝ゅ锛屽鏋滀笉鍔�鐨勮瘽璁＄畻缁撴灉鏄敊璇殑
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
		cal.setTime(birthDay);

		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH);
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

		int age = yearNow - yearBirth;

		if (monthNow <= monthBirth) {
			if (monthNow == monthBirth) {
				// monthNow==monthBirth
				if (dayOfMonthNow < dayOfMonthBirth) {
					age--;
				} else {
					// do nothing
				}
			} else {
				// monthNow>monthBirth
				age--;
			}
		} else {
			// monthNow<monthBirth
			// donothing
		}

		return age;
	}

	public static int getDaysBetween(String beginDate, String endDate) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date bDate = null;
		Date eDate = null;
		try {
			bDate = format.parse(beginDate);
			eDate = format.parse(endDate);
		} catch (ParseException e) {
			System.out.println("鏃堕棿鏍煎紡閿欒");
			return -1;
		}
		Calendar d1 = new GregorianCalendar();
		d1.setTime(bDate);
		Calendar d2 = new GregorianCalendar();
		d2.setTime(eDate);
		int days = d2.get(6) - d1.get(6);
		int y2 = d2.get(1);
		if (d1.get(1) != y2) {
			d1 = (Calendar) d1.clone();
			do {
				days += d1.getActualMaximum(6);
				d1.add(1, 1);
			} while (d1.get(1) != y2);
		}
		return days;
	}

	public static String getDate() {
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		Date dd = new Date();
		return ft.format(dd);
	}

	public static String getDate(Date day) {
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		// Date dd = new Date();
		return ft.format(day);
	}

	public static long getQuot(String time1, String time2) {
		long quot = 0;
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date1 = ft.parse(time1);
			Date date2 = ft.parse(time2);
			quot = date1.getTime() - date2.getTime();
			quot = quot / 1000 / 60 / 60 / 24;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return quot;
	}

	public static List<String> getBetweenTwoDayCalender(String startDay,
			String endDay) {
		System.out.println("startDay:" + startDay + ",endDay:" + endDay);
		List<String> list = new ArrayList<String>();
		if (startDay == null || startDay.equals("")) {
			return null;
		}
		if (endDay == null || endDay.equals("")) {
			return null;
		}
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date startD = myFormatter.parse(startDay);
			Date endD = myFormatter.parse(endDay);
			long day = (endD.getTime() - startD.getTime())
					/ (24 * 60 * 60 * 1000);
			long dayMill = 24 * 60 * 60 * 1000;
			int d = Integer.parseInt(String.valueOf(day));
			String updateTime;
			long longTime;
			for (int i = 0; i <= d; i++) {
				longTime = startD.getTime() + dayMill * i;
				updateTime = myFormatter.format(new Date(longTime));
				list.add(updateTime);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 功能：将日期转化为时间戳
	 * */
	public static long getTimestamp(Date date1) {
		try {
			Date date2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.parse("1970-01-01 08:00:00");
			long timestamp = date1.getTime() - date2.getTime() > 0 ? date1
					.getTime() - date2.getTime() : date2.getTime()
					- date1.getTime();
			return timestamp;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return System.currentTimeMillis();
	}

	/**
	 * 获取明日凌晨2点半的
	 * 
	 * @return
	 */
	public static Date getTomorrowTwo() {
		Calendar cale = Calendar.getInstance();
		cale.setTime(new Date());
		cale.add(Calendar.DATE, 1);
		cale.set(Calendar.HOUR_OF_DAY, 2);
		cale.set(Calendar.SECOND, 0);
		cale.set(Calendar.MINUTE, 30);
		return parser(formatDate(cale.getTime(), "yyyy-MM-dd HH:mm:ss"),
				"yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 获取该日期之后某日的日期(包括时分秒)
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date addFullDay(Date date, int day) {
		Calendar cale = Calendar.getInstance();
		cale.setTime(parser(formatDate(date, "yyyy-MM-dd HH:mm:ss"),
				"yyyy-MM-dd HH:mm:ss"));
		cale.add(Calendar.DAY_OF_MONTH, day);
		return parser(formatDate(cale.getTime(), "yyyy-MM-dd HH:mm:ss"),
				"yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < 10; i++) {
			map.put("12", "33");
			map.put("122", "33");
			map.put("123", "33");
		}
		for (Map.Entry<String, String> string : map.entrySet()) {
			System.out.println(string.getKey() + "_" + string.getValue());
		}
	}

}
