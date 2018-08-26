package com.hongniu.supply.wxapi;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.hongniu.supply.R;
import com.sang.common.utils.JLog;
import com.sang.thirdlibrary.pay.PayConfig;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
	
	private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";
	
    private IWXAPI api;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiy_wx_pay_entry);
        
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
		int result = 0;
		JLog.i(resp.toString()+">>>"+resp.getType());
		if (resp.getType()== ConstantsAPI.COMMAND_PAY_BY_WX){
			if (resp.errCode==0){
				Toast.makeText(this,"支付成功",Toast.LENGTH_LONG).show();
			}else if (resp.errCode==-2){
				Toast.makeText(this,"取消支付",Toast.LENGTH_LONG).show();
			}else {
				Toast.makeText(this,"支付失败",Toast.LENGTH_LONG).show();
			}
			finish();
		}

	}
}