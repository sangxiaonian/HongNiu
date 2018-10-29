package com.hongniu.moduleorder.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.moduleorder.R;
import com.sang.common.event.BusFactory;
import com.sang.common.imgload.ImageLoader;
import com.sang.thirdlibrary.pay.ali.AliPay;
import com.sang.thirdlibrary.pay.entiy.PayBean;
import com.sang.thirdlibrary.pay.entiy.PayResult;
import com.sang.thirdlibrary.pay.unionpay.UnionPayClient;
import com.sang.thirdlibrary.pay.wechat.WeChatAppPay;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * @data 2018/10/29
 * @Author PING
 * @Description 支付界面
 */
public class WaitePayActivity extends BaseActivity {
    public static final String PAYTYPE = "payType";
    public static final String PAYINFOR = "payInfor";
    public static final String ISDEUBG = "ISDEUBG";
    private int payType;
    private PayBean bean;
    private boolean isDebug;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waite_pay);
        setToolbarTitle("等待付款");
        payType = getIntent().getIntExtra(PAYTYPE, 0);
        bean = getIntent().getParcelableExtra(PAYINFOR);
        isDebug = getIntent().getBooleanExtra(ISDEUBG,false);
//        0微信支付 1银联支付 2线下支付 3支付宝 4余额
        initView();
        switch (payType) {
            case 0://微信支付
                new WeChatAppPay() .pay(this, bean);
                break;
            case 1://银联支付
                new UnionPayClient().setDebug(isDebug).pay(this,   bean);
                break;
            case 2://线下支付
                BusFactory.getBus().post(new PayResult(PayResult.SUCCESS));
                break;
            case 3://支付宝支付
                new AliPay().setDebug(isDebug).pay(this,  bean);
                break;
            case 4://余额支付
                BusFactory.getBus().post(new PayResult(PayResult.SUCCESS));
                break;
        }


    }

    @Override
    protected void initView() {
        super.initView();
        ImageView img = findViewById(R.id.img);
        ImageLoader.getLoader().load(this, img, R.raw.listloading);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String str = data.getExtras().getString("pay_result");
        if (str.equalsIgnoreCase("success")) {
            BusFactory.getBus().post(new PayResult(PayResult.SUCCESS));
        } else if (str.equalsIgnoreCase("fail")) {
            BusFactory.getBus().post(new PayResult(PayResult.FAIL));

        } else if (str.equalsIgnoreCase("cancel")) {
            BusFactory.getBus().post(new PayResult(PayResult.CANCEL));
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(PayResult event) {
        Intent mIntent = new Intent();
        mIntent.putExtra("payResult", event.code);
        mIntent.putExtra("payResultDes", event.ms);
        // 设置结果，并进行传送
        setResult(Activity.RESULT_OK, mIntent);
        finish();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BusFactory.getBus().unregister(this);
    }


    public static void startPay(Activity activity, int payType, PayBean payBean) {
        startPay(activity, payType, payBean,false);
    }

    public static void startPay(Activity activity, int payType, PayBean payBean, boolean isDebug) {
        Intent intent = new Intent(activity, WaitePayActivity.class);
        intent.putExtra(PAYTYPE, payType);
        intent.putExtra(PAYINFOR, payBean);
        intent.putExtra(ISDEUBG, isDebug);
        activity.startActivityForResult(intent, 0);
    }


}
