package com.hongniu.moduleorder.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Poi;
import com.amap.api.navi.AmapNaviPage;
import com.amap.api.navi.AmapNaviParams;
import com.amap.api.navi.AmapNaviType;
import com.google.gson.Gson;
import com.hongniu.baselibrary.arouter.ArouterParamOrder;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.base.NetObserver;
import com.hongniu.baselibrary.base.RefrushFragmet;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.entity.OrderCreatBean;
import com.hongniu.baselibrary.entity.OrderDetailBean;
import com.hongniu.baselibrary.entity.PageBean;
import com.hongniu.baselibrary.entity.RoleTypeBean;
import com.hongniu.baselibrary.event.Event;
import com.hongniu.baselibrary.utils.PermissionUtils;
import com.hongniu.baselibrary.widget.order.OrderDetailItem;
import com.hongniu.baselibrary.widget.order.OrderDetailItemControl;
import com.hongniu.moduleorder.R;
import com.hongniu.moduleorder.control.OrderEvent;
import com.hongniu.moduleorder.control.SwitchStateListener;
import com.hongniu.moduleorder.entity.OrderMainQueryBean;
import com.hongniu.moduleorder.net.HttpOrderFactory;
import com.hongniu.moduleorder.utils.LoactionCollectionUtils;
import com.hongniu.moduleorder.utils.OrderUtils;
import com.hongniu.moduleorder.widget.OrderMainPop;
import com.sang.common.event.BusFactory;
import com.sang.common.recycleview.adapter.XAdapter;
import com.sang.common.recycleview.holder.BaseHolder;
import com.sang.common.recycleview.holder.PeakHolder;
import com.sang.common.utils.DeviceUtils;
import com.sang.common.utils.JLog;
import com.sang.common.utils.ToastUtils;
import com.sang.common.widget.SwitchTextLayout;
import com.sang.common.widget.dialog.CenterAlertDialog;
import com.sang.common.widget.dialog.builder.CenterAlertBuilder;
import com.sang.common.widget.dialog.inter.DialogControl;
import com.sang.common.widget.popu.BasePopu;
import com.sang.common.widget.popu.inter.OnPopuDismissListener;
import com.sang.thirdlibrary.map.utils.MapConverUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;

/**
 * 订单列表Fragment
 */
@Route(path = ArouterParamOrder.fragment_order_main)
public class OrderMainFragmet extends RefrushFragmet<OrderDetailBean> implements SwitchStateListener, SwitchTextLayout.OnSwitchListener, OrderMainPop.OnPopuClickListener, OnPopuDismissListener, OrderDetailItemControl.OnOrderDetailBtClickListener {
    private SwitchTextLayout switchLeft;
    private SwitchTextLayout switchRight;
    private int leftSelection;
    private int rightSelection;
    private OrderMainPop<String> orderMainPop;
    private List<String> times;
    private List<String> states;

    private OrderDetailItemControl.RoleState roleState;//角色
    private OrderMainQueryBean queryBean = new OrderMainQueryBean();
    private LatLng latLng = new LatLng(0, 0);

    public OrderMainFragmet() {
    }

    @Override
    public void setArguments(@Nullable Bundle args) {
        super.setArguments(args);
        int anInt = args.getInt(Param.TRAN);
        switch (anInt) {
            case 0:
                roleState = OrderDetailItemControl.RoleState.CARGO_OWNER;//货主
                queryBean.setUserType(3);
                break;
            case 1:
                roleState = OrderDetailItemControl.RoleState.CAR_OWNER;
                queryBean.setUserType(1);
                break;
            case 2:
                roleState = OrderDetailItemControl.RoleState.DRIVER;
                queryBean.setUserType(2);
                break;
        }


    }

    @Override
    protected View initView(LayoutInflater inflater) {
        View inflate = inflater.inflate(R.layout.fragment_order_main_fragmet, null);
        switchLeft = inflate.findViewById(R.id.switch_left);
        switchRight = inflate.findViewById(R.id.switch_right);


        return inflate;
    }

