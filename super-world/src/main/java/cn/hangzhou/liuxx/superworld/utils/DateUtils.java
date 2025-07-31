package cn.hangzhou.liuxx.superworld.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtils {
    private static final Logger log = LoggerFactory.getLogger(DateUtils.class);

    public static boolean isDifferenceWithinDay(long start, long end, int day){
        long diff = end - start;
        long days = day * (1000 * 60 * 60 * 24) - 1000l;
        return diff <= days;
    }

    public static String toFormatString(long time, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(time));
    }

    public static long getTimestampSpecialInterval(int type) {
        DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cale = Calendar.getInstance();
        cale.add(Calendar.DATE, type);
        String tarday = new SimpleDateFormat("yyyy-MM-dd").format(cale.getTime()) + " 00:00:00";
        // String转Date
        try {
            Date date = format2.parse(tarday);
            return date.getTime();
        } catch (ParseException e) {
            log.error("获取日期报错", e);
        }
        return -1L;
    }

    public static List<String> getLast7Days() {

        List<String> result = new ArrayList<>();
        Calendar cale = Calendar.getInstance();
        cale.add(Calendar.DATE, -6);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String last7day = sdf.format(cale.getTime());

        result.add(last7day);

        cale.add(Calendar.DATE, 1);
        result.add(sdf.format(cale.getTime()));

        cale.add(Calendar.DATE, 1);
        result.add(sdf.format(cale.getTime()));

        cale.add(Calendar.DATE, 1);
        result.add(sdf.format(cale.getTime()));

        cale.add(Calendar.DATE, 1);
        result.add(sdf.format(cale.getTime()));

        cale.add(Calendar.DATE, 1);
        result.add(sdf.format(cale.getTime()));

        cale.add(Calendar.DATE, 1);
        result.add(sdf.format(cale.getTime()));

//        cale.add(Calendar.DATE, 1);
//        result.add(sdf.format(cale.getTime()));

        return result;
    }

    public static List<String> getLast24Hours() {
        List<String> result = new ArrayList<>();
        Calendar cale = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        cale.add(Calendar.HOUR, -24);
        for (int i = 0; i < 24; i++) {
            cale.add(Calendar.HOUR, 1);
            cale.set(Calendar.MINUTE, 0);
            cale.set(Calendar.SECOND, 0);
            result.add(sdf.format(cale.getTime()));
        }

        return result;
    }

    public static String getCurrentDay() {
        Calendar cale = Calendar.getInstance();
        return new SimpleDateFormat("yyyy-MM-dd").format(cale.getTime());
    }

    public static String getTomorrow() {
        Calendar cale = Calendar.getInstance();
        cale.add(Calendar.DATE, 1);
        return new SimpleDateFormat("yyyy-MM-dd").format(cale.getTime());
    }

    public static String timestampToString(long timestamp) {
        Date d = new Date();
        d.setTime(timestamp);
        return new SimpleDateFormat("yyyy-MM-dd").format(d);
    }

    public static String timestampToyyyyMM(long timestamp) {
        Date d = new Date();
        d.setTime(timestamp);
        return new SimpleDateFormat("yyyy-MM").format(d);
    }

    public static String timestampToyyyyMMdd(Long timestamp) {
        Date d = new Date();
        d.setTime(timestamp);
        return new SimpleDateFormat("yyyy-MM-dd").format(d);
    }

    public static String timestampToyyyyMMddHHmmss(long timestamp) {
        Date d = new Date();
        d.setTime(timestamp);
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(d);
    }

    public static long getCurrentDayStartTimestamp() {
        return getTimestampSpecialInterval(0);
    }

    public static Set<String> getLast7DaysMonth(){//获取七天前的月份
        Set<String> set = new HashSet<>();
        Calendar cale = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String currDay = sdf.format(cale.getTime());
        set.add(currDay);
        cale.add(Calendar.DATE, -6);
        String last7day = sdf.format(cale.getTime());
        set.add(last7day);
        return set;
    }

    public static String getLast7DayTimestamp(){//获取七天前的零点时间戳
        Date currentDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        cal.add(Calendar.DAY_OF_MONTH,-7);

        cal.set(Calendar.HOUR_OF_DAY,0);
        cal.set(Calendar.MINUTE,0);
        cal.set(Calendar.SECOND,0);
        cal.set(Calendar.MILLISECOND,0);
        return Long.toString(cal.getTimeInMillis());
    }

    //获取本月的开始的时间戳 比如 2023-12-01
    public static Long getMonthStartTime() {

        Long currentTime = System.currentTimeMillis();
        String timeZone = "GMT+8:00";
        Calendar calendar = Calendar.getInstance();// 获取当前日期
        calendar.setTimeZone(TimeZone.getTimeZone(timeZone));
        calendar.setTimeInMillis(currentTime);
        calendar.add(Calendar.YEAR, 0);
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }
    //获取上个月的开始时间戳
    public static Long getLastMonthStartTime() throws Exception {
        Long currentTime = System.currentTimeMillis();

        String timeZone = "GMT+8:00";
        Calendar calendar = Calendar.getInstance();// 获取当前日期
        calendar.setTimeZone(TimeZone.getTimeZone(timeZone));
        calendar.setTimeInMillis(currentTime);
        calendar.add(Calendar.YEAR, 0);
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTimeInMillis();
    }


    public static Long getLastMonthEndTime() {
        Long currentTime = System.currentTimeMillis();

        String timeZone = "GMT+8:00";
        Calendar calendar = Calendar.getInstance();// 获取当前日期
        calendar.setTimeZone(TimeZone.getTimeZone(timeZone));
        calendar.setTimeInMillis(currentTime);
        calendar.add(Calendar.YEAR, 0);
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));// 获取当前月最后一天
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);

        return calendar.getTimeInMillis();
    }

    // 获取昨天的零点时间戳
    public static Long getYesterdayTimestamp(){
        Date currentDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        cal.add(Calendar.DAY_OF_MONTH,-1);

        cal.set(Calendar.HOUR_OF_DAY,0);
        cal.set(Calendar.MINUTE,0);
        cal.set(Calendar.SECOND,0);
        cal.set(Calendar.MILLISECOND,0);
        return cal.getTimeInMillis();
    }
    public static String getYesterday() {
        Calendar cale = Calendar.getInstance();
        cale.add(Calendar.DATE, -1);
        return new SimpleDateFormat("yyyy-MM-dd").format(cale.getTime());
    }





}
