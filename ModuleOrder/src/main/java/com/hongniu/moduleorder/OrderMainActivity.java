package com.hongniu.moduleorder;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.hongniu.baselibrary.arouter.ArouterParamLogin;
import com.hongniu.baselibrary.arouter.ArouterParamOrder;
import com.hongniu.baselibrary.arouter.ArouterParamsFinance;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.baselibrary.base.NetObserver;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.entity.CloseActivityEvent;
import com.hongniu.baselibrary.entity.RoleTypeBean;
import com.hongniu.baselibrary.event.Event;
import com.hongniu.baselibrary.net.HttpAppFactory;
import com.hongniu.baselibrary.utils.PermissionUtils;
import com.hongniu.baselibrary.utils.Utils;
import com.hongniu.baselibrary.widget.dialog.UpDialog;
import com.hongniu.moduleorder.control.OrderEvent;
import com.hongniu.moduleorder.control.OrderMainControl;
import com.hongniu.moduleorder.control.SwitchStateListener;
import com.hongniu.moduleorder.entity.VersionBean;
import com.hongniu.moduleorder.net.HttpOrderFactory;
import com.hongniu.moduleorder.present.OrderMainPresenter;
import com.hongniu.moduleorder.utils.LoactionUpUtils;
import com.hongniu.moduleorder.widget.OrderMainTitlePop;
import com.sang.common.event.BusFactory;
import com.sang.common.utils.CommonUtils;
import com.sang.common.utils.ConvertUtils;
import com.sang.common.utils.DeviceUtils;
import com.sang.common.utils.JLog;
import com.sang.common.widget.SwitchTextLayout;
import com.sang.common.widget.dialog.BottomAlertDialog;
import com.sang.common.widget.dialog.CenterAlertDialog;
import com.sang.common.widget.dialog.builder.BottomAlertBuilder;
import com.sang.common.widget.dialog.builder.CenterAlertBuilder;
import com.sang.common.widget.dialog.inter.DialogControl;
import com.sang.common.widget.guideview.BaseGuide;
import com.sang.common.widget.popu.BasePopu;
import com.sang.common.widget.popu.inter.OnPopuDismissListener;
import com.sang.thirdlibrary.map.LoactionUtils;
import com.sang.thirdlibrary.map.utils.MapConverUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * 订单中心主页
 */
@Route(path = ArouterParamOrder.activity_order_main)
public class OrderMainActivity extends BaseActivity implements OrderMainControl.IOrderMainView, SwitchTextLayout.OnSwitchListener, OrderMainTitlePop.OnOrderMainClickListener, OnPopuDismissListener, View.OnClickListener, AMapLocationListener {

    private SwitchTextLayout switchTitle;

    private DrawerLayout drawerLayout;

    private OrderMainTitlePop titlePop;

    private LinearLayout llOrder;//我要下单
    private LinearLayout llLoginOut;//退出登录
    private LinearLayout llContactService;//联系客服
    private LinearLayout llAboutUs;//关于我们
    private LinearLayout llMyCar;//我的车辆
    private LinearLayout llPersonInfor;//个人资料
    private LinearLayout llPayMethod;//收款方式

    private ImageView srcFinance;
    private ImageView srcPersonCenter;

    private Fragment cargoFragment, carOwnerFragmeng, driverFragmeng, currentFragmeng;
    private SwitchStateListener switchStateListener;
    private BaseGuide guideTitle;

    private OrderMainControl.IOrderMainPresent present;
    private LoactionUtils loaction;

    private TextView tvName, tvPhone;
    private LoactionUpUtils upLoactionUtils;//上传位置信息
    private int position = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_main);
        setToolbarTitle("");

        present = new OrderMainPresenter(this);
        initView();
        initData();
        initListener();
        loaction = LoactionUtils.getInstance();
        loaction.init(this);
        loaction.setListener(this);

        HttpAppFactory.getRoleType()

                .subscribe(new NetObserver<RoleTypeBean>(null) {

                    @Override
                    public void doOnSuccess(RoleTypeBean data) {
                        EventBus.getDefault().postSticky(data);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        EventBus.getDefault().postSticky(new RoleTypeBean());
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }
                });


    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
