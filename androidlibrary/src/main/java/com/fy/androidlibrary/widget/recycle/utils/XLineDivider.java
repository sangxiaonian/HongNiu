package com.fy.androidlibrary.widget.recycle.utils;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class XLineDivider extends RecyclerView.ItemDecoration {

    private int paddingLeft;
    private int paddingRight;
    private int paddingTop;
    private int paddingBottom;
    private int dividerColor;
    private Paint mPaint;
    private int mDividerHeight;//分割线高度，默认为1px
    private int mDividerWidth;//分割线高度，默认为1px
    private int mOrientation;//列表的方向：LinearLayoutManager.VERTICAL或LinearLayoutManager.HORIZONTAL

    //需要隐藏分割线的列表
    private List<Integer> hidePosition = new ArrayList<>();
    private boolean hideLast;//隐藏最后一个
    private Point headPoint;


    public XLineDivider() {
        mOrientation = LinearLayoutManager.VERTICAL;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(dividerColor = Color.parseColor("#eeeeee"));
        mPaint.setStyle(Paint.Style.FILL);
        mDividerHeight = 2;
        mDividerWidth = 2;
    }


    public XLineDivider setmDividerWidth(int mDividerWidth) {
        this.mDividerWidth = mDividerWidth;
        return this;
    }


    public XLineDivider setmDividerHeight(int mDividerHeight) {
        this.mDividerHeight = mDividerHeight;
        return this;
    }

    public XLineDivider setmOrientation(int mOrientation) {
        this.mOrientation = mOrientation;
        return this;
    }

    public XLineDivider setPaddingLeft(int paddingLeft) {
        this.paddingLeft = paddingLeft;
        return this;
    }

    public XLineDivider setPaddingRight(int paddingRight) {
        this.paddingRight = paddingRight;
        return this;
    }

    public XLineDivider setHidePosition(List<Integer> hidePosition) {
        this.hidePosition.clear();
        if (hidePosition != null && hidePosition.size() > 0) {
            this.hidePosition.addAll(hidePosition);
        }
        return this;
    }

    public XLineDivider addHidePosition(int hidePosition) {
        this.hidePosition.add(hidePosition);
        return this;
    }


    public XLineDivider setPaddingTop(int paddingTop) {
        this.paddingTop = paddingTop;
        return this;
    }

    public XLineDivider setPaddingBottom(int paddingBottom) {
        this.paddingBottom = paddingBottom;
        return this;
    }

    public XLineDivider setDividerColor(int dividerColor) {
        this.dividerColor = dividerColor;
        return this;
    }

    //获取分割线尺寸
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        //对于最后一个，向下偏移
        final int position = parent.getChildAdapterPosition(view);

        if (_hideCurrent(position, parent)) {
            outRect.set(0, 0, 0, 0);
        } else {
            if (mOrientation == LinearLayoutManager.VERTICAL) {
                if (position == 0) {

                    outRect.set(0,headPoint == null ? 0 : headPoint.y,  0, mDividerHeight);
                } else {
                    outRect.set(0, 0, 0, mDividerHeight);
                }
            } else {
                if (position == 0) {

                    outRect.set(headPoint == null ? 0 : headPoint.x, 0, mDividerWidth, 0);
                } else {
                    outRect.set(0, 0, mDividerWidth, 0);
                }
            }
        }
    }

    //绘制分割线
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if (mOrientation == LinearLayoutManager.VERTICAL) {
            drawHorizontal(c, parent);
        } else {
            drawVertical(c, parent);
        }
    }

    private Rect rect = new Rect();

    //绘制横向 item 分割线
    private void drawHorizontal(Canvas canvas, RecyclerView parent) {
        final int left = parent.getPaddingLeft() + paddingLeft;
        final int right = parent.getMeasuredWidth() - parent.getPaddingRight() - paddingRight;
        final int childSize = parent.getChildCount();
        mPaint.setColor(dividerColor);
        if (parent.getAdapter() != null) {
            for (int i = 0; i < childSize; i++) {
                final View child = parent.getChildAt(i);
                int position = parent.getChildAdapterPosition(child);
                if (_hideCurrent(position, parent)) {
                    continue;
                }
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
                final int top = child.getBottom() + layoutParams.bottomMargin;
                final int bottom = top + mDividerHeight;
                drawLine(canvas, top, bottom, left, right);
            }
        }
    }

    //绘制纵向 item 分割线
    private void drawVertical(Canvas canvas, RecyclerView parent) {
        final int top = parent.getPaddingTop() + paddingTop;
        final int bottom = parent.getMeasuredHeight() - parent.getPaddingBottom() - paddingBottom;
        final int childSize = parent.getChildCount();
        mPaint.setColor(dividerColor);
        if (parent.getAdapter() != null) {
            for (int i = 0; i < childSize; i++) {
                final View child = parent.getChildAt(i);
                int position = parent.getChildAdapterPosition(child);
                if (_hideCurrent(position, parent)) {
                    continue;
                }
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
                final int left = child.getRight() + layoutParams.rightMargin;
                final int right = left + mDividerWidth;
                drawLine(canvas, top, bottom, left, right);
            }
        }
    }

    private void drawLine(Canvas canvas, int top, int bottom, int left, int right) {
        rect.set(left, top, right, bottom);
        canvas.drawRect(rect, mPaint);
    }


    public XLineDivider hideLast(boolean hideLast) {
        this.hideLast = hideLast;
        return this;
    }

    private boolean _hideCurrent(int position, RecyclerView parent) {
        boolean showLast = false;
        if (parent != null && parent.getAdapter() != null) {
            final int last = parent.getAdapter().getItemCount() - 1;
            showLast = hideLast && (position == last);
            if (last==0&&headPoint!=null){
                showLast=false;
            }
        }

        return showLast || hidePosition.contains(position);
    }


    /**
     * 由于此分割线默认为每个item的底部存在，因此会造成第一个顶部无法出现分割线，此方法作为补偿，用来控制顶部间隔，以对应部分特殊需求
     *
     * @return
     */
    public XLineDivider setHeadGap(int headWidth, int headHeight) {
        headPoint = new Point(headWidth, headHeight);
        return this;
    }
}