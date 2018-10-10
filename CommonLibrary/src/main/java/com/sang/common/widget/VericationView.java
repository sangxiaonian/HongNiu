package com.sang.common.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.sang.common.R;
import com.sang.common.utils.DeviceUtils;

/**
 * 作者： ${PING} on 2018/1/12.
 * 短信验证码使用的View
 */

public class VericationView extends LinearLayout implements TextWatcher, View.OnFocusChangeListener, View.OnKeyListener {

    private int mEditCount;
    private int childGap;
    private int childWidth, childHeight;
    private int childLayout;
    private OnCompleteListener listener;
    private int type;

    private OnContentChangeListener onChangeListener;

    public VericationView(Context context) {
        super(context);
        intiView(context, null);
    }

    public VericationView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        intiView(context, attrs);
    }

    public VericationView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        intiView(context, attrs);
    }

    private void intiView(Context context, AttributeSet attrs) {
        mEditCount = 6;
        float scale = context.getResources().getDisplayMetrics().density;
        childGap = (int) (10f * scale + 0.5f);
        childLayout = R.layout.verication_default_item;

        post(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < mEditCount; i++) {
                    EditText editext = getEditext(childWidth, childHeight);
                    addView(editext);
                    if (i == 0) {
                        editext.setFocusable(true);
                        editext.setFocusableInTouchMode(true);
                        editext.requestFocus();
                    }
                }
            }
        });
    }


    public void setChildLayout(int childLayout) {
        this.childLayout = childLayout;
        requestLayout();
    }

    public void setListener(OnCompleteListener listener) {
        this.listener = listener;
        requestLayout();
    }

    public void setOnChangeListener(OnContentChangeListener onChangeListener) {
        this.onChangeListener = onChangeListener;
    }

    /**
     * 设置验证码是否加密
     *
     * @param type 0 明文数字，1 密码数字
     */
    public void setType(int type) {
        this.type = type;
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            if (childAt instanceof EditText) {
                if (type == 1) {
                    ((EditText) childAt).setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
                } else {
                    ((EditText) childAt).setInputType(InputType.TYPE_CLASS_NUMBER);

                }
            }
        }
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        int mWidth = (int) ((getMeasuredWidth() - getPaddingLeft() - getPaddingRight() - childGap * (mEditCount - 1)) / (mEditCount * 1.0f));
        int mHeight = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();
        childHeight = mHeight;
        childWidth = mWidth;


    }

    private EditText getEditext(int childWidth, int childHeight) {

        EditText editText;
        if (childLayout != 0) {
            editText = (EditText) LayoutInflater.from(getContext()).inflate(childLayout, this, false);
        } else {
            editText = new EditText(getContext());
        }

        ViewGroup.LayoutParams params = editText.getLayoutParams();
        if (params == null) {
            params = new ViewGroup.LayoutParams(childWidth, childHeight);
        } else {
            params.width = childWidth;
            params.height = childHeight;
        }
        editText.setGravity(Gravity.CENTER);
        editText.setLayoutParams(params);
        editText.setCursorVisible(true);
        editText.setEms(2);
        editText.setMaxLines(1);
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(1)});
        editText.setSingleLine(true);
        if (type == 1) {
            editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        } else {
            editText.setInputType(InputType.TYPE_CLASS_NUMBER);

        }
        editText.setLongClickable(false);
        editText.addTextChangedListener(this);
        editText.setOnFocusChangeListener(this);
        editText.setOnKeyListener(this);


        return editText;

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        int startX = getPaddingLeft();
        int startY = getPaddingTop();

        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            view.layout(startX, startY, startX + view.getMeasuredWidth(), startY + view.getMeasuredHeight());
            startX += view.getMeasuredWidth() + childGap;
        }

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }


    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.length() > 0) {
            changeFouce();
            checkAndCommit();
        } else {
            backFocus();
        }
    }

    private void checkAndCommit() {
        StringBuilder stringBuilder = new StringBuilder();
        boolean full = true;
        for (int i = 0; i < mEditCount; i++) {
            EditText editText = (EditText) getChildAt(i);
            String content = editText.getText().toString();
            if (content.length() == 0) {
                full = false;
                break;
            } else {
                stringBuilder.append(content);
            }
        }

        if (onChangeListener != null) {
            onChangeListener.onContentChange(mEditCount,stringBuilder.toString());
        }

        if (full) {
            if (listener != null) {
                listener.onComplete(stringBuilder.toString());
            }

        }
    }

    public String getContent() {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < mEditCount; i++) {
            EditText editText = (EditText) getChildAt(i);
            String content = editText.getText().toString();

            stringBuilder.append(content);

        }
        return stringBuilder.toString();
    }


    /**
     * 更改光标位置
     */
    private void changeFouce() {
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (view instanceof EditText) {
                if (((EditText) view).getText().toString().length() == 0) {
                    view.requestFocus();//获取焦点
                    break;
                }
            }
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus && v instanceof EditText) {
            ((EditText) v).setSelection(((EditText) v).getText().toString().length());
        }
    }


    private void backFocus() {
        int count = getChildCount();
        EditText editText;
        for (int i = count - 1; i >= 0; i--) {
            editText = (EditText) getChildAt(i);
            if (editText.getText().length() == 1) {
                editText.requestFocus();
                editText.setSelection(editText.getText().length());
                return;
            }
        }
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_DEL) {
            backFocus();
        }

        return false;
    }

    public void clear() {
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (view instanceof EditText) {
                ((EditText) view).setText("");
            }
        }
        if (getChildAt(0) != null) {
            getChildAt(0).requestFocus();
        }
    }

    public void openSoft() {
        post(new Runnable() {
            @Override
            public void run() {
                if (getChildAt(0) != null) {
                    DeviceUtils.openSoft(getChildAt(0));
                }
            }
        });
    }

    public void closeSoft() {

        post(new Runnable() {
            @Override
            public void run() {
                DeviceUtils.hideSoft(findFocus());
            }
        });
    }


    public interface OnCompleteListener {
        void onComplete(String content);
    }

    public interface OnContentChangeListener {
        /**
         * 验证码输入框内容变化监听
         * @param mEditCount 验证码总共位数
         * @param content    已输入的内容
         */
        void onContentChange(int mEditCount, String content);
    }
}