    @Override
    protected void initData() {
        super.initData();
        orderMainPop = new OrderMainPop<>(getContext());
        String[] stringArray = getResources().getStringArray(R.array.order_main_state);
        states = new ArrayList<>();
        for (String s : stringArray) {
            states.add(s);
        }

        if (roleState!= OrderDetailItemControl.RoleState.CARGO_OWNER){
            states.remove(1);
        }

        times = Arrays.asList(getResources().getStringArray(R.array.order_main_time));
        View view = new View(getContext());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DeviceUtils.dip2px(getContext(), 75));
        view.setLayoutParams(params);
        adapter.addFoot(new PeakHolder(view));
    }


    @Override
    protected boolean getUseEventBus() {
        return true;
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onLocationChange(final Event.UpLoaction event) {
        if (event != null) {
            latLng = new LatLng(event.latitude, event.longitude);
        }
    }


    @Override
    public XAdapter<OrderDetailBean> getAdapter(List<OrderDetailBean> datas) {
        return new XAdapter<OrderDetailBean>(getContext(), datas) {
            @Override
            public BaseHolder<OrderDetailBean> initHolder(ViewGroup parent, int viewType) {
                return new BaseHolder<OrderDetailBean>(getContext(), parent, R.layout.order_main_item) {
                    @Override
                    public void initView(View itemView, int position, final OrderDetailBean data) {
                        super.initView(itemView, position, data);
                        OrderDetailItem item = itemView.findViewById(R.id.order_detail);//身份角色
                        item.setIdentity(roleState);
                        item.setInfor(data);
                        item.setOnButtonClickListener(OrderMainFragmet.this);

                        itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                onCheckPath(data);
                            }
                        });
                    }
                };
            }
        };
    }

    @Override
    protected Observable<CommonBean<PageBean<OrderDetailBean>>> getListDatas() {
        queryBean.setPageNum(currentPage);
        return HttpOrderFactory.queryOrder(queryBean);

    }

    @Override
    protected void initListener() {
        super.initListener();
        switchRight.setListener(this);
        switchLeft.setListener(this);
        orderMainPop.setOnDismissListener(this);
        orderMainPop.setListener(this);

    }

    @Override
    public void onOpen(SwitchTextLayout switchTextLayout, View view) {
        changeState(switchTextLayout, true);


    }

    @Override
    public void onClose(SwitchTextLayout switchTextLayout, View view) {
        changeState(switchTextLayout, false);
    }

    @Override
    public void onPopuClick(OrderMainPop pop, View view, int position) {

        if (view.getId() == R.id.switch_left) {//时间
            leftSelection = position;
            switchLeft.setTitle(position==0?getString(R.string.order_main_start_time): times.get(position));
            String time = null;
            switch (position) {
                case 0://全部
                    break;
                case 1://今天
                    time = "today";

                    break;
                case 2://明天
                    time = "tomorrow";
                    break;
                case 3://本周
                    time = "thisweek";
                    break;
                case 4://下周
                    time = "nextweek";
                    break;
            }
            queryBean.setDeliveryDateType(time);
        } else if (view.getId() == R.id.switch_right) {
            rightSelection = position;
            switchRight.setTitle(states.get(position));
            queryBean.setQueryStatus(null);
            queryBean.setHasFreight(null);
            if (position == 0) {//全部状态
                queryBean.setQueryStatus(null);
            } else if (position == 1&&roleState== OrderDetailItemControl.RoleState.CARGO_OWNER) {//待支付状态
                queryBean.setQueryStatus(null);
                queryBean.setHasFreight(false);
            } else {
                queryBean.setQueryStatus((1+position) + "");
            }
        }
        queryData(true, true);
        pop.dismiss();
    }


    private void changeState(View view, boolean open) {
        if (view.getId() == R.id.switch_left) {
            switchLeft.setSelect(true);
            switchRight.setSelect(false);
            switchRight.closeSwitch();

            if (open) {
                orderMainPop.setSelectPosition(leftSelection);
                orderMainPop.upDatas(times);
                orderMainPop.show(view);
            } else {
                orderMainPop.dismiss();
            }

        } else if (view.getId() == R.id.switch_right) {
            switchRight.setSelect(true);
            switchLeft.setSelect(false);
            switchLeft.closeSwitch();
            if (open) {

                orderMainPop.setSelectPosition(rightSelection);
                orderMainPop.upDatas(states);
                orderMainPop.show(view);
            } else {
                orderMainPop.dismiss();

            }
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
        switchLeft.closeSwitch();
        switchRight.closeSwitch();
    }

    @Override
    public boolean isShowing() {
        if (orderMainPop != null) {
            return orderMainPop.isShow();
        } else {
            return false;
        }
    }

    @Override
    public void closePop() {
        if (orderMainPop != null) {
            orderMainPop.dismiss();
        }
    }

    @Override
    public void setRoalState(OrderDetailItemControl.RoleState state) {
        this.roleState = state;
    }


    /**
     * "取消订单";
     *
     * @param orderBean
     */
    @Override
    public void onOrderCancle(final OrderDetailBean orderBean) {
        creatDialog("确认要取消订单？", "订单一旦去取消，无法恢复", "返回订单", "取消订单")
                .setRightClickListener(new DialogControl.OnButtonRightClickListener() {
                    @Override
                    public void onRightClick(View view, DialogControl.ICenterDialog dialog) {
                        dialog.dismiss();
                        HttpOrderFactory.cancleOrder(orderBean.getId())
                                .subscribe(new NetObserver<OrderDetailBean>(OrderMainFragmet.this) {
                                    @Override
                                    public void doOnSuccess(OrderDetailBean data) {
                                        queryData(true, true);
                                    }
                                });
                    }
                }).creatDialog(new CenterAlertDialog(getContext()))
                .show();
    }



    @Subscribe( threadMode = ThreadMode.MAIN)
    public void onMessageEvent(final OrderEvent.OrderUpdate event) {
        if (event != null) {
            if (rv!=null&&roleState!=null&&!roleState.equals(event.roleState)){
                rv.smoothScrollToPosition(0);
            }
            queryData(true,true);
        }
    }



    /**
     * 购买保险
     *
     * @param orderBean
     */
    @Override
    public void onOrderBuyInsurance(OrderDetailBean orderBean) {
        ArouterUtils.getInstance()
                .builder(ArouterParamOrder.activity_order_pay)
                .withBoolean(Param.TRAN, true)
                .navigation(getContext());
        OrderEvent.PayOrder payOrder = new OrderEvent.PayOrder();
        payOrder.insurance = true;
        payOrder.orderID = orderBean.getId();
        payOrder.orderNum = orderBean.getOrderNum();
        BusFactory.getBus().postSticky(payOrder);
//        changeOrderState(orderBean, 2, true, true);
    }

    /**
     * ORDER_PAY 继续付款
     *
     * @param orderBean
     */
    @Override
    public void onOrderPay(OrderDetailBean orderBean) {
        if (orderBean.getMoney() != null) {
            try {
                OrderEvent.PayOrder payOrder = new OrderEvent.PayOrder();
                payOrder.insurance = false;
                payOrder.money = Float.parseFloat(orderBean.getMoney());
                payOrder.orderID = orderBean.getId();
                payOrder.orderNum = orderBean.getOrderNum();
                BusFactory.getBus().postSticky(payOrder);
                ArouterUtils.getInstance()
                        .builder(ArouterParamOrder.activity_order_pay)
                        .navigation(getContext());

//                changeOrderState(orderBean, 2, false, true);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }


        }

    }


    /**
     * ORDER_CHECK_INSURANCE 查看保单
     *
     * @param orderBean
     */
    @Override
    public void onCheckInsruance(OrderDetailBean orderBean) {
        if (orderBean.getPolicyInfo() != null) {
            OrderCreatBean orderCreatBean = new Gson().fromJson(orderBean.getPolicyInfo(), OrderCreatBean.class);
            OrderUtils.scanPDf(getActivity(), orderCreatBean.getDownloadUrl());
        } else {
            ToastUtils.getInstance().makeToast(ToastUtils.ToastType.NORMAL).show("暂无保单信息");

        }
    }

    /**
     * ORDER_CHECK_PATH 查看轨迹
     *
     * @param orderBean
     */
    @Override
    public void onCheckPath(final OrderDetailBean orderBean) {
        ArouterUtils.getInstance().builder(ArouterParamOrder.activity_map_check_path).withBoolean(Param.TRAN, true).navigation(getActivity());
        OrderEvent.CheckPathEvent checkPathEvent = new OrderEvent.CheckPathEvent(orderBean);
        checkPathEvent.setBean(orderBean);
        checkPathEvent.setRoaleState(roleState);
        BusFactory.getBus().postSticky(checkPathEvent);

    }

    @Override
    public void onStart() {
        super.onStart();
        queryData(true);
    }

    /**
     * ORDER_ENTRY_ORDER 确认收货
     *
     * @param orderBean
     */
    @Override
    public void onEntryOrder(final OrderDetailBean orderBean) {
        creatDialog("确认已收到货物？", "收货请务必检查货物完好无损", "返回订单", "确定收货")
                .setRightClickListener(new DialogControl.OnButtonRightClickListener() {
                    @Override
                    public void onRightClick(View view, DialogControl.ICenterDialog dialog) {
                        dialog.dismiss();
                        HttpOrderFactory.entryReceiveCargo(orderBean.getId())
                                .subscribe(new NetObserver<OrderDetailBean>(OrderMainFragmet.this) {
                                    @Override
                                    public void doOnSuccess(OrderDetailBean data) {
                                        BusFactory.getBus().post(new OrderEvent.OrderUpdate(roleState));
                                    }
                                });
                    }
                }).creatDialog(new CenterAlertDialog(getContext()))
                .show();
    }

    /**
     * ORDER_START_CAR           ="开始发车";
     *
     * @param orderBean
     */
    @Override
    public void onStartCar(final OrderDetailBean orderBean) {
        creatDialog("确认要开始发车？", "车辆行驶中请记得安全驾车", "返回订单", "开始发车")
                .setRightClickListener(new DialogControl.OnButtonRightClickListener() {
                    @Override
                    public void onRightClick(View view, DialogControl.ICenterDialog dialog) {

                        double v = MapConverUtils.caculeDis(latLng.latitude
                                , latLng.longitude
                                , orderBean.getDestinationLatitude(), orderBean.getDestinationLongitude());
                        dialog.dismiss();
                        if (latLng.latitude == 0 || latLng.longitude == 0) {
                            ToastUtils.getInstance().makeToast(ToastUtils.ToastType.CENTER).show("正在获取当前位置，请稍后再试");
                        } else if (v > Param.ENTRY_MIN) {//距离过大，超过确认订单的最大距离
//                        if (false) {
                            ToastUtils.getInstance().makeToast(ToastUtils.ToastType.CENTER).show("距离发货地点还有"+(v/1000)+"公里，无法确认到达");
                        } else {
                            HttpOrderFactory.driverStart(orderBean.getId())
                                    .subscribe(new NetObserver<String>(OrderMainFragmet.this) {
                                        @Override
                                        public void doOnSuccess(String data) {
                                            OrderEvent.UpLoactionEvent upLoactionEvent = new OrderEvent.UpLoactionEvent();
                                            upLoactionEvent.start = true;
                                            upLoactionEvent.orderID = orderBean.getId();
                                            upLoactionEvent.cardID = orderBean.getCarId();
                                            upLoactionEvent.destinationLatitude = orderBean.getDestinationLatitude();
                                            upLoactionEvent.destinationLongitude = orderBean.getDestinationLongitude();

                                            BusFactory.getBus().post(upLoactionEvent);
                                            BusFactory.getBus().post(new OrderEvent.OrderUpdate(roleState));

                                        }
                                    });
                        }

                        dialog.dismiss();
                    }
                }).creatDialog(new CenterAlertDialog(getContext()))
                .show();
    }

    /**
     * ORDER_CHECK_ROUT          ="查看路线";
     *
     * @param orderBean
     */
    @Override
    public void onCheckRout(final OrderDetailBean orderBean) {
        PermissionUtils.applyMap(getActivity(), new PermissionUtils.onApplyPermission() {
            @Override
            public void hasPermission(List<String> granted, boolean isAll) {

                Poi start = new Poi(orderBean.getStartPlaceInfo(), new LatLng(orderBean.getStartLatitude(), orderBean.getStartLongitude()), "");
                Poi end = new Poi(orderBean.getDestinationInfo(), new LatLng(orderBean.getDestinationLatitude(), orderBean.getDestinationLongitude()), "");
                AmapNaviParams amapNaviParams = new AmapNaviParams(start, null, end, AmapNaviType.DRIVER);
                amapNaviParams.setUseInnerVoice(true);
                LoactionCollectionUtils loactionCollectionUtils = new LoactionCollectionUtils();
                loactionCollectionUtils.setOrderNum(orderBean.getId());
                loactionCollectionUtils.setCarID(orderBean.getCarId());
                AmapNaviPage.getInstance().showRouteActivity(getContext().getApplicationContext(), amapNaviParams, loactionCollectionUtils);
            }


            @Override
            public void noPermission(List<String> denied, boolean quick) {

            }
        });

    }

    /**
     * ORDER_ENTRY_ARRIVE        ="确认到达";
     *
     * @param orderBean
     */
    @Override
    public void onEntryArrive(final OrderDetailBean orderBean) {
        creatDialog("确认已到达目的地？", "感谢您的辛苦付出", "返回订单", "确定到达")
                .setRightClickListener(new DialogControl.OnButtonRightClickListener() {
                    @Override
                    public void onRightClick(View view, DialogControl.ICenterDialog dialog) {
                        double v = MapConverUtils.caculeDis(latLng.latitude
                                , latLng.longitude
                                , orderBean.getDestinationLatitude(), orderBean.getDestinationLongitude());
                        dialog.dismiss();
                        if (latLng.latitude == 0 || latLng.longitude == 0) {
                            ToastUtils.getInstance().makeToast(ToastUtils.ToastType.CENTER).show("正在获取当前位置，请稍后再试");
                        } else if (v > Param.ENTRY_MIN) {//距离过大，超过确认订单的最大距离
//                        if (false) {
                            ToastUtils.getInstance().makeToast(ToastUtils.ToastType.CENTER).show("距离收货地点还有"+(v/1000)+"公里，无法确认到达");
                        } else {
                            HttpOrderFactory.entryArrive(orderBean.getId())
                                    .subscribe(new NetObserver<String>(OrderMainFragmet.this) {
                                        @Override
                                        public void doOnSuccess(String data) {
                                            BusFactory.getBus().post(new OrderEvent.UpLoactionEvent());
                                            BusFactory.getBus().post(new OrderEvent.OrderUpdate(roleState));
                                        }
                                    });
                        }
                    }
                }).creatDialog(new CenterAlertDialog(getContext()))
                .show();
    }

    private CenterAlertBuilder creatDialog(String title, String content, String btleft, String
            btRight) {
        return new CenterAlertBuilder()
                .setDialogTitle(title)
                .setDialogContent(content)
                .setBtLeft(btleft)
                .setBtRight(btRight)
                .setBtLeftColor(getResources().getColor(R.color.color_title_dark))
                .setBtRightColor(getResources().getColor(R.color.color_white))
                .setBtRightBgRes(R.drawable.shape_f06f28);
    }


}
