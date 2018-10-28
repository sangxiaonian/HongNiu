package com.hongniu.modulelogin.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamLogin;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.baselibrary.base.NetObserver;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.net.HttpAppFactory;
import com.hongniu.baselibrary.utils.Utils;
import com.hongniu.modulelogin.R;
import com.hongniu.modulelogin.net.HttpLoginFactory;
import com.sang.common.net.OkHttp;
import com.sang.common.utils.ConvertUtils;
import com.sang.common.utils.ToastUtils;
import com.sang.common.widget.ItemView;

/**
 * @data 2018/10/26
 * @Author PING
 * @Description 忘记/设置支付密码界面
 */
@Route(path = ArouterParamLogin.activity_login_forget_pass)
public class LoginForgetPassActivity extends BaseActivity implements View.OnClickListener {

    private ItemView itemPhone;
    private ItemView itemSms;
    private ItemView itemPass;
    private ItemView itemNewPass;
    private TextView sendSms;
    private Button buSum;

    private final int originTime = 60;
    private int currentTime;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (currentTime > 1) {
                currentTime--;
                sendSms.setText(currentTime + getString(R.string.login_up_sms_veri));
                sendSms.setEnabled(false);
                handler.sendEmptyMessageDelayed(0, 1000);
            } else {
                sendSms.setEnabled(true);
                sendSms.setText(getString(R.string.login_get_sms_veri));
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_forget_pass);
        int type = getIntent().getIntExtra(Param.TRAN, 0);
        setToolbarTitle(type==0?"忘记支付密码":"设置支付密码");
        initView();
        initData();
        initListener();
    }

    @Override
    protected void initView() {
        super.initView();
        itemPhone = findViewById(R.id.item_phone);
        itemSms = findViewById(R.id.item_sms);
        itemPass = findViewById(R.id.item_pass);
        itemNewPass = findViewById(R.id.item_entry_pass);
        sendSms = findViewById(R.id.bt_sms);
        buSum = findViewById(R.id.bt_sum);
    }

    @Override
    protected void initData() {
        super.initData();
        itemPhone.setTextCenter(Utils.getPersonInfor().getContact());

    }

    @Override
    protected void initListener() {
        super.initListener();
        buSum.setOnClickListener(this);
        sendSms.setOnClickListener(this);

    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bt_sms) {
            ToastUtils.getInstance().show("发送验证码");

            HttpAppFactory.getSmsCode(Utils.getLoginInfor().getMobile())
                    .subscribe(new NetObserver<String>(this) {
                        @Override
                        public void doOnSuccess(String data) {
                            startCountTime();
                        }
                    });


        } else if (v.getId() == R.id.bt_sum) {
            if (check()) {
                ToastUtils.getInstance().makeToast(ToastUtils.ToastType.SUCCESS).show();
                String trim = itemPass.getTextCenter();

                HttpLoginFactory
                        .upPassword(ConvertUtils.MD5(trim),itemSms.getTextCenter())
                        .subscribe(new NetObserver<String>(this) {
                            @Override
                            public void doOnSuccess(String data) {
                                Utils.setPassword(true);
                                ToastUtils.getInstance().makeToast(ToastUtils.ToastType.SUCCESS).show();
                                Intent intent=new Intent();
                                setResult(RESULT_OK,intent);
                                finish();
                            }
                        });

            }


        }
    }

    private boolean check() {
        if (TextUtils.isEmpty(itemSms.getTextCenter())) {
            showAleart(itemSms.getTextCenterHide());
            return false;
        }
        if (TextUtils.isEmpty(itemPass.getTextCenter())) {
            showAleart(itemPass.getTextCenterHide());
            return false;
        }

        if (itemPass.getTextCenter().toString().trim().length()<6) {
            showAleart(itemPass.getTextCenterHide());
            return false;
        }

        if (TextUtils.isEmpty(itemNewPass.getTextCenter())) {
            showAleart(itemNewPass.getTextCenterHide());
            return false;
        }



        if (!TextUtils.equals(itemPass.getTextCenter(),itemNewPass.getTextCenter())){
            showAleart("两次密码输入不一致");
            return false;
        }
        return true;

    }

    private void startCountTime() {
        currentTime = originTime;
        handler.sendEmptyMessageDelayed(0, 0);
    }
}
