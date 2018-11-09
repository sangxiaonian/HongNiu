package com.sangxiaonian.xcalendar.adapter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import com.sangxiaonian.xcalendar.entity.DateBean;
import com.sangxiaonian.xcalendar.utils.DeviceUtils;

/**
 * 作者： ${PING} on 2018/10/15.
 * 默认情况下需要绘制的数据
 */
public class DefaultCalendarAdapter extends CalenderAdapter {

    protected int selectBgColor;
    protected int normalBgColor;
    protected int selectTxtColor;
    protected int normalTxtColor;

    public    int radio;
    public int textSize;

    public DefaultCalendarAdapter(Context context) {
        selectBgColor = Color.parseColor("#F06F28");
        normalBgColor = Color.TRANSPARENT;
        selectTxtColor = Color.WHITE;
        normalTxtColor = Color.parseColor("#333333");
        radio= DeviceUtils.dip2px(context,15);
        textSize= DeviceUtils.dip2px(context,12);
    }

    /**
     * 绘制标记
     *
     * @param canvas
     * @param mPaint

     */
    @Override
    public void drawMark(Canvas canvas, Paint mPaint, DateBean dateBean) {

    }


    /**
     * 绘制未选中的文字
     *
     * @param canvas
     * @param rectF  绘制区域
     * @param mPaint
     */
    @Override
    public void drawNormalTxt(Canvas canvas, RectF rectF, Paint mPaint, DateBean dateBean) {
        mPaint.setColor(normalTxtColor);
        mPaint.setTextSize(getTextSize(dateBean));
        final String str = String.valueOf(dateBean.getDay());
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        float distance = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
        canvas.drawText(str, 0, str.length(), rectF.centerX(), rectF.centerY() + distance, mPaint);
    }


    /**
     * 绘制未选中的背景区域
     *
     * @param canvas
     * @param rectF
     */
    @Override
    public void drawNormalBg(Canvas canvas, RectF rectF, Paint mPaint, DateBean dateBean) {
        mPaint.setColor(normalBgColor);
        canvas.drawRect(rectF, mPaint);
    }

    /**
     * 绘制被选中的日期
     *
     * @param canvas
     * @param rectF  本选中的
     */
    @Override
    public void drawSelectTxt(Canvas canvas, RectF rectF, Paint mPaint, DateBean dateBean) {
        mPaint.setColor(selectTxtColor);
        mPaint.setTextSize(getTextSize(dateBean));
        final String str = String.valueOf(dateBean.getDay());
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        float distance = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
        canvas.drawText(str, 0, str.length(), rectF.centerX(), rectF.centerY() + distance, mPaint);
    }

    /**
     * 绘制选中情况的背景
     *
     * @param canvas
     * @param rectF  需要绘制的区域
     * @param mPaint

     */
    @Override
    public void drawSelectBg(Canvas canvas, RectF rectF, Paint mPaint, DateBean dateBean) {
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(selectBgColor);
        canvas.drawCircle(rectF.centerX(),rectF.centerY(),radio, mPaint);
    }

    /**
     * 获取指定位置的文字大小
     *
     * @param dateBean
     * @return
     */
    @Override
    public float getTextSize(DateBean dateBean) {
        return textSize;
    }

    /**
     * 绘制当前日期的文字
     *  @param canvas
     * @param rectF
     * @param isSelect
     * @param mPaint
     * @param dateBean
     */
    @Override
    public void drawCurrentDayTxt(Canvas canvas, RectF rectF, boolean isSelect, Paint mPaint, DateBean dateBean) {
        if (isSelect){
            drawSelectTxt(canvas,rectF,mPaint,dateBean);
        }else {
            drawNormalTxt(canvas,rectF,mPaint,dateBean);

        }
    }

    /**
     * 绘制当前日期的背景
     *  @param canvas
     * @param rectF
     * @param isSelect
     * @param mPaint
     * @param dateBean
     */
    @Override
    public void drawCurrentDayBg(Canvas canvas, RectF rectF, boolean isSelect, Paint mPaint, DateBean dateBean) {
        if (isSelect){
            drawSelectBg(canvas,rectF,mPaint,dateBean);
        }else {
            drawNormalBg(canvas,rectF,mPaint,dateBean);

        }
    }


}
