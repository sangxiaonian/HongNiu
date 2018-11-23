package com.hongniu.moduleorder.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.githang.statusbar.StatusBarCompat;
import com.hongniu.baselibrary.arouter.ArouterParamOrder;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.base.BaseFragment;
import com.hongniu.baselibrary.base.NetObserver;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.entity.CloseActivityEvent;
import com.hongniu.baselibrary.entity.QueryPayPassword;
import com.hongniu.baselibrary.entity.RoleTypeBean;
import com.hongniu.baselibrary.event.Event;
import com.hongniu.baselibrary.net.HttpAppFactory;
import com.hongniu.baselibrary.utils.PermissionUtils;
import com.hongniu.baselibrary.utils.Utils;
import com.hongniu.baselibrary.widget.order.OrderDetailItemControl;
import com.hongniu.moduleorder.R;
import com.hongniu.moduleorder.control.OrderEvent;
import com.hongniu.moduleorder.control.OrderMainControl;
import com.hongniu.moduleorder.control.SwitchStateListener;
import com.hongniu.moduleorder.ui.fragment.OrderMainFragmet;
import com.hongniu.moduleorder.utils.LoactionUpUtils;
import com.hongniu.moduleorder.widget.OrderMainTitlePop;
import com.sang.common.event.BusFactory;
import com.sang.common.utils.ConvertUtils;
import com.sang.common.utils.DeviceUtils;
import com.sang.common.utils.JLog;
import com.sang.common.widget.SwitchTextLayout;
import com.sang.common.widget.dialog.CenterAlertDialog;
import com.sang.common.widget.dialog.builder.CenterAlertBuilder;
import com.sang.common.widget.dialog.inter.DialogControl;
import com.sang.common.widget.popu.BasePopu;
import com.sang.common.widget.popu.inter.OnPopuDismissListener;
import com.sang.thirdlibrary.map.LoactionUtils;
import com.sang.thirdlibrary.map.utils.MapConverUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import static com.hongniu.baselibrary.widget.order.OrderDetailItemControl.RoleState.CARGO_OWNER;
import static com.hongniu.baselibrary.widget.order.OrderDetailItemControl.RoleState.CAR_OWNER;
import static com.hongniu.baselibrary.widget.order.OrderDetailItemControl.RoleState.DRIVER;

/**
 * 订单中心主页
 */
//@Route(path = ArouterParamsApp.activity_main)
@Route(path = ArouterParamOrder.fragment_order_main)
public class OrderHomeFragment extends BaseFragment implements OrderMainControl.IOrderMainView, SwitchTextLayout.OnSwitchListener, OrderMainTitlePop.OnOrderMainClickListener, OnPopuDismissListener, View.OnClickListener, AMapLocationListener, OrderMainTitlePop.OnBackClickListener {

    private SwitchTextLayout switchTitle;


    private OrderMainTitlePop titlePop;


    private Fragment cargoFragment, carOwnerFragmeng, driverFragmeng, currentFragmeng;
    private SwitchStateListener switchStateListener;

    private LoactionUtils loaction;

    private LoactionUpUtils upLoactionUtils;//上传位置信息
    private OrderDetailItemControl.RoleState roleState = CARGO_OWNER;
    private Context mContext;


