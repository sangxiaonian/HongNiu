package com.hongniu.freight.ui;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.fy.androidlibrary.event.BusFactory;
import com.fy.androidlibrary.toast.ToastUtils;
import com.fy.androidlibrary.utils.DeviceUtils;
import com.fy.androidlibrary.utils.JLog;
import com.fy.androidlibrary.utils.SharedPreferencesUtils;
import com.fy.androidlibrary.utils.permission.PermissionUtils;
import com.fy.companylibrary.config.ArouterParamApp;
import com.fy.companylibrary.config.Param;
import com.fy.companylibrary.entity.CommonBean;
import com.fy.companylibrary.net.NetObserver;
import com.fy.companylibrary.ui.CompanyBaseActivity;
import com.fy.companylibrary.ui.CompanyBaseFragment;
import com.hdgq.locationlib.listener.OnResultListener;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.freight.BuildConfig;
import com.hongniu.freight.R;
import com.hongniu.freight.entity.Event;
import com.hongniu.freight.entity.PersonInfor;
import com.hongniu.freight.entity.UmenToken;
import com.hongniu.freight.huoyun.FreightClient;
import com.hongniu.freight.net.HttpAppFactory;
import com.hongniu.freight.ui.fragment.ChactListFragment;
import com.hongniu.freight.ui.fragment.HomeFragment;
import com.hongniu.freight.utils.InfoUtils;
import com.hongniu.freight.utils.LoactionUpUtils;
import com.hongniu.freight.utils.Utils;
import com.hongniu.freight.widget.DialogComment;
import com.sang.thirdlibrary.chact.ChactHelper;
import com.sang.thirdlibrary.chact.UserInfor;
import com.sang.thirdlibrary.chact.control.ChactControl;
import com.sang.thirdlibrary.chact.control.OnGetUserInforListener;
import com.sang.thirdlibrary.map.LoactionUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

@Route(path = ArouterParamApp.activity_main)
public class MainActivity extends CompanyBaseActivity implements View.OnClickListener,   AMapLocationListener, ChactControl.OnReceiveUnReadCountListener {

    private TextView demo;


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


    CompanyBaseFragment homeFragment, orderFragment, messageFragment, meFragment, currentFragment;
    private LoactionUtils loaction;

    private boolean isLogin;
    private DialogComment dialogAttes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setWhitToolBar("");
        isLogin = getIntent().getBooleanExtra(Param.TRAN, false);
        initView();
        initData();
        initListener();
        tab1.performClick();
        loaction = LoactionUtils.getInstance();
        loaction.init(this);
        loaction.setListener(this);

        //初始化网络获取数据
        FreightClient.getClient().initSdk(MainActivity.this, getPackageName(),
                getString(R.string.freight_secret),
                getString(R.string.freight_uniquie),
                true, new OnResultListener() {
                    @Override
                    public void onSuccess() {
                        JLog.i("初始化成功");
                    }

                    @Override
                    public void onFailure(String s, String s1) {
                        JLog.e("初始化失败：" + s + "  " + s1);

                    }
                });

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
        dialogAttes = Utils.dialogAttes(mContext, new DialogComment.OnButtonRightClickListener() {
            @Override
            public void onRightClick(View view, Dialog dialog) {
                dialog.dismiss();
                PersonInfor personInfo = InfoUtils.getMyInfo();
                if (personInfo == null) {
                    return;
                }
                Utils.jump2Attestation(mContext, personInfo);

            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        tv_unread.setVisibility(View.GONE);
        demo.setVisibility(BuildConfig.DEBUG ? View.VISIBLE : View.GONE);
    }

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

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (ChactHelper.getHelper().disConnectState()) {
            String rongToken = InfoUtils.getLoginInfo().getRongToken();
            ChactHelper.getHelper().connect(mContext, rongToken, new RongIMClient.ConnectCallback() {
                @Override
                public void onSuccess(final String userID) {
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
                    HttpAppFactory.queryRongInfor(userID)
                            .subscribe(new NetObserver<UserInfor>(null) {
                                @Override
                                public void doOnSuccess(UserInfor data) {

                                    ChactHelper.getHelper().refreshUserInfoCache(userID, data);
                                }
                            });

                    RongIM.getInstance().setMessageAttachedUserInfo(true);
                    BusFactory.getBus().post(new Event.UpChactFragment());
                }

                @Override
                public void onError(RongIMClient.ConnectionErrorCode connectionErrorCode) {
                    if (connectionErrorCode.getValue() == 31010) {//不是异地登录
                        ToastUtils.getInstance().show("异地登录");
                    }
                }

                @Override
                public void onDatabaseOpened(RongIMClient.DatabaseOpenStatus databaseOpenStatus) {

                }
            });
            ChactHelper.getHelper().setUnReadCountListener(this);
        }
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tab1 || id == R.id.tab2 || id == R.id.tab4 || id == R.id.tab5) {
            changeTabeState(v.getId());
        } else if (id == R.id.tab3) {
            if (InfoUtils.getState(InfoUtils.getMyInfo()) == 4) {
                ArouterUtils.getInstance().builder(ArouterParamApp.activity_order_create)
                        .navigation();
            } else if (InfoUtils.isShowAlert()) {
                dialogAttes.show();
            } else {
                ToastUtils.getInstance().makeToast(ToastUtils.ToastType.CENTER)
                        .show("身份认证审核中");
            }
        }
    }

