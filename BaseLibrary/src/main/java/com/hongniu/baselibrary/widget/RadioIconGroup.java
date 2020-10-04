package com.hongniu.baselibrary.widget;

import android.content.Context;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * 作者：  on 2019/10/27.
 */
public class RadioIconGroup extends LinearLayout {

    CheckListener checkListener;
    CheckChangeListener checkChangeListener;

    public RadioIconGroup(Context context) {
        super(context);
    }

    public RadioIconGroup(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RadioIconGroup(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child instanceof RadioIconButton) {
                child.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        _changeCheck((RadioIconButton) v);
                    }
                });
            }
        }
    }

    public void setCheckListener(CheckListener checkListener) {
        this.checkListener = checkListener;
    }

    public void setCheckChangeListener(CheckChangeListener checkChangeListener) {
        this.checkChangeListener = checkChangeListener;
    }

    /**
     * 更改当前被选中的选项
     *
     * @param radioIconButton
     */
    private void _changeCheck(RadioIconButton radioIconButton) {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child instanceof RadioIconButton) {
                if (child == radioIconButton) {
                    if (((RadioIconButton) child).isCheck()) {
                        continue;
                    } else {
                        ((RadioIconButton) child).setCheck(true);
                        if (checkListener != null) {
                            checkListener.onCheck(this, (RadioIconButton) child);
                        }
                        if (checkChangeListener != null) {
                            checkChangeListener.onCheckChange(this, (RadioIconButton) child, child.getId(), true);
                        }
                    }
                } else {
                    if (((RadioIconButton) child).isCheck()) {
                        ((RadioIconButton) child).setCheck(false);
                        if (checkChangeListener != null) {
                            checkChangeListener.onCheckChange(this, (RadioIconButton) child, child.getId(), false);
                        }
                    }
                }
            }
        }

    }

    public interface CheckChangeListener {
        /**
         * 选择更改的时候
         *
         * @param group
         * @param iconButton
         */
        void onCheckChange(RadioIconGroup group, RadioIconButton iconButton, int iconButtonId, boolean check);
    }

    public interface CheckListener {
        /**
         * 选择更改的时候
         *
         * @param group
         * @param iconButton
         */
        void onCheck(RadioIconGroup group, RadioIconButton iconButton);
    }
}
