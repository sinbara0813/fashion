package com.d2cmall.buyer.util;

import android.content.Context;
import android.text.format.Time;

import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.bean.TimeBean;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static android.text.format.DateUtils.isToday;

/**
 * 时间格式化工具类
 * Author: Blend
 * Date: 16/6/5 01:06
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class DateUtil {

    final static String chineseNumber[] = {"一", "二", "三", "四", "五", "六", "七",
            "八", "九", "十", "十一", "十二"};

    public static String getChinaDayNumber(int month) {
        return chineseNumber[month];
    }

    private final static ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(Constants.DATE_FORMAT_LONG1);
        }
    };

    private final static ThreadLocal<SimpleDateFormat> dateFormater2 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(Constants.DATE_FORMAT_SHORT);
        }
    };

    private final static ThreadLocal<SimpleDateFormat> dateFormater3 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(Constants.DATE_FORMAT_HM);
        }
    };

    private final static ThreadLocal<SimpleDateFormat> dateFormater4 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(Constants.DATE_FORMAT_MD);
        }
    };

    private final static ThreadLocal<SimpleDateFormat> dateFormater5 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(Constants.DATE_FORMAT_MDHM);
        }
    };
    private final static ThreadLocal<SimpleDateFormat> dateFormater6 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(Constants.DATE_FORMAT_MD_HM);
        }
    };
    private final static ThreadLocal<SimpleDateFormat> dateFormater7 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(Constants.DATE_FORMAT_YMD);
        }
    };
    private final static ThreadLocal<SimpleDateFormat> dateFormater8 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(Constants.DATE_FORMAT_SHORT1);
        }
    };

    public static String getFriendlyTime(String sdate) {
        Date time = null;
        if (isInEasternEightZones()) {
            time = toDate(sdate);
        } else {
            time = transformTime(toDate(sdate),
                    TimeZone.getTimeZone("GMT+08"), TimeZone.getDefault());
        }
        if (time == null) {
            return "Unknown";
        }
        Calendar cal = Calendar.getInstance();
        // 判断是否是同一天
        String curDate = dateFormater2.get().format(cal.getTime());
        String paramDate = dateFormater2.get().format(time);
        if (curDate.equals(paramDate)) {
            return dateFormater3.get().format(time);
        }
        if (isTodayYear(time.getTime())) {//如果是今年，显示月/日
            return dateFormater4.get().format(time);
        } else {
            return dateFormater2.get().format(time);
        }
    }

    public static boolean checkRedTime(String time){
        if (Util.isEmpty(time)){
            return true;
        }
        SimpleDateFormat dateFormat=new SimpleDateFormat(Constants.DATE_FORMAT_LONG);
        try {
            Date date=dateFormat.parse(time);
            if (date.getTime()>System.currentTimeMillis()){
                return true;
            }else {
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String getFriendlyTime2(String sdate) {
        Date time = null;
        if (isInEasternEightZones()) {
            time = toDate(sdate);
        } else {
            time = transformTime(toDate(sdate),
                    TimeZone.getTimeZone("GMT+08"), TimeZone.getDefault());
        }
        if (time == null) {
            return "Unknown";
        }
        Calendar nowCalendar = Calendar.getInstance();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        int year1 = calendar.get(Calendar.YEAR);
        int year2 = nowCalendar.get(Calendar.YEAR);
        int month1 = calendar.get(Calendar.MONTH);
        int month2 = nowCalendar.get(Calendar.MONTH);
        int day1 = calendar.get(Calendar.DAY_OF_MONTH);
        int day2 = nowCalendar.get(Calendar.DAY_OF_MONTH);
        if (year1 == year2 && month1 == month2 && day2 - day1 == -1) {
            return "明天 " + dateFormater3.get().format(time);
        } else if (year1 == year2 && month1 == month2 && day1 == day2) {
            return "今天 " + dateFormater3.get().format(time);
        } else if (year1 < year2) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
            return simpleDateFormat.format(time);
        } else {
            return dateFormater5.get().format(time);
        }
    }

    public static String toString(Date date){
        if (date==null) return "";
        return dateFormater.get().format(date);
    }

    public static Date toDate(String sdate) {
        return toDate(sdate, dateFormater.get());
    }

    public static Date toShortDate(String s){
        try {
            return dateFormater8.get().parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date toDate(String sdate, SimpleDateFormat dateFormater) {
        try {
            return dateFormater.parse(sdate);
        } catch (ParseException e) {
            SimpleDateFormat dateFormat=new SimpleDateFormat(Constants.DATE_FORMAT_LONG);
            try {
                return dateFormat.parse(sdate);
            } catch (ParseException e1) {
                e1.printStackTrace();
                return null;
            }
        }
    }

    public static String getFutureDate(String baseDate,int futureDays){
        Date d=toDate(baseDate);
        if (d==null)return "";
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.add(Calendar.DAY_OF_YEAR, futureDays);
        Date date = calendar.getTime();
        return dateFormater.get().format(date);
    }

    public static String getChDate(String text){
        StringBuilder builder=new StringBuilder();
        builder.append(text.substring(0,4)).append("年").append(text.substring(5, 7)).append("月").append(text.substring(8,10)).append("日");
        builder.append(text.substring(10, text.length()));
        return builder.toString();
    }

    public static String stampToDate(String s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    private static boolean isInEasternEightZones() {
        return TimeZone.getDefault() == TimeZone.getTimeZone("GMT+08");
    }

    private static boolean isTodayYear(long time) {
        Calendar pre = Calendar.getInstance();
        Date predate = new Date(System.currentTimeMillis());
        pre.setTime(predate);

        Calendar cal = Calendar.getInstance();
        Date date = new Date(time);
        cal.setTime(date);

        if (cal.get(Calendar.YEAR) == (pre.get(Calendar.YEAR))) {
            return true;
        }
        return false;
    }

    private static Date transformTime(Date date, TimeZone oldZone, TimeZone newZone) {
        Date finalDate = null;
        if (date != null) {
            int timeOffset = oldZone.getOffset(date.getTime())
                    - newZone.getOffset(date.getTime());
            finalDate = new Date(date.getTime() - timeOffset);
        }
        return finalDate;
    }

    public static TimeBean getTimeBean(Context context, Calendar calendar) {
        TimeBean timeBean = new TimeBean();
        Calendar nowCalendar = Calendar.getInstance();
        int year1 = calendar.get(Calendar.YEAR);
        int year2 = nowCalendar.get(Calendar.YEAR);
        int month1 = calendar.get(Calendar.MONTH);
        int month2 = nowCalendar.get(Calendar.MONTH);
        int day1 = calendar.get(Calendar.DAY_OF_MONTH);
        int day2 = nowCalendar.get(Calendar.DAY_OF_MONTH);
        if (year1 == year2 && month1 == month2 && day2 - day1 == 1) {
            timeBean.setDay(context.getString(R.string.label_yesterday));
            timeBean.setTodayOrYesterday(true);
            return timeBean;
        } else if (year1 == year2 && month1 == month2 && day1 == day2) {
            timeBean.setDay(context.getString(R.string.label_today));
            timeBean.setTodayOrYesterday(true);
            return timeBean;
        } else {
            timeBean.setMonth(context.getString(R.string.label_month,
                    getChinaDayNumber(calendar.get(Calendar.MONTH))));
            timeBean.setDay(String.valueOf(day1));
            timeBean.setTodayOrYesterday(false);
            return timeBean;
        }
    }

    public static long getMinutesNum(Date time){
        Long hqtime=time.getTime();
        Long s = (System.currentTimeMillis() - hqtime) / (1000 * 60);
        return s;
    }

    public static int isThisMinutes(String sdate, int mMinute) {
        Date time = null;
        if (isInEasternEightZones()) {
            time = toDate(sdate);
        } else {
            time = transformTime(toDate(sdate),
                    TimeZone.getTimeZone("GMT+08"), TimeZone.getDefault());
        }
        Calendar cal = Calendar.getInstance();
        long dateTime = cal.getTimeInMillis() - time.getTime();
        long minute = dateTime / 60000;
        if (dateTime <= 0) {//还没到指定分钟内
            if (minute < -mMinute) {
                return 0;
            } else {//到指定分钟内了
                return 1;
            }
        } else {//过期了
            return 2;
        }
    }

    public static int isThisMinutes2(String sdate, int mMinute) {
        Date time = null;
        if (isInEasternEightZones()) {
            time = toDate(sdate);
        } else {
            time = transformTime(toDate(sdate),
                    TimeZone.getTimeZone("GMT+08"), TimeZone.getDefault());
        }
        Calendar cal = Calendar.getInstance();
        long dateTime = cal.getTimeInMillis() - time.getTime();
        long minute = dateTime / 60000;
        if (dateTime < 0) {//还没到指定分钟内
            return 0;
        } else if (dateTime == 0 || (dateTime > 0 && minute <= mMinute)) {//来的正是时候
            return 1;
        } else {//来晚了
            return 2;
        }
    }

    public static int isThisMinutes3(long startDateMillis, long endDateMillis, int mMinute) {
        Calendar cal = Calendar.getInstance();
        long currentDateMillis = cal.getTimeInMillis();
        if (currentDateMillis < startDateMillis) {//还没到
            long dateTime = currentDateMillis - startDateMillis;
            long minute = dateTime / 60000;
            if (minute < -mMinute) {
                return 0;
            } else {
                return 1;
            }
        } else if (currentDateMillis >= startDateMillis && currentDateMillis <= endDateMillis) {
            return 2;
        } else {//过了
            return 3;
        }
    }

    public static String getFriendlyTime3(String sdate) {
        Date time = toDate(sdate);
        if (time == null) {
            return "Unknown";
        }
        String ftime = "";
        Calendar cal = Calendar.getInstance();

        //判断是否是同一天
        String curDate = dateFormater2.get().format(cal.getTime());
        String paramDate = dateFormater2.get().format(time);
        if (curDate.equals(paramDate)) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0) {
                long minute = Math.max((cal.getTimeInMillis() - time.getTime()) / 60000, 1);
                if (minute >= 0 && minute <= 10) {
                    ftime = "刚刚";
                } else {
                    ftime = minute + "分钟前";
                }
            } else {
                ftime = hour + "小时前";
            }
            return ftime;
        }

        long lt = time.getTime() / 86400000;
        long ct = cal.getTimeInMillis() / 86400000;
        int days = (int) (ct - lt);
        if (days == 0) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0) {
                long minute = Math.max((cal.getTimeInMillis() - time.getTime()) / 60000, 1);
                if (minute >= 0 && minute <= 10) {
                    ftime = "刚刚";
                } else {
                    ftime = minute + "分钟前";
                }
            } else {
                ftime = hour + "小时前";
            }
        }
        if (days > 0) {
            ftime = dateFormater3.get().format(time);
        }
        return ftime;
    }

    public static String getFriendlyTime4(String sdate) {
        Date time = toDate(sdate);
        if (time == null) {
            return "Unknown";
        }
        String ftime = "";
        Calendar cal = Calendar.getInstance();

        //判断是否是同一天
        String curDate = dateFormater2.get().format(cal.getTime());
        String paramDate = dateFormater2.get().format(time);
        if (curDate.equals(paramDate)) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0) {
                long minute = Math.max((cal.getTimeInMillis() - time.getTime()) / 60000, 1);
                if (minute >= 0 && minute <= 10) {
                    ftime = "刚刚";
                } else {
                    ftime = minute + "分钟前";
                }
            } else {
                ftime = hour + "小时前";
            }
            return ftime;
        }

        long lt = time.getTime() / 86400000;
        long ct = cal.getTimeInMillis() / 86400000;
        int days = (int) (ct - lt);
        if (days == 0) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0) {
                long minute = Math.max((cal.getTimeInMillis() - time.getTime()) / 60000, 1);
                if (minute >= 0 && minute <= 10) {
                    ftime = "刚刚";
                } else {
                    ftime = minute + "分钟前";
                }
            } else {
                ftime = hour + "小时前";
            }
            return ftime;
        }
        if (days <= 2) {
            ftime = days + "天前";
        } else {
            ftime = dateFormater4.get().format(time);
        }
        return ftime;
    }

    public static String getAddReViewTime(String firstViewTime,String reViewTime){
        Date firstTime = toDate(firstViewTime);
        Date secondTime= toDate(reViewTime);
        Calendar firstCal = Calendar.getInstance();
        firstCal.setTime(firstTime);
        Calendar secondCal = Calendar.getInstance();
        secondCal.setTime(secondTime);
        if (firstTime == null) {
            return "Unknown";
        }
        int firstDay = firstCal.get(Calendar.DAY_OF_YEAR);
        int secondDay=secondCal.get(Calendar.DAY_OF_YEAR);
        if (firstDay==secondDay){//同一天
            long offer=secondCal.getTimeInMillis()-firstCal.getTimeInMillis();
            int hour=(int)offer/3600000;
            int minute=((int)offer/60000)%60;
            if (hour>0){
                return hour+"小时后";
            }else {
                if (minute>10){
                    return minute+"分钟后";
                }else {
                    return "刚刚";
                }
            }
        }else if (secondDay>firstDay){
            return (secondDay-firstDay)+"天后";
        }else {
            return "Unknown";
        }
    }


    public static Date strToDateLong(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    public static String ConverToString(Date date) {
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return df.format(date);
    }
    public static String ConverToYMDString(Date date) {
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        return df.format(date);
    }
    public static String getDurationString(long duration) {
//        long days = duration / (1000 * 60 * 60 * 24);
        long hours = (duration % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (duration % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = (duration % (1000 * 60)) / 1000;

        String hourStr = (hours < 10) ? "0" + hours : hours + "";
        String minuteStr = (minutes < 10) ? "0" + minutes : minutes + "";
        String secondStr = (seconds < 10) ? "0" + seconds : seconds + "";

        if (hours != 0) {
            return hourStr + ":" + minuteStr + ":" + secondStr;
        } else {
            return minuteStr + ":" + secondStr;
        }
    }

    public static String getFriendlyTime5(String sdate) {
        Date time = null;
        if (isInEasternEightZones()) {
            time = toDate(sdate);
        } else {
            time = transformTime(toDate(sdate),
                    TimeZone.getTimeZone("GMT+08"), TimeZone.getDefault());
        }
        if (time == null) {
            return "Unknown";
        }
        Calendar cal = Calendar.getInstance();
        // 判断是否是同一天
        String curDate = dateFormater2.get().format(cal.getTime());
        String paramDate = dateFormater2.get().format(time);
        if (curDate.equals(paramDate)) {
            return dateFormater3.get().format(time);
        }
        if (isTodayYear(time.getTime())) {//如果是今年，显示月/日/时/分
            return dateFormater6.get().format(time);
        } else {
            return dateFormater2.get().format(time);
        }
    }

    public static String getFriendlyTime6(String sdate) {
        Date time = null;
        if (isInEasternEightZones()) {
            time = toDate(sdate);
        } else {
            time = transformTime(toDate(sdate),
                    TimeZone.getTimeZone("GMT+08"), TimeZone.getDefault());
        }
        if (time == null) {
            return "Unknown";
        }
        if (isTodayYear(time.getTime())) {//如果是今年，显示月/日/时/分
            return dateFormater6.get().format(time);
        } else {
            return dateFormater2.get().format(time);
        }
    }

    public static String getFriendlyTime7(String sdate) {
        Date time = null;
        if (isInEasternEightZones()) {
            time = toDate(sdate);
        } else {
            time = transformTime(toDate(sdate),
                    TimeZone.getTimeZone("GMT+08"), TimeZone.getDefault());
        }
        if (time == null) {
            return "Unknown";
        }
        if (isToday(time.getTime())) {//如果是今年，显示/时/分
            return dateFormater3.get().format(time);
        } else if (isTodayYear(time.getTime())) {
            return dateFormater4.get().format(time);
        } else {
            return dateFormater2.get().format(time);
        }
    }
    public static String getFriendlyTime8(String sdate) {
        Date time = null;
        if (isInEasternEightZones()) {
            time = toDate(sdate);
        } else {
            time = transformTime(toDate(sdate),
                    TimeZone.getTimeZone("GMT+08"), TimeZone.getDefault());
        }
        if (time == null) {
            return "Unknown";
        }
            return dateFormater7.get().format(time);
    }
    public static String getFriendlyTime9(String sdate) {
        Date time = null;
        if (isInEasternEightZones()) {
            time = toDate(sdate);
        } else {
            time = transformTime(toDate(sdate),
                    TimeZone.getTimeZone("GMT+08"), TimeZone.getDefault());
        }
        if (time == null) {
            return "Unknown";
        }
        return dateFormater8.get().format(time);
    }

    public static int compareToday(long when){
        Time time = new Time();
        time.set(when);

        int thenYear = time.year;
        int thenMonth = time.month;
        int thenMonthDay = time.monthDay;

        time.set(System.currentTimeMillis());
        if (thenYear<time.year){
            return -1;
        }else if (thenYear>time.year){
            return 1;
        }else {
           if (thenMonth<time.month){
               return -1;
           }else if (thenMonth>time.month){
               return 1;
           }else {
               if (thenMonthDay<time.monthDay){
                   return -1;
               }else if (thenMonthDay>time.monthDay){
                   return 1;
               }else {
                   return 0;
               }
           }
        }
    }

    public static boolean isSameDate(Date date1, Date date2) {

        Calendar cal1 = Calendar.getInstance();

        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();

        cal2.setTime(date2);

        boolean isSameYear = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);

        boolean isSameMonth = isSameYear && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);

        boolean isSameDate = isSameMonth && cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);

        return isSameDate;

    }



    public static Date parseDate(String str, String... parsePatterns) throws ParseException {
        return parseDateWithLeniency(str, parsePatterns, true);
    }

    public static Date parseDateStrictly(String str, String... parsePatterns) throws ParseException {
        return parseDateWithLeniency(str, parsePatterns, false);
    }

    private static Date parseDateWithLeniency(String str, String[] parsePatterns, boolean lenient) throws ParseException {
        if(str != null && parsePatterns != null) {
            SimpleDateFormat parser = new SimpleDateFormat();
            parser.setLenient(lenient);
            ParsePosition pos = new ParsePosition(0);
            String[] arr$ = parsePatterns;
            int len$ = parsePatterns.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                String parsePattern = arr$[i$];
                String pattern = parsePattern;
                if(parsePattern.endsWith("ZZ")) {
                    pattern = parsePattern.substring(0, parsePattern.length() - 1);
                }

                parser.applyPattern(pattern);
                pos.setIndex(0);
                String str2 = str;
                if(parsePattern.endsWith("ZZ")) {
                    str2 = str.replaceAll("([-+][0-9][0-9]):([0-9][0-9])$", "$1$2");
                }

                Date date = parser.parse(str2, pos);
                if(date != null && pos.getIndex() == str2.length()) {
                    return date;
                }
            }

            throw new ParseException("Unable to parse the date: " + str, -1);
        } else {
            throw new IllegalArgumentException("Date and Patterns must not be null");
        }
    }

}