    private int lastID;

    private void changeTabeState(int id) {
        if (lastID == id) {
            return;
        }
        lastID = id;
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
        if (id == R.id.tab1) {
            if (homeFragment == null) {
                homeFragment = new HomeFragment();
                Bundle bundle = new Bundle();
                bundle.putBoolean(Param.TRAN, isLogin);
                homeFragment.setBundle(bundle);
                fragmentTransaction.add(R.id.content, homeFragment);
            } else {
                fragmentTransaction.show(homeFragment);
            }
            currentFragment = homeFragment;
        } else if (id == R.id.tab2) {
            if (orderFragment == null) {
                orderFragment = (CompanyBaseFragment) ArouterUtils.getInstance().builder(ArouterParamApp.fragment_order_home).navigation(mContext);
                fragmentTransaction.add(R.id.content, orderFragment);
            } else {
                fragmentTransaction.show(orderFragment);
            }
            currentFragment = orderFragment;
        } else if (id == R.id.tab4) {
            if (messageFragment == null) {
                messageFragment = new ChactListFragment();
                fragmentTransaction.add(R.id.content, messageFragment);
            } else {
                fragmentTransaction.show(messageFragment);
            }

            currentFragment = messageFragment;
        } else if (id == R.id.tab5) {
            if (meFragment == null) {
                meFragment = (CompanyBaseFragment) ArouterUtils.getInstance().builder(ArouterParamApp.fragment_person_center).navigation(mContext);
                fragmentTransaction.add(R.id.content, meFragment);
            } else {
                fragmentTransaction.show(meFragment);
            }

            currentFragment = meFragment;
        }

        fragmentTransaction.commitAllowingStateLoss();


    }






    @Override
    public void onBackPressed() {
        Intent i = new Intent(Intent.ACTION_MAIN);
        i.addCategory(Intent.CATEGORY_HOME);
        startActivity(i);
    }


    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        JLog.v("定位成功");
        //地理位置发生改变
        if (upLoactionUtils != null) {
            upLoactionUtils.add(aMapLocation.getLatitude(), aMapLocation.getLongitude(), aMapLocation.getTime(), aMapLocation.getSpeed(), aMapLocation.getBearing());
        }
    }

    @Override
    protected boolean isUseEventBus() {
        return true;
    }

    private LoactionUpUtils upLoactionUtils;//上传位置信息

    //开始或停止记录用户位置信息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStartLoactionMessage(final Event.UpLoactionEvent event) {
        if (event != null) {
            //如果有正在运输中的订单，则此时获取到用户的位置信息
            if (event.start) {//开始记录数据
                PermissionUtils.applyMap(this, new PermissionUtils.onApplyPermission() {
                    @Override
                    public void hasPermission() {
                        if (TextUtils.isEmpty(event.cardID)) {
                            loaction.setInterval(1000);
                        } else {
                            loaction.startLoaction();
                        }
                        //首次创建位置信息收集数据
                        if (upLoactionUtils == null || TextUtils.isEmpty(upLoactionUtils.getCarID())) {
                            if (!DeviceUtils.isOpenGps(mContext)) {
                                ToastUtils.getInstance().makeToast(ToastUtils.ToastType.CENTER).show("为了更准确的记录您的轨迹信息，请打开GPS");
                            }
                            upLoactionUtils = new LoactionUpUtils();
                            upLoactionUtils.setOrderInfor(event.orderID, event.cardID, event.destinationLatitude, event.destinationLongitude);
                            //更新位置信息收起器
                        } else if (!upLoactionUtils.getCarID().equals(event.cardID)) {
                            upLoactionUtils.onDestroy();
                            if (!DeviceUtils.isOpenGps(mContext)) {
                                ToastUtils.getInstance().makeToast(ToastUtils.ToastType.CENTER).show("为了更准确的记录您的轨迹信息，请打开GPS");
                            }
                            upLoactionUtils = new LoactionUpUtils();
                            upLoactionUtils.setOrderInfor(event.orderID, event.cardID, event.destinationLatitude, event.destinationLongitude);
                        }
                        JLog.i("开始发车");
                    }

                    @Override
                    public void noPermission() {

                    }
                });

            } else {
                if (upLoactionUtils != null) {
                    upLoactionUtils.onDestroy();
                }
                if (loaction != null) {
                    loaction.stopLoaction();
                }

            }
        }
    }

    /**
     * 有未读消息时候
     *
     * @param count 未读消息
     */
    @Override
    public void onReceiveUnRead(int count) {
        String msg = "";
        if (count > 99) {
            msg = "99+";
        } else if (count > 0) {
            msg = count + "";
        }
        tv_unread.setVisibility(TextUtils.isEmpty(msg) ? View.GONE : View.VISIBLE);
        tv_unread.setText(msg);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onCityChangeEvent(UmenToken umenToken) {
        if (umenToken != null) {
            String token = SharedPreferencesUtils.getInstance().getString(Param.UMENGTOKEN);
            JLog.e("提交友盟token:" + token);
            HttpAppFactory.upDateToken(token)
                    .subscribe(new NetObserver<Object>(null) {
                        @Override
                        public void doOnSuccess(Object o) {
                            super.doOnSuccess(o);
                            JLog.d("上传友盟token成功");
                        }
                    });
            BusFactory.getBus().removeStickyEvent(umenToken);
        }
    }
}
