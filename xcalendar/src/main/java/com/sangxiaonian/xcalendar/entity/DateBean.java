package com.sangxiaonian.xcalendar.entity;

import androidx.annotation.NonNull;

/**
 * 作者： ${PING} on 2018/10/15.
 */
public class DateBean implements Comparable<DateBean>{

    private int year;
    private int month;
    private int day;

    public DateBean() {
    }

    public DateBean(int year, int month, int day) {

        this.year = year;
        this.month = month;
        this.day = day;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else if (this == obj) {
            return true;
        } else if (obj instanceof DateBean) {
            DateBean o = (DateBean) obj;
            return (o.getYear() == year && o.getMonth() == getMonth() && o.getDay() == getDay());
        } else {
            return false;
        }
    }


    /**
     * 忽略日期比较月份
     * @return
     */
    public int compareWithNoDayTo(DateBean o) {
        int current = getYear() * 10000 + getMonth() * 100  ;
        int day = o.getYear() * 10000 +  o.getMonth() * 100  ;
        return current-day;
    }

    @Override
    public String toString() {
        return "DateBean{" +
                "year=" + year +
                ", month=" + month +
                ", day=" + day +
                '}';
    }


    /**
     * 两个日期对比
     * @param o 被对比的日期
     * @return 大于0当前日期大于被对比的日期 小于0当前日期小于被对比的日期 0两个日期相同
     */
    @Override
    public int compareTo(@NonNull DateBean o) {
        int current = getYear() * 10000 + getMonth() * 100 + getDay();
        int day = o.getYear() * 10000 +  o.getMonth() * 100 +  o.getDay();
        return current-day;
    }
}
