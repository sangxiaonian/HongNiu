package com.hongniu.modulelogin.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamLogin;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.baselibrary.base.NetObserver;
import com.hongniu.baselibrary.net.HttpAppFactory;
import com.hongniu.baselibrary.utils.Utils;
import com.hongniu.modulelogin.R;
import com.sang.common.utils.ToastUtils;
import com.sang.common.widget.ItemView;

/**
 * @data 2018/10/26
 * @Author PING
 * @Description 忘记支付密码界面
 */
@Route(path = ArouterParamLogin.activity_login_forget_pass)
public class LoginForgetPassActivity extends BaseActivity implements View.OnClickListener {

    private ItemView itemPhone;
    private ItemView itemSms;
    private ItemView itemPass;
    private ItemView itemNewPass;
    private Button sendSms;
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
        setToolbarTitle("忘记支付密码");
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

            HttpAppFactory.getSmsCode(Utils.getLoginInfor().getContact())
                    .subscribe(new NetObserver<String>(this) {
                        @Override
                        public void doOnSuccess(String data) {
                            handler.sendEmptyMessage(0);
                        }
                    });


        } else if (v.getId() == R.id.bt_sum) {
            ToastUtils.getInstance().makeToast(ToastUtils.ToastType.SUCCESS).show();

        }
    }
}
