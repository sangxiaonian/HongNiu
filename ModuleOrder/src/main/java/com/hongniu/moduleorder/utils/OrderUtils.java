package com.hongniu.moduleorder.utils;

import android.app.Activity;
import android.text.TextUtils;

import com.fy.androidlibrary.toast.ToastUtils;
import com.hongniu.baselibrary.arouter.ArouterParamsApp;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.entity.H5Config;

/**
 * 作者： ${桑小年} on 2018/8/27.
 * 努力，为梦长留
 */
public class OrderUtils {

    public static void scanPDf(Activity activity, String url) {
        if (TextUtils.isEmpty(url)) {
            ToastUtils.getInstance().makeToast(ToastUtils.ToastType.NORMAL).show("保单号异常");
        } else {
            if (!url.contains(".pdf")) {
                H5Config h5Config = new H5Config("查看保单", url, false);
                ArouterUtils.getInstance().builder(ArouterParamsApp.activity_h5)
                        .withSerializable(Param.TRAN, h5Config).navigation(activity);
            } else {
                ArouterUtils.getInstance().builder(ArouterParamsApp.activity_pdf)
                        .withString(Param.TRAN, url)
                        .navigation(activity);
            }
        }
    }
}
