package com.hongniu.baselibrary.utils.clickevent;

import android.content.Context;
import android.text.TextUtils;

import com.fy.androidlibrary.utils.CollectionUtils;
import com.fy.androidlibrary.utils.CommonUtils;
import com.fy.androidlibrary.utils.ConvertUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.fy.androidlibrary.utils.DeviceUtils;
import com.fy.androidlibrary.utils.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 作者： ${桑小年} on 2018/12/2.
 * 努力，为梦长留
 * 点击事件统计
 */
public class ClickEventUtils {

    String TAG = "clickevent";


    private static class Inner {
        private static ClickEventUtils utils = new ClickEventUtils();
    }

    private ClickEventUtils() {
    }

    public static ClickEventUtils getInstance() {
        return Inner.utils;
    }

    private void init() {

    }


    /**
     * 添加一次点击事件
     *
     * @param name 点击事件的名称
     */
    public void onClick(String name) {
        Gson gson = new Gson();
        List<ItemClickEventBean> clickEvent = getClickEvent();
        ItemClickEventBean clickEventBean = new ItemClickEventBean();
        clickEventBean.setEventkey(name);
        clickEventBean.setTime(ConvertUtils.formatTime(new Date(), "yyyy-MM-dd HH:mm:ss"));
        clickEvent.add(clickEventBean);
        SharedPreferencesUtils.getInstance().putString(TAG, gson.toJson(clickEvent));
    }


    public List<ItemClickEventBean> getClickEvent() {
        String string = SharedPreferencesUtils.getInstance().getString(TAG);
        List<ItemClickEventBean> stringList = null;
        if (!TextUtils.isEmpty(string)) {
            try {
                stringList = new Gson().fromJson(string, new TypeToken<List<ItemClickEventBean>>() {
                }.getType());
            } catch (Exception e) {
                e.printStackTrace();
                stringList=new ArrayList<>();
                clear();
            }

        } else {
            stringList = new ArrayList<>();
        }
        return stringList;
    }


    public ClickEventBean getEventParams(Context context) {
        ClickEventBean eventBean = new ClickEventBean();
        eventBean.setSystemType("android");
        try {
            eventBean.setAppVersion(DeviceUtils.getVersionName(context));
        } catch (Exception e) {
            e.printStackTrace();
        }
        eventBean.setSystemModel(DeviceUtils.getDeviceBrand());
        List<ItemClickEventBean> clickEvent = getClickEvent();
        eventBean.setEvents(clickEvent);
        return CollectionUtils.isEmpty(clickEvent) ? null : eventBean;

    }

    public void clear() {
        SharedPreferencesUtils.getInstance().remove(TAG);
    }

}
