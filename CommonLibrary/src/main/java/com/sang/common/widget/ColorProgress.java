package com.sang.common.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.fy.androidlibrary.utils.DeviceUtils;

/**
 * 作者： ${PING} on 2018/8/6.
 */
public class ColorProgress extends View {

    private int textSize;//进度大小
    private int textColor;//文字颜色

    private int passColor;//已经经过进度条颜色
    private int unPasscolor;//未经过的进度条颜色
    private int maxValue;//最大进度值
    private int currentValue;//当前进度值
    private int proWidth;//进度条宽度

    private Paint mPaint;

    private final String STANDARD = "100%";

    private Rect textRect;

    private float gap;

    public ColorProgress(Context context) {
        this(context, null, 0);

    }


    public ColorProgress(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public ColorProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }

    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        textSize = DeviceUtils.dip2px(context, 18);
        passColor = Color.parseColor("#F06F28");
        textColor = Color.parseColor("#F06F28");
        unPasscolor = Color.parseColor("#EEEEEE");
        maxValue = 100;
        proWidth = DeviceUtils.dip2px(context, 6);
        gap = DeviceUtils.dip2px(context, 10);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textRect = new Rect();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        final int width = getMeasuredWidth();
        final float pro = progress();
        final String text = ((int) (progress() * 100)) + "%";



        mPaint.setStrokeWidth(1);
        mPaint.setTextSize(textSize);
        mPaint.getTextBounds(STANDARD, 0, STANDARD.length(), textRect);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setColor(textColor);
        canvas.drawText(text, textStart(pro, width), textRect.height(), mPaint);
        mPaint.setStrokeWidth(proWidth);
        mPaint.setStrokeCap(Paint.Cap.ROUND);



        final float y=textRect.height() + gap + proWidth / 2;
        mPaint.setColor(unPasscolor);

        canvas.drawLine(proWidth, y, width - proWidth, y, mPaint);
        mPaint.setColor(passColor);

        final float end = (width - proWidth * 2) * pro + proWidth;
        canvas.drawLine(proWidth, y, end, y, mPaint);

    }

    private float textStart(float progress, float width) {
        final float current = progress * width;
        final float textWidth = textRect.width() / 2;
        return (current < textWidth) ? textWidth : (current > width - textWidth) ? (width - textWidth) : current;
    }


    private float progress() {
        return currentValue * 1.0f / maxValue;
    }

    public void setCurrentValue(int currentValue) {
        if (currentValue < 0) {
            currentValue = 0;
        }
        if (currentValue > maxValue) {
            currentValue = maxValue;
        }
        this.currentValue = currentValue;
        postInvalidate();
    }
}