    @Override
    protected View initView(LayoutInflater inflater) {
        View inflate = inflater.inflate(R.layout.fragment_order_main_fragmet, null, false);
        inflate.findViewById(R.id.img_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArouterUtils.getInstance()
                        .builder(ArouterParamOrder.activity_order_search)
                        .withSerializable(Param.TRAN, roleState)
                        .navigation(getContext());

            }
        });

        switchTitle = inflate.findViewById(R.id.switch_title);
        titlePop = new OrderMainTitlePop(getContext());

        return inflate;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        loaction = LoactionUtils.getInstance();
        loaction.init(getContext());
        loaction.setListener(this);
        StatusBarCompat.setStatusBarColor(getActivity(), getResources().getColor(R.color.white), true);

    }


    protected void showAleart(String msg) {
        CenterAlertDialog alertDialog = new CenterAlertDialog(getContext());

        new CenterAlertBuilder()
                .setRightClickListener(new DialogControl.OnButtonRightClickListener() {
                    @Override
                    public void onRightClick(View view, DialogControl.ICenterDialog dialog) {

                        dialog.dismiss();
                    }

                })
                .hideBtLeft()
                .hideContent()
                .setDialogTitle(msg)
                .creatDialog(alertDialog)
                .show();
    }

    @Override
    protected void initData() {
        super.initData();


    }


    @Override
    protected void initListener() {
        super.initListener();
        switchTitle.setListener(this);
        titlePop.setListener(this);
        titlePop.setOnDismissListener(this);
        titlePop.setOnBackClickListener(this);

    }




    @Override
    public void onResume() {
        super.onResume();
        if (loaction != null) {
            loaction.showFront(false);
        }
    }

    @Override
    public void onStop() {
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
                PermissionUtils.applyMap(getActivity(), new PermissionUtils.onApplyPermission() {
                    @Override
                    public void hasPermission(List<String> granted, boolean isAll) {
                        if (TextUtils.isEmpty(event.cardID)) {
                            loaction.setInterval(1000);
                        } else {
                            loaction.startLoaction();
                        }
                        //首次创建位置信息收集数据
                        if (upLoactionUtils == null || TextUtils.isEmpty(upLoactionUtils.getCarID())) {
                            if (!DeviceUtils.isOpenGps(getContext())) {
                                showAleart("为了更准确的记录您的轨迹信息，请打开GPS");
                            }
                            upLoactionUtils = new LoactionUpUtils();
                            upLoactionUtils.setOrderInfor(event.orderID, event.cardID, event.destinationLatitude, event.destinationLongitude);
                            JLog.i("创建位置信息收集器");
                            //更新位置信息收起器
                        } else if (!upLoactionUtils.getCarID().equals(event.cardID)) {
                            upLoactionUtils.onDestroy();
                            if (!DeviceUtils.isOpenGps(getContext())) {
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
                JLog.i("停止定位");

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
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onDestroy() {
        if (upLoactionUtils != null) {
            upLoactionUtils.onDestroy();
        }
        loaction.onDestroy();
        super.onDestroy();


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
        switch (position) {
            case 3:
                this.roleState = CARGO_OWNER;
                break;
            case 1:
                this.roleState = CAR_OWNER;
                break;
            case 2:
                this.roleState = DRIVER;
                break;
        }


        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        if (currentFragmeng != null) {
            fragmentTransaction.hide(currentFragmeng);
        }
        if (position == 1) {
            switchTitle.setTitle("我是车主");
            if (carOwnerFragmeng == null) {
                carOwnerFragmeng =  new OrderMainFragmet();
                Bundle bundle = new Bundle();
                bundle.putSerializable(Param.TRAN, roleState);
                carOwnerFragmeng.setArguments(bundle);
                fragmentTransaction.add(R.id.content, carOwnerFragmeng);
            } else {
                fragmentTransaction.show(carOwnerFragmeng);
            }
            currentFragmeng = carOwnerFragmeng;
        } else if (position == 2) {
            switchTitle.setTitle("我是司机");

            if (driverFragmeng == null) {
                driverFragmeng = new OrderMainFragmet();
                Bundle bundle = new Bundle();
                bundle.putSerializable(Param.TRAN, roleState);
                driverFragmeng.setArguments(bundle);
                fragmentTransaction.add(R.id.content, driverFragmeng);
            } else {
                fragmentTransaction.show(driverFragmeng);
            }
            currentFragmeng = driverFragmeng;
        } else {
            switchTitle.setTitle("我是货主");
            if (cargoFragment == null) {
                cargoFragment = new OrderMainFragmet();
                Bundle bundle = new Bundle();
                bundle.putSerializable(Param.TRAN, roleState);
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
