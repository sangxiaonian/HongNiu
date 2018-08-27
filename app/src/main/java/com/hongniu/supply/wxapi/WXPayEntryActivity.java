package com.hongniu.supply.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.hongniu.baselibrary.arouter.ArouterParamOrder;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.entity.CreatInsuranceBean;
import com.hongniu.baselibrary.event.Event;
import com.hongniu.supply.R;
import com.sang.common.event.BusFactory;
import com.sang.common.utils.JLog;
import com.sang.thirdlibrary.pay.PayConfig;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class WXPayEntryActivity extends BaseActivity implements IWXAPIEventHandler {

    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";

    private IWXAPI api;
    private CreatInsuranceBean insuranceBean;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiy_wx_pay_entry);
        setToolbarDarkTitle("");
        BusFactory.getBus().register(this);
        api = WXAPIFactory.createWXAPI(this, PayConfig.weChatAppid);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        JLog.i("-----------------------------------");
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (resp.errCode == 0) {
                Toast.makeText(this, "支付成功", Toast.LENGTH_LONG).show();
                if (insuranceBean != null) {
                    ArouterUtils.getInstance().builder(ArouterParamOrder.activity_insurance_creat)
                            .withParcelable(Param.TRAN,insuranceBean)
                            .navigation(this);
                }
            } else if (resp.errCode == -2) {
                Toast.makeText(this, "取消支付", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "支付失败", Toast.LENGTH_LONG).show();
            }
            finish();
        }

    }

    @Override
    protected boolean getUseEventBus() {
        return true;
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Event.CraetInsurance event) {
        if (event != null && event.getBean() != null) {
            CreatInsuranceBean bean = event.getBean();
            this.insuranceBean = bean;
        }
        BusFactory.getBus().removeStickyEvent(event);
    }

}