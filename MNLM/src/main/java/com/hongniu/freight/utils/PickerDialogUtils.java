package com.hongniu.freight.utils;

import android.content.Context;
import android.graphics.Color;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.hongniu.freight.R;

import java.util.Calendar;


/**
 * 作者： ${PING} on 2017/9/28.
 */

public class PickerDialogUtils {


    public static TimePickerView initTimePicker(Context mContext, OnTimeSelectListener listener, boolean[] type) {
        TimePickerView pvTime = creatTimePicker(mContext, listener, type).build();
        pvTime.setKeyBackCancelable(false);//系统返回键监听屏蔽掉

        return pvTime;
    }

    public static TimePickerBuilder creatTimePicker(Context mContext, OnTimeSelectListener listener, boolean[] type) {
        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
        Calendar selectedDate = Calendar.getInstance();

        Calendar startDate = Calendar.getInstance();
        startDate.set(selectedDate.get(Calendar.YEAR)-3, 0, 23);

        Calendar endDate = Calendar.getInstance();
        endDate.set(selectedDate.get(Calendar.YEAR)+3, 11, 28);
        int size = 15;
        int contentSize = 23;
        if (type == null) {
            type = new boolean[]{true, true, true, false, false, false};
        }

        //时间选择器
        TimePickerBuilder pvTime = new TimePickerBuilder(mContext, listener)
                .setType(type)
                .setLabel("", "", "", "", "", "") //设置空字符串以隐藏单位提示   hide label
                .setRangDate(startDate, endDate)
                .setDate(selectedDate)
                .setTitleText("")
                .setTitleColor(mContext.getResources().getColor(R.color.color_of_999999))
                .setSubmitColor(mContext.getResources().getColor(R.color.color_of_040000))
                .setCancelColor(mContext.getResources().getColor(R.color.color_of_040000))
                .setTitleBgColor(mContext.getResources().getColor(R.color.color_of_ffffff))
                .setSubmitText("确定")
                .setSubCalSize(size)
                .setCancelText("取消")
                .setContentTextSize(contentSize)//设置滚轮文字大小
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
    public static OptionsPickerView initPickerDialog(Context mContext, OnOptionsSelectListener listener) {
        return creatPickerDialog(mContext, listener).build();
    }

    /**
     * 初始化Dialog
     */
    public static OptionsPickerBuilder creatPickerDialog(Context mContext, OnOptionsSelectListener listener) {
        int size = 14;
        int contentSize = 15;
        OptionsPickerBuilder pvOptions = new OptionsPickerBuilder(mContext, listener)
                .setTitleColor(mContext.getResources().getColor(R.color.color_of_999999))
                .setSubmitColor(mContext.getResources().getColor(R.color.color_of_040000))
                .setCancelColor(mContext.getResources().getColor(R.color.color_of_040000))
                .setTitleBgColor(mContext.getResources().getColor(R.color.color_of_ffffff))
                .setSubmitText("确定")
                .setLineSpacingMultiplier(1.5f)
                .setSubCalSize(size)
                .setCancelText("取消")
                .setContentTextSize(contentSize)//设置滚轮文字大小
                .setDividerColor(Color.LTGRAY)//设置分割线的颜色
                .setSelectOptions(0, 0,0)//默认选中项
                .setBgColor(Color.WHITE)
                .setTextColorCenter(Color.BLACK)
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setBackgroundId(0x66000000) //设置外部遮罩颜色
                ;
        return pvOptions;
    }
}