//        super.onSaveInstanceState(outState, outPersistentState);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String extra = intent.getStringExtra(Param.TRAN);
        if (extra != null && extra.equals(Param.LOGIN_OUT)) {
            ArouterUtils.getInstance().builder(ArouterParamLogin.activity_login).navigation(mContext);
            finish();
        }
    }

    @Override
    protected void initView() {
        super.initView();
        titlePop = new OrderMainTitlePop(this);
        switchTitle = findViewById(R.id.switch_title);
        drawerLayout = findViewById(R.id.drawer);
        srcFinance = findViewById(R.id.src_finance);
        srcPersonCenter = findViewById(R.id.src_me);
        llOrder = findViewById(R.id.ll_order);
        llLoginOut = findViewById(R.id.ll_login_out);
        llContactService = findViewById(R.id.ll_contact_service);
        llAboutUs = findViewById(R.id.ll_about_us);
        llMyCar = findViewById(R.id.ll_my_car);
        llPersonInfor = findViewById(R.id.ll_person_infor);
        llPayMethod = findViewById(R.id.ll_pay_method);

        tvName = findViewById(R.id.tv_name);
        tvPhone = findViewById(R.id.tv_phone);

        guideTitle = new BaseGuide();
        guideTitle.setMsg("这里可以切换角色哦")
                .setView(switchTitle)
                .setHighTargetGraphStyle(0)
                .setActivity(this)
                .setShowTop(false)
                .setSharedPreferencesKey(Param.ORDER_MAIN_TITLE_GUIDE)
        ;
        BaseGuide guideFinance = new BaseGuide();
        guideFinance.setMsg("支出&收入，一目了然")
                .setView(srcFinance)
                .setHighTargetGraphStyle(1)
                .setActivity(this)
                .setShowTop(true)
                .setSharedPreferencesKey(Param.ORDER_MAIN_FINANCE_GUIDE)
        ;
        guideTitle.setNextGuide(guideFinance);

        findViewById(R.id.img_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArouterUtils.getInstance()
                        .builder(ArouterParamOrder.activity_order_search)
                        .withInt(Param.TRAN, position)
                        .navigation(mContext);

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

    @Override
    protected void showAleart(String msg) {
        super.showAleart(msg);
    }

    @Override
    protected void initData() {
        super.initData();
        if (Utils.checkInfor()) {
            tvName.setText(Utils.getPersonInfor().getContact() == null ? "待完善" : Utils.getPersonInfor().getContact());
        }
        tvPhone.setText(Utils.getLoginInfor().getMobile() == null ? "" : Utils.getLoginInfor().getMobile());
        switchTitle.post(new Runnable() {
            @Override
            public void run() {
                guideTitle.showGuide();
            }
        });
        checkVersion();
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
                                String current = versionName.replace(".","").replace("-debug","");
                                String needUpdata = data.getVersionCode().replace(".","");
                                 if (Integer.parseInt(current)<Integer.parseInt(needUpdata)){
                                     showUpAleart(data.getVersionName());
                                 }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    protected void initListener() {
        super.initListener();
        switchTitle.setListener(this);
        srcFinance.setOnClickListener(this);
        srcPersonCenter.setOnClickListener(this);
        llOrder.setClickable(true);
        llLoginOut.setClickable(true);
        llContactService.setClickable(true);
        llAboutUs.setClickable(true);
        llMyCar.setClickable(true);
        llPersonInfor.setClickable(true);
        llPayMethod.setClickable(true);


        llOrder.setOnClickListener(this);
        llLoginOut.setOnClickListener(this);
        llContactService.setOnClickListener(this);
        llAboutUs.setOnClickListener(this);
        llMyCar.setOnClickListener(this);
        llPersonInfor.setOnClickListener(this);
        llPayMethod.setOnClickListener(this);

        titlePop.setListener(this);
        titlePop.setOnDismissListener(this);


    }


    @Override
    protected void onStart() {
        super.onStart();
        BusFactory.getBus().post(new CloseActivityEvent());

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (loaction != null) {
            loaction.showFront(false);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    @Override
    protected boolean getUseEventBus() {
        return true;
    }


    //进入首页时候，根据获取到的数据切换当前角色
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMessageEvent(final RoleTypeBean event) {
        if (event != null) {
            changeStaff(event.getRoleId());//此处接收到用户类型
            //如果有正在运输中的订单，则此时获取到用户的位置信息
            if (event.getCarId() != null && event.getOrderId() != null) {
                switchTitle.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        OrderEvent.UpLoactionEvent upLoactionEvent = new OrderEvent.UpLoactionEvent();
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
    public void onStartLoactionMessage(final OrderEvent.UpLoactionEvent event) {
        if (event != null) {
            //如果有正在运输中的订单，则此时获取到用户的位置信息
            if (event.start) {//开始记录数据
                PermissionUtils.applyMap(this, new PermissionUtils.onApplyPermission() {
                    @Override
                    public void hasPermission(List<String> granted, boolean isAll) {
                        if (TextUtils.isEmpty(event.cardID)) {
                            loaction.setInterval(1000);
                        } else {
                            loaction.startLoaction();
                        }
                        //首次创建位置信息收集数据
                        if (upLoactionUtils == null) {
                            if (!DeviceUtils.isOpenGps(mContext)) {
                                showAleart("为了更准确的记录您的轨迹信息，请打开GPS");
                            }
                            upLoactionUtils = new LoactionUpUtils();
                            upLoactionUtils.setOrderInfor(event.orderID, event.cardID, event.destinationLatitude, event.destinationLongitude);
                            JLog.i("创建位置信息收集器");
                            //更新位置信息收起器
                        } else if (!upLoactionUtils.getCarID().equals(event.cardID)) {
                            upLoactionUtils.onDestroy();
                            if (!DeviceUtils.isOpenGps(mContext)) {
                                showAleart("为了更准确的记录您的轨迹信息，请打开GPS");
                            }
                            upLoactionUtils = new LoactionUpUtils();
                            upLoactionUtils.setOrderInfor(event.orderID, event.cardID, event.destinationLatitude, event.destinationLongitude);
                            JLog.i("更新位置信息收集器");
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

            }
        }
    }


    //App 进入后台时候
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onInBackgrond(final Event.OnBackground event) {
        if (event != null) {
            if (loaction != null) {
                loaction.showFront(DeviceUtils.isBackGround(mContext));
            }
        }
    }


    @Override
    protected void onDestroy() {
        if (upLoactionUtils != null) {
            upLoactionUtils.onDestroy();
        }
        loaction.onDestroy();
        super.onDestroy();


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpPersonInfor(Event.UpPerson event) {
        if (event != null) {
            if (Utils.checkInfor()) {
                tvName.setText(Utils.getPersonInfor().getContact());
            }
        }
    }


    /**
     * 改变角色信息
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onChangeRole(Event.UpRoale event) {
        if (event != null && event.roleState != null) {
            switch (event.roleState) {
                case CARGO_OWNER:
                    changeStaff(3);
                    break;
                case CAR_OWNER:
                    changeStaff(1);
                    break;
                case DRIVER:
                    changeStaff(2);
                    break;
            }

        }
    }

    /**
     * 顶部角色类型被选中点击的时候
     *
     * @param popu
     * @param position
     */
    @Override
    public void onMainClick(OrderMainTitlePop popu, int position) {
        popu.dismiss();
        changeStaff(position);
    }

    /**
     * 切换用户角色
     *
     * @param position
     */
    private void changeStaff(int position) {
        this.position = position;
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (currentFragmeng != null) {
            fragmentTransaction.hide(currentFragmeng);
        }
        if (position == 1) {
            switchTitle.setTitle("我是车主");
            if (carOwnerFragmeng == null) {
                carOwnerFragmeng = (Fragment) ArouterUtils.getInstance().builder(ArouterParamOrder.fragment_order_main).navigation(this);
                Bundle bundle = new Bundle();
                bundle.putInt(Param.TRAN, 1);
                carOwnerFragmeng.setArguments(bundle);
                fragmentTransaction.add(R.id.content, carOwnerFragmeng);
            } else {
                fragmentTransaction.show(carOwnerFragmeng);
            }
            currentFragmeng = carOwnerFragmeng;
        } else if (position == 2) {
            switchTitle.setTitle("我是司机");

            if (driverFragmeng == null) {
                driverFragmeng = (Fragment) ArouterUtils.getInstance().builder(ArouterParamOrder.fragment_order_main).navigation(this);
                Bundle bundle = new Bundle();
                bundle.putInt(Param.TRAN, 2);
                driverFragmeng.setArguments(bundle);
                fragmentTransaction.add(R.id.content, driverFragmeng);
            } else {
                fragmentTransaction.show(driverFragmeng);
            }
            currentFragmeng = driverFragmeng;
        } else {
            switchTitle.setTitle("我是货主");
            if (cargoFragment == null) {
                cargoFragment = (Fragment) ArouterUtils.getInstance().builder(ArouterParamOrder.fragment_order_main).navigation(this);
                Bundle bundle = new Bundle();
                bundle.putInt(Param.TRAN, 0);
                cargoFragment.setArguments(bundle);
                fragmentTransaction.add(R.id.content, cargoFragment);
            } else {
                fragmentTransaction.show(cargoFragment);
            }
            currentFragmeng = cargoFragment;
        }

        if (currentFragmeng != null && currentFragmeng instanceof SwitchStateListener) {
            switchStateListener = (SwitchStateListener) currentFragmeng;
        }

        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public void onBackPressed() {
        if (titlePop.isShow()) {
            titlePop.dismiss();
        } else if (switchStateListener != null && switchStateListener.isShowing()) {
            switchStateListener.closePop();
        } else {
            Intent i = new Intent(Intent.ACTION_MAIN);
            i.addCategory(Intent.CATEGORY_HOME);
            startActivity(i);
        }
    }

    @Override
    protected boolean reciveClose() {
        return false;
    }

    /**
     * Popu dimiss 监听
     *
     * @param popu   当前popu
     * @param target 目标View
     */
    @Override
    public void onPopuDismsss(BasePopu popu, View target) {
        switchTitle.closeSwitch();
    }


    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.src_finance) {

//            ArouterUtils.getInstance().builder(ArouterParamsFinance.activity_finance_activity).navigation(mContext);
            ArouterUtils.getInstance().builder(ArouterParamsFinance.activity_finance_wallet).navigation(mContext);
        } else if (i == R.id.src_me) {
            drawerLayout.openDrawer(Gravity.START);
        } else if (i == R.id.ll_order) {
            ArouterUtils.getInstance().builder(ArouterParamOrder.activity_order_create).navigation(mContext);
        } else if (i == R.id.ll_login_out) {
            drawerLayout.closeDrawer(Gravity.START);
            new BottomAlertBuilder()
                    .setDialogTitle(getString(R.string.login_out_entry))
                    .setTopClickListener(new DialogControl.OnButtonTopClickListener() {
                        @Override
                        public void onTopClick(View view, DialogControl.IBottomDialog dialog) {
                            dialog.dismiss();
                            ArouterUtils.getInstance().builder(ArouterParamLogin.activity_login).navigation(mContext);
                            finish();
                        }

                    })
                    .setBottomClickListener(new DialogControl.OnButtonBottomClickListener() {
                        @Override
                        public void onBottomClick(View view, DialogControl.IBottomDialog dialog) {
                            dialog.dismiss();

                        }

                    }).creatDialog(new BottomAlertDialog(mContext)).show();


        } else if (i == R.id.ll_contact_service) {
            drawerLayout.closeDrawer(Gravity.START);
            new CenterAlertBuilder()
                    .setDialogTitleSize(18)
                    .setDialogContentSize(15)
                    .setbtSize(18)
                    .setDialogSize(DeviceUtils.dip2px(mContext, 300), DeviceUtils.dip2px(mContext, 135))
                    .setDialogTitle(getString(R.string.login_contact_service))
                    .setDialogContent(getString(R.string.login_contact_phone))
                    .setLeftClickListener(new DialogControl.OnButtonLeftClickListener() {
                        @Override
                        public void onLeftClick(View view, DialogControl.ICenterDialog dialog) {
                            dialog.dismiss();

                        }


                    })
                    .setRightClickListener(new DialogControl.OnButtonRightClickListener() {
                        @Override
                        public void onRightClick(View view, DialogControl.ICenterDialog dialog) {
                            dialog.dismiss();
                            CommonUtils.toDial(mContext, getString(R.string.login_contact_phone));

                        }
                    }).creatDialog(new CenterAlertDialog(mContext)).show();

        } else if (i == R.id.ll_about_us) {
            drawerLayout.closeDrawer(Gravity.START);

            ArouterUtils.getInstance().builder(ArouterParamLogin.activity_about_us).navigation(mContext);
        } else if (i == R.id.ll_my_car) {
            drawerLayout.closeDrawer(Gravity.START);
            ArouterUtils.getInstance().builder(ArouterParamLogin.activity_car_list).navigation(mContext);
        } else if (i == R.id.ll_person_infor) {
            drawerLayout.closeDrawer(Gravity.START);
            ArouterUtils.getInstance().builder(ArouterParamLogin.activity_person_infor).navigation(mContext);

        } else if (i == R.id.ll_pay_method) {
            drawerLayout.closeDrawer(Gravity.START);
            ArouterUtils.getInstance().builder(ArouterParamLogin.activity_pay_ways).navigation(mContext);

        }
    }

    @Override
    public void onOpen(SwitchTextLayout switchTextLayout, View view) {
        if (switchStateListener != null) {
            switchStateListener.closePop();
        }
        titlePop.show(view);

    }

    @Override
    public void onClose(SwitchTextLayout switchTextLayout, View view) {
        titlePop.dismiss();
    }


    //定位成功，位置信息开始变化
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        //可在其中解析amapLocation获取相应内容。
        if (aMapLocation.getErrorCode() == 0) {//定位成功
            JLog.v("测试后台打点：" + DeviceUtils.isOpenGps(mContext)
                    + "\n Latitude：" + aMapLocation.getLatitude()
                    + "\n Longitude：" + aMapLocation.getLongitude()
                    + "\n" + ConvertUtils.formatTime(aMapLocation.getTime(), "yyyy-MM-dd HH:mm:ss")
            );
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


}
