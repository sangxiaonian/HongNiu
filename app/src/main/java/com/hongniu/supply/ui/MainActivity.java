package com.hongniu.supply.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.fy.androidlibrary.utils.CommonUtils;
import com.google.gson.Gson;
import com.hongniu.baselibrary.arouter.ArouterParamLogin;
import com.hongniu.baselibrary.arouter.ArouterParamOrder;
import com.hongniu.baselibrary.arouter.ArouterParamsApp;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.base.ModuleBaseActivity;
import com.hongniu.baselibrary.base.NetObserver;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.entity.CloseActivityEvent;
import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.entity.LoginBean;
import com.hongniu.baselibrary.entity.QueryPayPassword;
import com.hongniu.baselibrary.entity.RoleTypeBean;
import com.hongniu.baselibrary.entity.TruckGudieSwitchBean;
import com.hongniu.baselibrary.entity.UpLoactionEvent;
import com.hongniu.baselibrary.event.Event;
import com.hongniu.baselibrary.net.BaseClient;
import com.hongniu.baselibrary.net.HttpAppFactory;
import com.hongniu.baselibrary.utils.PermissionUtils;
import com.hongniu.baselibrary.utils.Utils;
import com.hongniu.baselibrary.utils.clickevent.ClickEventBean;
import com.hongniu.baselibrary.utils.clickevent.ClickEventParams;
import com.hongniu.baselibrary.utils.clickevent.ClickEventUtils;
import com.hongniu.baselibrary.widget.dialog.UpDialog;
import com.hongniu.moduleorder.entity.VersionBean;
import com.hongniu.moduleorder.net.HttpOrderFactory;
import com.hongniu.moduleorder.utils.LoactionUpUtils;
import com.hongniu.supply.R;
import com.hongniu.supply.net.HttpMainFactory;
import com.hongniu.supply.utils.RedDialog;
import com.fy.androidlibrary.event.BusFactory;
import com.fy.androidlibrary.utils.DeviceUtils;
import com.fy.androidlibrary.utils.JLog;
import com.fy.androidlibrary.utils.SharedPreferencesUtils;
import com.sang.common.widget.dialog.builder.CenterAlertBuilder;
import com.sang.common.widget.dialog.inter.DialogControl;
import com.sang.thirdlibrary.chact.UserInfor;
import com.sang.thirdlibrary.chact.control.OnGetUserInforListener;
import com.sang.thirdlibrary.map.LoactionUtils;
import com.sang.thirdlibrary.map.utils.MapConverUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import com.sang.thirdlibrary.chact.ChactHelper;

import io.reactivex.Observable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Function;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import rongyun.sang.com.chactmodule.ui.fragment.ChactListFragment;

@Route(path = ArouterParamsApp.activity_main)
public class MainActivity extends ModuleBaseActivity implements View.OnClickListener, AMapLocationListener {

    private TextView demo;

    private LoactionUtils loaction;
    private LoactionUpUtils upLoactionUtils;//上传位置信息

    ViewGroup tab1;
    ViewGroup tab2;
    ViewGroup tab3;
    ViewGroup tab4;
    ViewGroup tab5;

    TextView tv1;
    TextView tv2;
    TextView tv3;
    TextView tv4;
    TextView tv5;
    TextView tv_unread;

    ImageView img1;
    ImageView img2;
    ImageView img3;
    ImageView img4;
    ImageView img5;


