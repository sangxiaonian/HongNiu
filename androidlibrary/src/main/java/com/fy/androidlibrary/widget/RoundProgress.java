package com.fy.androidlibrary.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;


/**
 * 作者： ${PING} on 2018/11/13.
 */
public class RoundProgress extends View {

    private Paint mPaint;
    private int color = Color.WHITE;
    private int boundWidth = 2;
    private int radius;
    private PointF center;
    private int max;
    private float progress;
    private RectF rect;
    private int backgoundColor;

    public RoundProgress(Context context) {
        this(context, null, 0);
    }


    public RoundProgress(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }


    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        max = 100;
        progress = 60;
        backgoundColor = Color.parseColor("#b2000000");
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        color = Color.WHITE;
        mPaint.setColor(color);
        mPaint.setStrokeWidth(boundWidth);
        center = new PointF();
        rect = new RectF();

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        radius = w / (2 * 3);
        center.x = w / 2;
        center.y = h / 2;
        rect.left = center.x - radius;
        rect.top = center.y - radius;
        rect.right = center.x + radius;
        rect.bottom = center.y + radius;


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(backgoundColor);
        mPaint.setColor(color);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(center.x, center.y, radius, mPaint);
        float p =  progress * 360.0f / max;
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawArc(rect, -90, p, true, mPaint);

    }

    public void setMax(int max) {
        this.max = max;
    }

    public void setProgress(float progress) {
        this.progress = progress > max ? max : (progress < 0 ? 0 : progress);
        postInvalidate();
    }
}
