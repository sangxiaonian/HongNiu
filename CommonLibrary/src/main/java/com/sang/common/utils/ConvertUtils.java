package com.sang.common.utils;

import android.text.TextUtils;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 作者： ${PING} on 2018/8/6.
 */
public class ConvertUtils {

    /**
     * 获取随机数
     *
     * @param start
     * @param end
     * @return
     */
    public static int getRandom(int start, int end) {
        return start + new Random().nextInt(end - start + 1);
    }


    public static String MD5(String string) {
        return MD5(string, null);
    }

    public static String MD5(String string, String slat) {
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes;
            if (!TextUtils.isEmpty(slat)) {
                bytes = md5.digest((string + slat).getBytes());
            } else {
                bytes = md5.digest((string).getBytes());

            }
            String result = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 将data转换成指定格式
     *
     * @param date   时间
     * @param format "yyyy年MM月dd日 HH:mm:ss"
     */
    public static String formatTime(Date date, String format) {

        return new SimpleDateFormat(format).format(date.getTime());
    }

    /**
     * 将data转换成指定格式
     *
     * @param date   时间
     * @param format "yyyy年MM月dd日"
     */
    public static String formatTime(long date, String format) {

        return new SimpleDateFormat(format).format(date);
    }

    /**
     * 时间格式转换
     *
     * @param sour       原来时间    2018 年11月11日
     * @param sourFormat 原来时间    yyyy年MM月dd日
     * @param desFormat  指定时间格式 yyyy-MM-dd
     * @return yyyy-MM-dd
     */
    public static String formatString(String sour, String sourFormat, String desFormat) {
        Date date = StrToDate(sour, sourFormat);
        return formatTime(date,desFormat);
    }

    /**
     * 字符串转换成日期
     *
     * @param str    2018 年11月11日
     * @param format yyyy年MM月dd日
     * @return date
     */
    public static Date StrToDate(String str, String format) {
        Date date = null;
        try {
            date = new SimpleDateFormat(format).parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static double add(double a1, double b1) {
        BigDecimal a2 = new BigDecimal(Double.toString(a1));
        BigDecimal b2 = new BigDecimal(Double.toString(b1));
        return a2.add(b2).doubleValue();
    }
    //减法：
    public static double sub(double a1, double b1) {
        BigDecimal a2 = new BigDecimal(Double.toString(a1));
        BigDecimal b2 = new BigDecimal(Double.toString(b1));
        return a2.subtract(b2).doubleValue();
    }
    //乘法：
    public static double mul(double a1, double b1) {
        BigDecimal a2 = new BigDecimal(Double.toString(a1));
        BigDecimal b2 = new BigDecimal(Double.toString(b1));
        return a2.multiply(b2).doubleValue();
    }
    //除法：
    public static double div(double a1, double b1, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("error");
        }
        BigDecimal a2 = new BigDecimal(Double.toString(a1));
        BigDecimal b2 = new BigDecimal(Double.toString(b1));
        return a2.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

//除法：

    /**
     * 保留小数
     * @param num    原始数据
     * @param floatNum 保留的位数
     * @return
     */
    public static String changeFloat(double num, int floatNum) {
        StringBuffer buffer =new StringBuffer();
        buffer.append("0");
        if (floatNum>0){
            buffer.append(".");
            for (int i = 0; i < floatNum; i++) {
                buffer.append("0");
            }
        }
        DecimalFormat df1 = new DecimalFormat(buffer.toString());
        String str = df1.format(num);
        return str;
    }




}
