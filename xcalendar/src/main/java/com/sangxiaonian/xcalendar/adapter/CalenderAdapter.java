package com.sangxiaonian.xcalendar.adapter;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.sangxiaonian.xcalendar.entity.DateBean;

/**
 * 作者： ${PING} on 2018/10/15.
 */
public abstract class CalenderAdapter {


    /**
     * 绘制标记
     * @param canvas
     * @param mPaint

     */
    public abstract void drawMark(Canvas canvas, Paint mPaint, DateBean dateBean);


    /**
     * 绘制未选中的文字
     * @param canvas
     * @param rectF  绘制区域
     * @param mPaint
     */
    public abstract void drawNormalTxt(Canvas canvas, RectF rectF, Paint mPaint, DateBean dateBean);


    /**
     * 绘制未选中的背景区域
     * @param canvas
     * @param rectF
     */
    public abstract void drawNormalBg(Canvas canvas, RectF rectF, Paint mPaint, DateBean dateBean);

    /**
     * 绘制被选中的日期
     * @param canvas
     * @param rectF   本选中的
     * @param dateBean      日期信息
     */
    public abstract void drawSelectTxt(Canvas canvas, RectF rectF, Paint mPaint, DateBean dateBean);




    /**
     * 绘制选中情况的背景
     * @param canvas
     * @param rectF   需要绘制的区域
     * @param mPaint
     * @param dateBean  日期信息
     */
    public abstract void drawSelectBg(Canvas canvas, RectF rectF, Paint mPaint, DateBean dateBean);


    /**
     * 获取指定位置的文字大小
     * @return
     */
    public abstract float getTextSize( DateBean dateBean);


    /**
     * 绘制当前日期的文字
     * @param canvas
     * @param rectF
     * @param isSelect
     * @param mPaint
     * @param dateBean
     */
    public abstract void drawCurrentDayTxt(Canvas canvas, RectF rectF, boolean isSelect, Paint mPaint, DateBean dateBean);

    /**
     * 绘制当前日期的背景
     * @param canvas
     * @param rectF
     * @param isSelect
     * @param mPaint
     * @param dateBean
     */
    public abstract void drawCurrentDayBg(Canvas canvas, RectF rectF, boolean isSelect, Paint mPaint, DateBean dateBean);

}
