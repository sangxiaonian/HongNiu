package com.sang.common.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.support.animation.DynamicAnimation;
import android.support.animation.FlingAnimation;
import android.support.animation.FloatValueHolder;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;

import com.sang.common.utils.DeviceUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者： ${PING} on 2018/8/10.
 * <p>
 * 柱状图
 */
public class VistogramView extends View implements DynamicAnimation.OnAnimationEndListener, DynamicAnimation.OnAnimationUpdateListener {

    private Paint mPaint;
    private int cellWidth;//柱状图款度
    private int gap;

    private int bigGap;//大图之间的距离

    private Point hValue;
    private int colorLine;
    private int textColor;

    private PointF orginPoint;//坐标源点，左下角
    private PointF endPoint;//坐标终点，右上角


    private int numbersX = 3;//X轴上面所能显示的坐标数
    private float textLineGap;//文字和坐标之间的距离

    private int alpha = 100;

    /**
     * 当前X坐标
     */
    private int currentX;

    List<List<VistogramBean>> datas = new ArrayList<>();

    private int startX;

    private VelocityTracker mVelocityTracker;
    private FlingAnimation fling;

    Rect textRectX = new Rect();
    Rect textRectY = new Rect();
    private Rect rect = new Rect();


    public VistogramView(Context context) {
        this(context, null, 0);
    }

    public VistogramView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VistogramView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        gap = DeviceUtils.dip2px(context, 5);
        cellWidth = DeviceUtils.dip2px(context, 20);
        textLineGap = DeviceUtils.dip2px(context, 15);
        hValue = new Point();
        colorLine = Color.parseColor("#eeeeee");
        textColor = Color.parseColor("#6d6d6d");
        orginPoint = new PointF();
        endPoint = new PointF();


