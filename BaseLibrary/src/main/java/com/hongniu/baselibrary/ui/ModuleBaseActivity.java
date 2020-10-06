package com.hongniu.baselibrary.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fy.androidlibrary.net.listener.TaskControl;
import com.fy.androidlibrary.ui.BaseActivity;
import com.fy.androidlibrary.widget.load.LoadDialog;
import com.hongniu.baselibrary.R;

import io.reactivex.disposables.Disposable;

/**
 * 作者：  on 2019/10/28.
 */
public class ModuleBaseActivity extends BaseActivity implements TaskControl.OnTaskListener {
    protected Disposable disposable;
    protected Context mContext;
    private LoadDialog loading;
    private Handler dialogHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            dismiss();
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        initLoading();

    }
    @Override
    protected void onDestroy() {
        dismiss();
        if (disposable != null) {
            disposable.dispose();
        }
        super.onDestroy();
    }

    protected void showErrorAlert(int code, String msg) {

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
    public void onTaskFail(Throwable e, int code, String msg) {
        hideLoad(0);
        showErrorAlert(code, msg);
    }

    @Override
    public boolean isDestroyed() {
        return super.isDestroyed();
    }

    public void initLoading() {
        if (loading == null) {
            loading = new LoadDialog(mContext);
            loading.setImageLoad(R.raw.loading);
            loading.setCancelable(false);
            loading.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                        dismiss();
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
        try {
            dialogHandler.removeMessages(0);
            if (loading != null) {
                loading.show( );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void hideLoad(long delyTime) {
        dialogHandler.removeMessages(0);
        dialogHandler.sendEmptyMessageDelayed(0, delyTime);
    }

    public void hideLoad() {
        hideLoad(500);

    }

    private void dismiss() {
        try {
            if (loading != null) {
                loading.dismiss();
            }
        } catch (Exception e) {
        }
    }
}
