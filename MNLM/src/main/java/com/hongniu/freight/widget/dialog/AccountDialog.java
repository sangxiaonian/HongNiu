package com.hongniu.freight.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fy.androidlibrary.utils.CollectionUtils;
import com.fy.androidlibrary.utils.DeviceUtils;
import com.fy.androidlibrary.widget.recycle.adapter.XAdapter;
import com.hongniu.freight.R;
import com.hongniu.freight.widget.dialog.inter.DialogControl;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者： ${PING} on 2018/10/9.
 * 选择提现到账方式
 */
public class AccountDialog<T> implements DialogControl.IDialog, View.OnClickListener {

    protected View imgCancel;//取消按钮
    protected RecyclerView rv;
    protected Dialog dialog;
    protected List<T> inforBeans;

    protected XAdapter<T> adapter;
    private TextView tvTitle;




    public AccountDialog(@NonNull Context context) {
        this(context, 0);
    }


    public AccountDialog(@NonNull Context context, int themeResId) {
        initView(context, themeResId);
        initData(context);
    }

    protected void initView(Context context, int themeResId) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.mnlm_dialog_account, null);
        imgCancel = inflate.findViewById(R.id.img_cancel);
        imgCancel.setOnClickListener(this);
        rv = inflate.findViewById(R.id.rv);
        tvTitle = inflate.findViewById(R.id.tv_title);


        dialog = new Dialog(context, themeResId);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(inflate);

        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, DeviceUtils.dip2px(context, 360));
        dialog.getWindow().setWindowAnimations(R.style.dialog_ani);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));
    }

    protected void initData(Context context) {
        inforBeans = new ArrayList<T>();
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(manager);
        adapter = getAdapter(context, inforBeans);
        rv.setAdapter(adapter);
    }


    public void setTitle(String title) {
        tvTitle.setText(title == null ? "" : title);
    }


    protected XAdapter<T> getAdapter(Context context, List<T> inforBeans) {
        return null;
    }


    public void setData(List<T> data) {
        inforBeans.clear();
        if (!CollectionUtils.isEmpty(data)) {
            inforBeans.addAll(data);
        }
        adapter.notifyDataSetChanged();
    }



    @Override
    public void setCancelable(boolean cancelable) {
        dialog.setCancelable(cancelable);
    }

    @Override
    public void setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
        dialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
    }

    @Override
    public void show() {
        dialog.show();
    }

    @Override
    public boolean isShowing() {
        return dialog.isShowing();
    }

    @Override
    public void dismiss() {
        dialog.dismiss();
    }


    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.img_cancel) {
            dismiss();
        }
    }
}
