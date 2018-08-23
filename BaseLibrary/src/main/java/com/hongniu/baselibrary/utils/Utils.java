package com.hongniu.baselibrary.utils;

import android.app.Activity;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.hongniu.baselibrary.arouter.ArouterParamOrder;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.entity.LoginBean;
import com.sang.common.utils.SharedPreferencesUtils;

/**
 * 作者： ${PING} on 2018/8/15.
 */
public class Utils {

    /**
     * 获取登录相关信息
     */
    public static LoginBean getPgetPersonInfor() {
        String string = SharedPreferencesUtils.getInstance().getString(Param.LOGIN_ONFOR);
        if (!TextUtils.isEmpty(string)) {
            return new Gson().fromJson(string, LoginBean.class);
        }
        return null;
    }


    /**
     * 退出登录
     */
    public static void loginOut(Activity activity) {
        ArouterUtils.getInstance().builder(ArouterParamOrder.activity_order_main).withString(Param.TRAN,Param.LOGIN_OUT).navigation(activity);
    }

    /**
     * 判断用户是否登录
     * @return true 登录，false 未登录
     */
    public static boolean isLogin() {
        return getPgetPersonInfor()!=null;
    }
}