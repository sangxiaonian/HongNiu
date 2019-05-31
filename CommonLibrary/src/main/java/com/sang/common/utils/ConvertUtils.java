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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        try {
            return new SimpleDateFormat(format).format(date.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return date.toString();
        }
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


    /**
     * 计算颜色渐变
     * @param fraction   渐变 0-1
     * @param startValue 0 时候的颜色
     * @param endValue   1 时候的颜色
     * @return
     */
    public static int evaluateColor(float fraction, Object startValue, Object endValue) {
        int startInt = (Integer) startValue;
        float startA = ((startInt >> 24) & 0xff) / 255.0f;
        float startR = ((startInt >> 16) & 0xff) / 255.0f;
        float startG = ((startInt >>  8) & 0xff) / 255.0f;
        float startB = ( startInt        & 0xff) / 255.0f;

        int endInt = (Integer) endValue;
        float endA = ((endInt >> 24) & 0xff) / 255.0f;
        float endR = ((endInt >> 16) & 0xff) / 255.0f;
        float endG = ((endInt >>  8) & 0xff) / 255.0f;
        float endB = ( endInt        & 0xff) / 255.0f;

        // convert from sRGB to linear
        startR = (float) Math.pow(startR, 2.2);
        startG = (float) Math.pow(startG, 2.2);
        startB = (float) Math.pow(startB, 2.2);

        endR = (float) Math.pow(endR, 2.2);
        endG = (float) Math.pow(endG, 2.2);
        endB = (float) Math.pow(endB, 2.2);

        // compute the interpolated color in linear space
        float a = startA + fraction * (endA - startA);
        float r = startR + fraction * (endR - startR);
        float g = startG + fraction * (endG - startG);
        float b = startB + fraction * (endB - startB);

        // convert back to sRGB in the [0..255] range
        a = a * 255.0f;
        r = (float) Math.pow(r, 1.0 / 2.2) * 255.0f;
        g = (float) Math.pow(g, 1.0 / 2.2) * 255.0f;
        b = (float) Math.pow(b, 1.0 / 2.2) * 255.0f;

        return Math.round(a) << 24 | Math.round(r) << 16 | Math.round(g) << 8 | Math.round(b);
    }


    /**
     * 去除参数中的汉字
     * @param s
     * @return
     */
    public static String replaceChinese(String s){
        if (!TextUtils.isEmpty(s)) {
            String REGEX_CHINESE = "[\u4e00-\u9fa5]";// 中文正则
            Pattern pat = Pattern.compile(REGEX_CHINESE);
            Matcher mat = pat.matcher(s);
            return mat.replaceAll("");
        }else {
            return s;
        }
    }

}
