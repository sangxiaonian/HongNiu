package com.fy.androidlibrary.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 作者： ${PING} on 2018/11/9.
 */
public class XTextSwitcher  extends RecyclerView {

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            removeMessages(0);
            LayoutManager manager = getLayoutManager();
            Adapter adapter = getAdapter();
            if (manager != null&&adapter!=null && adapter.getItemCount() > 0) {
                cuttentPosition++;
                cuttentPosition = cuttentPosition % adapter.getItemCount();
                //对于最后一个，立刻刷新到第一位
                if (cuttentPosition == 0) {
                    scrollToPosition(cuttentPosition);
                    sendEmptyMessage(0);
                } else {
                    smoothScrollToPosition(cuttentPosition);
                    sendEmptyMessageDelayed(0, delayTime);
                }
            }
        }
    };

    private long delayTime = 3000;
    private int cuttentPosition;

    public XTextSwitcher(Context context) {
        this(context, null, 0);
    }

    public XTextSwitcher(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XTextSwitcher(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context, attrs, defStyle);
    }

    private void initView(Context context, AttributeSet attrs, int defStyle) {


        LinearLayoutManager manager = new SmoothScrollLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        setLayoutManager(manager);
        LinearSnapHelper helper = new LinearSnapHelper();
        helper.attachToRecyclerView(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        return false;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        scrollToPosition(cuttentPosition);
        if (handler != null) {
            handler.sendEmptyMessageDelayed(0, delayTime);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
//        handler.removeMessages(0);
    }

    @Override
    public void setAdapter(@Nullable Adapter adapter) {
        super.setAdapter(adapter);
    }

    public class SmoothScrollLayoutManager extends LinearLayoutManager {

        public SmoothScrollLayoutManager(Context context) {
            super(context);
        }

        @Override
        public void smoothScrollToPosition(RecyclerView recyclerView,
                                           RecyclerView.State state, final int position) {

            LinearSmoothScroller smoothScroller =
                    new LinearSmoothScroller(recyclerView.getContext()) {
                        // 返回：滑过1px时经历的时间(ms)。
                        @Override
                        protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                            return 1500f / displayMetrics.densityDpi;
                        }
                    };

            smoothScroller.setTargetPosition(position);
            startSmoothScroll(smoothScroller);
        }
    }

}
