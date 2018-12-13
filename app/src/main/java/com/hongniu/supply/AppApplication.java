package com.hongniu.supply;

import android.app.ActivityManager;
import android.content.Context;
import android.text.TextUtils;

import com.alibaba.android.arouter.launcher.ARouter;
import com.hongniu.baselibrary.base.BaseApplication;
import com.hongniu.baselibrary.base.NetObserver;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.event.Event;
import com.hongniu.baselibrary.utils.clickevent.ClickEventBean;
import com.hongniu.baselibrary.utils.clickevent.ClickEventUtils;
import com.hongniu.baselibrary.utils.clickevent.ItemClickEventBean;
import com.hongniu.supply.net.HttpMainFactory;
import com.sang.common.event.BusFactory;
import com.sang.common.net.rx.RxUtils;
import com.sang.common.utils.CommonUtils;
import com.sang.common.utils.DeviceUtils;
import com.sang.common.utils.JLog;
import com.sang.common.utils.errorcrushhelper.CrashHelper;
import com.sang.thirdlibrary.chact.ChactHelper;
import com.sang.thirdlibrary.chact.control.ChactControl;
import com.xiaomi.channel.commonutils.logger.LoggerInterface;
import com.xiaomi.mipush.sdk.Logger;
import com.xiaomi.mipush.sdk.MiPushClient;

import org.greenrobot.eventbus.EventBus;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.File;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import io.rong.push.RongPushClient;

import static io.rong.imkit.utils.SystemUtils.getCurProcessName;

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
            JLog.i("----------打印日志---------");
        }

        ARouter.init(this); // 尽可能早，推荐在Application中初始化
//        XMPush();
        CrashHelper.getInstance()
                .init(this);
        ChactHelper.getHelper()
                .initHelper(this)
                //未读消息监听
                .setUnReadCountListener(new ChactControl.OnReceiveUnReadCountListener() {
                    @Override
                    public void onReceiveUnRead(int count) {
                        EventBus.getDefault().postSticky(count);
                    }
                })
        ;

        initData();
    }

    private void XMPush() {
//        RongPushClient.registerMiPush(application, "2882303761517871354", "5731787151354");
        if (getApplicationInfo().packageName.equals(getCurProcessName(this))) {
            MiPushClient.registerPush(this, "2882303761517871354", "5731787151354");
            LoggerInterface newLogger = new LoggerInterface() {
                @Override
                public void setTag(String tag) {
                    // ignore
                }

                @Override
                public void log(String content, Throwable t) {
                    JLog.d(content);
                    t.printStackTrace();
                }

                @Override
                public void log(String content) {
                    JLog.d(content);
                }
            };
            Logger.setLogger(this, newLogger);
        }

    }


    //上传异常情况
    private void initData() {
        if (getApplicationInfo().packageName.equals(getCurProcessName(getApplicationContext()))) {
            upClickEvent();
            upApiError();
        }

    }

    private Subscription subscription;

    /**
     * 上传Api异常
     */
    private void upApiError() {
        File[] logInforFiles = CrashHelper.getInstance().getLogInforFiles();
        if (logInforFiles != null && logInforFiles.length > 0) {
            Flowable.fromArray(logInforFiles)
                    .map(new Function<File, String>() {
                        @Override
                        public String apply(File file) throws Exception {
                            String s = CrashHelper.getInstance().readFile(file);
                            //读取完成之后，直接删除文件
                            JLog.i("读取文件完成：" + file.getAbsolutePath());
                            file.delete();
                            return s;
                        }
                    })
                    .filter(new Predicate<String>() {
                        @Override
                        public boolean test(String s) throws Exception {
                            return !TextUtils.isEmpty(s);
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Subscriber<String>() {
                        @Override
                        public void onSubscribe(Subscription s) {
                            s.request(5);
                            subscription = s;
                        }

                        @Override
                        public void onNext(String s) {
                            ClickEventBean eventBean = new ClickEventBean();
                            eventBean.setSystemType("android");
                            try {
                                eventBean.setAppVersion(DeviceUtils.getVersionName(AppApplication.this));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            eventBean.setSystemModel(DeviceUtils.getDeviceBrand());
                            eventBean.setCrashlog(s);
                            HttpMainFactory.upClickEvent(eventBean)
                                    .subscribe(new NetObserver<String>(null) {
                                        @Override
                                        public void onError(Throwable e) {
                                            if (subscription != null) {
                                                subscription.request(1);
                                            }

                                        }

                                        @Override
                                        public void doOnSuccess(String data) {
                                            if (subscription != null) {
                                                subscription.request(1);
                                            }
                                        }
                                    });
                        }

                        @Override
                        public void onError(Throwable t) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }


    }

    /**
     * 上传点击事件
     */
    private void upClickEvent() {
        ClickEventBean eventParams = ClickEventUtils.getInstance().getEventParams(this);
        if (eventParams != null) {
            HttpMainFactory.upClickEvent(eventParams)
                    .subscribe(new NetObserver<String>(null) {

                        @Override
                        public void doOnSuccess(String data) {
                            JLog.d("clickEvent:  上传完成，清除已上传数据");
                            ClickEventUtils.getInstance().clear();
                        }
                    });
        }
    }
}
