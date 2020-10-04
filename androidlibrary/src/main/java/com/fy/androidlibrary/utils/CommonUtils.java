package com.fy.androidlibrary.utils;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.fy.androidlibrary.R;

import java.util.Objects;
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
    public static void call(Context cxt, String num) {
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
//        String regist = "^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领a-zA-Z]{1}[a-zA-Z]{1}[a-zA-Z0-9]{4}[a-zA-Z0-9挂学警港澳]{1}$";
        String regist = "^([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼]{1}(([A-HJ-ZA-HJ-Z]{1}[A-HJ-NP-Z0-9]{5})|([A-HJ-Z]{1}(([DF]{1}[A-HJ-NP-Z0-9]{1}[0-9]{4})|([0-9]{5}[DF]{1})))|([A-HJ-Z]{1}[A-D0-9]{1}[0-9]{3}警)))|([0-9]{6}使)|((([沪粤川云桂鄂陕蒙藏黑辽渝]{1}A)|鲁B|闽D|蒙E|蒙H)[0-9]{4}领)|(WJ[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼·•]{1}[0-9]{4}[TDSHBXJ0-9]{1})|([VKHBSLJNGCE]{1}[A-DJ-PR-TVY]{1}[0-9]{5})$";
        Matcher p = Pattern.compile(regist).matcher(carNum);
        return p.matches();
    }


    public static boolean isPhone(String phone) {
        if (TextUtils.isEmpty(phone)) {
            return false;
        } else return phone.length() == 11 && phone.startsWith("1");
    }

    public static boolean isIdCard(String idCard) {
        if (TextUtils.isEmpty(idCard)) {
            return false;
        } else return idCard.length() == 15 || idCard.length() == 18;
    }

    /**
     * 是否是邮箱
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        String regist = "^([a-zA-Z0-9]+[_|\\_|\\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\\_|\\.]?)*[a-zA-Z0-9]+\\.[a-zA-Z]{2,3}$";
        Matcher p = Pattern.compile(regist).matcher(email);
        return p.matches();
    }


    /**
     * 启动到应用商店app详情界面
     *
     * @param marketPkg 应用商店包名 ,如果为""则由系统弹出应用商店列表供用户选择,否则调转到目标市场的应用详情界面，某些应用商店可能会失败
     */
    public static void launchAppDetail(Context context, String marketPkg) {
        try {


            Uri uri = Uri.parse("market://details?id=" + context.getApplicationInfo().packageName);
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

    /**
     * 启动到应用商店app详情界面
     */
    public static void launchAppDetail(Context context) {
        //华为
        boolean huawei = DeviceUtils.getDeviceBrand().equalsIgnoreCase("Huawei");
        boolean honor = DeviceUtils.getDeviceBrand().equalsIgnoreCase("honor");
        boolean oppo = DeviceUtils.getDeviceBrand().equalsIgnoreCase("OPPO");
        boolean vivo = DeviceUtils.getDeviceBrand().equalsIgnoreCase("vivo");
        boolean xiaomi = DeviceUtils.getDeviceBrand().equalsIgnoreCase("Xiaomi");
        String pkg = "com.tencent.android.qqdownloader";
        if (honor || huawei || oppo || vivo || xiaomi) {
            pkg = "";
        }
        //如果安装了应用宝
        if (TextUtils.isEmpty(pkg) || DeviceUtils.isPkgInstalled(context, pkg)) {
            launchAppDetail(context, pkg);
        } else {//没有应用市场，浏览器跳转
            Uri uri = Uri.parse("http://a.app.qq.com/o/simple.jsp?pkgname=" + context.getApplicationInfo().packageName);
            Intent it = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(it);
        }


    }


    public static boolean equals(Object a, Object b) {
        return (a == b) || (a != null && a.equals(b));
    }

    public static void setText(TextView text, String msg) {
        setText(text, msg, "");
    }

    public static void setText(TextView text, String msg, String defult) {
        if (text != null) {
            text.setText(TextUtils.isEmpty(msg) ? defult : msg);
        }
    }

    public static String getText(String msg) {
        return getText(msg, "");
    }

    public static String getText(String msg, String defaultMsg) {
        return TextUtils.isEmpty(msg) ? defaultMsg : msg;
    }

    //判断界面是否已经被销毁
    public static boolean isDestroy(Activity activity) {
        if (activity == null || activity.isFinishing() || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && activity.isDestroyed())) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 复制内容到剪切板
     *
     * @param copyStr
     * @return
     */
    public static boolean copy(Context context, String copyStr) {
        try {
            //获取剪贴板管理器
            ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            // 创建普通字符型ClipData
            ClipData mClipData = ClipData.newPlainText("Label", copyStr);
            // 将ClipData内容放到系统剪贴板里。
            cm.setPrimaryClip(mClipData);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 复制内容到剪贴板
     *
     * @param mContext
     * @param msg
     */
    public static void copyToPlate(Context mContext, String msg) {
        if (mContext != null && !TextUtils.isEmpty(msg)) {
            //获取剪贴板管理器：
            ClipboardManager cm = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
// 创建普通字符型ClipData
            ClipData mClipData = ClipData.newPlainText("Label", msg);
// 将ClipData内容放到系统剪贴板里。
            cm.setPrimaryClip(mClipData);
        }
    }
}
