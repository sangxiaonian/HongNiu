package com.hongniu.modulelogin;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamLogin;
import com.hongniu.baselibrary.arouter.ArouterParamOrder;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.baselibrary.config.Param;
import com.sang.common.utils.ToastUtils;
import com.sang.common.widget.VericationView;

@Route(path = ArouterParamLogin.activity_sms_verify)
public class LoginSmsVerifyActivity extends BaseActivity implements VericationView.OnCompleteListener {

    private String phone;
    private TextView tvPhone;
    private Button btGetNewVeri;
    private VericationView vericationView;

    private final int originTime=10;
    private int currentTime;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (currentTime>0) {
                currentTime--;
                btGetNewVeri.setText(currentTime+getString(R.string.login_up_sms_veri));
                handler.sendEmptyMessageDelayed(0, 1000);
            }else {
                btGetNewVeri.setEnabled(true);
                btGetNewVeri.setText(getString(R.string.login_get_sms_veri));
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_sms_verify);
        setToolbarTitle("");
        phone=getIntent().getStringExtra(Param.TRAN);
        initView();
        initData();
        initListener();
    }


    @Override
    protected void initView() {
        super.initView();
        tvPhone = findViewById(R.id.tv_phone);
        vericationView = findViewById(R.id.vercationview);
        btGetNewVeri=findViewById(R.id.bt_get_new_veri);
        startCountTime();
    }

    @Override
    protected void initData() {
        super.initData();
        StringBuffer buffer=new StringBuffer();
        buffer.append("验证码已发送至\t");
        if (phone!=null&&phone.length()==11){
            buffer.append(phone.substring(0,3))
                    .append("\t")
                    .append(phone.substring(3,7))
                    .append("\t")
                    .append(phone.substring(7));
        }
        tvPhone.setText(buffer.toString().trim());
    }

    @Override
    protected void initListener() {
        super.initListener();
        vericationView.setListener(this);
        btGetNewVeri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCountTime();
            }
        });
    }

    private void startCountTime(){
        currentTime=originTime;
        btGetNewVeri.setEnabled(false);
        btGetNewVeri.setText(currentTime+getString(R.string.login_up_sms_veri));
        handler.sendEmptyMessageDelayed(0,1000);
    }

    /**
     * 验证码输入完成
     * @param content 输入的验证码
     */
    @Override
    public void onComplete(String content) {
        ArouterUtils.getInstance().builder(ArouterParamOrder.activity_order_main).navigation(mContext);
    }


}
