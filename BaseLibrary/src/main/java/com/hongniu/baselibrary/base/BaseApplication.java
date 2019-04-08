package com.hongniu.baselibrary.base;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.hongniu.baselibrary.R;
import com.hongniu.baselibrary.config.Param;
import com.sang.common.imgload.ImageLoader;
import com.sang.common.utils.SharedPreferencesUtils;
import com.sang.common.utils.ToastUtils;
import com.sang.common.utils.errorcrushhelper.CrashHelper;
import com.sang.thirdlibrary.bug.BugClient;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * 作者： ${桑小年} on 2018/7/30.
 * 努力，为梦长留
 */
public class BaseApplication extends MultiDexApplication {

    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context=this;
        final IWXAPI msgApi = WXAPIFactory.createWXAPI(this, null);
// 将该app注册到微信
        msgApi.registerApp(Param.weChatAppid);

        BugClient.getInstance().init(this);

        ToastUtils.getInstance().init(this);
        SharedPreferencesUtils.getInstance().initSharePreference(this);
        ImageLoader.getLoader().globalErrorImg(R.mipmap.placeholder);
        ImageLoader.getLoader().globalPlaceholder(R.mipmap.placeholder);
        ImageLoader.getLoader().headPlaceholder(R.mipmap.icon_default_avatar_100);
        ImageLoader.getLoader().headErrorImg(R.mipmap.icon_default_avatar_100);

    }

    public static Context getContext(){
        return context;
    }
}
