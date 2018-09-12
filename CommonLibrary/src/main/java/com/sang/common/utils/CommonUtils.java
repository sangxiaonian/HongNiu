package com.sang.common.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 作者： ${PING} on 2018/8/3.
 */
public class CommonUtils {

    /**
     * 携带指定的号码去拨号界面
     *
     * @param cxt
     * @param num
     */
    public static void toDial(Context cxt, String num) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + num));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        cxt.startActivity(intent);
    }

    /**
     * 车牌号校验
     *
     * @param carNum
     */
    public static boolean carNumMatches(String carNum) {

//        String regist = "^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1}$";
        //不区分大小写的正则
        String regist = "^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领a-zA-Z]{1}[a-zA-Z]{1}[a-zA-Z0-9]{4}[a-zA-Z0-9挂学警港澳]{1}$";
        Matcher p = Pattern.compile(regist).matcher(carNum);
        return p.matches();
    }


    public static boolean isPhone(String phone) {
        if (TextUtils.isEmpty(phone)) {
            return false;
        } else if (phone.length() == 11 && phone.startsWith("1")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isIdCard(String idCard) {
        if (TextUtils.isEmpty(idCard)) {
            return false;
        } else if (idCard.length() == 15 || idCard.length() == 18) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 是否是邮箱
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        String regist="^([a-zA-Z0-9]+[_|\\_|\\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\\_|\\.]?)*[a-zA-Z0-9]+\\.[a-zA-Z]{2,3}$";
        Matcher p = Pattern.compile(regist).matcher(email);
        return p.matches();
    }


    /**
     * 启动到应用商店app详情界面
     *
     * @param appPkg    目标App的包名
     * @param marketPkg 应用商店包名 ,如果为""则由系统弹出应用商店列表供用户选择,否则调转到目标市场的应用详情界面，某些应用商店可能会失败
     */
    public static void launchAppDetail(Context context, String appPkg, String marketPkg) {
        try {
            if (TextUtils.isEmpty(appPkg)) return;

            Uri uri = Uri.parse("market://details?id=" + appPkg);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            if (!TextUtils.isEmpty(marketPkg)) {
                intent.setPackage(marketPkg);
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
           context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
