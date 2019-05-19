package com.hongniu.baselibrary.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hongniu.baselibrary.R;

/**
 * 作者： ${PING} on 2019/5/19.
 */
public class LinearDividerDecoration extends RecyclerView.ItemDecoration {

    private   Context mContext;
    private int type;
    private int left;
    private int right;
    private int lineHeight;
    private int lineColor;
    private Rect rect;
    private Paint mPaint;

    public LinearDividerDecoration(Context context) {
        this.mContext=context;
        left= (int) context.getResources().getDimension(R.dimen.app_gap);
        right= (int) context.getResources().getDimension(R.dimen.app_gap);
        lineHeight = (int) context.getResources().getDimension(R.dimen.line_height);
        type=1;
        lineColor=context.getResources().getColor(R.color.line_color);
        rect=new Rect();
        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(lineColor);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        for (int i = 0; i < parent.getChildCount(); i++) {
            final View child = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(child);
            if (position<parent.getAdapter().getItemCount()-1) {
                rect.set(left, child.getBottom(), child.getRight() - right, child.getBottom() + lineHeight);
                c.drawRect(rect, mPaint);
            }

        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);

    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        //对于第一个，向下偏移
        outRect.set(0,0 , 0, lineHeight);



    }
}
