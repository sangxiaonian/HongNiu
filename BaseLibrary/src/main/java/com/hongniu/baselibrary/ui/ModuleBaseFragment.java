package com.hongniu.baselibrary.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fy.androidlibrary.event.BusFactory;
import com.fy.androidlibrary.net.listener.TaskControl;
import com.fy.androidlibrary.ui.BaseFragment;
import com.fy.androidlibrary.widget.load.LoadDialog;
import com.hongniu.baselibrary.R;

import io.reactivex.disposables.Disposable;

/**
 * 作者：  on 2019/10/29.
 */
public class ModuleBaseFragment extends BaseFragment implements TaskControl.OnTaskListener {

    protected Disposable disposable;
    private LoadDialog loading;
    private Handler dialogHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            dismiss();
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLoading();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = initView(inflater);
        view.setClickable(true);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (isUseEventBus()) {
            BusFactory.getBus().register(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (isUseEventBus()) {
            BusFactory.getBus().unregister(this);
        }
    }

    protected boolean isUseEventBus() {
        return false;
    }

    protected View initView(LayoutInflater inflater) {
        return null;
    }


    protected void initListener() {
    }

    protected void initData() {
    }

    @Override
    public void onDetach() {
        if (disposable != null) {
            disposable.dispose();
        }
        dismiss();
        super.onDetach();

    }


    protected void showErrorAlert(int code, String msg) {

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

    public void initLoading() {
        if (loading == null) {
            loading = new LoadDialog(getContext());
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
        dialogHandler.removeMessages(0);
        if (loading != null) {
            loading.show( );
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
            if (loading != null&&loading.isShowing()) {
                loading.dismiss();
            }
        } catch (Exception e) {
//            e.printStackTrace();
        }
    }
}
