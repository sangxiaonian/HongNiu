package com.hongniu.moduleorder;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamLogin;
import com.hongniu.baselibrary.arouter.ArouterParamOrder;
import com.hongniu.baselibrary.arouter.ArouterParamsFinance;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.entity.CloseActivityEvent;
import com.hongniu.baselibrary.entity.RoleTypeBean;
import com.hongniu.baselibrary.utils.PermissionUtils;
import com.hongniu.moduleorder.control.OrderMainControl;
import com.hongniu.moduleorder.control.SwitchStateListener;
import com.hongniu.moduleorder.present.OrderMainPresenter;
import com.hongniu.moduleorder.widget.OrderMainTitlePop;
import com.sang.common.event.BusFactory;
import com.sang.common.utils.CommonUtils;
import com.sang.common.utils.DeviceUtils;
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

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * 订单中心主页
 */
@Route(path = ArouterParamOrder.activity_order_main)
public class OrderMainActivity extends BaseActivity implements OrderMainControl.IOrderMainView, SwitchTextLayout.OnSwitchListener, OrderMainTitlePop.OnOrderMainClickListener, OnPopuDismissListener, View.OnClickListener {

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
        PermissionUtils.applyMap(this, new PermissionUtils.onApplyPermission() {
            @Override
            public void hasPermission(List<String> granted, boolean isAll) {
                loaction.startLoaction();
            }

            @Override
            public void noPermission(List<String> denied, boolean quick) {

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


    }


    @Override
    protected void initData() {
        super.initData();

        switchTitle.post(new Runnable() {
            @Override
            public void run() {
                guideTitle.showGuide();
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
    protected boolean getUseEventBus() {

        return true;
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMessageEvent(RoleTypeBean event) {
        if (event != null) {
            changeStaff(event.getRoleId());
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

        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (titlePop.isShow()) {
            titlePop.dismiss();
        } else if (switchStateListener != null && switchStateListener.isShowing()) {
            switchStateListener.closePop();
        } else {
            super.onBackPressed();
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
//            ToastUtils.getInstance().makeToast(ToastUtils.ToastType.NORMAL) .show("财务");
            ArouterUtils.getInstance().builder(ArouterParamsFinance.activity_finance_activity).navigation(mContext);
        } else if (i == R.id.src_me) {
//            ToastUtils.getInstance().makeToast(ToastUtils.ToastType.NORMAL) .show("个人中心");
            drawerLayout.openDrawer(Gravity.START);
        } else if (i == R.id.ll_order) {
//            ToastUtils.getInstance().makeToast(ToastUtils.ToastType.NORMAL) .show("我要下单");
            ArouterUtils.getInstance().builder(ArouterParamOrder.activity_order_create).navigation(mContext);

        } else if (i == R.id.ll_login_out) {
//            ToastUtils.showTextToast("退出登录");
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
//            ToastUtils.showTextToast("联系客服");
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
                            CommonUtils.toDial(mContext, getString(R.string.hongniu_phone));

                        }
                    }).creatDialog(new CenterAlertDialog(mContext)).show();

        } else if (i == R.id.ll_about_us) {
            drawerLayout.closeDrawer(Gravity.START);
//            ToastUtils.getInstance().makeToast(ToastUtils.ToastType.NORMAL) .show("关于我们");

            ArouterUtils.getInstance().builder(ArouterParamLogin.activity_about_us).navigation(mContext);
        } else if (i == R.id.ll_my_car) {
            drawerLayout.closeDrawer(Gravity.START);
//            ToastUtils.showTextToast("我的车辆");
            ArouterUtils.getInstance().builder(ArouterParamLogin.activity_car_list).navigation(mContext);
        } else if (i == R.id.ll_person_infor) {
            drawerLayout.closeDrawer(Gravity.START);
//            ToastUtils.getInstance().makeToast(ToastUtils.ToastType.NORMAL) .show("个人资料");
            ArouterUtils.getInstance().builder(ArouterParamLogin.activity_person_infor).navigation(mContext);

        } else if (i == R.id.ll_pay_method) {
            drawerLayout.closeDrawer(Gravity.START);
//            ToastUtils.getInstance().makeToast(ToastUtils.ToastType.NORMAL) .show("收款方式");
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
}
