package com.fy.companylibrary.ui;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.fy.androidlibrary.toast.ToastUtils;
import com.fy.companylibrary.R;
import com.githang.statusbar.StatusBarCompat;
import com.hongniu.baselibrary.ui.ModuleBaseActivity;
import com.sang.thirdlibrary.push.client.PushClient;


/**
 * 作者：  on 2019/10/28.
 * <p>
 * 所有相关业务的基类
 * 如需要公司业务相关逻辑代码，在此处实现
 */
public class CompanyBaseActivity extends ModuleBaseActivity {
    protected FrameLayout llToolbarLeft;
    protected FrameLayout llToolbarRight;
    protected ImageView imgToolbarLeft;
    protected ImageView imgToolbarRight;
    protected TextView tvToolbarTitle;
    protected TextView tvToolbarRight;
    protected View tool;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    }

    @Override
    protected void showErrorAlert(int code, String msg) {
        super.showErrorAlert(code, msg);
        ToastUtils.getInstance().show(msg);
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
                    onBackPressed();
                }
            });
        }
    }

    @Override
    public void setContentView(View layoutResID) {
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
                    onBackPressed();
                }
            });
        }
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    protected void setWhitToolBar(String title) {
        if (tvToolbarTitle != null && title != null) {
            tvToolbarTitle.setText(title);
        }
        configToolbar(
                getResources().getColor(R.color.color_of_ffffff),
                getResources().getColor(R.color.color_of_333333),
                R.drawable.ic_back_3d3d3d,
                getResources().getColor(R.color.color_of_333333),
                true
        );
    }

    protected void configToolbar(int bgColor, int titleColor, int backIcon, int rightColor, boolean lightStatusBar) {
        if (tvToolbarTitle != null) {
            if (tool != null) {
                tool.setBackgroundColor(bgColor);
            }
            tvToolbarTitle.setTextColor(titleColor);
        }
        if (imgToolbarLeft != null) {
            imgToolbarLeft.setImageResource(backIcon);
        }
        if (tvToolbarRight != null) {
            tvToolbarRight.setTextColor(rightColor);
        }

        //更改颜色
        StatusBarCompat.setStatusBarColor(this, bgColor, lightStatusBar);
    }

    protected void hideBack(boolean hide) {
        if (hide) {
            imgToolbarLeft.setVisibility(View.INVISIBLE);
            llToolbarLeft.setClickable(false);
        } else {
            imgToolbarLeft.setVisibility(View.VISIBLE);
            llToolbarLeft.setClickable(true);
        }
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

    protected void setToolbarMsgRight(String msg) {
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



    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
//        overridePendingTransition(R.anim.tran_entry_in,R.anim.tran_entry_out);
    }

    @Override
    public void finish() {
        super.finish();
//        overridePendingTransition(R.anim.tran_quit_in,R.anim.tran_quit_out);

    }

    @Override
    protected void onStart() {
        super.onStart();
        PushClient.getClient().onAppStart(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }
}
