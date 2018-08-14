package com.hongniu.modulelogin;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamLogin;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.baselibrary.base.NetObserver;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.modulelogin.net.HttpLoginFactory;
import com.sang.common.utils.ToastUtils;

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
        initListener();
    }

    @Override
    protected void initView() {
        super.initView();
        etPhone = findViewById(R.id.et_phone);
        bt = findViewById(R.id.bt_login);
        tvCaluse = findViewById(R.id.tv_clause);
        bt.setEnabled(false);
        bt.setOnClickListener(this);
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
                if (etPhone.getText().toString().length() == 11) {
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
            if (phone.length() == 11 && phone.startsWith("1")) {
                HttpLoginFactory.getSmsCode(phone)
                        .subscribe(new NetObserver<CommonBean<String>>(this) {
                            @Override
                            public void onNext(CommonBean<String> result) {
                                super.onNext(result);
                                ArouterUtils.getInstance()
                                        .builder(ArouterParamLogin.activity_sms_verify)
                                        .withString(Param.TRAN, phone)
                                        .navigation(mContext);
                            }
                        });


            } else {
                ToastUtils.getInstance().makeToast(ToastUtils.ToastType.NORMAL).show(R.string.login_phone_error);
            }

        } else if (i == R.id.tv_clause) {

        }
    }
}
