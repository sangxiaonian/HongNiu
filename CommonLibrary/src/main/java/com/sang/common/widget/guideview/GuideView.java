package com.sang.common.widget.guideview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

/**
 * 作者： ${PING} on 2018/2/27.
 */

public class GuideView extends FrameLayout {

    public View targView;
    private Path mPath;
    private Paint mPaint;
    private Rect rect;

    private int width;

    public GuideView(Context context) {
        this(context, null, 0);
    }

    public GuideView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GuideView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPath = new Path();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
        rect = new Rect();
        width = 40;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPath.reset();
        //绘制一个三角形
        if (targView != null) {
            View child = getChildAt(0);
            int center = rect.left / 2 + rect.right / 2;
            mPath.moveTo(center - width / 2, child.getBottom());
            mPath.lineTo(center + width / 2, child.getBottom());
            mPath.lineTo(center, child.getBottom() + width / 2);
            mPath.close();
            canvas.drawPath(mPath, mPaint);
        }

    }

    public void setTargView(View targView) {
        this.targView = targView;
        if (targView != null) {
            targView.getGlobalVisibleRect(rect);
        }

    }
}
