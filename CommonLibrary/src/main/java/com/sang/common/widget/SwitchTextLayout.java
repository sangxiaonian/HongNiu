package com.sang.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.sang.common.R;


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
        super(context);
    }

    public SwitchTextLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SwitchTextLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);

    }

    private void initView(Context context, AttributeSet attrs) {

        closeColor = getResources().getColor(R.color.color_title_dark);
        openColor = getResources().getColor(R.color.color_light);
        closeIcon = R.drawable.icon_arrow_down_333;
        openIcon = R.drawable.icon_arrow_down_f06f28;


        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SwitchTextLayout);
            title = typedArray.getString(R.styleable.SwitchTextLayout_title);
            textSize = typedArray.getDimension(R.styleable.SwitchTextLayout_titleSize, 0);
            openColor = typedArray.getColor(R.styleable.SwitchTextLayout_openColor, openColor);
            closeColor = typedArray.getColor(R.styleable.SwitchTextLayout_closeColor, closeColor);
            closeIcon = typedArray.getResourceId(R.styleable.SwitchTextLayout_closeColor, closeIcon);
            openIcon = typedArray.getResourceId(R.styleable.SwitchTextLayout_closeColor, openIcon);
            typedArray.recycle();
        }

        inflate = LayoutInflater.from(context).inflate(R.layout.switch_text_default_item, this, false);
        addView(inflate);
        tvTitle = inflate.findViewById(R.id.title);
        imageView = inflate.findViewById(R.id.img_course);
        inflate.setOnClickListener(this);
        closeSwitch();
        setTitle(title);

    }


    public void openSwitch() {
        open=true;
        tvTitle.setTextColor(openColor);
        imageView.setImageResource(openIcon);
        imageView.setRotation(0);
    }

    public void closeSwitch() {
        open=false;
        tvTitle.setTextColor(closeColor);
        imageView.setImageResource(closeIcon);
        imageView.setRotation(180);
    }

    @Override
    public void onClick(View v) {
        open = !open;
        if (open) {
            openSwitch();
            if (listener != null) {
                listener.onOpen(inflate);
            }
        } else {
            closeSwitch();
            if (listener != null) {
                listener.onClose(inflate);
            }
        }

    }

    public void setTitle(String title) {
        this.title = title;
        if (title!=null){
            tvTitle.setText(title);
        }
    }


    public interface OnSwitchListener {
        void onOpen(View view);

        void onClose(View view);
    }

}
