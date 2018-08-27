package com.hongniu.moduleorder.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.sang.common.utils.ToastUtils;

/**
 * 作者： ${桑小年} on 2018/8/27.
 * 努力，为梦长留
 */
public class OrderUtils {

    public static void scanPDf(Activity activity,String url){
        if (TextUtils.isEmpty(url)){
            ToastUtils.getInstance().makeToast(ToastUtils.ToastType.NORMAL).show("保单号异常");
        }else {
            try {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                activity.startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
