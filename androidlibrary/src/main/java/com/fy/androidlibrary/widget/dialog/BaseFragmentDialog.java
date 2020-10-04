package com.fy.androidlibrary.widget.dialog;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.fy.androidlibrary.widget.dialog.inter.OnBackPressListener;
import com.fy.androidlibrary.widget.dialog.inter.OnDismissListener;

/**
 * 作者：  on 2019/12/10.
 */
public class BaseFragmentDialog extends DialogFragment {

    private OnBackPressListener backPressListener;
    private OnDismissListener dismissListener;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    dismiss();
                    if (backPressListener != null) {
                        backPressListener.onBackPress(BaseFragmentDialog.this);
                    }
                    return true;
                }
                return false;
            }
        });
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        if (dismissListener != null) {
            dismissListener.onDismiss(this);
        }
    }


    public void setBackPressListener(OnBackPressListener backPressListener) {
        this.backPressListener = backPressListener;
    }

    public void setDismissListener(OnDismissListener dismissListener) {
        this.dismissListener = dismissListener;
    }


    @Override
    public void show(@NonNull FragmentManager manager, @Nullable String tag) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
            if (manager.isDestroyed())
                return;
        }

        try {
            //在每个add事务前增加一个remove事务，防止连续的add
            if (isAdded()){
                manager.beginTransaction().show(this).commit();
            }else {
                manager.beginTransaction().remove(this).commit();
                super.show(manager, tag);
            }
        } catch (Exception e) {
            //同一实例使用不同的tag会异常，这里捕获一下
            e.printStackTrace();
        }
    }


    @Override
    public void dismiss() {
        super.dismiss();
    }
}
