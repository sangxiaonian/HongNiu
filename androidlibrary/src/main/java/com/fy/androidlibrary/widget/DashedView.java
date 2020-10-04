package com.fy.androidlibrary.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import com.fy.androidlibrary.R;
/**
 *@data  2020/2/11
 *@Author PING
 *@Description
 *
 * 绘制虚线
 *
 */
public class DashedView extends View {

    private Paint paint;
    public static final int VERTICAL = 0;
    public static final int HORIZONTAL = 1;
    // 方向
    private int oritation;
    // 颜色
    private int dashedColor;
    // 线段长度
    private float lineLength;
    // 间隔长度
    private float spaceLength;
    // 线粗
    private float dashedWidth;

    private int mWidth;
    private int mHeight;
    private Path path;

    public DashedView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.DashedView);
        dashedColor = typedArray.getColor(R.styleable.DashedView_dashedColor,
                0x000000);
        oritation = typedArray.getInt(R.styleable.DashedView_dashedOritation,
                HORIZONTAL);
        lineLength = typedArray.getDimension(R.styleable.DashedView_lineLength,
                10.0f);
        spaceLength = typedArray.getDimension(
                R.styleable.DashedView_spaceLength, 5.0f);
        dashedWidth = typedArray.getDimension(
                R.styleable.DashedView_dashedWidth, 2.0f);

        paint = new Paint();
        paint.reset();
        paint.setColor(dashedColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(dashedWidth);
        DashPathEffect effects = new DashPathEffect(new float[] { lineLength,
                spaceLength }, 0);
        paint.setPathEffect(effects);
        path = new Path();

    }

    public DashedView(Context context) {
        this(context, null);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
               //居中画
        if (oritation == VERTICAL) {
            path.moveTo((mWidth - dashedWidth) / 2, 0);
            path.lineTo((mWidth - dashedWidth) / 2, mHeight);
        } else {
            path.moveTo(0, (mHeight - dashedWidth) / 2);
            path.lineTo(mWidth, (mWidth - dashedWidth) / 2);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(path, paint);
    }
}