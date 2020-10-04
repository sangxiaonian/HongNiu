package com.hongniu.baselibrary.widget;

import android.content.Context;
import android.content.res.TypedArray;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.hongniu.baselibrary.R;

/**
 * 作者：  on 2019/10/26.
 */
public class RadioIconButton extends FrameLayout {
    private   String titleCheck;
    private int iconIndexCheck;
    private int titleColorCheck;
    private int iconCheck;
    private int icon;
    private int titleColor;
    private int iconIndex;
    private String title;

    private ImageView imgIcon;
    private TextView tvTitle;
    private ImageView imgIndex;
    private boolean hide;//是否要隐藏index
    private boolean check;

    public RadioIconButton(@NonNull Context context) {
        this(context, null);
    }

    public RadioIconButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RadioIconButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View inflate = LayoutInflater.from(context).inflate(R.layout.radio_icon_button, this, false);
        addView(inflate);
        imgIcon = inflate.findViewById(R.id.img_icon);
        tvTitle = inflate.findViewById(R.id.tv_title);
        imgIndex = inflate.findViewById(R.id.img_index);


        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.radio_icon_button);
        title =
                a.getString(R.styleable.radio_icon_button_title);
        titleCheck =
                a.getString(R.styleable.radio_icon_button_titleCheck);
        titleColor =
                a.getColor(R.styleable.radio_icon_button_titleColor,
                        getResources().getColor(R.color.white));
        titleColorCheck =
                a.getColor(R.styleable.radio_icon_button_titleColorCheck,
                        getResources().getColor(R.color.white));
        icon =
                a.getResourceId(R.styleable.radio_icon_button_icon, 0);
        iconCheck =
                a.getResourceId(R.styleable.radio_icon_button_iconCheck, 0);
        iconIndexCheck =
                a.getResourceId(R.styleable.radio_icon_button_indexCheck ,0);
        iconIndex =
                a.getResourceId(R.styleable.radio_icon_button_index,0);
        hide = a.getBoolean(R.styleable.radio_icon_button_hideIndex, false);
        a.recycle();
        _upView();
    }

    public void setTitleCheck(String titleCheck) {
        this.titleCheck = titleCheck;
        _upView();
    }

    public void setIconIndexCheck(int iconIndexCheck) {
        this.iconIndexCheck = iconIndexCheck;
        _upView();
    }

    public void setTitleColorCheck(int titleColorCheck) {
        this.titleColorCheck = titleColorCheck;
        _upView();
    }

    public void setIconCheck(int iconCheck) {
        this.iconCheck = iconCheck;
        _upView();
    }

    public void setIcon(int icon) {
        this.icon = icon;
        _upView();
    }

    public void setTitleColor(int titleColor) {
        this.titleColor = titleColor;
        _upView();
    }

    public void setIconIndex(int iconIndex) {
        this.iconIndex = iconIndex;
        _upView();
    }

    public void setTitle(String title) {
        this.title = title;
        _upView();
    }

    public void setImgIcon(ImageView imgIcon) {
        this.imgIcon = imgIcon;
        _upView();
    }

    public void setTvTitle(TextView tvTitle) {
        this.tvTitle = tvTitle;
        _upView();
    }

    public void setImgIndex(ImageView imgIndex) {
        this.imgIndex = imgIndex;
        _upView();
    }

    public void setHide(boolean hide) {
        this.hide = hide;
        _upView();
    }

    public void setCheck(boolean check) {
        this.check = check;
        _upView();
    }

    public boolean isCheck() {
        return check;
    }

    /**
     * 更新控件
     */
    private void _upView() {
        if (check){
            titleCheck=titleCheck==null?title:titleCheck;
            titleColorCheck=titleColorCheck==0?titleColor:titleColorCheck;
            iconCheck=iconCheck==0?icon:iconCheck;
        }else {
            title=title==null?titleCheck:title;
            titleColor=titleColor==0?titleColorCheck:titleColor;
            icon=icon==0?iconCheck:icon;
        }
        tvTitle.setText(title == null ? "" : title);
        tvTitle.setTextColor(check ? titleColorCheck : titleColor);
        imgIcon.setImageResource(check?iconCheck:icon);

        imgIndex.setImageResource(check ? iconIndexCheck : iconIndex);
        imgIndex.setVisibility(hide?GONE:VISIBLE);
        imgIndex.setVisibility(VISIBLE);

    }

}
