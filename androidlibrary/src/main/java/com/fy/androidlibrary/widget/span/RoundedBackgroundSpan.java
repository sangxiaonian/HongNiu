package com.fy.androidlibrary.widget.span;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.style.ReplacementSpan;

import com.fy.androidlibrary.utils.DeviceUtils;
import com.fy.androidlibrary.utils.JLog;


public class RoundedBackgroundSpan extends ReplacementSpan {

    private int corner_radius = 8;//圆角
    private int backgroundColor;//背景色
    private int textColor;//文字颜色
    private int bordColor;//边框颜色
    private int bordWidth;//宽度
    private int paddingLeft;
    private int paddingTop;
    private int paddingRight;
    private int paddingBottom;

    private int marginLeft;
    private int marginTop;
    private int marginRight;
    private int marginBottom;

    private int textSize;

    RectF rect;
//    Rect textRect;

    public RoundedBackgroundSpan(Context context) {
        super();
        this.backgroundColor = Color.WHITE;
        this.textColor = Color.parseColor("#878787");
        this.bordColor = Color.parseColor("#EBEBEB");
//        this.bordColor = Color.BLACK;
        this.bordWidth = DeviceUtils.dip2px(context, 0.5f);
        corner_radius = DeviceUtils.dip2px(context, 2);
        paddingLeft = DeviceUtils.dip2px(context, 5);
        paddingTop = DeviceUtils.dip2px(context, 3);
        paddingRight = DeviceUtils.dip2px(context, 5);
        paddingBottom = DeviceUtils.dip2px(context, 3);


        rect = new RectF();
//        textRect = new Rect();
    }

    public void setCornerRadius(int cornerRadius) {
        corner_radius = cornerRadius;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public void setBordColor(int bordColor) {
        this.bordColor = bordColor;
    }

    public void setPadding(int left, int top, int right, int bottom) {
        paddingLeft = left;
        paddingTop = top;
        paddingRight = right;
        paddingBottom = bottom;
    }

    public void setMargin(int left, int top, int right, int bottom) {
        marginLeft = left;
        marginTop = top;
        marginRight = right;
        marginBottom = bottom;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {

        if (textSize>0){
            paint.setTextSize(textSize);
        }

        Paint.FontMetricsInt fmPaint = paint.getFontMetricsInt();
        float textHeight=fmPaint.bottom-fmPaint.top+paddingTop+paddingBottom+marginTop+marginBottom;
        float gap=(bottom-top-textHeight)/2;

        int centerY = (bottom - top) / 2+marginTop/2-marginBottom/2;
        float baseLine= centerY -(fmPaint.bottom+fmPaint.top)/2;
        float offect = (bordWidth >> 1) +0.5f;//容差
        rect.left = x +offect  + marginLeft;
        rect.top =marginTop+offect+gap;
        rect.right = rect.left + paddingLeft + paddingRight + measureText(paint, text, start, end) -offect*2  ;
        rect.bottom = bottom-marginBottom-offect-gap ;
        if (backgroundColor!=0) {
            paint.setColor(backgroundColor);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawRoundRect(rect, corner_radius, corner_radius, paint);
        }
        if (bordColor!=0) {

            paint.setColor(bordColor);
            paint.setStrokeWidth(bordWidth);
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawRoundRect(rect, corner_radius, corner_radius, paint);
        }

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(textColor);
        canvas.drawText(text, start, end, x + paddingLeft + marginLeft, baseLine , paint);
    }

    public void setBordWidth(int bordWidth) {
        this.bordWidth = bordWidth;
    }


    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        if (fm != null) {
            if (textSize>0){
                paint.setTextSize(textSize);
            }
            //原来字体型号
            Paint.FontMetricsInt fmPaint = paint.getFontMetricsInt();
            int fontHeight = fmPaint.bottom - fmPaint.top;
            int drHeight = fontHeight + paddingTop + paddingBottom + marginTop + marginBottom;
            int centerY = fmPaint.ascent + fontHeight / 2;
            //此处仅仅计算更改高度
            fm.ascent = centerY - drHeight / 2;
            fm.top = fm.ascent;
            fm.bottom = centerY + drHeight / 2;
            fm.descent = fm.bottom;
        }
        if (textSize>0){
            paint.setTextSize(textSize);
        }
        return Math.round(paint.measureText(text, start, end)) + paddingLeft + paddingRight + marginLeft + marginRight;
    }

    private float measureText(Paint paint, CharSequence text, int start, int end) {
        return paint.measureText(text, start, end);
    }
}
