package com.sangxiaonian.xcalendar.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 作者： ${PING} on 2018/10/15.
 */
public class CalendarUtils {


    private static class InnerClass {
        public static CalendarUtils calander = new CalendarUtils();
    }

    public static CalendarUtils getInstance(){
        return InnerClass.calander;
    }

    public CalendarUtils() {
    }

    /**
     * 获取当前月份的日期
     *
     * @return
     */
    public static List<String> getCurentMonthDays(int count) {
        final Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM月dd日");

        List<String> times = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            String format = simpleDateFormat.format(calendar.getTime());
            times.add(format);
        }
        return times;
    }

    /**
     * 获取到当前月
     *
     * @return 获取到当前月 0 代表一月
     */
    public int getCurrentMonth() {
        return Calendar.getInstance().get(Calendar.MONTH);
    }

    /**
     * 获取到当前年
     */
    public int getCurrentYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    /**
     * 获取到当前天
     */
    public int getCurrentDay() {
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }


    /**
     * 获取到当前月第一天是星期几
     */
    public int getCurrentMonthFirstDay() {


        return Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获取到当指定月第一天是星期几
     */
    public int getMonthFirstDayOfWeek(int year, int maoth) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(year, maoth, 1);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获取到当指定月天数
     *
     * @param year
     * @param month 指定月，从0开始
     * @return
     */
    public int getMonthDayCount(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);//注意,Calendar对象默认一月为0
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取到当月月天数
     *
     * @return
     */
    public int getCuttentMonthDayCount() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

}
