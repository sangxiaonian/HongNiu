package com.hongniu.baselibrary.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.hongniu.baselibrary.R;
import com.sang.common.utils.DeviceUtils;

/**
 * 作者： ${桑小年} on 2018/8/7.
 * 努力，为梦长留
 */
public class OrderProgress extends View {

    private Paint mPaint;
    private int max;
    private float current;
    private int proWidth;
    private int pointSize;
    private int passColor, unPassColor;
    private Bitmap bitmap;

    private Rect rect;
    private boolean showProgress = true;
    private boolean showLog = true;

    public OrderProgress(Context context) {
        this(context, null, 0);
    }


    public OrderProgress(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public OrderProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }

    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        max = 100;
        current = 50;
        proWidth = DeviceUtils.dip2px(context, 2);
        pointSize = DeviceUtils.dip2px(context, 5);
        passColor = Color.parseColor("#E83515");
        unPassColor = Color.parseColor("#DDDDDD");
        bitmap = creatBitmap();
        rect = new Rect();
    }

    private Bitmap creatBitmap() {
        int size = DeviceUtils.dip2px(getContext(), 25);
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_carmap_50);
// 获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 设置想要的大小
        int newWidth = size;
        int newHeight = size;
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        return Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(unPassColor);
        drawProprogress(canvas);
        mPaint.setColor(passColor);


        if (showProgress) {
//            final float currentPro = current * 1.0f / max;
            final float currentPro = 0.5f;
            final float proY = getMeasuredHeight() * currentPro;
            canvas.save();
            rect.top = 0;
            rect.left = 0;
            rect.right = getMeasuredWidth();
            rect.bottom = (int) (proY);
            canvas.clipRect(rect);
            drawProprogress(canvas);
            canvas.restore();
            if (true) {
                final float bitmapY = (proY - bitmap.getHeight()) > 0 ? (proY - bitmap.getHeight()) : 0;
                canvas.drawBitmap(bitmap, (getMeasuredWidth() - bitmap.getWidth()) / 2
                        , bitmapY, mPaint);
            }
        }


    }

    private void drawProprogress(Canvas canvas) {
        final int centerX = getMeasuredWidth() / 2;

        final int centerUpY = pointSize / 2;
        final int centerDownY = getMeasuredHeight() - pointSize / 2;

        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(centerX, centerUpY, pointSize / 2, mPaint);
        canvas.drawCircle(centerX, centerDownY, pointSize / 2, mPaint);
        mPaint.setStrokeWidth(proWidth);
        canvas.drawLine(centerX, centerUpY, centerX, centerDownY, mPaint);
    }


    public void setCurent(float curent) {

        this.current = curent < 0 ? 0 : curent > max ? max : curent;
        postInvalidate();
    }

    //隐藏当前进度
    public void showProgress(boolean show) {
        showProgress = show;
        postInvalidate();
    }

    /**
     * 隐藏进度图标
     *
     * @param showLog
     */
    public void showLog(boolean showLog) {
        this.showLog = showLog;
        postInvalidate();
    }
}
