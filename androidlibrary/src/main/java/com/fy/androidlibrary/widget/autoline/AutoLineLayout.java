package com.fy.androidlibrary.widget.autoline;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.fy.androidlibrary.widget.autoline.helper.AutoLayoutHelper;
import com.fy.androidlibrary.widget.autoline.inter.IAutoAdapter;

/**
 * 作者：  on 2019/10/31.
 */
public class AutoLineLayout extends ViewGroup {

    IAutoAdapter adapter;
    AutoLayoutHelper helper;

    public AutoLineLayout(Context context) {
        this(context, null);
    }

    public AutoLineLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoLineLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int[] sizes = autoMeasure(helper == null ? 0 : helper.getType(), widthSize, widthMeasureSpec, heightMeasureSpec);
        int width = sizes[0];
        int height = sizes[1];
        setMeasuredDimension(
                widthMode == MeasureSpec.EXACTLY ? widthSize : width + getPaddingLeft() + getPaddingRight(),
                heightMode == MeasureSpec.EXACTLY ? heightSize : height + getPaddingTop() + getPaddingBottom()//
        );

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        autoWidthLayout();
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    //自动换行，自适应宽高
    private int[] autoMeasure(int type, int widthSize, int widthMeasureSpec, int heightMeasureSpec) {
        int width = 0;
        int height = 0;
        int lineWidth = 0;//单行的宽度
        int lineHeight = 0;//单行的高度
        int childCount = getChildCount();
        int vGap = helper == null ? 0 : helper.getvGap();
        int hGap = helper == null ? 0 : helper.gethGap();
        int column = helper == null||helper.getColumn()<=0 ? 4 : helper.getColumn();
        final int cellWidth = (widthSize - (column - 1) * hGap) / column;

        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == GONE) {
                if (i == childCount - 1) {
                    width = Math.max(width, lineWidth);
                    height = Math.max(height + lineHeight, lineHeight);
                }
                continue;
            }

            if (adapter != null) {
                adapter.onBindView(child, i);
            }

            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            //自适应宽高
            int childWith;
            int childHeight = child.getMeasuredHeight();

            if (type == 1) {

                //固定列数
                childWith = cellWidth;
                //更改控件实际宽度
                LayoutParams params = child.getLayoutParams();
                params.width = childWith;
                child.setLayoutParams(params);
                measureChild(child, widthMeasureSpec, heightMeasureSpec);
            } else if (type == 2) {
                //列数可以更改
                LayoutParams params = child.getLayoutParams();
                params.width = LayoutParams.WRAP_CONTENT;
                child.setLayoutParams(params);
                measureChild(child, widthMeasureSpec, heightMeasureSpec);
                childWith = child.getMeasuredWidth();
                double cellScral = Math.ceil(childWith * 1.0f / cellWidth);
                childWith = (int) (cellScral * (cellWidth + hGap) - (hGap));
                childWith = childWith > widthSize ? widthSize : childWith;
                //自适应宽高
                LayoutParams params1 = child.getLayoutParams();
                params1.width = childWith;
                child.setLayoutParams(params1);
                measureChild(child, widthMeasureSpec, heightMeasureSpec);
            } else {
                //不固定列数
                childWith = child.getMeasuredWidth();
            }
            if (childWith + lineWidth > widthSize - getPaddingLeft() - getPaddingRight()) {
                //换到第二行
                width = Math.max(lineWidth, width);
                height += lineHeight;
                lineWidth = childWith + hGap;
                lineHeight = childHeight + vGap;
            } else {
                //当前行
                lineWidth += childWith + hGap;
                lineHeight = Math.max(lineHeight, childHeight + vGap);
            }
            if (i == childCount - 1) {
                //如果是最后一个控件
                width = Math.max(width, lineWidth);
                height = Math.max(height + lineHeight, lineHeight);
            }
        }
        return new int[]{width, height};
    }

    private void autoWidthLayout() {
        int count = getChildCount();
        int left = getPaddingLeft();
        int top = getPaddingTop();
        int right = 0;
        int bottom = 0;
        int startX = left, startY = top;//左上角坐标
        int lineHeight = 0;
        int vGap = helper == null ? 0 : helper.getvGap();
        int hGap = helper == null ? 0 : helper.gethGap();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == GONE) {
                continue;
            }


            int chlidWidth = child.getMeasuredWidth();
            int chlidHeight = child.getMeasuredHeight();
            if (chlidWidth + startX > getWidth() - getPaddingRight() - getPaddingLeft()) {
                //换行
                startX = left;
                startY += lineHeight;
                left = startX;
                top = startY;
                right = left + chlidWidth;
                bottom = top + chlidHeight;
                lineHeight = chlidHeight + vGap;
                startX += chlidWidth + hGap;
            } else {
                left = startX;
                top = startY;
                right = left + chlidWidth;
                bottom = top + chlidHeight;
                //不换行
                startX += chlidWidth + hGap;
                lineHeight = Math.max(lineHeight, chlidHeight + vGap);
            }
            child.layout(left, top, right, bottom);
        }
    }

    public void setAdapter(IAutoAdapter adapter) {
        this.adapter = adapter;
        adapter.onAttchParent(this);
        notifyAllItemChange();
    }


    public void notifyAllItemChange() {
        removeAllViews();
        for (int i = 0; i < adapter.getItemCount(); i++) {
            View view = adapter.onCreateView(this, adapter.getItemViewType(i));
            addView(view);
        }

    }

    public void notifyItemChange(int position) {
        if (adapter != null)
            adapter.onBindView(getChildAt(position), position);
        requestLayout();
    }

    public void setLayoutHelper(AutoLayoutHelper helper) {
        this.helper = helper;
        if (adapter!=null){
            requestLayout();
        }
    }

    public IAutoAdapter getAdapter() {
        return adapter;
    }

    public AutoLayoutHelper getHelper() {
        return helper;
    }

    public void setHelper(AutoLayoutHelper helper) {
        this.helper = helper;
    }
}
