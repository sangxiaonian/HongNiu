package com.hongniu.baselibrary.base;

import android.support.multidex.MultiDexApplication;

import com.alibaba.android.arouter.launcher.ARouter;
import com.hongniu.baselibrary.config.Param;
import com.sang.common.utils.SharedPreferencesUtils;
import com.sang.common.utils.ToastUtils;
import com.sang.thirdlibrary.bug.BugClient;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * 作者： ${桑小年} on 2018/7/30.
 * 努力，为梦长留
 */
public class BaseApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        final IWXAPI msgApi = WXAPIFactory.createWXAPI(this, null);
// 将该app注册到微信
        msgApi.registerApp(Param.weChatAppid);


       BugClient.getInstance().init(this);

        ToastUtils.getInstance().init(      this);
        SharedPreferencesUtils.getInstance().initSharePreference(this);
    }
}
