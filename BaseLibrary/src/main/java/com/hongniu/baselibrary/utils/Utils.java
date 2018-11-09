package com.hongniu.baselibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.amap.api.services.core.PoiItem;
import com.google.gson.Gson;
import com.hongniu.baselibrary.R;
import com.hongniu.baselibrary.arouter.ArouterParamOrder;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.entity.LoginBean;
import com.hongniu.baselibrary.entity.LoginPersonInfor;
import com.sang.common.utils.SharedPreferencesUtils;
import com.sang.common.widget.dialog.builder.CenterAlertBuilder;

/**
 * 作者： ${PING} on 2018/8/15.
 */
public class Utils {

    /**
     * 获取登录相关信息
     */
    public static LoginBean getLoginInfor() {
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
        SharedPreferencesUtils.getInstance().remove(Param.LOGIN_ONFOR);
        SharedPreferencesUtils.getInstance().remove(Param.PERSON_ONFOR);
        ArouterUtils.getInstance().builder(ArouterParamOrder.activity_order_main).withString(Param.TRAN, Param.LOGIN_OUT).navigation(activity);
    }

    /**
     * 判断用户是否登录
     *
     * @return true 登录，false 未登录
     */
    public static boolean isLogin() {
        return getLoginInfor() != null;
    }


    /**
     * 获取个人信息
     */
    public static boolean checkInfor() {
        LoginPersonInfor personInfor = getPersonInfor();
        if (personInfor == null) {
            return false;
        } else if (TextUtils.isEmpty(personInfor.getAddress())
                || TextUtils.isEmpty(personInfor.getCity())
                || TextUtils.isEmpty(personInfor.getCityId())
                || TextUtils.isEmpty(personInfor.getContact())
                || TextUtils.isEmpty(personInfor.getDistrict())
                || TextUtils.isEmpty(personInfor.getDistrictId())
                || TextUtils.isEmpty(personInfor.getEmail())
                || TextUtils.isEmpty(personInfor.getIdnumber())
                || TextUtils.isEmpty(personInfor.getMobile())
                || TextUtils.isEmpty(personInfor.getProvince())
                || TextUtils.isEmpty(personInfor.getProvinceId())
                ) {
            return false;
        } else {
            return true;
        }


    }

    public static LoginPersonInfor getPersonInfor() {
        String string = SharedPreferencesUtils.getInstance().getString(Param.PERSON_ONFOR);
        if (!TextUtils.isEmpty(string)) {
            return new Gson().fromJson(string, LoginPersonInfor.class);
        }
        return new LoginPersonInfor();
    }

    /**
     * 储存登录信息
     *
     * @param data
     */
    public static void saveLoginInfor(LoginBean data) {
        SharedPreferencesUtils.getInstance().putString(Param.LOGIN_ONFOR, new Gson().toJson(data));

    }

    public static void savePersonInfor(LoginPersonInfor data) {
        //储存个人信息
        SharedPreferencesUtils.getInstance().putString(Param.PERSON_ONFOR, new Gson().toJson(data));
    }

    public static boolean querySetPassword( ) {
        boolean aBoolean = SharedPreferencesUtils.getInstance().getBoolean(Param.HASPAYPASSWORD);
        return aBoolean;
    }

    public static boolean setPassword(boolean has) {
        return SharedPreferencesUtils.getInstance().putBoolean(Param.HASPAYPASSWORD, has);
    }

    public static CenterAlertBuilder creatDialog(Context context,String title, String content, String btleft, String btRight) {
        return new CenterAlertBuilder()
                .setDialogTitle(title)
                .setDialogContent(content)
                .setBtLeft(btleft)
                .setBtRight(btRight)
                .setBtLeftColor(context.getResources().getColor(R.color.color_title_dark))
                .setBtRightColor(context.getResources().getColor(R.color.color_white))
                .setBtRightBgRes(R.drawable.shape_f06f28);
    }


    /**
     * 对地址显示进行处理
     * @param data
     */
    public static String dealPioPlace(PoiItem data) {
        String placeInfor="";
        if (data!=null&&data.getProvinceName()!=null) {
            if (data.getProvinceName().equals( data.getCityName())){
                placeInfor = data.getProvinceName()   + data.getAdName()
                        + data.getSnippet();
            }else {
                placeInfor = data.getProvinceName() + data.getCityName() + data.getAdName()
                        + data.getSnippet();
            }
        }
        return placeInfor;
    }
}