package com.fy.companylibrary.widget.pop;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.fy.androidlibrary.utils.DeviceUtils;
import com.fy.androidlibrary.utils.JLog;
import com.fy.androidlibrary.utils.StatusBarUtils;
import com.fy.androidlibrary.widget.recycle.holder.PeakHolder;
import com.fy.companylibrary.R;
import com.fy.companylibrary.widget.pop.inter.OnFiltrateDismissListener;

/**
 * 作者：  on 2019/11/22.
 */
public class FiltrateDialog extends Dialog {

    OnFiltrateDismissListener dismissListener;
    private final Point point;
    private PeakHolder holder;
    private View tragetView;
    private ViewGroup content;
    private int type = -1;
    private int sourHeight;
    private View inflate;

    public FiltrateDialog(@NonNull Context context) {
        this(context, R.style.dialogTransparent);
    }


    public FiltrateDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        Window dialogWindow = getWindow();
        //FLAG_NOT_TOUCH_MODAL作用：即使该window可获得焦点情况下，仍把该window之外的任何event发送到该window之后的其他window
        dialogWindow.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);

//FLAG_WATCH_OUTSIDE_TOUCH作用：如果点击事件发生在window之外，就会收到一个特殊的MotionEvent，为ACTION_OUTSIDE
        dialogWindow.setFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);

//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);
        point = DeviceUtils.getScreenSize(context);
        lp.height = WindowManager.LayoutParams.MATCH_PARENT; // 高度设置为屏幕的
        lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度设置为屏幕的
        dialogWindow.setAttributes(lp);

        setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (dismissListener != null) {
                    dismissListener.onFiltrateDismiss(FiltrateDialog.this, tragetView);
                }
            }
        });

        setCanceledOnTouchOutside(false);
        initView(context);

        getWindow().setWindowAnimations(R.style.dialog_alpha); //设置窗口弹出动画

    }

    public void setDismissListener(OnFiltrateDismissListener dismissListener) {
        this.dismissListener = dismissListener;
    }

    void initView(Context context) {
        inflate = LayoutInflater.from(context).inflate(R.layout.popu_filtrate, null);
        content = inflate.findViewById(R.id.content);
        setContentView(inflate);
        View viewById = inflate.findViewById(R.id.view_out);
        viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        viewById.setFocusable(true);

    }


    public void changeType(PeakHolder holder) {
        this.holder = holder;
    }

    public void show(View view) {
        tragetView = view;
        content.removeAllViews();
        if (holder != null && holder.getItemView() != null) {
            holder.initView(holder.getItemView(), type);
            content.addView(holder.getItemView());
        }


        if (sourHeight == 0) {
            inflate.setAlpha(0);
        }
        if (!isShowing()) {
            show();
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        long delay = 0;
        if (sourHeight == 0) {
            delay = 200;
            sourHeight = inflate.getHeight();
        }
        if (tragetView != null) {
            WindowManager.LayoutParams params = getWindow().getAttributes();
            int[] locations = new int[2];
            tragetView.getLocationInWindow(locations);
            params.y = locations[1] - StatusBarUtils.getStatusBarHeight(getContext()) + tragetView.getHeight();
            params.height = sourHeight - params.y;
            params.width = point.x;
            getWindow().setAttributes(params);
        }
        content.postDelayed(new Runnable() {
            @Override
            public void run() {
                inflate.setAlpha(1);
            }
        }, delay);

    }
}
