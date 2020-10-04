package com.fy.androidlibrary.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.fy.androidlibrary.utils.DeviceUtils;

import java.util.ArrayList;
import java.util.List;

public class SideIndexBar extends View {


    Handler handler = new  Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (mTextDialog!=null){
                mTextDialog.setVisibility(GONE);
            }
        }
    };


    // 字母列表颜色
    int mLetterColor = 0xfffedcba;
    // 被选中的字母颜色
    int mSelectLetterColor = 0xffabcdef;
    // 字母字体大小
    float mLetterSize;


    private int mChoose = -1;// 选中的字母是第几个
    private TextPaint mPaint;//画笔0
    private TextView mTextDialog;//可以设置一个显示当前索引字母的对话框
    private List<String> mLetters = new ArrayList<>();//默认字符
    private OnLetterChangedListener mLetterChangedListener;// 触摸字母改变事件

    private float gap = 20;

    private List<RectF> rects = new ArrayList<>();

    public SideIndexBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    public SideIndexBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SideIndexBar(Context context) {
        this(context, null);


    }

    private void init(Context context, AttributeSet attrs) {

        mLetterSize = DeviceUtils.dip2px(context, 13);
        gap = DeviceUtils.dip2px(context, 9);
        mLetterColor = Color.parseColor("#a1a1a1");
        mSelectLetterColor = Color.parseColor("#656C99");
        mPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTypeface(Typeface.DEFAULT_BOLD);
        mPaint.setAntiAlias(true);
        setClickable(true);
    }

    public void setLetters(List<String> letters) {
        this.mLetters.clear();
        rects.clear();
        if (letters != null) {
            this.mLetters.addAll(letters);
            for (String mLetter : this.mLetters) {
                rects.add(new RectF());
            }
        }
        requestLayout();
        invalidate();
    }


    private Rect rect = new Rect();

    private float flagStartY;
    private float flagEndY;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mLetters.size() > 0) {
            mPaint.setTextSize(mLetterSize);
            float height = 0;
            for (String mLetter : mLetters) {
                mPaint.measureText(mLetter);
                mPaint.getTextBounds(mLetter, 0, mLetter.length(), rect);
                height += rect.height();
            }
            float maxGap = (getMeasuredHeight() - height) / mLetters.size();
            gap = Math.min(maxGap, gap);
            flagStartY = (getMeasuredHeight() - (height + gap * (mLetters.size() - 1))) / 2;
        }


    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float startX = getMeasuredWidth() / 2;
        float startY = flagStartY;
        for (int i = 0; i < mLetters.size(); i++) {
            if (mChoose == i) {
                mPaint.setColor(mSelectLetterColor);
            } else {
                mPaint.setColor(mLetterColor);
            }
            String letter = mLetters.get(i);
            mPaint.measureText(letter);
            mPaint.setTextAlign(Paint.Align.CENTER);
            Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
            mPaint.getTextBounds(letter, 0, letter.length(), rect);
            float baseLine = startY + rect.height() / 2.0f + (Math.abs(fontMetrics.ascent) - fontMetrics.descent) / 2;
            canvas.drawText(letter, startX, baseLine, mPaint);
            rects.get(i).set(0, startY, getMeasuredWidth(), startY += gap + rect.height());
        }
        flagEndY = startY;
    }


    public void setmLetterColor(int mLetterColor) {
        this.mLetterColor = mLetterColor;
    }

    public void setmSelectLetterColor(int mSelectLetterColor) {
        this.mSelectLetterColor = mSelectLetterColor;
    }

    public void setmLetterSize(float mLetterSize) {
        this.mLetterSize = mLetterSize;
    }

    public void setmTextDialog(TextView mTextDialog) {
        this.mTextDialog = mTextDialog;

    }



    public void setmLetterChangedListener(OnLetterChangedListener mLetterChangedListener) {
        this.mLetterChangedListener = mLetterChangedListener;
    }

    public void setGap(float gap) {
        this.gap = gap;
        requestLayout();
        invalidate();
    }

    //    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        float y = event.getY();// 点击y坐标
        float x = event.getX();// 点击y坐标
        int oldChoose = mChoose;
        mChoose = getChoiceIndex(x, y);
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mChoose = -1;//
                handler.removeMessages(0);
                handler.sendEmptyMessageDelayed(0,200);

            default:
                if (oldChoose != mChoose && mChoose != -1) {
                    if (mLetterChangedListener != null) {
                        mLetterChangedListener.onChanged(mLetters.get(mChoose), mChoose);
                    }
                    if (mTextDialog != null) {
                        mTextDialog.setText(mLetters.get(mChoose));
                        mTextDialog.setVisibility(View.VISIBLE);
                    }
                }
        }
        invalidate();
        return super.dispatchTouchEvent(event);
    }

    private int getChoiceIndex(float x, float y) {
        if (rects.size() > 0) {
            for (int i = 0; i < rects.size(); i++) {
                if (rects.get(i).contains(x, y)) {
                    return i;
                }
            }
        }
        return -1;
    }

    //设置接口
    public void setOnLetterChangedListener(OnLetterChangedListener
                                                   letterChangedListener) {
        mLetterChangedListener = letterChangedListener;
    }

    /**
     * 触摸选中的字母发生改变的接口
     */
    public interface OnLetterChangedListener {
        void onChanged(String s, int position);
    }
}