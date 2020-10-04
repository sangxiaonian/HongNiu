package com.fy.androidlibrary.widget.span;

import android.graphics.Color;
import android.text.TextPaint;
import android.text.style.ClickableSpan;

/**
 * 作者：  on 2020/2/8.
 */
public abstract class XClickableSpan extends ClickableSpan {
    private boolean showLine;
    private int color ;

    public XClickableSpan() {
        color= Color.BLACK;
    }

    /**
     * 点击内容是否显示下滑线,默认为false
     *
     * @param showLine 点击内容是否显示下滑线,默认为false
     * @return
     */
    public XClickableSpan setInderLineText(boolean showLine) {
        this.showLine = showLine;
        return this;
    }

    /**
     * 设置选中文字颜色
     *
     * @param color
     * @return
     */
    public XClickableSpan setColor(int color) {
        this.color = color;
        return this;
    }


    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setUnderlineText(showLine);
        ds.setColor(color);
    }
}
