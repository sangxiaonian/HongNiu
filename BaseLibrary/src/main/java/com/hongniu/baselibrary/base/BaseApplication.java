package com.hongniu.baselibrary.base;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.hongniu.baselibrary.config.Param;
import com.sang.common.utils.SharedPreferencesUtils;
import com.sang.common.utils.ToastUtils;

/**
 * 作者： ${桑小年} on 2018/7/30.
 * 努力，为梦长留
 */
public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (Param.isDebug) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this); // 尽可能早，推荐在Application中初始化

        ToastUtils.getInstance().init(this);
        SharedPreferencesUtils.getInstance().initSharePreference(this);
    }
}
