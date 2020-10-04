package com.fy.androidlibrary.widget;

import android.content.Context;
import android.content.res.TypedArray;

import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fy.androidlibrary.R;


/**
 * 作者： ${桑小年} on 2018/7/31.
 * 努力，为梦长留
 */
public class SwitchTextLayout extends FrameLayout implements View.OnClickListener {

    TextView tvTitle;
    ImageView imageView;

    private boolean open;
    OnSwitchListener listener;
    private View inflate;


    private int openColor;
    private int openIcon;
    private int closeIcon;
    private int closeColor;
    private String title;
    private float textSize;


    public SwitchTextLayout(@NonNull Context context) {
        this(context,null,0);
    }

    public SwitchTextLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SwitchTextLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);

    }

    public boolean isOpen() {
        return open;
    }

    public void setListener(OnSwitchListener listener) {
        this.listener = listener;
    }

    private void initView(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SwitchTextLayout);
            title = typedArray.getString(R.styleable.SwitchTextLayout_title);
            textSize = typedArray.getDimension(R.styleable.SwitchTextLayout_titleSize, 0);
            openColor = typedArray.getColor(R.styleable.SwitchTextLayout_openColor, openColor);
            closeColor = typedArray.getColor(R.styleable.SwitchTextLayout_closeColor, closeColor);
            closeIcon = typedArray.getResourceId(R.styleable.SwitchTextLayout_closeIcon, closeIcon);
            openIcon = typedArray.getResourceId(R.styleable.SwitchTextLayout_openIcon, openIcon);
            typedArray.recycle();
        }

        inflate = LayoutInflater.from(context).inflate(R.layout.switch_text_default_item, this, false);
        addView(inflate);
        tvTitle = inflate.findViewById(R.id.title);
        imageView = inflate.findViewById(R.id.img_course);
        inflate.setOnClickListener(this);
        closeSwitch();
        setTitle(title);
        setTextSize(textSize);
        setCheck(false);

    }


    public void openSwitch() {
        open=true;
        imageView.setRotation(180);
    }

    public void closeSwitch() {
        open=false;
        imageView.setRotation(0);
    }

    public void setCheck(boolean select){
        if (select){
            if (openColor!=0) {
                tvTitle.setTextColor(openColor);
            }
            if (openIcon!=0) {
                imageView.setImageResource(openIcon);
            }
        }else {
            if (closeColor!=0) {
                tvTitle.setTextColor(closeColor);
            }
            if (closeIcon!=0) {
                imageView.setImageResource(closeIcon);
            }
        }
    }


    @Override
    public void onClick(View v) {
        open = !open;
        if (open) {
            openSwitch();
            if (listener != null) {
                listener.onOpen(this,inflate);
            }
        } else {
            closeSwitch();
            if (listener != null) {
                listener.onClose(this,inflate);
            }
        }

    }

    public void setTitle(String title) {
        if (title!=null){
            this.title = title;
            tvTitle.setText(title);
        }
    }

    public void setTextSize(float textSize) {
        if (textSize>0){
            this.textSize = textSize;
            tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);
        }
    }

    public String getTitle() {
        return title;
    }

    public void setOpenColor(int openColor) {
        this.openColor = openColor;
    }

    public void setOpenIcon(int openIcon) {
        this.openIcon = openIcon;
    }

    public void setCloseIcon(int closeIcon) {
        this.closeIcon = closeIcon;
    }

    public void setCloseColor(int closeColor) {
        this.closeColor = closeColor;
    }

    public interface OnSwitchListener {
        void onOpen(SwitchTextLayout switchTextLayout, View view);

        void onClose(SwitchTextLayout switchTextLayout, View view);
    }

}
