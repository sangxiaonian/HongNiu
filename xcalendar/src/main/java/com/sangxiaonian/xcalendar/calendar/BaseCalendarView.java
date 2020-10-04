package com.sangxiaonian.xcalendar.calendar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.sangxiaonian.xcalendar.adapter.CalenderAdapter;
import com.sangxiaonian.xcalendar.adapter.DefaultCalendarAdapter;
import com.sangxiaonian.xcalendar.entity.DateBean;
import com.sangxiaonian.xcalendar.inter.CalendarControl;
import com.sangxiaonian.xcalendar.utils.CalendarUtils;
import com.sangxiaonian.xcalendar.utils.JLog;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者： ${PING} on 2018/10/15.
 */
public class BaseCalendarView extends View {
    protected CalendarUtils calendar;
    protected List<RectF> rectFS;
    protected Paint mPaint;
    protected CalenderAdapter adapter;
    protected CalendarControl.OnCalendarClickListener calendarClickListener;



    protected final int widthCellCount = 7;//横向单元格个数 固定 周日到周一
    protected final int heightCellCount = 6;//纵向单元格最多6个
    protected int startWeek;//开始的星期天日期 //默认周日

    protected int year, month, day;//当前页面中，展示的日历时间

    protected DateBean currentDate;//当前日期


    protected int firstDayOfWeek;
    protected int dayCount;//当月共有多少天


    protected int widthCell;
    protected int heightCell;
    protected int mWidth;
    protected int mHeight;


    protected int startIndex = 1;//从星期天第几天开始 周日为1 周一为2

    protected List<DateBean> showDates;//需要显示出来的日期
    protected List<DateBean> selectDates;//选中的日期


    public BaseCalendarView(Context context) {
        this(context, null, 0);

    }

    public BaseCalendarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public BaseCalendarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }

    protected void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        calendar = new CalendarUtils();
        showDates = new ArrayList<>();
        selectDates = new ArrayList<>();
        year = calendar.getCurrentYear();
        month = calendar.getCurrentMonth();
        day = calendar.getCurrentDay();
        currentDate = new DateBean(year, month, day);
        firstDayOfWeek = calendar.getCurrentMonthFirstDay();
        dayCount = calendar.getCuttentMonthDayCount();
        rectFS = new ArrayList<>();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        adapter = new DefaultCalendarAdapter(context);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        widthCell = w / widthCellCount;
        heightCell = h / heightCellCount;
        upRect(dayCount, year, month);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBackGround(canvas, rectFS);
    }


    protected int getDayByIndex(int i) {
        return i + 1 + startIndex - 1;
    }

    protected void drawBackGround(Canvas canvas, List<RectF> rectFS) {
        if (rectFS == null) {
            return;
        }
        mPaint.setStyle(Paint.Style.STROKE);
        for (int i = 0; i < rectFS.size(); i++) {
            RectF rectF = rectFS.get(i);
            mPaint.setTextAlign(Paint.Align.CENTER);
            DateBean dateBean = showDates.get(i);
            //进行绘制
            //绘制当天数据
            if (dateBean.getDay() == currentDate.getDay() && dateBean.getMonth() == currentDate.getMonth() && dateBean.getYear() == dateBean.getYear()) {//当天
                adapter.drawCurrentDayBg(canvas, rectF, selectDates.contains(dateBean), mPaint, dateBean);
                adapter.drawCurrentDayTxt(canvas, rectF, selectDates.contains(dateBean), mPaint, dateBean);
                //绘制选中数据
            } else if (selectDates.contains(dateBean)) {
                adapter.drawSelectBg(canvas, rectF, mPaint, dateBean);
                adapter.drawSelectTxt(canvas, rectF, mPaint, dateBean);
                //绘制未选中数据
            } else {
                adapter.drawNormalBg(canvas, rectF, mPaint, dateBean);
                adapter.drawNormalTxt(canvas, rectF, mPaint, dateBean);
            }
            //绘制其他的标识
            adapter.drawMark(canvas, mPaint, dateBean);
        }
    }


    public void setAdapter(CalenderAdapter adapter) {
        this.adapter = adapter;
    }

    /**
     * 设置当前显示的日期，此时无选中日期，无当前日期
     *
     * @param year  年
     * @param month 月 从0开始
     */
    public void setCurrentDate(int year, int month) {
        this.year = year;
        this.month = month;
        this.day = -1;

        firstDayOfWeek = calendar.getMonthFirstDayOfWeek(year, month);
        JLog.i(firstDayOfWeek+">>>"+year+">>>"+month);
        dayCount = calendar.getMonthDayCount(year, month);
        upRect(dayCount, year, month);
        postInvalidate();

    }


    private void upRect(int dayCount, int year, int month) {
        if (rectFS == null) {
            rectFS = new ArrayList<>();
        }else {
            rectFS.clear();
        }
        showDates.clear();
        selectDates.clear();
        int startX, startY = 0;
        startX = widthCell * (firstDayOfWeek - 1);
        for (int i = 0; i < dayCount; i++) {
            if (startX >= mWidth - widthCell) {
                startX = 0;
                startY += heightCell;
            }
            showDates.add(new DateBean(year, month, i + 1));
            rectFS.add(new RectF(startX, startY, startX + widthCell, startY + heightCell));
            startX += widthCell;
        }
    }


    /**
     * 返回当前所选的全部日期
     * @return
     */
    public List<DateBean> getSelectDates() {
        return selectDates;
    }

    public void setCalendarClickListener(CalendarControl.OnCalendarClickListener calendarClickListener) {
        this.calendarClickListener = calendarClickListener;
    }

    public void clearSelects() {
        selectDates.clear();
        postInvalidate();
    }
}