        mVelocityTracker = VelocityTracker.obtain();
        fling = new FlingAnimation(new FloatValueHolder(0));
        fling.addEndListener(this);
        fling.addUpdateListener(this);


    }


    private void cancleFling() {
        fling.cancel();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        initData();
    }

    private String markX = "12月";

    private int vistogramWidth;//绘图区域宽度
    private int maxWidth;//图标最大宽度

    //初始化各项参数
    private void initData() {
        mPaint.setTextSize(DeviceUtils.dip2px(getContext(), 11));
        mPaint.getTextBounds(markX, 0, markX.length(), textRectX);
        mPaint.getTextBounds(String.valueOf(hValue.x), 0, String.valueOf(hValue.x).length(), textRectY);
        mPaint.setStrokeWidth(2);
        mPaint.setColor(colorLine);
        orginPoint.x = mPaint.getStrokeWidth() + textRectY.width() + textLineGap;
        orginPoint.y = getMeasuredHeight() - mPaint.getStrokeWidth() - textRectX.height() - textLineGap;
        endPoint.x = getMeasuredWidth();
        endPoint.y = 0;
        vistogramWidth = (int) (endPoint.x - orginPoint.x);
        bigGap = (int) (vistogramWidth * 1.0f / numbersX);
        maxWidth = bigGap * datas.size();


    }

    private float downX, downY;


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (0 == downY && downX == 0) {
            downX = event.getRawX();
            downY = event.getRawX();
        }

        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.computeCurrentVelocity(1000);
        mVelocityTracker.addMovement(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                cancleFling();
                downX = event.getRawX();
                downY = event.getRawX();
                break;
            case MotionEvent.ACTION_MOVE:
                final float rawX = event.getRawX();
                final float rawY = event.getRawY();
                if (Math.abs(rawX) > Math.abs(rawY - downY)) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                changeStartX((int) (startX + (rawX - downX)));
                downX = rawX;
                downY = rawY;
                break;
            default:
                startFling(mVelocityTracker.getXVelocity());
                downY = 0;
                downX = 0;
                mVelocityTracker.clear();
                mVelocityTracker.recycle();
                mVelocityTracker = null;
                break;

        }


        return true;
    }

    private void changeStartX(int value) {
        if (vistogramWidth >= maxWidth) {
            startX = 0;
            return;
        }
        this.startX = value >= 0 ? 0 : value < -(maxWidth - vistogramWidth) ? -(maxWidth - vistogramWidth) : value;
        postInvalidate();
    }


    private void startFling(float xVelocity) {


        if (vistogramWidth >= maxWidth) {
            startX = 0;
            return;
        }

        fling.setStartValue(startX)
                .setMaxValue(1)
                .setMinValue(vistogramWidth - maxWidth)
                .setStartVelocity(xVelocity)
                .start();
        ;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制坐标线
        drawLine(canvas);
        //绘制柱状图
        if (!datas.isEmpty()) {
            canvas.save();
            rect.left = (int) orginPoint.x;
            rect.top = 0;
            rect.right = (int) endPoint.x;
            rect.bottom = getMeasuredHeight();
            canvas.clipRect(rect);
            for (int i = 0; i < datas.size(); i++) {
                drawVistogram(canvas, i, datas.get(i));
            }
            canvas.restore();
        }

        //绘制Y坐标
        drawCoordinateY(canvas);
        drawCoordinateX(canvas);
        //最后绘制一层遮罩
//        drawShade(canvas);

    }

    private void drawCoordinateX(Canvas canvas) {
        final Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        canvas.save();
        rect.left = (int) orginPoint.x;
        rect.top = 0;
        rect.right = (int) endPoint.x;
        rect.bottom = getMeasuredHeight();
        canvas.clipRect(rect);
        mPaint.setColor(textColor);
        mPaint.setTextAlign(Paint.Align.CENTER);
        for (int i = 0; i < datas.size(); i++) {
            if (i==currentX){
                mPaint.setAlpha(255);
            }else {
                mPaint.setAlpha(alpha);
            }
            final int start = (int) (orginPoint.x + i * bigGap) + startX;
            List<VistogramBean> list = datas.get(i);
            if (list != null && list.size() > 0) {
                String xMark = list.get(0).xMark;
                canvas.drawText(xMark, start + bigGap / 2, orginPoint.y + textRectX.height() + textLineGap + (fontMetrics.descent - fontMetrics.bottom), mPaint);
            }
        }
        canvas.restore();


    }

    //绘制遮罩
    private void drawShade(Canvas canvas) {
        rect.left = 0;
        rect.top = 0;
        rect.right = (int) endPoint.x - bigGap;
        rect.bottom = getMeasuredHeight();
        mPaint.setColor(Color.parseColor("#aaffffff"));
        canvas.drawRect(rect, mPaint);
    }


    /**
     * 绘制柱状图
     *
     * @param canvas
     * @param i
     * @param vistogramBeans
     */
    private void drawVistogram(Canvas canvas, int i, List<VistogramBean> vistogramBeans) {


        final int start = (int) (orginPoint.x + i * bigGap) + startX;
        final int proWidth = cellWidth * vistogramBeans.size() + (gap * vistogramBeans.size() - gap);
        final int cellX = (bigGap - proWidth) / 2 + start;

        final Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();

        for (int j = 0; j < vistogramBeans.size(); j++) {
            VistogramBean vistogramBean = vistogramBeans.get(j);
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setColor(vistogramBean.color);
            if (i==currentX){
                mPaint.setAlpha(255);
            }else {
                mPaint.setAlpha(alpha);
            }

            final float present = vistogramBean.value / hValue.x;
            rect.left = cellX + cellWidth * j + gap * j;
            rect.top = (int) (orginPoint.y - (orginPoint.y - endPoint.y) * present * 3 / 4);
            rect.right = rect.left + cellWidth;
            rect.bottom = (int) orginPoint.y;
            canvas.drawRect(rect, mPaint);
            mPaint.setStyle(Paint.Style.FILL);
            rect.left = cellX + cellWidth * j + gap * j;
            rect.top = (int) (orginPoint.y - (orginPoint.y - endPoint.y) * present * 3 / 4);
            rect.right = rect.left + cellWidth;
            rect.bottom = (int) orginPoint.y;
            canvas.save();
            canvas.translate(rect.left, rect.top);
            canvas.rotate(-35);
            canvas.translate(-rect.left, -rect.top);
            mPaint.setTextAlign(Paint.Align.LEFT);
            canvas.drawText(String.valueOf(vistogramBean.value), rect.centerX(), rect.top - (fontMetrics.descent - fontMetrics.bottom), mPaint);
            canvas.restore();
        }


    }


    private void drawLine(Canvas canvas) {
        mPaint.setAlpha(255);
        //绘制纵向线
        mPaint.setColor(colorLine);
        final int YstartX = (int) (orginPoint.x - mPaint.getStrokeWidth() / 2);
        final int YstartY = 0;
        final int YEndX = YstartX;
        final int YsEndY = (int) (orginPoint.y + (mPaint.getStrokeWidth() / 2));
        canvas.drawLine(YstartX, YstartY, YEndX, YsEndY, mPaint);
        //绘制横轴
        final float cellY = (orginPoint.y - endPoint.y) / 4;
        for (int i = 0; i < 4; i++) {
            final float y = orginPoint.y - cellY * i;
            canvas.drawLine(orginPoint.x, y, endPoint.x, y, mPaint);
        }

    }

    //绘制坐标
    private void drawCoordinateY(Canvas canvas) {
        //绘制纵坐标
        final float cellY = (orginPoint.y - endPoint.y) / 4;
        mPaint.setTextAlign(Paint.Align.RIGHT);
        mPaint.setColor(textColor);

        mPaint.setAlpha(alpha);

        final Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        float textHight = fontMetrics.top / 2 + fontMetrics.bottom / 2;
        for (int i = 0; i < 4; i++) {
            final float y = orginPoint.y - cellY * i;
            canvas.drawText(String.valueOf(hValue.x * (i) / 3), textRectY.width(), (y - textHight), mPaint);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        cancleFling();
    }


    public void setCurrentSelect(int currentSelect){
        currentX=currentSelect;
        changeStartX(-(currentX-2)*bigGap);
        postInvalidate();
    }



    /**
     * 惯性滑动结束
     *
     * @param animation animation that has ended or was canceled
     * @param canceled  whether the animation has been canceled
     * @param value     the final value when the animation stopped
     * @param velocity  the final velocity when the animation stopped
     */
    @Override
    public void onAnimationEnd(DynamicAnimation animation, boolean canceled, float value, float velocity) {

    }

    /**
     * 惯性滑动
     *
     * @param animation animation that the update listener is added to
     * @param value     the current value of the animation
     * @param velocity  the current velocity of the animation
     */
    @Override
    public void onAnimationUpdate(DynamicAnimation animation, float value, float velocity) {
        changeStartX((int) value);
    }

    public void setDatas(List<List<VistogramBean>> datas) {
        this.datas.clear();
        this.datas.addAll(datas);
        int max = 0;
        int min = 0;
        for (List<VistogramBean> data : datas) {
            if (data != null && !data.isEmpty()) {
                for (VistogramBean datum : data) {
                    max = max > datum.value ? max : (int) datum.value;
                    min = min > datum.value ? (int) datum.value : min;
                }
            }
        }
        hValue.x = (int) ((Math.ceil(max / 3000) + 1) * 3000);
        initData();
        postInvalidate();

    }


    public static class VistogramBean {
        public int color;
        public float value;
        public String xMark = "12月";

        /**
         * 颜色
         *
         * @param color 颜色
         * @param value 大小
         * @param xMark 横坐标表示
         */
        public VistogramBean(int color, float value, String xMark) {
            this.color = color;
            this.value = value;
            this.xMark = xMark;
        }
    }
}
