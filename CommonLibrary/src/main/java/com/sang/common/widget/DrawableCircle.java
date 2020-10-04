package com.sang.common.widget;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 作者： ${PING} on 2018/12/10.
 */
public class DrawableCircle extends Drawable {

    private float mWidth,mHeight;
    private int color;
    private int radius;
    private Paint mPaint;
    RectF rect;

    public DrawableCircle() {
        invalidateSelf();
        color=Color.WHITE;
        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(color);
        rect=new RectF();

    }

    public DrawableCircle setSize(float mWidth , float mHeight){
        this.mWidth = mWidth;
        this.mHeight = mHeight;
        rect.left=0;
        rect.top=0;
        rect.right=mWidth;
        rect.bottom=mHeight;
        return this;
    }


    public DrawableCircle setColor(int color) {
        this.color = color;
        mPaint.setColor(color);
        return this;

    }

    public void flush(){
        invalidateSelf();
    }

    public DrawableCircle setRadius(int radius) {
        this.radius = radius;
        return this;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        canvas.drawRoundRect(rect,radius,radius,mPaint);
    }


    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }


    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
    }


    @Override
    public int getOpacity() {
          return PixelFormat.TRANSLUCENT;
    }
}
