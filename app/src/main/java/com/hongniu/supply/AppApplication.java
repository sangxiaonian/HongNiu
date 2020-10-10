package com.hongniu.supply;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.alibaba.android.arouter.launcher.ARouter;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.hongniu.baselibrary.arouter.ArouterParamsMatch;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.base.BaseApplication;
import com.hongniu.baselibrary.base.NetObserver;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.event.Event;
import com.hongniu.baselibrary.net.BaseClient;
import com.hongniu.baselibrary.utils.clickevent.ClickEventBean;
import com.hongniu.baselibrary.utils.clickevent.ClickEventUtils;
import com.hongniu.freight.Config;
import com.hongniu.supply.entity.PushBean;
import com.hongniu.supply.net.HttpMainFactory;
import com.hongniu.supply.ui.receiver.MyPlushBroadcastReceiver;
import com.fy.androidlibrary.utils.DeviceUtils;
import com.fy.androidlibrary.utils.JLog;
import com.fy.androidlibrary.utils.SharedPreferencesUtils;
import com.sang.common.utils.errorcrushhelper.CrashHelper;
import com.sang.thirdlibrary.chact.ChactHelper;
import com.sang.thirdlibrary.chact.control.ChactControl;
import com.sang.thirdlibrary.push.NotificationUtils;
import com.sang.thirdlibrary.push.client.PushClient;
import com.sang.thirdlibrary.push.inter.PlushDealWithMessageListener;
import com.sang.thirdlibrary.push.inter.PlushRegisterListener;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import org.greenrobot.eventbus.EventBus;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.File;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

import static io.rong.imkit.utils.SystemUtils.getCurProcessName;

/**
 * 作者： ${PING} on 2018/10/8.
 */
public class AppApplication extends BaseApplication {


    @Override
    public void onCreate() {

        Config.getInstance().init(this,BuildConfig.DEBUG);

        BaseClient.getInstance().baseUrl(Param.url);
        //二维码扫描
        ZXingLibrary.initDisplayOpinion(this);
        super.onCreate();
        if (BuildConfig.DEBUG) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }

        ARouter.init(this); // 尽可能早，推荐在Application中初始化
        CrashHelper.getInstance()
                .init(this);
        ChactHelper.getHelper()
                .initHelper(this,BuildConfig.RONG_CLOUD_APP_KEY)
                //未读消息监听
                .setUnReadCountListener(new ChactControl.OnReceiveUnReadCountListener() {
                    @Override
                    public void onReceiveUnRead(int count) {
                        EventBus.getDefault().postSticky(count);
                    }
                })
        ;
        initData();
        registerUM();
    }

    public void registerUM() {
        PushClient plushClient = PushClient.getClient();
        plushClient.setPlush(this);
        plushClient.setPlushRegisterListener(new PlushRegisterListener() {
            @Override
            public void onSuccess(String data) {
                JLog.d("推送注册成功:" + data);
                SharedPreferencesUtils.getInstance().putString(Param.UMENG, data);
                EventBus.getDefault().postSticky(new Event.Umeng());
            }

            @Override
            public void onFailure(String code, String errorReson) {
                JLog.e("推送注册失败:" + code + " :  " + errorReson);
            }
        });
        NotificationUtils.getInstance().setReceiver(MyPlushBroadcastReceiver.class);
        plushClient.setPlushDealWithMessageListener(new PlushDealWithMessageListener() {
            @Override
            public void dealMessage(Context context, Object message) {
                JLog.i("接收到推送信息：" + message.toString());
                if (message instanceof UMessage) {
                    try {
                        String custom = ((UMessage) message).custom;
                        PushBean pushBean = null;
                        try {
                              pushBean = new Gson().fromJson(custom, PushBean.class);
                        } catch (JsonSyntaxException e) {
                            e.printStackTrace();
                        }
                        NotificationUtils.getInstance()
                                .setSound(pushBean!=null&&pushBean.showSound?R.raw.notify_sound:0)
                                .showNotification(context, (UMessage) message);
                    } catch (JsonSyntaxException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

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
