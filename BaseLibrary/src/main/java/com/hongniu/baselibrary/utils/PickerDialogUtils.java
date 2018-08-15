package com.hongniu.baselibrary.utils;

import android.content.Context;
import android.graphics.Color;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.hongniu.baselibrary.R;

import java.util.Calendar;


/**
 * 作者： ${PING} on 2017/9/28.
 */

public class PickerDialogUtils {


    public static TimePickerView initTimePicker(Context mContext, TimePickerView.OnTimeSelectListener listener, boolean[] type) {
        TimePickerView pvTime = creatTimePicker(mContext, listener, type).build();
        pvTime.setKeyBackCancelable(false);//系统返回键监听屏蔽掉
        return pvTime;
    }

    public static TimePickerView.Builder creatTimePicker(Context mContext, TimePickerView.OnTimeSelectListener listener, boolean[] type) {
        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
        Calendar selectedDate = Calendar.getInstance();

        Calendar startDate = Calendar.getInstance();
        startDate.set(2013, 0, 23);

        Calendar endDate = Calendar.getInstance();
        endDate.set(2020, 11, 28);
        int size = 15;
        int contentSize = 23;
        if (type == null) {
            type = new boolean[]{true, true, true, false, false, false};
        }

        //时间选择器
        TimePickerView.Builder pvTime = new TimePickerView.Builder(mContext, listener)
                .setType(type)
                .setLabel("", "", "", "", "", "") //设置空字符串以隐藏单位提示   hide label
                .setRangDate(startDate, endDate)
                .setDate(selectedDate)
                .setTitleText("")
                .setSubmitColor(mContext.getResources().getColor(R.color.color_title_dark))
                .setCancelColor(mContext.getResources().getColor(R.color.color_title_dark))
                .setTitleBgColor(mContext.getResources().getColor(R.color.color_white))
                .setSubmitText("确定")
                .setSubCalSize(size)
                .setCancelText("取消")
                .setContentSize(contentSize)//设置滚轮文字大小
                .setDividerColor(Color.LTGRAY)//设置分割线的颜色
                .setBgColor(Color.WHITE)
                .setTextColorCenter(Color.BLACK)
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setBackgroundId(0x66000000) //设置外部遮罩颜色
                ;
        return pvTime;
    }


    /**
     * 初始化Dialog
     */
    public static OptionsPickerView initPickerDialog(Context mContext, OptionsPickerView.OnOptionsSelectListener listener) {
        return creatPickerDialog(mContext, listener).build();
    }

    /**
     * 初始化Dialog
     */
    public static OptionsPickerView.Builder creatPickerDialog(Context mContext, OptionsPickerView.OnOptionsSelectListener listener) {
        int size = 14;
        int contentSize = 15;
        OptionsPickerView.Builder pvOptions = new OptionsPickerView.Builder(mContext, listener)
                .setTitleColor(mContext.getResources().getColor(R.color.color_content_light))
                .setSubmitColor(mContext.getResources().getColor(R.color.color_title_dark))
                .setCancelColor(mContext.getResources().getColor(R.color.color_title_dark))
                .setTitleBgColor(mContext.getResources().getColor(R.color.color_white))
                .setSubmitText("确定")
                .setSubCalSize(size)
                .setCancelText("取消")
                .setContentTextSize(contentSize)//设置滚轮文字大小
                .setDividerColor(Color.LTGRAY)//设置分割线的颜色
                .setSelectOptions(0, 1)//默认选中项
                .setBgColor(Color.WHITE)
                .setTextColorCenter(Color.BLACK)
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setBackgroundId(0x66000000) //设置外部遮罩颜色
                ;


        return pvOptions;
    }
}
