package com.hongniu.supply.wxapi;

import android.content.Intent;
import android.os.Bundle;

import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.baselibrary.entity.CreatInsuranceBean;
import com.hongniu.baselibrary.event.Event;
import com.hongniu.supply.R;
import com.fy.androidlibrary.event.BusFactory;
import com.sang.thirdlibrary.pay.PayConfig;
import com.sang.thirdlibrary.pay.entiy.PayResult;
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
    private boolean insurance;

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
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (resp.errCode == 0) {
                BusFactory.getBus().post(new PayResult(PayResult.SUCCESS));
//                Toast.makeText(this, "支付成功", Toast.LENGTH_LONG).show();
//                if (insuranceBean != null&&insurance) {
//                    ArouterUtils.getInstance().builder(ArouterParamOrder.activity_insurance_creat)
//                            .withParcelable(Param.TRAN,insuranceBean)
//                            .navigation(this);
//                }else {
//                    ArouterUtils.getInstance().builder(ArouterParamsApp.activity_main)
//                            .navigation(this);
//                }
            } else if (resp.errCode == -2) {
                BusFactory.getBus().post(new PayResult(PayResult.CANCEL));

//                Toast.makeText(this, "取消支付", Toast.LENGTH_LONG).show();
            } else {
                BusFactory.getBus().post(new PayResult(PayResult.FAIL));

//                Toast.makeText(this, "支付失败", Toast.LENGTH_LONG).show();
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
            this.insurance=event.isCreatInsurance;
        }
        BusFactory.getBus().removeStickyEvent(event);
    }

}