package com.fy.androidlibrary.widget;

import android.animation.ValueAnimator;
import android.content.Context;

import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;

import com.fy.androidlibrary.control.ParallaxListener;
import com.fy.androidlibrary.control.ScrollPercentChangeListener;
import com.fy.androidlibrary.utils.DeviceUtils;
import com.fy.androidlibrary.utils.JLog;


/**
 * 作者：  on 2019/11/7.
 */
public class XNestedScrollView extends NestedScrollView implements NestedScrollView.OnScrollChangeListener, ValueAnimator.AnimatorUpdateListener {

    private float scrollY;
    private float scrollX;
    private float flagY;
    private float flagX;

    ScrollPercentChangeListener percentChangeListener;
    ParallaxListener parallaxListener;

    ValueAnimator animator;

    public XNestedScrollView(@NonNull Context context) {
        this(context, null);
    }

    public XNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        setOnScrollChangeListener(this);
        animator = ValueAnimator.ofFloat(0, 0);
        animator.setDuration(200);
        animator.addUpdateListener(this);
        dragMax = DeviceUtils.dip2px(context, 50);
    }


    @Override
    public void onScrollChange(NestedScrollView nestedScrollView, int scrollX, int scrollY,
                               int oldScrollX, int oldScrollY) {
        this.scrollX = scrollX;
        this.scrollY = scrollY;

        JLog.i(scrollY+">>>>");
        if (percentChangeListener != null) {
            percentChangeListener.onScrollPercentChange(this.scrollX, this.scrollY
                    , flagX, flagY,
                    flagX == 0 ? 0 : this.scrollX / flagX,
                    flagY == 0 ? 0 : this.scrollY / flagY

            );
        }

    }

    private float currentY = -1;

    private float dragY;

    private float dragMax;

    public void setParallaxListener(ParallaxListener parallaxListener) {
        this.parallaxListener = parallaxListener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        cancelAnimation();

        if (parallaxListener!=null) {
            if (currentY <= 0&&getScrollY()==0) {
                currentY = ev.getY();
            }
            JLog.d(ev.getY()+"-------->"+currentY);
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    break;
                case MotionEvent.ACTION_MOVE:
                    float y = ev.getY();
                    float drag = y - currentY;
                    if ((drag>0&&!canScrollVertically(-1))||dragY!=0) {
                        float scale = 1;
                        if (dragY > dragMax && dragMax > 0) {
                            scale = dragMax / dragY;
                        }
                        dragY += (drag) * (scale/3);
                        if (dragY<0){
                            dragY=0;
                        }
                        if (parallaxListener!=null){
                            parallaxListener.onDrag(dragY);
                        }
                        currentY = y;
                        return true;
                    }
                    break;
                default:
                    currentY = -1;
                    startAnimatio(dragY);
                    dragY = 0;
                    break;
            }
        }


        return super.onTouchEvent(ev);
    }

    private void startAnimatio(float flagY) {
        if (flagY>0) {
            animator.setFloatValues(flagY, 0);
            animator.start();
        }
    }

    private void cancelAnimation() {
        animator.cancel();
    }

    public void setPercentChangeListener(ScrollPercentChangeListener percentChangeListener) {
        this.percentChangeListener = percentChangeListener;
    }

    public void setFlagY(float flagY) {
        this.flagY = flagY;
    }

    public void setFlagX(float flagX) {
        this.flagX = flagX;
    }

    /**
     * <p>Notifies the occurrence of another frame of the animation.</p>
     *
     * @param animation The animation which was repeated.
     */
    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
      float current= (float) animation.getAnimatedValue();
      if (parallaxListener!=null){
          parallaxListener.onUpDataAnimation(current);
      }
    }
}
