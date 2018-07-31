package com.sang.common.utils;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;


public class ToastUtils {
    private static Toast toast = null;
    private static Context context;

    public static void init(Context cnt) {
        context = cnt;
    }

    public static void showTextToast(String msg) {
        showTextToast(context, msg);
    }

    public static void showTextToast(int stringId) {
        showTextToast(context, context.getString(stringId));
    }


    public static void showTextToast(Context context, String msg) {
        if (toast == null) {
            if (context instanceof Activity){
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
            }else {
                toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
            }
        } else {
            toast.setText(msg);
        }
        toast.show();
    }

    public static void showTextToast(Context context, int msg) {
        showTextToast(context, context.getString(msg));
    }


}