package com.sangxiaonian.xcalendar.calendar;

import android.content.Context;
import android.graphics.RectF;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 作者： ${PING} on 2018/10/15.
 * 单选日期的日历
 */
public class SingleCalendarView extends BaseCalendarView {


    public boolean isSingle=true;

    public SingleCalendarView(Context context) {
        super(context);
    }

    public SingleCalendarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SingleCalendarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private int clickDay=-1;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                final float x = event.getX();
                final float y = event.getY();
                for (int i = 0; i < rectFS.size(); i++) {
                    RectF rectF = rectFS.get(i);
                    if (rectF.contains(x, y)) {
                        clickDay = getDayByIndex(i);
                        return true;
                    }
                }
            }
            break;
            case MotionEvent.ACTION_MOVE:
                return false;
            case MotionEvent.ACTION_UP:
                final float x = event.getX();
                final float y = event.getY();
                for (int i = 0; i < rectFS.size(); i++) {
                    RectF rectF = rectFS.get(i);
                    if (rectF.contains(x, y)&&clickDay==getDayByIndex(i)) {
                        //如果点击的日期没有被选中
                        if (!selectDates.contains(showDates.get(i))){
                            if (isSingle){
                                selectDates.clear();
                            }
                            selectDates.add(showDates.get(i));
                            if (calendarClickListener!=null){
                                calendarClickListener.onSelectChange(showDates.get(i), selectDates);
                            }
                        }else {
                            if (!isSingle){
                                selectDates.remove(showDates.get(i));
                                if (calendarClickListener!=null){
                                    calendarClickListener.onSelectChange(showDates.get(i), selectDates);
                                }
                            }
                        }
                        postInvalidate();
                        return true;
                    }
                }
                break;
        }

        return super.onTouchEvent(event);
    }


}
