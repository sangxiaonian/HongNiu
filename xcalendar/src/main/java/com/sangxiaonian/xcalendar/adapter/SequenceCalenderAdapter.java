package com.sangxiaonian.xcalendar.adapter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.sangxiaonian.xcalendar.entity.DateBean;

import java.util.Calendar;

/**
 * 作者： ${PING} on 2018/10/15.
 * 默认情况下需要绘制的数据
 */
public class SequenceCalenderAdapter extends DefaultCalendarAdapter {


    /**
     * 连续选择的起末点日期
     */
    public DateBean startIndex, endIndx;

    Calendar startCalendar, endCalendar, currentCalendar;
    private RectF drawRectF;

    public SequenceCalenderAdapter(Context context) {
        super(context);
        startCalendar = Calendar.getInstance();
        endCalendar = Calendar.getInstance();
        currentCalendar = Calendar.getInstance();
        drawRectF = new RectF();
    }


    public void setStartIndex(DateBean startIndex) {
        this.startIndex = startIndex;
        if (startIndex == null) {
            startCalendar.clear();
        } else {
            startCalendar.set(startIndex.getYear(), startIndex.getMonth(), startIndex.getDay());
        }
    }

    public void setEndIndx(DateBean endIndx) {
        this.endIndx = endIndx;
        if (endIndx == null) {
            endCalendar.clear();
        } else {
            endCalendar.set(endIndx.getYear(), endIndx.getMonth(), endIndx.getDay());

        }
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
        currentCalendar.set(dateBean.getYear(), dateBean.getMonth(), dateBean.getDay());
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(selectBgColor);
        drawRectF.left=rectF.left;
        drawRectF.top=rectF.centerY()-radio;
        drawRectF.right=rectF.right;
        drawRectF.bottom=rectF.centerY()+radio;
        if (startIndex == null || endIndx == null||endIndx.getDay()==startIndex.getDay()) {
            canvas.drawCircle(rectF.centerX(), rectF.centerY(), radio, mPaint);
        } else if (startIndex.getDay()==dateBean.getDay()) {
            canvas.drawCircle(rectF.centerX(), rectF.centerY(), radio, mPaint);
            if (startCalendar.before(endCalendar)) {
                drawRectF.left=rectF.centerX();
                drawRectF.right=rectF.right;
                canvas.drawRect(drawRectF,mPaint);
            }else {
                drawRectF.right=rectF.centerX();
                drawRectF.left=rectF.left;
                canvas.drawRect(drawRectF,mPaint);
            }

        } else if (endIndx.getDay()==dateBean.getDay()) {
            canvas.drawCircle(rectF.centerX(), rectF.centerY(), radio, mPaint);
            if (endCalendar.before(startCalendar)) {
                drawRectF.left=rectF.centerX();
                drawRectF.right=rectF.right;
                canvas.drawRect(drawRectF,mPaint);
            }else {
                drawRectF.right=rectF.centerX();
                drawRectF.left=rectF.left;
                canvas.drawRect(drawRectF,mPaint);
            }
        } else {
            canvas.drawRect(drawRectF, mPaint);
        }

    }


}
