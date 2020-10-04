package com.hongniu.baselibrary.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.hongniu.baselibrary.R;
import com.fy.androidlibrary.utils.DeviceUtils;

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
    private Bitmap mBitmap;

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
        mBitmap= zoomImg(BitmapFactory.decodeResource(getResources(), R.mipmap.icon_cartime_60),DeviceUtils.dip2px(context,30),DeviceUtils.dip2px(context,30));
        progress = 50;
        maxProgress = 100;

    }
    public Bitmap zoomImg(Bitmap bm, int newWidth, int newHeight) {
        // 获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        return newbm;
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
        canvas.drawBitmap(mBitmap,0,currentY-mBitmap.getHeight()/2,mPaint);

    }


    public void setCurrentProgress(int currentProgress){
        progress=currentProgress<0?0:(currentProgress>maxProgress?maxProgress:currentProgress);
        postInvalidate();
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
        if (v < 0.5) {
            canvas.drawCircle(startCenterX, startCenterY, radio, mPaint);
        } else if (v < 1) {
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
        if (v < 0.5) {
            canvas.drawCircle(endCenterX, endCenterY, radio, mPaint);
            canvas.drawCircle(centerX, centerY, radio, mPaint);
        } else if (v <1) {
            canvas.drawCircle(endCenterX, endCenterY, radio, mPaint);
        }
    }
}
