package com.hongniu.modulelogin;

import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.fy.androidlibrary.utils.CommonUtils;
import com.hongniu.baselibrary.arouter.ArouterParamLogin;
import com.hongniu.baselibrary.arouter.ArouterParamsApp;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.baselibrary.base.NetObserver;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.entity.H5Config;
import com.hongniu.baselibrary.net.HttpAppFactory;
import com.hongniu.modulelogin.net.HttpLoginFactory;
import com.hongniu.baselibrary.entity.CommonBean;
import com.fy.androidlibrary.utils.SharedPreferencesUtils;
import com.fy.androidlibrary.toast.ToastUtils;

@Route(path = ArouterParamLogin.activity_login)
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText etPhone;
    private Button bt;
    private TextView tvCaluse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setToolbarTitle("");
        initView();
        initData();
        initListener();
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferencesUtils.getInstance().remove(Param.LOGIN_ONFOR);
    }

    @Override
    protected void initView() {
        super.initView();
        etPhone = findViewById(R.id.et_phone);
        bt = findViewById(R.id.bt_login);
        tvCaluse = findViewById(R.id.tv_clause);
        bt.setEnabled(false);
        bt.setOnClickListener(this);
        tvCaluse.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        super.initData();
        SpannableStringBuilder builder=new SpannableStringBuilder(getString(R.string.login_consent));
        ForegroundColorSpan span=new ForegroundColorSpan( getResources().getColor(R.color.color_order_color_6d6d6d));
        builder.setSpan(span,10,builder.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvCaluse.setText(builder);
    }

    @Override
    protected boolean reciveClose() {
        return true;
    }

    @Override
    protected void initListener() {
        super.initListener();
        etPhone.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (CommonUtils.isPhone(etPhone.getText().toString().trim())) {
                    bt.setEnabled(true);
                } else {
                    if (bt.isEnabled()) {
                        bt.setEnabled(false);
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.bt_login) {
            final String phone = etPhone.getText().toString().trim();
            if (CommonUtils.isPhone(phone)) {
                HttpAppFactory.getSmsCode(phone)
                        .subscribe(new NetObserver<String>(this) {
                            @Override
                            public void doOnSuccess(String data) {
                                ArouterUtils.getInstance()
                                        .builder(ArouterParamLogin.activity_sms_verify)
                                        .withString(Param.TRAN, phone)
                                        .navigation(mContext);
                            }
                        });


            } else {
                ToastUtils.getInstance().makeToast(ToastUtils.ToastType.NORMAL).show(R.string.phone_error);
            }

        } else if (i == R.id.tv_clause) {
            H5Config h5Config=new H5Config(getString(R.string.hongniu_agreement),Param.hongniu_agreement,true);
            ArouterUtils.getInstance().builder(ArouterParamsApp.activity_h5).withSerializable(Param.TRAN,h5Config).navigation(this);
        }
    }
}
