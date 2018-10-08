package com.hongniu.supply;

import com.alibaba.android.arouter.launcher.ARouter;
import com.hongniu.baselibrary.base.BaseApplication;
import com.hongniu.baselibrary.config.Param;

/**
 * 作者： ${PING} on 2018/10/8.
 */
public class AppApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
//        ARouter.openLog();
//        ARouter.openDebug();
        ARouter.init(this); // 尽可能早，推荐在Application中初始化


    }
}
