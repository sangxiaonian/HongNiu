package com.hongniu.baselibrary.base;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.githang.statusbar.StatusBarCompat;
import com.hongniu.baselibrary.R;
import com.hongniu.baselibrary.entity.CloseActivityEvent;
import com.sang.common.event.BusFactory;
import com.sang.common.net.listener.TaskControl;
import com.sang.common.widget.dialog.CenterAlertDialog;
import com.sang.common.widget.dialog.LoadDialog;
import com.sang.common.widget.dialog.builder.CenterAlertBuilder;
import com.sang.common.widget.dialog.inter.DialogControl;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.reactivex.disposables.Disposable;

/**
 * 作者： ${桑小年} on 2018/7/30.
 * 努力，为梦长留
 */
public class BaseActivity extends AppCompatActivity implements TaskControl.OnTaskListener {

    protected Context mContext;
    protected FrameLayout llToolbarLeft;
    protected FrameLayout llToolbarRight;
    protected ImageView imgToolbarLeft;
    protected ImageView imgToolbarRight;
    protected TextView tvToolbarTitle;
    protected TextView tvToolbarRight;
    protected View tool;
    private LoadDialog loading;
    private Disposable disposable;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        initLoading();

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (getUseEventBus()) {
            BusFactory.getBus().register(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (getUseEventBus()) {
            BusFactory.getBus().unregister(this);
        }
    }

    protected boolean getUseEventBus() {
        return false;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(CloseActivityEvent event) {
        if (event != null) {
            finish();
        }
    }


    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        llToolbarLeft = findViewById(R.id.toolbar_left);
        llToolbarRight = findViewById(R.id.toolbar_right);
        imgToolbarLeft = findViewById(R.id.toolbar_src_left);
        imgToolbarRight = findViewById(R.id.toolbar_src_right);
        tvToolbarTitle = findViewById(R.id.toolbar_title);
        tvToolbarRight = findViewById(R.id.tv_toolbar_right);
        tool = findViewById(R.id.tool);

        if (llToolbarLeft != null) {
            llToolbarLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }
    }

    protected void setToolbarTitle(String title) {
        if (tvToolbarTitle != null) {
            tvToolbarTitle.setText(title);
        }
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.color_bg), true);
    }

    protected void setToolbarDarkTitle(String title) {
        if (tvToolbarTitle != null) {
            if (tool != null) {
                tool.setBackgroundColor(getResources().getColor(R.color.color_title_dark));
            }
            tvToolbarTitle.setTextColor(Color.WHITE);
            if (imgToolbarLeft != null) {
                imgToolbarLeft.setImageResource(R.mipmap.icon_back_w_36);
            }
            tvToolbarTitle.setText(title);
        }

        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.color_title_dark), false);


    }

    protected void setToolBarLeftClick(final View.OnClickListener leftClick) {
        if (llToolbarLeft != null) {
            llToolbarLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (leftClick != null) {
                        leftClick.onClick(view);
                    }
                }
            });
        }
    }

    protected void setToolbarSrcLeft(int resID) {
        if (imgToolbarLeft != null) {
            imgToolbarLeft.setImageResource(resID);

        }
    }


    protected void setToolbarSrcRight(int resID) {
        if (imgToolbarRight != null) {
            imgToolbarRight.setImageResource(resID);
        }
    }

    protected void setToolbarSrcRight(String msg) {
        if (imgToolbarRight != null) {
            imgToolbarRight.setVisibility(View.GONE);
        }
        if (tvToolbarRight != null) {
            tvToolbarRight.setVisibility(View.VISIBLE);
            tvToolbarRight.setText(msg == null ? "" : msg);
        }
    }

    protected void setToolbarRightClick(final View.OnClickListener listener) {
        if (llToolbarRight != null) {
            llToolbarRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onClick(view);
                    }
                }
            });
        }
    }


    protected void showAleart(String msg) {
        new CenterAlertBuilder()
                .setRightClickListener(new DialogControl.OnButtonRightClickListener() {
                    @Override
                    public void onRightClick(View view, DialogControl.ICenterDialog dialog) {
                        dialog.dismiss();

                    }

                })
                .hideBtLeft()
                .hideContent()
                .setDialogTitle(msg)
                .creatDialog(new CenterAlertDialog(mContext))
                .show();
    }


    protected void initView() {
    }

    protected void initData() {
    }

    protected void initListener() {
    }

    @Override
    public void onTaskStart(Disposable d) {
        this.disposable = d;
        showLoad();
    }

    @Override
    public void onTaskSuccess() {
        hideLoad();
    }

    @Override
    public void onTaskDetail(float present) {

    }

    @Override
    public void onTaskFail(Throwable e, String code, String msg) {
        hideLoad();
        showAleart(msg);
    }

    public void initLoading() {
        if (loading == null) {
            loading = new LoadDialog(this);
            loading.setImageLoad(R.raw.loading);
            loading.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                        hideLoad();
                        if (disposable != null) {
                            disposable.dispose();
                        }
                        return true;
                    }
                    return false;
                }
            });
        }

    }

    public void showLoad() {
        if (loading != null && !loading.isShowing()) {
            loading.show();
        }
    }

    public void hideLoad() {
        if (loading != null && loading.isShowing()) {
            loading.dismiss();
        }
    }
}
