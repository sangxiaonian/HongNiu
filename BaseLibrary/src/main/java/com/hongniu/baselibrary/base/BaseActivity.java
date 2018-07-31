package com.hongniu.baselibrary.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.QuickContactBadge;
import android.widget.TextView;

import com.hongniu.baselibrary.R;
import com.sang.common.net.listener.TaskControl;

import io.reactivex.disposables.Disposable;

/**
 * 作者： ${桑小年} on 2018/7/30.
 * 努力，为梦长留
 */
public class BaseActivity extends AppCompatActivity implements TaskControl.OnTaskListener {

    protected Context mContext;
    private FrameLayout llToolbarLeft;
    private FrameLayout llToolbarRight;
    private ImageButton imgToolbarLeft;
    private ImageButton imgToolbarRight;
    private TextView tvToolbarTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;

    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        llToolbarLeft = findViewById(R.id.toolbar_left);
        llToolbarRight = findViewById(R.id.toolbar_right);
        imgToolbarLeft = findViewById(R.id.toolbar_src_left);
        imgToolbarRight = findViewById(R.id.toolbar_src_right);
        tvToolbarTitle = findViewById(R.id.toolbar_title);

        if (llToolbarLeft!=null){
            llToolbarLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }
    }

    protected void setToolbarTitle(String title){
        if (tvToolbarTitle!=null){
            tvToolbarTitle.setText(title);
        }
    }

    protected void setToolBarLeftClick(final View.OnClickListener leftClick){
        if (llToolbarLeft!=null){
            llToolbarLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (leftClick!=null){
                        leftClick.onClick(view);
                    }
                }
            });
        }
    }

    protected void setToolbarSrcLeft(int resID){
        if (imgToolbarLeft!=null) {
            imgToolbarLeft.setImageResource(resID);
        }
    }


    protected void setToolbarSrcRight(int resID) {
        if (imgToolbarRight!=null) {
            imgToolbarRight.setImageResource(resID);
        }
    }

    protected void setLlToolbarRightClick(final View.OnClickListener listener){
        if (llToolbarRight!=null){
            llToolbarRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener!=null){
                        listener.onClick(view);
                    }
                }
            });
        }
    }
    protected void initView() {
    }

    protected void initData() {
    }

    protected void initListener() {
    }

    @Override
    public void onTaskStart(Disposable d) {

    }

    @Override
    public void onTaskSuccess() {

    }

    @Override
    public void onTaskDetail(float present) {

    }

    @Override
    public void onTaskFail(Throwable e, String code, String msg) {

    }
}
