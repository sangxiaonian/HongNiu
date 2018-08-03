package com.sang.common.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * 作者： ${PING} on 2018/8/3.
 */
public class CommonUtils {

    /**
     * 携带指定的号码去拨号界面
     * @param cxt
     * @param num
     */
    public static void toDial(Context cxt, String num) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + num));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        cxt.startActivity(intent);
    }

}
