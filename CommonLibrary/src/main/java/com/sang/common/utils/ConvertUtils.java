package com.sang.common.utils;

import android.text.TextUtils;
import android.widget.TextView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import retrofit2.http.POST;

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

}
