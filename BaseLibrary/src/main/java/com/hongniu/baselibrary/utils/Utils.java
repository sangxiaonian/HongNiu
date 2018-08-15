package com.hongniu.baselibrary.utils;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.entity.LoginBean;
import com.sang.common.utils.SharedPreferencesUtils;

/**
 * 作者： ${PING} on 2018/8/15.
 */
public class Utils {

    public static LoginBean getPgetPersonInfor() {
        String string = SharedPreferencesUtils.getInstance().getString(Param.LOGIN_ONFOR);
        if (!TextUtils.isEmpty(string)) {
            return new Gson().fromJson(string, LoginBean.class);
        }
        return null;
    }
}