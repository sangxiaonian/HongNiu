package com.fy.androidlibrary.widget.viewpager;

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
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

/**
 * 作者： ${PING} on 2018/11/9.
 */
public class XViewPager extends RecyclerView {

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            removeMessages(0);
            smoothScrollToPosition(getCurrentItems() + 1);
            if (autoRecycle) {
                sendEmptyMessageDelayed(0, delayTime);
            }
        }
    };

    private long delayTime = 3000;
    private boolean recycle = true;//是否开启无限切换

    private boolean autoRecycle;//是否开启自动循环
    private float speed = 200;//滑动幕需要的时间
    private int orientation;
    private SmoothScrollLayoutManager manager;
    PagerScrollChangeListener pagerScrollChangeListener;
    private boolean isPager;

    public XViewPager(Context context) {
        this(context, null, 0);
    }

    public XViewPager(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XViewPager(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context, attrs, defStyle);
    }

    private void initView(final Context context, AttributeSet attrs, int defStyle) {
        manager = new SmoothScrollLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        setLayoutManager(manager);

        orientation = LinearLayoutManager.VERTICAL;
    }


    @Override
    public boolean onTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                resumeHandle();
                break;
            default:
                pauseHandle();
                break;


        }
        return super.onTouchEvent(e);
    }

    /**
     * 暂停滑动
     */
    private void pauseHandle() {
        if (autoRecycle && handler != null) {
            handler.removeMessages(0);
        }
    }

    /**
     * 继续滑动
     */
    private void resumeHandle() {
        if (autoRecycle && handler != null) {
            handler.removeMessages(0);
            handler.sendEmptyMessageDelayed(0, delayTime);
        }
    }


    /**
     * 设置是否一次只能滑动一个Item
     *
     * @param isPager
     */
    public void setIsPager(boolean isPager) {
        this.isPager = isPager;
    }

    SnapHelper helper;

    public void setAdapter(@Nullable ViewPagerAdapter adapter) {
        adapter.setRecycle(recycle);
        super.setAdapter(adapter);
        if (helper == null) {
            if (isPager) {
                helper = new LinearSnapHelper();
                helper.attachToRecyclerView(this);
            } else {
                helper = new PagerSnapHelper();
                helper.attachToRecyclerView(this);
            }
        }
    }


    public void setOrientation(@RecyclerView.Orientation int orientation) {
        this.orientation = orientation;
        LayoutManager manager = getLayoutManager();
        if (manager instanceof SmoothScrollLayoutManager) {
            ((SmoothScrollLayoutManager) manager).setOrientation(orientation);
        }
    }

    /**
     * 开始自动滑动
     */
    public void start() {
        autoRecycle = true;
        resumeHandle();

    }

    /**
     * 停止自动滑动
     */
    public void stop() {
        pauseHandle();
        autoRecycle = false;

    }


    /**
     * 是否无限循环
     *
     * @param recycle
     */
    public void setRecycle(boolean recycle) {
        this.recycle = recycle;
    }

    /**
     * 每次切换间隔时间
     *
     * @param time
     */
    public void setDelayTime(long time) {
        if (time > 0) {
            delayTime = time;
        }
    }


    private int last;

    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        Adapter adapter = getAdapter();
        if (state == 0) {
            int current = getCurrentItems();
            int itemCount = adapter.getItemCount();
            if (pagerScrollChangeListener != null && last != current && itemCount > 0) {
                pagerScrollChangeListener.onPageSelect(current % itemCount);
            }
            last = current;
        }

        if (autoRecycle) {
            if (state == 0) {
                resumeHandle();
            } else {
                pauseHandle();
            }
        }
    }

    /**
     * 设置滑动所需要的时间，值越大速度越慢
     *
     * @param speed 默认情况下为1000 ms 滚动完成
     */
    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void setPagerScrollChangeListener(PagerScrollChangeListener pagerScrollChangeListener) {
        this.pagerScrollChangeListener = pagerScrollChangeListener;
    }

    /**
     * 获取当前显示的item
     *
     * @return
     */
    public int getCurrentItems() {
        Adapter adapter = getAdapter();
        int current = 0;
        if (manager != null && adapter != null && adapter.getItemCount() > 0) {
            current = manager.findFirstCompletelyVisibleItemPosition();
        }
        return current;
    }

    public class SmoothScrollLayoutManager extends LinearLayoutManager {

        public SmoothScrollLayoutManager(Context context) {
            super(context);
        }

        @Override
        public void smoothScrollToPosition(RecyclerView recyclerView,
                                           State state, final int position) {
            if (speed <= 0 || getMeasuredHeight() == 0 || getMeasuredWidth() == 0) {
                super.smoothScrollToPosition(recyclerView, state, position);
            } else {
                LinearSmoothScroller smoothScroller =
                        new LinearSmoothScroller(getContext()) {
                            // 返回：滑过1px时经历的时间(ms)。
                            @Override
                            protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                                int distance;
                                if (orientation == LinearLayoutManager.VERTICAL) {
                                    distance = getMeasuredHeight();
                                } else {
                                    distance = getMeasuredWidth();
                                }
                                return speed / (distance * displayMetrics.density);
                            }
                        };
                smoothScroller.setTargetPosition(position);
                startSmoothScroll(smoothScroller);
            }
        }
    }

    public interface PagerScrollChangeListener {
        void onPageSelect(int position);
    }
}
