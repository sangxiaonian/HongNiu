package com.sang.common.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.text.style.ImageSpan;

import com.sang.common.utils.JLog;

public class CenteredImageSpan extends ImageSpan {

    private int width;
    private int height;

    public CenteredImageSpan(Context context, final int drawableRes) {
        super(context, drawableRes);


    }

    public void setSpanSize(int width, int height) {
        this.width = width;
        this.height = height;
    }


    @Override
    public void draw(@NonNull Canvas canvas, CharSequence text,
                     int start, int end, float x,
                     int top, int y, int bottom, @NonNull Paint paint) {
        // image to draw

        Drawable b = getDrawable();

            b.setBounds(0, 0, width, height);

        Paint.FontMetricsInt fm = paint.getFontMetricsInt();
        int transY = (y + fm.descent + y + fm.ascent) / 2
                - b.getBounds().bottom / 2;

        canvas.save();


        canvas.translate(x, transY);
        b.draw(canvas);
        canvas.restore();
    }


}