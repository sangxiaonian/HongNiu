package com.hongniu.supply.ui.fragment;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamsApp;
import com.hongniu.baselibrary.base.BaseFragment;
import com.hongniu.supply.R;
import com.fy.androidlibrary.toast.ToastUtils;
import com.uuzuche.lib_zxing.activity.CodeUtils;

/**
 *@data  2019/8/23
 *@Author PING
 *@Description
 * 手动输入订单号
 *
 */
@Route(path = ArouterParamsApp.fragment_hand_input)
public class HandInputFragment extends BaseFragment implements View.OnClickListener {
    private EditText et;
    private Button bt;

    CodeUtils.AnalyzeCallback callback;


    @Override
    protected View initView(LayoutInflater inflater) {
        View inflate = inflater.inflate(R.layout.fragment_hand_input, null);
        et=inflate.findViewById(R.id.et);
        bt=inflate.findViewById(R.id.bt);
        return inflate;
    }


    @Override
    protected void initData() {
        super.initData();

    }

    @Override
    protected void initListener() {
        super.initListener();
        bt.setOnClickListener(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CodeUtils.AnalyzeCallback){
            callback= (CodeUtils.AnalyzeCallback) context;
        }
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.bt) {
            String s = et.getText().toString();
            if (TextUtils.isEmpty(s)){
                ToastUtils.getInstance().makeToast(ToastUtils.ToastType.CENTER).show("请输入运单号");
            }else {
                if (callback != null) {
                    callback.onAnalyzeSuccess(null, s);
                }
            }
        }
    }
}
