package com.sang.thirdlibrary.share;

import android.content.Context;
import android.graphics.Bitmap;

import com.sang.thirdlibrary.share.wechat.WeChartShareSession;

/**
 * 作者： ${PING} on 2018/10/18.
 * 分享
 */
public class ShareClient {

    /**
     * 0 朋友圈 1好友
     */
    private int type;

    public ShareClient(int type) {
        this.type = type;
    }

    public void shareUrl(Context activity, String url, String title, String des, Bitmap bmp) {
        if (type == 1) {
            new WeChartShareSession().shareUrl(activity, url, title, des, bmp);
        } else {
            new WeChartShareSession().shareUrl(activity, url, title, des, bmp);
        }
    }

}
