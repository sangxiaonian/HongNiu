package com.sang.thirdlibrary.pay;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import androidx.appcompat.app.AppCompatActivity;

import com.fy.androidlibrary.event.BusFactory;
import com.fy.androidlibrary.toast.ToastUtils;
import com.sang.thirdlibrary.R;
import com.sang.thirdlibrary.pay.ali.AliPay;
import com.sang.thirdlibrary.pay.control.PayControl;
import com.sang.thirdlibrary.pay.entiy.PayBean;
import com.sang.thirdlibrary.pay.entiy.PayResult;
import com.sang.thirdlibrary.pay.entiy.PayType;
import com.sang.thirdlibrary.pay.unionpay.UnionPayClient;
import com.sang.thirdlibrary.pay.wechat.WeChatAppPay;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class PayActivity extends AppCompatActivity {

    private Parcelable bean;
    private PayControl.IPayClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        PayType payType = (PayType) getIntent().getSerializableExtra("payType");
        bean = getIntent().getParcelableExtra("payInfor");
//        0微信支付1银联支付2线下支付3支付宝支付
        switch (payType) {
            case ALI:
                client = new AliPay();
                break;
            case WECHAT:
                client = new WeChatAppPay();
                break;
            case UNIONPAY:
                client = new UnionPayClient();
                break;
            default:
                ToastUtils.getInstance().show("请选择有效的支付方式");
                break;
        }
        if (client!=null) {
            client.pay(this, (PayBean) bean);
            BusFactory.getBus().register(this);
        }else {
            finish();
        }
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
}
