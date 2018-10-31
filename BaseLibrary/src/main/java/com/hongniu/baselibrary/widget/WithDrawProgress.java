package com.hongniu.baselibrary.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.sang.common.utils.DeviceUtils;

/**
 * 作者： ${PING} on 2018/10/30.
 */
public class WithDrawProgress extends View {

    private Paint mPaint;
    private int passColor;
    private int unPassColor;
    private int radio;
    private int bitmapSize;
    private int startCenterX;
    private int endCenterX;
    private int startCenterY;
    private int endCenterY;
    private int centerX;
    private int centerY;

    protected int progress;
    protected int maxProgress;

    public WithDrawProgress(Context context) {
        this(context, null, 0);
    }


    public WithDrawProgress(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public WithDrawProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }

    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        radio = DeviceUtils.dip2px(context, 4);
        mPaint.setStrokeWidth(radio / 2);
        bitmapSize = DeviceUtils.dip2px(context, 30);
        passColor = Color.parseColor("#F06F28");
        unPassColor = Color.parseColor("#DDDDDD");

        progress = 50;
        maxProgress = 100;

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        startCenterX = getWidth() / 2;
        startCenterY = bitmapSize / 2;
        endCenterX = getWidth() / 2;
        endCenterY = getHeight() - bitmapSize / 2;
        centerX = getWidth() / 2;
        centerY = getHeight() / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        final float v = progress * 1.0f / maxProgress;
        final float currentY = startCenterY + (endCenterY - startCenterY) * v;
        drawUnPassLine(canvas, v, currentY);
        drawPassLine(canvas, v, currentY);
        drawUnPass(canvas, v, currentY);
        drawPass(canvas, v, currentY);
    }


    private void drawPassLine(Canvas canvas, float v, float currentY) {
        mPaint.setColor(passColor);
        if (v > 0) {
            canvas.drawLine(startCenterX, startCenterY, endCenterX, currentY, mPaint);
        }
    }
    /**
     * 绘制已经到达的数据
     */
    private void drawPass(Canvas canvas, float v, float currentY) {
        mPaint.setColor(passColor);
        if (v == 0) {
            canvas.drawCircle(startCenterX, startCenterY, radio, mPaint);
        } else if (v == 0.5) {
            canvas.drawCircle(startCenterX, startCenterY, radio, mPaint);
            canvas.drawCircle(centerX, centerY, radio, mPaint);
        } else if (v == 1) {
            canvas.drawCircle(startCenterX, startCenterY, radio, mPaint);
            canvas.drawCircle(centerX, centerY, radio, mPaint);
            canvas.drawCircle(endCenterX, endCenterY, radio, mPaint);
        }
    }

    /**
     * 绘制尚未到达的数据
     */
    private void drawUnPassLine(Canvas canvas, float v, float currentY) {
        mPaint.setColor(unPassColor);
        if (v < 1) {
            canvas.drawLine(startCenterX, currentY, endCenterX, endCenterY, mPaint);
        }
    }
    /**
     * 绘制尚未到达的数据
     */
    private void drawUnPass(Canvas canvas, float v, float currentY) {
        mPaint.setColor(unPassColor);
        if (v == 0) {
            canvas.drawCircle(startCenterX, startCenterY, radio, mPaint);
            canvas.drawCircle(centerX, centerY, radio, mPaint);
        } else if (v == 0.5) {
            canvas.drawCircle(endCenterX, endCenterY, radio, mPaint);
        } else if (v == 1) {

        }
    }
}
