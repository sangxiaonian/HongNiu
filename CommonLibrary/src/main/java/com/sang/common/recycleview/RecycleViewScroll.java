package com.sang.common.recycleview;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * 作者： ${PING} on 2018/12/10.
 */
public class RecycleViewScroll extends RecyclerView {

    private float disY;//纵向滑动距离
    private float disX;//横向
    private float maxY=1;//横向滑动渐变最大距离
    private float maxX=1;//横向滑动渐变最大距离
    OnScrollDisChangeListener listener;

    public void setOnScrollDisChangeListener(OnScrollDisChangeListener listener) {
        this.listener = listener;
    }

    public RecycleViewScroll(Context context) {
        super(context);
    }

    public RecycleViewScroll(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RecycleViewScroll(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    public void setMaxY(float maxY) {
        this.maxY = maxY;
    }

    public void setMaxX(float maxX) {
        this.maxX = maxX;
    }

    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);
        disY += dy;
        disX += dx;
        if (listener!=null){
            listener.onScrollDisChange(disX,disX/maxX,disY,disY/maxY);
        }

    }


    public interface OnScrollDisChangeListener {
        /**
         * View滑动监听
         *
         * @param disX     横向滑动距离
         * @param percentX 横向滑动的百分比
         * @param dixY     纵向滑动距离
         * @param percentY 纵向滑动的百分比
         */
        void onScrollDisChange(float disX, float percentX, float dixY, float percentY);
    }


}