    Fragment homeFragment, orderFragment, messageFragment, meFragment, currentFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mnlm_activity_main);
        initView();
        initData();
        initListener();
        loaction = LoactionUtils.getInstance();
        loaction.init(mContext);
        loaction.setListener(this);
        tab4.performClick();
        tab1.performClick();

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        //切换url
        BaseClient.getInstance().baseUrl(Param.url);


        String extra = intent.getStringExtra(Param.TRAN);
        if (extra != null && extra.equals(Param.LOGIN_OUT)) {
            ArouterUtils.getInstance().builder(ArouterParamLogin.activity_login).navigation(mContext);
            finish();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        BaseClient.getInstance().baseUrl(Param.url);
    }

    @Override
    protected boolean reciveClose() {
        return false;
    }

    @Override
    protected boolean getUseEventBus() {
        return true;
    }

    @Override
    protected void initView() {
        super.initView();
        tab1 = findViewById(R.id.tab1);
        tab2 = findViewById(R.id.tab2);
        tab3 = findViewById(R.id.tab3);
        tab4 = findViewById(R.id.tab4);
        tab5 = findViewById(R.id.tab5);
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        tv3 = findViewById(R.id.tv3);
        tv4 = findViewById(R.id.tv4);
        tv5 = findViewById(R.id.tv5);
        tv_unread = findViewById(R.id.tv_unread);
        img1 = findViewById(R.id.img1);
        img2 = findViewById(R.id.img2);
        img3 = findViewById(R.id.img3);
        img4 = findViewById(R.id.img4);
        img5 = findViewById(R.id.img5);
        demo = findViewById(R.id.demo);

    }

    @Override
    protected void initData() {
        super.initData();
        tv_unread.setVisibility(View.GONE);
//        messageFragment = new ChactListFragment();
//        getSupportFragmentManager()
//                .beginTransaction()
//                .add(R.id.content, messageFragment)
//                .commit();
        demo.setVisibility(Param.isDebug ? View.VISIBLE : View.GONE);

        //检查版本更新
        checkVersion();

        //查询是否开启货车导航
        HttpAppFactory.queryTruckGuide()
                .subscribe(new NetObserver<TruckGudieSwitchBean>(null) {
                    @Override
                    public void doOnSuccess(TruckGudieSwitchBean data) {
                        SharedPreferencesUtils.getInstance().putBoolean(Param.CANTRUCK, data.isState());
                        SharedPreferencesUtils.getInstance().putString(Param.CANTRUCKINFOR, new Gson().toJson(data));
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        SharedPreferencesUtils.getInstance().putBoolean(Param.CANTRUCK, false);

                    }
                });
        ;
    }


    //连接融云服务器
    private void connectRong() {
        LoginBean infor = Utils.getLoginInfor();
        if (infor != null) {
            ChactHelper.getHelper().connect(this, infor.getRongToken(), new RongIMClient.ConnectCallback() {


                @Override
                public void onSuccess(final String s) {
                    HttpAppFactory.queryRongInfor(s)
                            .subscribe(new NetObserver<UserInfor>(null) {
                                @Override
                                public void doOnSuccess(UserInfor data) {

                                    ChactHelper.getHelper().refreshUserInfoCache(s, data);
                                }
                            });
                    ChactHelper.getHelper().setUseInfor(new OnGetUserInforListener() {
                        @Override
                        public Observable<UserInfor> onGetUserInfor(String usrID) {
                            return HttpAppFactory.queryRongInfor(usrID)
                                    .map(new Function<CommonBean<UserInfor>, UserInfor>() {
                                        @Override
                                        public UserInfor apply(CommonBean<UserInfor> userInforCommonBean) throws Exception {
                                            return userInforCommonBean.getData();
                                        }
                                    })
                                    ;
                        }
                    });

                    RongIM.getInstance().setMessageAttachedUserInfo(true);
                    JLog.i("发送消息");
                    BusFactory.getBus().post(new Event.UpChactFragment());
                }

                @Override
                public void onError(RongIMClient.ConnectionErrorCode connectionErrorCode) {

                }

                @Override
                public void onDatabaseOpened(RongIMClient.DatabaseOpenStatus databaseOpenStatus) {

                }


            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        //链接数据
        if (ChactHelper.getHelper().disConnectState()) {
            connectRong();
        }
        BusFactory.getBus().post(new CloseActivityEvent());
        HttpAppFactory.queryPayPassword()
                .subscribe(new NetObserver<QueryPayPassword>(null) {
                    @Override
                    public void doOnSuccess(QueryPayPassword data) {
                        Utils.setPassword(data.isSetPassWord());
                    }
                });


    }


    @Override
    public void onBackPressed() {
        Intent i = new Intent(Intent.ACTION_MAIN);
        i.addCategory(Intent.CATEGORY_HOME);
        startActivity(i);

    }

    /**
     * 检查版本号，确定是否需要更新
     */
    private void checkVersion() {


        HttpOrderFactory.checkVersion()
                .subscribe(new NetObserver<VersionBean>(null) {
                    @Override
                    public void doOnSuccess(VersionBean data) {
                        try {
                            if (data != null && !TextUtils.isEmpty(data.getVersionCode())) {
                                String versionName = DeviceUtils.getVersionName(mContext);
                                String current = versionName.replace(".", "").replace("-debug", "");
                                String needUpdata = data.getVersionCode().replace(".", "");
                                if (Integer.parseInt(current) < Integer.parseInt(needUpdata)) {
                                    showUpAleart(data.getVersionName());
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 显示强制更新接口
     */
    public void showUpAleart(String msg) {
        UpDialog alertDialog = new UpDialog(mContext);
        new CenterAlertBuilder()
                .setRightClickListener(new DialogControl.OnButtonRightClickListener() {
                    @Override
                    public void onRightClick(View view, DialogControl.ICenterDialog dialog) {
                        CommonUtils.launchAppDetail(mContext);
                    }

                })
                .hideBtLeft()
                .hideContent()
                .setCancelable(false)
                .setCanceledOnTouchOutside(false)
                .setDialogTitle(msg)
                .setBtRight("立即更新")
                .creatDialog(alertDialog)
                .show();
    }


    private boolean show;
    @Override
    protected void initListener() {
        super.initListener();
        tab1.setOnClickListener(this);
        tab2.setOnClickListener(this);
        tab3.setOnClickListener(this);
        tab4.setOnClickListener(this);
        tab5.setOnClickListener(this);
        demo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                show=!show;
//                NotificationUtils.getInstance()
//                        .setSound(show?R.raw.notify_sound:0)
//                        .showNotification(mContext,ConvertUtils.getRandom(0,10000),null,null,null);
//
//                ArouterUtils.getInstance().builder(ArouterParamsMatch.activity_match_estimate_order)
//                        .navigation(mContext);
//                startActivity(new Intent(mContext, SplashActivity.class));



            }
        });

    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.tab1:
            case R.id.tab2:
            case R.id.tab4:
            case R.id.tab5:
                changeTabeState(v.getId());
                break;
            case R.id.tab3:
                ClickEventUtils.getInstance().onClick(ClickEventParams.菜单栏_下单);
                ArouterUtils.getInstance().builder(ArouterParamOrder.activity_order_create).navigation(this);
                break;
        }
    }

    private void changeTabeState(int id) {
        tv1.setTextColor(getResources().getColor(id == R.id.tab1 ? R.color.color_tabe_select : R.color.color_tabe_unselect));
        tv2.setTextColor(getResources().getColor(id == R.id.tab2 ? R.color.color_tabe_select : R.color.color_tabe_unselect));
        tv4.setTextColor(getResources().getColor(id == R.id.tab4 ? R.color.color_tabe_select : R.color.color_tabe_unselect));
        tv5.setTextColor(getResources().getColor(id == R.id.tab5 ? R.color.color_tabe_select : R.color.color_tabe_unselect));
        img1.setImageResource(id == R.id.tab1 ? R.mipmap.icon_home_selected_46 : R.mipmap.icon_home_unselected_46);
        img2.setImageResource(id == R.id.tab2 ? R.mipmap.icon_gz_selected_46 : R.mipmap.icon_gz_unselected_46);
        img4.setImageResource(id == R.id.tab4 ? R.mipmap.icon_message_selected_46 : R.mipmap.icon_message_unselected_46);
        img5.setImageResource(id == R.id.tab5 ? R.mipmap.icon_me_selected_46 : R.mipmap.icon_me_unselected_46);


        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (currentFragment != null) {
            fragmentTransaction.hide(currentFragment);
        }
        switch (id) {
            case R.id.tab1:

                ClickEventUtils.getInstance().onClick(ClickEventParams.菜单栏_首页);
                if (homeFragment == null) {

                    homeFragment = (Fragment) ArouterUtils.getInstance().builder(ArouterParamsApp.fragment_home_fragment).navigation(mContext);
                    fragmentTransaction.add(R.id.content, homeFragment);
                } else {
                    fragmentTransaction.show(homeFragment);
                }
                currentFragment = homeFragment;
                break;
            case R.id.tab2:
                ClickEventUtils.getInstance().onClick(ClickEventParams.菜单栏_订单);
                if (orderFragment == null) {
                    orderFragment = (Fragment) ArouterUtils.getInstance().builder(ArouterParamOrder.fragment_order_main).navigation(mContext);
                    fragmentTransaction.add(R.id.content, orderFragment);
                } else {
                    fragmentTransaction.show(orderFragment);
                }
                currentFragment = orderFragment;
                break;

            case R.id.tab4:
                ClickEventUtils.getInstance().onClick(ClickEventParams.菜单栏_聊天);
                if (messageFragment == null) {
                    messageFragment = new ChactListFragment();
                    fragmentTransaction.add(R.id.content, messageFragment);
                } else {
                    fragmentTransaction.show(messageFragment);
                }

                currentFragment = messageFragment;

                break;
            case R.id.tab5:
                ClickEventUtils.getInstance().onClick(ClickEventParams.菜单栏_我);
                if (meFragment == null) {
                    meFragment = (Fragment) ArouterUtils.getInstance().builder(ArouterParamLogin.fragment_login_my).navigation(mContext);
                    fragmentTransaction.add(R.id.content, meFragment);
                } else {
                    fragmentTransaction.show(meFragment);
                }

                currentFragment = meFragment;

                break;

        }

        fragmentTransaction.commitAllowingStateLoss();


    }


    //定位成功，位置信息开始变化
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        //可在其中解析amapLocation获取相应内容。
        if (aMapLocation.getErrorCode() == 0) {//定位成功
            //发送当前的定位数据
            Event.UpLoaction upLoaction = new Event.UpLoaction(aMapLocation.getLatitude(), aMapLocation.getLongitude());
            upLoaction.bearing = aMapLocation.getBearing();
            upLoaction.movingTime = aMapLocation.getTime();
            upLoaction.speed = aMapLocation.getSpeed();
            BusFactory.getBus().postSticky(upLoaction);
        } else {
            //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
        }
    }


    /**
     * 位置信息变化
     *
     * @param count 红包金额
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showRedDialog(String count) {
        if (!TextUtils.isEmpty(count)) {
            RedDialog redDialog = new RedDialog(mContext);
            redDialog.setContent(count);
            redDialog.show();
        }
    }

    /**
     * 位置信息变化
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void upLoaction(Event.UpLoaction event) {
        if (event != null) {
            if (upLoactionUtils != null) {
                upLoactionUtils.add(event.latitude, event.longitude, event.movingTime, event.speed, event.bearing);
            }
        }
    }

    /**
     * 未读信息
     *
     * @param event
     */
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void unReaderMessage(Integer event) {
        if (event != null) {
            String msg = "";
            if (event > 99) {
                msg = "99+";
            } else if (event > 0) {
                msg = event + "";
            }
            tv_unread.setVisibility(TextUtils.isEmpty(msg) ? View.GONE : View.VISIBLE);
            tv_unread.setText(msg);

        }
        BusFactory.getBus().removeStickyEvent(Integer.class);
    }

    private boolean canUp = true;

    //App 进入后台时候
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onInBackgrond(final Event.OnBackground event) {
        if (event != null) {
            if (loaction != null) {
                loaction.showFront(DeviceUtils.isBackGround(mContext));
            }
        }

        ClickEventBean eventParams = ClickEventUtils.getInstance().getEventParams(this);
        if (canUp && eventParams != null && DeviceUtils.isBackGround(mContext)) {
            canUp = false;
            HttpMainFactory.upClickEvent(eventParams)
                    .doFinally(new Action() {
                        @Override
                        public void run() throws Exception {
                            canUp = true;
                        }
                    })
                    .subscribe(new NetObserver<String>(null) {

                        @Override
                        public void doOnSuccess(String data) {
                            JLog.d("clickEvent:  上传完成，清除已上传数据");
                            ClickEventUtils.getInstance().clear();
                        }
                    });
        }
    }

    @Override
    public void onDestroy() {
        if (upLoactionUtils != null) {
            upLoactionUtils.onDestroy();
        }
        loaction.onDestroy();
        super.onDestroy();


    }

    @Override
    public void onResume() {
        super.onResume();
        if (loaction != null) {
            loaction.showFront(false);
        }
    }

    //进入首页时候，根据获取到的数据切换当前角色
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMessageEvent(final RoleTypeBean event) {
        if (event != null) {
            //如果有正在运输中的订单，则此时获取到用户的位置信息
            if (event.getCarId() != null && event.getOrderId() != null) {
                tab1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        UpLoactionEvent upLoactionEvent = new UpLoactionEvent();
                        upLoactionEvent.start = true;
                        upLoactionEvent.orderID = event.getOrderId();
                        upLoactionEvent.cardID = event.getCarId();
                        upLoactionEvent.destinationLatitude = event.getDestinationLatitude();
                        upLoactionEvent.destinationLongitude = event.getDestinationLongitude();
                        BusFactory.getBus().post(upLoactionEvent);
                        float v = MapConverUtils.caculeDis(event.getStartLatitude(), event.getStartLongitude(), event.getDestinationLatitude(), event.getDestinationLongitude());
                        loaction.upInterval(v);
                    }
                }, 200);

            }
        }
    }


    //开始或停止记录用户位置信息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStartLoactionMessage(final UpLoactionEvent event) {
        if (event != null) {
            //如果有正在运输中的订单，则此时获取到用户的位置信息
            if (event.start) {//开始记录数据
                PermissionUtils.applyMap((Activity) mContext, new PermissionUtils.onApplyPermission() {
                    @Override
                    public void hasPermission(List<String> granted, boolean isAll) {
                        if (TextUtils.isEmpty(event.cardID)) {
                            loaction.setInterval(1000);
                        } else {
                            loaction.startLoaction();
                        }
                        //首次创建位置信息收集数据
                        if (upLoactionUtils == null || TextUtils.isEmpty(upLoactionUtils.getCarID())) {
                            if (!DeviceUtils.isOpenGps(mContext)) {
                                showAleart("为了更准确的记录您的轨迹信息，请打开GPS");
                            }
                            upLoactionUtils = new LoactionUpUtils();
                            upLoactionUtils.setOrderInfor(event.orderID, event.cardID, event.destinationLatitude, event.destinationLongitude);
                            //更新位置信息收起器
                        } else if (!upLoactionUtils.getCarID().equals(event.cardID)) {
                            upLoactionUtils.onDestroy();
                            if (!DeviceUtils.isOpenGps(mContext)) {
                                showAleart("为了更准确的记录您的轨迹信息，请打开GPS");
                            }
                            upLoactionUtils = new LoactionUpUtils();
                            upLoactionUtils.setOrderInfor(event.orderID, event.cardID, event.destinationLatitude, event.destinationLongitude);
                        }
                    }

                    @Override
                    public void noPermission(List<String> denied, boolean quick) {
                    }
                });
            } else {
                if (upLoactionUtils != null) {
                    upLoactionUtils.onDestroy();
                }
                if (loaction != null) {
                    loaction.stopLoaction();
                }
                JLog.i("停止定位");

            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onReceiveUMeng(final Event.Umeng event) {
        String token = SharedPreferencesUtils.getInstance().getString(Param.UMENG);
        if (!TextUtils.isEmpty(token)) {
            BusFactory.getBus().removeStickyEvent(event);
            HttpMainFactory.upToken(token).subscribe(new NetObserver<Object>(null) {
                @Override
                public void doOnSuccess(Object data) {
                    JLog.d("友盟token提交成功");
                }
            });
        }
    }


}
